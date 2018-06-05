package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.adapter.O2oGoodStoresAdapter;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.model.WapIndexSupplierListModel;
import java.util.List;

/**
 * 好店推荐
 * Created by Administrator on 2016/12/8.
 */

public class O2oGoodStoreRecommendView extends SDAppView
{
    private TextView tv_more;
    private LinearLayout ll_layout_store;
    private List<WapIndexSupplierListModel> supplier_list;
    private O2oGoodStoresAdapter adapter;

    public O2oGoodStoreRecommendView(Context context)
    {
        super(context);
        init();
    }

    public O2oGoodStoreRecommendView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public O2oGoodStoreRecommendView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<WapIndexSupplierListModel> supplier_list)
    {
        this.supplier_list = supplier_list;
        bindData();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_o2o_good_store_recommend);
        initView();
    }

    private void initView()
    {
        tv_more = find(R.id.tv_more);
        ll_layout_store = find(R.id.ll_layout_store);
    }

    private void bindData()
    {
        if(isShowView(supplier_list))
        {
            show();
        }else
        {
            hide();
            return;
        }

        initData();
    }

    private boolean isShowView(List<WapIndexSupplierListModel> list)
    {
        if(list == null || list.size() == 0)
        {
            return false;
        }

        return true;
    }

    private void initData()
    {
        adapter = new O2oGoodStoresAdapter(supplier_list,getActivity());
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<WapIndexSupplierListModel>()
        {
            @Override
            public void onClick(int position, WapIndexSupplierListModel item, View view)
            {
                clickWebView(item.getApp_url());
            }
        });
        getAllStore();

        tv_more.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=stores";
                clickWebView(url);
            }
        });
    }

    private void getAllStore()
    {
        ll_layout_store.removeAllViews();
        for (int i = 0; i < supplier_list.size(); i++)
        {
            View view = adapter.getView(i, null, ll_layout_store);
            if (view != null)
            {
                ll_layout_store.addView(view);
            }
        }
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            getActivity().startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }
}
