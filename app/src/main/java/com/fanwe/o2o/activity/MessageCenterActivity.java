package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppUserCenterMessageActModel;
import com.fanwe.o2o.model.UserCenterMsgAccountModel;
import com.fanwe.o2o.model.UserCenterMsgConfirmModel;
import com.fanwe.o2o.model.UserCenterMsgDeliveryModel;
import com.fanwe.o2o.model.UserCenterMsgNoticeModel;

/**
 * Created by Administrator on 2017/1/9.
 */

public class MessageCenterActivity extends BaseTitleActivity
{
    private LinearLayout ll_logistics;
    private TextView tv_logistics_num;//物流消息数
    private TextView tv_logistics_title;//物流消息标题
    private TextView tv_logistics;//物流消息
    private TextView tv_logistics_time;//物流消息时间
    private LinearLayout ll_notice;
    private TextView tv_notice_num;
    private TextView tv_notice_title;
    private TextView tv_notice;
    private TextView tv_notice_time;
    private LinearLayout ll_assets;
    private TextView tv_assets_num;
    private TextView tv_assets_title;
    private TextView tv_assets;
    private TextView tv_assets_time;
    private LinearLayout ll_verify;
    private TextView tv_verify_num;
    private TextView tv_verify_title;
    private TextView tv_verify;
    private TextView tv_verify_time;

    private UserCenterMsgDeliveryModel delivery;
    private UserCenterMsgNoticeModel notify;
    private UserCenterMsgAccountModel account;
    private UserCenterMsgConfirmModel confirm;

