package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.LocationCityDialog;
import com.fanwe.o2o.model.CityModel;
import com.fanwe.o2o.model.CityFirstModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位城市首字母适配器
 * Created by Administrator on 2016/12/12.
 */

public class LocationFirstAdapter extends SDSimpleAdapter<CityFirstModel> {
    private LocationCityDialog dialog;
    public LocationFirstAdapter(List<CityFirstModel> listModel, Activity activity, LocationCityDialog dialog) {
        super(listModel, activity);
        this.dialog = dialog;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_location_first;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final CityFirstModel model) {
        TextView tv_name = get(R.id.tv_name, convertView);
        FlowLayout flowlayout = get(R.id.flowlayout, convertView);
        initFlowlayout(flowlayout,model.getValue());
        SDViewBinder.setTextView(tv_name,model.getKey());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position, model, convertView);
                }
            }
        });
    }


    private void initFlowlayout(FlowLayout flowlayout, List<CityModel> list) {
        flowlayout.removeAllViews();
        for (CityModel model : list) {
            View view = createView(model);
            if (view != null) {
                flowlayout.addView(view);
            }
        }
    }

    private View createView(final CityModel model) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_o2o_search_tag, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(model.getName());
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppRuntimeWorker.setCity_name(model.getName()))
                {
                    dialog.dismiss();
                } else
                {
                    SDToast.showToast("设置城市失败");
                }
            }
        });
        return view;
    }
}
