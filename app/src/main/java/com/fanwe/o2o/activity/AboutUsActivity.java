package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.o2o.R;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.fragment.AppWebViewFragment;

/**
 * 关于我们
 * Created by Administrator on 2017/2/22.
 */

public class AboutUsActivity extends BaseTitleActivity
{
    /**
     * 要显示的HTML内容
     */
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";

    private String htmlContent;
    private String htmlContentString;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_about_us);

        title.setLeftImageLeft(R.drawable.ic_o2o_back);
        title.setMiddleTextTop("关于我们");

        if (getIntent().hasExtra(EXTRA_HTML_CONTENT))
        {
            htmlContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
            htmlContentString = htmlContent.replaceAll("src=\".","src=\""+ ApkConstant.SERVER_URL_SCHEMES+ApkConstant.SERVER_URL_DOMAIN);
        }

        AppWebViewFragment fraHtml = new AppWebViewFragment();
        fraHtml.setmProgressMode(WebViewFragment.EnumProgressMode.NONE);
        fraHtml.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.MATCH_PARENT);
        getSDFragmentManager().replace(R.id.fl_about_us, fraHtml);
        fraHtml.setHtmlContent(htmlContentString);
    }
}
