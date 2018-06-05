package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ConsumeCouponInPersonMiddle {
  private String supplier_id;
  private String order_id;
  private String deal_id;
  private String is_valid;
  private String supplier_name;
  private String order_sn;
  private String all_number;
  private String end_time;
  private String count;
  private List<ConsumeCouponInPersonInner> coupon;

  public List<ConsumeCouponInPersonInner> getCoupon() {
    return coupon;
  }

  public void setCoupon(List<ConsumeCouponInPersonInner> coupon) {
    this.coupon = coupon;
  }

  public String getSupplier_id() {
    return supplier_id;
  }

  public void setSupplier_id(String supplier_id) {
    this.supplier_id = supplier_id;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public String getDeal_id() {
    return deal_id;
  }

  public void setDeal_id(String deal_id) {
    this.deal_id = deal_id;
  }

  public String getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(String is_valid) {
    this.is_valid = is_valid;
  }

  public String getSupplier_name() {
    return supplier_name;
  }

  public void setSupplier_name(String supplier_name) {
    this.supplier_name = supplier_name;
  }

  public String getOrder_sn() {
    return order_sn;
  }

  public void setOrder_sn(String order_sn) {
    this.order_sn = order_sn;
  }

  public String getAll_number() {
    return all_number;
  }

  public void setAll_number(String all_number) {
    this.all_number = all_number;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }
}
