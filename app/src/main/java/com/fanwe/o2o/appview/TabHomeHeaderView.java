package com.fanwe.o2o.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.fragment.AppWebViewFragment;
import com.fanwe.o2o.fragment.HomeFragment;
import com.fanwe.o2o.model.WapIndexAdvs2Model;
import com.fanwe.o2o.model.WapIndexAdvsModel;
import com.fanwe.o2o.model.WapIndexArticleModel;
import com.fanwe.o2o.model.WapIndexIndexsListModel;
import com.fanwe.o2o.model.WapIndexSupplierListModel;

import java.util.List;


/**
 * Created by Administrator on 2016/12/9.
 */

public class TabHomeHeaderView extends SDAppView
{
    private LinearLayout ll_slide_play;
    private LinearLayout ll_slide_play2;
    private LinearLayout ll_adv;
    private LinearLayout ll_page_view;
    private LinearLayout ll_store;
    public LinearLayout ll_like;
    private FrameLayout fl_super_ben;
    private FrameLayout fl_good_qua;
    private FrameLayout fl_group_pur;
    private FrameLayout fl_shop_mall;

    private View view_up_on_fl_super_ben;
    private View view_up_on_fl_good_qua;
    private View view_up_on_fl_group_pur;
    private View view_up_on_fl_shop_mall;
    private View view_up_on_ll_store;

    private O2oSlidingPlayView slidingPlayView;
    private HomeSlidingPlayView slidingPlayView2;
    private IndexPageView indexPageView;
    private HomeHeadlinesView homeHeadlinesView;
    private O2oGoodStoreRecommendView storeView;

    private List<WapIndexAdvsModel> advs;
    private List<WapIndexAdvs2Model> advs2;
    private List<WapIndexIndexsListModel> list;
    private List<WapIndexArticleModel> article;
    private List<WapIndexSupplierListModel> supplier_list;
    private String html3, html4, html5, html6;

    private HomeFragment homeFragment;
    private AppWebViewFragment fraHtml3;
    private AppWebViewFragment fraHtml4;
    private AppWebViewFragment fraHtml5;
    private AppWebViewFragment fraHtml6;

    public TabHomeHeaderView(Context context)
    {
        super(context);
        init();
    }

    public TabHomeHeaderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public TabHomeHeaderView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public TabHomeHeaderView(Context context, HomeFragment homeFragment)
    {
        super(context);
        this.homeFragment = homeFragment;
        init();
    }

