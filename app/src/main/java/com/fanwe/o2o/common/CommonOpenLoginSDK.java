package com.fanwe.o2o.common;

import android.app.Activity;
import android.app.Dialog;

import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.app.App;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-3-21 上午10:40:36 类说明
 */
public class CommonOpenLoginSDK
{
    private static UMShareAPI shareAPI;

    public static UMShareAPI getShareAPI()
    {
        return shareAPI;
    }

    /**
     * 点击微信登录，先获取个人资料
     */
    public static void loginWx(final Activity activity, UMAuthListener listener)
    {
        SDDialogProgress dialog = new SDDialogProgress();
        dialog.setTextMsg("正在启动微信");
        umLogin(activity, dialog, SHARE_MEDIA.WEIXIN,listener);
    }

    public static void umSinalogin(Activity activity, UMAuthListener listener)
    {
        SDDialogProgress dialog = new SDDialogProgress();
        dialog.setTextMsg("正在启动新浪微博");
        umLogin(activity, dialog, SHARE_MEDIA.SINA, listener);
    }

    public static void umQQlogin(Activity activity, UMAuthListener listener)
    {
        SDDialogProgress dialog = new SDDialogProgress();
        dialog.setTextMsg("正在启动QQ");
        umLogin(activity, dialog, SHARE_MEDIA.QQ, listener);
    }

    public static void umLogin(Activity activity, Dialog dialog, SHARE_MEDIA platform, UMAuthListener listener)
    {
        if (activity == null || listener == null || platform == null)
        {
            return;
        }

        if (shareAPI == null)
        {
            shareAPI = UMShareAPI.get(App.getApplication().getApplicationContext());
        }

        if (!shareAPI.isInstall(activity, platform))
        {
            if (platform == SHARE_MEDIA.SINA)
            {
                SDToast.showToast("您未安装新浪微博客户端");
            } else if (platform == SHARE_MEDIA.WEIXIN)
            {
                SDToast.showToast("您未安装微信客户端");
            } else if (platform == SHARE_MEDIA.QQ)
            {
                SDToast.showToast("您未安装QQ客户端");
            }
            return;
        }

        Config.dialog = dialog;
        shareAPI.doOauthVerify(activity, platform, listener);
    }
}
