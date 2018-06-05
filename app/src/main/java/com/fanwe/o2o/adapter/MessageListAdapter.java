package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.UcMsgCateDataModel;

import java.util.List;

/**
 * Created by luod on 2017/3/10.
 */

public class MessageListAdapter extends SDSimpleAdapter<UcMsgCateDataModel>
{
    public MessageListAdapter(List<UcMsgCateDataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_message_list;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UcMsgCateDataModel model)
    {
        TextView tv_create_time = get(R.id.tv_create_time, convertView);
        TextView tv_title = get(R.id.tv_title, convertView);
        TextView tv_content = get(R.id.tv_content, convertView);
        SDViewBinder.setTextView(tv_create_time,model.getCreate_time());
        SDViewBinder.setTextView(tv_title,model.getTitle());
        SDViewBinder.setTextView(tv_content,model.getContent());

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
