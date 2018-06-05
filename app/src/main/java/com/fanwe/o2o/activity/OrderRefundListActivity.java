package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.fanwe.o2o.adapter.OrderRefundListAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.ItemOrderRefundModel;
import com.fanwe.o2o.model.OrderRefundListModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

/**
 * 订单--退款订单 Created by Administrator on 2017/3/1.
 */

public class OrderRefundListActivity extends BaseTitleActivity {
  @ViewInject(R.id.lv_content)
  private SDProgressPullToRefreshListView lv_content;
  @ViewInject(R.id.iv_back_to_top)
  private ImageView iv_back_to_top;
  @ViewInject(R.id.ll_no_content)
  private LinearLayout ll_no_content;
  @ViewInject(R.id.tv_empty_content)
  private TextView tv_empty_content;

  private List<ItemOrderRefundModel> listModel;
  private OrderRefundListAdapter adapter;

  private View view;
  private PageModel page = new PageModel();

  private MoreTitleDialog titleDialog;

  @Override
  protected void onResume() {
    super.onResume();
    requestData(false);
  }

  @Override
  protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    setContentView(R.layout.act_order_refund_list);
    initData();
    setAdapter();
    initPullToRefresh();
  }

  private void initData() {
    title.setMiddleTextTop("退款订单");
    title.initRightItem(1);
    title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
    listModel = new ArrayList<>();

  }

  @Override
  public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
    super.onCLickRight_SDTitleSimple(v, index);
    if (titleDialog == null) {
      titleDialog = new MoreTitleDialog(this);
    }
    titleDialog.requestData();
    titleDialog.showTop();
  }

  private void setAdapter() {
    view=new View(this);
    adapter = new OrderRefundListAdapter(listModel, this);
    adapter.setItemClickListener(new SDAdapter.ItemClickListener<ItemOrderRefundModel>() {
      @Override public void onClick(int position, ItemOrderRefundModel item, View view) {
        if (listModel == null || listModel.size() == 0) {
          return;
        }
        Intent intent = new Intent(OrderRefundListActivity.this, OrderRefundDetailActivity.class);
        intent.putExtra("mid", listModel.get(position).getMid());
        intent.putExtra("id", listModel.get(position).getId());
        startActivity(intent);
      }
    });
    lv_content.setAdapter(adapter);


  }

  protected void initPullToRefresh() {
    lv_content.setDefalutScrollListener(iv_back_to_top);
    lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

      @Override
      public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page.resetPage();
        requestData(false);
      }

      @Override
      public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (!page.increment()) {
          lv_content.addFooter(OrderRefundListActivity.this,page.getPage_size());
          lv_content.onRefreshComplete();
        } else {
          requestData(true);
        }
      }
    });
  }

  protected void requestData(final boolean isLoadMore) {
    showProgressDialog("");
    CommonInterface.requestOrderRefundList(page.getPage(),
        new AppRequestCallback<OrderRefundListModel>() {
          @Override
          protected void onSuccess(SDResponse sdResponse) {
            if (actModel.isOk()) {
              page.update(actModel.getPage());
              List<ItemOrderRefundModel> items = actModel.getItem();
              if (items != null && items.size() > 0) {
                SDViewUtil.hide(ll_no_content);
              } else {
                tv_empty_content.setText("暂无退款记录");
                SDViewUtil.show(ll_no_content);
              }
              SDViewUtil.updateAdapterByList(listModel, items, adapter, isLoadMore);
            }
          }

          @Override
          protected void onError(SDResponse resp) {
            super.onError(resp);
          }

          @Override
          protected void onFinish(SDResponse resp) {
            super.onFinish(resp);
            dismissProgressDialog();
            lv_content.onRefreshComplete();
          }
        });
  }

  public void onEventMainThread(ERefreshRequest event)
  {
    requestData(false);
    lv_content.getRefreshableView().smoothScrollToPosition(0);
  }

}
