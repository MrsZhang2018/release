package com.fanwe.o2o.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.fragment.AppWebViewFragment;
import com.fanwe.o2o.model.GroupPurIndexAdvsModel;
import com.fanwe.o2o.model.GroupPurIndexIndexsListModel;

import java.util.List;

/**
 * 团购首页头部
 * Created by Administrator on 2016/12/12.
 */

public class GroupPurchaseHeaderView extends SDAppView
{
    private LinearLayout ll_slide_play;
    private LinearLayout ll_page_view;

    private FrameLayout fl_super_ben;
    private FrameLayout fl_good_qua;
    private FrameLayout fl_group_pur;
    private FrameLayout fl_shop_mall;
    private View view_up_on_fl_super_ben;
    private View view_up_on_fl_good_qua;
    private View view_up_on_fl_group_pur;
    private View view_up_on_fl_shop_mall;

    private GroupPurSlidingPlayView slidingPlayView;
    private GroupPurIndexPageView indexPageView;

    private List<GroupPurIndexAdvsModel> advs;
    private List<GroupPurIndexIndexsListModel> list;
    private String html3,html4,html5,html6;

    private AppWebViewFragment fraHtml3;
    private AppWebViewFragment fraHtml4;
    private AppWebViewFragment fraHtml5;
    private AppWebViewFragment fraHtml6;

    public GroupPurchaseHeaderView(Context context)
    {
        super(context);
        init();
    }

    public GroupPurchaseHeaderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GroupPurchaseHeaderView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<GroupPurIndexAdvsModel> advs, List<GroupPurIndexIndexsListModel> list,String html3,String html4,String html5,String html6)
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
        setContentView(R.layout.view_group_purchase_header);
        initView();
    }

    private void initView()
    {
        ll_slide_play = find(R.id.ll_slide_play);
        ll_page_view = find(R.id.ll_page_view);

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
            slidingPlayView = new GroupPurSlidingPlayView(getActivity());
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
            indexPageView = new GroupPurIndexPageView(getActivity());
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
}
