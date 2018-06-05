package com.fanwe.o2o.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.model.WapIndexArticleModel;
import com.fanwe.o2o.view.HeadlinesView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */

public class HeadlinesAdapter
{
    private List<WapIndexArticleModel> article;
    private OnClickListener listener;

    public void setListener(OnClickListener listener)
    {
        this.listener = listener;
    }

    public void setData(List<WapIndexArticleModel> mDatas)
    {
        this.article = mDatas;
        if (mDatas == null || mDatas.isEmpty())
        {
            throw new RuntimeException("nothing to show");
        }
    }

    /**
     * 获取数据的条数
     * @return
     */
    public int getCount()
    {
        return article == null ? 0 : article.size();
    }

    /**
     * 获取摸个数据
     * @param position
     * @return
     */
    public WapIndexArticleModel getItem(int position)
    {
        return article.get(position);
    }

    /**
     * 获取条目布局
     * @param parent
     * @return
     */
    public View getView(HeadlinesView parent)
    {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_o2o_adv, null);
    }

    /**
     * 条目数据适配
     * @param view
     * @param data
     */
    public void setItem(final View view, final WapIndexArticleModel data)
    {
        TextView tv = (TextView) view.findViewById(R.id.title);
        tv.setText(data.getName());
        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //比如打开url
                listener.onClickWebView(data.getId());
            }
        });
    }

    public interface OnClickListener
    {
        void onClickWebView(String id);
    }
}
