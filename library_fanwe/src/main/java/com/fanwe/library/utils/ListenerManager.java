package com.fanwe.library.utils;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager<T>
{
	private List<T> mListListener = new ArrayList<T>();

	public void add(T listener)
	{
		synchronized (ListenerManager.class)
		{
			if (listener != null)
			{
				if (!mListListener.contains(listener))
				{
					mListListener.add(listener);
				}
			}
		}
	}

	public void remove(T listener)
	{
		synchronized (ListenerManager.class)
		{
			if (listener != null)
			{
				mListListener.remove(listener);
			}
		}
	}

	public List<T> getListeners()
	{
		return mListListener;
	}

	public void clear()
	{
		synchronized (ListenerManager.class)
		{
			mListListener.clear();
		}
	}

}
