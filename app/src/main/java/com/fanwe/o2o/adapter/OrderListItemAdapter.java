package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.HorizontalScrollViewPageIndicator;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

/**
 * Created by luod on 2017/3/2.
 */

public class OrderListItemAdapter extends SDSimpleAdapter<UcOrderItemDealOrderItemModel>
{
    private OnHsvClickListener onHsvClickListener;
    public OrderListItemAdapter(List<UcOrderItemDealOrderItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_order_list_item;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UcOrderItemDealOrderItemModel model)
    {
        TextView tv_supplier_name = get(R.id.tv_supplier_name, convertView);
        LinearLayout ll_content_for_one = get(R.id.ll_content_for_one, convertView);
        TextView tv_status_name = get(R.id.tv_status_name, convertView);
        ImageView iv_deal_icon_for_one = get(R.id.iv_deal_icon_for_one, convertView);
        TextView tv_name_for_one = get(R.id.tv_name_for_one, convertView);
        TextView tv_unit_price_for_one = get(R.id.tv_unit_price_for_one, convertView);
        TextView tv_number_for_one = get(R.id.tv_number_for_one, convertView);
        TextView tv_spec=get(R.id.tv_spec,convertView);

        FrameLayout fl_content_for_more = get(R.id.fl_content_for_more, convertView);
        View v_hsv = get(R.id.v_hsv, convertView);
        HorizontalScrollViewPageIndicator hsv_goods = get(R.id.hsv_goods, convertView);
        SDViewBinder.setTextView(tv_status_name, model.getStatus_name());
        SDViewBinder.setTextView(tv_supplier_name, model.getSupplier_name());

        /*如果商品等于一件*/
        if (model.getList().size()==1){
            SDViewUtil.show(ll_content_for_one);
            GlideUtil.load(model.getList().get(0).getDeal_icon()).into(iv_deal_icon_for_one);
            SDViewBinder.setTextView(tv_name_for_one, model.getList().get(0).getName());
            SDViewBinder.setTextView(tv_unit_price_for_one,"￥"+ model.getList().get(0).getApp_format_unit_price()+"");
            SDViewBinder.setTextView(tv_number_for_one, "x"+model.getList().get(0).getNumber());
            final String attr=model.getList().get(0).getAttr_str();
            if(TextUtils.isEmpty(attr)){
                SDViewUtil.hide(tv_spec);
            }else {
                SDViewUtil.show(tv_spec);
                SDViewBinder.setTextView(tv_spec,"规格: "+attr);
            }
        }else {
            SDViewUtil.hide(ll_content_for_one);
        }


        /*如果商品大于一件*/
        if (model.getList().size()>1){
            SDViewUtil.show(fl_content_for_more);
            OrderListItemGoodsAdapter adapter = new OrderListItemGoodsAdapter(model.getList(), getActivity());
            hsv_goods.setAdapter(adapter);
            v_hsv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //HorizontalScrollViewPageIndicator截获了点击事件，写一个view在HorizontalScrollViewPageIndicator上面获取点击监听
                    if(onHsvClickListener!=null){
                        onHsvClickListener.onHsvClickReceive(v);
                    }
                }
            });
        }else {
            SDViewUtil.hide(fl_content_for_more);
        }

    }


    public void setOnHsvClickListener(OnHsvClickListener onHsvClickListener){
        this.onHsvClickListener = onHsvClickListener;
    }

    public interface OnHsvClickListener
    {
        void onHsvClickReceive(View v);
    }

}
