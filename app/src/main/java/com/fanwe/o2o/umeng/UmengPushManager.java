package com.fanwe.o2o.umeng;

import android.content.Context;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.o2o.R;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

public class UmengPushManager
{
	private static PushAgent mPushAgent;

	/**
	 * 必须在Application的onCreate方法中调用
	 * 
	 * @param context
	 */

	public static void init(Context context)
	{
		mPushAgent = PushAgent.getInstance(context);
		mPushAgent.setResourcePackageName(R.class.getName().replace(".R", ""));
		mPushAgent.setNotificationClickHandler(new AppUmengNotificationClickHandler());
		mPushAgent.setDisplayNotificationNumber(5);
		mPushAgent.register(new IUmengRegisterCallback()
		{
			@Override
			public void onSuccess(String s)
			{
				LogUtil.i("register push success regId:" + s);
			}

			@Override
			public void onFailure(String s, String s1)
			{
				LogUtil.i("register push failure:" + s + "," + s1);
			}
		});
	}

	public static PushAgent getPushAgent()
	{
		return mPushAgent;
	}

}
