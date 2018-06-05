package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.MallClassificationActivity;
import com.fanwe.o2o.activity.SearchActivity;
import com.fanwe.o2o.fragment.AppWebViewFragment;
import com.fanwe.o2o.model.ShopIndexAdvsModel;
import com.fanwe.o2o.model.ShopIndexIndexsListModel;

import java.util.List;


/**
 * Created by Administrator on 2016/12/9.
 */

public class MallHeaderView extends SDAppView
{
    private LinearLayout ll_slide_play;
    private LinearLayout ll_page_view;
    private LinearLayout ll_clas;
    public LinearLayout ll_like;
    private CommonTitleSearchView view_search;
    private FrameLayout fl_super_ben;
    private FrameLayout fl_good_qua;
    private FrameLayout fl_group_pur;
    private FrameLayout fl_shop_mall;
    private View view_up_on_fl_super_ben;
    private View view_up_on_fl_good_qua;
    private View view_up_on_fl_group_pur;
    private View view_up_on_fl_shop_mall;

    private ShopSlidingPlayView slidingPlayView;
    private ShopIndexPageView indexPageView;

    private List<ShopIndexAdvsModel> advs;
    private List<ShopIndexIndexsListModel> list;
    private String html3,html4,html5,html6;

    private AppWebViewFragment fraHtml3;
    private AppWebViewFragment fraHtml4;
    private AppWebViewFragment fraHtml5;
    private AppWebViewFragment fraHtml6;

    public MallHeaderView(Context context)
    {
        super(context);
        init();
    }

    public MallHeaderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public MallHeaderView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<ShopIndexAdvsModel> advs, List<ShopIndexIndexsListModel> list,String html3,String html4,String html5,String html6)
    {
        this.advs = advs;
        this.list = list;
        this.html3 = html3;
        this.html4 = html4;
        this.html5 = html5;
        this.html6 = html6;

        initSlidingPlayView();
        initIndexPageView();
        initSuperBen();
        initGoodQua();
        initGroupPur();
        initShopMall();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_mall_header);
        initView();

        ll_clas.setOnClickListener(this);
        view_search.setOnClickListener(this);
    }

    private void initView()
    {
        ll_slide_play = find(R.id.ll_slide_play);
        ll_page_view = find(R.id.ll_page_view);
        ll_clas = find(R.id.ll_clas);
        view_search = find(R.id.view_search);
        ll_like = find(R.id.ll_like);
        fl_good_qua = find(R.id.fl_good_qua);
        fl_group_pur = find(R.id.fl_group_pur);
        fl_shop_mall = find(R.id.fl_shop_mall);
        fl_super_ben = find(R.id.fl_super_ben);

        view_up_on_fl_super_ben = find(R.id.view_up_on_fl_super_ben);
        view_up_on_fl_good_qua = find(R.id.view_up_on_fl_good_qua);
        view_up_on_fl_group_pur = find(R.id.view_up_on_fl_group_pur);
        view_up_on_fl_shop_mall = find(R.id.view_up_on_fl_shop_mall);
    }

    /**
     * 首页广告
     */
    private void initSlidingPlayView()
    {
        if (slidingPlayView == null)
        {
            slidingPlayView = new ShopSlidingPlayView(getActivity());
            SDViewUtil.replaceView(ll_slide_play,slidingPlayView);
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
            indexPageView = new ShopIndexPageView(getActivity());
            SDViewUtil.replaceView(ll_page_view,indexPageView);
        }
        indexPageView.setmIndexModel(list);
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
                ((SDBaseActivity)getActivity()).getSDFragmentManager().replace(R.id.fl_super_ben, fraHtml3);
            }
            fraHtml3.setHtmlContent(html3);
        }else
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
                ((SDBaseActivity)getActivity()).getSDFragmentManager().replace(R.id.fl_good_qua, fraHtml4);
            }
            fraHtml4.setHtmlContent(html6);
        }else
        {
            fl_good_qua.setVisibility(GONE);
            view_up_on_fl_good_qua.setVisibility(GONE);
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
                ((SDBaseActivity)getActivity()).getSDFragmentManager().replace(R.id.fl_group_pur, fraHtml5);
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
                ((SDBaseActivity)getActivity()).getSDFragmentManager().replace(R.id.fl_shop_mall, fraHtml6);
            }
            fraHtml6.setHtmlContent(html4);
        }else
        {
            SDViewUtil.hide(fl_shop_mall);
            SDViewUtil.hide(view_up_on_fl_shop_mall);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ll_clas:
                clickClassification();
                break;
            case R.id.view_search:
                clickSearch();
                break;
            default:
                break;
        }
    }

    private void clickClassification()
    {
        Intent intent = new Intent(getActivity(), MallClassificationActivity.class);
        getActivity().startActivity(intent);
    }

    private void clickSearch()
    {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivity(intent);
    }

}
