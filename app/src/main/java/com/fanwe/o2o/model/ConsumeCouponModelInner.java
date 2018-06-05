package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/3/9.
 */

public abstract class ConsumeCouponModelInner {
  protected String id;
  protected String password; //券码
  protected String end_time;
  protected String confirm_time; //确认时间
  protected String is_new;
  protected String is_valid;
  protected String qrcode;
  protected String status; //1:可使用，0：不可使用
  protected String info;  //可使用 //状态说明

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public String getConfirm_time() {
    return confirm_time;
  }

  public void setConfirm_time(String confirm_time) {
    this.confirm_time = confirm_time;
  }

  public String getIs_new() {
    return is_new;
  }

  public void setIs_new(String is_new) {
    this.is_new = is_new;
  }

  public String getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(String is_valid) {
    this.is_valid = is_valid;
  }

  public String getQrcode() {
    return qrcode;
  }

  public void setQrcode(String qrcode) {
    this.qrcode = qrcode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
