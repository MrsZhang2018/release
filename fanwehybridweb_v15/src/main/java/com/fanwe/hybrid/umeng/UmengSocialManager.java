package com.fanwe.hybrid.umeng;

import android.app.Activity;
import android.text.TextUtils;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;

import cn.fanwe.yi.R;

public class UmengSocialManager
{
    public static final String SHARE = "com.umeng.share";
    public static final String LOGIN = "com.umeng.login";

    public static void init(Activity activity)
    {
        InitActModel model = InitActModelDao.query();
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
                wxAppKey = activity.getResources().getString(R.string.wx_app_id);
                wxAppSecret = activity.getResources().getString(R.string.wx_app_secret);
            }

            if (!TextUtils.isEmpty(model.getQq_app_key()) && !TextUtils.isEmpty(model.getQq_app_secret()))
            {
                qqAppKey = model.getQq_app_key();
                qqAppSecret = model.getQq_app_secret();

            } else
            {
                qqAppKey = activity.getResources().getString(R.string.qq_app_id);
                qqAppSecret = activity.getResources().getString(R.string.qq_app_key);
            }

            if (!TextUtils.isEmpty(model.getSina_app_key()) && !TextUtils.isEmpty(model.getSina_app_secret()))
            {
                sinaAppKey = model.getSina_app_key();
                sinaAppSecret = model.getSina_app_secret();
            } else
            {
                sinaAppKey = activity.getResources().getString(R.string.sina_app_key);
                sinaAppSecret = activity.getResources().getString(R.string.sina_app_secret);
            }
        } else
        {
            // /////////////////////////////////////////微信
            wxAppKey = activity.getResources().getString(R.string.wx_app_id);
            wxAppSecret = activity.getResources().getString(R.string.wx_app_secret);

            qqAppKey = activity.getResources().getString(R.string.qq_app_id);
            qqAppSecret = activity.getResources().getString(R.string.qq_app_key);

            sinaAppKey = activity.getResources().getString(R.string.sina_app_key);
            sinaAppSecret = activity.getResources().getString(R.string.sina_app_secret);
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
        list.add(SHARE_MEDIA.EMAIL);
        list.add(SHARE_MEDIA.SMS);

        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            int wx_app_api = model.getWx_app_api();
            if (wx_app_api == 1)
            {
                list.add(SHARE_MEDIA.WEIXIN);
                list.add(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
            int qq_app_api = model.getQq_app_api();
            if (qq_app_api == 1)
            {
                list.add(SHARE_MEDIA.QQ);
                list.add(SHARE_MEDIA.QZONE);
            }
            int sina_app_api = model.getSina_app_api();
            if (sina_app_api == 1)
            {
                list.add(SHARE_MEDIA.SINA);
            }
        } else
        {
            list.add(SHARE_MEDIA.WEIXIN);
            list.add(SHARE_MEDIA.WEIXIN_CIRCLE);
            list.add(SHARE_MEDIA.QQ);
            list.add(SHARE_MEDIA.QZONE);
            list.add(SHARE_MEDIA.SINA);
        }

        SHARE_MEDIA[] displaylist = new SHARE_MEDIA[list.size()];
        list.toArray(displaylist);
        return displaylist;
    }

    public static void openShare(String title, String content, String imageUrl, String clickUrl, Activity activity, UMShareListener listener)
    {
        UMImage umImage = null;
        if (!TextUtils.isEmpty(imageUrl))
        {
            umImage = new UMImage(activity, imageUrl);
        }

        String str_title;
        if (!TextUtils.isEmpty(title))
        {
            str_title = title;
        } else
        {
            str_title = "title为空";
        }
        String str_content;
        if (!TextUtils.isEmpty(content))
        {
            str_content = content;
        } else
        {
            str_content = "str_content为空";
        }
        openShare(str_title, str_content, umImage, clickUrl, activity, listener);
    }

    public static void openShare(String title, String content, UMImage umImage, String clickUrl, Activity activity, UMShareListener listener)
    {
        SHARE_MEDIA[] displaylist = getPlatform(activity);
        new ShareAction(activity).setDisplayList(displaylist).withText(content).withTitle(title).withTargetUrl(clickUrl).withMedia(umImage).setListenerList(listener).open();
    }

}