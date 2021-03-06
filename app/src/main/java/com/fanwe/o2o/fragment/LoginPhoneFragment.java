package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.ModifyPwdActivity;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.event.EConfirmImageCode;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.http.AppSessionRequestCallback;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.Sms_send_sms_codeActModel;
import com.fanwe.o2o.model.User_infoModel;

import org.xutils.view.annotation.ViewInject;

import cn.xiaoneng.uiapi.Ntalker;

import static com.fanwe.o2o.activity.LoginActivity.EXTRA_WEB_URL;

public class LoginPhoneFragment extends LoginBaseFragment {

    public static final String EXTRA_PHONE_NUMBER = "extra_phone_number";

    @ViewInject(R.id.et_mobile)
    private ClearEditText mEtMobile;

    @ViewInject(R.id.et_code)
    private ClearEditText mEtCode;

    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton mBtnSendCode;

    @ViewInject(R.id.tv_user_agreement)
    private TextView tv_user_agreement;

    @ViewInject(R.id.tv_login)
    private TextView mTv_login;

    @ViewInject(R.id.tv_find_password)
    private TextView tv_find_password;

    private String mStrMobile;
    private String mStrCode;
    private String webUrl;


    @Override
    protected int onCreateContentView() {
        return R.layout.frag_phone_login;
    }

    @Override
    protected void init() {
        reSetBtnSendCode();
        getIntentData();
        registeClick();
        initSDSendValidateButton();
    }

    private void reSetBtnSendCode()
    {
        mBtnSendCode.setmTextColorEnable(getResources().getColor(R.color.text_content_deep));
        mBtnSendCode.setmTextColorDisable(getResources().getColor(R.color.text_content_deep));
        mBtnSendCode.setmBackgroundDisableResId(R.drawable.layer_white);
        mBtnSendCode.setmBackgroundEnableResId(R.drawable.layer_white);
        mBtnSendCode.setmTextEnable("发送验证码");
        mBtnSendCode.setmTextDisable("重新发送");
        mBtnSendCode.updateViewState(true);
    }

    private void getIntentData() {
        final Bundle bundle=getArguments();
        webUrl=bundle.getString(EXTRA_WEB_URL);
        String mobile = getActivity().getIntent().getStringExtra(EXTRA_PHONE_NUMBER);
        if (!TextUtils.isEmpty(mobile)) {
            mEtMobile.setText(mobile);
        }
    }

    /**
     * 初始化发送验证码按钮
     */
    private void initSDSendValidateButton() {
        mBtnSendCode.setmListener(new SDSendValidateButtonListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                requestSendCode();
            }
        });
    }

    private void registeClick() {
        mTv_login.setOnClickListener(this);
        tv_user_agreement.setOnClickListener(this);

        tv_find_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mTv_login)
        {
            clickLogin();
        }else if (v == tv_user_agreement)
        {
            String url = ApkConstant.SERVER_URL_WAP + "?ctl=user&act=protocol";
            clickWebView(url);
        }else if (v == tv_find_password)
        {
            clickFindPwd();
        }
    }

    /**
     * 找回密码
     */
    private void clickFindPwd()
    {
        Intent intent = new Intent(getActivity(), ModifyPwdActivity.class);
        intent.putExtra(ModifyPwdActivity.EXTRA_TITLE, "找回密码");
        startActivity(intent);
    }

    /**
     * 用户协议
     */
    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            getActivity().startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }

    /**
     * 快捷登录
     */
    private void clickLogin() {
        if (validateParams()) {
            requestShortcutLogin();
        }
    }

    private boolean validateParams() {
        if (TextUtils.isEmpty(mStrMobile)) {
            SDToast.showToast("请输入手机号码");
            return false;
        }

        mStrCode = mEtCode.getText().toString();
        if (TextUtils.isEmpty(mStrCode)) {
            SDToast.showToast("请输入验证码");
            return false;
        }
        return true;
    }

    /**
     * 验证码接口
     */
    private void requestSendCode() {
        mStrMobile = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(mStrMobile)) {
            SDToast.showToast("请输入手机号码");
            return;
        }

        showProgressDialog("");
        CommonInterface.requestValidateCode(mStrMobile, 0, new AppRequestCallback<Sms_send_sms_codeActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                switch (actModel.getStatus()) {
                    case -1:
                        break;
                    case 1:
                        mBtnSendCode.setmDisableTime(actModel.getLesstime());
                        mBtnSendCode.startTickWork();
                        break;

                    default:
                        break;
                }
            }

            @Override
            protected void onError(SDResponse resp) {
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
     * 快捷登录接口
     */
    private void requestShortcutLogin() {

        showProgressDialog("");
        CommonInterface.requestShortcutLogin(mStrMobile, mStrCode, new AppSessionRequestCallback<User_infoModel>(webUrl) {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    dealLoginNormalSuccess(actModel, true);
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    protected void dealLoginNormalSuccess(User_infoModel actModel, boolean postEvent) {
        int xiaoneng_login_status=Ntalker.getInstance().login(String.valueOf(actModel.getId()), actModel.getUser_name(),0 );
        String new_user = actModel.getNew_user();
        if(new_user!= null&&new_user.equals("1")){
            //首次使用手机快捷登录，前往修改密码
            Intent intent = new Intent(getActivity(), ModifyPwdActivity.class);
            intent.putExtra(ModifyPwdActivity.EXTRA_TITLE, "修改密码");
            intent.putExtra(ModifyPwdActivity.EXTRA_MOBILE, actModel.getMobile());
            startActivity(intent);
        }
        LocalUserModel.dealLoginSuccess(actModel, postEvent);
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        if (mBtnSendCode != null) {
            mBtnSendCode.stopTickWork();
        }
        super.onDestroy();
    }

    public void onEventMainThread(EConfirmImageCode event) {
        if (SDActivityManager.getInstance().isLastActivity(getActivity())) {
            requestSendCode();
        }
    }
}