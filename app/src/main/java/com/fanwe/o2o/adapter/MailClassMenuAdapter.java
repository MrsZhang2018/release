package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.ShopCateListModel;

import java.util.List;

/**
 * 商城分类适配器
 * Created by Administrator on 2016/12/12.
 */

public class MailClassMenuAdapter extends SDSimpleAdapter<ShopCateListModel> {
    public MailClassMenuAdapter(List<ShopCateListModel> listModel, Activity activity) {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_class_menu;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final ShopCateListModel model) {
        View view_select = get(R.id.view_select,convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        SDViewBinder.setTextView(tv_name,model.getName());

        if(model.isSelected()){
            SDViewUtil.show(view_select);
            SDViewUtil.setTextViewColorResId(tv_name,R.color.main_color);
            SDViewUtil.setBackgroundColorResId(tv_name,R.color.bg_act_fra);
        }else {
            SDViewUtil.hide(view_select);
            SDViewUtil.setTextViewColorResId(tv_name,R.color.text_content_deep);
            SDViewUtil.setBackgroundColorResId(tv_name,R.color.white);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position, model, convertView);
                    getSelectManager().performClick(position);
                }
            }
        });
    }
}
