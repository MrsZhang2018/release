package com.fanwe.hybrid.jshandler;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.dao.LoginSuccessModelDao;
import com.fanwe.hybrid.event.EJsSdkShare;
import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.hybrid.model.CutPhotoModel;
import com.fanwe.hybrid.model.LoginSuccessModel;
import com.fanwe.hybrid.model.OpenTypeModel;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.hybrid.model.StartAppPageJsonModel;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.handler.js.BaseJsHandler;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

import java.util.Calendar;

import de.greenrobot.event.EventBus;

import static android.R.attr.text;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-18 上午9:49:34 类说明
 */
public class AppJsHandler extends BaseJsHandler
{
    private static final String DEFALUT_NAME = "App";

    public AppJsHandler(Activity activity)
    {
        super(DEFALUT_NAME, activity);
    }

    public static final int MIN_CLICK_DELAY_TIME = 5000;
    private long lastClickTime = 0;

    @JavascriptInterface
    public void check_network()
    {
        SDEventManager.post(EnumEventTag.EVENT_ONPEN_NETWORK.ordinal());
    }

    @JavascriptInterface
    public void refresh_reload()
    {
        EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_REFRESH_RELOAD.ordinal()));
    }

    @JavascriptInterface
    public void sdk_share(String json)
    {
        EJsSdkShare event = new EJsSdkShare();
        event.json = json;

        SDEventManager.post(event);
    }

    @JavascriptInterface
    public void pay_sdk(String json)
    {
        PaySdkModel model = JSON.parseObject(json, PaySdkModel.class);
        if (!isActModelNull(model))
        {
            EventBus.getDefault().post(new SDBaseEvent(model, EnumEventTag.EVENT_PAY_SDK.ordinal()));
        }
    }

    @JavascriptInterface
    public void login_success(String json)
    {
        App.getApplication().mLockPatternUtils.clearLock();
        LoginSuccessModel model = JSON.parseObject(json, LoginSuccessModel.class);
        model.setUserid(model.getId());
        model.setIs_current(1);
        LoginSuccessModel loginSuccessModel = LoginSuccessModelDao.queryModelCurrentLogin();
        if (loginSuccessModel != null)
        {
            if (loginSuccessModel.getUserid() == model.getId())
            {
                model.setPatternpassword(loginSuccessModel.getPatternpassword());
                LoginSuccessModelDao.insertOrUpdateModel2(model);
            } else
            {
                LoginSuccessModelDao.insertOrUpdateModel2(model);
            }
        } else
        {
            LoginSuccessModelDao.insertOrUpdateModel2(model);
        }

        EventBus.getDefault().post(new SDBaseEvent(model, EnumEventTag.EVENT_LOGIN_SUCCESS.ordinal()));
    }

    @JavascriptInterface
    public void logout()
    {
        CookieManager.getInstance().removeSessionCookie();
        LoginSuccessModel model = LoginSuccessModelDao.queryModelCurrentLogin();
        if (model != null)
        {
            model.setIs_current(0);
            LoginSuccessModelDao.insertOrUpdateModel2(model);
        }

        // LoginSuccessModelDao.deleteAllModel();
        App.getApplication().mLockPatternUtils.clearLock();
        EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_LOGOUT_SUCCESS.ordinal()));
    }

    @JavascriptInterface
    public void onConfirm(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            EventBus.getDefault().post(new SDBaseEvent(url, EnumEventTag.EVENT_FINSHI_ACTIVITY.ordinal()));
        } else
        {
            EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_FINSHI_ACTIVITY.ordinal()));
        }
    }

    @JavascriptInterface
    public void open_type(String json)
    {
        OpenTypeModel model = JSON.parseObject(json, OpenTypeModel.class);
        EventBus.getDefault().post(new SDBaseEvent(model, EnumEventTag.EVENT_OPEN_TYPE.ordinal()));
    }

    @JavascriptInterface
    public void qr_code_scan()
    {
        EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_QR_CODE_SCAN.ordinal()));
    }


    @JavascriptInterface
    public void getClipBoardText()
    {
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        ClipboardManager cbm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        String text = cbm.getText().toString();
        if (!TextUtils.isEmpty(text))
        {
            EventBus.getDefault().post(new SDBaseEvent(text, EnumEventTag.EVENT_CLIPBOARDTEXT.ordinal()));
        } else
        {
            SDToast.showToast("您还未复制内容哦");
        }
    }

    @JavascriptInterface
    public void CutPhoto(String json)
    {
        CutPhotoModel model = JSON.parseObject(json, CutPhotoModel.class);
        EventBus.getDefault().post(new SDBaseEvent(model, EnumEventTag.EVENT_CUTPHOTO.ordinal()));
    }

    @JavascriptInterface
    public void restart()
    {
        Intent i = App.getApplication().getPackageManager().getLaunchIntentForPackage(App.getApplication().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SDActivityManager.getInstance().getLastActivity().startActivity(i);
    }

    @JavascriptInterface
    public void position()
    {
        EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.TENCENT_LOCATION_MAP.ordinal()));
    }

    @JavascriptInterface
    public void position2()
    {
        EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.TENCENT_LOCATION_ADDRESS.ordinal()));
    }

    @JavascriptInterface
    public void apns()
    {
        EventBus.getDefault().post(new SDBaseEvent(null, EnumEventTag.EVENT_APNS.ordinal()));
    }

    @JavascriptInterface
    public void login_sdk(String login_sdk_type)
    {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME)
        {
            lastClickTime = currentTime;
            EventBus.getDefault().post(new SDBaseEvent(login_sdk_type, EnumEventTag.EVENT_LOGIN_SDK.ordinal()));
        } else
        {
            // SDToast.showToast("距离上次点击时间未超过5秒");
        }
    }

    @JavascriptInterface
    public void is_exist_installed(String scheme)
    {
        EventBus.getDefault().post(new SDBaseEvent(scheme, EnumEventTag.EVENT_IS_EXIST_INSTALLED.ordinal()));
    }

    public void js_share_sdk(String json)
    {
        EventBus.getDefault().post(new SDBaseEvent(json, EnumEventTag.EVENT_JS_SHARE_SDK.ordinal()));
    }

    @JavascriptInterface
    public void start_app_page(String json)
    {
        try
        {
            StartAppPageJsonModel model = JSON.parseObject(json, StartAppPageJsonModel.class);

            String packename = SDPackageUtil.getPackageName();
            String target = model.getAndroid_page();

            Intent intent = new Intent();
            intent.setClassName(packename, target);

            if (!TextUtils.isEmpty(model.getData()))
            {
                intent.putExtra("data", model.getData());
            }

            startActivity(intent);
        } catch (Exception e)
        {
            e.printStackTrace();
            SDToast.showToast("数据解析异常");
        }

    }

    @JavascriptInterface
    public void setTextToClipBoard(String msg_str)
    {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME)
        {
            Activity activity = SDActivityManager.getInstance().getLastActivity();
            ClipboardManager cbm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            if (!TextUtils.isEmpty(msg_str))
            {
                cbm.setText(msg_str);
                EventBus.getDefault().post(new SDBaseEvent(text, EnumEventTag.EVENT_CLIPBOARDTEXT.ordinal()));
            } else
            {
                SDToast.showToast("您还未复制内容哦");
            }
        }
    }

    @JavascriptInterface
    public void savePhotoToLocal(String img_url_str)
    {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME)
        {
            RequestHandler handler = new RequestHandler(mActivity);
            handler.savePicture(img_url_str);
        }
    }

    public boolean isActModelNull(BaseActModel actModel)
    {
        if (actModel != null)
        {
            if (!TextUtils.isEmpty(actModel.getShow_err()))
            {
                SDToast.showToast(actModel.getShow_err());
            }
            return false;
        } else
        {
            SDToast.showToast("接口访问失败或者json解析出错!");
            return true;
        }
    }

}
