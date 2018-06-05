package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.title.TitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.OrderRefundDetailActivity;
import com.fanwe.o2o.model.ItemRefundDetailModel;
import com.fanwe.o2o.utils.GlideUtil;
import java.util.List;

import static com.fanwe.o2o.jshandler.AppJsHandler.jump2DealWap;

/**
 * Created by Administrator on 2017/3/2.
 */

public class OrderRefundDetailAdapter extends SDSimpleAdapter<ItemRefundDetailModel> {

  public OrderRefundDetailAdapter(List<ItemRefundDetailModel> listModel, Activity activity) {
    super(listModel, activity);
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_refund_detail;
  }

  /**
   * 测试站和wap站 model和UI不太一样,以测试站为准
   */
  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final ItemRefundDetailModel model) {
    TitleItem title_item_shop = get(R.id.title_item_shop, convertView);
    ImageView iv_goods_image = get(R.id.iv_goods_image, convertView);
    TextView tv_instruct = get(R.id.tv_instruct, convertView);
    TextView tv_group_pur = get(R.id.tv_group_pur, convertView);
    TextView tv_price = get(R.id.tv_price, convertView);
    TextView tv_sum = get(R.id.tv_sum, convertView);
    TextView tv_refund_feedback = get(R.id.tv_refund_feedback, convertView);
    LinearLayout ll_goods_info = get(R.id.ll_goods_info, convertView);

    ImageView iv_state = get(R.id.iv_state, convertView);
    TextView tv_state = get(R.id.tv_state, convertView);
    TextView tv_request_time = get(R.id.tv_request_time, convertView);
    TextView tv_refund_money = get(R.id.tv_refund_money, convertView);
    TextView tv_money_going_to = get(R.id.tv_money_going_to, convertView);
    TextView tv_total_refund = get(R.id.tv_total_refund, convertView);
    LinearLayout ll_refund_money = get(R.id.ll_refund_money, convertView);
    LinearLayout ll_money_going_to = get(R.id.ll_money_going_to, convertView);

    title_item_shop.setImageLeft(R.drawable.ic_store);
    title_item_shop.setImageLeftSize(21, 21);

    final String rs2 = model.getRs2();
    final String id = model.getId();
    final String supplier_name = model.getSupplier_name();
    final String refund_status = model.getRefund_status();
    final String refund_info = model.getRefund_info();
    final String name = model.getName();
    final String attr_str = model.getAttr_str();
    final float unit_price = model.getUnit_price();
    final String number = model.getNumber();
    final String password = model.getPassword();

    final String create_time = model.getCreate_time();
    final String refund_money = model.getRefund_money();

    GlideUtil.loadHeadImage(model.getDeal_icon()).into(iv_goods_image);
    title_item_shop.setTextTop(supplier_name);
    title_item_shop.setTextTopSize(14);
    title_item_shop.setTextTopColorInt(tv_instruct.getCurrentTextColor());  //默认的安卓字体颜色
    SDViewBinder.setTextView(tv_group_pur, attr_str);
    SDViewBinder.setTextView(tv_instruct, name);
    SDViewBinder.setTextView(tv_price, "¥" + String.valueOf(unit_price));
    SDViewBinder.setTextView(tv_sum, "x" + number);
    SDViewBinder.setTextView(tv_refund_feedback, refund_info);
    SDViewBinder.setTextView(tv_total_refund, "¥" + refund_money);
    SDViewBinder.setTextView(tv_request_time, create_time);
    if (!TextUtils.isEmpty(rs2)) {
      if (rs2.equals("2")) {  //1申请退款中 2已退款 3退款被拒绝
        SDViewUtil.show(ll_refund_money);
        SDViewUtil.show(ll_money_going_to);
        SDViewUtil.setBackgroundResource(iv_state, R.drawable.ic_tick);
        SDViewBinder.setTextView(tv_state, "退款成功");
        SDViewBinder.setTextView(tv_refund_money, "¥" + refund_money);
        SDViewBinder.setTextView(tv_money_going_to, "账户余额");
      } else {
        SDViewUtil.hide(ll_refund_money);
        SDViewUtil.hide(ll_money_going_to);
        if (rs2.equals("1")) {
          SDViewUtil.setBackgroundResource(iv_state, R.drawable.ic_waiting);
          SDViewBinder.setTextView(tv_state, "退款申请已提交，请等待审核");
        } else if (rs2.equals("3")) {
          SDViewUtil.setBackgroundResource(iv_state, R.drawable.icon_fail);
          SDViewBinder.setTextView(tv_state, "退款申请已被拒绝");
        }
      }
    }
    if (password != null && !password.equals("")) {
      SDViewBinder.setTextView(tv_group_pur, "团购券: " + password);
    }

    ll_goods_info.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
          final String deal_id=model.getDeal_id();
          jump2DealWap(getActivity(),"deal",deal_id);
      }
    });
  }
}




