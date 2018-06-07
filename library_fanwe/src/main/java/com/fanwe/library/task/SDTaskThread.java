package com.fanwe.library.task;

class SDTaskThread implements Runnable
{
	private int threadId;
	private Thread thread;
	private boolean running;
	private boolean stop;

	public SDTaskThread(int threadId)
	{
		this.threadId = threadId;
		thread = new Thread(this);
	}

	public void run()
	{
		while (!stop)
		{
			SDTask task = SDTaskManager.getInstance().take();
			task.run();
		}
	}

	public void start()
	{
		thread.start();
		running = true;
	}

	public void stop()
	{
		stop = true;
		running = false;
	}

	public boolean isRunning()
	{
		return running;
	}

	public int getThreadId()
	{
		return threadId;
	}
}
