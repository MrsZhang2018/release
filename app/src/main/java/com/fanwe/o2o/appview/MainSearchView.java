package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.MessageCenterActivity;
import com.fanwe.o2o.activity.SearchActivity;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.dialog.LocationCityDialog;
import com.fanwe.o2o.event.EIntentDiscover;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.sunday.eventbus.SDEventManager;


/**
 * 首页搜索View
 * Created by luodong on 2016/12/12.
 */

public class MainSearchView extends SDAppView {
    private TextView tv_city;
    private TextView tv_search;
    private View v_title_bg;
    private LinearLayout ll_msg;
    private ImageView iv_news_tip;

    private String cityName;

    private LocationCityDialog locationCityDialog;
    public MainSearchView(Context context) {
        super(context);
        init();
    }

    public MainSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public View getViewTitleBg() {
        return v_title_bg;
    }

    public void setData(int count)
    {
        if (count != 0)
            SDViewUtil.show(iv_news_tip);
        else
            SDViewUtil.hide(iv_news_tip);
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
        showCityText();
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.view_main_search);
        tv_city = find(R.id.tv_city);
        tv_search = find(R.id.tv_search);
        v_title_bg = find(R.id.v_title_bg);
        ll_msg = find(R.id.ll_msg);
        iv_news_tip = find(R.id.iv_news_tip);
        initCity();
        initSearch();
        locationCity();
    }

    private void initCity() {
        tv_city.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( locationCityDialog == null) {
                    locationCityDialog = new LocationCityDialog(getActivity());
                }
                locationCityDialog.showTop();
            }
        });
    }

    public void showCityText() {
        String city = AppRuntimeWorker.getCity_name();
        if (!TextUtils.isEmpty(city))
            SDViewBinder.setTextView(tv_city,city);
        else
            SDViewBinder.setTextView(tv_city,cityName);
    }

    private void initSearch() {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                getActivity().startActivity(intent);
                SDEventManager.post(new EIntentDiscover());
            }
        });

        ll_msg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppRuntimeWorker.isLogin(getActivity()))
                    clickMsgCenter();
            }
        });
    }

    private void clickMsgCenter()
    {
        Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
        getActivity().startActivity(intent);
    }

    private void locationCity() {
        if (BaiduMapManager.getInstance().hasLocationSuccess()) {
            dealChangeLocation();
        } else {
            BaiduMapManager.getInstance().startLocation(new BDLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation location) {
                    dealChangeLocation();
                }
            });
        }
    }

    public void dealChangeLocation() {
        String defaultCity = AppRuntimeWorker.getCity_name();
        if (TextUtils.isEmpty(defaultCity)) {
            return;
        }
        if (!BaiduMapManager.getInstance().hasLocationSuccess()) {
            return;
        }

        String dist = BaiduMapManager.getInstance().getDistrict();
        String distShort = BaiduMapManager.getInstance().getDistrictShort();
        String city = BaiduMapManager.getInstance().getCity();
        String cityShort = BaiduMapManager.getInstance().getCityShort();

        if (!dealLocation(dist)) {
            if (!dealLocation(distShort)) {
                if (!dealLocation(city)) {
                    if (!dealLocation(cityShort)) {

                    }
                }
            }
        }
    }

    private boolean dealLocation(String locationName) {
        String defaultCity = AppRuntimeWorker.getCity_name();
        int cityId = AppRuntimeWorker.getCityIdByCityName(locationName);
        if (cityId > 0) // 地点存在于城市列表
        {
            if (!locationName.equals(defaultCity)) // 地点不是默认的
            {
                showChangeLocationDialog(locationName);
            }
            return true;
        } else {
            return false;
        }
    }

    private void showChangeLocationDialog(final String location) {
        new SDDialogConfirm(getActivity()).setTextContent("当前定位位置为：" + location + "\n" + "是否切换到" + location + "?").setmListener(new SDDialogCustom.SDDialogCustomListener() {

            @Override
            public void onDismiss(SDDialogCustom dialog) {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                AppRuntimeWorker.setCity_name(location);
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
            }
        }).show();
    }
}
