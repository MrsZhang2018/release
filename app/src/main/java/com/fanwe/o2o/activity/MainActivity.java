package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDCache;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.common.DbManagerX;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.dao.CityListModelDao;
import com.fanwe.o2o.event.EIntentAppMain;
import com.fanwe.o2o.event.EIntentClassIfy;
import com.fanwe.o2o.event.EIntentDiscover;
import com.fanwe.o2o.event.EIntentShopCart;
import com.fanwe.o2o.event.EIntentUserCenter;
import com.fanwe.o2o.fragment.ClassIfyFragment;
import com.fanwe.o2o.fragment.DiscoverFragment;
import com.fanwe.o2o.fragment.HomeFragment;
import com.fanwe.o2o.fragment.MeFragment;
import com.fanwe.o2o.fragment.MeFragmentNew;
import com.fanwe.o2o.fragment.ShopCartFragment;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.App_RegionListActModel;
import com.fanwe.o2o.model.CityListModel;
import com.fanwe.o2o.model.RegionModel;
import com.fanwe.o2o.utils.AppUpgradeHelper;
import com.fanwe.o2o.view.O2oTabMainMenuView;
import com.fanwe.o2o.work.AppRuntimeWorker;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Iterator;

import static com.fanwe.o2o.work.AppRuntimeWorker.HASCITYLIST;

public class MainActivity extends BaseActivity {
    public static final String EXTRA_TAB = "extra_tab";
    private O2oTabMainMenuView view_tab_home;//首页
    private O2oTabMainMenuView view_tab_classify;//分类
    private O2oTabMainMenuView view_tab_dis;//发现
    private O2oTabMainMenuView view_tab_car;//订单
    private O2oTabMainMenuView view_tab_me;//我的
    private int tab;

    private SDSelectViewManager<O2oTabMainMenuView> selectViewManager = new SDSelectViewManager<O2oTabMainMenuView>();

    @Override
    protected int onCreateContentView() {
        return R.layout.act_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mIsExitApp = true;
        getIntentData();
        initView();
        initData();
        if (!SDConfig.getInstance().getBoolean(HASCITYLIST, false)) {
            checkRegionVersion();
        }
        new AppUpgradeHelper(this).check(0);
    }

    private void getIntentData() {
        tab = getIntent().getIntExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.APP_MAIN);
    }

    private void initView() {
        view_tab_home = find(R.id.view_tab_home);
        view_tab_classify = find(R.id.view_tab_classify);
        view_tab_dis = find(R.id.view_tab_dis);
        view_tab_car = find(R.id.view_tab_car);
        view_tab_me = find(R.id.view_tab_me);
    }

    private void initData() {

        selectViewManager.setReSelectListener(new SDSelectManager.ReSelectListener<O2oTabMainMenuView>() {
            @Override
            public void onSelected(int index, O2oTabMainMenuView item) {

            }
        });
        selectViewManager.setListener(new SDSelectViewManager.SDSelectManagerListener<O2oTabMainMenuView>() {
            @Override
            public void onNormal(int index, O2oTabMainMenuView item) {
                switch (index) {
                    case 0:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_home_default);
                        SDViewBinder.setTextView(item.tv_tab, "首页");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.text_content);
                        break;
                    case 1:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_classification_default);
                        SDViewBinder.setTextView(item.tv_tab, "分类");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.text_content);
                        break;
                    case 2:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_discovery_default);
                        SDViewBinder.setTextView(item.tv_tab, "发现");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.text_content);
                        break;
                    case 3:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_order_default);
                        SDViewBinder.setTextView(item.tv_tab, "订单");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.text_content);
                        break;
                    case 4:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_mine_default);
                        SDViewBinder.setTextView(item.tv_tab, "我的");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.text_content);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSelected(int index, O2oTabMainMenuView item) {
                switch (index) {
                    case 0:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_home_selected);
                        SDViewBinder.setTextView(item.tv_tab, "首页");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.main_color);
                        clickTabHome();
                        break;
                    case 1:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_classification_selected);
                        SDViewBinder.setTextView(item.tv_tab, "分类");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.main_color);
                        clickTabClassift();
                        break;
                    case 2:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_discovery_selected);
                        SDViewBinder.setTextView(item.tv_tab, "发现");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.main_color);
                        clickTabDis();
                        break;
                    case 3:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_order_selected);
                        SDViewBinder.setTextView(item.tv_tab, "订单");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.main_color);
                        clickTabCar();
                        break;
                    case 4:
                        item.iv_tab_image.setImageResource(R.mipmap.tabbar_icon_mine_selected);
                        SDViewBinder.setTextView(item.tv_tab, "我的");
                        SDViewUtil.setTextViewColorResId(item.tv_tab, R.color.main_color);
                        clickTabMe();
                        break;
                    default:
                        break;
                }
            }
        });

        O2oTabMainMenuView[] items = new O2oTabMainMenuView[]{view_tab_home, view_tab_classify, view_tab_dis, view_tab_car, view_tab_me};
        selectViewManager.setItems(items);
        selectViewManager.performClick(tab);
    }

    /**
     * 检查地区版本、更新保存数据
     */
    private void checkRegionVersion() {
//        App_RegionListActModel regionActModel = AppRuntimeWorker.getRegionListActModel();
//        if (regionActModel == null)
//        {
        CommonInterface.requestDeliveryRegion(new AppRequestCallback<App_RegionListActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    SDHandlerManager.getBackgroundHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            SDConfig.getInstance().setInt(R.string.config_region_version, actModel.getRegion_versions());
                            initCityData(actModel.getRegion_list());
                        }
                    });

                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }
        });
