package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.GroupPurchaseAdapter;
import com.fanwe.o2o.appview.GroupPurchaseBottomView;
import com.fanwe.o2o.appview.GroupPurchaseHeaderView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.LocationCityDialog;
import com.fanwe.o2o.event.EChangeCity;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppGroupPurIndexActModel;
import com.fanwe.o2o.model.AppGroupPurIndexDealListActModel;
import com.fanwe.o2o.model.GroupPurIndexAdvsModel;
import com.fanwe.o2o.model.GroupPurIndexDealListModel;
import com.fanwe.o2o.model.GroupPurIndexIndexsListModel;
import com.fanwe.o2o.model.GroupPurRecommendDealCateModel;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 团购首页
 * Created by Administrator on 2016/12/12.
 */

public class GroupPurchaseActivity extends BaseTitleActivity
{
    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;
    @ViewInject(R.id.lv_content)
    private PullToRefreshListView lv_content;
    private GroupPurchaseAdapter adapter;
    private List<GroupPurIndexDealListModel> listModel;

    private TextView tv_type;
    private TextView tv_city;
    private GroupPurchaseHeaderView headerView;
    private GroupPurchaseBottomView bottomView;

    private int page;
    private int has_next;

    private LocationCityDialog locationCityDialog;
    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_group_purchase);
        initTitle();
        requestGroupPurData();
        initData();
    }

    private void initTitle()
    {
        title.initRightItem(1);
        title.getItemRight(0).setImageLeft(R.drawable.ic_gray_serach);

        View view = getLayoutInflater().inflate(R.layout.include_title_group_purchase, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_city.setText(AppRuntimeWorker.getCity_name());

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(GroupPurchaseActivity.this, LocationCityActivity.class);
//                startActivity(intent);
                if( locationCityDialog == null)
                {
                     locationCityDialog = new LocationCityDialog(GroupPurchaseActivity.this);
                }
                locationCityDialog.showTop();
            }
        });
        title.setCustomViewMiddle(view);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        super.onCLickRight_SDTitleSimple(v, index);
        Intent intent = new Intent(GroupPurchaseActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void initData()
    {
        setAdapter();
        initPullToRefresh();
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

    private void addHeaderView(List<GroupPurIndexAdvsModel> advs,List<GroupPurIndexIndexsListModel> list, String html3,String html4,String html5,String html6)
    {
        if (headerView == null)
        {
            headerView = new GroupPurchaseHeaderView(this);
            lv_content.getRefreshableView().addHeaderView(headerView);
        }
        headerView.setData(advs,list,html3,html4,html5,html6);
    }

    private void addBottomView(List<GroupPurRecommendDealCateModel> recommend_deal_cate)
    {
        if (bottomView == null)
        {
            bottomView = new GroupPurchaseBottomView(this);
            lv_content.getRefreshableView().addFooterView(bottomView);
        }
        bottomView.setData(recommend_deal_cate);
        bottomView.isShow(false);
    }

    private void setAdapter()
    {
        listModel = new ArrayList<>();
        adapter = new GroupPurchaseAdapter(listModel,this);
        lv_content.setAdapter(adapter);

        adapter.setItemClickListener(new SDAdapter.ItemClickListener<GroupPurIndexDealListModel>()
        {
            @Override
            public void onClick(int position, GroupPurIndexDealListModel item, View view)
            {
                clickWebView(item.getApp_url());
            }
        });
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(this, AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }

    protected void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestGroupPurData();
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
                if (bottomView != null)
                    bottomView.isShow(false);
                page++;
            } else
            {
                if (bottomView != null)
                    bottomView.isShow(true);
                lv_content.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            page = 1;
        }
        showProgressDialog("");
        CommonInterface.requestGroupPurLoadMore(page, new AppRequestCallback<AppGroupPurIndexDealListActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    page = actModel.getPage();
                    has_next = actModel.getHas_next();
                    List<GroupPurIndexDealListModel> deal_list = actModel.getDeal_list();
                    if (deal_list != null)
                        SDViewUtil.updateAdapterByList(listModel, deal_list, adapter, isLoadMore);
                }
            }
            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                lv_content.onRefreshComplete();
                dismissProgressDialog();
            }
        });
    }

    private void requestGroupPurData()
    {
        showProgressDialog("");
        CommonInterface.requestGroupPurchaseIndex(new AppRequestCallback<AppGroupPurIndexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    page = 1;
                    has_next = 1;
                    List<GroupPurIndexAdvsModel> advs = actModel.getAdvs();
                    List<GroupPurIndexIndexsListModel> list = actModel.getIndexs().getList();
                    List<GroupPurIndexDealListModel> deal_list = actModel.getDeal_list();
                    List<GroupPurRecommendDealCateModel> recommend_deal_cate = actModel.getRecommend_deal_cate();
                    String html3 = actModel.getZt_html3();
                    String html4 = actModel.getZt_html4();
                    String html5 = actModel.getZt_html5();
                    String html6 = actModel.getZt_html6();
                    addHeaderView(advs,list,html3,html4,html5,html6);
                    addBottomView(recommend_deal_cate);
                    SDViewUtil.updateAdapterByList(listModel, deal_list, adapter, false);
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

    private void changeCity() {
        tv_city.setText(AppRuntimeWorker.getCity_name());
        requestGroupPurData();
    }

    public void onEventMainThread(EChangeCity event) {
        changeCity();
    }

}
