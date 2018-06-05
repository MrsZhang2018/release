package com.fanwe.o2o.dialog;

import android.app.Activity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.LocationFirstAdapter;
import com.fanwe.o2o.appview.LocationHeaderView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.App_CityActModel;
import com.fanwe.o2o.model.CityFirstModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LocationCityDialog extends SDDialogCustom implements SDTitleSimple.SDTitleSimpleListener
{
	private SDTitleSimple title;
	@ViewInject(R.id.lv_content)
	private ListView lv_content;
	private LocationFirstAdapter adapter;
	private List<CityFirstModel> listModel;
	private LocationHeaderView locationHeaderView;
	public LocationCityDialog(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.dialog_location_city);
		setFullScreen();
		x.view().inject(this, getContentView());
		initTitle();
		addHeader();
		setAdapter();
		setData();
	}

	private void setData() {
		CommonInterface.requestCity(new AppRequestCallback<App_CityActModel>() {
			@Override
			protected void onSuccess(SDResponse resp) {
				if (actModel.isOk()) {
					//热门城市
					locationHeaderView.setFlowlayout(actModel.getHot_city());
					//全部城市
					List<CityFirstModel> list = new ArrayList<CityFirstModel>();
					for (String key : actModel.getCity_list().keySet()) {
						CityFirstModel model = new CityFirstModel();
						model.setKey(key);
						model.setValue(actModel.getCity_list().get(key));
						list.add(model);
					}
					SDViewUtil.updateAdapterByList(listModel, list, adapter, false);
				}
			}

			@Override
			protected void onError(SDResponse resp) {
				super.onError(resp);
			}

			@Override
			protected void onFinish(SDResponse resp) {
				super.onFinish(resp);
			}
		});
	}

	private void addHeader() {
		locationHeaderView = new LocationHeaderView(getOwnerActivity(),this);
		lv_content.addHeaderView(locationHeaderView);
	}

	private void setAdapter() {
		listModel = new ArrayList<CityFirstModel>();
		adapter = new LocationFirstAdapter(listModel, getOwnerActivity(),this);
		lv_content.setAdapter(adapter);

		lv_content.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (locationHeaderView != null)
					locationHeaderView.clearPop();
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}
		});
	}

	private void initTitle() {
		title = (SDTitleSimple) findViewById(R.id.title);
		title.setLeftImageLeft(R.drawable.ic_o2o_back);
		title.setmListener(this);
		getTitleCity();
	}

	private void getTitleCity() {
		title.setMiddleTextTop("当前城市-"+ AppRuntimeWorker.getCity_name());
	}

	@Override
	public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
		dismiss();
	}

	@Override
	public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {

	}

	@Override
	public void showTop() {
		super.showTop();
		getTitleCity();
	}

	@Override
	public void dismiss() {

		if (locationHeaderView != null)
			locationHeaderView.clearPop();

		super.dismiss();
	}
}
