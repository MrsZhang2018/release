package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.ShopCateTypeModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

/**
 * 商城二级分类适配器
 * Created by Administrator on 2016/12/12.
 */

public class MailClassMenuDetailsAdapter extends SDSimpleAdapter<ShopCateTypeModel> {
    public MailClassMenuDetailsAdapter(List<ShopCateTypeModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_class_menu_details;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final ShopCateTypeModel model) {
        ImageView iv_image = get(R.id.iv_image, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        GlideUtil.load(model.getCate_img()).into(iv_image);
        SDViewBinder.setTextView(tv_name,model.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position, model, convertView);
                }
            }
        });
    }
}
