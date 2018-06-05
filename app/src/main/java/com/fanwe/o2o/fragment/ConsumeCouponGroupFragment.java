package com.fanwe.o2o.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.ConsumeCouponActivity;
import com.fanwe.o2o.adapter.ConsumeCouponGroupAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.ConsumeCouponGroupModelMiddle;
import com.fanwe.o2o.model.ConsumeCouponGroupModelOuter;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.jshandler.AppJsHandler.jump2DealWap;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.DISABLED;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END;

/**
 * 消费券列表下的团购Tab页面 Created by Administrator on 2017/3/7.
 */

public class ConsumeCouponGroupFragment extends BaseFragment {
  @ViewInject(R.id.lv_content)
  private SDProgressPullToRefreshListView lv_content;
  @ViewInject(R.id.ll_no_content)
  private LinearLayout ll_no_content;
  @ViewInject(R.id.tv_empty_content)
  private TextView tv_empty_content;
  @ViewInject(R.id.iv_back_to_top)
  private ImageView iv_back_to_top;
  private int pay_status;
  private ConsumeCouponGroupAdapter adapter;
  private List<ConsumeCouponGroupModelMiddle> listModel = new ArrayList<>();

  private PageModel page = new PageModel();
  private String order_id;

  @Override
  protected int onCreateContentView() {
    return R.layout.common_list_container;
  }

  @Override
  protected void init() {
    super.init();
    getIntentData();
    setAdapter();
    initPullToRefresh();
  }

  @Override public void onResume() {
    super.onResume();
    requestData(false);
  }

  private void getIntentData() {
    order_id = getActivity().getIntent().getStringExtra(ConsumeCouponActivity.EXTRA_ID);
  }

  private void setAdapter() {
    adapter = new ConsumeCouponGroupAdapter(listModel, getActivity());
    lv_content.setAdapter(adapter);
    adapter.setItemClickListener(new SDAdapter.ItemClickListener<ConsumeCouponGroupModelMiddle>() {
      @Override public void onClick(int position, ConsumeCouponGroupModelMiddle item, View view) {
        if (item != null) {
          final String deal_id = item.getDeal_id();
          jump2DealWap(getActivity(),"deal", deal_id);
        }
      }
    });
  }

  private void initPullToRefresh() {
    lv_content.setDefalutScrollListener(iv_back_to_top);
    lv_content.setMode(PULL_FROM_END);
    lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

      @Override
      public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page.resetPage();
        requestData(false);
      }

      @Override
      public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (!page.increment()) {
          lv_content.addFooter(getActivity(),page.getPage_size());
          lv_content.onRefreshComplete();
        } else {
          requestData(true);
        }
      }
    });
  }

  public void requestData(final boolean isLoadMore) {
    showProgressDialog("");
    CommonInterface.requestConsumeCouponList(page.getPage(), "0", order_id,
        new AppRequestCallback<ConsumeCouponGroupModelOuter>() {
          @Override
          protected void onSuccess(SDResponse sdResponse) {
            if (actModel.isOk()) {
              page.update(actModel.getPage());
              if ( actModel.getTuan_item() != null &&  actModel.getTuan_item().size() > 0) {
                SDViewUtil.hide(ll_no_content);
              } else {
                tv_empty_content.setText("暂无团购消费券");
                SDViewUtil.show(ll_no_content);
              }
              SDViewUtil.updateAdapterByList(listModel, actModel.getTuan_item(), adapter,
                  isLoadMore);
              adapter.setSelectManagerList();
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

