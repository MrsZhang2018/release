package com.fanwe.o2o.fragment;

import android.webkit.JavascriptInterface;

import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonOpenLoginSDK;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.EThirdPartyLoginSDK;
import com.fanwe.o2o.jshandler.AppJsHandler;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 我的
 * Created by Administrator on 2016/12/8.
 */

public class MeFragment extends BaseFragment {
    private AppWebViewFragment mFragWebview;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_o2o_tab_me;
    }

    @Override
    protected void init() {
        super.init();
        mFragWebview = new AppWebViewFragment() {
            @Override
            protected void addJavascriptInterface() {
                super.addJavascriptInterface();
                // 详情回调处理
                AppJsHandler detailHandler = new AppJsHandler(getActivity()) {
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
                };
                mWeb.addJavascriptInterface(detailHandler, detailHandler.getName());
            }
        };


        String url = ApkConstant.SERVER_URL_WAP;
        UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
        urlBuilder.add("ctl", "user_center");
        mFragWebview.setUrl(urlBuilder.build());
        mFragWebview.setmProgressMode(WebViewFragment.EnumProgressMode.HORIZONTAL);
        mFragWebview.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.MATCH_PARENT);

        getSDFragmentManager().replace(R.id.fl_me_content, mFragWebview);
    }

    /**
     * 获取微信平台用户数据
     */
    public void platformWx() {
        CommonOpenLoginSDK.loginWx(getActivity(), new UMAuthListener() {

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                SDToast.showToast("授权成功");
                mFragWebview.getWebView().loadJsFunction(Constant.JS_THIRD_PARTY_LOGIN_SDK, SDJsonUtil.object2Json(data));
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
}
