package com.fanwe.o2o.app;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.webkit.CookieManager;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDCookieManager;
import com.fanwe.library.utils.SDCache;
import com.fanwe.o2o.R;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.event.EExitApp;
import com.fanwe.o2o.model.SearchHistoryDaoModel;
import com.fanwe.o2o.umeng.UmengPushManager;
import com.fanwe.o2o.umeng.UmengSocialManager;
import com.sunday.eventbus.SDEventManager;

import org.xutils.x;

import cn.xiaoneng.uiapi.Ntalker;


/**
 * Created by luodong on 2016/12/8.
 */
public class App extends Application
{

    private static App sInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;
        init();
    }

    public static App getApplication()
    {
        return sInstance;
    }

    private void init()
    {
        MultiDex.install(this);
        SDCache.init(this);
        SDLibrary.getInstance().init(this);
        x.Ext.init(sInstance);
        initBaiduMap();
        initUmengPush();
        initUmengSocial();
        initXiaoNeng();
    }

    private void initUmengPush()
    {
        UmengPushManager.init(this);
    }

    private void initUmengSocial()
    {
        UmengSocialManager.init(this);
    }

    private void initBaiduMap()
    {
        BaiduMapManager.getInstance().init(this);
    }

    /**
     *  @params appContext
     *  @params siteid: 企业id，即企业唯一标识。格式示例：kf_9979【必填】
     *  @params sdkkey: 企业key，即小能通行密钥【必填】
     *  @return int  0 表示初始化成功, 其他值请查看错误码
     */
    private void initXiaoNeng()
    {
        Log.i("xiaoneng", getResources().getString(R.string.xiaoneng_siteid) + "  " + getResources().getString(R.string.xiaoneng_sdk_key));
        Ntalker.getInstance().initSDK(this,getResources().getString(R.string.xiaoneng_siteid), getResources().getString(R.string.xiaoneng_sdk_key));
    }

    public void exitApp(boolean isBackground)
    {
        SDActivityManager.getInstance().finishAllActivity();
        EExitApp event = new EExitApp();
        SDEventManager.post(event);
        if (!isBackground)
        {
            System.exit(0);
        }
    }

    public void clearAppsLocalUserModel()
    {

        SearchHistoryDaoModel.clear();//清空发现的搜索记录
        LocalUserModelDao.deleteAllModel();
        //AppConfig.setSessionId("");  //
        CookieManager.getInstance().removeAllCookie();
    }
}
