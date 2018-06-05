package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.YouhuiModel;

import java.util.List;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

public class CouponsListAdapter extends SDSimpleAdapter<YouhuiModel>
{
	private ClickReceive listener;

	public void setListener(ClickReceive listener)
	{
		this.listener = listener;
	}

	public CouponsListAdapter(List<YouhuiModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_coupon;
	}

	@Override
	public void bindData(final int position, final View convertView, ViewGroup parent, final YouhuiModel model)
	{
		LinearLayout ll_left = get(R.id.ll_left, convertView);
		RelativeLayout rl_right = get(R.id.rl_right, convertView);
		LinearLayout ll_dis = get(R.id.ll_dis, convertView);
		TextView tv_dis = get(R.id.tv_dis, convertView);
		//TextView tv_dis2 = get(R.id.tv_dis2, convertView);
		LinearLayout ll_mul = get(R.id.ll_mul, convertView);
		TextView tv_mul1 = get(R.id.tv_mul1, convertView);
		TextView tv_mul = get(R.id.tv_mul, convertView);
		TextView tv_mul2 = get(R.id.tv_mul2, convertView);
		TextView tv_coupon_name = get(R.id.tv_coupon_name, convertView);
		TextView tv_name = get(R.id.tv_name, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		LinearLayout ll_right = get(R.id.ll_right, convertView);
		final TextView tv_state = get(R.id.tv_state, convertView);
		final TextView tv_info = get(R.id.tv_info, convertView);
		final TextView tv_receive = get(R.id.tv_receive, convertView);
		ImageView iv_state = get(R.id.iv_state, convertView);

		int state = model.getStatus();
		/*'info'="已结束";
		'order_status'=7;

		'info'="已抢光";
		'order_status'=6;

		'info'="积分不足";
		'order_status'=2;

		'info'="经验不足";
		'order_status'=3;

		'info'="已领取";
		'order_status'=5;

		'order_status'=4;
		'info'="x时x分"; // 倒计时*/
		int order_state = model.getOrder_status();
		String type = model.getYouhui_type();//0:满立减 1:折扣券
		float value = model.getYouhui_value();

		SDViewBinder.setTextView(tv_coupon_name, model.getName());
		SDViewBinder.setTextView(tv_name, model.getLocation_name());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());
		SDViewBinder.setTextView(tv_info, model.getInfo());
		switch (state)//1-可领取；-1-未开始；0-不可领取；2-已领取，去使用
		{
			case 0:
				if (type.equals("1"))
				{
					SDViewUtil.hide(ll_mul);
					SDViewUtil.show(ll_dis);
					SDViewUtil.setTextViewColorResId(tv_dis,R.color.text_content);
					tv_dis.setText(getSpannableString(value));
					//SDViewUtil.setTextViewColorResId(tv_dis2,R.color.text_content);
					//SDViewBinder.setTextView(tv_dis,String.valueOf(value));
				}else if (type.equals("0"))
				{
					SDViewUtil.show(ll_mul);
					SDViewUtil.hide(ll_dis);
					isHideZero(value,tv_mul2);
					SDViewUtil.setTextViewColorResId(tv_mul1,R.color.text_content);
					SDViewUtil.setTextViewColorResId(tv_mul,R.color.text_content);
					SDViewUtil.setTextViewColorResId(tv_mul2,R.color.text_content);
					SDViewBinder.setTextView(tv_mul,String.valueOf((int)value));
				}
				if (order_state == 2)
				{
					iv_state.setImageResource(R.drawable.ic_integerl_not_enough);
					SDViewUtil.show(tv_name);
					SDViewUtil.show(tv_distance);
				}
				else if (order_state == 6)
				{
					iv_state.setImageResource(R.drawable.ic_coupon_sold_out);
					SDViewUtil.show(tv_name);
					SDViewUtil.show(tv_distance);
				}
				SDViewUtil.hide(ll_right);
				SDViewUtil.show(iv_state);
				SDViewUtil.setBackgroundResource(ll_left,R.drawable.ic_coupon_gray_left);
				SDViewUtil.setBackgroundResource(rl_right,R.drawable.ic_coupon_gray_right);
				break;
			case 1:
				if (type.equals("1"))
				{
					SDViewUtil.hide(ll_mul);
					SDViewUtil.show(ll_dis);
					SDViewUtil.setTextViewColorResId(tv_dis,R.color.main_color);
					tv_dis.setText(getSpannableString(value));
					//SDViewUtil.setTextViewColorResId(tv_dis2,R.color.text_content);
					//SDViewBinder.setTextView(tv_dis,String.valueOf(value));
				}else if (type.equals("0"))
				{
					SDViewUtil.show(ll_mul);
					SDViewUtil.hide(ll_dis);
					isHideZero(value,tv_mul2);
					SDViewUtil.setTextViewColorResId(tv_mul1,R.color.main_color);
					SDViewUtil.setTextViewColorResId(tv_mul,R.color.main_color);
					SDViewUtil.setTextViewColorResId(tv_mul2,R.color.main_color);
					SDViewBinder.setTextView(tv_mul,String.valueOf((int)value));
				}
				SDViewUtil.show(ll_right);
				SDViewUtil.hide(iv_state);
				SDViewUtil.show(tv_name);
				SDViewUtil.show(tv_distance);
				SDViewUtil.setBackgroundResource(ll_left,R.drawable.ic_coupon_red_left);
				SDViewUtil.setBackgroundResource(rl_right,R.drawable.ic_coupon_red_right);
				SDViewUtil.show(tv_state);
				SDViewBinder.setTextView(tv_state, "已抢");
				SDViewUtil.setTextViewColorResId(tv_info,R.color.text_content_deep);
				SDViewBinder.setTextView(tv_receive,"马上领取");
				break;
			case 2:
				if (type.equals("1"))
				{
					SDViewUtil.hide(ll_mul);
					SDViewUtil.show(ll_dis);
					SDViewUtil.setTextViewColorResId(tv_dis,R.color.main_color);
					tv_dis.setText(getSpannableString(value));
					//SDViewUtil.setTextViewColorResId(tv_dis2,R.color.text_content);
					//SDViewBinder.setTextView(tv_dis,String.valueOf(value));
				}else if (type.equals("0"))
				{
					SDViewUtil.show(ll_mul);
					SDViewUtil.hide(ll_dis);
					isHideZero(value,tv_mul2);
					SDViewUtil.setTextViewColorResId(tv_mul1,R.color.main_color);
					SDViewUtil.setTextViewColorResId(tv_mul,R.color.main_color);
					SDViewUtil.setTextViewColorResId(tv_mul2,R.color.main_color);
					SDViewBinder.setTextView(tv_mul,String.valueOf((int)value));
				}
				SDViewUtil.show(ll_right);
				SDViewUtil.hide(iv_state);
				SDViewUtil.show(tv_name);
				SDViewUtil.show(tv_distance);
				SDViewUtil.hide(tv_state);
				SDViewUtil.setBackgroundResource(ll_left,R.drawable.ic_coupon_red_left);
				SDViewUtil.setBackgroundResource(rl_right,R.drawable.ic_coupon_red_right);
				SDViewUtil.setTextViewColorResId(tv_info,R.color.text_content);
				SDViewBinder.setTextView(tv_receive,"去使用");
				break;
			case -1:
				if (type.equals("1"))
				{
					SDViewUtil.hide(ll_mul);
					SDViewUtil.show(ll_dis);
					SDViewUtil.setTextViewColorResId(tv_dis,R.color.text_content);
					tv_dis.setText(getSpannableString(value));
					//SDViewUtil.setTextViewColorResId(tv_dis2,R.color.text_content);
					//SDViewBinder.setTextView(tv_dis,String.valueOf(value));
				}else if (type.equals("0"))
				{
					SDViewUtil.show(ll_mul);
					SDViewUtil.hide(ll_dis);
					isHideZero(value,tv_mul2);
					SDViewUtil.setTextViewColorResId(tv_mul1,R.color.text_content);
					SDViewUtil.setTextViewColorResId(tv_mul,R.color.text_content);
					SDViewUtil.setTextViewColorResId(tv_mul2,R.color.text_content);
					SDViewBinder.setTextView(tv_mul,String.valueOf((int)value));
				}
				SDViewUtil.hide(ll_right);
				SDViewUtil.show(iv_state);
				SDViewUtil.hide(tv_name);
				SDViewUtil.hide(tv_distance);
				SDViewUtil.setBackgroundResource(ll_left,R.drawable.ic_coupon_gray_left);
				SDViewUtil.setBackgroundResource(rl_right,R.drawable.ic_coupon_gray_right);
				break;
			default:
				break;
		}

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				itemClickListener.onClick(position,model,convertView);
			}
		});

		/**
		 * 马上领取
		 */
		tv_receive.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				listener.onClickReceive(position,model);
			}
		});
	}

	private SpannableString getSpannableString(float discount){
		final StringBuilder sb=new StringBuilder();
		sb.append(discount).append("折");
		SpannableString ss=new SpannableString(sb);
		AbsoluteSizeSpan ass=new AbsoluteSizeSpan(35);
		ss.setSpan(ass, 2, 4, SPAN_INCLUSIVE_EXCLUSIVE);
		return ss;
	}

	private void isHideZero(float value,TextView tv_mul2){
		if(value>=10){
			SDViewUtil.hide(tv_mul2);
		}else {
			SDViewUtil.show(tv_mul2);
		}
	}

	public interface ClickReceive
	{
		void onClickReceive(int position, YouhuiModel model);
	}

}
