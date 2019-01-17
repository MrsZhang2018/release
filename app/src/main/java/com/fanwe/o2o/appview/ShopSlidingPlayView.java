package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.adapter.ShopHotBannerPagerAdapter;
import com.fanwe.o2o.model.ShopIndexAdvsModel;

import java.util.List;

/**
 * 商城头部图片展示
 * Created by Administrator on 2016/12/9.
 */

public class ShopSlidingPlayView extends SDAppView
{
    private SDSlidingPlayView spv_content;
    private ShopHotBannerPagerAdapter adapter;

    private List<ShopIndexAdvsModel> listModel;

    public ShopSlidingPlayView(Context context)
    {
        super(context);
        init();
    }

    public ShopSlidingPlayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<ShopIndexAdvsModel> listModel)
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

    private boolean isShowView(List<ShopIndexAdvsModel> list)
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

        adapter = new ShopHotBannerPagerAdapter(listModel,getActivity());
        spv_content.setAdapter(adapter);

        setListLiveBannerModel(listModel);

        adapter.setItemClicklistener(new SDAdapter.ItemClickListener<ShopIndexAdvsModel>()
        {
            @Override
            public void onClick(int position, ShopIndexAdvsModel item, View view)
            {
                clickToWebView(item.getData().getUrl());
            }
        });
    }

    private void clickToWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            getActivity().startActivity(intent);
        }
        else
            SDToast.showToast("url为空");
    }

    private void setListLiveBannerModel(List<ShopIndexAdvsModel> listModel)
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
