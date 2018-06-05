package com.fanwe.o2o.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dao.InitActModelDao;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.fragment.ConsumeCouponDistFragment;
import com.fanwe.o2o.fragment.ConsumeCouponGroupFragment;
import com.fanwe.o2o.fragment.ConsumeCouponInPersonFragment;
import com.fanwe.o2o.model.Init_indexActModel;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

/**
 * 消费券列表 Created by Administrator on 2017/3/8.
 */

public class ConsumeCouponActivity extends BaseTitleActivity {
  public static final String EXTRA_ID = "extra_id";
  public static final String EXTRA_COUPON_STATUS = "extra_coupon_status";
  public static final String EXTRA_COUPON_NAME = "extra_coupon_name";
  static int is_open_distribution;//驿站是否开启 1开启 0关闭
  private SDTabUnderline tab_group;
  private SDTabUnderline tab_in_person;
  private SDTabUnderline tab_dist;
  private PagerAdapter pagerAdapter;
  @ViewInject(R.id.vpg_content)
  private SDViewPager vpg_content;
  //private SparseArray<ConsumeCouponGroupFragment> arrFragment = new SparseArray<>();
  private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();
  private MoreTitleDialog titleDialog;

  @Override
  protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    setContentView(R.layout.act_consume_coupon_list);
    initTitle();

    tab_group = (SDTabUnderline) findViewById(R.id.tab_group);
    tab_in_person = (SDTabUnderline) findViewById(R.id.tab_in_person);
    tab_dist = (SDTabUnderline) findViewById(R.id.tab_dist);
    final Init_indexActModel model = InitActModelDao.query();
    is_open_distribution = model.getIs_open_distribution(); //驿站是否开启 1开启 0关闭

    initSDViewPager();
    initTabs();

    setTab();
  }

  private void setTab() {
    int coupon_status = getIntent().getIntExtra(EXTRA_COUPON_STATUS, 0);
    if (coupon_status == 1) {
      selectViewManager.setSelected(1, true);
    } else {
      selectViewManager.setSelected(0, true);
    }
  }

  private void initTitle() {
    final CharSequence c=getIntent().getCharSequenceExtra(EXTRA_COUPON_NAME);
    title.setMiddleTextTop(TextUtils.isEmpty(c)?"消费券":String.valueOf(c));
    title.initRightItem(1);
    title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
  }

  @Override
  public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
    super.onCLickRight_SDTitleSimple(v, index);
    if (titleDialog == null) {
      titleDialog = new MoreTitleDialog(this);
    }
    titleDialog.requestData();
    titleDialog.showTop();
  }

  private void initSDViewPager() {
    vpg_content.setOffscreenPageLimit(2);
    List<String> listModel = new ArrayList<>();
    listModel.add("");
    listModel.add("");
    if (is_open_distribution == 1) {
      listModel.add("");
    }

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
    pagerAdapter = new PagerAdapter(listModel, this, getSupportFragmentManager());
    vpg_content.setAdapter(pagerAdapter);
  }

  private void initTabs() {
    initTab(tab_group, "团购");
    initTab(tab_in_person, "自提");
    ArrayList<SDTabUnderline> items = new ArrayList<>();
    items.add(tab_group);
    items.add(tab_in_person);

    if (is_open_distribution == 1) {
      SDViewUtil.show(tab_dist);
      initTab(tab_dist, "取货");
      items.add(tab_dist);
    } else {
      SDViewUtil.hide(tab_dist);
    }

    selectViewManager.setItems(items);

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
        vpg_content.setCurrentItem(index);
      }
    });
  }

  private void initTab(SDTabUnderline tab, String titile) {
    if (tab == null) {
      return;
    }
    tab.setTextTitle(titile);
    tab.getViewConfig(tab.mIvUnderline)
        .setBackgroundColorNormal(Color.TRANSPARENT)
        .setBackgroundColorSelectedResId(R.color.main_color);
    tab.getViewConfig(tab.mTvTitle)
        .setTextColorNormal(SDResourcesUtil.getColor(R.color.text_content))
        .setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title))
        .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14))
        .setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_14));
  }

  final static class PagerAdapter extends SDFragmentPagerAdapter<String> {
    private List<Fragment> fragments;

    public PagerAdapter(List<String> listModel, Activity activity, FragmentManager fm) {
      super(listModel, activity, fm);
      fragments = new ArrayList<>();
      final ConsumeCouponGroupFragment fragment1 = new ConsumeCouponGroupFragment();
      final ConsumeCouponInPersonFragment fragment2 = new ConsumeCouponInPersonFragment();
      fragments.add(fragment1);
      fragments.add(fragment2);
      if (is_open_distribution == 1) {
        final ConsumeCouponDistFragment fragment3 = new ConsumeCouponDistFragment();
        fragments.add(fragment3);
      }
    }

    @Override
    public Fragment getItemFragment(int position, String model) {
      return fragments.get(position);
    }
  }
}

