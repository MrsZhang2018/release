package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.PaidModel;

import java.util.List;

/**
 * 订单详情-优惠金额item
 * Created by luod on 2017/3/3.
 */

public class OrderDetailsPaidAdapter extends SDSimpleAdapter<PaidModel>
{
    public OrderDetailsPaidAdapter(List<PaidModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_order_details_paid;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final PaidModel model)
    {
        TextView tv_value = get(R.id.tv_value, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        SDViewBinder.setTextView(tv_name, model.getName());
        SDViewBinder.setTextView(tv_value, "-￥"+model.getValue());
    }

}
