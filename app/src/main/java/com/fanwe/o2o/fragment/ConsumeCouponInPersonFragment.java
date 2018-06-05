package com.fanwe.o2o.fragment;

import android.content.Intent;
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
import com.fanwe.o2o.activity.ConsumeCouponActivity;
import com.fanwe.o2o.activity.OrderDetailsActivity;
import com.fanwe.o2o.adapter.ConsumeCouponInPersonAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.ConsumeCouponInPersonMiddle;
import com.fanwe.o2o.model.ConsumeCouponInPersonOuter;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.activity.OrderDetailsActivity.EXTRA_ID;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.DISABLED;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END;

/**
 * 消费券列表下的自提Tab页面
 * Created by Administrator on 2017/3/8.
 */

public class ConsumeCouponInPersonFragment extends BaseFragment {
  @ViewInject(R.id.lv_content)
  private SDProgressPullToRefreshListView lv_content;
  @ViewInject(R.id.ll_no_content)
  private LinearLayout ll_no_content;
  @ViewInject(R.id.tv_empty_content)
  private TextView tv_empty_content;
  @ViewInject(R.id.iv_back_to_top)
  private ImageView iv_back_to_top;

  private int pay_status;
  private ConsumeCouponInPersonAdapter adapter;
  private List<ConsumeCouponInPersonMiddle> listModel = new ArrayList<>();

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
    adapter = new ConsumeCouponInPersonAdapter(listModel, getActivity());
    lv_content.setAdapter(adapter);
    adapter.setItemClickListener(new SDAdapter.ItemClickListener<ConsumeCouponInPersonMiddle>() {
      @Override public void onClick(int position, ConsumeCouponInPersonMiddle item, View view) {
        Intent intent=new Intent(getActivity(), OrderDetailsActivity.class);
        final String order_id=item.getOrder_id();
        intent.putExtra(EXTRA_ID,order_id);
        startActivity(intent);
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
    //TODO order_id 订单id为"",应该从上个页面传下来
    CommonInterface.requestConsumeCouponList(page.getPage(), "1", order_id,
        new AppRequestCallback<ConsumeCouponInPersonOuter>() {
          @Override
          protected void onSuccess(SDResponse sdResponse) {
            if (actModel.isOk()) {
              page.update(actModel.getPage());
              if ( actModel.getPick_item() != null &&  actModel.getPick_item().size() > 0) {
                SDViewUtil.hide(ll_no_content);
              } else {
                tv_empty_content.setText("暂无自提消费券");
                SDViewUtil.show(ll_no_content);
              }
              SDViewUtil.updateAdapterByList(listModel, actModel.getPick_item(), adapter,
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

