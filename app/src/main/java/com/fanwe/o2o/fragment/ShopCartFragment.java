package com.fanwe.o2o.fragment;

import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.event.ELoginSuccess;
import com.fanwe.o2o.event.EUnLogin;

/**
 * 购物车
 * Created by Administrator on 2016/12/8.
 */

public class ShopCartFragment extends BaseFragment {

    private AppWebViewFragment webViewFragment;
    @Override
    protected int onCreateContentView() {
        return R.layout.frag_o2o_tab_shop_cart;
    }

    @Override
    protected void init() {
        super.init();
        webViewFragment = new AppWebViewFragment();
        String url = ApkConstant.SERVER_URL_WAP;
        UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
        urlBuilder.add("ctl", "cart");
        webViewFragment.setUrl(urlBuilder.build());// mFragWebview.setUrl("http://o2o.fanwe.net/wap/index.php?ctl=cart");
        webViewFragment.setmProgressMode(WebViewFragment.EnumProgressMode.HORIZONTAL);
        webViewFragment.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.MATCH_PARENT);

        getSDFragmentManager().replace(R.id.fl_shop_cart_content, webViewFragment);
    }
}
