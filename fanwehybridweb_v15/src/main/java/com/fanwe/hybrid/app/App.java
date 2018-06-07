package com.fanwe.hybrid.app;

import android.app.Application;
import android.text.TextUtils;

import com.fanwe.gesture.customview.LockPatternView.Cell;
import com.fanwe.gesture.utils.LockPatternUtils;
import com.fanwe.hybrid.common.ImageLoaderManager;
import com.fanwe.hybrid.dao.LoginSuccessModelDao;
import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.hybrid.model.LoginSuccessModel;
import com.fanwe.hybrid.umeng.UmengPushManager;
import com.fanwe.library.SDLibrary;
import com.fanwe.library.common.SDActivityManager;
import com.fanwei.jubaosdk.shell.FWPay;
import com.sunday.eventbus.SDBaseEvent;

import org.xutils.x;

import java.util.List;

import de.greenrobot.event.EventBus;

public class App extends Application
{
	private static App mInstance;
	public LockPatternUtils mLockPatternUtils;

	public static App getApplication()
	{
		return mInstance;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		init();
	}

	private void init()
	{
		mInstance = this;
		FWPay.initialize(this, true);
		ImageLoaderManager.initImageLoader();
		x.Ext.init(this);
		initSDLibrary();
		initLockConfig();
		initUmengPush();
		SDTencentMapManager.getInstance().init(this);
	}

	private void initSDLibrary()
	{
		SDLibrary.getInstance().init(mInstance);
	}

	private void initLockConfig()
	{
		mLockPatternUtils = new LockPatternUtils(this);
		LoginSuccessModel model = LoginSuccessModelDao.queryModelCurrentLogin();
		if (model != null && !TextUtils.isEmpty(model.getPatternpassword()))
		{
			@SuppressWarnings("static-access")
			List<Cell> list = mLockPatternUtils.stringToPattern(model.getPatternpassword());
			mLockPatternUtils.saveLockPattern(list);
		}
	}

	private void initUmengPush()
	{
		UmengPushManager.init(this);
	}

	public void exitApp(boolean isBackground)
	{
		SDActivityManager.getInstance().finishAllActivity();
		EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_EXIT_APP.ordinal()));
		if (!isBackground)
		{
			System.exit(0);
		}
	}
}
