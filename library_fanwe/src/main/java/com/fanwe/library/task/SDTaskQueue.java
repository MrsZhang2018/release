package com.fanwe.library.task;

import java.util.concurrent.LinkedBlockingQueue;

class SDTaskQueue
{
	private LinkedBlockingQueue<SDTask> queue = new LinkedBlockingQueue<SDTask>();

	public boolean add(SDTask task)
	{
		return queue.add(task);
	}

	public synchronized SDTask take()
	{
		SDTask task = null;
		try
		{
			task = queue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return task;
	}

	public synchronized void clear()
	{
		queue.clear();
	}
}
