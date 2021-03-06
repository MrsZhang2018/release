package com.mxkdsc.www.wxapp;

import android.text.TextUtils;

import com.fanwe.library.common.SDActivityManager;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class SDWxappPay
{

	private static SDWxappPay mInstance;
	private String mAppId;
	private boolean mIsRegister = false;

	public static SDWxappPay getInstance()
	{
		if (mInstance == null)
		{
			mInstance = new SDWxappPay();
		}
		return mInstance;
	}

	private SDWxappPay()
	{
		String appId = AppRuntimeWorker.getWx_app_key();
		setAppId(appId);
	}

	public String getAppId()
	{
		return this.mAppId;
	}

	public void setAppId(String appId)
	{
		this.mAppId = appId;
		register();
	}

	public void register()
	{
		if (!mIsRegister && !TextUtils.isEmpty(mAppId))
		{
			mIsRegister = getWXAPI().registerApp(mAppId);
		}
	}

	public void pay(PayReq request)
	{
		if (request != null)
		{
			getWXAPI().sendReq(request);
		}
	}

	public IWXAPI getWXAPI()
	{
		return WXAPIFactory.createWXAPI(SDActivityManager.getInstance().getLastActivity(), mAppId);
	}

}
