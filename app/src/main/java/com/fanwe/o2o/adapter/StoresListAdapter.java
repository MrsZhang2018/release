package com.fanwe.o2o.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.StoreModel;
import com.fanwe.o2o.utils.GlideUtil;

public class StoresListAdapter extends SDSimpleAdapter<StoreModel>
{
	private OnClickStore listener;

	public void setListener(OnClickStore listener)
	{
		this.listener = listener;
	}

	public StoresListAdapter(List<StoreModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_merchant_list;
	}

	@Override
	public void bindData(final int position, final View convertView, ViewGroup parent, final StoreModel model)
	{
		ImageView iv_image = get(R.id.iv_image, convertView);
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_bill = get(R.id.tv_bill, convertView);
		TextView tv_qualify=get(R.id.tv_qualify,convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		RatingBar rb_rating = get(R.id.rb_rating, convertView);
		TextView tv_avg_point = get(R.id.tv_avg_point, convertView);
		TextView tv_no_score = get(R.id.tv_no_score,convertView);
		TextView tv_address = get(R.id.tv_address, convertView);
		TextView tv_store_type = get(R.id.tv_store_type, convertView);
		View view_pre_pay = get(R.id.view_pre_pay, convertView);
		RelativeLayout rl_pre_pay = get(R.id.rl_pre_pay, convertView);
		TextView tv_dis = get(R.id.tv_dis, convertView);

		String is_verify=String.valueOf(model.getIs_verify());
		String open_store_payment = model.getOpen_store_payment();
		float avg_point = model.getAvg_point();

		GlideUtil.load(model.getPreview()).into(iv_image);
		SDViewBinder.setTextView(tv_name, model.getName());
		if (avg_point != 0)
		{
			SDViewUtil.show(rb_rating);
			SDViewUtil.show(tv_avg_point);
			SDViewUtil.hide(tv_no_score);
			SDViewBinder.setRatingBar(rb_rating, avg_point);
			SDViewBinder.setTextView(tv_avg_point, String.valueOf(avg_point));
		}else
		{
			SDViewUtil.hide(rb_rating);
			SDViewUtil.hide(tv_avg_point);
			SDViewUtil.show(tv_no_score);
		}
		SDViewBinder.setTextView(tv_store_type, model.getStore_type());
		SDViewBinder.setTextView(tv_address, model.getQuan_name());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());
		if (open_store_payment.equals("1"))
		{
			SDViewUtil.show(tv_bill);
			SDViewUtil.show(view_pre_pay);
			SDViewUtil.show(rl_pre_pay);
			String promote_info = model.getPromote_info();
			if (!TextUtils.isEmpty(promote_info))
				SDViewBinder.setTextView(tv_dis,promote_info);
		}else
		{
			SDViewUtil.hide(tv_bill);
			SDViewUtil.hide(view_pre_pay);
			SDViewUtil.hide(rl_pre_pay);
		}

		if (is_verify.equals("1"))
		{
			SDViewUtil.show(tv_qualify);
			//SDViewUtil.show(view_pre_pay);
			//SDViewUtil.show(rl_pre_pay);
			//String promote_info = model.getPromote_info();
			//if (!TextUtils.isEmpty(promote_info))
			//	SDViewBinder.setTextView(tv_dis,promote_info);
		}else
		{
			SDViewUtil.hide(tv_qualify);
			//SDViewUtil.hide(view_pre_pay);
			//SDViewUtil.hide(rl_pre_pay);
		}

		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				itemClickListener.onClick(position,model,convertView);
			}
		});

		tv_bill.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				listener.onBill(model);
			}
		});

		rl_pre_pay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				listener.onPurCoupon(model);
			}
		});
	}

	public interface OnClickStore
	{
		void onBill(StoreModel model);
		void onPurCoupon(StoreModel model);
	}
}
