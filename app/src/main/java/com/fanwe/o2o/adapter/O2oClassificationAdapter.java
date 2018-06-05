package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.SearchClassModel;

import java.util.List;

/**
 * Created by luodong on 2016/12/10 .
 */
public class O2oClassificationAdapter extends SDSimpleAdapter<SearchClassModel> {
    public O2oClassificationAdapter(List<SearchClassModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_o2o_classification;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, final SearchClassModel model) {
        TextView item_authentication_type = get(R.id.item_name, convertView);
        SDViewBinder.setTextView(item_authentication_type, model.getName());
    }
}
