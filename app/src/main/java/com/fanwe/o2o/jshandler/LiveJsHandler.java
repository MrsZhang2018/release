package com.fanwe.o2o.jshandler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.handler.js.AppJsWHandler;
import com.fanwe.o2o.event.ERefreshReload;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by luodong on 2016/11/15.
 */
public class LiveJsHandler extends AppJsWHandler
{
    public static final int REQUEST_CODE_QR = 99;// 二维码
    public static final int REQUEST_CODE_WEB_ACTIVITY = 2;// WEB回调

    private static final String DEFALUT_NAME = "App";

    public LiveJsHandler(Activity activity)
    {
        super(DEFALUT_NAME, activity);
    }

    @JavascriptInterface
    public void check_network()
    {
        openNetwork(getActivity());
    }

    @JavascriptInterface
    public void refresh_reload()
    {
        ERefreshReload event = new ERefreshReload();
        SDEventManager.post(event);
    }


    /** 打开WIFI设置界面 */
    public static void openNetwork(final Activity context)
    {

        SDDialogConfirm dialogConfirm = new SDDialogConfirm(context);
        dialogConfirm.setTextTitle("网络设置提示");
        dialogConfirm.setTextContent("网络连接不可用,是否进行设置?");
        dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                context.startActivity(createNetWorkIntent());
            }

            @Override
            public void onDismiss(SDDialogCustom dialog) {
                dialog.dismiss();
            }
        });
        dialogConfirm.show();
    }

    /** 网络配置 */
    public static Intent createNetWorkIntent()
    {
        Intent intent = null;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10)
        {
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        } else
        {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        return intent;
    }
}
