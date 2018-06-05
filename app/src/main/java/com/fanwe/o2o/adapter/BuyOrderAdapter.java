package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.BuyOrderActModel.BuyOrderModel;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BuyOrderAdapter extends SDSimpleAdapter<BuyOrderModel> {

  protected ListItemClickListener listItemClickListener;

  public void setListItemClickListener(final ListItemClickListener listItemClickListener) {
    this.listItemClickListener = listItemClickListener;
    setItemClickListener(new ItemClickListener<BuyOrderModel>() {
      @Override
      public void onClick(int position, BuyOrderModel item, View view) {
        if (listItemClickListener != null) {
          listItemClickListener.onClick(position, item, view);
        }
      }
    });
  }

  public BuyOrderAdapter(List<BuyOrderModel> listModel, Activity activity) {
    super(listModel, activity);
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_pay_order;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final BuyOrderModel model) {
    TextView tv_state = get(R.id.tv_state, convertView);
    TextView tv_supplier_name = get(R.id.tv_supplier_name, convertView);
    TextView tv_consume_money = get(R.id.tv_consume_money, convertView);
    TextView tv_discount_money = get(R.id.tv_discount_money, convertView);
    TextView tv_actual_pay = get(R.id.tv_actual_pay, convertView);
    TextView tv_pay_time = get(R.id.tv_pay_time, convertView);

    final String status = model.getStatus();
    final String location_name = model.getLocation_name();
    final String total_price = model.getTotal_price();
    final String discount_price = model.getDiscount_price();
    final String pay_amount = model.getPay_amount();
    final String create_time = model.getCreate_time();

    SDViewBinder.setTextView(tv_state, status);
    SDViewBinder.setTextView(tv_supplier_name, location_name);
    SDViewBinder.setTextView(tv_consume_money, "¥ " + total_price);
    SDViewBinder.setTextView(tv_discount_money, "¥ " + discount_price);
    SDViewBinder.setTextView(tv_actual_pay, "¥ " + pay_amount);
    SDViewBinder.setTextView(tv_pay_time, create_time);
  }

  public interface ListItemClickListener {
    void onClick(int position, BuyOrderModel item, View view);
  }
}





