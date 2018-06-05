package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.AccountManageActModel;
import com.fanwe.o2o.model.AccountManageGroupInfoModel;
import com.fanwe.o2o.model.AccountManageLevelInfoModel;
import com.fanwe.o2o.model.AccountManageUserInfoModel;
import com.fanwe.o2o.view.ProgressLineView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyucan on 2017/1/14.
 */

public class AccountManageStateView extends SDAppView {
  public static final int STYLE_VIP = 0;
  public static final int STYLE_EXP = 1;
  int colorInt;
  private LinearLayout linear_container;
  private ProgressLineView plv_progress;
  private TextView tv_insert_title;
  private TextView tv_state;
  private TextView tv_state_explain;
  private TextView tv_left_state;
  private TextView tv_right_state;
  private TextView tv_instruct;

  public AccountManageStateView(Context context) {
    this(context, null);
    init();
  }

  public AccountManageStateView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
    init();
  }

  public AccountManageStateView(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
    init();
    TypedArray ta =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.AccountManageHeaderView, 0, 0);
    final int amhvStyle = ta.getInteger(R.styleable.AccountManageHeaderView_amhvStyle, 0);
    colorInt = ta.getColor(R.styleable.AccountManageHeaderView_amhvProgressColor,
        getResources().getColor(R.color.yellow));
    if (amhvStyle == STYLE_EXP) {
      setStyle(STYLE_EXP);
    }
  }

  @Override
  protected void init() {
    super.init();
    setContentView(R.layout.view_account_manage_header);
    initView();
  }

  private void initView() {
    linear_container = find(R.id.linear_container);
    plv_progress = find(R.id.plv_progress);
    tv_insert_title = find(R.id.tv_insert_title);
    tv_state = find(R.id.tv_state);
    tv_state_explain = find(R.id.tv_state_explain);
    tv_left_state = find(R.id.tv_left_state);
    tv_right_state = find(R.id.tv_right_state);
    tv_instruct = find(R.id.tv_instruct);
  }

  private void setContainerBg(@DrawableRes int res) {
    Drawable drawable = getResources().getDrawable(res);
    if (drawable != null) {
      linear_container.setBackgroundDrawable(drawable);
    }
  }

  public AccountManageStateView setStyle(int style) {
    if (style == STYLE_VIP) {
      SDViewUtil.show(tv_insert_title);
      setContainerBg(R.drawable.gradient_red);
    } else {
      SDViewUtil.hide(tv_insert_title);
      FrameLayout.LayoutParams lp =
          new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      lp.setMargins(0, 0, 0, 0);
      linear_container.setLayoutParams(lp);
      tv_instruct.setTextColor(getResources().getColor(R.color.blue_light));
      SDViewUtil.setBackgroundResource(tv_state_explain, R.drawable.dialog_box_blue);
      setContainerBg(R.drawable.gradient_blue);
      plv_progress.setLineColorInt(colorInt);
    }
    return this;
  }

  public AccountManageStateView setProgress(float part, float total) {
    plv_progress.setFinalX(part, total);
    return this;
  }

  public AccountManageStateView setTxts(Map<String, Object> txts) {
    setTv_insert_title(String.valueOf(txts.get("setTv_insert_title")));
    setTv_state(String.valueOf(txts.get("setTv_state")));
    setTv_state_explain(String.valueOf(txts.get("setTv_state_explain")));
    setTv_left_state(String.valueOf(txts.get("setTv_left_state")));
    setTv_right_state(String.valueOf(txts.get("setTv_right_state")));
    setTv_instruct(String.valueOf(txts.get("setTv_instruct")));
    return this;
  }

  public Map<String, Object> getTxtsMap(AccountManageActModel actModel, int style) {
    Map<String, Object> txts = new HashMap<String, Object>();
    if (actModel == null) {
      return txts;
    }
    AccountManageUserInfoModel userInfoModel = actModel.getUser_info();
    if (style == STYLE_VIP) {
      tv_state_explain.setVisibility(VISIBLE);
      txts.put("setTv_insert_title", "我的会员组");
      List<AccountManageGroupInfoModel> group = actModel.getGroup_info();

      if (group != null) {
        AccountManageGroupInfoModel info1 = group.get(0);
        AccountManageGroupInfoModel info2 = group.get(1);
        if (info1 != null && info2 != null) {
          final String ghighest = actModel.getGhighest();
          txts.put("setTv_left_state", info1.getName());
          txts.put("setTv_right_state", info2.getName());
          if (!TextUtils.isEmpty(ghighest) && ghighest.equals("0")) {//ghighest 1:会员等级最高级， 0：非最高级
            txts.put("setTv_right_state", info2.getName());
            String formatedDiscount = formatDiscount(info2.getDiscount());
            StringBuilder sb = new StringBuilder()
                .append("还差")
                .append(Integer.parseInt(info2.getScore()) -
                    Integer.parseInt(userInfoModel.getTotal_score()))
                .append("积分升级至")
                .append(info2.getName())
                .append("，购物享")
                .append(formatedDiscount)
                .append("折优惠");
            txts.put("setTv_instruct", String.valueOf(sb));
          } else if (!TextUtils.isEmpty(ghighest) && ghighest.equals("1")) {
            txts.put("setTv_instruct", "您已升至最高级");
            tv_right_state.setVisibility(VISIBLE);// wap端满级进度条会突出,此为错误;故修正为不隐藏tv_right_state,
            //plv_progress.requestLayout();
          }
        }
        final AccountManageActModel.Currdis currdis = actModel.getCurrdis();
        txts.put("setTv_state", currdis.getName());
        final String formatedDiscount=formatDiscount(currdis.getDiscount());
        if(!formatedDiscount.equals("1")){
          StringBuilder sb =
              new StringBuilder().append("享")
                  .append(formatedDiscount)
                  .append("折优惠");
          txts.put("setTv_state_explain", sb);
        }else {
          tv_state_explain.setVisibility(INVISIBLE);
        }

      }
    } else if (style == STYLE_EXP) {
      List<AccountManageLevelInfoModel> level = actModel.getLevel_info();
      txts.put("setTv_state", userInfoModel.getPoint());
      tv_state_explain.setVisibility(VISIBLE);
      txts.put("setTv_state_explain", "当前经验值");
      AccountManageLevelInfoModel info1 = level.get(0);
      AccountManageLevelInfoModel info2 = level.get(1);
      final String phighest = actModel.getPhighest();
      if (info1 != null && info2 != null) {
        txts.put("setTv_left_state", info1.getName());
        txts.put("setTv_right_state", info2.getName());
        if (!TextUtils.isEmpty(phighest) && phighest.equals("0")) {//phighest 1:会员等级最高级， 0：非最高级
          StringBuilder sb = new StringBuilder()
              .append("还差")
              .append(
                  Integer.parseInt(info2.getPoint()) - Integer.parseInt(
                      userInfoModel.getPoint()))
              .append("经验值升级至")
              .append(info2.getName());

          txts.put("setTv_instruct", String.valueOf(sb));
        } else if (!TextUtils.isEmpty(phighest) && phighest.equals(
            "1")) {//phighest 1:会员等级最高级， 0：非最高级
          txts.put("setTv_instruct", "您已升至最高级");
          tv_right_state.setVisibility(VISIBLE); // wap端满级进度条会突出,此为错误;故修正为不隐藏tv_right_state,
          //plv_progress.requestLayout();
        }
      }
    }
    return txts;
  }

  private String formatDiscount(String discount) {
    StringBuilder sb = new StringBuilder();
    if (discount != null) {
      int oldLength = discount.length();
      if (oldLength > 0) {
        for (int i = 0; i < oldLength; i++) {
          char c = discount.charAt(i);
          if (c != '0' && c != '.') {
            sb.append(c);
          }
          if (sb.length() > 1) {
            break;
          }
        }
        if (sb.length() > 1) {
          sb.insert(1, '.');
        }
      }
    }
    return String.valueOf(sb);
  }

  public AccountManageStateView setTv_insert_title(CharSequence txt) {
    tv_insert_title.setText(txt);
    return this;
  }

  public AccountManageStateView setTv_state(CharSequence txt) {
    tv_state.setText(txt);
    return this;
  }

  public AccountManageStateView setTv_state_explain(CharSequence txt) {
    tv_state_explain.setText(txt);
    return this;
  }

  public AccountManageStateView setTv_left_state(CharSequence txt) {
    tv_left_state.setText(txt);
    return this;
  }

  public AccountManageStateView setTv_right_state(CharSequence txt) {
    tv_right_state.setText(txt);
    return this;
  }

  public AccountManageStateView setTv_instruct(CharSequence txt) {
    tv_instruct.setText(txt);
    return this;
  }
}
