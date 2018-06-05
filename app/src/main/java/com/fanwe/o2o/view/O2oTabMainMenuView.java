package com.fanwe.o2o.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.o2o.R;


/**
 * Created by Administrator on 2016/7/29.
 */
public class O2oTabMainMenuView extends LinearLayout
{
    public ImageView iv_tab_image;
    public TextView tv_tab;

    public O2oTabMainMenuView(Context context)
    {
        super(context);
        init();
    }

    public O2oTabMainMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public O2oTabMainMenuView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_o2o_tab_main_menu, this, true);

        iv_tab_image = (ImageView) findViewById(R.id.iv_tab_image);
        tv_tab = (TextView) findViewById(R.id.tv_tab);
    }
}
