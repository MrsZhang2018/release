package com.fanwe.hybrid.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import cn.fanwe.yi.R;

import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.customview.SDSimpleTitleView;
import com.fanwe.hybrid.customview.SDSimpleTitleView.OnLeftButtonClickListener;
import com.fanwe.hybrid.customview.SDSimpleTitleView.OnRightButtonClickListener;
import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebViewClient;
import com.sunday.eventbus.SDBaseEvent;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-25 上午10:27:09 类说明
 */
public class AppWebViewActivity extends BaseActivity
{
	public static final String EXTRA_CODE = "open_url_type";
	/** webview 要加载的链接 */
	public static final String EXTRA_URL = "extra_url";
	/** 要显示的HTML内容 */
	public static final String EXTRA_HTML_CONTENT = "extra_html_content";

	public static final String EXTRA_FINISH_TO_MAIN = "extra_finish_to_mai";

	private int mCurrentExtraCode;

	private String mUrl;
	private String mHttmContent;

	@ViewInject(R.id.title)
	private SDSimpleTitleView mTitle;

	@ViewInject(R.id.webview)
	private CustomWebView mWeb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_app_webview);
		x.view().inject(this);
		init();
	}

	private void init()
	{
		// mWeb.addJavascriptInterface(new AppJsHandler(this));
		mWeb.getSettings().setBuiltInZoomControls(true);
		initTitle();
		getIntentInfo();
		initWebViewFragment();
	}

	private void initTitle()
	{
		mTitle.setLeftLinearLayout(new OnLeftButtonClickListener()
		{
			@Override
			public void onLeftBtnClick(View button)
			{
				finish();
			}
		});
		mTitle.setLeftButton("返回", R.drawable.ic_arrow_left_white, null);

		mTitle.setRightLinearLayout(new OnRightButtonClickListener()
		{
			@Override
			public void onRightBtnClick(View button)
			{
				mWeb.reload();
			}
		});
		mTitle.setRightButton("刷新", null, null);
	}

	private void getIntentInfo()
	{
		if (getIntent().hasExtra(EXTRA_FINISH_TO_MAIN))
		{
			getIntent().getExtras().getBoolean(EXTRA_FINISH_TO_MAIN);
		}

		if (getIntent().hasExtra(EXTRA_CODE))
		{
			mCurrentExtraCode = getIntent().getExtras().getInt(EXTRA_CODE);
		}

		mUrl = getIntent().getExtras().getString(EXTRA_URL);
		mHttmContent = getIntent().getExtras().getString(EXTRA_HTML_CONTENT);
	}

	private void initWebViewFragment()
	{
		final String wap_url = ApkConstant.SERVER_URL;
		mWeb.setWebViewClient(new DefaultWebViewClient()
		{
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				showDialog();
			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
				dimissDialog();
				mTitle.setTitle(view.getTitle());
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				if (url.contains(wap_url))
				{
					if (url.contains("?"))
					{
						url = url + "&app=1";
					} else
					{
						url = url + "?app=1";
					}
					view.loadUrl(url);
					return true;
				} else
				{
					return super.shouldOverrideUrlLoading(view, url);
				}

			}
		});

		mWeb.setWebChromeClient(new WebChromeClient()
		{

			@Override
			public void onReceivedTitle(WebView view, String title)
			{
				mTitle.setTitle(title);
			}

		});

		if (!TextUtils.isEmpty(mUrl))
		{
			mWeb.get(mUrl);
		} else if (!TextUtils.isEmpty(mHttmContent))
		{
			mWeb.loadData(mHttmContent, "text/html", "utf-8");
		}
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case EVENT_FINSHI_ACTIVITY:
			if (event.getData() != null)
			{
				String url = (String) event.getData();
				String domain = ApkConstant.SERVER_URL;
				if (url.contains("http://"))
				{

				} else
				{
					url = "http://" + domain + url + "&show_prog=1";
				}
				Intent intent = new Intent();
				intent.putExtra(MainActivity.EXTRA_URL, url);
				setResult(Activity.RESULT_OK, intent);

			}
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void finish()
	{
		if (getIntent().hasExtra(EXTRA_FINISH_TO_MAIN))
		{
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}

		if (mCurrentExtraCode == 2)
		{
			setResult(Activity.RESULT_OK);
		}

		super.finish();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// mWeb.onPause();
		// mWeb.pauseTimers();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// mWeb.onResume();
		// mWeb.resumeTimers();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// mWeb.loadUrl("about:blank");
		// mWeb.stopLoading();
		// mWeb.setWebChromeClient(null);
		// mWeb.setWebViewClient(null);
		// mWeb.destroy();
		// mWeb = null;
	}
}
