package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.model.ShopIndexIndexsListModel;
import com.fanwe.o2o.utils.GlideUtil;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.List;

public class ShopIndexAdapter extends SDSimpleAdapter<ShopIndexIndexsListModel>
{

	public ShopIndexAdapter(List<ShopIndexIndexsListModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_index;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final ShopIndexIndexsListModel model)
	{
		ImageView ivImage = ViewHolder.get(R.id.item_home_index_iv_image, convertView);
		TextView tvName = ViewHolder.get(R.id.item_home_index_tv_name, convertView);

		SDViewUtil.setViewWidth(ivImage, SDViewUtil.getScreenWidth() / 9);
		SDViewUtil.setViewHeight(ivImage, SDViewUtil.getScreenWidth() / 9);

		SDViewBinder.setTextView(tvName, model.getName());
		GlideUtil.load(model.getImg()).into(ivImage);

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int type = model.getType();
				Intent intent = AppRuntimeWorker.createIntentByType(type, ApkConstant.SERVER_URL_WAP + "?ctl="+model.getCtl(),"",model.getData().getCate_id());
				SDActivityUtil.startActivity(mActivity, intent);
			}
		});
	}

}