package com.fanwe.o2o.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SD2LvCategoryViewHelper.SD2LvCategoryViewHelperAdapterInterface;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.Quan_listModel;

public class CategoryQuanRightAdapter extends SDSimpleAdapter<Quan_listModel> implements SD2LvCategoryViewHelperAdapterInterface
{
	private int mDefaultIndex;

	public CategoryQuanRightAdapter(List<Quan_listModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	public void setmDefaultIndex(int rightIndex)
	{
		mDefaultIndex = rightIndex;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_category_right;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Quan_listModel model)
	{
		TextView tvTitle = ViewHolder.get(R.id.item_category_right_tv_title, convertView);

		SDViewBinder.setTextView(tvTitle, model.getName());
		if (model.isSelect())
		{
//			convertView.setBackgroundResource(R.drawable.choose_item_right);
			SDViewUtil.setTextViewColorResId(tvTitle,R.color.main_color);
		} else
		{
//			convertView.setBackgroundColor(SDResourcesUtil.getColor(R.color.bg_act_fra));
			SDViewUtil.setTextViewColorResId(tvTitle,R.color.text_content_deep);
		}
	}

	@Override
	public void setPositionSelectState(int position, boolean select, boolean notify)
	{
		getItem(position).setSelect(select);
		if (notify)
		{
			notifyDataSetChanged();
		}

	}

	@Override
	public String getTitleNameFromPosition(int position)
	{
		return getItem(position).getName();
	}

	@Override
	public BaseAdapter getAdapter()
	{
		return this;
	}

	@Override
	public Object getSelectModelFromPosition(int position)
	{
		return getItem(position);
	}

	@Override
	public int getTitleIndex()
	{
		return mDefaultIndex;
	}

	@Override
	public Object getRightListModelFromPosition_left(int position)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateRightListModel_right(Object rightListModel)
	{
		List<Quan_listModel> listRight = (List<Quan_listModel>) rightListModel;
		updateData(listRight);
	}

	@Override
	public void setPositionSelectState_left(int positionLeft, int positionRight, boolean select)
	{

	}

}