//        }
//        else
//        {
//            handleCityData(regionActModel.getRegion_list());
//        }
    }

    /**
     * 初始化城市数据
     *
     * @param regionModelArrayList
     */
    private void initCityData(ArrayList<RegionModel> regionModelArrayList) {

        final ArrayList<RegionModel> mListProvince = new ArrayList<>();//省份集合
        final ArrayList<ArrayList<RegionModel>> mListCity = new ArrayList<>();//城市集合
        final ArrayList<ArrayList<ArrayList<RegionModel>>> mListCounty = new ArrayList<>();//区集合

        final ArrayList<RegionModel> provinces = new ArrayList<>();
        final ArrayList<RegionModel> cities = new ArrayList<>();
        final ArrayList<RegionModel> counties = new ArrayList<>();

        Iterator<RegionModel> it = regionModelArrayList.iterator();
        RegionModel item;
        while (it.hasNext()) {
            item = it.next();
            if (item.getRegion_level() == 2) {
                mListProvince.add(item);
                provinces.add(item);
            } else if (item.getRegion_level() == 3) {
                cities.add(item);
            } else if (item.getRegion_level() == 4) {
                counties.add(item);
            }
//            it.remove();
        }


        if (provinces != null) {
            RegionModel city;

            for (RegionModel model : provinces) {
                ArrayList<RegionModel> provice = new ArrayList<>();
                Iterator<RegionModel> it1 = cities.iterator();
                while ((it1.hasNext())) {
                    city = it1.next();
                    if (city.getPid() == model.getId()) {
                        provice.add(city);
                        it1.remove();
                    }
                }
                mListCity.add(provice); //省-市
            }
        }

        if (mListCity != null) {
            RegionModel county;
            for (ArrayList<RegionModel> model : mListCity) {
                ArrayList<ArrayList<RegionModel>> cities_counties = new ArrayList<>();
                for (RegionModel model1 : model) {
                    ArrayList<RegionModel> city_counties = new ArrayList<>();
                    Iterator<RegionModel> it1 = counties.iterator();
                    while ((it1.hasNext())) {
                        county = it1.next();
                        if (county.getPid() == model1.getId()) {
                            city_counties.add(county);
                            it1.remove();
                        }
                    }
                    cities_counties.add(city_counties);
                }
                mListCounty.add(cities_counties);
            }
        }

        CityListModel model = new CityListModel();
        model.setProvinces(mListProvince);
        model.setCities(mListCity);
        model.setCounties(mListCounty);
        CityListModelDao.insertOrUpdate(model);
        SDConfig.getInstance().setBoolean(HASCITYLIST, true);

    }

    private void clickTabHome() {
        getSDFragmentManager().toggle(R.id.fl_main_content, null, HomeFragment.class);
    }

    private void clickTabClassift() {
        getSDFragmentManager().toggle(R.id.fl_main_content, null, ClassIfyFragment.class);
    }

    private void clickTabDis() {
//        Intent intent = new Intent(this, SearchActivity.class);
//        startActivity(intent);
//        selectViewManager.selectLastIndex();
        getSDFragmentManager().toggle(R.id.fl_main_content, null, DiscoverFragment.class);
    }

    private void clickTabCar() {
//        getSDFragmentManager().toggle(R.id.fl_main_content, null, ShopCartFragment.class);//源生
        intentWebviewActivity("cart");//wap
        selectViewManager.selectLastIndex();
    }

    private void clickTabMe() {
        getSDFragmentManager().toggle(R.id.fl_main_content, null, MeFragmentNew.class);//源生
//        intentWebviewActivity("user_center");//wap
//        finish();
    }

    private void intentWebviewActivity(String ctlString) {
        Intent intent = new Intent(App.getApplication(), AppWebViewActivity.class);//wap
        String url = ApkConstant.SERVER_URL_WAP;
        UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
        urlBuilder.add("ctl", ctlString);
        intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE, false);
        intent.putExtra(AppWebViewActivity.EXTRA_URL, urlBuilder.build());
        startActivity(intent);
        overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
    }

    public void onEventMainThread(EIntentAppMain event) {
        selectViewManager.performClick(0);
    }

    public void onEventMainThread(EIntentClassIfy event) {
        selectViewManager.performClick(1);
    }

    public void onEventMainThread(EIntentDiscover event) {
        selectViewManager.performClick(2);
    }

    public void onEventMainThread(EIntentShopCart event) {
        selectViewManager.performClick(3);
    }

    public void onEventMainThread(EIntentUserCenter event) {
        selectViewManager.performClick(4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        postOnActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
