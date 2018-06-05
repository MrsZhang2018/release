package com.fanwe.o2o.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebViewClient;
import com.fanwe.o2o.R;
import com.fanwe.o2o.event.ERefreshReload;
import com.fanwe.o2o.jshandler.LiveJsHandler;

import org.xutils.http.cookie.DbCookieStore;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;


/**
 * Created by luodong on 2016/12/9
 */
public class O2oWebViewActivity extends BaseTitleActivity
{
    public static final String no_network_url = "file:///android_asset/error_network.html";
    // webview 要加载的链接
    public static final String EXTRA_URL = "extra_url";
    //要显示的HTML内容
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";

    private String url;
    private String htmlContent;

    protected CustomWebView customWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_webview);
        init();
    }

    private void init()
    {
        customWebView = find(R.id.webview);
        getIntentInfo();
        customWebView.setSupportZoom(true);
        customWebView.setScaleToShowAll(false);
        customWebView.addJavascriptInterface(new LiveJsHandler(this));
        customWebView.getSettings().setBuiltInZoomControls(true);
        initWebView();
    }

    private void getIntentInfo()
    {
        if (getIntent().hasExtra(EXTRA_URL))
        {
            url = getIntent().getStringExtra(EXTRA_URL);
        }
        if (getIntent().hasExtra(EXTRA_HTML_CONTENT))
        {
            htmlContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
        }
    }

    private void initWebView()
    {
        customWebView.setWebViewClientListener(new DefaultWebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
                customWebView.loadUrl(no_network_url);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                title.setMiddleTextTop(view.getTitle());
            }
        });

        customWebView.setWebChromeClientListener(new WebChromeClient()
        {

            @Override
            public void onReceivedTitle(WebView view, String mTitle)
            {
                title.setMiddleTextTop(view.getTitle());
            }
        });

        if (!TextUtils.isEmpty(url))
        {
            initCookies();
            customWebView.get(url);
        } else if (!TextUtils.isEmpty(htmlContent))
        {
            customWebView.loadData(htmlContent, "text/html", "utf-8");
//            customWebView.loadData(htmlContent);
        }
    }

    /**
     * 同步一下cookie
     */
    public void initCookies()
    {
        if (!TextUtils.isEmpty(url))
        {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            try
            {
                URI uri = new URI(url);
                List<HttpCookie> list = DbCookieStore.INSTANCE.getCookies();
                for (HttpCookie httpCookie : list)
                {
                    String name = httpCookie.getName();
                    String value = httpCookie.getValue();
                    String cookieString = name + "=" + value;
                    cookieManager.setCookie(url, cookieString);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            CookieSyncManager.getInstance().sync();
        }

    }


    @Override
    public void onCLickLeft_SDTitleSimple(SDTitleItem v)
    {
        if (no_network_url.equals(customWebView.getUrl()))
        {
            //如果当前url是网络错误的url，返回就关闭页面
            finish();
            return;
        }
        if (customWebView.canGoBack())
        {
            customWebView.goBack();
        } else
        {
            finish();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        customWebView.reload();
    }

    @Override
    public void onBackPressed()
    {
        if (customWebView.canGoBack())
        {
            customWebView.goBack();
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        customWebView.removeAllViews();
        customWebView.destroy();
    }

    /**
     * 重新加载首页
     */
    public void onEventMainThread(ERefreshReload event)
    {
        customWebView.get(url);
    }
}
