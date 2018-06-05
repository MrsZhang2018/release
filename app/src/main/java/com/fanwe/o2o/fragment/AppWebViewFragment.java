package com.fanwe.o2o.fragment;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.fanwe.library.SDLibrary;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.event.EOnActivityResult;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.common.CommonOpenLoginSDK;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.ELoginSuccess;
import com.fanwe.o2o.event.ERefreshReload;
import com.fanwe.o2o.event.EUnLogin;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.jshandler.AppJsHandler;
import com.fanwe.o2o.model.UploadImageActModel;
import com.fanwe.o2o.utils.PhotoBotShowUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.io.File;
import java.util.Map;

public class AppWebViewFragment extends WebViewFragment {
	private String intentUrl;
	private PhotoHandler mHandler;
	private ImageFileCompresser mCompresser;

	@Override
	public void setUrl(String url)
	{
		this.mStrUrl = wrapperUrl(url);
		this.intentUrl = url;
	}

	public String getUrl(String url)
	{
		return wrapperUrl(url);
	}

	@Override
	protected void addJavascriptInterface()
	{
		// 详情回调处理
		AppJsHandler detailHandler = new AppJsHandler(getActivity())
		{
			@Override
			@JavascriptInterface
			public void page_title(final String title)
			{
				SDHandlerManager.getMainHandler().post(new Runnable()
				{
					@Override
					public void run()
					{
						setTitle(title);
					}
				});
			}

			@Override
			@JavascriptInterface
			public void third_party_login_sdk(final String value) {
				SDHandlerManager.getMainHandler().post(new Runnable() {
					@Override
					public void run() {
						if (value.equals("wxlogin")) {
							platformWx();
						}
					}
				});

			}

			@Override
			@JavascriptInterface
			public void CutPhoto() {
				SDHandlerManager.getMainHandler().post(new Runnable() {
					@Override
					public void run() {

						mHandler = new PhotoHandler((FragmentActivity) getActivity());
						mHandler.setListener(new PhotoHandler.PhotoHandlerListener() {
							@Override
							public void onResultFromAlbum(File file) {
								uploadImage(file);
							}

							@Override
							public void onResultFromCamera(File file) {
								uploadImage(file);
							}

							@Override
							public void onFailure(String msg) {
							}
						});
						PhotoBotShowUtils.openBotPhotoView(getActivity(), mHandler, PhotoBotShowUtils.DIALOG_BOTH);
					}
				});

			}

			/**
			 * 压缩上传图片
			 *
			 */
			private void uploadImage(File file) {
				mCompresser = new ImageFileCompresser();
				mCompresser.setmListener(new ImageFileCompresser.ImageFileCompresserListener()
				{
					@Override
					public void onStart()
					{
						getBaseActivity().showProgressDialog("正在处理图片");
					}

					@Override
					public void onSuccess(File fileCompressed)
					{

						CommonInterface.requestUploadImage(fileCompressed, new AppRequestCallback<UploadImageActModel>()
						{
							@Override
							protected void onSuccess(SDResponse resp)
							{
								if (actModel.isOk())
								{
									SDHandlerManager.getMainHandler().post(new Runnable() {
										@Override
										public void run() {
											getWebView().loadJsFunction(Constant.JS_CUT_CALL_BACK, actModel.getBig_url());
										}
									});
								}
							}

							@Override
							protected void onError(SDResponse resp)
							{
								super.onError(resp);
								SDToast.showToast("上传失败");
							}

							@Override
							protected void onFinish(SDResponse resp)
							{
								super.onFinish(resp);
							}
						});
					}

					@Override
					public void onFailure(String msg)
					{
						if (!TextUtils.isEmpty(msg))
						{
							SDToast.showToast(msg);
						}
					}

					@Override
					public void onFinish()
					{
						getBaseActivity().dismissProgressDialog();
					}
				});
				mCompresser.setmMaxLength(200 * 200);
				mCompresser.compressImageFile(file);

			}

			/**
			 * 定位
			 *
			 */
			@Override
			@JavascriptInterface
			public void position() {
				if (BaiduMapManager.getInstance().hasLocationSuccess())
				{
					getWebView().loadJsFunction(Constant.JS_POSITION, BaiduMapManager.getInstance().getLatitude(),BaiduMapManager.getInstance().getLongitude());
				}else {
					getWebView().loadJsFunction(Constant.JS_POSITION,0,0);
				}
			}
		};
		detailHandler.setUrl(mStrUrl);
		mWeb.addJavascriptInterface(detailHandler, detailHandler.getName());
	}

