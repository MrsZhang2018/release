package com.fanwe.o2o.activity;

import android.view.View;

import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.title.SDTitleSimple.SDTitleSimpleListener;
import com.fanwe.o2o.R;

public class BaseTitleActivity extends BaseActivity implements SDTitleSimpleListener
{
    protected SDTitleSimple title;

    @Override
    public void setContentView(View view)
    {
        super.setContentView(view);

        title = (SDTitleSimple) findViewById(R.id.title);
        title.setLeftImageLeft(R.drawable.ic_arrow_left_grey);
        title.setmListener(this);
    }

    @Override
    protected int onCreateTitleViewResId()
    {
        return R.layout.include_title_simple;
    }

    @Override
    public void onCLickLeft_SDTitleSimple(SDTitleItem v)
    {
        finish();
    }

    @Override
    public void onCLickMiddle_SDTitleSimple(SDTitleItem v)
    {

    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {

    }

}
