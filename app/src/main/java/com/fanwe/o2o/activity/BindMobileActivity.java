package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.customview.SDSendValidateButton.SDSendValidateButtonListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.event.EBindMobileSuccess;
import com.fanwe.o2o.event.EConfirmImageCode;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.Sms_send_sms_codeActModel;
import com.fanwe.o2o.model.User_infoModel;
import com.fanwe.o2o.R;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

/**
 * 绑定手机号
 *
 * @author Administrator
 */
public class BindMobileActivity extends BaseTitleActivity
{
    /**
     * 标题内容显示
     */
    public static final String EXTRA_TITLE = "extra_title";

    public static final String EXTRA_VALUE = "验证绑定手机号";
    /**
     * 手机号
     */
    public static final String EXTRA_MOBILE = "extra_mobile";
    /**
     * 已绑定手机的第二步验证参数
     */
    public static final String EXTRA_IS_LUCK = "extra_is_luck";

    @ViewInject(R.id.et_mobile)
    private EditText et_mobile;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton btn_send_code;
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    private String strMobile;
    private String strCode;
    private String strTitle;
    private int unique = 1;// 是否需要检测被占用 0:不检测 1:要检测是否被抢占（用于注册，绑定时使用）2:要检测是否存在（取回密码）3 检测会员是否绑定手机
    private int step = 2;//1:已有绑定手机的第一步 2:无绑定手机或已绑定手机的第二步
    private int is_luck;//已绑定手机的第二步验证参数
    private String extraMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bind_mobile);
        init();
    }

    private void init()
    {
        initTitle();
        initData();
        reSetBtnSendCode();
        initSDSendValidateButton();
    }

    private void initTitle()
    {
        strTitle = getIntent().getStringExtra(EXTRA_TITLE);
        extraMobile = getIntent().getStringExtra(EXTRA_MOBILE);
        is_luck = getIntent().getIntExtra(EXTRA_IS_LUCK,0);
        title.setLeftImageLeft(R.drawable.ic_o2o_back);
        if (!TextUtils.isEmpty(strTitle))
        {
            title.setMiddleTextTop(strTitle);
        }
        else
        {
            strTitle = "";
            title.setMiddleTextTop("绑定手机号");
        }
    }

    private void initData()
    {
        if (strTitle.equals(EXTRA_VALUE))
        {
            step = 1;
            unique = 3;
            if (!TextUtils.isEmpty(extraMobile))
            {
                String startMobile = extraMobile.substring(0,4);
                String endMobile = extraMobile.substring(8);
                SDViewBinder.setTextView(et_mobile,startMobile + "****" + endMobile);
                et_mobile.setEnabled(false);
            }
            SDViewBinder.setTextView(tv_submit,"下一步");
        }
        else
        {
            step = 2;
            unique = 1;
            SDViewBinder.setTextView(tv_submit,"确定");
        }

        tv_submit.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v) {
                clickSubmit();
            }
        });
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

    protected void clickSubmit()
    {
        if (validateParams())
        {
            showProgressDialog("");
            CommonInterface.requestBindMobile(strMobile, strCode, step, is_luck, new AppRequestCallback<User_infoModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.isOk())
                    {
                        if (strTitle.equals(EXTRA_VALUE))
                        {
                            Intent intent = new Intent(BindMobileActivity.this,BindMobileActivity.class);
                            intent.putExtra(BindMobileActivity.EXTRA_TITLE,"绑定新的手机号");
                            intent.putExtra(BindMobileActivity.EXTRA_IS_LUCK,actModel.getIs_luck());
                            startActivity(intent);
                            finish();
                        }else
                        {
                            SDEventManager.post(new EBindMobileSuccess());
                            finish();
                        }
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

    private boolean validateParams()
    {
        if (!TextUtils.isEmpty(extraMobile))
            strMobile = extraMobile;
        else
            strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号码");
            return false;
        }
        strCode = et_code.getText().toString();
        if (TextUtils.isEmpty(strCode))
        {
            SDToast.showToast("请输入验证码");
            return false;
        }

        return true;
    }

    private void initSDSendValidateButton()
    {
        btn_send_code.setmListener(new SDSendValidateButtonListener()
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
     * 发送验证码
     */
    protected void requestSendCode()
    {
        if (!TextUtils.isEmpty(extraMobile))
            strMobile = extraMobile;
        else
            strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号码");
            return;
        }

        showProgressDialog("");
        CommonInterface.requestValidateCode(strMobile, unique, new AppRequestCallback<Sms_send_sms_codeActModel>()
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

    public void onEventMainThread(EConfirmImageCode event)
    {
        if (SDActivityManager.getInstance().isLastActivity(this))
        {
            requestSendCode();
        }
    }
}
