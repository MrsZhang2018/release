package com.fanwe.o2o.appview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.app.App;

public class InputSuggestionView extends LinearLayout
{

	private ListView mLv;
	private EditText mEt;

	private BaseAdapter mAdapter;

	private InputSuggestionView_TextChangedListener mListenerTextChanged;
	private InputSuggestionView_OnItemClickListener mListenerOnItemClick;

	public InputSuggestionView(Context context)
	{
		this(context, null);
	}

	public InputSuggestionView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public void setmListenerTextChanged(InputSuggestionView_TextChangedListener listenerTextChanged)
	{
		this.mListenerTextChanged = listenerTextChanged;
	}

	public void setmListenerOnItemClick(InputSuggestionView_OnItemClickListener listenerOnItemClick)
	{
		this.mListenerOnItemClick = listenerOnItemClick;
	}

	private void init()
	{
		hide();
	}

	public BaseAdapter getmAdapter()
	{
		return mAdapter;
	}

	public void setEditText(EditText et)
	{
		this.mEt = et;
		mEt.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (TextUtils.isEmpty(s.toString()))
				{
					hide();
				}
				if (mListenerTextChanged != null)
				{
					mListenerTextChanged.afterTextChanged(s);
				}
			}
		});
	}

	public void hide()
	{
		SDViewUtil.hide(this);
	}

	public void show()
	{
		SDViewUtil.show(this);
	}

	public void setAdapter(BaseAdapter adapter)
	{
		this.mAdapter = adapter;
		mLv.setAdapter(mAdapter);
		if (mAdapter.getCount() > 0)
		{
			show();
		}
	}

	public void setViewResId(int resId)
	{
		LayoutInflater.from(App.getApplication()).inflate(resId, this, true);
	}

	public void setListViewId(int id)
	{
		mLv = (ListView) findViewById(id);
		mLv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (mListenerOnItemClick != null)
				{
					mListenerOnItemClick.onItemClick((int) id);
				}
				hide();
			}
		});
	}

	public interface InputSuggestionView_TextChangedListener
	{
		public void afterTextChanged(Editable s);
	}

	public interface InputSuggestionView_OnItemClickListener
	{
		public void onItemClick(int position);
	}

}
