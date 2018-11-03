package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.event.EConfirmImageCode;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.Sms_send_sms_codeActModel;
import com.fanwe.o2o.model.User_infoModel;
import com.fanwe.o2o.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 找回（修改）密码
 */
public class ModifyPwdActivity extends BaseTitleActivity {
    /**
     * 页面标题(String)
     */
    public static final String EXTRA_TITLE = "extra_title";
    /**
     * 手机号
     */
    public static final String EXTRA_MOBILE = "extra_mobile";

    @ViewInject(R.id.et_mobile)
    private EditText et_mobile;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.ll_show)
    private LinearLayout ll_show;
    @ViewInject(R.id.iv_show)
    private ImageView iv_show;
    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton btn_send_code;
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    private String strTitle;

    private String strMobile;
    private String strCode;
    private String strPwd;
    private int unique = 2;// 是否需要检测被占用 0:不检测 1:要检测是否被抢占（用于注册，绑定时使用）2:要检测是否存在（取回密码）3 检测会员是否绑定手机
    private String extraMobile;
    private boolean isShow;

    private String msg_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modify_pwd);
        init();
    }

    private void init() {
        getIntentData();
        initTitle();
        reSetBtnSendCode();
        registeClick();
        initSDSendValidateButton();
        showBindPhoneDialog();
    }

    private void getIntentData() {
        strTitle = getIntent().getStringExtra(EXTRA_TITLE);
        extraMobile = getIntent().getStringExtra(EXTRA_MOBILE);
    }

    private void initTitle() {
        title.setMiddleTextTop(strTitle);
    }

    private void reSetBtnSendCode() {
        btn_send_code.setmTextColorEnable(getResources().getColor(R.color.text_content_deep));
        btn_send_code.setmTextColorDisable(getResources().getColor(R.color.text_content_deep));
        btn_send_code.setmBackgroundDisableResId(R.drawable.layer_white);
        btn_send_code.setmBackgroundEnableResId(R.drawable.layer_white);
        btn_send_code.setmTextEnable("发送验证码");
        btn_send_code.setmTextDisable("重新发送");
        btn_send_code.updateViewState(true);
    }

    private void showBindPhoneDialog() {
//        LocalUserModel user = LocalUserModelDao.queryModel();
        if (!TextUtils.isEmpty(extraMobile)) {
            String startMobile = extraMobile.substring(0, 4);
            String endMobile = extraMobile.substring(8);
            et_mobile.setEnabled(false);
            SDViewBinder.setTextView(et_mobile, startMobile + "****" + endMobile);
        } else {
            et_mobile.setEnabled(true);
        }
    }

    /**
     * 初始化发送验证码按钮
     */
    private void initSDSendValidateButton() {
        btn_send_code.setmListener(new SDSendValidateButtonListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                requestSendCode();
            }
        });
    }

    private void requestSendCode() {
        if (!TextUtils.isEmpty(extraMobile))
            strMobile = extraMobile;
        else
            strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile)) {
            SDToast.showToast("请输入手机号码");
            return;
        }

        showProgressDialog("");
        CommonInterface.requestValidateCode(strMobile, unique, new AppRequestCallback<Sms_send_sms_codeActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    btn_send_code.setmDisableTime(actModel.getLesstime());
                    btn_send_code.startTickWork();
                    msg_id = actModel.msg_id;
                    Log.e("msg_id", "msg_id = " + msg_id);
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

    private void registeClick() {
        ll_show.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_show:
                clickShowPwd();
                break;
            case R.id.tv_submit:
                clickSubmit();
                break;
            default:
                break;
        }
    }

    /**
     * 是否显示密码
     */
    private void clickShowPwd() {
        if (isShow) {
            isShow = false;
            iv_show.setImageResource(R.drawable.ic_o2o_show_pwd);
            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isShow = true;
            iv_show.setImageResource(R.drawable.ic_o2o_hide_pwd);
            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void clickSubmit() {
        if (validateParam()) {
            showProgressDialog("");
            CommonInterface.requestModifyPassword(strMobile, strCode, strPwd, msg_id, new AppRequestCallback<User_infoModel>() {
                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if (actModel.isOk()) {
                        LocalUserModel.dealLoginSuccess(actModel, false);
                        finish();
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

    }

    private boolean validateParam() {
        if (!TextUtils.isEmpty(extraMobile))
            strMobile = extraMobile;
        else
            strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile)) {
            SDToast.showToast("请输入手机号码");
            return false;
        }

        strPwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(strPwd)) {
            SDToast.showToast("请输入密码");
            return false;
        }

        strCode = et_code.getText().toString();
        if (TextUtils.isEmpty(strCode)) {
            SDToast.showToast("请输入验证码");
            return false;
        }

        return true;
    }

    public void onEventMainThread(EConfirmImageCode event) {
        if (SDActivityManager.getInstance().isLastActivity(this)) {
            requestSendCode();
        }
    }

}