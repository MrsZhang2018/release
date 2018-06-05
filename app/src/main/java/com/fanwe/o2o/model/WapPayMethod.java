package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/3/22.
 */

public class WapPayMethod  {

  /**
   * open_url_type : 1
   * url : https://o2owap.fanwe.net/wap/index.php?ctl=payment&act=done&id=522&page_type=app
   * title : 支付宝手机支付(WAP版本)
   */

  private String open_url_type;  //0打开带WebActicity 1打开系统浏览器
  private String url;
  private String title;

  public String getOpen_url_type() {
    return open_url_type;
  }

  public void setOpen_url_type(String open_url_type) {
    this.open_url_type = open_url_type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
