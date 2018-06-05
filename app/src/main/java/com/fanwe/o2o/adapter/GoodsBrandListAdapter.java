package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.Brand_listModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class GoodsBrandListAdapter extends SDSimpleAdapter<Brand_listModel>
{

    public GoodsBrandListAdapter(List<Brand_listModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.MULTI);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_goods_brand;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final Brand_listModel model)
    {
        ImageView iv_select = get(R.id.iv_select,convertView);
        TextView tv_state = get(R.id.tv_state, convertView);

        if (model.isSelected())
        {
            SDViewUtil.setTextViewColorResId(tv_state,R.color.main_color);
            SDViewUtil.show(iv_select);
        } else
        {
            SDViewUtil.setTextViewColorResId(tv_state,R.color.text_content);
            SDViewUtil.hide(iv_select);
        }
        SDViewBinder.setTextView(tv_state, model.getName());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (itemClickListener != null)
                {
                    itemClickListener.onClick(position,model,convertView);
                }
            }
        });
    }
}
