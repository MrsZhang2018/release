package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.ShowQrcodeDialog;
import com.fanwe.o2o.model.ConsumeCouponModelInner;
import java.util.List;

import static com.fanwe.library.utils.SDResourcesUtil.getResources;

/**
 * 消费券列表下item里的展开项列表适配器 Created by Administrator on 2017/3/7.
 */

public class ConsumeCouponItemExpandAdapter<T extends ConsumeCouponModelInner> extends SDSimpleAdapter<T> {
  public static final String GROUP = "GROUP";
  public static final String IN_PERSON = "IN_PERSON";
  private String state = "";

  protected ListItemClickListener listItemClickListener;

  public void setListItemClickListener(
      final ListItemClickListener listItemClickListener) {
    this.listItemClickListener = listItemClickListener;
    setItemClickListener(new ItemClickListener<T>() {
      @Override
      public void onClick(int position, ConsumeCouponModelInner item, View view) {
        if (listItemClickListener != null) {
          listItemClickListener.onClick(position, item, view);
        }
      }
    });
  }

  public ConsumeCouponItemExpandAdapter(List<T> listModel,
      Activity activity, String state) {
    super(listModel, activity);
    this.state = state;
  }

  @Override
  public int getLayoutId(int position, View convertView, ViewGroup parent) {
    return R.layout.item_consume_coupon_group_expand;
  }

  @Override
  public void bindData(final int position, final View convertView, ViewGroup parent,
      final T model) {

    TextView tv_coupon = get(R.id.tv_coupon, convertView);
    TextView tv_coupon_code = get(R.id.tv_coupon_code, convertView);
    final ImageView iv_arrow1 = get(R.id.iv_arrow1, convertView);

    final String password = model.getPassword();
    final String status = model.getStatus();
    final String info = model.getInfo();
    final String qrcode = model.getQrcode();

    if (status.equals("0")) { //1:可使用，0：不可使用
      convertView.setEnabled(false);
      tv_coupon.setTextColor(getResources().getColor(R.color.text_content));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.text_content));
      final StringBuilder sb = new StringBuilder(password).append("(").append(info).append(")");
      tv_coupon_code.setText(sb);
    } else if (status.equals("1")) { //1:可使用，0：不可使用
      convertView.setEnabled(true);
      tv_coupon.setTextColor(getResources().getColor(R.color.text_title));
      tv_coupon_code.setTextColor(getResources().getColor(R.color.main_color));
      tv_coupon_code.setText(password);
      convertView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

          final ShowQrcodeDialog dialog = new ShowQrcodeDialog(getActivity(), qrcode, password);
          dialog.showCenter();
        }
      });
    }
  }

  public interface ListItemClickListener {
    void onClick(int position, ConsumeCouponModelInner item, View view);
  }
}

