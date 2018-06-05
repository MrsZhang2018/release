package com.fanwe.library.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.event.EOnActivityResult;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * WebView fragment
 *
 * @author js02
 */
public class WebViewFragment extends SDBaseFragment implements SDTitleSimple.SDTitleSimpleListener
{
    public static final String no_network_url = "file:///android_asset/error_network.html";

    public static final int REQUEST_GET_CONTENT = 100;

    protected ProgressBar mPgbHorizontal;
    protected CustomWebView mWeb;

    protected EnumProgressMode mProgressMode = EnumProgressMode.NONE;
    protected EnumWebviewHeightMode mWebviewHeightMode = EnumWebviewHeightMode.WRAP_CONTENT;

    protected String mStrUrl;
    protected String mStrHtmlContent;
    protected String mStrReferer;
    protected String mStrTitle;

    protected boolean isShowTitle = false;
    protected boolean isScaleToShowAll = false;
    protected boolean isSupportZoom = true;
    protected WebViewFragmentListener mListener;
    protected SDTitleSimple mTitle;
    protected TextView mTvClose;
    protected ValueCallback<Uri> mUploadMsg;

    public final static int FILECHOOSER_RESULTCODE = 201;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 200;
    protected ValueCallback<Uri> mUploadMessage;
    protected ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public void setmListener(WebViewFragmentListener mListener)
    {
        this.mListener = mListener;
    }

    public void setmProgressMode(EnumProgressMode mProgressMode)
    {
        this.mProgressMode = mProgressMode;
        changeProgressMode();
    }

    public void setmWebviewHeightMode(EnumWebviewHeightMode mWebviewHeightMode)
    {
        this.mWebviewHeightMode = mWebviewHeightMode;
        changeWebViewHeightMode();
    }

    public CustomWebView getWebView()
    {
        return this.mWeb;
    }

    public void setShowTitle(boolean isShowTitle)
    {
        this.isShowTitle = isShowTitle;
        changeTitleVisibility();
    }

    public void setSupportZoom(boolean isSupportZoom)
    {
        this.isSupportZoom = isSupportZoom;
    }

    public void setScaleToShowAll(boolean isScaleToNormal)
    {
        this.isScaleToShowAll = isScaleToNormal;
    }

    public void setUrl(String url)
    {
        this.mStrUrl = url;
    }

    public void setTitle(String title)
    {
        this.mStrTitle = title;
        if (mTitle != null)
        {
            mTitle.setMiddleTextTop(title);
        }
    }

    public void setHtmlContent(String htmlContent)
    {
        this.mStrHtmlContent = htmlContent;
    }

    public void setReferer(String referer)
    {
        this.mStrReferer = referer;
    }

    @Override
    protected int onCreateContentView()
    {
        int resId = getContentViewResId();
        if (resId == 0)
        {
            resId = R.layout.frag_webview;
        }
        return resId;
    }

    /**
     * 可以被重写返回fragment布局ID，如果重写此方法，需要重写findWebView()和findProgressBar()
     *
     * @return
     */
    protected int getContentViewResId()
    {
        return 0;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        findViews();
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected View onCreateTitleView()
    {
        mTitle = new SDTitleSimple(getActivity());
        mTitle.setmListener(this);
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_grey);
        return mTitle;
    }

    private void findViews()
    {
        mWeb = (CustomWebView) findViewById(R.id.wv_webview);
        mPgbHorizontal = (ProgressBar) findViewById(R.id.pgb_horizontal);

        CustomWebView newWebview = findWebView();
        if (newWebview == null)
        {
            newWebview = new CustomWebView(getActivity());
        }
        SDViewUtil.replaceOldView(mWeb, newWebview);
        mWeb = newWebview;

        ProgressBar newProgressBar = findProgressBar();
        if (newProgressBar == null)
        {
            newProgressBar = new ProgressBar(getActivity());
        }
        SDViewUtil.replaceOldView(mPgbHorizontal, newProgressBar);
        mPgbHorizontal = newProgressBar;
    }

    protected CustomWebView findWebView()
    {
        return null;
    }

    protected ProgressBar findProgressBar()
    {
        return null;
    }

    protected void init()
    {
        initTitle(mStrTitle);
        changeWebViewHeightMode();
        changeProgressMode();
        initWebView();
        initFinish();
        startLoadData();
    }

    private void initTitle(String title)
    {

        mTitle.setMiddleTextTop(title);

        // addCloseButton();

        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextTop("关闭");

        changeTitleVisibility();
    }

    protected void changeWebViewHeightMode()
    {
        if (mWeb != null && mWebviewHeightMode != null)
        {
            switch (mWebviewHeightMode)
            {
                case WRAP_CONTENT:
                    SDViewUtil.setViewHeight(mWeb, ViewGroup.LayoutParams.WRAP_CONTENT);
                    break;
                case MATCH_PARENT:
                    SDViewUtil.setViewHeight(mWeb, ViewGroup.LayoutParams.MATCH_PARENT);
                    break;

                default:
                    break;
            }
        }
    }

