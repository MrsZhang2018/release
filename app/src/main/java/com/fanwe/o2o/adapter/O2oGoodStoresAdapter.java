package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.WapIndexSupplierListModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class O2oGoodStoresAdapter extends SDSimpleAdapter<WapIndexSupplierListModel>
{
    public O2oGoodStoresAdapter(List<WapIndexSupplierListModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_o2o_good_store;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final WapIndexSupplierListModel model)
    {
        ImageView iv_store = get(R.id.iv_store,convertView);
        TextView tv_store = get(R.id.tv_store,convertView);

        GlideUtil.load(model.getPreview()).placeholder(0).into(iv_store);
        SDViewBinder.setTextView(tv_store,model.getName());

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
