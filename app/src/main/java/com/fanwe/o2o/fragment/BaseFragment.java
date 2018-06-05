package com.fanwe.o2o.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fanwe.library.event.ELoginEvent;
import com.fanwe.library.fragment.SDBaseFragment;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.title.SDTitleSimple.SDTitleSimpleListener;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.BaseActivity;
import com.fanwe.o2o.activity.MainActivity;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.constant.Constant;
import com.sunday.eventbus.SDBaseEvent;

import org.xutils.x;

public class BaseFragment extends SDBaseFragment implements SDTitleSimpleListener
{

	protected SDTitleSimple mTitle;
	protected static boolean hasJump2Login;

	private Constant.TitleType mTitleType = Constant.TitleType.TITLE_NONE;

	public Constant.TitleType getmTitleType()
	{
		return mTitleType;
	}

	public void setmTitleType(Constant.TitleType mTitleType)
	{
		this.mTitleType = mTitleType;
	}

	@Override
	protected View onCreateTitleView()
	{
		View viewTitle = null;
		switch (getmTitleType())
		{
		case TITLE:
			mTitle = new SDTitleSimple(App.getApplication());
			mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
			mTitle.setmListener(this);
			viewTitle = mTitle;
			break;

		default:
			break;
		}
		return viewTitle;
	}

	protected void showProgressDialog(String msg)
	{
        if (getBaseActivity() != null)
        {
            getBaseActivity().showProgressDialog(msg);
        }
	}

	protected void dismissProgressDialog()
	{
		if (getBaseActivity() != null)
		{
			getBaseActivity().dismissProgressDialog();
		}
	}


//	@Override
//	protected LayoutParams generateTitleViewLayoutParams()
//	{
//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, SDResourcesUtil.getDimensionPixelSize(R.dimen.height_title_bar));
//		return params;
//	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		x.view().inject(this, view);
		init();
		super.onViewCreated(view, savedInstanceState);
	}

	protected void init()
	{

	}

	@Override
	public void onCLickLeft_SDTitleSimple(SDTitleItem v)
	{
		getActivity().finish();
	}

	@Override
	public void onCLickMiddle_SDTitleSimple(SDTitleItem v)
	{

	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{

	}

	@Override
	public void onEvent(SDBaseEvent event)
	{

	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{

	}

	public void onEventMainThread(ELoginEvent var1) {
		//mEventTag = var1.tag;
		//if(mEventTag.equals(tag)) {
		hasJump2Login = var1.hasJump2Login;
		//}
	}

	@Override
	public void onEventBackgroundThread(SDBaseEvent event)
	{

	}

	@Override
	public void onEventAsync(SDBaseEvent event)
	{

	}

	public BaseActivity getBaseActivity()
	{
		return (BaseActivity) getActivity();
	}

	public MainActivity getMainActivity()
	{
		Activity activity = getActivity();
		if (activity != null)
		{
			if (activity instanceof MainActivity)
			{
				return ((MainActivity) activity);
			}
		}
		return null;
	}

	@Override
	protected int onCreateContentView()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
