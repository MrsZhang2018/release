package com.fanwe.o2o.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.fanwe.library.adapter.http.BuildConfig;
import com.fanwe.o2o.adapter.AddAddressMapAdapter_dc;
import com.fanwe.o2o.adapter.BaiduSuggestionSearchAdapter_dc;
import com.fanwe.o2o.baidumap.BaiduGeoCoder;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.baidumap.BaiduSuggestionSearch;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.appview.InputSuggestionView;
import com.fanwe.o2o.model.BaiduSuggestionSearchModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import org.xutils.view.annotation.ViewInject;

/***
 * 地址地图定位（订餐）
 *
 * @author
 */
public class AddAddressMapActivity extends BaseActivity {
    /***
     * 返回的地址名,以及经纬度值
     */
    public final static String EXTRA_NAME = "extra_name";
    public final static String EXTRA_ADDRESS = "extra_address";
    public final static String EXTRA_LATITUDE = "extra_latitude";
    public final static String EXTRA_LONGTITUDE = "extra_longtitude";

    @ViewInject(R.id.mMapView)
    private MapView mMapView;

    @ViewInject(R.id.iv_location)
    private ImageView mIv_location;

    @ViewInject(R.id.lv_listview)
    private ListView mListview;

    @ViewInject(R.id.iv_center)
    private ImageView mIv_center;

    @ViewInject(R.id.iv_back)
    private ImageView mIv_back;
    // 顶部搜索模块
    @ViewInject(R.id.et_search)
    private ClearEditText mEt_search;
    @ViewInject(R.id.isv_suggestion)
    private InputSuggestionView mIsv_suggestion;

    /***
     * 定位相关
     */
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    private double mLatitude;
    private double mLongtitude;

    /***
     * POI列表相关
     */
    private GeoCoder mSearch;
    List<BaiduSuggestionSearchModel> mBaiduModel = new ArrayList<BaiduSuggestionSearchModel>();
    AddAddressMapAdapter_dc mAdapter;

