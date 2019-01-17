package com.fanwe.o2o.umeng;

import android.content.Context;
import android.content.Intent;

//import com.fanwe.AppWebViewActivity;
//import com.fanwe.EventDetailActivity;
//import com.fanwe.NoticeDetailActivity;
//import com.fanwe.StoreDetailActivity;
//import com.fanwe.TuanDetailActivity;
//import com.fanwe.YouHuiDetailActivity;
//import com.fanwe.app.App;
//import com.fanwe.constant.Constant.PushType;
//import com.fanwe.library.common.SDActivityManager;
//import com.fanwe.library.utils.SDTypeParseUtil;
//import com.fanwe.model.JpushDataModel;
//import com.fanwe.utils.JsonUtil;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.MainActivity;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.EIntentAppMain;
import com.fanwe.o2o.model.JpushDataModel;
import com.sunday.eventbus.SDEventManager;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Map;

/**
 * 友盟推送通知栏点击事件处理
 * 
 * @author Administrator
 * 
 */
public class AppUmengNotificationClickHandler extends UmengNotificationClickHandler
{
	@Override
	public void dealWithCustomAction(Context context, UMessage msg)
	{
		clickNotification(context, msg);
	}

	protected void clickNotification(Context context, UMessage msg)
	{
		JpushDataModel model = parseData2Model(context, msg);
		if (model == null)
		{
			super.launchApp(context, msg);
			return;
		}
		if(context==null){
            return;
        }

		int type = SDTypeParseUtil.getInt(model.getType());
		String data = model.getData();
		Intent intent = null;
		switch (type)
		{
		case Constant.PushType.MAIN:
		    SDEventManager.post(new EIntentAppMain());
            intent = new Intent(App.getApplication(), MainActivity.class);
			break;
		case Constant.PushType.URL:
			intent = new Intent(App.getApplication(), AppWebViewActivity.class);
			intent.putExtra(AppWebViewActivity.EXTRA_URL, data);
			break;
		case Constant.PushType.TUAN_DETAIL:
            intent=getWapIntent(context,"deal",data);
			break;
		case Constant.PushType.GOODS_DETAIL:
            intent=getWapIntent(context,"deal",data);
			break;
		case Constant.PushType.EVENT_DETAIL:
            intent=getWapIntent(context,"event",data);
			break;
		case Constant.PushType.YOUHUI_DETAIL:
            intent=getWapIntent(context,"youhui",data);
			break;
		case Constant.PushType.STORE_DETAIL:
            intent=getWapIntent(context,"store",data);
			break;
//		case Constant.PushType.NOTICE_DETAIL:
//            AppJsHandler.jump2DealWap(context,"event", String.valueOf(data));
//			break;
		default:

			break;
		}

		if (intent != null)
		{
			super.launchApp(context, msg);
			if(!SDActivityManager.getInstance().isEmpty())
			{
				SDActivityManager.getInstance().getLastActivity().startActivity(intent);
			}
//			if (SDActivityManager.getInstance().isEmpty())
//			{
////				App.getApplication().mPushIntent = intent;
//				super.launchApp(context, msg);
//			} else
//			{
//				SDActivityManager.getInstance().getLastActivity().startActivity(intent);
//			}
		} else
		{
			super.launchApp(context, msg);
		}
	}


	private Intent getWapIntent(Context context, String ctl, String deal_id){
        Intent intent = new Intent(context.getApplicationContext(), AppWebViewActivity.class);
            final StringBuilder sb = new StringBuilder();
            sb.append(ApkConstant.SERVER_URL_WAP)
                    .append("?ctl=").append(ctl)
                    .append("&data_id=").append(deal_id);
            String url = String.valueOf(sb);
            intent.putExtra(AppWebViewActivity.EXTRA_URL, url);
//            context.startActivity(intent);
        return intent;
    }

	private JpushDataModel parseData2Model(Context context, UMessage msg)
	{
		JpushDataModel model = null;
		if (msg != null)
		{
			Map<String, String> mapData = msg.extra;
			if (mapData != null)
			{
//				model = JsonUtil.map2Object(mapData, JpushDataModel.class);
			}
		}
		return model;
	}

	@Override
	public void autoUpdate(Context context, UMessage msg)
	{
		super.autoUpdate(context, msg);
	}

	@Override
	public void dismissNotification(Context context, UMessage msg)
	{
		super.dismissNotification(context, msg);
	}

	@Override
	public void handleMessage(Context context, UMessage msg)
	{
		super.handleMessage(context, msg);
	}

	@Override
	public void launchApp(Context context, UMessage msg)
	{
		clickNotification(context, msg);
	}

	@Override
	public void openActivity(Context context, UMessage msg)
	{
		super.openActivity(context, msg);
	}

	@Override
	public void openUrl(Context context, UMessage msg)
	{
		super.openUrl(context, msg);
	}
}
