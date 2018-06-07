package com.fanwe.hybrid.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


import cn.fanwe.yi.R;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.dialog.SDProgressDialog;
import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.umeng.UmengPushManager;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.sunday.eventbus.SDBaseEvent;
import com.umeng.socialize.UMShareAPI;

public class BaseActivity extends SDBaseActivity
{
	/** 触摸返回键是否退出App */
	protected boolean mIsExitApp = false;

	protected long mExitTime = 0;

	protected Dialog mDialog;

	protected void showDialog()
	{
		if (mDialog == null)
		{
			mDialog = new SDProgressDialog(this);
			mDialog.show();
		}
	}

	protected void dimissDialog()
	{
		if (mDialog != null)
		{
			mDialog.dismiss();
			mDialog = null;
		}
	}

	/**
	 * 初始化
	 */
	@Override
	protected void baseInit()
	{
		UmengPushManager.getPushAgent().onAppStart();
	}

	@SuppressLint("NewApi")
	@Override
	public void setContentView(int resViewId)
	{
		InitActModel model = InitActModelDao.query();
		if (model != null && !TextUtils.isEmpty(model.getTopnav_color()))
		{
			SDViewUtil.setStatusBarTintColor(this, model.getTopnav_color());
		} else
		{
			SDViewUtil.setStatusBarTintResource(this, R.color.title_color_blue);
		}
	
		View view = LayoutInflater.from(this).inflate(resViewId, null);
		view.setFitsSystemWindows(true);
		super.setContentView(view);
	}

	public void exitApp()
	{
		if (System.currentTimeMillis() - mExitTime > 2000)
		{
			SDToast.showToast("再按一次退出!");
		} else
		{
			App.getApplication().exitApp(false);
		}
		mExitTime = System.currentTimeMillis();
	}

	/**
	 * 返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (mIsExitApp)
			{
				exitApp();
			} else
			{
				finish();
			}
		}
		return true;
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case EVENT_EXIT_APP:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onEvent(SDBaseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventBackgroundThread(SDBaseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventAsync(SDBaseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

}
