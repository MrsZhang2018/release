package com.fanwe.o2o.umeng;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.o2o.R;
import com.fanwe.o2o.dao.InitActModelDao;
import com.fanwe.o2o.model.Init_indexActModel;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;

import java.util.ArrayList;

public class UmengSocialManager
{
	public static final String SHARE = "com.umeng.share";
	public static final String LOGIN = "com.umeng.login";

	public static void init(Application app)
	{
		Init_indexActModel model = InitActModelDao.query();
		String wxAppKey = null;
		String wxAppSecret = null;

		String qqAppKey = null;
		String qqAppSecret = null;

		String sinaAppKey = null;
		String sinaAppSecret = null;

		if (model != null)
		{
			if (!TextUtils.isEmpty(model.getWx_app_key()) && !TextUtils.isEmpty(model.getWx_app_secret()))
			{
				wxAppKey = model.getWx_app_key();
				wxAppSecret = model.getWx_app_secret();
			} else
			{
				wxAppKey = app.getResources().getString(R.string.wx_app_id);
				wxAppSecret = app.getResources().getString(R.string.wx_app_secret);
			}

			if (!TextUtils.isEmpty(model.getQq_app_key()) && !TextUtils.isEmpty(model.getQq_app_secret()))
			{
				qqAppKey = model.getQq_app_key();
				qqAppSecret = model.getQq_app_secret();

			} else
			{
				qqAppKey = app.getResources().getString(R.string.qq_app_id);
				qqAppSecret = app.getResources().getString(R.string.qq_app_key);
			}

			if (!TextUtils.isEmpty(model.getSina_app_key()) && !TextUtils.isEmpty(model.getSina_app_secret()))
			{
				sinaAppKey = model.getSina_app_key();
				sinaAppSecret = model.getSina_app_secret();
			} else
			{
				sinaAppKey = app.getResources().getString(R.string.sina_app_key);
				sinaAppSecret = app.getResources().getString(R.string.sina_app_secret);
			}
		} else
		{
			// /////////////////////////////////////////微信
			wxAppKey = app.getResources().getString(R.string.wx_app_id);
			wxAppSecret = app.getResources().getString(R.string.wx_app_secret);

			qqAppKey = app.getResources().getString(R.string.qq_app_id);
			qqAppSecret = app.getResources().getString(R.string.qq_app_key);

			sinaAppKey = app.getResources().getString(R.string.sina_app_key);
			sinaAppSecret = app.getResources().getString(R.string.sina_app_secret);
		}

		PlatformConfig.setWeixin(wxAppKey, wxAppSecret);
		// 微信 appid appsecret
		PlatformConfig.setQQZone(qqAppKey, qqAppSecret);
		// QQ和Qzone appid appkey
		PlatformConfig.setSinaWeibo(sinaAppKey, sinaAppSecret);
		// 新浪微博 appkey appsecret
	}

	private static SHARE_MEDIA[] getPlatform(Activity activity)
	{
		ArrayList<SHARE_MEDIA> list = new ArrayList<SHARE_MEDIA>();

		Init_indexActModel model = InitActModelDao.query();

		if (isWeixinEnable(model))
		{
			list.add(SHARE_MEDIA.WEIXIN);
			list.add(SHARE_MEDIA.WEIXIN_CIRCLE);
		}

		if (isQQEnable(model))
		{
			list.add(SHARE_MEDIA.QQ);
			list.add(SHARE_MEDIA.QZONE);
		}

		if (isSinaEnable(model))
		{
			list.add(SHARE_MEDIA.SINA);
		}

		SHARE_MEDIA[] displaylist = new SHARE_MEDIA[list.size()];
		list.toArray(displaylist);
		return displaylist;
	}

	public static boolean isWeixinEnable(Init_indexActModel model)
	{
		if (model != null)
		{
			String wxAppKey = model.getWx_app_key();
			String wxAppSecret = model.getWx_app_secret();
			if (!TextUtils.isEmpty(wxAppKey) && !TextUtils.isEmpty(wxAppSecret))
			{
				return true;
			}else {
				return false;
			}
		} else
		{
			return false;
		}
	}

