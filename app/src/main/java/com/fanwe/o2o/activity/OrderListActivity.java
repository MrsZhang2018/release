package com.fanwe.o2o.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.EIntentUserCenter;
import com.fanwe.o2o.fragment.OrderListFragment;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单页面
 * Created by luod on 2017/3/2.
 */

public class OrderListActivity extends BaseTitleActivity
{
    public static final String EXTRA_ORDER_TYPE = "extra_order_type";

    private SDTabUnderline tab_order_all;
    private SDTabUnderline tab_order_wait_payment;
    private SDTabUnderline tab_order_wait_send;
    private SDTabUnderline tab_order_wait_confirm;
    private SDTabUnderline tab_order_wait_evaluate;

    @ViewInject(R.id.vpg_content)
    private SDViewPager vpg_content;
    private SparseArray<OrderListFragment> arrFragment = new SparseArray<>();
    private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();
    private MoreTitleDialog titleDialog;

    private int order_type;
    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_order_list);
        getIntentData();
        initTitle();
        initView();
        initSDViewPager();
        initTabs();

        setTab();
    }

    private void getIntentData() {
        order_type = getIntent().getIntExtra(OrderListActivity.EXTRA_ORDER_TYPE,0);
    }

    private void initView() {
        tab_order_all = (SDTabUnderline) findViewById(R.id.tab_order_all);
        tab_order_wait_payment = (SDTabUnderline) findViewById(R.id.tab_order_wait_payment);
        tab_order_wait_send = (SDTabUnderline) findViewById(R.id.tab_order_wait_send);
        tab_order_wait_confirm = (SDTabUnderline) findViewById(R.id.tab_order_wait_confirm);
        tab_order_wait_evaluate = (SDTabUnderline) findViewById(R.id.tab_order_wait_evaluate);
    }

    private void initTitle() {
        title.setMiddleTextTop("我的订单");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
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


    private void initSDViewPager()
    {
        vpg_content.setOffscreenPageLimit(2);
        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");
        listModel.add("");
        listModel.add("");
        listModel.add("");

        vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                if (selectViewManager.getSelectedIndex() != position)
                {
                    selectViewManager.setSelected(position, true);
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
        vpg_content.setAdapter(new LivePagerAdapter(listModel, this, getSupportFragmentManager()));

    }

    private class LivePagerAdapter extends SDFragmentPagerAdapter<String>
    {

        public LivePagerAdapter(List<String> listModel, Activity activity, FragmentManager fm)
        {
            super(listModel, activity, fm);
        }

        @Override
        public Fragment getItemFragment(int position, String model)
        {
            OrderListFragment fragment = new OrderListFragment();
            switch (position)
            {
                case 0:
                    fragment.setPayStatus(0);
                    break;
                case 1:
                    fragment.setPayStatus(1);
                    break;
                case 2:
                    fragment.setPayStatus(2);
                    break;
                case 3:
                    fragment.setPayStatus(3);
                    break;
                case 4:
                    fragment.setPayStatus(4);
                    break;
                default:
                    break;
            }
            arrFragment.put(position, fragment);
            return fragment;
        }
    }

    private void initTabs()
    {
        tab_order_all.setTextTitle("全部");
        tab_order_all.getViewConfig(tab_order_all.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_order_all.getViewConfig(tab_order_all.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        tab_order_wait_payment.setTextTitle("待付款");
        tab_order_wait_payment.getViewConfig(tab_order_wait_payment.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_order_wait_payment.getViewConfig(tab_order_wait_payment.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        tab_order_wait_send.setTextTitle("待发货");
        tab_order_wait_send.getViewConfig(tab_order_wait_send.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_order_wait_send.getViewConfig(tab_order_wait_send.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        tab_order_wait_confirm.setTextTitle("待确认");
        tab_order_wait_confirm.getViewConfig(tab_order_wait_confirm.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_order_wait_confirm.getViewConfig(tab_order_wait_confirm.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        tab_order_wait_evaluate.setTextTitle("待评价");
        tab_order_wait_evaluate.getViewConfig(tab_order_wait_evaluate.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);
        tab_order_wait_evaluate.getViewConfig(tab_order_wait_evaluate.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));

        SDTabUnderline[] items = new SDTabUnderline[]{tab_order_all, tab_order_wait_payment,tab_order_wait_send,tab_order_wait_confirm,tab_order_wait_evaluate};

        selectViewManager.setReSelectListener(new SDSelectManager.ReSelectListener<SDTabUnderline>()
        {
            @Override
            public void onSelected(int index, SDTabUnderline item)
            {
            }
        });

        selectViewManager.setListener(new SDSelectManager.SDSelectManagerListener<SDTabUnderline>()
        {

            @Override
            public void onNormal(int index, SDTabUnderline item)
            {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item)
            {
                switch (index)
                {
                    case 0:
                        clickOrderAll();
                        break;
                    case 1:
                        clickOrderWaitPayment();
                        break;
                    case 2:
                        clickOrderWaitSend();
                        break;
                    case 3:
                        clickOrderWaitConfirm();
                        break;
                    case 4:
                        clickOrderWaitEvaluate();
                        break;

                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
    }

    public void setOrder_type(int order_type){
        this.order_type=order_type;
        setTab();
    }

    private void setTab() {
        switch (order_type){
            case 0:
                selectViewManager.setSelected(0, true);
                break;
            case 1:
                selectViewManager.setSelected(1, true);
                break;
            case 2:
                selectViewManager.setSelected(2, true);
                break;
            case 3:
                selectViewManager.setSelected(3, true);
                break;
            case 4:
                selectViewManager.setSelected(4, true);
                break;
            default:
                selectViewManager.setSelected(0, true);
                break;
        }
    }

    protected void clickOrderAll()
    {
        vpg_content.setCurrentItem(0);
    }

    protected void clickOrderWaitPayment()
    {
        vpg_content.setCurrentItem(1);
    }

    private void clickOrderWaitSend() {
        vpg_content.setCurrentItem(2);
    }

    private void clickOrderWaitConfirm() {
        vpg_content.setCurrentItem(3);
    }

    private void clickOrderWaitEvaluate() {
        vpg_content.setCurrentItem(4);
    }

    @Override
    public void finish()
    {
        super.finish();
        if (SDActivityManager.getInstance().getLastActivity()instanceof OrderListActivity){
            SDActivityManager.getInstance().getLastActivity().finish();
        }
        SDEventManager.post(new EIntentUserCenter());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
