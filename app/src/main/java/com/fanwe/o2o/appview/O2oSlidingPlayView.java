package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.adapter.O2oTabHotBannerPagerAdapter;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.model.WapIndexAdvsDataModel;
import com.fanwe.o2o.model.WapIndexAdvsModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.List;

/**
 * 头部图片展示
 * Created by Administrator on 2016/12/9.
 */

public class O2oSlidingPlayView extends SDAppView
{
    private SDSlidingPlayView spv_content;
    private O2oTabHotBannerPagerAdapter adapter;

    private List<WapIndexAdvsModel> listModel;

    public O2oSlidingPlayView(Context context)
    {
        super(context);
        init();
    }

    public O2oSlidingPlayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<WapIndexAdvsModel> listModel)
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

    private boolean isShowView(List<WapIndexAdvsModel> list)
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

        adapter = new O2oTabHotBannerPagerAdapter(listModel,getActivity());
        spv_content.setAdapter(adapter);

        setListLiveBannerModel(listModel);

        adapter.setItemClicklistener(new SDAdapter.ItemClickListener<WapIndexAdvsModel>()
        {
            @Override
            public void onClick(int position, WapIndexAdvsModel item, View view)
            {
                WapIndexAdvsDataModel dataModel = item.getData();
                int data_id=0;
                if (dataModel != null)
                {
                    data_id = dataModel.getData_id();
                }
//                Intent intent = AppRuntimeWorker.createIntentByType(item.getType(),item.getData().getUrl(),"",0);
                Intent intent = AppRuntimeWorker.createIntentByType(item.getType(), ApkConstant.SERVER_URL_WAP + "?ctl="+item.getCtl()+"&data_id="+data_id,"",data_id);
                SDActivityUtil.startActivity(getActivity(), intent);
            }
        });
    }

    private void setListLiveBannerModel(List<WapIndexAdvsModel> listModel)
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
