package com.fanwe.o2o.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.o2o.model.InitActStart_pageModel;
import com.fanwe.o2o.R;
import com.fanwe.o2o.utils.GlideUtil;

public class InitAdvsPagerAdapter extends SDPagerAdapter<InitActStart_pageModel>
{

	public InitAdvsPagerAdapter(List<InitActStart_pageModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(ViewGroup container, int position)
	{
		View itemView = mActivity.getLayoutInflater().inflate(R.layout.item_start_page, null);
		ImageView iv = (ImageView) itemView.findViewById(R.id.iv_image);
		InitActStart_pageModel model = getItemModel(position);
		if (model != null)
		{
			GlideUtil.load(model.getImg()).into(iv);
		}
		return itemView;
	}

}
