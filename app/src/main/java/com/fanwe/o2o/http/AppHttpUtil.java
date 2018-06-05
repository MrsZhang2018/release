package com.fanwe.o2o.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.utils.SDCookieFormater;
import com.fanwe.library.adapter.http.SDHttpUtil;
import com.fanwe.library.adapter.http.callback.SDRequestCallback;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDFileBody;
import com.fanwe.library.adapter.http.model.SDMultiFile;
import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDCookieManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.o2o.R;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AppHttpUtil extends SDHttpUtil
{

    private static AppHttpUtil mInstance;

    private AppHttpUtil()
    {

    }

    public static AppHttpUtil getInstance()
    {
        if (mInstance == null)
        {
            synchronized (AppHttpUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new AppHttpUtil();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected SDRequestHandler postImpl(SDRequestParams params, final SDRequestCallback callback)
    {
        callback.notifyStart();
        if (callback instanceof AppRequestCallback)
        {
            AppRequestCallback appCallback = (AppRequestCallback) callback;
            if (appCallback.isCache)
            {
                return null;
            }
        }
        Cancelable cancelable = x.http().post(parseRequestParams(params), new CommonCallback<String>()
        {
            private SDResponse response = new SDResponse();

            @Override
            public void onCancelled(CancelledException e)
            {
                callback.notifyCancel(response);
            }

            @Override
            public void onError(Throwable t, boolean b)
            {
                response.setThrowable(t);
                callback.notifyError(response);
            }

            @Override
            public void onFinished()
            {
                callback.notifyFinish(response);
            }

            @Override
            public void onSuccess(String result)
            {
                response.setResult(result);
                callback.notifySuccess(response);
            }
        });

        AppRequestHandler requestHandler = new AppRequestHandler(cancelable);
        callback.setRequestHandler(requestHandler);
        return requestHandler;
    }

    @Override
    protected SDRequestHandler getImpl(SDRequestParams params, final SDRequestCallback callback)
    {
        callback.notifyStart();
        if (callback instanceof AppRequestCallback)
        {
            AppRequestCallback appCallback = (AppRequestCallback) callback;
            if (appCallback.isCache)
            {
                return null;
            }
        }
        Cancelable cancelable = x.http().get(parseRequestParams(params), new CommonCallback<String>()
        {
            private SDResponse response = new SDResponse();

            @Override
            public void onCancelled(CancelledException e)
            {
                callback.notifyCancel(response);
            }

            @Override
            public void onError(Throwable t, boolean b)
            {
                response.setThrowable(t);
                callback.notifyError(response);
            }

            @Override
            public void onFinished()
            {
                callback.notifyFinish(response);
            }

            @Override
            public void onSuccess(String result)
            {
                response.setResult(result);
                callback.notifySuccess(response);
            }
        });

        AppRequestHandler requestHandler = new AppRequestHandler(cancelable);
        callback.setRequestHandler(requestHandler);
        return requestHandler;
    }

    public RequestParams parseRequestParams(SDRequestParams params)
    {
        String ctl = String.valueOf(params.getCtl());
        String act = String.valueOf(params.getAct());
        String url = params.getUrl();

        if (ApkConstant.SERVER_URL_INIT_URL.equals(url))
        {

        }

        RequestParams request = new RequestParams(url);
        printUrl(params);
        initCookie();

        Map<String, Object> data = params.getData();
        if (!data.isEmpty())
        {
            for (Entry<String, Object> item : data.entrySet())
            {
                String key = item.getKey();
                Object value = item.getValue();

                if (value != null)
                {
                    request.addBodyParameter(key, String.valueOf(value));
                }
            }
        }

        Map<String, SDFileBody> dataFile = params.getDataFile();
        if (!dataFile.isEmpty())
        {
            request.setMultipart(true);
            for (Entry<String, SDFileBody> item : dataFile.entrySet())
            {
                SDFileBody fileBody = item.getValue();
                request.addBodyParameter(item.getKey(), fileBody.getFile(), fileBody.getContentType(), fileBody.getFileName());
            }
        }

        List<SDMultiFile> listFile = params.getDataMultiFile();
        if (!listFile.isEmpty())
        {
            request.setMultipart(true);
            for (SDMultiFile item : listFile)
            {
                SDFileBody fileBody = item.getFileBody();
                request.addBodyParameter(item.getKey(), fileBody.getFile(), fileBody.getContentType(), fileBody.getFileName());
            }
        }

        return request;
    }

    private void initHttpCookie()
    {
        try
        {
            String cookie = SDCookieManager.getInstance().getCookie(ApkConstant.SERVER_URL_API);
            if (!TextUtils.isEmpty(cookie))
            {
                List<HttpCookie> listCookie = HttpCookie.parse(cookie);
                if (listCookie != null)
                {
                    URI uri = new URI(ApkConstant.SERVER_URL_API);
                    for (HttpCookie item : listCookie)
                    {
                        DbCookieStore.INSTANCE.add(uri, item);
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.e("put webview cookie to http error:" + e.toString());
        }
    }

    /**
     * 强制将cookie同步到WebView
     * @param url WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     */
    public static boolean syncCookie(String url,String cookie) {
        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        //    CookieSyncManager.createInstance(context);
        //}
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie)?false:true;
    }

    private void initCookie()
    {


        DbCookieStore.INSTANCE.getCookies();

        String cookie = SDConfig.getInstance().getString(R.string.config_webview_cookie, "");
        if (!TextUtils.isEmpty(cookie))
        {
            SDCookieFormater formater = new SDCookieFormater(cookie);
            Map<String, String> mapCookie = formater.format();
            if (!mapCookie.isEmpty())
            {
                for (Entry<String, String> item : mapCookie.entrySet())
                {
                    HttpCookie bcc = new HttpCookie(item.getKey(), item.getValue());
                    try
                    {
                        URI uri = new URI(ApkConstant.SERVER_URL_API);
                        DbCookieStore.INSTANCE.remove(uri, bcc);
                        DbCookieStore.INSTANCE.add(uri, bcc);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        LogUtil.e("put webview cookie to http error:" + e.toString());
                    }
                }
            }
        }

    }

    private void printUrl(SDRequestParams params)
    {
        if (ApkConstant.DEBUG)
        {
            if (params != null)
            {
                LogUtil.i(params.parseToUrl());
            }
        }
    }
}
