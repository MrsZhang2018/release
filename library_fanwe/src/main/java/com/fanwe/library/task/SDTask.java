package com.fanwe.library.task;

import com.fanwe.library.utils.SDHandlerUtil;

public abstract class SDTask implements Runnable
{

	private SDTaskRequest request;
	private SDTaskResponse response;
	private SDTaskListener listener;

	public SDTaskRequest getRequest()
	{
		return request;
	}

	public SDTask setRequest(SDTaskRequest request)
	{
		this.request = request;
		return this;
	}

	public SDTaskResponse getResponse()
	{
		return response;
	}

	public SDTask setResponse(SDTaskResponse response)
	{
		this.response = response;
		return this;
	}

	public SDTaskListener getListener()
	{
		return listener;
	}

	public SDTask setListener(SDTaskListener listener)
	{
		this.listener = listener;
		return this;
	}

	private void beforeRun()
	{
		notifyStart();
	}

	@Override
	public final void run()
	{
		try
		{
			beforeRun();
			boolean result = onBackground();
			if (result)
			{
				// 不通知结束监听
			} else
			{
				notifyFinish();
			}
		} catch (Exception e)
		{
			onError(e);
			notifyError(e);
		}
	}

	/**
	 * 
	 * @return true-需要手动通知监听结束，false-方法执行完毕会自动通知监听结束
	 */
	public abstract boolean onBackground();

	protected void onError(Exception e)
	{

	}

	protected void notifySuccess()
	{
		if (listener != null)
		{
			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					listener.onSuccess(getResponse());
				}
			});
		}
	}

	protected void notifyError(final Throwable t)
	{
		if (listener != null)
		{
			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					listener.onError(getResponse(), t);
				}
			});
		}
	}

	private void notifyStart()
	{
		if (listener != null)
		{
			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					listener.onStart();
				}
			});
		}
	}

	private void notifyFinish()
	{
		if (listener != null)
		{
			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					listener.onFinish();
				}
			});
		}
	}

	protected final void runOnUiThread(Runnable r)
	{
		if (r != null)
		{
			SDHandlerUtil.runOnUiThread(r);
		}
	}

}
