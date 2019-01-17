package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.adapter.HeadlinesAdapter;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.model.WapIndexArticleModel;
import com.fanwe.o2o.view.HeadlinesView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */

public class HomeHeadlinesView extends SDAppView
{
    private ImageView iv_adv;
    private HeadlinesView view_adv;
    private TextView tv_more;

    private List<WapIndexArticleModel> article;
    private HeadlinesAdapter adapter;

    public HomeHeadlinesView(Context context)
    {
        super(context);
        init();
    }

    public HomeHeadlinesView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public HomeHeadlinesView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<WapIndexArticleModel> article)
    {
        this.article = article;
        bindData();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_home_adv);
        initView();
    }

    private void initView()
    {
        iv_adv = find(R.id.iv_adv);
        view_adv = find(R.id.view_adv);
        tv_more = find(R.id.tv_more);
    }

    private void bindData()
    {
        if(isShowView(article))
        {
            show();
        }else
        {
            hide();
            return;
        }

        initData();
    }

    private boolean isShowView(List<WapIndexArticleModel> list)
    {
        if(list == null || list.size() == 0)
        {
            return false;
        }

        return true;
    }

    private void initData()
    {
        if (adapter != null)
            view_adv.stop();
        if (adapter == null)
            adapter = new HeadlinesAdapter();
        adapter.setData(article);
        view_adv.setAdapter(adapter);
        //开启线程滚动
        view_adv.start();

        adapter.setListener(new HeadlinesAdapter.OnClickListener()
        {
            @Override
            public void onClickWebView(String id)
            {
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=notice&data_id=" + id;
                clickWebView(url);
            }
        });

        tv_more.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=notices";
                clickWebView(url);
            }
        });
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            getActivity().startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }
}
