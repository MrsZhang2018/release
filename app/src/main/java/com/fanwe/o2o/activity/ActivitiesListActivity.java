package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.view.HorizontalScrollViewPageIndicator;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.TabAdapter;
import com.fanwe.o2o.appview.CommonTitleSearchView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.fragment.ActivitiesListFragment;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppActivitesActModel;
import com.fanwe.o2o.model.TabModel;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 活动列表
 * Created by Administrator on 2017/1/22.
 */

public class ActivitiesListActivity extends BaseTitleActivity
{
    /**
     * 关键字搜索
     */
    public static final String EXTRA_KEYWORD = "extra_keyword";

    @ViewInject(R.id.ll_tabs)
    private HorizontalScrollViewPageIndicator ll_tabs;
    @ViewInject(R.id.vpg_content)
    private SDViewPager vpg_content;

    private TabAdapter adapter;
    private boolean isNeedBindTitle = true;
    private MoreTitleDialog titleDialog;
    private String keyWord;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_activites_list);
        getIntentData();
        initTitle();
        requestData();
    }

    private void getIntentData()
    {
        keyWord = getIntent().getStringExtra(EXTRA_KEYWORD);
    }

    private void initTitle()
    {
        CommonTitleSearchView titleSearchView = new CommonTitleSearchView(this);
        if (!TextUtils.isEmpty(keyWord))
            titleSearchView.setHintTip(keyWord);
        else
            titleSearchView.setHintTip("搜索活动关键字");
        title.setCustomViewMiddle(titleSearchView);
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
//        title.setMiddleTextTop("活动列表");
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        super.onCLickRight_SDTitleSimple(v, index);
        if (titleDialog == null)
            titleDialog = new MoreTitleDialog(this);
        titleDialog.requestData();
        titleDialog.showTop();
    }

    private void initHorizontalListView(List<TabModel> bcate_list)
    {
        adapter = new TabAdapter(bcate_list, this);
        adapter.getSelectManager().setSelected(0,true);
        adapter.setTabsClickListener(new TabAdapter.OnActiviteTabsClickListener()
        {
            @Override
            public void clickItem(View view, int position, TabModel model)
            {
                vpg_content.setCurrentItem(position);
            }
        });

        ll_tabs.setAdapter(adapter);
        ll_tabs.setViewPager(vpg_content);
    }

    private void initSDViewPager(List<TabModel> listModel)
    {
        vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                if (adapter.getSelectManager().getSelectedIndex() != position)
                {
                    adapter.getSelectManager().setSelected(position, true);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

        vpg_content.setAdapter(new SDFragmentPagerAdapter<TabModel>(listModel,this, getSupportFragmentManager())
        {
            @Override
            public Fragment getItemFragment(int i, TabModel tabModel)
            {
                ActivitiesListFragment fragment = new ActivitiesListFragment();
                fragment.setTabItem(tabModel.getId(),keyWord);
                return fragment;
            }
        });
    }

    private void requestData()
    {
        showProgressDialog("");
        CommonInterface.requestActivitesWapIndex(1, "0",keyWord, new AppRequestCallback<AppActivitesActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    if (isNeedBindTitle)
                    {
                        List<TabModel> listTab = actModel.getBcate_list();
                        initHorizontalListView(listTab);
                        initSDViewPager(listTab);
                        isNeedBindTitle = false;
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        vpg_content.setCurrentItem(0);
    }
}