    private MoreTitleDialog titleDialog;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_message_center);
        initView();
        initData();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        requestUserCenterMessage();
    }

    private void initView()
    {
        ll_logistics = find(R.id.ll_logistics);
        tv_logistics_num = find(R.id.tv_logistics_num);
        tv_logistics_title = find(R.id.tv_logistics_title);
        tv_logistics = find(R.id.tv_logistics);
        tv_logistics_time = find(R.id.tv_logistics_time);
        ll_notice = find(R.id.ll_notice);
        tv_notice_num = find(R.id.tv_notice_num);
        tv_notice_title = find(R.id.tv_notice_title);
        tv_notice = find(R.id.tv_notice);
        tv_notice_time = find(R.id.tv_notice_time);
        ll_assets = find(R.id.ll_assets);
        tv_assets_num = find(R.id.tv_assets_num);
        tv_assets_title = find(R.id.tv_assets_title);
        tv_assets = find(R.id.tv_assets);
        tv_assets_time = find(R.id.tv_assets_time);
        ll_verify = find(R.id.ll_verify);
        tv_verify_num = find(R.id.tv_verify_num);
        tv_verify_title = find(R.id.tv_verify_title);
        tv_verify = find(R.id.tv_verify);
        tv_verify_time = find(R.id.tv_verify_time);
    }

    private void initData()
    {
        title.setMiddleTextTop("消息中心");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);

        ll_logistics.setOnClickListener(this);
        ll_notice.setOnClickListener(this);
        ll_assets.setOnClickListener(this);
        ll_verify.setOnClickListener(this);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        super.onCLickRight_SDTitleSimple(v, index);
        if (titleDialog == null)
            titleDialog = new MoreTitleDialog(this);
        titleDialog.requestData();
        titleDialog.showTop();
    }

    private void requestUserCenterMessage()
    {
        CommonInterface.requestUserCenterMessage(new AppRequestCallback<AppUserCenterMessageActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    delivery = actModel.getData().getDelivery();
                    notify = actModel.getData().getNotify();
                    account = actModel.getData().getAccount();
                    confirm = actModel.getData().getConfirm();
                    if (delivery != null)
                    {
                        String unReadStr = delivery.getUnread();
                        int unRead = Integer.parseInt(unReadStr);
                        SDViewUtil.show(tv_logistics_num);
                        if (unRead > 9)
                            SDViewBinder.setTextView(tv_logistics_num,"9+");
                        else if (unRead == 0)
                            SDViewUtil.hide(tv_logistics_num);
                        else
                            SDViewBinder.setTextView(tv_logistics_num,unReadStr);
//                        SDViewBinder.setTextView(tv_logistics_title,delivery.getTitle());
                        SDViewBinder.setTextView(tv_logistics,delivery.getContent());
                        SDViewBinder.setTextView(tv_logistics_time,delivery.getCreate_time());
                    }else
                    {
                        SDViewUtil.hide(tv_logistics_num);
//                        SDViewBinder.setTextView(tv_logistics_title,"物流消息");
                        SDViewBinder.setTextView(tv_logistics,"暂无消息");
                        SDViewBinder.setTextView(tv_logistics_time,"");
                    }

                    if (notify != null)
                    {
                        String unReadStr = notify.getUnread();
                        int unRead = Integer.parseInt(unReadStr);
                        SDViewUtil.show(tv_notice_num);
                        if (unRead > 9)
                            SDViewBinder.setTextView(tv_notice_num,"9+");
                        else if (unRead == 0)
                            SDViewUtil.hide(tv_notice_num);
                        else
                            SDViewBinder.setTextView(tv_notice_num,unReadStr);
//                        SDViewBinder.setTextView(tv_notice_title,notify.getTitle());
                        SDViewBinder.setTextView(tv_notice,notify.getContent());
                        SDViewBinder.setTextView(tv_notice_time,notify.getCreate_time());
                    }else
                    {
                        SDViewUtil.hide(tv_notice_num);
//                        SDViewBinder.setTextView(tv_notice_title,"通知消息");
                        SDViewBinder.setTextView(tv_notice,"暂无消息");
                        SDViewBinder.setTextView(tv_notice_time,"");
                    }

                    if (account != null)
                    {
                        String unReadStr = account.getUnread();
                        int unRead = Integer.parseInt(unReadStr);
                        SDViewUtil.show(tv_assets_num);
                        if (unRead > 9)
                            SDViewBinder.setTextView(tv_assets_num,"9+");
                        else if (unRead == 0)
                            SDViewUtil.hide(tv_assets_num);
                        else
                            SDViewBinder.setTextView(tv_assets_num,unReadStr);
//                        SDViewBinder.setTextView(tv_assets_title,account.getTitle());
                        SDViewBinder.setTextView(tv_assets,account.getContent());
                        SDViewBinder.setTextView(tv_assets_time,account.getCreate_time());
                    }else
                    {
                        SDViewUtil.hide(tv_assets_num);
//                        SDViewBinder.setTextView(tv_assets_title,"资产消息");
                        SDViewBinder.setTextView(tv_assets,"暂无消息");
                        SDViewBinder.setTextView(tv_assets_time,"");
                    }

                    if (confirm != null)
                    {
                        String unReadStr = confirm.getUnread();
                        int unRead = Integer.parseInt(unReadStr);
                        SDViewUtil.show(tv_verify_num);
                        if (unRead > 9)
                            SDViewBinder.setTextView(tv_verify_num,"9+");
                        else if (unRead == 0)
                            SDViewUtil.hide(tv_verify_num);
                        else
                            SDViewBinder.setTextView(tv_verify_num,unReadStr);
//                        SDViewBinder.setTextView(tv_verify_title,confirm.getTitle());
                        SDViewBinder.setTextView(tv_verify,confirm.getContent());
                        SDViewBinder.setTextView(tv_verify_time,confirm.getCreate_time());
                    }else
                    {
                        SDViewUtil.hide(tv_verify_num);
//                        SDViewBinder.setTextView(tv_verify_title,"验证消息");
                        SDViewBinder.setTextView(tv_verify,"暂无消息");
                        SDViewBinder.setTextView(tv_verify_time,"");
                    }
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
        if (v == ll_logistics)
        {
            if (delivery != null)
            {
//                String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_msg&act=cate&type=" + delivery.getType();
//                clickWebView(url);
                Intent intent = new Intent(this,MessageListActivity.class);
                intent.putExtra(MessageListActivity.EXTRA_TYPE,delivery.getType());
                startActivity(intent);
            }
        }else if (v == ll_notice)
        {
            if (notify != null)
            {
//                String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_msg&act=cate&type=" + notify.getType();
//                clickWebView(url);
                Intent intent = new Intent(this,MessageListActivity.class);
                intent.putExtra(MessageListActivity.EXTRA_TYPE,notify.getType());
                startActivity(intent);
            }
        }else if (v == ll_assets)
        {
            if (account != null)
            {
//                String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_msg&act=cate&type=" + account.getType();
//                clickWebView(url);
                Intent intent = new Intent(this,MessageListActivity.class);
                intent.putExtra(MessageListActivity.EXTRA_TYPE,account.getType());
                startActivity(intent);
            }
        }else if (v == ll_verify)
        {
            if (confirm != null)
            {
//                String url = ApkConstant.SERVER_URL_WAP + "?ctl=uc_msg&act=cate&type=" + confirm.getType();
//                clickWebView(url);
                Intent intent = new Intent(this,MessageListActivity.class);
                intent.putExtra(MessageListActivity.EXTRA_TYPE,confirm.getType());
                startActivity(intent);
            }
        }
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(this, AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        requestUserCenterMessage();
    }
}
