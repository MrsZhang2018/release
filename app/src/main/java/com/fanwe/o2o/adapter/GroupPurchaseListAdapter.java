package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDMoreLinearLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.GoodsModel;
import com.fanwe.o2o.model.GroupPurListItemModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class GroupPurchaseListAdapter extends SDSimpleAdapter<GroupPurListItemModel>
{
    protected GroupPurItemClickListener itemGroupPurClickListener;

    public void setItemGroupPurClickListener(GroupPurItemClickListener itemGroupPurClickListener)
    {
        this.itemGroupPurClickListener = itemGroupPurClickListener;
    }

    public GroupPurchaseListAdapter(List<GroupPurListItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_group_pur_list;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, GroupPurListItemModel model)
    {
        TextView tv_loc_name = get(R.id.tv_loc_name, convertView);
        RatingBar rb_rating = get(R.id.rb_rating, convertView);
        TextView tv_avg_point = get(R.id.tv_avg_point, convertView);
        TextView tv_no_score = get(R.id.tv_no_score, convertView);
        TextView tv_area_name = get(R.id.tv_area_name, convertView);
        TextView tv_distance = get(R.id.tv_distance, convertView);
        TextView tv_authentication = get(R.id.tv_authentication, convertView);
        TextView tv_check = get(R.id.tv_check, convertView);


        final SDMoreLinearLayout more_content = get(R.id.more_content, convertView);

        float avg_point = model.getAvg_point();

        SDViewBinder.setTextView(tv_loc_name, model.getLocation_name());
        if (avg_point != 0)
        {
            SDViewUtil.show(rb_rating);
            SDViewUtil.show(tv_avg_point);
            SDViewUtil.hide(tv_no_score);
            SDViewBinder.setRatingBar(rb_rating, avg_point);
            SDViewBinder.setTextView(tv_avg_point, avg_point + "分");
        } else
        {
            SDViewUtil.hide(rb_rating);
            SDViewUtil.hide(tv_avg_point);
            SDViewUtil.show(tv_no_score);
        }
        SDViewBinder.setTextView(tv_area_name, model.getArea_name());
        SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());

        if ("1".equals(model.getIs_verify()))
        {
            SDViewUtil.show(tv_authentication);
        } else
        {
            SDViewUtil.hide(tv_authentication);
        }
        if ("1".equals(model.getOpen_store_payment()))
        {
            SDViewUtil.show(tv_check);
        } else
        {
            SDViewUtil.hide(tv_check);
        }
        setItemAdapter(more_content, model);
    }

    private void setItemAdapter(final SDMoreLinearLayout more_content, GroupPurListItemModel model)
    {
        List<GoodsModel> deal = model.getDeal();
        GroupPurchaseItemListAdapter itemAdapter = new GroupPurchaseItemListAdapter(deal, getActivity());
        more_content.setmMaxShowCount(2);
        more_content.setAdapter(itemAdapter);

        if (deal != null && deal.size() > 2)
        {
            more_content.setmViewMoreLayoutId(R.layout.item_view_group_pur_more);
            TextView tv_other = (TextView) more_content.getmViewMore().findViewById(R.id.tv_other);
            SDViewBinder.setTextView(tv_other, "其他" + (deal.size() - 2) + "个团购");
        }
        itemAdapter.setItemClickListener(new ItemClickListener<GoodsModel>()
        {
            @Override
            public void onClick(int position, GoodsModel item, View view)
            {
                if (itemGroupPurClickListener != null)
                {
                    itemGroupPurClickListener.onClick(position, item, view);
                }
            }
        });

        more_content.setmListenerOnOpenClose(new SDMoreLinearLayout.OnOpenCloseListener()
        {
            @Override
            public void onOpen(List<View> listView, View viewMore)
            {
                SDViewUtil.hide(viewMore);
            }

            @Override
            public void onClose(List<View> listView, View viewMore)
            {

            }
        });
    }

    public interface GroupPurItemClickListener
    {
        void onClick(int position, GoodsModel item, View view);
    }
}
