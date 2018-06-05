package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.GroupPurRecommendDealCateModel;

/**
 * Created by Administrator on 2016/12/12.
 */

public class GroupPurchaseBottomAdapter extends SDSimpleRecyclerAdapter<GroupPurRecommendDealCateModel>
{

    public GroupPurchaseBottomAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_group_purchase_bottom;
    }

    @Override
    public void bindData(SDRecyclerViewHolder holder, final int position, final GroupPurRecommendDealCateModel model)
    {
        super.bindData(holder, position, model);
        TextView tv_clas = holder.get(R.id.tv_clas);

        int itemWidth = (SDViewUtil.getScreenWidth() / 3);
        SDViewUtil.setViewWidth(tv_clas, itemWidth);
//        SDViewUtil.setViewHeight(tv_clas, itemWidth);

        int imageWidth = itemWidth - SDViewUtil.dp2px(10);
        SDViewUtil.setViewWidth(tv_clas, imageWidth);
//        SDViewUtil.setViewHeight(iv_image, imageWidth);

        SDViewBinder.setTextView(tv_clas,model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickListener(position,model,v);
            }
        });
    }
}
