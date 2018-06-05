package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.utils.GlideUtil;

/**
 * Created by heyucan on 2017/1/15.
 */

public class CommonRowView extends SDAppView {
  private TextView tv_left;
  private TextView tv_right;
  private CircleImageView civ_right;
  private ImageView iv_right_action_img;
  private ImageView iv_bottom_divider;

  public CommonRowView(Context context) {
    this(context, null);
  }

  public CommonRowView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CommonRowView(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
    init();
    TypedArray ta =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.CommonRowView, 0, 0);
    final boolean crvShowLeftTxt = ta.getBoolean(R.styleable.CommonRowView_crvShowLeftTxt, true);
    final boolean crvShowRightTxt = ta.getBoolean(R.styleable.CommonRowView_crvShowRightTxt, true);
    final boolean crvShowRightActionImg =
        ta.getBoolean(R.styleable.CommonRowView_crvShowRightActionImg, true);
    final boolean crvShowRightDra = ta.getBoolean(R.styleable.CommonRowView_crvShowRightDra, false);
    final boolean crvShowBottomDivider =
        ta.getBoolean(R.styleable.CommonRowView_crvShowBottomDivider, true);

    final String crvLeftTxt = ta.getString(R.styleable.CommonRowView_crvLeftTxt);
    final String crvRightTxt = ta.getString(R.styleable.CommonRowView_crvRightTxt);

    final Drawable crvRightDrawable = ta.getDrawable(R.styleable.CommonRowView_crvRightDrawable);
    final Drawable crvRightActionImg = ta.getDrawable(R.styleable.CommonRowView_crvRightActionImg);
    final int color = ta.getColor(R.styleable.CommonRowView_crvBottomDivider,
        getResources().getColor(R.color.gray_press));
    ta.recycle();

    showTv_left(crvShowLeftTxt);
    if (crvShowLeftTxt) {
      setTv_left(crvLeftTxt);
    }
    showTv_right(crvShowRightTxt);
    if (crvShowRightTxt) {
      setTv_right(crvRightTxt);
    }
    showCiv_right(crvShowRightDra);
    if (crvShowRightDra) {
      setCiv_right(crvRightDrawable);
    }
    showIv_right_action_img(crvShowRightActionImg);
    if (crvShowRightActionImg) {
      setIv_right_action_img(crvRightActionImg);
    }
    showIv_bottom_divider(crvShowBottomDivider);
    if (crvShowBottomDivider) {
      setIv_bottom_dividerColorInt(color);
    }
  }

  @Override
  protected void init() {
    super.init();
    setContentView(R.layout.view_common_row_view);
    initView();
  }

  private void initView() {
    tv_left = find(R.id.tv_left);
    tv_right = find(R.id.tv_right);
    civ_right = find(R.id.civ_right);
    iv_right_action_img = find(R.id.iv_right_action_img);
    iv_bottom_divider = find(R.id.iv_bottom_divider);
  }

  public CommonRowView showTv_left(boolean isShow) {
    tv_left.setVisibility(isShow ? VISIBLE : INVISIBLE);
    return this;
  }

  public CommonRowView setTv_left(String txt) {
    tv_left.setText(txt);
    return this;
  }

  public CommonRowView showTv_right(boolean isShow) {
    tv_right.setVisibility(isShow ? VISIBLE : INVISIBLE);
    return this;
  }

  public CommonRowView setTv_right(String txt) {
    tv_right.setText(txt);
    return this;
  }

  public CommonRowView setTv_rightTxtColor(@ColorRes int resId) {
    tv_right.setTextColor(getResources().getColor(resId));
    return this;
  }


  public CommonRowView showCiv_right(boolean isShow) {
    civ_right.setVisibility(isShow ? VISIBLE : INVISIBLE);
    return this;
  }

  public CommonRowView setCiv_right(Drawable drawable) {
    SDViewUtil.setImageViewImageDrawable(civ_right, drawable);
    return this;
  }

  public CommonRowView setCiv_right(@DrawableRes int resId) {
    SDViewUtil.setImageViewImageDrawableRes(civ_right, resId);
    return this;
  }

  public CommonRowView setCiv_right(String url) {
    GlideUtil.loadHeadImage(url).into(civ_right);
    return this;
  }

  public CommonRowView showIv_right_action_img(boolean isShow) {
    iv_right_action_img.setVisibility(isShow ? VISIBLE : INVISIBLE);
    return this;
  }

  public CommonRowView setIv_right_action_img(Drawable drawable) {
    SDViewUtil.setBackgroundDrawable(iv_right_action_img, drawable);
    return this;
  }

  public CommonRowView setIv_right_action_img(@DrawableRes int resId) {
    SDViewUtil.setBackgroundResource(iv_right_action_img, resId);
    return this;
  }

  public CommonRowView showIv_bottom_divider(boolean isShow) {
    iv_bottom_divider.setVisibility(isShow ? VISIBLE : INVISIBLE);
    return this;
  }

  public CommonRowView setIv_bottom_dividerColorInt(@ColorInt int color) {
    SDViewUtil.setBackgroundColorInt(iv_bottom_divider, color);
    return this;
  }

  public CommonRowView setIv_bottom_dividerColorRes(@ColorRes int resId) {
    SDViewUtil.setBackgroundColorResId(iv_bottom_divider, resId);
    return this;
  }
}
