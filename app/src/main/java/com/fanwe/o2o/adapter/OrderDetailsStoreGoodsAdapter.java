package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemListModel;
import com.fanwe.o2o.utils.GlideUtil;
import java.util.List;

import static com.fanwe.o2o.jshandler.AppJsHandler.jump2DealWap;

/**
 * 订单详情-商品item
 * Created by luod on 2017/3/3.
 */

public class OrderDetailsStoreGoodsAdapter extends SDSimpleAdapter<UcOrderItemDealOrderItemListModel> {
    public OrderDetailsStoreGoodsAdapter(List<UcOrderItemDealOrderItemListModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_order_details_store_goods;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UcOrderItemDealOrderItemListModel model) {
        ImageView iv_deal_icon_for_one = get(R.id.iv_deal_icon, convertView);
        TextView tv_name_for_one = get(R.id.tv_name, convertView);
        TextView tv_unit_price_for_one = get(R.id.tv_unit_price, convertView);
        TextView tv_number_for_one = get(R.id.tv_number, convertView);
        TextView tv_state=get(R.id.tv_state,convertView);
        TextView tv_code=get(R.id.tv_code,convertView);

        final String attr=model.getAttr_str();
        final String pwd=model.getPassword();
        if(!TextUtils.isEmpty(pwd)){
            SDViewUtil.show(tv_code);
            SDViewBinder.setTextView(tv_code,"券码 ："+pwd);
        }else {
//            SDViewUtil.hide(tv_code);
            if(TextUtils.isEmpty(attr)){
                SDViewUtil.hide(tv_code);
            }else {
                SDViewUtil.show(tv_code);
                SDViewBinder.setTextView(tv_code,"规格: "+attr);
            }

        }


        GlideUtil.load(model.getDeal_icon()).into(iv_deal_icon_for_one);
        SDViewBinder.setTextView(tv_name_for_one, model.getName());
        SDViewBinder.setTextView(tv_unit_price_for_one, "￥" + model.getApp_format_unit_price() + "");
        SDViewBinder.setTextView(tv_number_for_one, "x" + model.getNumber());
        SDViewBinder.setTextView(tv_state, model.getDeal_orders());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
              jump2DealWap(getActivity(),"deal",model.getDeal_id());
            }
        });

    }
}
