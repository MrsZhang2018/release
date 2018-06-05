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
import com.fanwe.o2o.model.EventModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ActivitiesListAdapter extends SDSimpleAdapter<EventModel>
{
    public ActivitiesListAdapter(List<EventModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_activites_list;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final EventModel model)
    {
        ImageView iv_activity = get(R.id.iv_activity, convertView);
        TextView tv_activity_name = get(R.id.tv_activity_name, convertView);
        ImageView iv_event = get(R.id.iv_event,convertView);
        ImageView iv_header = get(R.id.iv_header, convertView);
        TextView tv_seller = get(R.id.tv_seller, convertView);
        TextView tv_distance = get(R.id.tv_distance, convertView);
        TextView tv_time = get(R.id.tv_time, convertView);

        String submit_begin_time_format = model.getSubmit_begin_time_format();
        String submit_end_time_format = model.getSubmit_end_time_format();
        GlideUtil.load(model.getImg()).into(iv_activity);
        SDViewBinder.setTextView(tv_activity_name, model.getName());
        if (model.getOut_time() == 1 || model.getIs_over() == 1)
        {
            SDViewUtil.show(iv_event);
            iv_event.setImageResource(R.drawable.ic_events_sale_out);
        }else if (model.getSubmit_count() == model.getTotal_count())
        {
            SDViewUtil.show(iv_event);
            iv_event.setImageResource(R.drawable.ic_events_house_full);
        }else
        {
            SDViewUtil.hide(iv_event);
        }
        GlideUtil.load(model.getSupplier_info_preview()).into(iv_header);
        SDViewBinder.setTextView(tv_seller, model.getSupplier_info_name());
        SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());
        if (!TextUtils.isEmpty(submit_begin_time_format) && !TextUtils.isEmpty(submit_end_time_format))
            SDViewBinder.setTextView(tv_time, "报名时间:" + submit_begin_time_format + "至" + model.getSubmit_end_time_format());
        else
            SDViewBinder.setTextView(tv_time, "报名时间:" + model.getSheng_time_format());

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
