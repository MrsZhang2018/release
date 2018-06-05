package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDAbsListViewScrollListener;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.LocationFirstAdapter;
import com.fanwe.o2o.appview.LocationHeaderView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.App_CityActModel;
import com.fanwe.o2o.model.CityModel;
import com.fanwe.o2o.model.CityFirstModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位城市
 * Created by luodong on 2016/12/12
 */
public class LocationCityActivity extends BaseTitleActivity {
//
//    @ViewInject(R.id.lv_content)
//    private ListView lv_content;
//    private LocationFirstAdapter adapter;
//    private List<CityFirstModel> listModel;
//    private LocationHeaderView locationHeaderView;
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//        setContentView(R.layout.dialog_location_city);
//        initTitle();
//        addHeader();
//        setAdapter();
//        setData();
//    }
//
//    private void setData() {
//        CommonInterface.requestCity(new AppRequestCallback<App_CityActModel>() {
//            @Override
//            protected void onSuccess(SDResponse resp) {
//                if (actModel.isOk()) {
//                    //热门城市
//                    locationHeaderView.setFlowlayout(actModel.getHot_city());
//                    //全部城市
//                    List<CityFirstModel> list = new ArrayList<CityFirstModel>();
//                    for (String key : actModel.getCity_list().keySet()) {
//                        CityFirstModel model = new CityFirstModel();
//                        model.setKey(key);
//                        model.setValue(actModel.getCity_list().get(key));
//                        list.add(model);
//                    }
//                    SDViewUtil.updateAdapterByList(listModel, list, adapter, false);
//                }
//            }
//
//            @Override
//            protected void onError(SDResponse resp) {
//                super.onError(resp);
//            }
//
//            @Override
//            protected void onFinish(SDResponse resp) {
//                super.onFinish(resp);
//            }
//        });
//    }
//
//    private void addHeader() {
//        locationHeaderView = new LocationHeaderView(this);
//        lv_content.addHeaderView(locationHeaderView);
//    }
//
//
//    private void setAdapter() {
//        listModel = new ArrayList<CityFirstModel>();
//        adapter = new LocationFirstAdapter(listModel, this);
//        lv_content.setAdapter(adapter);
//
//        lv_content.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                SDViewUtil.hideInputMethod(view);
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
//    }
//
//
//    private void initTitle() {
//        title.setMiddleTextBot("当前城市-"+ AppRuntimeWorker.getCity_name());
//    }

}
