package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.fanwe.library.activity.WebViewActivity;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.fragment.WebViewFragment.EnumProgressMode;
import com.fanwe.library.fragment.WebViewFragment.EnumWebviewHeightMode;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.common.CommonOpenSDK;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.fragment.AppWebViewFragment;
import com.fanwe.o2o.listener.PayResultListner;
import com.fanwe.o2o.model.PaySdkModel;
import com.fanwe.o2o.model.WXPayStateModel;
import com.sunday.eventbus.SDBaseEvent;

/**
 * webview界面
 * 
 * @author js02
 * 
 */
public class AppWebViewActivity extends WebViewActivity implements PayResultListner
{

	/** 缩放到显示全部 (boolean) */
	public static final String EXTRA_SCALE_TO_SHOW_ALL = "extra_scale_to_show_all";

	private boolean mIsStartByAdvs = false;

	private boolean mScaleToShowAll = false;

	private boolean isShowTitle = true;

  private Handler mHandler;

	/** webview 是否显示title */
	public static final String EXTRA_IS_SHOW_TITLE = "extra_is_show_title";

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		init();
		super.onNewIntent(intent);
	}

	@Override
  protected void init(){
    super.init();
    mHandler=new Handler();
  }

	@Override
	protected void getIntentData()
	{
//		mIsStartByAdvs = getIntent().getBooleanExtra(BaseActivity.EXTRA_IS_ADVS, false);
		mScaleToShowAll = getIntent().getBooleanExtra(EXTRA_SCALE_TO_SHOW_ALL, false);
		mFragWebview.setScaleToShowAll(mScaleToShowAll);

		isShowTitle = getIntent().getBooleanExtra(EXTRA_IS_SHOW_TITLE, true);
		mFragWebview.setShowTitle(isShowTitle);
		super.getIntentData();
	}

	@Override
	protected WebViewFragment createFragment()
	{
		AppWebViewFragment fragment = new AppWebViewFragment();
		fragment.setmProgressMode(EnumProgressMode.NONE);
		fragment.setmWebviewHeightMode(EnumWebviewHeightMode.MATCH_PARENT);
		return fragment;
	}

	@Override
	public void onEventMainThread(final SDBaseEvent event) {
		super.onEventMainThread(event);
		switch (Constant.EnumEvent.valueOf(event.getTagInt())) {
			case EVENT_PAY_SDK:
//				PaySdkModel uc_Save_InchargeActModel = (PaySdkModel) event.getData();
//				openSDKPAY(uc_Save_InchargeActModel);
				break;
      case EVENT_WX_PAY_JS_BACK:
//        mHandler.post(new Runnable()
//        {
//          @Override
//          public void run()
//          {
//            WXPayStateModel stateModel=(WXPayStateModel) event.getData();
//            mFragWebview.getWebView().loadJsFunction("js_pay_sdk("+String.valueOf(stateModel.getState())+")");
//          }
//        });
        break;
			default:
				break;
		}
	}

	/**
	 * 用于接收银联支付的回调
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		String msg = null;
		final String re = data.getExtras().getString("pay_result");
		if (re.equalsIgnoreCase("success")) {
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 1);
				}
			});
		} else if (re.equalsIgnoreCase("fail")) {
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 6);
				}
			});
		} else if (re.equalsIgnoreCase("cancel")) {
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 4);
				}
			});
		}
	}


	public void openSDKPAY(PaySdkModel model)
	{
		String payCode = model.getPay_sdk_type();
		if (!TextUtils.isEmpty(payCode))
		{
			if (Constant.PaymentType.UPAPP.equalsIgnoreCase(payCode))
			{
				CommonOpenSDK.payUpApp(model, this, this);
			}else if (Constant.PaymentType.ALIPay.equalsIgnoreCase(payCode))
			{
				CommonOpenSDK.payAlipay(model, this, this);
			} else if (Constant.PaymentType.WXPAY.equalsIgnoreCase(payCode))
			{
				CommonOpenSDK.payWxPay(model, this, this);
			}
		} else
		{
			SDToast.showToast("payCode为空");
			onOther();
		}

	}


  @Override
  public void onSuccess()
  {  //接收支付宝回调
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 1);
			}
		});
  }

  @Override
  public void onDealing()
  {//接收支付宝回调
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 2);
			}
		});
  }

  @Override
  public void onFail()
  {//接收支付宝回调
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 3);
			}
		});
  }

  @Override
  public void onCancel()
  {//接收支付宝回调
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 4);
			}
		});
  }

  @Override
  public void onNetWork()
  {//接收支付宝回调
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 5);
			}
		});
  }

  @Override
  public void onOther()
  {//接收支付宝回调
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				mFragWebview.getWebView().loadJsFunction(Constant.JS_PAY_SDK, 6);
			}
		});
  }

	@Override
	public void finish()
	{
		if (mIsStartByAdvs)
		{
			startActivity(new Intent(this, MainActivity.class));
		}
		super.finish();
	}
}