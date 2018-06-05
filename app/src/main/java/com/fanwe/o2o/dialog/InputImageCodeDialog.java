package com.fanwe.o2o.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fanwe.o2o.config.AppConfig;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.event.EConfirmImageCode;
import com.fanwe.o2o.utils.GlideUtil;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class InputImageCodeDialog extends SDDialogCustom
{

	@ViewInject(R.id.iv_image_code)
	public ImageView mIv_image_code;

	@ViewInject(R.id.et_code)
	public EditText mEt_code;

	private String mStrUrl;

	private InputImageCodeDialogListener mListenerImageCode;

	public void setmListenerImageCode(InputImageCodeDialogListener mListenerImageCode)
	{
		this.mListenerImageCode = mListenerImageCode;
	}

	public InputImageCodeDialog(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();
		setDismissAfterClick(false);
		setCustomView(R.layout.dialog_input_image_code);
		x.view().inject(this, getContentView());

		mIv_image_code.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				GlideUtil.load(mStrUrl).into(mIv_image_code);
			}
		});

		setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
				if (mListenerImageCode != null)
				{
					mListenerImageCode.onDismiss(dialog);
				}
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				String content = mEt_code.getText().toString();
				if (TextUtils.isEmpty(content))
				{
					showTip("请输入图形验证码");
					return;
				}
				AppConfig.setImageCode(content);
				if (mListenerImageCode != null)
				{
					mListenerImageCode.onClickConfirm(content, v, dialog);
				}
				SDEventManager.post(new EConfirmImageCode());
				dialog.dismiss();
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
				if (mListenerImageCode != null)
				{
					mListenerImageCode.onClickCancel(v, dialog);
				}
				dialog.dismiss();
			}
		});
	}

	public void setImage(String url)
	{
		this.mStrUrl = url;
		GlideUtil.load(mStrUrl).into(mIv_image_code);
	}

	@Override
	public void show()
	{
		super.show();
		SDViewUtil.showInputMethod(mEt_code, 200);
	}

	@Override
	public void dismiss()
	{
		SDViewUtil.hideInputMethod(mEt_code);
		super.dismiss();
	}

	public interface InputImageCodeDialogListener
	{
		public void onClickCancel(View v, SDDialogCustom dialog);

		public void onClickConfirm(String content, View v, SDDialogCustom dialog);

		public void onDismiss(SDDialogCustom dialog);
	}

}
