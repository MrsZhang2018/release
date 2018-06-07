package com.fanwe.hybrid.customview;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import cn.fanwe.yi.R;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;

public class SDSimpleTitleView extends FrameLayout implements View.OnClickListener
{
	public LinearLayout mLinLeft = null;
	public LinearLayout mLinRight = null;

	public TextView mTxtLeft;
	public TextView mTxtRight;
	public TextView mTxtTitle;
	private TextView mTxtTitBot;

	public ImageView mImgLeft = null;
	public ImageView mImgRight = null;

	private OnLeftButtonClickListener mOnLeftButtonClickListener;
	private OnRightButtonClickListener mOnRightButtonClickListener;

	public void setTitle(String text)
	{
		mTxtTitle.setVisibility(View.VISIBLE);
		mTxtTitle.setText(text);
	}

	public void setTitle(int stringID)
	{
		mTxtTitle.setVisibility(View.VISIBLE);
		mTxtTitle.setText(stringID);
	}

	public void setTitleBottom(String text)
	{
		mTxtTitBot.setVisibility(View.VISIBLE);
		mTxtTitBot.setText(text);
	}

	public void setLeftLinearLayout(OnLeftButtonClickListener listener)
	{
		mOnLeftButtonClickListener = listener;
	}

	public void setLeftText(String leftText, Integer leftTextBg)
	{
		mLinLeft.setVisibility(View.VISIBLE);
		mTxtLeft.setVisibility(View.VISIBLE);
		mImgLeft.setVisibility(View.GONE);
		mTxtLeft.setText(leftText);
		if (leftTextBg != null)
		{
			mTxtLeft.setBackgroundResource(leftTextBg);
		}
	}

	public void setLeftImage(Integer leftImageBg)
	{
		mLinLeft.setVisibility(View.VISIBLE);
		mTxtLeft.setVisibility(View.GONE);
		mImgLeft.setVisibility(View.VISIBLE);
		if (leftImageBg != null)
		{
			mImgLeft.setImageResource(leftImageBg);
		}
	}

	public void setLeftButton(String leftText, Integer leftImageBg, Integer leftTextBg)
	{
		mLinLeft.setVisibility(View.VISIBLE);
		mTxtLeft.setVisibility(View.VISIBLE);
		mImgLeft.setVisibility(View.VISIBLE);
		mTxtLeft.setText(leftText);
		if (leftTextBg != null)
		{
			mTxtLeft.setBackgroundResource(leftTextBg);
		}
		if (leftImageBg != null)
		{
			mImgLeft.setImageResource(leftImageBg);
		}
	}

	public void removeLeftButton()
	{
		mLinLeft.setVisibility(View.GONE);
		mOnLeftButtonClickListener = null;
	}

	public void setRightLinearLayout(OnRightButtonClickListener listener)
	{
		mOnRightButtonClickListener = listener;
	}

	public void setRightText(String RightText, Integer RightTextBg)
	{
		mLinRight.setVisibility(View.VISIBLE);
		mTxtRight.setVisibility(View.VISIBLE);
		mImgRight.setVisibility(View.GONE);
		mTxtRight.setText(RightText);
		if (RightTextBg != null)
		{
			mTxtRight.setBackgroundResource(RightTextBg);
		}
	}

	public void setRightVisible(int isVisible)
	{
		if (isVisible == View.VISIBLE)
		{
			mLinRight.setVisibility(View.VISIBLE);
			mTxtRight.setVisibility(View.VISIBLE);
			mImgRight.setVisibility(View.GONE);
		} else
		{
			mLinRight.setVisibility(View.GONE);
			mTxtRight.setVisibility(View.GONE);
			mImgRight.setVisibility(View.GONE);
		}

	}

	public void setRightImage(Integer RightImageBg)
	{
		mLinRight.setVisibility(View.VISIBLE);
		mTxtRight.setVisibility(View.GONE);
		mImgRight.setVisibility(View.VISIBLE);
		if (RightImageBg != null)
		{
			mImgRight.setImageResource(RightImageBg);
		}
	}

	public void setRightButton(String rightText, Integer rightImageBg, Integer rightTextBg)
	{
		mLinRight.setVisibility(View.VISIBLE);
		mTxtRight.setVisibility(View.VISIBLE);
		mImgRight.setVisibility(View.VISIBLE);
		mTxtRight.setText(rightText);

		if (rightTextBg != null)
		{
			mTxtRight.setBackgroundResource(rightTextBg);
		}
		if (rightImageBg != null)
		{
			mImgRight.setImageResource(rightImageBg);
		}
	}

	public void removeRightButton()
	{
		mLinRight.setVisibility(View.GONE);
		mOnRightButtonClickListener = null;
	}

	public SDSimpleTitleView(Context context)
	{
		super(context);
		init(context);
	}

	public SDSimpleTitleView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public SDSimpleTitleView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context)
	{
		InitActModel model = InitActModelDao.query();
		if (model != null && !TextUtils.isEmpty(model.getTopnav_color()))
		{
			int title_color = Color.parseColor(model.getTopnav_color());
			this.setBackgroundColor(title_color);
		} else
		{
			this.setBackgroundColor(getResources().getColor(R.color.title_color_blue));
		}

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_simple_title, this, true);

		mLinLeft = (LinearLayout) findViewById(R.id.view_simple_title_lin_left);
		mLinRight = (LinearLayout) findViewById(R.id.view_simple_title_lin_right);

		mImgLeft = (ImageView) findViewById(R.id.view_simple_title_img_left);
		mImgRight = (ImageView) findViewById(R.id.view_simple_title_img_right);

		mTxtLeft = (TextView) findViewById(R.id.view_simple_title_txt_left);
		mTxtRight = (TextView) findViewById(R.id.view_simple_title_txt_right);

		mTxtTitle = (TextView) findViewById(R.id.view_simple_title_txt_title);

		mTxtTitBot = (TextView) findViewById(R.id.view_simple_title_txt_title_bottom);

		mLinLeft.setOnClickListener(this);
		mLinRight.setOnClickListener(this);

		mLinLeft.setVisibility(View.GONE);
		mLinRight.setVisibility(View.GONE);
		mTxtTitle.setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.view_simple_title_lin_left:
			if (mOnLeftButtonClickListener != null)
				mOnLeftButtonClickListener.onLeftBtnClick(v);
			break;
		case R.id.view_simple_title_lin_right:
			if (mOnRightButtonClickListener != null)
				mOnRightButtonClickListener.onRightBtnClick(v);
			break;
		}
	}

	public interface OnLeftButtonClickListener
	{
		public void onLeftBtnClick(View button);
	}

	public interface OnRightButtonClickListener
	{
		public void onRightBtnClick(View button);
	}

}
