package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemListModel;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemModel;

import java.util.List;

/**
 * 订单详情-商店item
 * Created by luod on 2017/3/2.
 */

public class OrderDetailsStoreAdapter extends SDSimpleAdapter<UcOrderItemDealOrderItemModel>
{
    public OrderDetailsStoreAdapter(List<UcOrderItemDealOrderItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_order_details_store;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UcOrderItemDealOrderItemModel model)
    {
        TextView tv_supplier_name = get(R.id.tv_supplier_name, convertView);
        SDGridLinearLayout gv_goods = get(R.id.gv_goods, convertView);

        SDViewBinder.setTextView(tv_supplier_name, model.getSupplier_name());
        setGoodsAdapter(gv_goods, model);
    }



    private void setGoodsAdapter(SDGridLinearLayout gv_content, UcOrderItemDealOrderItemModel model) {
        List<UcOrderItemDealOrderItemListModel> itemList = model.getList();
        OrderDetailsStoreGoodsAdapter itemAdapter = new OrderDetailsStoreGoodsAdapter(itemList, getActivity());
        gv_content.setColNumber(1);
        gv_content.setAdapter(itemAdapter);
        gv_content.postInvalidateDelayed(200);
    }

}
