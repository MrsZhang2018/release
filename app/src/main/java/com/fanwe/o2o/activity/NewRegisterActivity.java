package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.Sms_send_sms_codeActModel;
import com.fanwe.o2o.model.User_infoModel;
import com.fanwe.o2o.model.ValidationCodeModel;

import org.xutils.view.annotation.ViewInject;

public class NewRegisterActivity extends BaseActivity {

    @ViewInject(R.id.tv_cancel)
    TextView tv_cancel;

    @ViewInject(R.id.et_mobile)
    EditText et_mobile;

    @ViewInject(R.id.et_pwd)
    EditText et_pwd;

    @ViewInject(R.id.et_pwd_confirm)
    EditText et_pwd_confirm;

    @ViewInject(R.id.et_code)
    EditText et_code;

    @ViewInject(R.id.btn_send_code)
    SDSendValidateButton mBtnSendCode;

    @ViewInject(R.id.btn_reg)
    Button btn_reg;

    private String msg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg_new);
        initClick();

        reSetBtnSendCode();
        initSDSendValidateButton();
    }

    private void initClick() {
        tv_cancel.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    private void reSetBtnSendCode() {
        mBtnSendCode.setmTextColorEnable(getResources().getColor(R.color.white));
        mBtnSendCode.setmTextColorDisable(getResources().getColor(R.color.text_content_deep));
        mBtnSendCode.setmBackgroundDisableResId(R.drawable.layer_white);
        mBtnSendCode.setmBackgroundEnableResId(R.drawable.login_btn_code_bg);
        mBtnSendCode.setmTextEnable("发送验证码");
        mBtnSendCode.setmTextDisable("重新发送");
        mBtnSendCode.updateViewState(true);
    }

    /**
     * 初始化发送验证码按钮
     */
    private void initSDSendValidateButton() {
        mBtnSendCode.setmListener(new SDSendValidateButton.SDSendValidateButtonListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                requestSendCode();
            }
        });
    }

    /**
     * 验证码接口
     */
    private void requestSendCode() {
        String mStrMobile = et_mobile.getText().toString();
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
                        sdResponse.getResult();
                        msg_id = actModel.msg_id;
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.btn_reg:
                String sms_verify = et_code.getText().toString().trim();
                getValidationCode(msg_id, sms_verify);
                break;
        }
    }

    /**
     * 验证验证码
     */
    public void getValidationCode(String msg_id, String sms_verify) {
        CommonInterface.requestValidationCode(msg_id, sms_verify, new AppRequestCallback<ValidationCodeModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                String mStrMobile = et_mobile.getText().toString();
                if (TextUtils.isEmpty(mStrMobile)) {
                    SDToast.showToast("请输入手机号码");
                    return;
                }
                String mStrPwd = et_pwd.getText().toString();
                String mStrPwdCon = et_pwd_confirm.getText().toString();
                if (TextUtils.isEmpty(mStrPwd) || TextUtils.isEmpty(mStrPwdCon)) {
                    SDToast.showToast("请输入密码");
                    return;
                }
                if (!mStrPwd.equals(mStrPwdCon)) {
                    SDToast.showToast("两次输入密码不一样");
                    return;
                }
                String mStrCode = et_code.getText().toString();
                if (TextUtils.isEmpty(mStrCode)) {
                    SDToast.showToast("请输入验证码");
                    return;
                }
                getRegister(mStrMobile, mStrPwd, mStrCode);
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }
        });
    }

    /**
     * 注册
     */
    public void getRegister(String mStrMobile, String user_pwd, String sms_verify) {
        CommonInterface.requestRegister(mStrMobile, user_pwd, sms_verify, new AppRequestCallback<User_infoModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    finish();
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBtnSendCode != null) {
            mBtnSendCode.stopTickWork();
        }
    }

}
