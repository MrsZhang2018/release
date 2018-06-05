package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.o2o.model.GroupPurIndexIndexsListModel;

import java.util.List;

public class GroupPurIndexPageAdapter extends SDPagerAdapter<List<GroupPurIndexIndexsListModel>>
{

	public GroupPurIndexPageAdapter(List<List<GroupPurIndexIndexsListModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(ViewGroup container, int position)
	{
		final SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setColNumber(5);
		GroupPurIndexAdapter adapter = new GroupPurIndexAdapter(getItemModel(position), mActivity);
		ll.setAdapter(adapter);
		return ll;
	}

}
