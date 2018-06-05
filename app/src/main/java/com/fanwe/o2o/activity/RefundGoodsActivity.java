package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.RefundGoodsAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.model.RefundGoodsActModel;
import com.fanwe.o2o.model.RefundGoodsItemModelOuter.RefundGoodsItemModelInner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

import static com.fanwe.o2o.adapter.RefundGoodsAdapter.CHECKED;
import static com.fanwe.o2o.adapter.RefundGoodsAdapter.UN_CHECKED;

/**
 * 退款商品列表 Created by Administrator on 2017/3/9.
 */

public class RefundGoodsActivity extends BaseTitleActivity implements
    RefundGoodsAdapter.ListItemClickListener, View.OnClickListener {
  public static final String DATA_ID = "DATA_ID";

  @ViewInject(R.id.gv_items)
  private SDGridLinearLayout gv_items;
  @ViewInject(R.id.ll_no_content)
  private LinearLayout ll_no_content;
  @ViewInject(R.id.iv_back_to_top)
  private ImageView iv_back_to_top;
  @ViewInject(R.id.cb_check)
  private CheckBox cb_check;
  @ViewInject(R.id.btn_done)
  private Button btn_done;

  private List<RefundGoodsItemModelInner> listModel;
  private RefundGoodsAdapter adapter;
  private SDSelectViewManager multiSelectManager;

  private boolean selectAll = false;
  private String dataId;
  private ImageView mHeaderView;
  private View view;
  private PageModel page = new PageModel();

  private MoreTitleDialog titleDialog;

  @Override
  protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    setContentView(R.layout.act_refund_goods);
    initData();
    setAdapter();
    requestData(false);
    //initPullToRefresh();
    //setScrollChange();
  }

  private void initData() {
    dataId = getIntent().getStringExtra(DATA_ID);
    title.setMiddleTextTop("选择退款商品");
    title.initRightItem(1);
    title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
    cb_check.setTag(UN_CHECKED);
    btn_done.setOnClickListener(this);
    iv_back_to_top.setOnClickListener(this);
    cb_check.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cb_check:
        setCheckState((CheckBox) v);
        break;
      case R.id.iv_back_to_top:
        //lv_content.getRefreshableView().smoothScrollToPosition(0);
        break;
      case R.id.btn_done:
        if (multiSelectManager.getSelectedItems().size() > 0) {
          Intent intent = new Intent(RefundGoodsActivity.this, RefundRequestActivity.class);
          List<String> ids = getIDs(multiSelectManager.getSelectedItems());
          intent.putExtra(RefundRequestActivity.EXTRA_DEAL_ID, ids.get(0));
          intent.putExtra(RefundRequestActivity.EXTRA_COUPON_ID, ids.get(1));
          startActivity(intent);
          //finish();
        } else {
          SDToast.showToast("请选择要退款的物品！");
        }
        break;
      default:
        break;
    }
  }

  @Override public void onClick(int position, RefundGoodsItemModelInner item,
      View view) {
    if (listModel == null || listModel.size() == 0) {
      return;
    }
  }

  private List<String> getIDs(List<CheckBox> items) {

    List<String> ids = new ArrayList<>();
    if (items == null) {
      return ids;
    }
    final Iterator iterator = items.iterator();
    final StringBuilder deal_ids = new StringBuilder();
    final StringBuilder coupon_ids = new StringBuilder();
    while (iterator.hasNext()) {
      final CheckBox cb = (CheckBox) iterator.next();
      final RefundGoodsItemModelInner item = (RefundGoodsItemModelInner) cb.getTag(R.id.cb_check);
      final String isShop = item.getIs_shop();
      final String is_pick = item.getIs_pick();

      if(!TextUtils.isEmpty(isShop)){   // id代表coupon-id的只有两种情况:is_shop=1&&is_pick=1  或者 is_shop=0 ,否则id代表deal_id
        if(!TextUtils.isEmpty(is_pick)&&is_pick.equals("1")&&isShop.equals("1")){
          coupon_ids.append(item.getId()).append(",");
        }else if(isShop.equals("0")){
          coupon_ids.append(item.getId()).append(",");
        }
        else {
          deal_ids.append(item.getId()).append(",");
        }
      }

    }
    final int deal_idsLength = deal_ids.length();
    final int coupon_idsLength = coupon_ids.length();
    ids.add(deal_ids.substring(0, deal_idsLength == 0 ? 0 : deal_idsLength - 1));
    ids.add(coupon_ids.substring(0, coupon_idsLength == 0 ? 0 : coupon_idsLength - 1));
    return ids;
  }

  @Override public void onSelectAll(boolean selectAll) {
    this.selectAll = selectAll;
    if (selectAll) {
      cb_check.setChecked(true);
    } else {
      cb_check.setChecked(false);
    }
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
    listModel = new ArrayList<>();
    multiSelectManager = new SDSelectViewManager();
    multiSelectManager.setMode(SDSelectManager.Mode.MULTI);
    view = new View(this);
    adapter = new RefundGoodsAdapter(listModel, this, multiSelectManager);
    adapter.setListItemClickListener(this);
    gv_items.setColNumber(1);
    gv_items.setAdapter(adapter);
    gv_items.postInvalidateDelayed(200);
  }

  //protected void initPullToRefresh() {
  //  lv_content.setMode(PullToRefreshBase.Mode.BOTH);
  //  lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
  //
  //    @Override
  //    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
  //      page.resetPage();
  //      requestData(false);
  //    }
  //
  //    @Override
  //    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
  //      if (!page.increment()) {
  //        SDToast.showToast("没有更多数据了");
  //        lv_content.onRefreshComplete();
  //      } else {
  //        requestData(true);
  //      }
  //    }
  //  });
  //}

  private void setCheckState(CheckBox cb) {
    final int state = (Integer) cb.getTag();
    int newState = 0;
    if (state == CHECKED) {
      selectAll = false;
      cb.setChecked(false);
      cb.setTag(UN_CHECKED);
      newState = UN_CHECKED;
      multiSelectManager.resetIndex();
    } else if (state == UN_CHECKED) {
      selectAll = true;
      cb.setChecked(true);
      cb.setTag(CHECKED);
      newState = CHECKED;
      multiSelectManager.selectAll();
    }
    final Iterator iterator = multiSelectManager.getItems().iterator();
    while (iterator.hasNext()) {
      final CheckBox checkBox = (CheckBox) iterator.next();
      checkBox.setTag(newState);
      checkBox.setChecked(newState == CHECKED ? true : false);
    }
  }

  protected void requestData(final boolean isLoadMore) {
    showProgressDialog("");
    CommonInterface.requestRefundGoodsList(dataId,
        new AppRequestCallback<RefundGoodsActModel>() {
          @Override
          protected void onSuccess(SDResponse sdResponse) {
            if (actModel.isOk()) {
              page.update(actModel.getPage());
              if (actModel.getItem() == null && actModel.getItem() == null) {
                return;
              }
              List<RefundGoodsItemModelInner> items = new ArrayList<RefundGoodsItemModelInner>();
              //只取第一个:getDeal_order_item().get(0)
              items = actModel.getItem().getDeal_order_item().get(0).getList();

              if (items != null && items.size() > 0) {
                SDViewUtil.hide(ll_no_content);
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
            //lv_content.onRefreshComplete();
          }
        });
  }

  public void onEventMainThread(ERefreshRequest event)
  {
    requestData(false);
  }

  /**
   * listView的滑动监听
   */
  //private void setScrollChange() {
  //  lv_content.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
  //    @Override
  //    public void onScrollStateChanged(AbsListView view, int scrollState) {
  //
  //    }
  //
  //    @Override
  //    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
  //        int totalItemCount) {
  //      if (getScrollY() >= 700) {
  //        SDViewUtil.show(iv_back_to_top);
  //      } else {
  //        SDViewUtil.hide(iv_back_to_top);
  //      }
  //    }
  //
  //    public int getScrollY() {
  //      view = lv_content.getRefreshableView().getChildAt(0);
  //      if (view == null) {
  //        return 0;
  //      }
  //      int firstVisiblePosition = lv_content.getRefreshableView().getFirstVisiblePosition();
  //      int top = view.getTop();
  //      return -top + firstVisiblePosition * view.getHeight();
  //    }
  //  });
  //}
}


