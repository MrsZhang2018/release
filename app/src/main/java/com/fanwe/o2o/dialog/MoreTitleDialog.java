package com.fanwe.o2o.dialog;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDAnimationUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.LoginActivity;
import com.fanwe.o2o.activity.MainActivity;
import com.fanwe.o2o.activity.MessageCenterActivity;
import com.fanwe.o2o.activity.SearchActivity;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.EIntentAppMain;
import com.fanwe.o2o.event.EIntentUserCenter;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppHaveReadMsgActModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/2/8.
 */

public class MoreTitleDialog extends SDDialogCustom
{
    @ViewInject(R.id.iv_refresh)
    private ImageView iv_refresh;
    @ViewInject(R.id.ll_home)
    private LinearLayout ll_home;
    @ViewInject(R.id.ll_search)
    private LinearLayout ll_search;
    @ViewInject(R.id.ll_news)
    private LinearLayout ll_news;
    @ViewInject(R.id.tv_news_tip)
    private TextView tv_news_tip;
    @ViewInject(R.id.ll_cart)
    private LinearLayout ll_cart;
    @ViewInject(R.id.ll_my)
    private LinearLayout ll_my;
    @ViewInject(R.id.rl_dismiss)
    private RelativeLayout rl_dismiss;
    @ViewInject(R.id.iv_dismiss)
    private ImageView iv_dismiss;

    private int login_status;
    private int count;
    private float initY;
    private ObjectAnimator animator;

    public MoreTitleDialog(Activity activity)
    {
        super(activity);
    }

    public void requestData()
    {
        requestHaveReadMsg();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.dialog_more_title);
        setFullScreen();
        x.view().inject(this, getContentView());
        initData();
    }

    private void initData()
    {
        iv_refresh.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        ll_news.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        ll_my.setOnClickListener(this);
        rl_dismiss.setOnClickListener(this);
        iv_dismiss.setOnClickListener(this);
        initY=iv_dismiss.getTranslationY();
      animator=
          SDAnimationUtil.translationY(iv_dismiss,initY-200,initY,initY-150,initY,initY-100,initY,initY-50,initY);
          animator.setDuration(1500);
    }

    private void requestHaveReadMsg()
    {
        CommonInterface.requestHaveReadMsg(new AppRequestCallback<AppHaveReadMsgActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    login_status = actModel.getUser_login_status();
                    count = actModel.getCount();
                    if (count != 0)
                        SDViewUtil.show(tv_news_tip);
                    else
                        SDViewUtil.hide(tv_news_tip);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == iv_refresh)
        {
            clickRefresh();
        }else if (v == ll_home)
        {
            clickHome();
        }else if (v == ll_search)
        {
            clickSearch();
        }else if (v == ll_news)
        {
            clickNews();
        }else if (v == ll_cart)
        {
            clickCart();
        }else if (v == ll_my)
        {
            clickMy();
        }else if (v == rl_dismiss)
        {
            dismiss();
        }else if (v == iv_dismiss)
        {
            dismiss();
        }
    }

    private void clickRefresh()
    {
        SDEventManager.post(new ERefreshRequest());
        dismiss();
    }

    private void clickHome()
    {
        SDEventManager.post(new EIntentAppMain());
        Intent intent = new Intent(getOwnerActivity(), MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.APP_MAIN);
        getOwnerActivity().startActivity(intent);
        getOwnerActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
        dismiss();
    }

    private void clickSearch()
    {
        /**跳转首页的发现搜索**/
//        SDEventManager.post(new EIntentDiscover());
//        Intent intent = new Intent(getOwnerActivity(), MainActivity.class);
//        intent.putExtra(MainActivity.EXTRA_TAB,Constant.MainIntentTab.DISCOVER);
//        getOwnerActivity().startActivity(intent);
//        getOwnerActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
//        dismiss();

        /**跳转二级页面的发现搜索**/
        Intent intent = new Intent(getOwnerActivity(), SearchActivity.class);
        intent.putExtra(MainActivity.EXTRA_TAB,Constant.MainIntentTab.DISCOVER);
        getOwnerActivity().startActivity(intent);
        dismiss();

    }

    private void clickNews()
    {
        if (login_status == 0)
        {
            Intent intent = new Intent(getOwnerActivity(), LoginActivity.class);
            getOwnerActivity().startActivity(intent);
        }else if (login_status == 1)
        {
            Intent intent = new Intent(getOwnerActivity(), MessageCenterActivity.class);
            getOwnerActivity().startActivity(intent);
        }
        dismiss();
    }

    private void clickCart()
    {
//        SDEventManager.post(new EIntentShopCart());
//        Intent intent = new Intent(getOwnerActivity(), MainActivity.class);
//        intent.putExtra(MainActivity.EXTRA_TAB,Constant.MainIntentTab.SHOP_CART);
//        getOwnerActivity().startActivity(intent);
        Intent intent = new Intent(App.getApplication(), AppWebViewActivity.class);//wap
        String url = ApkConstant.SERVER_URL_WAP;
        UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
        urlBuilder.add("ctl", "cart");
        intent.putExtra(AppWebViewActivity.EXTRA_URL, urlBuilder.build());
        getOwnerActivity().startActivity(intent);
        dismiss();
    }

    private void clickMy()
    {
        SDEventManager.post(new EIntentUserCenter());
        Intent intent = new Intent(getOwnerActivity(), MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.USER_CENTER);
        getOwnerActivity().startActivity(intent);
        getOwnerActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
        dismiss();
    }

    @Override
    public void showTop()
    {
      super.showTop();
      if(animator!=null){
        animator.start();
      }
    }

}
