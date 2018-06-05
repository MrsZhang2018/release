package com.fanwe.o2o.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.title.TitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.OrderRefundListModel;
import com.fanwe.o2o.model.WapIndexDealListModel;
import com.fanwe.o2o.utils.GlideUtil;

/**
 * Created by Administrator on 2017/3/1.
 */

public class ItemOrderRefundView extends SDAppView
{

  private TitleItem title_item_shop;
  private TextView tv_refund_state;
  private ImageView iv_goods_image;
  private TextView tv_instruct;
  private TextView tv_price;
  private TextView tv_sum;

  private OrderRefundListModel model;

  public ItemOrderRefundView(Context context)
  {
    super(context);
    init();
  }

  public ItemOrderRefundView(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    init();
  }

  public ItemOrderRefundView(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
    init();
  }

  @Override
  protected void init()
  {
    super.init();
    setContentView(R.layout.item_o2o_order_refund);
    initView();
  }

  private void initView()
  {

    title_item_shop = find(R.id.title_item_shop);
    tv_refund_state = find(R.id.tv_refund_state);
    iv_goods_image = find(R.id.iv_goods_image);
    tv_instruct = find(R.id.tv_instruct);
    tv_price = find(R.id.tv_price);
    tv_sum = find(R.id.tv_sum);

    title_item_shop.setImageLeft(R.drawable.ic_tab_home_nomal);

  }


}
