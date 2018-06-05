package com.fanwe.o2o.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.GoodsBrandListAdapter;
import com.fanwe.o2o.event.EDismissBrandPopWindow;
import com.fanwe.o2o.model.Brand_listModel;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class GoodsBrandPopWindowGridView extends SDAppView
{
    private GridView gridView;
    private TextView tv_no_brand;
    private TextView tv_reset;
    private TextView tv_sure;
    private List<Brand_listModel> list;
    private List<Brand_listModel> items;
    private GoodsBrandListAdapter adapter;

    public GoodsBrandPopWindowGridView(Context context)
    {
        super(context);
        init();
    }

    public GoodsBrandPopWindowGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GoodsBrandPopWindowGridView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<Brand_listModel> list)
    {
        this.list = list;
        initData();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_goods_brand_grid);
        gridView = find(R.id.grid);
        tv_no_brand = find(R.id.tv_no_brand);
        tv_reset = find(R.id.tv_reset);
        tv_sure = find(R.id.tv_sure);

        tv_sure.setOnClickListener(this);
    }

    private void initData()
    {
        if (list != null && list.size() > 0)
        {
            SDViewUtil.show(gridView);
            SDViewUtil.hide(tv_no_brand);
            tv_reset.setOnClickListener(this);

            adapter = new GoodsBrandListAdapter(list, getActivity());
            gridView.setAdapter(adapter);
            adapter.setItemClickListener(new SDAdapter.ItemClickListener<Brand_listModel>()
            {
                @Override
                public void onClick(int position, Brand_listModel item, View view)
                {
                    adapter.getSelectManager().performClick(position);
                }
            });
        }else
        {
            SDViewUtil.hide(gridView);
            SDViewUtil.show(tv_no_brand);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_reset)
        {
            adapter.getSelectManager().clearSelected();
        }else if (v == tv_sure)
        {
            sureBrand();
        }
    }

    private void sureBrand()
    {
        if (list != null && list.size() > 0)
        {
            items = adapter.getSelectManager().getSelectedItems();
            if (items != null && items.size() > 0) {
                SDEventManager.post(new EDismissBrandPopWindow(items));
            }else {
                SDEventManager.post(new EDismissBrandPopWindow("0"));
            }
        }else
        {
            SDEventManager.post(new EDismissBrandPopWindow("0"));
        }
    }
}
