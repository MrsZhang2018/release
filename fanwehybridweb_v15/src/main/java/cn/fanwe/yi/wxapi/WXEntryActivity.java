package cn.fanwe.yi.wxapi;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

import cn.fanwe.yi.R;

import com.alibaba.fastjson.JSON;
import com.fanwe.hybrid.event.EnumEventTag;
import com.sunday.eventbus.SDBaseEvent;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import de.greenrobot.event.EventBus;

public class WXEntryActivity extends WXCallbackActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req)
	{
		switch (req.getType())
		{
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			break;
		default:
			break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp)
	{
		if (resp instanceof SendAuth.Resp)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("login_sdk_type", "wxlogin");
			map.put("err_code", resp.errCode + "");

			String wx_app_id = this.getResources().getString(R.string.wx_app_id);
			String  wx_app_secret= this.getResources().getString(R.string.wx_app_secret);
			map.put("wx_app_id", wx_app_id);
			map.put("wx_app_secret", wx_app_secret);
			
			SendAuth.Resp sar = (SendAuth.Resp) resp;
			String code = sar.code;
			switch (resp.errCode)
			{
			case BaseResp.ErrCode.ERR_OK:
				map.put("code", code);
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				map.put("code", "");
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				map.put("code", "");
				break;
			default:
				map.put("code", "");
				break;
			}
			String json = JSON.toJSONString(map);
			EventBus.getDefault().post(new SDBaseEvent(json, EnumEventTag.EVENT_WX_LOGIN_JS_BACK.ordinal()));
		}
		finish();
	}

}
