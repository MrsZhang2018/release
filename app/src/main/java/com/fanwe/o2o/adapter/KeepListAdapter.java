package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.KeepItemModel;
import com.fanwe.o2o.utils.GlideUtil;
import com.handmark.pulltorefresh.library.internal.SDUtils;

import java.util.List;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-09
 * Time: 09:15
 * 功能:
 */
public class KeepListAdapter extends SDSimpleAdapter<KeepItemModel> {
    private int keep_status;
    private SelectAllListener mSelectAllListener;

    public KeepListAdapter(List<KeepItemModel> listModel, Activity activity,int keep_status) {
        super(listModel, activity);
        this.keep_status = keep_status;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_keep_list;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent,final KeepItemModel model) {
        ImageView icon = get(R.id.item_keep_icon, convertView);
        ImageView shelf = get(R.id.item_keep_shelf, convertView);
        final ImageView select = get(R.id.item_keep_select, convertView);
        TextView name = get(R.id.item_keep_name, convertView);
        TextView price = get(R.id.item_keep_price, convertView);

        GlideUtil.load(model.getIcon()).into(icon);
        SDViewBinder.setTextView(name,model.getName());
        if(keep_status == 0){
            SDViewBinder.setTextView(price,"¥"+model.getCurrent_price());
        }else {
            if(model.getScore_limit() != 0){
                SDViewBinder.setTextView(price,model.getScore_limit()+"积分");
            }else {
                if(model.getPoint_limit() == 0){
                    SDViewUtil.hide(price);
                }
                SDViewBinder.setTextView(price,model.getPoint_limit()+"经验");
            }
        }
        if( model.getOut_time() == 1){
            SDViewUtil.show(shelf);
            if(keep_status == 1){
                shelf.setBackgroundResource(R.drawable.youhui_end);
            }else if(keep_status == 2){
                shelf.setBackgroundResource(R.drawable.events_sale_out);
            }
            name.setTextColor(getActivity().getResources().getColor(R.color.text_hint));
            price.setTextColor(getActivity().getResources().getColor(R.color.text_hint));
        }
        if(model.getIs_select() == 1){
            SDViewUtil.show(select);
            select.setBackgroundResource(R.drawable.ic_address_default);
        }else if(model.getIs_select() == 2){
            select.setBackgroundResource(R.drawable.ic_address_selected);
        }else if(model.getIs_select() == 0){
            SDViewUtil.hide(select);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                   if(model.getIs_select() == 1){
                        model.setIs_select(2);
                        select.setBackgroundResource(R.drawable.ic_address_selected);
                       mSelectAllListener.Selected();
                    }else if(model.getIs_select() == 2){
                        model.setIs_select(1);
                        select.setBackgroundResource(R.drawable.ic_address_default);
                       mSelectAllListener.Selected();
                    }
                    itemClickListener.onClick(position, model, convertView);
                }
            }
        });
    }
    public void setListener(SelectAllListener listener) {
        this.mSelectAllListener = listener;
    }
    public interface SelectAllListener{
        void Selected();
    }
}
