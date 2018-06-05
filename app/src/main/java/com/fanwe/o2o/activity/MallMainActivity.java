package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.ShopGoodsGuessLikeAdapter;
import com.fanwe.o2o.appview.MallHeaderView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppShopIndexActModel;
import com.fanwe.o2o.model.AppShopIndexDealListActModel;
import com.fanwe.o2o.model.ShopIndexAdvsModel;
import com.fanwe.o2o.model.ShopIndexIndexsListModel;
import com.fanwe.o2o.model.ShopIndexSupplierDealListModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城首页
 * Created by luodong on 2016/12/12
 */
public class MallMainActivity extends BaseTitleActivity
{

    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;
    @ViewInject(R.id.lv_content)
    private PullToRefreshListView lv_content;
    @ViewInject(R.id.mhv_header)
    private MallHeaderView mallHtmlView;
    @ViewInject(R.id.scroll_container)
    private ScrollView scroll_container;

    private ShopGoodsGuessLikeAdapter adapter;
    private List<List<ShopIndexSupplierDealListModel>> listModel;
    private MallHeaderView headerView;

    private int page;
    private int has_next;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_mall_main);
        initData();
    }

    private void initTitle()
    {
        title.setMiddleTextTop("商城");
    }

    private void initData()
    {
        initTitle();
        setAdapter();
        initPullToRefresh();
        requestShopIndexData();
        setScrollChange();

        iv_back_to_top.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lv_content.getRefreshableView().smoothScrollToPosition(0);
            }
        });
    }

    private void addHeaderView(List<ShopIndexAdvsModel> advs, List<ShopIndexIndexsListModel> list, String html3,String html4,String html5,String html6)
    {
        if (headerView == null)
        {
            headerView = new MallHeaderView(this);
            lv_content.getRefreshableView().addHeaderView(headerView);
        }
        headerView.setData(advs,list,html3,html4,html5,html6);
    }

    private void setAdapter()
    {
        listModel = new ArrayList<>();
        adapter = new ShopGoodsGuessLikeAdapter(listModel,this);
        lv_content.setAdapter(adapter);
    }

    protected void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestShopIndexData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData(true);
            }
        });
    }

    protected void requestData(final boolean isLoadMore)
    {
        if (isLoadMore)
        {
            if (has_next == 1)
            {
                page++;
            } else
            {
                lv_content.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            page = 1;
        }

        CommonInterface.requestShopLoadMore(page, new AppRequestCallback<AppShopIndexDealListActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    page = actModel.getPage();
                    has_next = actModel.getHas_next();
                    List<ShopIndexSupplierDealListModel> deal_list = actModel.getDeal_list();
                    if (deal_list != null){
                        SDViewUtil.updateAdapterByList(listModel, SDCollectionUtil.splitList(deal_list, 2), adapter, isLoadMore);
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                lv_content.onRefreshComplete();
            }
        });
    }

    private void requestShopIndexData()
    {
        showProgressDialog("");
        CommonInterface.requestShopIndex(new AppRequestCallback<AppShopIndexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    page = 1;
                    has_next = 1;
                    List<ShopIndexAdvsModel> advs = actModel.getAdvs();
                    List<ShopIndexIndexsListModel> list = actModel.getIndexs().getList();
                    List<ShopIndexSupplierDealListModel> supplier_deal_list = actModel.getSupplier_deal_list();
                    String html3 = actModel.getZt_html3();
                    String html4 = actModel.getZt_html4();
                    String html5 = actModel.getZt_html5();
                    String html6 = actModel.getZt_html6();
                    if (supplier_deal_list == null || supplier_deal_list.size() == 0)
                    {
                        SDViewUtil.show(scroll_container);
                        mallHtmlView.setData(advs,list,html3,html4,html5,html6);
                        mallHtmlView.ll_like.setVisibility(View.GONE);
                        SDViewUtil.hide(lv_content);
                    }else
                    {
                        SDViewUtil.hide(scroll_container);
                        SDViewUtil.show(lv_content);
                        addHeaderView(advs,list,html3,html4,html5,html6);
                        SDViewUtil.updateAdapterByList(listModel, SDCollectionUtil.splitList(supplier_deal_list, 2), adapter, false);
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
                lv_content.onRefreshComplete();
            }
        });
    }

    /**
     * listView的滑动监听
     */
    private void setScrollChange()
    {
        lv_content.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(getScrollY() >= 200)
                {
                    SDViewUtil.show(iv_back_to_top);
                }else
                {
                    SDViewUtil.hide(iv_back_to_top);
                }
            }

            public int getScrollY()
            {
                int top = 0;
                if (headerView != null)
                    top = headerView.getTop();
                return SDViewUtil.px2dp(Math.abs(top));
            }

        });
    }
}
