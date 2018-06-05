package com.mxkdsc.www.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.model.WXPayStateModel;
import com.fanwe.o2o.wxapp.SDWxappPay;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import static com.fanwe.o2o.constant.Constant.EnumEvent.EVENT_WX_PAY_JS_BACK;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler
{

	private IWXAPI mApi;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
	}

	private void init()
	{
		mApi = SDWxappPay.getInstance().getWXAPI();
		mApi.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
		mApi.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req)
	{
	}

	@Override
	public void onResp(BaseResp resp)
	{

		final WXPayStateModel model=new WXPayStateModel();

		switch (resp.errCode)
		{
			case 0: // 成功
				model.setState(1);
				SDEventManager.post(new SDBaseEvent(model,EVENT_WX_PAY_JS_BACK.ordinal()));
				break;
			case -1: // 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
				model.setState(6);
				SDEventManager.post(new SDBaseEvent(model,EVENT_WX_PAY_JS_BACK.ordinal()));
				break;
			case -2: // 无需处理。发生场景：用户不支付了，点击取消，返回APP。
				model.setState(4);
				SDEventManager.post(new SDBaseEvent(model,EVENT_WX_PAY_JS_BACK.ordinal()));
				break;
			default:
				break;
		}

		finish();
	}
}