package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.Sms_send_sms_codeActModel;
import com.fanwe.o2o.model.User_infoModel;
import com.fanwe.o2o.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 注册界面
 *
 * @author Administrator
 */
public class RegisterActivity extends BaseTitleActivity
{

    @ViewInject(R.id.et_mobile)
    private ClearEditText et_mobile;
    @ViewInject(R.id.et_pwd)
    private ClearEditText et_pwd;
    @ViewInject(R.id.ll_show)
    private LinearLayout ll_show;
    @ViewInject(R.id.iv_show)
    private ImageView iv_show;
    @ViewInject(R.id.et_code)
    private ClearEditText et_code;
    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton btn_send_code;
    @ViewInject(R.id.tv_user_agreement)
    private TextView tv_user_agreement;
    @ViewInject(R.id.tv_register)
    private TextView tv_register;

    private String strMobile;
    private String strPwd;
    private String strCode;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        init();
    }

    private void init()
    {
        initTitle();
        reSetBtnSendCode();
        initSDSendValidateButton();
        registeClick();
    }

    private void initTitle()
    {
        title.setMiddleTextTop("注册");
    }

    private void reSetBtnSendCode()
    {
        btn_send_code.setmTextColorEnable(getResources().getColor(R.color.text_content_deep));
        btn_send_code.setmTextColorDisable(getResources().getColor(R.color.text_content_deep));
        btn_send_code.setmBackgroundDisableResId(R.drawable.layer_white);
        btn_send_code.setmBackgroundEnableResId(R.drawable.layer_white);
        btn_send_code.setmTextEnable("发送验证码");
        btn_send_code.setmTextDisable("重新发送");
        btn_send_code.updateViewState(true);
    }

    private void registeClick()
    {
        ll_show.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_user_agreement.setOnClickListener(this);
    }

    /**
     * 初始化发送验证码按钮
     */
    private void initSDSendValidateButton()
    {
        btn_send_code.setmListener(new SDSendValidateButton.SDSendValidateButtonListener()
        {
            @Override
            public void onTick()
            {

            }
            @Override
            public void onClickSendValidateButton()
            {
                requestSendCode();
            }
        });
    }

    /**
     * 验证码接口
     */
    private void requestSendCode() 
    {
        strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号码");
            return;
        }

        showProgressDialog("");
        CommonInterface.requestValidateCode(strMobile, 0, new AppRequestCallback<Sms_send_sms_codeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                switch (actModel.getStatus())
                {
                    case -1:
                        break;
                    case 1:
                        btn_send_code.setmDisableTime(actModel.getLesstime());
                        btn_send_code.startTickWork();
                        break;
                    default:
                        break;
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_show:
                clickShowPwd();
                break;
            case R.id.tv_register:
                clickRegister();
                break;
            case R.id.tv_user_agreement:
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=user&act=protocol";
                clickWebView(url);
                break;
            default:
                break;
        }
    }

    /**
     *是否显示密码
     */
    private void clickShowPwd()
    {
        if (isShow)
        {
            isShow = false;
            iv_show.setImageResource(R.drawable.ic_o2o_show_pwd);
            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else
        {
            isShow = true;
            iv_show.setImageResource(R.drawable.ic_o2o_hide_pwd);
            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 用户协议
     */
    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(this, AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }

    private void clickRegister()
    {
        if (validateParam())
        {
            showProgressDialog("");
            CommonInterface.requestRegister(strMobile, strPwd, strCode, new AppRequestCallback<User_infoModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.isOk())
                    {
                        LocalUserModel.dealLoginSuccess(actModel, true);
                        AppConfig.setUserName(actModel.getUser_name());
                        finish();
                    }
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    super.onError(resp);
                }

                @Override
                protected void onFinish(SDResponse resp)
                {
                    super.onFinish(resp);
                    dismissProgressDialog();
                }
            });
        }
    }

    private boolean validateParam()
    {
        strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号码");
            return false;
        }

        //TODO 国外手机号不一定11位
        if (!strMobile.matches("[0-9]{8,15}"))
        {
            SDToast.showToast("请输入正确的手机号码");
            return false;
        }

        strPwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(strPwd))
        {
            SDToast.showToast("请输入密码");
            return false;
        }else
        {
            if (strPwd.trim().length() < 4)
            {
                SDToast.showToast("密码不能小于4位");
                return false;
            }
            if (strPwd.trim().length() > 30)
            {
                SDToast.showToast("密码不能多于30位");
                return false;
            }
        }

        strCode = et_code.getText().toString();
        if (TextUtils.isEmpty(strCode))
        {
            SDToast.showToast("请输入手机验证码");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
        if (btn_send_code != null) 
        {
            btn_send_code.stopTickWork();
        }
    }
}