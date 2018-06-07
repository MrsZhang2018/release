package com.fanwe.library.view;

import android.view.View;

public interface SDVisibleStateListener
{
	void onVisible(View view);

	void onGone(View view);

	void onInvisible(View view);
}
