package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.constant.ApkConstant;

import org.xutils.view.annotation.ViewInject;

import java.util.Locale;

/**
 * 购物车
 * Created by Administrator on 2016/12/8.
 */

public class ClassIfyFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.index_1)
    ImageView imageView1;
    @ViewInject(R.id.index_2)
    ImageView imageView2;
    @ViewInject(R.id.index_3)
    ImageView imageView3;
    @ViewInject(R.id.index_4)
    ImageView imageView4;
    @ViewInject(R.id.index_5)
    ImageView imageView5;
    @ViewInject(R.id.index_6)
    ImageView imageView6;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_o2o_tab_calssify;
    }

    @Override
    protected void init() {
        super.init();
        initData();
    }

    private void initData() {
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String url = ApkConstant.SERVER_URL_WAP + "?ctl=stores&cid=%1d";
        switch (v.getId()) {
            case R.id.index_1:
                clickWebView(String.format(Locale.getDefault(), url, 1));
                break;
            case R.id.index_2:
                clickWebView(String.format(Locale.getDefault(), url, 2));
                break;
            case R.id.index_3:
                clickWebView(String.format(Locale.getDefault(), url, 3));
                break;
            case R.id.index_4:
                clickWebView(String.format(Locale.getDefault(), url, 4));
                break;
            case R.id.index_5:
                clickWebView(String.format(Locale.getDefault(), url, 5));
                break;
            case R.id.index_6:
                clickWebView(String.format(Locale.getDefault(), url, 6));
                break;
        }
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
