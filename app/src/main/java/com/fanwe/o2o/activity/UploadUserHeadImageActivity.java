package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.appview.O2oCropImageView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.event.EUpLoadUserHeadImageComplete;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.UploadImageActModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * 上传头像
 * Created by Administrator on 2016/7/25.
 */
public class UploadUserHeadImageActivity extends BaseTitleActivity
{
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    @ViewInject(R.id.crop_imageview)
    private O2oCropImageView cropView;

    private String mStrUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_image);
        init();
    }

    private void init()
    {
        initTitle();
        getIntentData();
        initCropView();
    }

    private void initTitle()
    {
        title.setMiddleTextTop("上传头像");
        title.initRightItem(1);
        title.getItemRight(0).setTextBot("上传");
    }

    private void getIntentData()
    {
        mStrUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        if (isEmpty(mStrUrl))
        {
            SDToast.showToast("图片不存在");
            finish();
        }
        File file = new File(mStrUrl);
        if (!file.exists())
        {
            SDToast.showToast("图片不存在");
            finish();
        }
        mStrUrl = "file://" + mStrUrl;
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        cropView.clickUpload();
    }

    private void initCropView()
    {
        cropView.setImageFileCompresserListener(new ImageFileCompresser.ImageFileCompresserListener()
        {
            @Override
            public void onStart()
            {
                showProgressDialog("正在处理图片");
            }

            @Override
            public void onSuccess(File fileCompressed)
            {
                requestUpload(fileCompressed);
            }

            @Override
            public void onFailure(String msg)
            {
                if (!TextUtils.isEmpty(msg))
                {
                    SDToast.showToast(msg);
                }
            }

            @Override
            public void onFinish()
            {
                dismissProgressDialog();
            }
        });
        cropView.loadUrl(mStrUrl);
    }

    protected void requestUpload(File fileCompressed)
    {
        if (fileCompressed == null)
        {
            return;
        }

        if (!fileCompressed.exists())
        {
            return;
        }

        CommonInterface.requestUploadImage(fileCompressed, new AppRequestCallback<UploadImageActModel>()
        {
            @Override
            public void onStart()
            {
                showProgressDialog("正在上传");
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    String path = actModel.getBig_url();
                    if (!TextUtils.isEmpty(path))
                    {
                        EUpLoadUserHeadImageComplete event = new EUpLoadUserHeadImageComplete();
                        event.head_image = path;
                        SDEventManager.post(event);
                        finish();
                    } else
                    {
                        SDToast.showToast("图片地址为空");
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                SDToast.showToast("上传失败");
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }
}
