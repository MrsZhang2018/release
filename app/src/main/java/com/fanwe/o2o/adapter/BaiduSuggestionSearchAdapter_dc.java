package com.fanwe.o2o.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.BaiduSuggestionSearchModel;

public class BaiduSuggestionSearchAdapter_dc extends SDSimpleAdapter<BaiduSuggestionSearchModel>
{

	public BaiduSuggestionSearchAdapter_dc(List<BaiduSuggestionSearchModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_baidu_suggestion_search_dc;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, BaiduSuggestionSearchModel model)
	{
		TextView tv_title = get(R.id.tv_title, convertView);
		TextView tv_content = get(R.id.tv_content, convertView);

		SDViewBinder.setTextView(tv_title, model.getAddress());
		SDViewBinder.setTextView(tv_content, model.getDistrict());
	}

}
