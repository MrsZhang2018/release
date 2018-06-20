package com.fanwe.o2o.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.fanwe.library.webview.CustomWebView;
import com.fanwe.o2o.R;

/**
 * Created：Eric on 2018/6/19 20:17
 * describe:
 */


public class SmallWebViewFragment extends LinearLayout {

    private String htmlData = "";

    private CustomWebView webView;
    private View rootView;

    public SmallWebViewFragment(Context context) {
        super(context);
        initView();
    }

    public SmallWebViewFragment(Context context, String htmlData) {
        super(context);
        this.htmlData = htmlData;
        initView();
    }

    private void initView() {
        if (rootView == null)
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.small_frag_webview, null, false);
        webView = rootView.findViewById(R.id.wv_webview);
        start();
        addView(rootView);
    }

    private void start() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (!TextUtils.isEmpty(htmlData)) {
            webView.loadDataWithBaseURL("about:blank", htmlData, "text/html", "utf-8", null);
        }
    }

}