	public static boolean isQQEnable(Init_indexActModel model)
	{
		if (model != null)
		{
			String qqAppKey = model.getQq_app_key();
			String qqAppSecret = model.getQq_app_secret();
			if (!TextUtils.isEmpty(qqAppKey) && !TextUtils.isEmpty(qqAppSecret))
			{
				return true;
			}else {
				return false;
			}
		} else
		{
			return false;
		}
	}

	public static boolean isSinaEnable(Init_indexActModel model)
	{
		if (model != null)
		{
			String sinaAppKey = model.getSina_app_key();
			String sinaAppSecret = model.getSina_app_secret();
			if (!TextUtils.isEmpty(sinaAppKey) && !TextUtils.isEmpty(sinaAppSecret))
			{
				return true;
			}else {
				return false;
			}
		} else
		{
			return false;
		}
	}

	public static void shareWeixin(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		if (isWeixinEnable(InitActModelDao.query()))
		{
			shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.WEIXIN).share();
		}
	}

	public static void shareWeixinCircle(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		if (isWeixinEnable(InitActModelDao.query()))
		{
			shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
		}
	}

	public static void shareQQ(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		if (isQQEnable(InitActModelDao.query()))
		{
			shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.QQ).share();
		}
	}

	public static void shareQzone(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		if (isQQEnable(InitActModelDao.query()))
		{
			shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.QZONE).share();
		}
	}

	public static void shareSina(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		if (isSinaEnable(InitActModelDao.query()))
		{
			shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.SINA).share();
		}
	}

	/**
	 * 打开分享面板
	 */
	public static void openShare(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		shareAction(title, content, imageUrl, clickUrl, activity, listener).open();
	}

	private static ShareAction shareAction(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
	{
		SHARE_MEDIA[] displayList = getPlatform(activity);
		return shareAction(title, content, imageUrl, clickUrl, activity, listener, displayList);
	}

	private static ShareAction shareAction(String title, String content, String imageUrl, String clickUrl, Activity activity,
										   UMShareListener listener, SHARE_MEDIA... displayList)
	{
		Object media = null;
		if (!TextUtils.isEmpty(imageUrl))
		{
			media = new UMImage(activity, imageUrl);
		}
		return shareAction(title, content, media, clickUrl, activity, listener, displayList);
	}

	private static ShareAction shareAction(String title, String content, Object media, String clickUrl, Activity activity, UMShareListener listener,
										   SHARE_MEDIA... displayList)
	{
		ShareAction share = new ShareAction(activity);

		if (TextUtils.isEmpty(title))
		{
			title = "title为空";
		}
		if (TextUtils.isEmpty(content))
		{
			content = "content为空";
		}

		setShowLoadDialog();

		share.setDisplayList(displayList).withTitle(title).withText(content).withTargetUrl(clickUrl).setListenerList(listener);

		if (media instanceof UMImage)
		{
			share.withMedia((UMImage) media);
		} else if (media instanceof UMusic)
		{
			share.withMedia((UMusic) media);
		} else if (media instanceof UMVideo)
		{
			share.withMedia((UMVideo) media);
		}

		return share;
	}

	public static void setShowLoadDialog()
	{
		setShowLoadDialog("正在分享中");
	}

	public static void setShowLoadDialog(String text)
	{
		SDDialogProgress dialog = new SDDialogProgress();
		if (!TextUtils.isEmpty(text))
		{
			dialog.setTextMsg(text);
		} else
		{
			dialog.setTextMsg("正在分享中");
		}
		Config.dialog = dialog;
	}

	public static String getPlatformString(SHARE_MEDIA media)
	{
		String result = "";
		if (media != null)
		{
			switch (media)
			{
				case WEIXIN:
					result = "微信";
					break;
				case WEIXIN_CIRCLE:
					result = "朋友圈";
					break;
				case QQ:
					result = "QQ";
					break;
				case SINA:
					result = "新浪微博";
					break;
				case QZONE:
					result = "QQ空间";
					break;
				default:
					break;
			}
		}
		return result;
	}


}