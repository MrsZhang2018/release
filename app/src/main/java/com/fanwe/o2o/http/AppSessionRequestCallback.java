package com.fanwe.o2o.http;

import android.text.TextUtils;
import com.fanwe.library.adapter.http.model.SDResponse;

/**
 * 此类用于强制webview同步sessionid,原因:账号在未登录的情况下,点击购物车(wap页面)结算
 * 跳转到LoginActivity登陆后,sessionID有可能并未及时同步到webview,导致H5判定app未登录.不会留在结算页面而是刷新购物车
 * Created by Administrator on 2017/3/23.
 */

public abstract class AppSessionRequestCallback<D> extends AppRequestCallback<D> {
  private String url;

  public AppSessionRequestCallback(String url) {
    this.url=url;
  }

  @Override protected void onSuccessBefore(SDResponse resp) {
    super.onSuccessBefore(resp);
    if (!TextUtils.isEmpty(sessionId) && !TextUtils.isEmpty(url)) {
      AppHttpUtil.syncCookie(url, sessionId);
    }
  }

}
