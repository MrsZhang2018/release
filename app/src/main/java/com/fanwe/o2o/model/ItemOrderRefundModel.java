package com.fanwe.o2o.model;

/**
 * 退款商品数组 Created by Administrator on 2017/3/1.
 */

public class ItemOrderRefundModel {
  private String mid; //退款申请的消息id
  private String cid;
  private String id;
  private String number;//退货数量
  private String unit_price;//商品单价
  private String total_price;//商品总价
  private String refund_money;
  private String rs1;
  private String rs2;
  private String coupon_price;
  private String order_deal_id;
  private String name; //商品名称
  private String deal_icon; //商品图片路径
  private String supplier_name; //商户或平台名称
  private String refund_status;//退款代码 1:退款申请 2:已退款 3:驳回申请
  private String status_str;//同refund_status状态描述

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getUnit_price() {
    return unit_price;
  }

  public void setUnit_price(String unit_price) {
    this.unit_price = unit_price;
  }

  public String getTotal_price() {
    return total_price;
  }

  public void setTotal_price(String total_price) {
    this.total_price = total_price;
  }

  public String getRefund_money() {
    return refund_money;
  }

  public void setRefund_money(String refund_money) {
    this.refund_money = refund_money;
  }

  public String getRs1() {
    return rs1;
  }

  public void setRs1(String rs1) {
    this.rs1 = rs1;
  }

  public String getRs2() {
    return rs2;
  }

  public void setRs2(String rs2) {
    this.rs2 = rs2;
  }

  public String getCoupon_price() {
    return coupon_price;
  }

  public void setCoupon_price(String coupon_price) {
    this.coupon_price = coupon_price;
  }

  public String getOrder_deal_id() {
    return order_deal_id;
  }

  public void setOrder_deal_id(String order_deal_id) {
    this.order_deal_id = order_deal_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDeal_icon() {
    return deal_icon;
  }

  public void setDeal_icon(String deal_icon) {
    this.deal_icon = deal_icon;
  }

  public String getSupplier_name() {
    return supplier_name;
  }

  public void setSupplier_name(String supplier_name) {
    this.supplier_name = supplier_name;
  }

  public String getRefund_status() {
    return refund_status;
  }

  public void setRefund_status(String refund_status) {
    this.refund_status = refund_status;
  }

  public String getStatus_str() {
    return status_str;
  }

  public void setStatus_str(String status_str) {
    this.status_str = status_str;
  }
}