    /***
     * 顶部关键字搜索
     */
    private BaiduSuggestionSearch mSuggestionSearch = new BaiduSuggestionSearch();
    private BaiduGeoCoder mGeoCoder = new BaiduGeoCoder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.act_add_address_map_dc);

        init();
    }

    private void init() {
        initData();
        initLocation();
        initPoiList();
        initChangeList();
        initInputSuggestionView();
        initBaiduSuggestionSearch();

    }

    /***
     * 顶部关键字搜索模块
     */

    private void initInputSuggestionView() {
        mIsv_suggestion.setViewResId(R.layout.pop_category_dc_single_lv);
        mIsv_suggestion.setListViewId(R.id.lv_content);
        mIsv_suggestion.setEditText(mEt_search);
        mIsv_suggestion.setmListenerTextChanged(new InputSuggestionView.InputSuggestionView_TextChangedListener() {

            @Override
            public void afterTextChanged(Editable s) {
                clickSearch();
            }
        });
        mIsv_suggestion.setmListenerOnItemClick(new InputSuggestionView.InputSuggestionView_OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                BaiduSuggestionSearchModel model = (BaiduSuggestionSearchModel) mIsv_suggestion.getmAdapter().getItem(position);
                clickSuggestion(model);

            }
        });
    }

    /***
     * 获取经纬度以及地址
     *
     * @param model
     */

    protected void clickSuggestion(final BaiduSuggestionSearchModel model) {
        // TODO 将地址解析为经纬度
        mGeoCoder.listener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            }

            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    model.setLatLng(result.getLocation());
                    // TODO 返回信息
                    String name = model.getAddress();
                    String address = model.getAddress();
                    double latitude = Double.valueOf(model.getLatitude());
                    double longitude = Double.valueOf(model.getLongitude());

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_NAME, name);
                    intent.putExtra(EXTRA_ADDRESS, address);
                    intent.putExtra(EXTRA_LATITUDE, latitude);
                    intent.putExtra(EXTRA_LONGTITUDE, longitude);

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    SDToast.showToast("获取经纬度失败");
                }
            }
        }).address(model.getAddress()).geocode();
    }

    /***
     * 搜索城市定位以及关键字
     */

    private void clickSearch() {

        String keyWord = mEt_search.getText().toString().trim();
        if (isEmpty(keyWord)) {
            return;
        }
        String city = AppRuntimeWorker.getCity_name();
        if (isEmpty(city)) {
            return;
        }
        mSuggestionSearch.city(city).keyword(keyWord).search();
    }

    /***
     * 在线建议监听
     */

    private void initBaiduSuggestionSearch() {
        mSuggestionSearch.listener(new OnGetSuggestionResultListener() {

            @Override
            public void onGetSuggestionResult(SuggestionResult result) {
                List<BaiduSuggestionSearchModel> listModel = BaiduSuggestionSearchModel.createList(result);
                BaiduSuggestionSearchAdapter_dc adapter = new BaiduSuggestionSearchAdapter_dc(listModel, mActivity);
                mIsv_suggestion.setAdapter(adapter);
            }
        });
    }

    /***
     * 定位发生改变时的动作
     */

    private void initChangeList() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                LatLng latLng = status.target;
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

            }

            @Override
            public void onMapStatusChange(MapStatus status) {

            }
        });

    }

    /***
     * 定位列表
     */

    private void initPoiList() {
        mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检索到结果
                }
                // 获取地理编码结果

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有找到检索结果
                    SDToast.showToast("没有找到检索结果 !");
                }
                // 获取反向地理编码结果
                List<PoiInfo> poiInfos = result.getPoiList();
                if (poiInfos == null || poiInfos.size() == 0)
                {
                    SDToast.showToast("无法定位您当前的位置信息，请确认已授权app访问位置权限，并开启GPS");
                }
                mBaiduModel.clear();
                for (PoiInfo info : poiInfos) {
                    String name = info.name;
                    String address = info.address;

                    LatLng mLatLng = info.location;
                    double latitude = mLatLng.latitude;
                    double longtitude = mLatLng.longitude;

                    BaiduSuggestionSearchModel mInfoModel = new BaiduSuggestionSearchModel();

                    mInfoModel.setName(name);
                    mInfoModel.setAddress(address);
                    mInfoModel.setLatitude(String.valueOf(latitude));
                    mInfoModel.setLongitude(String.valueOf(longtitude));
                    mBaiduModel.add(mInfoModel);

                }

                mAdapter = new AddAddressMapAdapter_dc(mBaiduModel, AddAddressMapActivity.this);
                mListview.setAdapter(mAdapter);
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        // 默认进入时显示经纬度
        LatLng latLng = BaiduMapManager.getInstance().getLatLngCurrent();
        if (latLng != null) {
            boolean isSucceed=mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
//            SDToast.showToast("反向地理解码是否成功:"+isSucceed);

        }
    }

    private void initData() {
        // 隐藏右下角放大缩小图标以及刻度尺
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        // 设置地图刻度为500m
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

        // 我的位置按钮
        mIv_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                centerToMyLocation();
            }
        });
        mListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BaiduSuggestionSearchModel model = mAdapter.getItem((int) id);

                String name = model.getName();
                String address = model.getAddress();
                double latitude = Double.valueOf(model.getLatitude());
                double longitude = Double.valueOf(model.getLongitude());

                Intent intent = new Intent();
                intent.putExtra(EXTRA_NAME, name);
                intent.putExtra(EXTRA_ADDRESS, address);
                intent.putExtra(EXTRA_LATITUDE, latitude);
                intent.putExtra(EXTRA_LONGTITUDE, longitude);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        mIv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    /***
     * 定位设置
     */

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);

    }

    /**
     * 定位到我的位置
     */
    private void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);

            // 更新经纬度
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();

            if (isFirstIn) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 开始定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // 停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

        try {
            mMapView.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSearch.destroy();

    }

}
