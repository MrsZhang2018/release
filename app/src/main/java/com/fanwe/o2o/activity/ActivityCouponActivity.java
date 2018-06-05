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
import com.fanwe.o2o.adapter.ActivityCouponAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.ActivityCouponActModel;
import com.fanwe.o2o.model.ActivityCouponActModel.ActivityCouponModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.jshandler.AppJsHandler.jump2DealWap;

/**
 * 活动券列表
 * Created by Administrator on 2017/3/5.
 */

public class ActivityCouponActivity extends BaseTitleActivity {
  @ViewInject(R.id.lv_content)
  private SDProgressPullToRefreshListView lv_content;
  @ViewInject(R.id.ll_no_content)
  private LinearLayout ll_no_content;
  @ViewInject(R.id.tv_empty_content)
  private TextView tv_empty_content;
  @ViewInject(R.id.iv_back_to_top)
  private ImageView iv_back_to_top;

  private List<ActivityCouponModel> listModel;
  private ActivityCouponAdapter adapter;

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
    setContentView(R.layout.act_acitivty_coupon);
    initData();
    setAdapter();
    initPullToRefresh();
  }

  private void initData() {
    title.setMiddleTextTop("活动券");
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
    adapter = new ActivityCouponAdapter(listModel, this);
    adapter.setItemClickListener(new SDAdapter.ItemClickListener<ActivityCouponModel>() {
      @Override
      public void onClick(int position, ActivityCouponModel item, View view) {
        if (item != null) {
          final String event_id = item.getEvent_id();
          jump2DealWap(ActivityCouponActivity.this,"event", event_id);
        }
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
          lv_content.addFooter(ActivityCouponActivity.this,page.getPage_size());
          lv_content.onRefreshComplete();
        } else {
          requestData(true);
        }
      }
    });
  }

  protected void requestData(final boolean isLoadMore) {
    showProgressDialog("");
    CommonInterface.requestActivityCouponList(page.getPage(),
        new AppRequestCallback<ActivityCouponActModel>() {
          @Override
          protected void onSuccess(SDResponse sdResponse) {
            if (actModel.isOk()) {
              page.update(actModel.getPage());
              List<ActivityCouponModel> items = actModel.getItem();
              if ( actModel.getItem() != null &&  actModel.getItem().size() > 0) {
                SDViewUtil.hide(ll_no_content);
              } else {
                tv_empty_content.setText("暂无活动券");
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