	@Override
	public void onDestroy() {
		if (mCompresser != null)
			mCompresser.deleteCompressedImageFile();
		super.onDestroy();
	}

	public void onEventMainThread(final EOnActivityResult event)
	{
		super.onEventMainThread(event);
		if (event.activity == getActivity())
		{
			mHandler.onActivityResult(event.requestCode, event.resultCode, event.data);
		}
	}

	/**
	 * 获取微信平台用户数据
	 */
	public void platformWx() {
		CommonOpenLoginSDK.loginWx(getActivity(), new UMAuthListener() {

			@Override
			public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
				SDToast.showToast("授权成功");
				getWebView().loadJsFunction(Constant.JS_THIRD_PARTY_LOGIN_SDK, SDJsonUtil.object2Json(data));
			}

			@Override
			public void onError(SHARE_MEDIA platform, int action, Throwable t) {
				SDToast.showToast("授权失败");
			}

			@Override
			public void onCancel(SHARE_MEDIA platform, int action) {
				SDToast.showToast("授权取消");
			}
		});
	}

	@Override
	protected void onReceivedTitle(WebView view,final String title) {
		setTitle(title);
	}

	private String wrapperUrl(String url)
	{
		String resultUrl = putSessionId(url);
		resultUrl = putPageType(resultUrl);
		resultUrl = putRefId(resultUrl);
		resultUrl = putTip(resultUrl);
		return resultUrl;
	}

	private String putPageType(String url)
	{
		if (!isEmpty(url))
		{
			url = new UrlLinkBuilder(url).add("page_type", "app").build();
		}
		return url;
	}

	private String putRefId(String url)
	{
		String refId = AppConfig.getRefId();
		if (!isEmpty(url) && !isEmpty(refId))
		{
			url = new UrlLinkBuilder(url).add("r", SDBase64.encodeToString(refId)).build();
		}
		return url;
	}

	private String putTip(String url)
	{
		if (!isEmpty(url))
		{
			url = new UrlLinkBuilder(url).add("from","app").add("r_type","5").add("page_finsh","1").build();
		}
		return url;
	}

	private String putSessionId(String url)
	{
		// 如果是域名的url，添加session
		if (url != null && url.contains(ApkConstant.SERVER_URL_DOMAIN))
		{
			String sessionId = AppConfig.getSessionId();
			if (sessionId != null)
			{
				url = new UrlLinkBuilder(url).add("sess_id", sessionId).build();
			}
		}
		return url;
	}

	@Override
	protected ProgressBar findProgressBar() {
		ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		progressBar.setProgressDrawable(SDLibrary.getInstance().getApplication().getResources().getDrawable(R.drawable.style_progress));
		return progressBar;
	}

	/**
	 * 重新加载首页
	 */
	public void onEventMainThread(ERefreshReload event) {
		mWeb.get(mStrUrl);
	}

	public void onEventMainThread(ELoginSuccess event) {
//		startLoadData();
		mWeb.get(getUrl(intentUrl));//带session的URL刷新
		getWebView().loadJsFunction(Constant.JS_AJAX_LOAD,AppConfig.getSessionId());
	}

	public void onEventMainThread(EUnLogin event) {
		if(mWeb!=null) {
			if(intentUrl!=null) {
				mWeb.get(getUrl(intentUrl));
			}
		}
	}


	// 处理web加载时候显示的Loadingdialog
	protected void onProgressChanged(WebView view, int newProgress)
	{
		if (mProgressMode != null)
		{
			if (newProgress == 100)
			{
				getBaseActivity().dismissProgressDialog();
			} else
			{
				getBaseActivity().showProgressDialog("");
			}
		}
		super.onProgressChanged(view,newProgress);
	}


	public SDBaseActivity getBaseActivity()
	{
		return (SDBaseActivity) getActivity();
	}
}
