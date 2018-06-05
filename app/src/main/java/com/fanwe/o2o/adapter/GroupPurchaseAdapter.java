package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.GroupPurIndexDealListModel;
import com.fanwe.o2o.model.GroupPurchaseModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class GroupPurchaseAdapter extends SDSimpleAdapter<GroupPurIndexDealListModel>
{
    public GroupPurchaseAdapter(List<GroupPurIndexDealListModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_group_purchase;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final GroupPurIndexDealListModel model)
    {
        ImageView iv_group = get(R.id.iv_group,convertView);
        TextView tv_des = get(R.id.tv_des,convertView);
        TextView tv_dis = get(R.id.tv_dis,convertView);
        TextView tv_des2 = get(R.id.tv_des2,convertView);
        TextView tv_price = get(R.id.tv_price,convertView);
        TextView tv_ori_price = get(R.id.tv_ori_price,convertView);
        TextView tv_sold = get(R.id.tv_sold,convertView);

        String buy_count = model.getBuy_count();
        float org_price = model.getOrigin_price();
        GlideUtil.load(model.getIcon()).into(iv_group);
        SDViewBinder.setTextView(tv_des,model.getSupplier_name());
        SDViewBinder.setTextView(tv_dis,model.getDistance());
        SDViewBinder.setTextView(tv_des2,model.getBrief());
        SDViewBinder.setTextView(tv_price,"￥ " + model.getCurrent_price());
        tv_ori_price.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        if (org_price != 0)
            SDViewBinder.setTextView(tv_ori_price, "￥ " + String.valueOf(org_price));
        if (!TextUtils.isEmpty(buy_count) && !buy_count.equals("0"))
            SDViewBinder.setTextView(tv_sold,"已售" + buy_count);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemClickListener.onClick(position,model,convertView);
            }
        });
    }
}
