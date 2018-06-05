package com.fanwe.o2o.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.o2o.adapter.InitAdvsPagerAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.model.InitActStart_pageModel;
import com.fanwe.o2o.model.Init_indexActModel;
import com.fanwe.o2o.R;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.http.AppRequestCallback;

import org.xutils.view.annotation.ViewInject;

/**
 * 初始化Activity
 */
public class InitAdvsMultiActivity extends BaseActivity {

    /**
     * 广告图片显示时间
     */
    private static final long ADVS_DISPLAY_TIME = 2000;

    /**
     * 正常初始化成功后显示时间
     */
    private static final long NORMAL_DISPLAY_TIME = 1000;

    @ViewInject(R.id.btn_skip)
    private Button mBtn_skip;

    @ViewInject(R.id.spv_content)
    private SDSlidingPlayView mSpvAd;

    private InitAdvsPagerAdapter mAdapter;

    private SDTimer mTimer = new SDTimer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_init_advs_multi);
        init();
    }

    private void init() {
//		startLocation();
//		enableUmengPush();
//		MobclickAgent.updateOnlineConfig(this);
        registerClick();
        initSlidingPlayView();
        requestInitInterface();
    }

    private void startLocation() {
        BaiduMapManager.getInstance().startLocation(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation arg0) {
            }
        });
    }

    private void enableUmengPush() {
//		LogUtil.i("isenable:" + UmengPushManager.getPushAgent().isEnabled() + "registionId:" + UmengPushManager.getPushAgent().getRegistrationId());
//		UmengPushManager.getPushAgent().enable(new IUmengRegisterCallback()
//		{
//
//			@Override
//			public void onRegistered(String arg0)
//			{
//				LogUtil.i(arg0);
//			}
//		});
    }

    private void registerClick() {
        mBtn_skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
    }

    private void initSlidingPlayView() {
        mSpvAd.getViewPager().setMeasureMode(SDViewPager.MeasureMode.NORMAL);
        mSpvAd.setNormalImageResId(R.drawable.ic_page_normal);
        mSpvAd.setSelectedImageResId(R.drawable.ic_page_select);
        mSpvAd.setViewPagerTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (mAdapter != null && mAdapter.getCount() > 1) {
                                    mTimer.stopWork();
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                }
        );
    }

    private void requestInitInterface() {
        CommonInterface.requestInit(new AppRequestCallback<Init_indexActModel>() {
            private boolean nSuccess = false;

            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    nSuccess = true;
                    dealInitSuccess(actModel);
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
                nSuccess = false;
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                if (!nSuccess) {
                    startMainActivity();
                }
            }
        });
    }

    protected void dealInitSuccess(Init_indexActModel model) {
        List<InitActStart_pageModel> listModel = model.getStart_page_new();
        bindAdvsImages(listModel);
    }

    protected void bindAdvsImages(List<InitActStart_pageModel> listModel) {
        if (!SDCollectionUtil.isEmpty(listModel)) {
            mAdapter = new InitAdvsPagerAdapter(listModel, mActivity);
            mAdapter.setItemClicklistener(new SDAdapter.ItemClickListener<InitActStart_pageModel>() {
                @Override
                public void onClick(int position, InitActStart_pageModel item, View view) {
//					InitActStart_pageModel model = mAdapter.getItemModel(position);
//					if (model != null)
//					{
//						int type = model.getType();
//						Intent intent = AppRuntimeWorker.createIntentByType(type, model.getData(), false);
//						if (intent != null)
//						{
//							try
//							{
//								mTimer.stopWork();
//								intent.putExtra(BaseActivity.EXTRA_IS_ADVS, true);
//								startActivity(intent);
//								finish();
//							} catch (Exception e)
//							{
//								e.printStackTrace();
//							}
//						}
//					}
                }
            });
            mSpvAd.setAdapter(mAdapter);
            startAdvsDisplayTimer();
            SDViewUtil.show(mBtn_skip);
        } else {
            startNormalDisplayTimer();
        }
    }

    private void startAdvsDisplayTimer() {
        mTimer.startWork(ADVS_DISPLAY_TIME, Long.MAX_VALUE, new SDTimerListener() {
            @Override
            public void onWorkMain() {
                startMainActivity();
            }

            @Override
            public void onWork() {

            }
        });
    }

    private void startNormalDisplayTimer() {
        mTimer.startWork(NORMAL_DISPLAY_TIME, Long.MAX_VALUE, new SDTimerListener() {
            @Override
            public void onWorkMain() {
                startMainActivity();
            }

            @Override
            public void onWork() {

            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mTimer.stopWork();
        super.onDestroy();
    }

}
