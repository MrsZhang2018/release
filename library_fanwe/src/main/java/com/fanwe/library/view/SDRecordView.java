package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.library.utils.SDViewUtil;

public class SDRecordView extends LinearLayout
{

	private View recordView;
	private View cancelView;

	private boolean isTouchCancelView = false;
	private boolean isCancel = false;
	private boolean isIntercept = false;

	private RecordViewListener listener;

	public void setListener(RecordViewListener listener)
	{
		this.listener = listener;
	}

	public void setCancelView(View cancelView)
	{
		this.cancelView = cancelView;
	}

	public void setRecordView(View recordView)
	{
		this.recordView = recordView;
	}

	public SDRecordView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public SDRecordView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public SDRecordView(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if (SDViewUtil.isTouchView(recordView, ev))
			{
				super.dispatchTouchEvent(ev);
				isIntercept = true;
				if (listener != null)
				{
					listener.onDownRecordView();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isIntercept)
			{
				if (isCancel)
				{
					reset();
					if (listener != null)
					{
						listener.onCancel();
					}
					return super.dispatchTouchEvent(ev);
				} else
				{
					if (SDViewUtil.isTouchView(cancelView, ev))
					{
						if (!isTouchCancelView)
						{
							isTouchCancelView = true;
							if (listener != null)
							{
								listener.onEnterCancelView();
							}
						}
					} else
					{
						if (isTouchCancelView)
						{
							isTouchCancelView = false;
							if (listener != null)
							{
								listener.onLeaveCancelView();
							}
						}
					}
					return true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			reset();
			if (SDViewUtil.isTouchView(cancelView, ev))
			{
				if (listener != null)
				{
					listener.onUpCancelView();
				}
			} else
			{
				if (listener != null)
				{
					listener.onUp();
				}
			}
		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}

	protected void reset()
	{
		isCancel = false;
		isTouchCancelView = false;
		isIntercept = false;
	}

	public void cancel()
	{
		isCancel = true;
		if (listener != null)
		{
			listener.onCancel();
		}
	}

	public interface RecordViewListener
	{
		void onDownRecordView();

		void onEnterCancelView();

		void onLeaveCancelView();

		void onUp();

		void onUpCancelView();

		void onCancel();
	}

}
