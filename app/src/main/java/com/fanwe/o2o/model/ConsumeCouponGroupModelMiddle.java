package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ConsumeCouponGroupModelMiddle {
  private String deal_id;
  private String user_id;
  private String name;
  private String end_time;
  private String begin_time;
  private String img;
  private String order_id;
  private String coupon_end_time;
  private String count;
  private List<ConsumeCouponGroupModelInner> coupon;

  public String getDeal_id() {
    return deal_id;
  }

  public void setDeal_id(String deal_id) {
    this.deal_id = deal_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public String getBegin_time() {
    return begin_time;
  }

  public void setBegin_time(String begin_time) {
    this.begin_time = begin_time;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public String getCoupon_end_time() {
    return coupon_end_time;
  }

  public void setCoupon_end_time(String coupon_end_time) {
    this.coupon_end_time = coupon_end_time;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public List<ConsumeCouponGroupModelInner> getCoupon() {
    return coupon;
  }

  public void setCoupon(List<ConsumeCouponGroupModelInner> coupon) {
    this.coupon = coupon;
  }
}
