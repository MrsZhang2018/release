package com.fanwe.o2o.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.HomeIndexPageAdapter;
import com.fanwe.o2o.adapter.ShopIndexPageAdapter;
import com.fanwe.o2o.model.ShopIndexIndexsListModel;
import com.fanwe.o2o.model.WapIndexIndexsListModel;

import java.util.List;

/**
 * 商城首页导航分类view
 * Created by Administrator on 2016/12/8.
 */

public class ShopIndexPageView extends SDAppView
{
    private SDSlidingPlayView mSpvAd;
    private List<ShopIndexIndexsListModel> list;
    private ShopIndexPageAdapter mAdapter;

    public ShopIndexPageView(Context context)
    {
        super(context);
        init();
    }

    public ShopIndexPageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ShopIndexPageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setmIndexModel(List<ShopIndexIndexsListModel> list)
    {
        this.list = list;
        bindData();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_index_page);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        initSlidingPlayView();
    }

    private void initSlidingPlayView()
    {
        mSpvAd = find(R.id.spv_content);
        mSpvAd.setNormalImageResId(R.drawable.ic_page_normal);
        mSpvAd.setSelectedImageResId(R.drawable.ic_page_select);
    }


    private void bindData()
    {
        if(isShowView(list)){
            show();
        }else {
            hide();
            return;
        }

        if (list.size() > 10)
        {
            SDViewUtil.setViewMarginBottom(mSpvAd.getViewPager(), SDViewUtil.dp2px(10));
        }

        mAdapter = new ShopIndexPageAdapter(SDCollectionUtil.splitList(list, 10), getActivity());
        mSpvAd.setAdapter(mAdapter);
    }

    private boolean isShowView(List<ShopIndexIndexsListModel> list) {
        if(list==null){
            return false;
        }

        if(list.size()==0){
            return false;
        }

        return true;
    }

}
