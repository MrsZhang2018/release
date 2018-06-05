package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.o2o.model.ShopIndexIndexsListModel;

import java.util.List;

public class ShopIndexPageAdapter extends SDPagerAdapter<List<ShopIndexIndexsListModel>>
{

	public ShopIndexPageAdapter(List<List<ShopIndexIndexsListModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(ViewGroup container, int position)
	{
		final SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setColNumber(5);
		ShopIndexAdapter adapter = new ShopIndexAdapter(getItemModel(position), mActivity);
		ll.setAdapter(adapter);
		return ll;
	}

}