    public void setData(List<WapIndexAdvsModel> advs, List<WapIndexAdvs2Model> advs2, List<WapIndexIndexsListModel> list, List<WapIndexArticleModel> article,
                        List<WapIndexSupplierListModel> supplier_list, String html3, String html4, String html5, String html6)
    {
        this.advs = advs;
        this.advs2 = advs2;
        this.list = list;
        this.article = article;
        this.supplier_list = supplier_list;
        this.html3 = html3;
        this.html4 = html4;
        this.html5 = html5;
        this.html6 = html6;
        initData();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_home_header);
        initView();
    }

    private void initView()
    {
        ll_slide_play = find(R.id.ll_slide_play);
        ll_page_view = find(R.id.ll_page_view);
        ll_adv = find(R.id.ll_adv);
        ll_store = find(R.id.ll_store);
        ll_slide_play2 = find(R.id.ll_slide_play2);
        ll_like = find(R.id.ll_like);
        fl_good_qua = find(R.id.fl_good_qua);
        fl_group_pur = find(R.id.fl_group_pur);
        fl_shop_mall = find(R.id.fl_shop_mall);
        fl_super_ben = find(R.id.fl_super_ben);

        view_up_on_fl_super_ben = find(R.id.view_up_on_fl_super_ben);
        view_up_on_fl_good_qua = find(R.id.view_up_on_fl_good_qua);
        view_up_on_fl_group_pur = find(R.id.view_up_on_fl_group_pur);
        view_up_on_fl_shop_mall = find(R.id.view_up_on_fl_shop_mall);
        view_up_on_ll_store = find(R.id.view_up_on_ll_store);
    }

    private void initData()
    {
        initSlidingPlayView();
        initSlidingPlayView2();
        initAdvView();
        initGoodStoreRecommendView();
        initIndexPageView();
        initSuperBen();
        initGoodQua();
        initGroupPur();
        initShopMall();
    }

    public void setHomeFragment(HomeFragment homeFragment)
    {
        this.homeFragment = homeFragment;
    }

    /**
     * 首页广告
     */
    private void initSlidingPlayView()
    {
        if (slidingPlayView == null)
        {
            slidingPlayView = new O2oSlidingPlayView(getActivity());
            SDViewUtil.replaceView(ll_slide_play, slidingPlayView);
        }
        slidingPlayView.setData(advs);
    }

    /**
     * 首页导航分类
     */
    private void initIndexPageView()
    {
        if (indexPageView == null)
        {
            indexPageView = new IndexPageView(getActivity());
            SDViewUtil.replaceView(ll_page_view, indexPageView);
        }
        indexPageView.setmIndexModel(list);
    }

    /**
     * 头条跑马灯
     */
    private void initAdvView()
    {
        if (homeHeadlinesView == null)
        {
            homeHeadlinesView = new HomeHeadlinesView(getActivity());
            SDViewUtil.replaceView(ll_adv, homeHeadlinesView);
        }
        homeHeadlinesView.setData(article);
    }

    /**
     * 超实惠
     */
    private void initSuperBen()
    {
        if (!TextUtils.isEmpty(html3))
        {
            view_up_on_fl_super_ben.setVisibility(VISIBLE);
            if (fraHtml3 == null)
            {
                fraHtml3 = new AppWebViewFragment();
                fraHtml3.setmProgressMode(WebViewFragment.EnumProgressMode.NONE);
                fraHtml3.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.WRAP_CONTENT);
                homeFragment.getSDFragmentManager().replace(R.id.fl_super_ben, fraHtml3);
            }
            fraHtml3.setHtmlContent(html3);
        } else
        {
            fl_super_ben.setVisibility(GONE);
            view_up_on_fl_super_ben.setVisibility(GONE);
        }
    }

    /**
     * 好品质
     */
    private void initGoodQua()
    {
        if (!TextUtils.isEmpty(html6))
        {
            view_up_on_fl_good_qua.setVisibility(VISIBLE);
            if (fraHtml4 == null)
            {
                fraHtml4 = new AppWebViewFragment();
                fraHtml4.setmProgressMode(WebViewFragment.EnumProgressMode.NONE);
                fraHtml4.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.WRAP_CONTENT);
                homeFragment.getSDFragmentManager().replace(R.id.fl_good_qua, fraHtml4);
            }
            fraHtml4.setHtmlContent(html6);
        }else
        {
            fl_good_qua.setVisibility(GONE);
            view_up_on_fl_good_qua.setVisibility(GONE);
        }
    }

    /**
     * 中间轮播广告
     */
    private void initSlidingPlayView2()
    {
        if (advs2 != null && advs2.size() > 0)
        {
            if (slidingPlayView2 == null)
            {
                slidingPlayView2 = new HomeSlidingPlayView(getActivity());
                SDViewUtil.replaceView(ll_slide_play2, slidingPlayView2);
            }
            slidingPlayView2.setData(advs2);
        }
    }

    /**
     * 精选团购
     */
    private void initGroupPur()
    {
        if (!TextUtils.isEmpty(html5))
        {
            SDViewUtil.show(view_up_on_fl_group_pur);
            if (fraHtml5 == null)
            {
                fraHtml5 = new AppWebViewFragment();
                fraHtml5.setmProgressMode(WebViewFragment.EnumProgressMode.NONE);
                fraHtml5.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.WRAP_CONTENT);
                homeFragment.getSDFragmentManager().replace(R.id.fl_group_pur, fraHtml5);
            }
            fraHtml5.setHtmlContent(html5);
        }else
        {
            SDViewUtil.hide(fl_group_pur);
            SDViewUtil.hide(view_up_on_fl_group_pur);
        }
    }

    /**
     * 逛商城
     */
    private void initShopMall()
    {
        if (!TextUtils.isEmpty(html4))
        {
            SDViewUtil.show(view_up_on_fl_shop_mall);
            if (fraHtml6 == null)
            {
                fraHtml6 = new AppWebViewFragment();
                fraHtml6.setmProgressMode(WebViewFragment.EnumProgressMode.NONE);
                fraHtml6.setmWebviewHeightMode(WebViewFragment.EnumWebviewHeightMode.WRAP_CONTENT);
                homeFragment.getSDFragmentManager().replace(R.id.fl_shop_mall, fraHtml6);
            }
            fraHtml6.setHtmlContent(html4);
        }else
        {
            SDViewUtil.hide(fl_shop_mall);
            SDViewUtil.hide(view_up_on_fl_shop_mall);
        }
    }

    /**
     * 好店推荐
     */
    private void initGoodStoreRecommendView()
    {
        if (supplier_list != null &&supplier_list.size() > 0)
        {
            SDViewUtil.hide(view_up_on_ll_store);
            if (storeView == null)
            {
                storeView = new O2oGoodStoreRecommendView(getActivity());
                SDViewUtil.replaceView(ll_store, storeView);
            }
            storeView.setData(supplier_list);
        }else
        {
            SDViewUtil.hide(view_up_on_ll_store);
            SDViewUtil.hide(ll_store);
        }

    }
}
