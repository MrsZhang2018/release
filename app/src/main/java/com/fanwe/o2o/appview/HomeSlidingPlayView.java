package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.HomeTabHotBannerPagerAdapter;
import com.fanwe.o2o.model.WapIndexAdvs2Model;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.List;

/**
 * home首页中间区图片展示
 * Created by Administrator on 2016/12/9.
 */

public class HomeSlidingPlayView extends SDAppView
{
    private SDSlidingPlayView spv_content;
    private HomeTabHotBannerPagerAdapter adapter;

    private List<WapIndexAdvs2Model> listModel;

    public HomeSlidingPlayView(Context context)
    {
        super(context);
        init();
    }

    public HomeSlidingPlayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<WapIndexAdvs2Model> listModel)
    {
        this.listModel = listModel;
        bindData();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_o2o_sliding_play);
        initView();
    }

    private void initView()
    {
        spv_content = find(R.id.spv_content);
    }

    private void bindData()
    {
        if(isShowView(listModel))
        {
            show();
        }else
        {
            hide();
            return;
        }

        initData();
    }

    private boolean isShowView(List<WapIndexAdvs2Model> list)
    {
        if(list == null || list.size() == 0)
        {
            return false;
        }

        return true;
    }

    private void initData()
    {
        spv_content.setNormalImageResId(R.drawable.ic_page_normal);
        spv_content.setSelectedImageResId(R.drawable.ic_page_select);

        adapter = new HomeTabHotBannerPagerAdapter(listModel,getActivity());
        spv_content.setAdapter(adapter);

        setListLiveBannerModel(listModel);

        adapter.setItemClicklistener(new SDAdapter.ItemClickListener<WapIndexAdvs2Model>()
        {
            @Override
            public void onClick(int position, WapIndexAdvs2Model item, View view)
            {
                Intent intent = AppRuntimeWorker.createIntentByType(item.getType(),item.getData().getUrl(),"",0);
                SDActivityUtil.startActivity(getActivity(), intent);
            }
        });
    }

    private void setListLiveBannerModel(List<WapIndexAdvs2Model> listModel)
    {
        adapter.updateData(listModel);

        if (!adapter.getData().isEmpty())
        {
            SDViewUtil.show(spv_content);
            spv_content.startPlay(5 * 1000);
        } else
        {
            SDViewUtil.hide(spv_content);
        }
    }

    @Override
    public void onDestroy()
    {
        spv_content.stopPlay();
        super.onDestroy();
    }
}
