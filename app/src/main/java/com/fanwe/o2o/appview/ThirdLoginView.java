package com.fanwe.o2o.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.common.CommonOpenLoginSDK;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.User_infoModel;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class ThirdLoginView extends SDAppView {
    private LinearLayout mLlThridLogin;

    private LinearLayout mLlLoginSina;
    private LinearLayout mLlLoginQQ;
    private LinearLayout mLlLoginWx;

    public ThirdLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThirdLoginView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_third_login, this, true);

        mLlThridLogin = (LinearLayout) findViewById(R.id.ll_third_login);

        mLlLoginSina = (LinearLayout) findViewById(R.id.ll_login_sina);
        mLlLoginQQ = (LinearLayout) findViewById(R.id.ll_login_qq);
        mLlLoginWx = (LinearLayout) findViewById(R.id.ll_login_wx);

        initViewState();
        register();
    }

    private void initViewState() {
        String sinaAppKey = AppRuntimeWorker.getSina_app_key();
        String qqAppKey = AppRuntimeWorker.getQq_app_key();
        String wxAppKey = AppRuntimeWorker.getWx_app_key();
        if (TextUtils.isEmpty(sinaAppKey) && TextUtils.isEmpty(qqAppKey) && TextUtils.isEmpty(wxAppKey)) {
            SDViewUtil.hide(mLlThridLogin);
        } else {
            SDViewUtil.show(mLlThridLogin);

            if (TextUtils.isEmpty(sinaAppKey)) {
                SDViewUtil.hide(mLlLoginSina);
            } else {
                SDViewUtil.show(mLlLoginSina);
            }

            if (TextUtils.isEmpty(qqAppKey)) {
                SDViewUtil.hide(mLlLoginQQ);
            } else {
                SDViewUtil.show(mLlLoginQQ);
            }

            if (TextUtils.isEmpty(wxAppKey)) {
                SDViewUtil.hide(mLlLoginWx);
            } else {
                SDViewUtil.show(mLlLoginWx);
            }
        }
    }

    private void register() {
        mLlLoginSina.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clickLoginSina();
            }
        });

        mLlLoginQQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clickLoginQQ();
            }
        });

        mLlLoginWx.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clickLoginWx();
            }
        });
    }

    /**
     * 点击微信登录，先获取个人资料
     */
    private void clickLoginWx() {
        CommonOpenLoginSDK.loginWx(getActivity(), new UMAuthListener()
        {

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
            {
                SDToast.showToast("授权成功");
                String openid = data.get("openid");
                String access_token = data.get("access_token");
                String unionid = data.get("unionid");

                getWxPlatformInfo(openid,access_token,unionid);

            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t)
            {
                SDToast.showToast("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action)
            {
                SDToast.showToast("授权取消");
            }
        });

    }

    private void getWxPlatformInfo(final String openid, final String access_token, final String unionid) {
        CommonOpenLoginSDK.getShareAPI().getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
                String screen_name = data.get("screen_name");
                String profile_image_url = data.get("profile_image_url");
                requestLoginWx(openid, access_token, screen_name, unionid, profile_image_url);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
            }
        });
    }

    private void requestLoginWx(String openId, String access_token, String nickName, String unionid, String headimgurl) {
        showProgressDialog("");
        CommonInterface.requestLoginWx(openId,access_token,nickName,unionid,headimgurl,new AppRequestCallback<User_infoModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    LocalUserModel.dealLoginSuccess(actModel, true);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 点击qq登录,先获取个人资料
     */
    private void clickLoginQQ() {
        CommonOpenLoginSDK.umQQlogin(getActivity(), new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                SDToast.showToast("授权成功");
                String openid = data.get("openid");
                String access_token = data.get("access_token");

                getQQPlatformInfo(openid, access_token);

            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                SDToast.showToast("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                SDToast.showToast("授权取消");
            }
        });

    }

    private void getQQPlatformInfo(final String openid, final String access_token) {

        CommonOpenLoginSDK.getShareAPI().getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
                String screen_name = data.get("screen_name");
                requestQQLogin(openid, access_token, screen_name);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    protected void requestQQLogin(String openId, String access_token, String nickname) {
        showProgressDialog("");
        CommonInterface.requestQQLogin(openId,access_token,nickname,new AppRequestCallback<User_infoModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    LocalUserModel.dealLoginSuccess(actModel, true);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 点击新浪微博登录,先获取个人资料，然后登录
     */
    private void clickLoginSina() {
        CommonOpenLoginSDK.umSinalogin(getActivity(), new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                SDToast.showToast("授权成功");
                String access_token = data.get("access_token");
                String uid = data.get("uid");
                String userName = data.get("userName");
                requestSinaLogin(uid, access_token, userName);
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                SDToast.showToast("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                SDToast.showToast("授权取消");
            }
        });

    }

    protected void requestSinaLogin(String uid, String access_token, String nickname) {
        showProgressDialog("");
        CommonInterface.requestSinaLogin(uid,access_token,nickname,new AppRequestCallback<User_infoModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    LocalUserModel.dealLoginSuccess(actModel, true);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

}
