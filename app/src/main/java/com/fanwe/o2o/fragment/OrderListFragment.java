package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.MainActivity;
import com.fanwe.o2o.activity.OrderDetailsActivity;
import com.fanwe.o2o.adapter.OrderListAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.EIntentAppMain;
import com.fanwe.o2o.event.EOrderListRefresh;
import com.fanwe.o2o.event.ERefreshOrderList;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.model.UcOrderActModel;
import com.fanwe.o2o.model.UcOrderItemModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import com.sunday.eventbus.SDEventManager;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表
 * Created by luod on 2017/3/2.
 */

public class OrderListFragment extends BaseFragment
{
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;
    @ViewInject(R.id.tv_empty_content)
    private TextView tv_empty_content;
    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;
    private int pay_status;
    private OrderListAdapter adapter;
    private List<UcOrderItemModel> listModel = new ArrayList<>();

    private PageModel page = new PageModel();

    public void setPayStatus(int pay_status)
    {
        this.pay_status = pay_status;
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_order_list;
    }

    @Override
    protected void init()
    {
        super.init();
//        ll_no_content.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                SDEventManager.post(new EIntentAppMain());
//                Intent intent=new Intent(getActivity(), MainActivity.class);
//                //intent.putExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.APP_MAIN);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
//            }
//        });
        setAdapter();
        initPullToRefresh();

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        requestData(false);
//    }

    private void setAdapter()
    {
        adapter = new OrderListAdapter(listModel,getActivity());
        lv_content.setAdapter(adapter);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<UcOrderItemModel>()
        {
            @Override
            public void onClick(int position, UcOrderItemModel item, View view)
            {
                Intent intent = new Intent(getActivity(),OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.EXTRA_ID,item.getId());
                startActivity(intent);
            }
        });
    }

    private void initPullToRefresh()
    {
        lv_content.setDefalutScrollListener(iv_back_to_top);
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                page.resetPage();
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                if (!page.increment())
                {
                    SDToast.showToast("没有更多数据了");
                    lv_content.onRefreshComplete();
                } else
                {
                    requestData(true);
                }
            }
        });
    }

    public void requestData(final boolean isLoadMore)
    {
        showProgressDialog("");
        CommonInterface.requestUcOrderWapIndex(page.getPage(), pay_status, new AppRequestCallback<UcOrderActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    page.update(actModel.getPage());
                    if ( actModel.getItem() != null &&  actModel.getItem().size() > 0) {
                        ll_no_content.setEnabled(false);
                        SDViewUtil.hide(ll_no_content);
                    } else {
                        tv_empty_content.setText("列表这么空，不如去买买买~");
                        ll_no_content.setEnabled(true);
                        SDViewUtil.show(ll_no_content);
                    }
                    SDViewUtil.updateAdapterByList(listModel, actModel.getItem(), adapter, isLoadMore);
                    if (!isLoadMore)
                    {
                        lv_content.getRefreshableView().smoothScrollToPosition(0);
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
     * 刷新listview
     */
    public void onEventMainThread(ERefreshOrderList event) {
        page.resetPage();
        requestData(false);
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        page.resetPage();
        requestData(false);
    }

    public void onEventMainThread(EOrderListRefresh event)
    {
        page.resetPage();
        requestData(false);
    }

}