    protected void changeProgressMode()
    {
        if (mProgressMode != null && mPgbHorizontal != null)
        {
            switch (mProgressMode)
            {
                case HORIZONTAL:
                    SDViewUtil.show(mPgbHorizontal);
                    break;
                case NONE:
                    SDViewUtil.hide(mPgbHorizontal);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 初始化webview
     */
    private void initWebView()
    {
        initSetting();
        addJavascriptInterface();

        mWeb.setWebViewClientListener(new DefaultWebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                WebViewFragment.this.shouldOverrideUrlLoading(view, url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWeb.loadUrl(no_network_url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });

        mWeb.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                WebViewFragment.this.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                WebViewFragment.this.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
            {
                WebViewFragment.this.openFileChooser(uploadFile, acceptType, capture);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                WebViewFragment.this.onReceivedTitle(view, title);
            }
        });

        if (mListener != null)
        {
            mListener.onInitFinish(mWeb);
        }
    }

    protected void initFinish()
    {

    }

    protected void initSetting()
    {
        mWeb.setSupportZoom(isSupportZoom);
        mWeb.setScaleToShowAll(isScaleToShowAll);
    }

    protected void addJavascriptInterface()
    {

    }

    public void startLoadData()
    {
        if (mStrHtmlContent != null)
        {
            mWeb.loadData(mStrHtmlContent);
            return;
        }

        if (mStrUrl != null)
        {
            if (!TextUtils.isEmpty(mStrReferer))
            {
                Map<String, String> mapHeader = new HashMap<String, String>();
                mapHeader.put("Referer", mStrReferer);
                mWeb.get(mStrUrl, mapHeader);
            } else
            {
                mWeb.get(mStrUrl);
            }
            return;
        }
    }

    // WebViewClient 方法回调
    protected boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if (url.startsWith("weixin://"))
        {
            try
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e)
            {
            }
            return true;
        }

        if (mListener != null)
        {
            return mListener.onLoadUrl(view, url);
        }
        return false;
    }

    // WebChromeClient 方法回调
    protected void onProgressChanged(WebView view, int newProgress)
    {
        if (mProgressMode != null)
        {
            switch (mProgressMode)
            {
                case HORIZONTAL:
                    if (newProgress == 100)
                    {
                        SDViewUtil.hide(mPgbHorizontal);
                    } else
                    {
                        SDViewUtil.show(mPgbHorizontal);
                        mPgbHorizontal.setProgress(newProgress);
                    }
                    break;
                case NONE:

                    break;

                default:
                    break;
            }
        }
    }

    protected void onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
    {
        mUploadMessageForAndroid5 = filePathCallback;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");

        getActivity().startActivityForResult(chooserIntent,
                FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    protected void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
    {
        mUploadMessage = uploadFile;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        getActivity().startActivityForResult(Intent.createChooser(i, "选择图片"),
                FILECHOOSER_RESULTCODE);
    }

    protected void onReceivedTitle(WebView view, String title)
    {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onEventMainThread(final EOnActivityResult event)
    {
        if (event.requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = event.data == null || event.resultCode != Activity.RESULT_OK ? null
                    : event.data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (event.requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;

            SDHandlerManager.getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {

                    LogUtil.i("FILECHOOSER_RESULTCODE_FOR_ANDROID_5");

                    Uri result = (event.data == null || event.resultCode != Activity.RESULT_OK) ? null
                            : event.data.getData();
                    if (result != null) {
                        mUploadMessageForAndroid5.onReceiveValue(new Uri[] { result });
                    } else {
                        mUploadMessageForAndroid5.onReceiveValue(new Uri[] {});
                    }
                    mUploadMessageForAndroid5 = null;
                }
            });


        }
    }

    public boolean goBack()
    {
        boolean goback = false;
        if (mWeb != null && mWeb.canGoBack())
        {
            mWeb.goBack();
            goback = true;
        }
        return goback;
    }

    @Override
    public void onPause()
    {
        mWeb.onPause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        mWeb.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        if (mWeb != null)
        {
            mWeb.destroy();
            mWeb = null;
        }
        super.onDestroy();
    }

    private void changeTitleVisibility()
    {
        if (mTitle != null)
        {
            if (isShowTitle)
            {
                SDViewUtil.show(mTitle);
            } else
            {
                SDViewUtil.hide(mTitle);
            }
        }
    }

    protected void addCloseButton()
    {
        if (mTvClose != null)
        {
            mTitle.mLlLeft.removeView(mTvClose);
        }
        LinearLayout.LayoutParams paramsClose = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        // paramsClose.leftMargin = SDViewUtil.dp2px(15);

        mTvClose = new TextView(getActivity());
        mTvClose.setText("关闭");
        SDViewUtil.setTextSizeSp(mTvClose, 17);
        mTvClose.setGravity(Gravity.CENTER);
        mTvClose.setTextColor(Color.parseColor("#ffffff"));
        mTvClose.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                getActivity().finish();
            }
        });
        mTitle.mLlLeft.addView(mTvClose, paramsClose);
    }

    @Override
    public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
        if (no_network_url.equals(mWeb.getUrl())) {
            //如果当前url是网络错误的url，返回就关闭页面
            getActivity().finish();
            return;
        }

        if (goBack())
        {
        } else
        {
            getActivity().finish();
        }
    }

    @Override
    public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        getActivity().finish();
    }

    public static class WebViewFragmentListener
    {
        public void onInitFinish(WebView webView)
        {
        }

        public boolean onLoadUrl(WebView webView, String url)
        {
            return false;
        }
    }

    public enum EnumProgressMode
    {
        HORIZONTAL, NONE
    }

    public enum EnumWebviewHeightMode
    {
        WRAP_CONTENT, MATCH_PARENT
    }
}