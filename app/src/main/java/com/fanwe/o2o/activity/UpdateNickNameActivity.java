package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppUpdateNickNameActModel;

import org.xutils.view.annotation.ViewInject;

/**
 * 修改昵称
 * Created by Administrator on 2017/2/11.
 */

public class UpdateNickNameActivity extends BaseTitleActivity
{
    /**
     * 会员昵称
     */
    public static final String EXTRA_NICK_NAME = "extra_nick_name";

    @ViewInject(R.id.et_nick_name)
    private EditText et_nick_name;
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    private String nickName;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_update_nickname);
        initData();
    }

    private void initData()
    {
        title.setMiddleTextTop("修改昵称");
        nickName = getIntent().getStringExtra(EXTRA_NICK_NAME);
        SDViewBinder.setTextView(et_nick_name,nickName);

        if (TextUtils.isEmpty(nickName))
            nickName = "";
        tv_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestUpdateNickName();
            }
        });
    }

    private void requestUpdateNickName()
    {
        nickName = et_nick_name.getText().toString();
        if (TextUtils.isEmpty(nickName))
        {
            SDToast.showToast("请输入新的昵称");
            return;
        }
        showProgressDialog("");
        CommonInterface.requestUpdateNickName(nickName, new AppRequestCallback<AppUpdateNickNameActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    AppConfig.setUserName(nickName);
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
