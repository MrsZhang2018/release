package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.title.TitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.ShowQrcodeDialog;
import com.fanwe.o2o.model.ConsumeCouponGroupModelInner;
import com.fanwe.o2o.model.ConsumeCouponGroupModelMiddle;
import com.fanwe.o2o.model.ConsumeCouponInPersonInner;
import com.fanwe.o2o.model.ConsumeCouponInPersonMiddle;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.fanwe.library.utils.SDResourcesUtil.getResources;
import static com.fanwe.o2o.adapter.ConsumeCouponItemExpandAdapter.IN_PERSON;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ConsumeCouponInPersonAdapter extends SDSimpleAdapter<ConsumeCouponInPersonMiddle> {
  public static final int DEFALUT_ITEM_ID = -1;
  private Animation rotateAnimation;
  private SDSelectManager<ConsumeCouponInPersonMiddle> expandSelectManager;
  private SDSelectManager<ConsumeCouponInPersonMiddle> visibleSelectManager;

  public ConsumeCouponInPersonAdapter(List<ConsumeCouponInPersonMiddle> listModel,
      Activity activity) {
    super(listModel, activity);
    expandSelectManager =new SDSelectManager<>();
    expandSelectManager.setMode(SDSelectManager.Mode.MULTI);
    visibleSelectManager=new SDSelectManager<>();
    visibleSelectManager.setMode(SDSelectManager.Mode.MULTI);
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_consumer_coupon_in_person;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final ConsumeCouponInPersonMiddle model) {
    convertView.setId(DEFALUT_ITEM_ID);

    TextView tv_order_code = get(R.id.tv_order_code, convertView);
    TitleItem title_item_shop = get(R.id.title_item_shop, convertView);
    TextView tv_goods_count = get(R.id.tv_goods_count, convertView);
    TextView tv_coupon = get(R.id.tv_coupon, convertView);
    TextView tv_coupon_code = get(R.id.tv_coupon_code, convertView);
    final TextView tv_click = get(R.id.tv_click, convertView);
    final RelativeLayout rl_click = get(R.id.rl_click, convertView);
    final ImageView iv_arrow1 = get(R.id.iv_arrow1, convertView);
    final RelativeLayout rl_coupon = get(R.id.rl_coupon, convertView);
    final SDGridLinearLayout gv_coupon_codes = get(R.id.gv_coupon_codes, convertView);

    final String order_sn = model.getOrder_sn();
    final String supplier_name = model.getSupplier_name();
    final String all_number = model.getAll_number();

    title_item_shop.setImageLeft(R.drawable.ic_store);
    title_item_shop.setImageLeftSize(21,21);
    //
    title_item_shop.setTextTop(supplier_name);
    title_item_shop.setTextTopSize(14);
    title_item_shop.setTextTopColor(R.color.text_title);

    List<ConsumeCouponInPersonInner> codeLists;
    codeLists = model.getCoupon();

    final StringBuilder sb = new StringBuilder("共").append(all_number).append("件商品");
    SDViewBinder.setTextView(tv_goods_count, sb);
    SDViewBinder.setTextView(tv_order_code, "订单编号 : " + order_sn);

    ConsumeCouponInPersonInner firstItem = new ConsumeCouponInPersonInner();
    if (codeLists != null && codeLists.size() > 0) {
      firstItem = codeLists.get(0);
    }
    final String password = firstItem.getPassword();
    final String status = firstItem.getStatus();
    final String info = firstItem.getInfo();
    final String qrcode = firstItem.getQrcode();

    if (status != null && status.equals("0")) { //1:可使用，0：不可使用
      rl_coupon.setEnabled(false);
      tv_coupon.setTextColor(getResources().getColor(R.color.text_content));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.text_content));
      final StringBuilder sb1 = new StringBuilder(password).append("(").append(info).append(")");
      tv_coupon_code.setText(sb1);
    } else if (status != null && status.equals("1")) { //1:可使用，0：不可使用
      rl_coupon.setEnabled(true);
      tv_coupon.setTextColor(getResources().getColor(R.color.text_title));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.main_color));
      tv_coupon_code.setText(password);
    }

    if (!expandSelectManager.isSelected(position)) {
      gv_coupon_codes.setVisibility(GONE);
      gv_coupon_codes.setEnabled(false);
      tv_click.setText("点击展开");
      SDViewUtil.setImageViewImageDrawableRes(iv_arrow1,R.drawable.ic_arrow_down);
    } else {
      gv_coupon_codes.setVisibility(View.VISIBLE);
      gv_coupon_codes.setEnabled(true);
      tv_click.setText("点击收起");
      SDViewUtil.setImageViewImageDrawableRes(iv_arrow1,R.drawable.ic_arrow_up_gray);
    }

    if (codeLists != null && codeLists.size() > 0) {
      List<ConsumeCouponInPersonInner> list = codeLists.subList(1, codeLists.size());
      if(list.size()>0) {
        setCodesAdapter(gv_coupon_codes, list);
        rl_click.setVisibility(View.VISIBLE);
        visibleSelectManager.setSelected(position,true);
      }else {
        visibleSelectManager.setSelected(position,false);
        rl_click.setVisibility(GONE);
      }
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
            if (status != null && status.equals("1")) {
              final ShowQrcodeDialog dialog = new ShowQrcodeDialog(getActivity(), qrcode, password);
              dialog.show();
            }
            break;
          case R.id.rl_click:
            if (expandSelectManager.isSelected(position)) {
              expandSelectManager.setSelected(position,false);
              gv_coupon_codes.setVisibility(GONE);
              gv_coupon_codes.setEnabled(false);
              tv_click.setText("点击展开");
              SDViewUtil.setImageViewImageDrawableRes(iv_arrow1,R.drawable.ic_arrow_down);
            } else {
              expandSelectManager.setSelected(position,true);
              gv_coupon_codes.setVisibility(View.VISIBLE);
              gv_coupon_codes.setEnabled(true);
              tv_click.setText("点击收起");
              SDViewUtil.setImageViewImageDrawableRes(iv_arrow1,R.drawable.ic_arrow_up_gray);
            }
            //iv_arrow1.startAnimation(getRotateAnimation());
            break;
          default:
            break;
        }
      }
    };

    convertView.setOnClickListener(listener);
    rl_click.setOnClickListener(listener);
    rl_coupon.setOnClickListener(listener);
  }

  private void setCodesAdapter(SDGridLinearLayout gv_content,
      List<ConsumeCouponInPersonInner> codeLists) {
    ConsumeCouponItemExpandAdapter itemAdapter =
        new ConsumeCouponItemExpandAdapter(codeLists, mActivity,IN_PERSON);
    gv_content.setColNumber(1);
    gv_content.setAdapter(itemAdapter);
    gv_content.postInvalidateDelayed(200);
  }

  public void setSelectManagerList(){
    List<ConsumeCouponInPersonMiddle> list=new ArrayList<>();
    list.addAll(listModel);
    expandSelectManager.appendItems(list,true);
    visibleSelectManager.appendItems(list,true);
  }

  private Animation getRotateAnimation() {
    if (rotateAnimation == null) {
      rotateAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_180_degree);
      rotateAnimation.setInterpolator(new LinearInterpolator());
    }
    rotateAnimation.setFillAfter(!rotateAnimation.getFillAfter());
    return rotateAnimation;
  }

}