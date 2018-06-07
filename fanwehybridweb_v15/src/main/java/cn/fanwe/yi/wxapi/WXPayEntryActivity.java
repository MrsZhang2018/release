package cn.fanwe.yi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.hybrid.wxapp.SDWxappPay;
import com.fanwe.library.utils.SDToast;
import com.sunday.eventbus.SDBaseEvent;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import de.greenrobot.event.EventBus;

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
		int respType = resp.getType();
		switch (respType)
		{
		case ConstantsAPI.COMMAND_PAY_BY_WX:
			String content = null;
			switch (resp.errCode)
			{
			case 0: // 成功
				content = "支付成功";
				EventBus.getDefault().post(new SDBaseEvent(RespErrCode.CODE_SUCCESS, EnumEventTag.EVENT_WX_PAY_JS_BACK.ordinal()));
				break;
			case -1: // 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
				content = "支付失败";
				EventBus.getDefault().post(new SDBaseEvent(RespErrCode.CODE_FAIL, EnumEventTag.EVENT_WX_PAY_JS_BACK.ordinal()));
				break;
			case -2: // 无需处理。发生场景：用户不支付了，点击取消，返回APP。
				content = "取消支付";
				EventBus.getDefault().post(new SDBaseEvent(RespErrCode.CODE_CANCEL, EnumEventTag.EVENT_WX_PAY_JS_BACK.ordinal()));
				break;

			default:
				break;
			}
			if (content != null)
			{
				SDToast.showToast(content);
			}
			break;

		default:
			break;
		}
		finish();
	}

	public static final class RespErrCode
	{
		public static final int CODE_SUCCESS = 0;
		public static final int CODE_FAIL = -1;
		public static final int CODE_CANCEL = -2;
	}
}