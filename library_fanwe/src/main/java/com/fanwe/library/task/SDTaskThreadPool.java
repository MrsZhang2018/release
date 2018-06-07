package com.fanwe.library.task;

class SDTaskThreadPool
{
	private int threadCount;
	private SDTaskThread arrThread[];
	private boolean started;

	public SDTaskThreadPool(int threadCount)
	{
		this.threadCount = threadCount;
	}

	public void start()
	{
		if (!started)
		{
			arrThread = new SDTaskThread[threadCount];
			for (int i = 0; i < threadCount; i++)
			{
				arrThread[i] = new SDTaskThread(i);
				arrThread[i].start();
			}
			started = true;
		}
	}

	public void stop()
	{
		if (started)
		{
			for (SDTaskThread thread : arrThread)
			{
				thread.stop();
			}
			arrThread = null;
			started = false;
		}
	}
}
