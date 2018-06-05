package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.ShowQrcodeDialog;
import com.fanwe.o2o.model.ActivityCouponActModel.ActivityCouponModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
import static com.fanwe.library.utils.SDResourcesUtil.getResources;

/**
 * Created by Administrator on 2017/3/5.
 */

public class ActivityCouponAdapter extends SDSimpleAdapter<ActivityCouponModel> {

    public ActivityCouponAdapter(List<ActivityCouponModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_activity_coupon;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent,
                         final ActivityCouponModel model) {

        final RelativeLayout rl_coupon = get(R.id.rl_coupon, convertView);
        ImageView iv_icon = get(R.id.iv_icon, convertView);
        TextView tv_instruct = get(R.id.tv_instruct, convertView);
        TextView tv_valid_date = get(R.id.tv_valid_date, convertView);
        TextView tv_coupon = get(R.id.tv_coupon, convertView);
        TextView tv_coupon_code = get(R.id.tv_coupon_code, convertView);
        //
        final String event_end_time = model.getEvent_end_time();
        final String sn = model.getSn();
        final String eName = model.geteName();
        final String icon = model.getIcon();
        final String status = model.getStatus();
        final String qrcode = model.getQrcode();

        //// TODO ?????使用期限 永久???
        SDViewBinder.setTextView(tv_valid_date, "有效期：" + event_end_time);
        GlideUtil.loadHeadImage(icon).into(iv_icon);
        SDViewBinder.setTextView(tv_instruct, eName);

        if (status.equals("0")) { //0不可用 1可用
            tv_coupon.setTextColor(getResources().getColor(R.color.text_content));
            tv_coupon_code.setTextColor(getResources().getColor(R.color.text_content));
            final StringBuilder sb = new StringBuilder().append(sn).append("(").append(model.getInfo()).append(")");
            SDViewBinder.setTextView(tv_coupon_code, sb);
        } else {
            tv_coupon.setTextColor(tv_instruct.getCurrentTextColor());
            tv_coupon_code.setTextColor(getResources().getColor(R.color.main_color));
            SDViewBinder.setTextView(tv_coupon_code, sn);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == convertView) {
                    if (itemClickListener != null) {
                        itemClickListener.onClick(position, model, convertView);
                    }
                } else if (v == rl_coupon) {
                    if (status != null && status.equals("1")) {
                        final ShowQrcodeDialog dialog = new ShowQrcodeDialog(getActivity(), qrcode, sn);
                        dialog.show();
                    }
                }
            }
        };
        convertView.setOnClickListener(listener);
        rl_coupon.setOnClickListener(listener);
    }

}




