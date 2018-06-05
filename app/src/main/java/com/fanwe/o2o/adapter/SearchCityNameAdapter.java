package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.CityModel;
import com.fanwe.o2o.model.SearchClassModel;

import java.util.List;


public class SearchCityNameAdapter extends SDSimpleAdapter<CityModel> {
    public SearchCityNameAdapter(List<CityModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_city_name_search;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, final CityModel model) {
        TextView item_authentication_type = get(R.id.item_name, convertView);
        SDViewBinder.setTextView(item_authentication_type, model.getName());
    }
}
