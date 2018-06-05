package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.listener.SDItemClickListener;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.GroupPurchaseBottomAdapter;
import com.fanwe.o2o.model.GroupPurRecommendDealCateModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.List;

/**
 * 团购首页底部
 * Created by Administrator on 2016/12/12.
 */

public class GroupPurchaseBottomView extends SDAppView
{
    private TextView tv_bottom;
    private SDRecyclerView recycleView;
    private GroupPurchaseBottomAdapter adapter;
    List<GroupPurRecommendDealCateModel> recommend_deal_cate;

    public GroupPurchaseBottomView(Context context)
    {
        super(context);
        init();
    }

    public GroupPurchaseBottomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GroupPurchaseBottomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<GroupPurRecommendDealCateModel> recommend_deal_cate)
    {
        this.recommend_deal_cate = recommend_deal_cate;
        bindData();
    }

    public void isShow(boolean isShow)
    {
        if (isShow)
            SDViewUtil.show(tv_bottom);
        else
            SDViewUtil.hide(tv_bottom);
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_group_purchase_bottom);
        initView();
    }

    private void initView()
    {
        tv_bottom = find(R.id.tv_bottom);
        recycleView = find(R.id.recycleView);
    }

    private void bindData()
    {
        if(isShowView(recommend_deal_cate))
        {
            show();
        }else
        {
            hide();
            return;
        }

        initData();
    }

    private boolean isShowView(List<GroupPurRecommendDealCateModel> list)
    {
        if(list == null || list.size() == 0)
        {
            return false;
        }

        return true;
    }

    private void initData()
    {
        recycleView.setGridVertical(3);
        adapter = new GroupPurchaseBottomAdapter(getActivity());
        adapter.updateData(recommend_deal_cate);
        recycleView.setAdapter(adapter);

        adapter.setItemClickListener(new SDItemClickListener<GroupPurRecommendDealCateModel>()
        {
            @Override
            public boolean onClick(int position, GroupPurRecommendDealCateModel item, View view)
            {
                int type = item.getType();
                Intent intent = AppRuntimeWorker.createIntentByType(type,"", "", Integer.parseInt(item.getId()));
                SDActivityUtil.startActivity(getActivity(), intent);
                return false;
            }
        });
    }
}
