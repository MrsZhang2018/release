package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.title.TitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.ShowQrcodeDialog;
import com.fanwe.o2o.model.ConsumeCouponDistMiddle;
import java.util.List;

import static com.fanwe.library.utils.SDResourcesUtil.getResources;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsumeCouponDistAdapter extends SDSimpleAdapter<ConsumeCouponDistMiddle> {
  public static final int DEFALUT_ITEM_ID = -1;
  private Animation rotateAnimation;

  public ConsumeCouponDistAdapter(List<ConsumeCouponDistMiddle> listModel,
      Activity activity) {
    super(listModel, activity);
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_consume_coupon_dist;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final ConsumeCouponDistMiddle model) {
    convertView.setId(DEFALUT_ITEM_ID);

    TextView tv_order_code = get(R.id.tv_order_code, convertView);
    TitleItem title_item_shop = get(R.id.title_item_shop, convertView);
    TextView tv_goods_count = get(R.id.tv_goods_count, convertView);
    TextView tv_coupon = get(R.id.tv_coupon, convertView);
    TextView tv_coupon_code = get(R.id.tv_coupon_code, convertView);
    final ImageView iv_arrow1 = get(R.id.iv_arrow1, convertView);
    final RelativeLayout rl_coupon = get(R.id.rl_coupon, convertView);

    final String order_sn = model.getOrder_sn();
    final String dist_name = model.getDist_name();
    final String number = model.getNumber();
    final String sn = model.getSn();
    final int status = model.getStatus();
    final String info = model.getInfo();
    final String qrcode = model.getQrcode();

    title_item_shop.setImageLeft(R.drawable.ic_store);
    title_item_shop.setImageLeftSize(21, 21);
    //
    title_item_shop.setTextTop(dist_name);
    title_item_shop.setTextTopSize(14);
    title_item_shop.setTextTopColor(R.color.text_title);

    final StringBuilder sb = new StringBuilder("共").append(number).append("件商品");
    SDViewBinder.setTextView(tv_goods_count, sb);
    SDViewBinder.setTextView(tv_order_code, "订单编号 : " + order_sn);

    if (status == 0) { //1:可使用，0：不可使用
      rl_coupon.setEnabled(false);
      tv_coupon.setTextColor(getResources().getColor(R.color.text_content));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.text_content));
      final StringBuilder sb1 = new StringBuilder(sn).append("(").append(info).append(")");
      tv_coupon_code.setText(sb1);
    } else if (status == 1) { //1:可使用，0：不可使用
      rl_coupon.setEnabled(true);
      tv_coupon.setTextColor(getResources().getColor(R.color.text_title));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.main_color));
      tv_coupon_code.setText(sn);
    }

    View.OnClickListener listener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        switch (v.getId()) {
          case DEFALUT_ITEM_ID:
            if (itemClickListener != null) {
              itemClickListener.onClick(position, model, convertView);
            }
            break;
          case R.id.rl_coupon:
            if (status == 1) {
              final ShowQrcodeDialog dialog = new ShowQrcodeDialog(getActivity(), qrcode, sn);
              dialog.show();
            }
            break;
          default:
            break;
        }
      }
    };

    convertView.setOnClickListener(listener);
    rl_coupon.setOnClickListener(listener);
  }
}
