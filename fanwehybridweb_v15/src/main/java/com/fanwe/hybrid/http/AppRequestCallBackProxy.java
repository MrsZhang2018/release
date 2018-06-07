package com.fanwe.hybrid.http;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.conn.ConnectTimeoutException;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.fanwe.hybrid.constant.Constant;
import com.fanwe.hybrid.http.AppRequestParams.ResponseDataType;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.callback.SDModelRequestCallback;
import com.fanwe.library.adapter.http.callback.SDRequestCallback;
import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDToast;

public class AppRequestCallBackProxy extends SDModelRequestCallback<BaseActModel>
{

	private static final String STRING_NULL = "\":null";
	private static final String STRING_FALSE = "\":false";
	private static final String STRING_EMPTY_ARRAY = "\":[]";

	private AppRequestParams mRequestParams;

	private SDRequestCallback mOriginalCallback;

	public AppRequestCallBackProxy(SDRequestCallback originalCallback)
	{
		super();
		this.mOriginalCallback = originalCallback;
	}

	@Override
	protected void onStart()
	{
		mOriginalCallback.notifyStart();
	}

	@Override
	protected void onSuccessBefore(SDResponse resp)
	{
		SDRequestParams sp = getRequestParams();
		if (sp != null && sp instanceof AppRequestParams)
		{
			mRequestParams = (AppRequestParams) sp;
			String result = resp.getResult();
			if (!TextUtils.isEmpty(result))
			{
				switch (mRequestParams.getResponseDataType())
				{
				case ResponseDataType.BASE64:
					result = SDBase64.decodeToString(result);
					break;
				case ResponseDataType.JSON:

					break;
				case ResponseDataType.AES:
					result = AESUtil.decrypt(result, Constant.APK_AES_KEY);
					break;

				default:
					break;
				}
				// 替换false为null
				if (result.contains(STRING_FALSE))
				{
					result = result.replace(STRING_FALSE, STRING_NULL);
				}
				// 替换[]为null
				if (result.contains(STRING_EMPTY_ARRAY))
				{
					result = result.replace(STRING_EMPTY_ARRAY, STRING_NULL);
				}
				// 解密后的result赋值回去
				resp.setResult(result);
			}
		}
		super.onSuccessBefore(resp);
	}

	@Override
	protected void onSuccess(SDResponse resp)
	{
		boolean continueNotify = true;

		if (continueNotify)
		{
			try
			{
				mOriginalCallback.notifySuccess(resp);
			} catch (Exception e)
			{
				showErrorTip(e);
			}
		}
	}

	@Override
	protected void onSuccessAfter(SDResponse resp)
	{

	}

	@Override
	protected void onError(SDResponse resp)
	{
		mOriginalCallback.notifyError(resp);
	}

	@Override
	protected void onCancel(SDResponse resp)
	{
		mOriginalCallback.notifyCancel(resp);
	}

	@Override
	protected void onFinish(SDResponse resp)
	{
		mOriginalCallback.notifyFinish(resp);
	}

	private void showErrorTip(Throwable error)
	{
		if (error != null)
		{
			if (error instanceof JSONException)
			{
				showErrorToast("错误:" + "数据解析异常!");
			} else if (error instanceof UnknownHostException)
			{
				showErrorToast("错误:" + "无法访问的服务器地址!");
			} else if (error instanceof ConnectException)
			{
				showErrorToast("错误:" + "连接服务器失败!");
			} else if (error instanceof SocketTimeoutException || error instanceof ConnectTimeoutException)
			{
				showErrorToast("错误:" + "连接超时!");
			} else if (error instanceof SocketException)
			{
				showErrorToast("错误:" + "连接服务器失败!");
			} else
			{
				showErrorToast("未知错误,请求失败!");
			}
		} else
		{
			showErrorToast("未知错误,请求失败!");
		}
	}

	private void showErrorToast(String text)
	{
		if (mRequestParams != null && mRequestParams.isNeedShowErrorMsg())
		{
			SDToast.showToast(text);
		}
	}
}
