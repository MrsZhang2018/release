package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.SearchActivity;

/**
 * Created by Administrator on 2017/1/24.
 */

public class CommonTitleSearchView extends SDAppView
{
    private TextView tv_search;

    public CommonTitleSearchView(Context context)
    {
        super(context);
        init();
    }

    public CommonTitleSearchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CommonTitleSearchView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setHintTip(String hintTip)
    {
        SDViewBinder.setTextView(tv_search,hintTip);
        SDViewUtil.setTextViewColorResId(tv_search,R.color.text_content_deep);
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_com_title_search);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);

        tv_search = find(R.id.tv_search);

        tv_search.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
