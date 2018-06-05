package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.event.ELoginEvent;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ELoginOut;
import com.fanwe.o2o.event.ELoginSuccess;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppUserLoginOutActModel;
import com.fanwe.o2o.model.AppUserSettingActModel;
import com.fanwe.o2o.utils.DataCleanManager;
import com.sunday.eventbus.SDEventManager;

import cn.xiaoneng.uiapi.Ntalker;

/**
 * Created by Administrator on 2017/1/9.
 */

public class SettingActivity extends BaseTitleActivity
{
    private RelativeLayout rl_version;
    private TextView tv_version;
    private RelativeLayout rl_service_phone;
    private TextView tv_tel;
    private RelativeLayout rl_service_mailbox;
    private TextView tv_email;
    private RelativeLayout rl_about_us;
    private RelativeLayout rl_login_out;
    private RelativeLayout rl_clean_cache;
    private TextView tv_clean_chache;

    private String html;
    private String cacheSize = "0MB";
    private MoreTitleDialog titleDialog;
    private SDDialogConfirm dialog;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_setting);

        rl_version = find(R.id.rl_version);
        tv_version = find(R.id.tv_version);
        rl_service_phone = find(R.id.rl_service_phone);
        tv_tel = find(R.id.tv_tel);
        rl_service_mailbox = find(R.id.rl_service_mailbox);
        tv_email = find(R.id.tv_email);
        rl_about_us = find(R.id.rl_about_us);
        rl_login_out = find(R.id.rl_login_out);
        rl_clean_cache = find(R.id.rl_clean_cache);
        tv_clean_chache = find(R.id.tv_clean_chache);
        title.setMiddleTextTop("设置");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);

        requestUserSetting();
        cacheSize = getCacheSize();
        SDViewBinder.setTextView(tv_clean_chache, "(缓存大小:" + cacheSize + ")");

        rl_version.setOnClickListener(this);
        rl_clean_cache.setOnClickListener(this);
        rl_service_phone.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        rl_login_out.setOnClickListener(this);
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

    private void requestUserSetting()
    {
        CommonInterface.requestUserSetting(new AppRequestCallback<AppUserSettingActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    html = actModel.getAPP_ABOUT_US();
                    int login_status = actModel.getUser_login_status();
                    if (login_status == 1)
                        SDViewUtil.show(rl_login_out);
                    else if (login_status == 0)
                        SDViewUtil.hide(rl_login_out);

                    SDViewBinder.setTextView(tv_version,actModel.getDB_VERSION());
                    SDViewBinder.setTextView(tv_tel,actModel.getSHOP_TEL());
                    SDViewBinder.setTextView(tv_email,actModel.getREPLY_ADDRESS());
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
        if (v == rl_version)
        {
            //版本更新
        } else if (v == rl_clean_cache) {
            //清理缓存
            clickCleanCache();
        } else if (v == rl_service_phone)
        {
            //客服电话
        }else if (v == rl_about_us)
        {
            //关于我们
            clickAboutUs();
        }else if (v == rl_login_out)
        {
            //退出登录
            clickLoginOut();
        }
    }

    private void clickAboutUs()
    {
        Intent intent = new Intent(this, AboutUsActivity.class);
        intent.putExtra(AboutUsActivity.EXTRA_HTML_CONTENT,html);
        startActivity(intent);
    }

    /**
     * 该方法清理了 /mnt/sdcard/android/data/com.xxx.xxx/cache  /mnt/sdcard/android/data/com.xxx.xxx/files
     * data/data/com.xx/cache ,data/data/com.xx/files 目录结构下的所有文件及目录
     */
    private void clickCleanCache() {
        showClearDialog(cacheSize);
    }

    private String getCacheSize() {
        final String formatedSize;
        final long externalCacheSize;
        final long externalFilesSize;
        final long internalCacheSize;
        final long internalFileCacheSize;
        final long totalSize;
        try {
            externalCacheSize =
                DataCleanManager.getFolderSize(getApplicationContext().getExternalCacheDir());
            externalFilesSize =
                DataCleanManager.getFolderSize(getApplicationContext().getExternalFilesDir(null));
            internalCacheSize = DataCleanManager.getFolderSize(getApplicationContext().getCacheDir());
            internalFileCacheSize = DataCleanManager.getFolderSize(getApplicationContext().getFilesDir());
            totalSize = externalCacheSize + externalFilesSize + internalCacheSize + internalFileCacheSize;
            formatedSize = DataCleanManager.getFormatSize(totalSize);
            return formatedSize;
        } catch (Exception e) {
            e.printStackTrace();
            return "0MB";
        }
    }

    private void showClearDialog(String content) {
        if (dialog == null) {
            dialog = new SDDialogConfirm(this);
            dialog.setTextGravity(Gravity.CENTER);
            dialog.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                @Override
                public void onClickCancel(View v, SDDialogCustom dialog) {

                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog) {
                    DataCleanManager.cleanInternalCache(getApplicationContext());
                    DataCleanManager.cleanFiles(getApplicationContext());
                    DataCleanManager.cleanExternalCache(getApplicationContext());
                    DataCleanManager.cleanExternalFiles(getApplicationContext());
                    cacheSize = getCacheSize();
                    SDViewBinder.setTextView(tv_clean_chache, "缓存大小:" + cacheSize);
                }

                @Override
                public void onDismiss(SDDialogCustom dialog) {
                    dialog.dismiss();
                }
            });
        }
        dialog.setTextContent("是否清空缓存:" + content);
        dialog.show();
    }

    private void clickLoginOut()
    {
        CommonInterface.requestUserLoginOut(new AppRequestCallback<AppUserLoginOutActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk())
                {
                    //用户登出： 用户注销时调用,调用之后用户为游客状态
                    Ntalker.getInstance().logout();

                    App.getApplication().clearAppsLocalUserModel();
                    SDEventManager.post(new ELoginEvent(false));
                    SDEventManager.post(new ELoginOut());
                    finish();
                }
            }
        });
    }

    public void onEventMainThread(ELoginSuccess event)
    {
        requestUserSetting();
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        requestUserSetting();
    }
}
