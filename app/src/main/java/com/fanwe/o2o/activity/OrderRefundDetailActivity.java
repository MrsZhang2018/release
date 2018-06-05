package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.view.View;
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
import com.fanwe.o2o.adapter.OrderRefundDetailAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.ItemRefundDetailModel;
import com.fanwe.o2o.model.OrderRefundDetailModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.jshandler.AppJsHandler.jump2DealWap;

/**
 * Created by Administrator on 2017/3/2.
 */

public class OrderRefundDetailActivity extends BaseTitleActivity {
  @ViewInject(R.id.tv_refund_reason)
  private TextView tv_refund_reason;
  @ViewInject(R.id.lv_content)
  private SDProgressPullToRefreshListView lv_content;
  @ViewInject(R.id.ll_no_content)
  private LinearLayout ll_no_content;
  @ViewInject(R.id.iv_back_to_top)
  private ImageView iv_back_to_top;

  private List<ItemRefundDetailModel> listModel;
  private OrderRefundDetailAdapter adapter;

  private PageModel page = new PageModel();

  private MoreTitleDialog titleDialog;
  private View view;
  private String mid;
  private String id;

  @Override
  protected void onResume() {
    super.onResume();
    requestData(false);
  }

  @Override
  protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    setContentView(R.layout.act_refund_detail);
    initData();
    setAdapter();
    initPullToRefresh();
  }

  private void initData() {
    mid=getIntent().getStringExtra("mid");
    id=getIntent().getStringExtra("id");
    title.setMiddleTextTop("退款详情");
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
    view = new View(this);
    adapter = new OrderRefundDetailAdapter(listModel, this);
    lv_content.setAdapter(adapter);
    lv_content.getRefreshableView().addHeaderView(view);

  }

  protected void initPullToRefresh() {
    lv_content.setDefalutScrollListener(iv_back_to_top);
    lv_content.setMode(PullToRefreshBase.Mode.BOTH);
    lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

      @Override
      public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page.resetPage();
        requestData(false);
      }

      @Override
      public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (!page.increment()) {
          lv_content.addFooter(OrderRefundDetailActivity.this,page.getPage_size());
          lv_content.onRefreshComplete();
        } else {
          requestData(true);
        }
      }
    });
  }

  protected void requestData(final boolean isLoadMore) {
    showProgressDialog("");
    CommonInterface.requestOrderRefundDetail(mid,id,
        new AppRequestCallback<OrderRefundDetailModel>() {
          @Override
          protected void onSuccess(SDResponse sdResponse) {
            if (actModel.isOk()) {
              page.update(actModel.getPage());
              List<ItemRefundDetailModel> items = actModel.getItem();
              if (items != null && items.size() > 0) {
                SDViewUtil.hide(ll_no_content);
                if(tv_refund_reason.getText()==null||tv_refund_reason.getText().equals("")){
                  tv_refund_reason.setText(items.get(0).getContent());
                }
              } else {
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

