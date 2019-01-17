package com.fanwe.o2o.fragment;

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
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.ModifyPwdActivity;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.http.AppSessionRequestCallback;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.User_infoModel;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.view.annotation.ViewInject;

import cn.xiaoneng.uiapi.Ntalker;

import static com.fanwe.o2o.activity.LoginActivity.EXTRA_WEB_URL;

public class LoginFragment extends LoginBaseFragment
{

	@ViewInject(R.id.et_account)
	private ClearEditText et_account;
	@ViewInject(R.id.et_pwd)
	private ClearEditText et_pwd;
	@ViewInject(R.id.ll_show)
	private LinearLayout ll_show;
	@ViewInject(R.id.iv_show)
	private ImageView iv_show;
	@ViewInject(R.id.tv_login)
	private TextView tv_login;
	@ViewInject(R.id.tv_find_password)
	private TextView tv_find_password;
	@ViewInject(R.id.tv_user_agreement)
	private TextView tv_user_agreement;

	private String strUserName;
	private String strPassword;
	private String webUrl;
	private boolean isShow;

	@Override
	protected int onCreateContentView()
	{
		return R.layout.frag_login;
	}

	@Override
	protected void init()
	{
		getIntentData();
		initViewState();
		registeClick();
	}

	private void registeClick()
	{
		ll_show.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		tv_user_agreement.setOnClickListener(this);
		tv_find_password.setOnClickListener(this);
	}

	private void getIntentData(){
		final Bundle bundle=getArguments();
		webUrl=bundle.getString(EXTRA_WEB_URL);
	}

	private void initViewState()
	{
		et_account.setText(AppConfig.getUserName());
		validatePassword();
	}

	private void validatePassword()
	{
		LocalUserModel user = LocalUserModelDao.queryModel();
		if (user != null)
		{
			String userName = user.getUser_name();
			if (!TextUtils.isEmpty(userName))
			{
				et_account.setText(userName);
				et_account.setEnabled(false);
				et_pwd.setHint("为了保证账户安全，请重新验证密码");
			}
		}
	}

	private void clickLoginNormal()
	{
		if (validateParam())
		{
			showProgressDialog("");
			CommonInterface.requestLoginNormal(strUserName,strPassword,new AppSessionRequestCallback<User_infoModel>(
					webUrl)
			{
				@Override
				protected void onSuccess(SDResponse sdResponse)
				{
					if (actModel.isOk())
					{
						int xiaoneng_login_status=Ntalker.getInstance().login(String.valueOf(actModel.getId()), actModel.getUser_name(),0 );
//						SDToast.showToast("小能登入状态 0为成功,:" + xiaoneng_login_status);

						DbCookieStore.INSTANCE.getCookies();

						AppConfig.setUserName(actModel.getUser_name());
						LocalUserModel.dealLoginSuccess(actModel, true);
						getActivity().finish();
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
		strUserName = et_account.getText().toString();
		if (TextUtils.isEmpty(strUserName))
		{
			SDToast.showToast("请输入手机号/用户名");
			et_account.requestFocus();
			return false;
		}
		strPassword = et_pwd.getText().toString();
		if (TextUtils.isEmpty(strPassword))
		{
			SDToast.showToast("请输入密码");
			et_pwd.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v == ll_show)
		{
			clickShowPwd();
		}else if (v == tv_login)
		{
			clickLoginNormal();
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
			Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
			intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
			getActivity().startActivity(intent);
		}else
			SDToast.showToast("url为空");
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

}