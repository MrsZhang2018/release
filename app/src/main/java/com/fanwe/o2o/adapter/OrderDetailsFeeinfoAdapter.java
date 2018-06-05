package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.FeeinfoModel;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemListModel;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemModel;

import java.util.List;

/**
 * 订单详情-商品金额item
 * Created by luod on 2017/3/2.
 */

public class OrderDetailsFeeinfoAdapter extends SDSimpleAdapter<FeeinfoModel>
{
    public OrderDetailsFeeinfoAdapter(List<FeeinfoModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_order_details_feeinfo;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final FeeinfoModel model)
    {
        TextView tv_value = get(R.id.tv_value, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        SDViewBinder.setTextView(tv_name, model.getName());
        SDViewBinder.setTextView(tv_value, "￥"+model.getValue());
    }

}
