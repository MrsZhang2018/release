package com.fanwe.hybrid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.fanwe.gesture.activity.CreateGesturePasswordActivity;
import com.fanwe.gesture.activity.UnlockGesturePasswordActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.common.ImageMultithreadingManager;
import com.fanwe.hybrid.common.ImageMultithreadingManager.ImageMultithreadingManagerListening;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.constant.Constant.CommonSharePTag;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.dao.LoginSuccessModelDao;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.model.LoginSuccessModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.config.SDConfig;

import java.util.ArrayList;

import cn.fanwe.yi.R;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-16 下午4:39:42 类说明 启动页
 */
public class InitActivity extends BaseActivity
{
	public static final long mInitTime = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_init);
		init();
	}

	private void init()
	{
		new Handler().postDelayed(new Runnable()
		{

			public void run()
			{
				requestInit();
			}

		}, mInitTime);
	}

	private void requestInit()
	{
		AppRequestParams params = new AppRequestParams();
		params.setUrl(ApkConstant.SERVER_URL_INIT_URL);
		AppHttpUtil.getInstance().get(params, new AppRequestCallback<InitActModel>()
		{

			@Override
			protected void onSuccess(SDResponse resp)
			{
				Log.e("TAG","onSuccess***"+resp.getResult()+"***"+resp.getHttpCode()+"***"+resp.getThrowable().toString());
				InitActModelDao.insertOrUpdate(actModel);
				startMainAct();
			}

			@Override
			protected void onError(SDResponse resp)
			{
				Log.e("TAG","onError***"+resp.getResult()+"***"+resp.getHttpCode()+"***"+resp.getThrowable().toString());
				super.onError(resp);
				startMainAct();
			}

		});
	}

	private void startMainAct()
	{
		Intent intent = null;
		final InitActModel initActModel = InitActModelDao.query();
		if (initActModel != null && !TextUtils.isEmpty(initActModel.getAd_img()))
		{
			ArrayList<String> mImg = new ArrayList<String>();
			mImg.add(initActModel.getAd_img());
			ImageMultithreadingManager i = new ImageMultithreadingManager(mImg);
			i.setmImageMultithreadingManagerListening(new ImageMultithreadingManagerListening()
			{
				@Override
				public void onComplete()
				{
					startInitAdImg(initActModel.getAd_img());
				}
			});
			i.loadImage();
		} else
		{
			int is_first_open_app = SDConfig.getInstance().getInt(CommonSharePTag.IS_FIRST_OPEN_APP, 0);
			boolean is_open_adv = getResources().getBoolean(R.bool.is_open_adv);
			if (is_first_open_app != 1 && is_open_adv)
			{
				ArrayList<String> array = new ArrayList<String>();
				array.add("adv_img_1");
				array.add("adv_img_2");
				array.add("adv_img_3");
				startInitAdvList(array);
			} else
			{
				LoginSuccessModel model = LoginSuccessModelDao.queryModelCurrentLogin();
				boolean is_open_gesture = getResources().getBoolean(R.bool.is_open_gesture);
				if (model != null && is_open_gesture)
				{
					if (!App.getApplication().mLockPatternUtils.savedPatternExists())
					{
						intent = new Intent(InitActivity.this, CreateGesturePasswordActivity.class);
						intent.putExtra(CreateGesturePasswordActivity.EXTRA_CODE, CreateGesturePasswordActivity.ExtraCodel.EXTRA_CODE_0);
					} else
					{
						intent = new Intent(InitActivity.this, UnlockGesturePasswordActivity.class);
					}
				} else
				{
					intent = new Intent(InitActivity.this, MainActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}
	}

	private void startInitAdvList(ArrayList<String> array)
	{
		Intent intent = new Intent(InitActivity.this, InitAdvListActivity.class);
		intent.putStringArrayListExtra(InitAdvListActivity.EXTRA_ARRAY, array);
		startActivity(intent);
		finish();
	}

	private void startInitAdImg(String ad_img)
	{
		Intent intent = new Intent(InitActivity.this, AdImgActivity.class);
		intent.putExtra(AdImgActivity.EXTRA_URL, ad_img);
		startActivity(intent);
		finish();
	}
}
