package com.fanwe.library.task;

public interface SDTaskListener
{

	public void onStart();

	public void onSuccess(SDTaskResponse response);

	public void onError(SDTaskResponse response, Throwable t);

	public void onFinish();

}
