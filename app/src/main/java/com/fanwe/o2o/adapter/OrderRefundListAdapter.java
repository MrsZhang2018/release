package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.title.TitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.ItemOrderRefundModel;
import com.fanwe.o2o.utils.GlideUtil;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OrderRefundListAdapter extends SDSimpleAdapter<ItemOrderRefundModel> {

  public OrderRefundListAdapter(List<ItemOrderRefundModel> listModel, Activity activity) {
    super(listModel, activity);
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_o2o_order_refund;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final ItemOrderRefundModel model) {
    TitleItem title_item_shop = get(R.id.title_item_shop, convertView);
    TextView tv_refund_state = get(R.id.tv_refund_state, convertView);
    ImageView iv_goods_image = get(R.id.iv_goods_image, convertView);
    TextView tv_instruct = get(R.id.tv_instruct, convertView);
    TextView tv_price = get(R.id.tv_price, convertView);
    TextView tv_sum = get(R.id.tv_sum, convertView);
    TextView tv_refund_money = get(R.id.tv_refund_money, convertView);

    title_item_shop.setImageLeft(R.drawable.ic_store);
    title_item_shop.setImageLeftSize(21, 21);
    title_item_shop.setImageRight(R.drawable.ic_arrow_right_gray);

    final String supplier_name = model.getSupplier_name();
    final String refund_status = model.getRefund_status();
    final String status_str = model.getStatus_str();
    final String name = model.getName();
    final String unit_price = model.getUnit_price();
    final String refund_money = model.getRefund_money();
    final String number = model.getNumber();

    GlideUtil.loadHeadImage(model.getDeal_icon()).into(iv_goods_image);
    title_item_shop.setTextTop(supplier_name);
    title_item_shop.setTextTopSize(14);
    title_item_shop.setTextTopColor(R.color.text_title);
    SDViewBinder.setTextView(tv_refund_state, status_str);
    SDViewBinder.setTextView(tv_instruct, name);
    SDViewBinder.setTextView(tv_price, unit_price);
    SDViewBinder.setTextView(tv_sum, "x" + number);
    if (refund_status.equals("2")) {
      tv_refund_money.setVisibility(View.VISIBLE);
      SDViewBinder.setTextView(tv_refund_money, "退款金额 : ¥" + refund_money);
    } else {
      tv_refund_money.setVisibility(View.GONE);
    }

    convertView.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        if (itemClickListener != null) {
          itemClickListener.onClick(position, model, convertView);
        }
      }
    });
  }

  public interface ListItemClickListener {
    void onClick(int position, ItemOrderRefundModel item, View view);
  }
}



