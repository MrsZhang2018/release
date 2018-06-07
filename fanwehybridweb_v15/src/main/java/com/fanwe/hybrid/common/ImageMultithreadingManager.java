package com.fanwe.hybrid.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-11-10 上午8:51:18 类说明
 */
public class ImageMultithreadingManager
{
	private int mSize;

	private HashMap<String, Boolean> mUrlList;

	private ImageMultithreadingManagerListening mImageMultithreadingManagerListening;

	public void setmImageMultithreadingManagerListening(ImageMultithreadingManagerListening mImageMultithreadingManagerListening)
	{
		this.mImageMultithreadingManagerListening = mImageMultithreadingManagerListening;
	}

	public ImageMultithreadingManager(List<String> urlList)
	{
		if (urlList != null && urlList.size() > 0)
		{
			mUrlList = new HashMap<String, Boolean>();
			for (int i = 0; i < urlList.size(); i++)
			{
				String url = urlList.get(i);
				if (!TextUtils.isEmpty(url))
				{
					mUrlList.put(url, false);
				}
			}
			mSize = mUrlList.size();
		}
	}

	@SuppressWarnings("rawtypes")
	public void loadImage()
	{
		Set set = mUrlList.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();)
		{
			final String key = (String) iter.next();
			Boolean value = (Boolean) mUrlList.get(key);
			System.out.println("===================" + key + " :" + value);

			ImageLoader.getInstance().loadImage(key, new ImageLoadingListener()
			{
				@Override
				public void onLoadingStarted(String imageUri, View view)
				{
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason)
				{
					mSize = mSize - 1;
					mUrlList.put(key, false);
					if (mSize == 0 && mImageMultithreadingManagerListening != null)
					{
						mImageMultithreadingManagerListening.onComplete();
					}
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
				{
					mSize = mSize - 1;
					mUrlList.put(key, true);
					if (mSize == 0 && mImageMultithreadingManagerListening != null)
					{
						mImageMultithreadingManagerListening.onComplete();
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view)
				{

				}
			});
		}

	}

	public interface ImageMultithreadingManagerListening
	{
		public void onComplete();
	}
}
