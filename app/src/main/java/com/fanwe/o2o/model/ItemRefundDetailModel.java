package com.fanwe.o2o.model;

/**
 * 有团购商品和普通商品两种情况
 * Created by Administrator on 2017/3/2.
 */

public class ItemRefundDetailModel {
  private String id;
  private String deal_id; //商品id
  private String number; //退款数量
  private float unit_price;//退款单价
  private float total_price;//退款总价
  private String rs2; //1申请退款中 2已退款 3退款被拒绝
  private String name;//商品名称
  private String attr_str;//	商品属性 有传did时才有
  private String message_id;
  private String deal_icon;//	商品图片路径
  private String supplier_name;//	平台属性
  private String create_time;
  private String refund_money;
  private String admin_memo;
  private String refund_info; //退款状态描述
  private String content;//退款理由

  private String refund_status;//	退款状态 1:退款中 2:已退款 3:拒绝退款
  private int is_coupon;//团购商品标识 没有传did时()才有
  private String password;//团购券编号 没有传did时才有

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDeal_id() {
    return deal_id;
  }

  public void setDeal_id(String deal_id) {
    this.deal_id = deal_id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public float getUnit_price() {
    return unit_price;
  }

  public void setUnit_price(float unit_price) {
    this.unit_price = unit_price;
  }

  public float getTotal_price() {
    return total_price;
  }

  public void setTotal_price(float total_price) {
    this.total_price = total_price;
  }

  public String getRefund_status() {
    return refund_status;
  }

  public void setRefund_status(String refund_status) {
    this.refund_status = refund_status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAttr_str() {
    return attr_str;
  }

  public void setAttr_str(String attr_str) {
    this.attr_str = attr_str;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getRefund_info() {
    return refund_info;
  }

  public void setRefund_info(String refund_info) {
    this.refund_info = refund_info;
  }

  public int getIs_coupon() {
    return is_coupon;
  }

  public void setIs_coupon(int is_coupon) {
    this.is_coupon = is_coupon;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRs2() {
    return rs2;
  }

  public void setRs2(String rs2) {
    this.rs2 = rs2;
  }

  public String getMessage_id() {
    return message_id;
  }

  public void setMessage_id(String message_id) {
    this.message_id = message_id;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getRefund_money() {
    return refund_money;
  }

  public void setRefund_money(String refund_money) {
    this.refund_money = refund_money;
  }

  public String getAdmin_memo() {
    return admin_memo;
  }

  public void setAdmin_memo(String admin_memo) {
    this.admin_memo = admin_memo;
  }
}
