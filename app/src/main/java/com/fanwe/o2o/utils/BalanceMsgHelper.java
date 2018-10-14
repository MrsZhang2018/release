package com.fanwe.o2o.utils;

import com.fanwe.o2o.app.App;
import com.fanwe.o2o.model.User_infoModel;

public class BalanceMsgHelper {

    private static final String CURRENT_USER_INFO = "current_user_info";
    private static User_infoModel user_infoModel;

    BalanceMsgHelper() {
    }

    private static class SingletonHolder {
        static BalanceMsgHelper INSTANCE = new BalanceMsgHelper();
    }

    public static BalanceMsgHelper get() {
        return SingletonHolder.INSTANCE;
    }

    public void setUserInfoBean(User_infoModel user_infoModel) {
        SharePreferencesUtil.addString(App.getApplication(), CURRENT_USER_INFO, JsonUtil.toJson(user_infoModel));
    }

    public User_infoModel getUserInfoBean() {
        if (user_infoModel == null) {
            String userInfo = SharePreferencesUtil.getString(App.getApplication(), CURRENT_USER_INFO, null);
            user_infoModel = JsonUtil.fromJson(userInfo, User_infoModel.class);
        }
        return user_infoModel;
    }

}
