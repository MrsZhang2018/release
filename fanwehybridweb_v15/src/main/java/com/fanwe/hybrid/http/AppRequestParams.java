package com.fanwe.hybrid.http;

import android.text.TextUtils;

import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.constant.Constant.DeviceType;
import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDViewUtil;

import cn.fanwe.yi.R;

/**
 * Created by Administrator on 2016/3/30.
 */
public class AppRequestParams extends SDRequestParams
{

	public static final class RequestDataType
	{
		public static final int BASE64 = 0;
		public static final int AES = 4;
	}

	public static final class ResponseDataType
	{
		public static final int BASE64 = 0;
		public static final int JSON = 1;
		public static final int AES = 4;
	}

	public AppRequestParams()
	{
		super();
		setUrl(ApkConstant.SERVER_URL_INIT_URL);
		putSessionId();

		put("screen_width", SDViewUtil.getScreenWidth());
		put("screen_height", SDViewUtil.getScreenHeight());
		put("sdk_type", DeviceType.DEVICE_ANDROID);
		put("sdk_version",SDPackageUtil.getVersionCode());
		put("sdk_version_name",SDPackageUtil.getVersionName());
	}

	public void putSessionId()
	{
		String sessionId = SDConfig.getInstance().getString(R.string.config_session_id, null);
		if (!TextUtils.isEmpty(sessionId))
		{
			put("sess_id", sessionId);
		}
	}

}
