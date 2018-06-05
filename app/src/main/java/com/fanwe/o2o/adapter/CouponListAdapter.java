package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.ShowQrcodeDialog;
import com.fanwe.o2o.model.CouponActModel.CouponModel;
import com.fanwe.o2o.utils.SDNumberUtil;

import java.util.List;

import static android.text.Spanned.SPAN_EXCLUSIVE_INCLUSIVE;
import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;
import static com.fanwe.library.utils.SDResourcesUtil.getResources;

/**
 * Created by Administrator on 2017/3/4.
 */

public class CouponListAdapter extends SDSimpleAdapter<CouponModel> {

  public CouponListAdapter(List<CouponModel> listModel, Activity activity) {
    super(listModel, activity);
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_coupon_list;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final CouponModel model) {
    TextView tv_instruct = get(R.id.tv_instruct, convertView);
    TextView tv_valid_date = get(R.id.tv_valid_date, convertView);
    TextView tv_price = get(R.id.tv_price, convertView);
    TextView tv_coupon = get(R.id.tv_coupon, convertView);
    TextView tv_coupon_code = get(R.id.tv_coupon_code, convertView);
    final RelativeLayout rl_coupon = get(R.id.rl_coupon, convertView);

    final String expire_time = model.getExpire_time();
    final String youhui_sn = model.getYouhui_sn();
    final String name = model.getyName();
    final String youhui_type = model.getYouhui_type();
    String youhui_value = model.getYouhui_value();
    final String status = model.getStatus();
    final String qrcode = model.getQrcode();

    if(!TextUtils.isEmpty(expire_time)){
      SDViewUtil.show(tv_valid_date);
      if(expire_time.equals("永久")||expire_time.equals("无限时")){
        SDViewBinder.setTextView(tv_valid_date, "使用期限 : " + "永久");
      }else {
        SDViewBinder.setTextView(tv_valid_date, "有效期 : " + expire_time);
      }
    }else {
      SDViewUtil.hide(tv_valid_date);
    }

    SDViewBinder.setTextView(tv_instruct, name);

    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(60);
    if (youhui_type.equals("0")) {   //减免0/折扣1
      if(youhui_value.length()<=1){
        youhui_value= String.valueOf(SDNumberUtil.round(Double.parseDouble(youhui_value),1));
      }
      final SpannableString ss = new SpannableString("¥" + youhui_value);
      ss.setSpan(ass, 0, 1, SPAN_INCLUSIVE_EXCLUSIVE);
      if(youhui_value.contains(".")){
        AbsoluteSizeSpan ass1 = new AbsoluteSizeSpan(60);
        ss.setSpan(ass1, 2, 4, SPAN_INCLUSIVE_EXCLUSIVE);
      }
      tv_price.setText(ss);
    } else if (youhui_type.equals("1")) {
      final SpannableString ss =
          new SpannableString(String.valueOf(Float.parseFloat(youhui_value) * 0.1) + "折");
      ss.setSpan(ass, 2, 4, SPAN_INCLUSIVE_EXCLUSIVE);
      tv_price.setText(ss);
    }

    if (status.equals("1")) { //0已过期 2已使用 1可用 2
      tv_coupon.setTextColor(tv_instruct.getCurrentTextColor());
      tv_coupon_code.setTextColor(getResources().getColor(R.color.main_color));
      SDViewBinder.setTextView(tv_coupon_code, youhui_sn);

    } else {
      tv_coupon.setTextColor(getResources().getColor(R.color.text_content));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.text_content));
      StringBuilder sb=null;
      if (status.equals("0"))
      {
        sb = new StringBuilder().append(youhui_sn).append("(已过期)");
      } else if (status.equals("2"))
      {
        sb = new StringBuilder().append(youhui_sn).append("(已使用)");
      }
      SDViewBinder.setTextView(tv_coupon_code, sb);
    }
    View.OnClickListener listener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (v == convertView) {
          if (itemClickListener != null) {
            itemClickListener.onClick(position, model, convertView);
          }
        } else if (v == rl_coupon) {
          if (status != null && status.equals("1")) {
            final ShowQrcodeDialog dialog = new ShowQrcodeDialog(getActivity(), qrcode, youhui_sn);
            dialog.show();
          }
        }
      }
    };
    convertView.setOnClickListener(listener);
    rl_coupon.setOnClickListener(listener);
  }

}



