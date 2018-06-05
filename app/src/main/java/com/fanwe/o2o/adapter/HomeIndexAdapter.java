package com.fanwe.o2o.adapter;

import com.fanwe.o2o.model.AdvsDataModel;
import java.util.List;

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
import com.fanwe.o2o.model.WapIndexIndexsListModel;
import com.fanwe.o2o.utils.GlideUtil;
import com.fanwe.o2o.work.AppRuntimeWorker;

public class HomeIndexAdapter extends SDSimpleAdapter<WapIndexIndexsListModel>
{

	public HomeIndexAdapter(List<WapIndexIndexsListModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_home_index;
	}

	@Override
	public void bindData(final int position, View convertView, ViewGroup parent, final WapIndexIndexsListModel model)
	{
		ImageView ivImage = ViewHolder.get(R.id.item_home_index_iv_image, convertView);
		TextView tvName = ViewHolder.get(R.id.item_home_index_tv_name, convertView);

		SDViewUtil.setViewWidth(ivImage, SDViewUtil.getScreenWidth() / 9);
		SDViewUtil.setViewHeight(ivImage, SDViewUtil.getScreenWidth() / 9);

		SDViewBinder.setTextView(tvName, model.getName());
		GlideUtil.loadHeadImage(model.getImg()).into(ivImage);

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int type = model.getType();

				AdvsDataModel dataModel=model.getData();
				int data_id=dataModel.getData_id();
				int cata_id=dataModel.getCate_id();
                int id=data_id==0?cata_id:data_id;
				Intent intent;

                intent = AppRuntimeWorker.createIntentByType(type, ApkConstant.SERVER_URL_WAP + "?ctl="+model.getCtl()+"&data_id="+id,"",id);

				SDActivityUtil.startActivity(mActivity, intent);
			}
		});
	}

}