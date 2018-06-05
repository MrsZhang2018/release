package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/1/16.
 */

public class AccountManageUserInfoModel {
  private String id;
  private String user_name;
  private String total_score;
  private String point;
  private String mobile;
  private String email;
  private String password;
  private String user_avatar;
  private String is_tmp;//后台是否开启可以修改昵称 1.为改名字 0.不能改

  public String getIs_tmp()
  {
    return is_tmp;
  }

  public void setIs_tmp(String is_tmp)
  {
    this.is_tmp = is_tmp;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getTotal_score() {
    return total_score;
  }

  public void setTotal_score(String total_score) {
    this.total_score = total_score;
  }

  public String getPoint() {
    return point;
  }

  public void setPoint(String point) {
    this.point = point;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUser_avatar() {
    return user_avatar;
  }

  public void setUser_avatar(String user_avatar) {
    this.user_avatar = user_avatar;
  }
}
