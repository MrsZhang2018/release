package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.BaiduSuggestionSearchModel;

import java.util.List;

public class AddAddressMapAdapter_dc extends SDSimpleAdapter<BaiduSuggestionSearchModel> {

    public AddAddressMapAdapter_dc(List<BaiduSuggestionSearchModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_add_address_map_dc;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final BaiduSuggestionSearchModel model) {
        ImageView iv_image = ViewHolder.get(convertView, R.id.iv_image);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_address = ViewHolder.get(convertView, R.id.tv_address);

        if (model != null) {
            SDViewBinder.setTextView(tv_name, model.getName());
            SDViewBinder.setTextView(tv_address, model.getAddress());
        }
    }

}
