package com.fanwe.library.task;

public final class SDTaskManager
{
	private static SDTaskManager instance;
	private boolean started;
	private SDTaskThreadPool threadPool;
	private SDTaskQueue queue;

	private SDTaskManager()
	{
	}

	public static SDTaskManager getInstance()
	{
		if (instance == null)
		{
			instance = new SDTaskManager();
		}
		return instance;
	}

	public void start(int threadCount)
	{
		if (!started)
		{
			queue = new SDTaskQueue();
			threadPool = new SDTaskThreadPool(threadCount);
			threadPool.start();
			started = true;
		}
	}

	public void stop()
	{
		if (started)
		{
			queue.clear();
			threadPool.stop();
			started = false;
		}
	}

	public boolean isStarted()
	{
		return started;
	}

	public SDTask take()
	{
		return queue.take();
	}

	public boolean add(SDTask task)
	{
		return queue.add(task);
	}

	public void clear()
	{
		queue.clear();
	}
}
