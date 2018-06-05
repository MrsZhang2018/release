package com.fanwe.o2o.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.o2o.model.IndexActIndexsModel;
import com.fanwe.o2o.model.WapIndexIndexsListModel;

public class HomeIndexPageAdapter extends SDPagerAdapter<List<WapIndexIndexsListModel>>
{

	public HomeIndexPageAdapter(List<List<WapIndexIndexsListModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(ViewGroup container, int position)
	{
		final SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setColNumber(5);
		HomeIndexAdapter adapter = new HomeIndexAdapter(getItemModel(position), mActivity);
		ll.setAdapter(adapter);
		return ll;
	}

}
