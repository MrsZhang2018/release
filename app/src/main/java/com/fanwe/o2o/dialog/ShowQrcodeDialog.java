package com.fanwe.o2o.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.utils.GlideUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 弹出二维码扫描 Created by Administrator on 2017/3/7.
 */

public class ShowQrcodeDialog extends SDDialogCustom {

  @ViewInject(R.id.iv_qrcode)
  public ImageView iv_qrcode;
  @ViewInject(R.id.iv_back)
  public ImageView iv_back;

  @ViewInject(R.id.tv_coupon_code)
  public TextView tv_coupon_code;

  private String mStrUrl;
  private String coupon;

  public ShowQrcodeDialog(Activity activity, String qrCode, String coupon) {
    super(activity);
    this.mStrUrl = qrCode;
    this.coupon = coupon;
    setImage(mStrUrl);
    setCoupon(coupon);

  }

  @Override
  protected void init() {
    super.init();
    setDismissAfterClick(false);
    setContentView(R.layout.dialog_show_qrcode,new ViewGroup.LayoutParams(SDViewUtil.dp2px(310),
        ViewGroup.LayoutParams.WRAP_CONTENT));
    x.view().inject(this, getContentView());
    setCanceledOnTouchOutside(true);
    iv_back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });
  }

  public void setImage(String url) {
    GlideUtil.load(url).into(iv_qrcode);
  }

  public void setCoupon(String couponCode){
    final StringBuilder sb=new StringBuilder().append("券码：").append(couponCode);
    tv_coupon_code.setText(sb);
  }

}
