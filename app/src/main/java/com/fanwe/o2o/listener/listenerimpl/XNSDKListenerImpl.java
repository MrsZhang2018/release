package com.fanwe.o2o.listener.listenerimpl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.fanwe.o2o.activity.AppWebViewActivity;

import cn.xiaoneng.uiapi.XNSDKListener;

/**
 * 小能客服SDK 各种点击监听
 * Created by hyc on 2017/6/9.
 * http://doc.xiaoneng.cn/doku.php?id=android1:importsdk
 */

public class XNSDKListenerImpl implements XNSDKListener
{

    private Context context;

    public XNSDKListenerImpl(Context context)
    {
        this.context = context;
    }

    @Override
    public void onChatMsg(boolean b, String s, String s1, String s2, long l, boolean b1)
    {

    }

    @Override
    public void onUnReadMsg(String s, String s1, String s2, int i)
    {

    }

    @Override
    public void onClickMatchedStr(String s, String s1)
    {

    }

    @Override
    public void onClickUrlorEmailorNumber(int i, String s)
    {
        Intent intent = new Intent(context, AppWebViewActivity.class);
        if (!TextUtils.isEmpty(s))
        {
            if (!s.contains("http"))
            {
                s = "http://" + s;
            }
        }
        intent.putExtra(AppWebViewActivity.EXTRA_URL,s);
        context.startActivity(intent);
    }

    @Override
    public void onClickShowGoods(int i, int i1, String s, String s1, String s2, String s3, String s4, String s5)
    {
        Intent intent = new Intent(context, AppWebViewActivity.class);
        intent.putExtra(AppWebViewActivity.EXTRA_URL,s4);
        context.startActivity(intent);
    }

    @Override
    public void onError(int i)
    {

    }
}
