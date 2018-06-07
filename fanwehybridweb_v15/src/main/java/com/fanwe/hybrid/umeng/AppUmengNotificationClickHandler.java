package com.fanwe.hybrid.umeng;

import java.util.Map;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.fanwe.hybrid.activity.MainActivity;
import com.fanwe.hybrid.constant.Constant.PushType;
import com.fanwe.hybrid.model.JpushDataModel;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-1-12 上午10:33:36 类说明 该Handler是在BroadcastReceiver中被调用，故
 *          如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
 */
public class AppUmengNotificationClickHandler extends UmengNotificationClickHandler
{
	@Override
	public void autoUpdate(Context arg0, UMessage arg1)
	{
		// TODO Auto-generated method stub
		super.autoUpdate(arg0, arg1);
	}

	@Override
	public void dismissNotification(Context arg0, UMessage arg1)
	{
		// TODO Auto-generated method stub
		super.dismissNotification(arg0, arg1);
	}

	@Override
	public void handleMessage(Context arg0, UMessage arg1)
	{
		// TODO Auto-generated method stub
		super.handleMessage(arg0, arg1);
	}

	@Override
	public void openActivity(Context arg0, UMessage arg1)
	{
		// TODO Auto-generated method stub
		super.openActivity(arg0, arg1);
	}

	@Override
	public void openUrl(Context arg0, UMessage arg1)
	{
		// TODO Auto-generated method stub
		super.openUrl(arg0, arg1);
	}

	@Override
	public void launchApp(Context arg0, UMessage arg1)
	{
		// TODO Auto-generated method stub
		super.launchApp(arg0, arg1);
	}

	@Override
	public void dealWithCustomAction(Context context, UMessage msg)
	{
		JpushDataModel model = parseData2Model(context, msg);
		if (model == null)
		{
			launchApp(context, msg);
			return;
		}

		int type = SDTypeParseUtil.getInt(model.getType(), 0);
		String data = model.getData();

		Intent intent = null;
		switch (type)
		{
		case PushType.NORMAL:
			launchApp(context, msg);
			break;
		case PushType.URL:
			intent = new Intent(context, MainActivity.class);
			intent.putExtra(MainActivity.EXTRA_URL, data);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			break;
		case PushType.PROJECT_ID:
			break;
		case PushType.ARTICLE_ID:
			break;
		default:
			launchApp(context, msg);
			break;
		}
		context.startActivity(intent);
	}

	private JpushDataModel parseData2Model(Context context, UMessage msg)
	{
		JpushDataModel model = null;
		if (msg != null)
		{
			Map<String, String> mapData = msg.extra;
			if (mapData != null)
			{
				String json = JSON.toJSONString(mapData);
				model = JSON.parseObject(json, JpushDataModel.class);
			}
		}
		return model;
	}
}
