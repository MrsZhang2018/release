package com.fanwe.o2o.http;

import android.text.TextUtils;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.ETempLogin;
import com.fanwe.o2o.event.EUnLogin;
import com.fanwe.o2o.model.BaseActModel;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Administrator on 2017/3/3.
 */

public abstract class BaseAppRequestCallbackWithEvent<D> extends AppRequestCallback<D> {
  private boolean hasJump2Login = false;

  public BaseAppRequestCallbackWithEvent(boolean hasJump2Login) {
    this.hasJump2Login = hasJump2Login;
  }

  @Override
  protected void onSuccessBefore(SDResponse resp) {
    if (requestParams != null) {
      LogUtil.i("onSuccessBefore:"
          + requestParams.getCtl()
          + ","
          + requestParams.getAct()
          + "："
          + resp.getResult());

      String result = resp.getResult();
      if (!TextUtils.isEmpty(result)) {
        if (ApkConstant.DEBUG) {
          if (result.contains("false")) {
            SDToast.showToast(requestParams.getCtl() + "," + requestParams.getAct() + " false");
          }
        }

        switch (requestParams.getResponseDataType()) {
          case AppRequestParams.ResponseDataType.BASE64:
            result = SDBase64.decodeToString(result);
            break;
          case AppRequestParams.ResponseDataType.JSON:

            break;
          case AppRequestParams.ResponseDataType.AES:
            result = AESUtil.decrypt(result, ApkConstant.KEY_AES);
            break;

          default:
            break;
        }

        // 解密后的result赋值回去
        resp.setResult(result);
        requestParams.setResponseDataType(AppRequestParams.ResponseDataType.JSON);
      }
    }
    // 调用父类方法转实体
    if(this.clazz != null) {
      this.actModel = this.parseActModel(resp.getResult(), this.clazz);
    }

    if (actModel != null) {
      if (actModel instanceof BaseActModel) {
        baseActModel = (BaseActModel) actModel;

        if (baseActModel != null) {
          // 保存session
          String session = baseActModel.getSess_id();
          if (!TextUtils.isEmpty(session)) {
            AppConfig.setSessionId(session);
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
                SDEventManager.post(new EUnLogin());
//                if (!hasJump2Login) {
                  startLoginActivity();
//                }
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
}
