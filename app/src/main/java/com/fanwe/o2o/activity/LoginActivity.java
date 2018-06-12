package com.fanwe.o2o.activity;

import com.fanwe.library.event.ELoginEvent;
import com.sunday.eventbus.SDEventManager;

import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.o2o.constant.Constant.EnumLoginState;
import com.fanwe.o2o.event.ELoginSuccess;
import com.fanwe.o2o.fragment.LoginFragment;
import com.fanwe.o2o.fragment.LoginPhoneFragment;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.R;
import com.fanwe.o2o.work.AppRuntimeWorker;

import org.xutils.view.annotation.ViewInject;

public class LoginActivity extends BaseActivity {
    public static final String EXTRA_WEB_URL = "extra_web_url";
    public static final String EXTRA_SELECT_TAG_INDEX = "extra_select_tag_index";

//    @ViewInject(R.id.ll_tabs)
//    private LinearLayout mLl_tabs;

//    @ViewInject(R.id.act_login_new_tab_login_normal)
//    private SDTabCornerText mTabLoginNormal;

//    @ViewInject(R.id.act_login_new_tab_login_phone)
//    private SDTabCornerText mTabLoginPhone;

//    private SDSelectViewManager<SDTabCornerText> mSelectManager = new SDSelectViewManager<SDTabCornerText>();

    private int mSelectTabIndex = 0;
    private String webUrl;
    private List<Integer> mListSelectIndex = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        init();
        SDEventManager.post(new ELoginEvent(true));
    }

    private void init() {
        clickLoginPhone();
        getIntentData();
//        initTitle();
        changeViewState();
    }

    private void getIntentData() {
        mListSelectIndex.add(0);
        mListSelectIndex.add(1);
        webUrl = getIntent().getStringExtra(EXTRA_WEB_URL);
        mSelectTabIndex = getIntent().getIntExtra(EXTRA_SELECT_TAG_INDEX, 0);
        if (!mListSelectIndex.contains(mSelectTabIndex)) {
            mSelectTabIndex = 0;
        }
    }

//    private void initTitle() {
//        title.setLeftImageLeft(R.drawable.ic_o2o_back);
//        title.setMiddleTextTop("登录");
//
//        title.initRightItem(1);
//        title.getItemRight(0).setTextBot("立即注册");
//    }
//
//    @Override
//    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
//        startRegisterActivity();
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        init();
        super.onNewIntent(intent);
    }

    private void changeViewState() {
        EnumLoginState state = AppRuntimeWorker.getLoginState();
        switch (state) {
            case LOGIN_EMPTY_PHONE:
//                changeViewLoginEmptyPhone();
                break;
            case UN_LOGIN:
//                changeViewUnLogin();
                break;
            case LOGIN_NEED_BIND_PHONE:
//                changeViewUnLogin();
                showBindPhoneDialog();
                break;
            case LOGIN_NEED_VALIDATE:
//                changeViewUnLogin();
                break;

            default:
                break;
        }
    }

    private void showBindPhoneDialog() {
        Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
        startActivity(intent);
        finish();
    }

//    private void changeViewLoginEmptyPhone() {
//        mLl_tabs.setVisibility(View.GONE);
//        clickLoginNormal();
//    }

//    private void changeViewUnLogin() {
//        mTabLoginPhone.getViewConfig(mTabLoginPhone).setBackgroundNormal(getResources().getDrawable(R.drawable.layer_stroke_login_normal));
//        mTabLoginPhone.getViewConfig(mTabLoginPhone).setBackgroundSelected(getResources().getDrawable(R.drawable.layer_white_login_selected));
//        mTabLoginPhone.getViewConfig(mTabLoginPhone.mTvTitle).setTextColorNormalResId(R.color.text_content_deep);
//        mTabLoginPhone.getViewConfig(mTabLoginPhone.mTvTitle).setTextColorSelectedResId(R.color.black);
//        mTabLoginPhone.setTextTitle("手机号快捷登录");
//        mTabLoginPhone.setTextSizeTitle(13);
//        mTabLoginPhone.setPosition(TabPosition.FIRST);
//
//        mTabLoginNormal.getViewConfig(mTabLoginNormal).setBackgroundNormal(getResources().getDrawable(R.drawable.layer_stroke_login_normal));
//        mTabLoginNormal.getViewConfig(mTabLoginNormal).setBackgroundSelected(getResources().getDrawable(R.drawable.layer_white_login_selected));
//        mTabLoginNormal.getViewConfig(mTabLoginNormal.mTvTitle).setTextColorNormalResId(R.color.text_content_deep);
//        mTabLoginNormal.getViewConfig(mTabLoginNormal.mTvTitle).setTextColorSelectedResId(R.color.black);
//        mTabLoginNormal.setTextTitle("账号密码登录");
//        mTabLoginNormal.setTextSizeTitle(13);
//        mTabLoginNormal.setPosition(TabPosition.LAST);
//
//        mSelectManager.setListener(new SDSelectManagerListener<SDTabCornerText>() {
//
//            @Override
//            public void onNormal(int index, SDTabCornerText item) {
//
//            }
//
//            @Override
//            public void onSelected(int index, SDTabCornerText item) {
//                switch (index) {
//                    case 0: // 快捷登录
//                        clickLoginPhone();
//                        break;
//                    case 1: // 正常登录
//                        clickLoginNormal();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//
//        mSelectManager.setItems(new SDTabCornerText[]{mTabLoginPhone, mTabLoginNormal});
//        mSelectManager.performClick(mSelectTabIndex);
//    }

    /**
     * 手机号快捷登录的选项卡被选中
     */
    protected void clickLoginPhone() {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_WEB_URL, webUrl);
        getSDFragmentManager().toggle(R.id.act_login_fl_content, null, LoginPhoneFragment.class, bundle, false);
    }

    /**
     * 正常登录的选项卡被选中
     */
//    protected void clickLoginNormal() {
//        Bundle bundle = new Bundle();
//        bundle.putString(EXTRA_WEB_URL, webUrl);
//        getSDFragmentManager().toggle(R.id.act_login_fl_content, null, LoginFragment.class, bundle, false);
//    }

//    protected void startRegisterActivity() {
//        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//        startActivity(intent);
//    }

    public void onEventMainThread(ELoginSuccess event) {
        finish();
    }
}