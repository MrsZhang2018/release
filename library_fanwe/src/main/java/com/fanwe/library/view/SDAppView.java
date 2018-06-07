package com.fanwe.library.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.library.activity.SDBaseActivity.SDActivityLifeCircleListener;
import com.fanwe.library.activity.SDBaseActivity.SDActivityOnActivityResultListener;
import com.fanwe.library.utils.SDViewUtil;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;

/**
 * 如果手动的new对象的话Context必须传入Activity对象
 * 
 * @author Administrator
 * @date 2016-6-29 上午11:27:25
 */
public class SDAppView extends LinearLayout implements View.OnClickListener, SDEventObserver, SDActivityLifeCircleListener,
		SDActivityOnActivityResultListener
{

	private Activity mActivity;
	private SDVisibleStateListener listenerVisibleState;

	public SDAppView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		baseInit();
	}

	public SDAppView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		baseInit();
	}

	public SDAppView(Context context)
	{
		super(context);
		baseInit();
	}

	public void setListenerVisibleState(SDVisibleStateListener listenerVisibleState)
	{
		this.listenerVisibleState = listenerVisibleState;
	}

	public void setActivity(Activity activity)
	{
		this.mActivity = activity;
	}

	public Activity getActivity()
	{
		return mActivity;
	}

	private void baseInit()
	{
		Context context = getContext();
		if (context instanceof Activity)
		{
			mActivity = (Activity) context;
		}
		SDEventManager.register(this);

		int layoutId = onCreateContentView();
		if (layoutId != 0)
		{
			setContentView(layoutId);
		}

		//init();
	}

	protected int onCreateContentView()
	{
		return 0;
	}

	public void setContentView(int layoutId)
	{
		LayoutInflater.from(getContext()).inflate(layoutId, this, true);
	}

	/**
	 * 为了统一规范，子类的初始化操作重写此方法，然后在需要初始化的地方调用，父类不调用此方法
	 */
	protected void init()
	{

	}

	public void notifyVisibleStateListener()
	{
		if (listenerVisibleState != null)
		{
			switch (getVisibility())
			{
			case View.GONE:
				listenerVisibleState.onGone(this);
				break;
			case View.VISIBLE:
				listenerVisibleState.onVisible(this);
				break;
			case View.INVISIBLE:
				listenerVisibleState.onInvisible(this);
				break;

			default:
				break;
			}
		}
	}

	public void show()
	{
		SDViewUtil.show(this);
		notifyVisibleStateListener();
	}

	public void hide()
	{
		SDViewUtil.hide(this);
		notifyVisibleStateListener();
	}

	public void invisible()
	{
		SDViewUtil.invisible(this);
		notifyVisibleStateListener();
	}

	@Override
	public void onEvent(SDBaseEvent event)
	{
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
	}

	@Override
	public void onEventBackgroundThread(SDBaseEvent event)
	{
	}

	@Override
	public void onEventAsync(SDBaseEvent event)
	{
	}

	@Override
	public void onCreate(Bundle savedInstanceState, Activity activity)
	{
	}

	@Override
	public void onStart(Activity activity)
	{
	}

	@Override
	public void onRestart(Activity activity)
	{
	}

	@Override
	public void onResume(Activity activity)
	{
	}

	@Override
	public void onPause(Activity activity)
	{
	}

	@Override
	public void onStop(Activity activity)
	{
	}

	@Override
	public void onDestroy(Activity activity)
	{
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity)
	{
	}

	@Override
	public void onClick(View v)
	{

	}

	@Override
	protected void onDetachedFromWindow()
	{
		SDEventManager.unregister(this);
		super.onDetachedFromWindow();
	}
}
