package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.GoodsModel;
import com.fanwe.o2o.utils.GlideUtil;

/**
 * Created by Administrator on 2017/1/20.
 */

public class GoodsRecyclerAdapter extends SDSimpleRecyclerAdapter<GoodsModel>
{
    private String isShow;

    public GoodsRecyclerAdapter(String isShow, Activity activity)
    {
        super(activity);
        this.isShow = isShow;
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_goods_recycler;
    }

    @Override
    public void bindData(SDRecyclerViewHolder holder, final int position, final GoodsModel model)
    {
        super.bindData(holder, position, model);

        LinearLayout ll_hor =  holder.get(R.id.ll_hor);
        ImageView iv_group2 =  holder.get(R.id.iv_group2);
        TextView tv_des2 = holder.get(R.id.tv_des2);
        TextView tv_price2 = holder.get(R.id.tv_price2);
        TextView tv_ori_price2 = holder.get(R.id.tv_ori_price2);
        TextView tv_sold2 = holder.get(R.id.tv_sold2);

        LinearLayout ll_ver = holder.get(R.id.ll_ver);
        ImageView iv_group = holder.get(R.id.iv_group);
        TextView tv_des = holder.get(R.id.tv_des);
        TextView tv_price = holder.get(R.id.tv_price);
        TextView tv_ori_price = holder.get(R.id.tv_ori_price);
        TextView tv_sold = holder.get(R.id.tv_sold);

        if (isShow.equals("horizontal"))
        {
            SDViewUtil.show(ll_hor);
            SDViewUtil.hide(ll_ver);
        }
        else if (isShow.equals("vertical"))
        {
            SDViewUtil.hide(ll_hor);
            SDViewUtil.show(ll_ver);
        }

        ViewGroup.LayoutParams para = iv_group2.getLayoutParams();
        para.width = SDViewUtil.getScreenWidth() / 2 - SDViewUtil.dp2px(5f);
        para.height = SDViewUtil.getScreenWidth() / 2 - SDViewUtil.dp2px(5f);
        iv_group2.setLayoutParams(para);

        String f_icon = model.getF_icon();
        String name = model.getName();
        double price = model.getCurrent_price();
        String buy_count = model.getBuy_count();
        double org_price = model.getOrigin_price();
        GlideUtil.load(f_icon).into(iv_group2);
        SDViewBinder.setTextView(tv_des2,name);
        SDViewBinder.setTextView(tv_price2,"￥ " + price);
        tv_ori_price2.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        if (org_price != 0)
            SDViewBinder.setTextView(tv_ori_price2, String.valueOf(org_price));
        if (!TextUtils.isEmpty(buy_count) && !buy_count.equals("0"))
            SDViewBinder.setTextView(tv_sold2,"已售" + buy_count);

        GlideUtil.load(f_icon).into(iv_group);
        SDViewBinder.setTextView(tv_des,name);
        SDViewBinder.setTextView(tv_price,"￥ " + price);
        tv_ori_price.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        if (org_price != 0)
            SDViewBinder.setTextView(tv_ori_price, String.valueOf(org_price));
        if (!TextUtils.isEmpty(buy_count) && !buy_count.equals("0"))
            SDViewBinder.setTextView(tv_sold,"已售" + buy_count);

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
