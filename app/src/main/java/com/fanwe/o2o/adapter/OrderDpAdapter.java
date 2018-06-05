package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.UcOrderOrderDpItemModel;
import com.fanwe.o2o.utils.GlideUtil;
import com.fanwe.o2o.view.CommonRatingBar;
import java.util.List;

/**
 * Created by luod on 2017/3/8.
 */

public class OrderDpAdapter extends SDSimpleAdapter<UcOrderOrderDpItemModel> {

    public OrderDpAdapter(List<UcOrderOrderDpItemModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_order_dp;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UcOrderOrderDpItemModel model) {
        CommonRatingBar rb_rating = get(R.id.rb_rating, convertView);
        ImageView iv_deal_icon = get(R.id.iv_deal_icon, convertView);
        EditText et_content = get(R.id.et_content, convertView);

        GlideUtil.load(model.getDeal_icon()).into(iv_deal_icon);
        rb_rating.setOnRatingChangeListener(new CommonRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float rating) {
                getItem(position).setRating(rating);
            }
        });
        SDViewBinder.setTextView(et_content,model.getContent());
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(position).setContent(s.toString());
            }
        });

//        SDViewBinder.setRatingBar(rb_rating, model.getNumber());
//        convertView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (itemClickListener != null) {
//                    itemClickListener.onClick(position, model, convertView);
//                }
//            }
//        });
    }

}
