package com.fanwe.o2o.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.event.EKeepListSelected;
import com.fanwe.o2o.event.ERefreshKeepActivity;
import com.fanwe.o2o.fragment.KeepListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-07
 * Time: 11:44
 * 功能:
 */
public class KeepActivity extends BaseTitleActivity {
    private RelativeLayout rl_but;
    private ImageView select; //全选
    private TextView cancel; //取消收藏
    private SDViewPager vpg_content;
    private SDTabUnderline tab_commodity;
    private SDTabUnderline tab_coupon;
    private SDTabUnderline tab_activity;
    private KeepListFragment mFragment;
    private KeepListFragment mCurrentFragment; //当前选中的fragment
    private ERefreshKeepActivity event;
    private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();
    private SparseArray<KeepListFragment> arrFragment = new SparseArray<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setContentView(R.layout.act_keep_list);
        event = new ERefreshKeepActivity();
        initTitle();
        initView();
        initTabs();
        initSDViewPage();
    }

    private void initTitle() {
        title.setMiddleTextTop("我的收藏");
        title.initRightItem(1);
        title.getItemRight(0).setTextBot("编辑");
    }

    private void initView() {
        rl_but = (RelativeLayout) findViewById(R.id.act_keep_but);
        rl_but.bringToFront();
        select = (ImageView) findViewById(R.id.act_keep_select);
        cancel = (TextView) findViewById(R.id.act_keep_cancel);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
        tab_commodity = (SDTabUnderline) findViewById(R.id.tab_keep_commodity);
        tab_coupon = (SDTabUnderline) findViewById(R.id.tab_keep_coupon);
        tab_activity = (SDTabUnderline) findViewById(R.id.tab_keep_activity);
        select.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private boolean t = false; //用于判断右标题状态 true 为编辑状态

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        super.onCLickRight_SDTitleSimple(v, index);
        if (mCurrentFragment.is_null) {
            Toast toast = Toast.makeText(this,"当前没有收藏!!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        t = !t;
        if (t) {
            title.getItemRight(0).setTextBot("完成");
            vpg_content.setLocked(true);
            mCurrentFragment.setIs_select(1);
            selectViewManager.setEnable(false);
            SDViewUtil.show(rl_but);
        } else {
            title.getItemRight(0).setTextBot("编辑");
            mCurrentFragment.setIs_select(0);
            vpg_content.setLocked(false);
            selectViewManager.setEnable(true);
            select.setBackgroundResource(R.drawable.ic_address_default);
            SDViewUtil.hide(rl_but);
        }
    }

    private boolean is_select ; //用于判断全选按钮状态 true为选中

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.act_keep_select:
                is_select = !is_select;
                if (is_select) {
                    mCurrentFragment.selectAll(is_select);
                    select.setBackgroundResource(R.drawable.ic_address_selected);
                } else {
                    mCurrentFragment.selectAll(is_select);
                    select.setBackgroundResource(R.drawable.ic_address_default);
                }
                break;
            case R.id.act_keep_cancel:
                mCurrentFragment.CancelKeepList();
                break;
        }
    }

    public void onEventMainThread(ERefreshKeepActivity event) {
        t=!t;
        title.getItemRight(0).setTextBot("编辑");
        mCurrentFragment.setIs_select(0);
        vpg_content.setLocked(false);
        selectViewManager.setEnable(true);
        select.setBackgroundResource(R.drawable.ic_address_default);
        SDViewUtil.hide(rl_but);
    }

    private void initSDViewPage() {
        vpg_content.setOffscreenPageLimit(2);
        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");
        listModel.add("");
        vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (selectViewManager.getSelectedIndex() != position) {
                    selectViewManager.setSelected(position, true);
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        vpg_content.setAdapter(new LivePagerAdapter(listModel, this, getSupportFragmentManager()));
    }

    private class LivePagerAdapter extends SDFragmentPagerAdapter<String> {

        public LivePagerAdapter(List<String> listModel, Activity activity, FragmentManager fm) {
            super(listModel, activity, fm);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (KeepListFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItemFragment(int position, String model) {
            mFragment = new KeepListFragment();
            switch (position) {
                case 0:
                    mFragment.setKeepStatus(0);
                    break;
                case 1:
                    mFragment.setKeepStatus(1);
                    break;
                case 2:
                    mFragment.setKeepStatus(2);
                    break;
                default:
                    break;
            }
            arrFragment.put(position, mFragment);
            return mFragment;
        }
    }

    private void initTabs() {
        tab_commodity.setTextTitle("商品&团购");
        tab_commodity.getViewConfig(tab_commodity.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_commodity.getViewConfig(tab_commodity.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        tab_coupon.setTextTitle("优惠券");
        tab_coupon.getViewConfig(tab_coupon.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_coupon.getViewConfig(tab_coupon.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        tab_activity.setTextTitle("活动");
        tab_activity.getViewConfig(tab_activity.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_activity.getViewConfig(tab_activity.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        SDTabUnderline[] items = new SDTabUnderline[]{tab_commodity, tab_coupon, tab_activity};
        selectViewManager.setReSelectListener(new SDSelectManager.ReSelectListener<SDTabUnderline>() {
            @Override
            public void onSelected(int index, SDTabUnderline item) {
            }
        });

        selectViewManager.setListener(new SDSelectManager.SDSelectManagerListener<SDTabUnderline>() {

            @Override
            public void onNormal(int index, SDTabUnderline item) {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item) {
                if (t) {
                    return;
                }
                switch (index) {
                    case 0:
                        clickKeepCommodity();
                        break;
                    case 1:
                        clickKeepCoupon();
                        break;
                    case 2:
                        clickKeepActivity();
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }

    protected void clickKeepCommodity() {
        vpg_content.setCurrentItem(0);
    }

    protected void clickKeepCoupon() {
        vpg_content.setCurrentItem(1);
    }

    private void clickKeepActivity() {
        vpg_content.setCurrentItem(2);
    }

    public void onEventMainThread(EKeepListSelected event) {
        if(event.select){
            select.setBackgroundResource(R.drawable.ic_address_selected);
        }else {
            select.setBackgroundResource(R.drawable.ic_address_default);
        }
    }
}
