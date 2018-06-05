package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/10.
 */

public class UcMsgCateDataModel extends BaseActModel {


  /**
   * id : 1248
   * content : 您的优惠券<248780730258>已过期
   * user_id : 73
   * create_time : 2017-03-03 15:48
   * is_read : 1
   * is_delete : 0
   * type : notify
   * data_id : 0
   * title : 优惠券已过期
   * link : /wap/index.php?ctl=uc_youhui
   */

  private String id;
  private String content;
  private String user_id;
  private String create_time;
  private String is_read;
  private String is_delete;
  private String type;
  private String data_id;
  private String title;
  private String link;
  private String wap_link;
  private AppTypeModel app;

  public AppTypeModel getApp() {
    return app;
  }

  public void setApp(AppTypeModel app) {
    this.app = app;
  }

  public String getWap_link() {
    return wap_link;
  }

  public void setWap_link(String wap_link) {
    this.wap_link = wap_link;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getIs_read() {
    return is_read;
  }

  public void setIs_read(String is_read) {
    this.is_read = is_read;
  }

  public String getIs_delete() {
    return is_delete;
  }

  public void setIs_delete(String is_delete) {
    this.is_delete = is_delete;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getData_id() {
    return data_id;
  }

  public void setData_id(String data_id) {
    this.data_id = data_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
