package com.fanwe.o2o.http;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.o2o.activity.LoginActivity;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.ETempLogin;
import com.fanwe.o2o.http.AppRequestParams.ResponseDataType;
import com.fanwe.library.adapter.http.callback.SDModelRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.model.BaseActModel;
import com.sunday.eventbus.SDEventManager;

public abstract class AppRequestCallback<D> extends SDModelRequestCallback<D> {

    protected AppRequestParams requestParams;
    protected BaseActModel baseActModel;
    protected boolean isCache;
    protected String sessionId;

    @Override
    public void setRequestParams(SDRequestParams requestParams) {
        super.setRequestParams(requestParams);
        if (requestParams instanceof AppRequestParams)
        {
            this.requestParams = (AppRequestParams) requestParams;
        }
    }

    @Override
    protected void onStartAfter() {

    }

    @Override
    protected void onSuccessBefore(SDResponse resp) {
        if (requestParams != null) {
            LogUtil.i("onSuccessBefore:" + requestParams.getCtl() + "," + requestParams.getAct() + "：" + resp.getResult());

            String result = resp.getResult();
            if (!TextUtils.isEmpty(result)) {
                if (ApkConstant.DEBUG) {
                    if (result.contains("false")) {
                        SDToast.showToast(requestParams.getCtl() + "," + requestParams.getAct() + " false");
                    }
                }

                switch (requestParams.getResponseDataType()) {
                    case ResponseDataType.BASE64:
                        result = SDBase64.decodeToString(result);
                        break;
                    case ResponseDataType.JSON:

                        break;
                    case ResponseDataType.AES:
                        result = AESUtil.decrypt(result, ApkConstant.KEY_AES);
                        break;

                    default:
                        break;
                }

                // 解密后的result赋值回去
                resp.setResult(result);
                requestParams.setResponseDataType(ResponseDataType.JSON);
            }
        }
        // 调用父类方法转实体
        super.onSuccessBefore(resp);

        if (actModel != null) {
            if (actModel instanceof BaseActModel) {
                baseActModel = (BaseActModel) actModel;

                if (baseActModel != null)
                {
                    // 保存session
                     sessionId = baseActModel.getSess_id();
                    if (!TextUtils.isEmpty(sessionId))
                    {
                        AppConfig.setSessionId(sessionId);
                    }

                    // 保存refId
                    String refId = baseActModel.getRef_uid();
                    AppConfig.setRefId(refId);
                }

                if (requestParams != null) {
                    if (requestParams.isNeedShowActInfo()) {
                        SDToast.showToast(baseActModel.getInfo());
                    }
                    if (requestParams.isNeedCheckLoginState()) {
                        switch (baseActModel.getUser_login_status()) {
                            case Constant.UserLoginState.LOGIN:

                                break;
                            case Constant.UserLoginState.UN_LOGIN:
                                App.getApplication().clearAppsLocalUserModel();
//                                SDEventManager.post(new EUnLogin());
//                                startLoginActivity();
                                break;
                            case Constant.UserLoginState.TEMP_LOGIN:
                                SDEventManager.post(new ETempLogin());
                                startLoginActivity();
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    protected void startLoginActivity()
    {
        Activity lastActivity = SDActivityManager.getInstance().getLastActivity();
        if (lastActivity != null)
        {
            Intent intent = new Intent(lastActivity, LoginActivity.class);
            lastActivity.startActivity(intent);
//            if(lastActivity.getClass()!= MainActivity.class&&lastActivity.getClass()!=InitAdvsMultiActivity.class){
//                SDActivityManager.getInstance().onDestroy(lastActivity);
//                lastActivity.finish();
//            }
        }
    }

    @Override
    protected void onError(SDResponse resp) {
        if (requestParams != null) {
            LogUtil.i("onError:" + requestParams.getCtl() + "," + requestParams.getAct() + "：" + resp.getThrowable());

            if (ApkConstant.DEBUG) {
                SDToast.showToast(requestParams.getCtl() + "," + requestParams.getAct() + " " + String.valueOf(resp.getThrowable()));
            }
        }
    }

    @Override
    protected void onCancel(SDResponse resp) {

    }

    @Override
    protected void onFinish(SDResponse resp) {

    }

    @Override
    protected <T> T parseActModel(String result, Class<T> clazz) {
        return SDJsonUtil.json2Object(result, clazz);
    }


    public void showToast()
    {
        if (actModel != null) {
            if (requestParams != null) {
                if (requestParams.isNeedShowActInfo()) {
                    if (actModel instanceof BaseActModel) {
                        BaseActModel baseActModel = (BaseActModel) actModel;
                        SDToast.showToast(baseActModel.getInfo());
                    }
                }
            }
        }
    }
}
