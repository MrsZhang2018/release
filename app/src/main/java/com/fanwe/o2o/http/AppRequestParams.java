package com.fanwe.o2o.http;

import android.text.TextUtils;

import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.model.LocalUserModel;

/**
 * Created by luodong on 2016/12/13.
 */
public class AppRequestParams extends SDRequestParams {

    private boolean isNeedShowActInfo = true;
    private boolean isNeedShowErrorTip = true;

    public static final class RequestDataType {
        public static final int BASE64 = 0;
        public static final int AES = 4;
    }

    public static final class ResponseDataType {
        public static final int BASE64 = 0;
        public static final int JSON = 1;
        public static final int AES = 4;
    }

    public AppRequestParams() {
        super();
        setUrl(ApkConstant.SERVER_URL_API);

        putUser();
        putSessionId();
        putAct("index");
        putCityId();
        putLocation();
        putRefId();
        put("from", "app");
        put("r_type", "0");
        put("version_name", SDPackageUtil.getVersionName());
    }

    public void putUser() {
        LocalUserModel user = LocalUserModelDao.queryModel();
        if (user != null) {
            put("email", user.getUser_name());
            put("pwd", user.getUser_pwd());
        }
    }

    public void putSessionId() {
        String sessionId = AppConfig.getSessionId();
        if (!TextUtils.isEmpty(sessionId)) {
            put("sess_id", sessionId);
        }
    }

    private void putRefId()
    {
        String refId = AppConfig.getRefId();
        if (!TextUtils.isEmpty(refId))
        {
            put("ref_uid", refId);
        }
    }


    public void putCityId()
    {
        put("city_id", AppRuntimeWorker.getCity_id());
    }

    public void putLocation()
    {
        if (BaiduMapManager.getInstance().hasLocationSuccess())
        {
            put("m_latitude", BaiduMapManager.getInstance().getLatitude());
            put("m_longitude", BaiduMapManager.getInstance().getLongitude());
        }
    }

    public boolean isNeedShowActInfo()
    {
        return isNeedShowActInfo;
    }

    public void setNeedShowActInfo(boolean isNeedShowActInfo)
    {
        this.isNeedShowActInfo = isNeedShowActInfo;
    }

    public boolean isNeedShowErrorTip()
    {
        return isNeedShowErrorTip;
    }

    public void setIsNeedShowErrorTip(boolean isNeedShowErrorTip)
    {
        this.isNeedShowErrorTip = isNeedShowErrorTip;
    }

}
