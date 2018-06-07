package com.fanwe.hybrid.jshandler;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.hybrid.model.QrCodeScan2Model;
import com.fanwe.library.utils.SDToast;
import com.sunday.eventbus.SDBaseEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/10/21.
 */

public class AddAppJsHandler extends AppJsHandler
{
    public AddAppJsHandler(Activity activity)
    {
        super(activity);
    }

    @JavascriptInterface
    public void qr_code_scan2(String json)
    {
        try
        {
            QrCodeScan2Model model = JSON.parseObject(json, QrCodeScan2Model.class);
            if (!TextUtils.isEmpty(model.getPrefix_data()))
            {
                EventBus.getDefault().post(new SDBaseEvent(model, EnumEventTag.EVENT_QR_CODE_SCAN_2.ordinal()));
            } else
            {
                SDToast.showToast("model.getPrdfix_data()为空");
            }
        } catch (Exception e)
        {
            SDToast.showToast("json解析异常");
        }

    }
}
