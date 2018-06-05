package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.RefundGoodsItemModelOuter;
import com.fanwe.o2o.model.RefundGoodsItemModelOuter.RefundGoodsItemModelInner;
import com.fanwe.o2o.utils.GlideUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RefundGoodsAdapter extends SDSimpleAdapter<RefundGoodsItemModelInner>
    implements SDSelectManager.SDSelectManagerListener<CheckBox> {
  //public static final int CHECKED_STATE=0;
  public static final int UN_CHECKED = 1;
  public static final int CHECKED = 2;
  protected ListItemClickListener listItemClickListener;
  private SDSelectViewManager<CheckBox> selectViewManager;

  public RefundGoodsAdapter(List<RefundGoodsItemModelInner> listModel, Activity activity,
      SDSelectViewManager<CheckBox> svm) {
    super(listModel, activity);
    selectViewManager = svm;
    selectViewManager.setListener(this);
  }

  public void setListItemClickListener(final ListItemClickListener listItemClickListener) {
    this.listItemClickListener = listItemClickListener;
    setItemClickListener(new ItemClickListener<RefundGoodsItemModelInner>() {
      @Override
      public void onClick(int position, RefundGoodsItemModelInner item, View view) {
        if (listItemClickListener != null) {
          listItemClickListener.onClick(position, item, view);
        }
      }
    });
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_refund_goods;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final RefundGoodsItemModelInner model) {
    ImageView iv_goods_image = get(R.id.iv_goods_image, convertView);
    TextView tv_instruct = get(R.id.tv_instruct, convertView);
    TextView tv_goods_state = get(R.id.tv_goods_state, convertView);
    TextView tv_code=get(R.id.tv_code,convertView);
    TextView tv_spec=get(R.id.tv_spec,convertView);
    final CheckBox cb_check = get(R.id.cb_check, convertView);
    final String attr=model.getAttr_str();
    cb_check.setTag(R.id.cb_check, model);
    cb_check.setTag(UN_CHECKED);
    if (selectViewManager.getItems().isEmpty()) {
      selectViewManager.getItems().add(cb_check);
    } else if (!selectViewManager.getItems().contains(cb_check)) {
      selectViewManager.appendItem(cb_check, true);
    }

    if(TextUtils.isEmpty(attr)){
      SDViewUtil.hide(tv_spec);
    }else {
      SDViewUtil.show(tv_spec);
      SDViewBinder.setTextView(tv_spec,"规格: "+attr);
    }

    final String deal_orders = model.getDeal_orders();
    final String name = model.getName();
    final String deal_icon = model.getDeal_icon();
    final String pwd=model.getPassword();

    GlideUtil.loadHeadImage(deal_icon).into(iv_goods_image);
    if(!TextUtils.isEmpty(pwd)){
      SDViewUtil.show(tv_code);
      SDViewBinder.setTextView(tv_code,"券码 ："+pwd);
    }else {
      SDViewUtil.hide(tv_code);
    }
    SDViewBinder.setTextView(tv_instruct, name);
    SDViewBinder.setTextView(tv_goods_state, deal_orders);

    final View.OnClickListener listener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (v == convertView) {
          listItemClickListener.onClick(position, model, convertView);
        } else if (v == cb_check) {
          setCheckState(cb_check, position, selectViewManager);
        }
      }
    };

    convertView.setOnClickListener(listener);
    cb_check.setOnClickListener(listener);
  }

  private void setCheckState(CheckBox cb, int position, SDSelectViewManager selectViewManager) {
    final int state = (Integer) cb.getTag();
    if (state == CHECKED) {
      cb.setChecked(false);
      cb.setTag(UN_CHECKED);
      selectViewManager.setSelected(position, false);
    } else if (state == UN_CHECKED) {
      cb.setChecked(true);
      cb.setTag(CHECKED);
      selectViewManager.setSelected(position, true);
    }
  }

  @Override public void onNormal(int index, CheckBox item) {
    if (selectViewManager.getItems().size() != selectViewManager.getSelectedItems().size()) {
      if (listItemClickListener != null) {
        listItemClickListener.onSelectAll(false);
      }
    }
  }

  @Override public void onSelected(int index, CheckBox item) {
    if (selectViewManager.getItems().size() == selectViewManager.getSelectedItems().size()) {
      if (listItemClickListener != null) {
        listItemClickListener.onSelectAll(true);
      }
    }
  }

  public interface ListItemClickListener {
    void onClick(int position, RefundGoodsItemModelInner item, View view);

    void onSelectAll(boolean selectAll);
  }
}
