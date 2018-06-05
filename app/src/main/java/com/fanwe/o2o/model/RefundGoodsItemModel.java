package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RefundGoodsItemModel {
  private String id;
  private String order_sn;
  private String order_status;
  private String pay_status;//消费金额
  private String delivery_status;
  private String create_time;//买单时间
  private String total_price;
  private String deal_total_price;
  private String supplier_name;
  private String count;
  private String delivery_id;
  private String pay_amount;
  private String discount_price;
  private String delivery_fee;
  private String record_delivery_fee;
  private String ecv_money;
  private String promote_arr;
  private String payment_fee;
  private String buy_type;
  private String is_delete;
  private String is_refund;
  private String is_groupbuy_or_pick;
  private String is_check_logistics;
  private String c;
  private String is_coupon;
  private String is_dp;
  private String status;
  private String youhui_price;
  private List<RefundGoodsItemModelOuter> deal_order_item;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrder_sn() {
    return order_sn;
  }

  public void setOrder_sn(String order_sn) {
    this.order_sn = order_sn;
  }

  public String getOrder_status() {
    return order_status;
  }

  public void setOrder_status(String order_status) {
    this.order_status = order_status;
  }

  public String getPay_status() {
    return pay_status;
  }

  public void setPay_status(String pay_status) {
    this.pay_status = pay_status;
  }

  public String getDelivery_status() {
    return delivery_status;
  }

  public void setDelivery_status(String delivery_status) {
    this.delivery_status = delivery_status;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getTotal_price() {
    return total_price;
  }

  public void setTotal_price(String total_price) {
    this.total_price = total_price;
  }

  public String getDeal_total_price() {
    return deal_total_price;
  }

  public void setDeal_total_price(String deal_total_price) {
    this.deal_total_price = deal_total_price;
  }

  public String getSupplier_name() {
    return supplier_name;
  }

  public void setSupplier_name(String supplier_name) {
    this.supplier_name = supplier_name;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public String getDelivery_id() {
    return delivery_id;
  }

  public void setDelivery_id(String delivery_id) {
    this.delivery_id = delivery_id;
  }

  public String getPay_amount() {
    return pay_amount;
  }

  public void setPay_amount(String pay_amount) {
    this.pay_amount = pay_amount;
  }

  public String getDiscount_price() {
    return discount_price;
  }

  public void setDiscount_price(String discount_price) {
    this.discount_price = discount_price;
  }

  public String getDelivery_fee() {
    return delivery_fee;
  }

  public void setDelivery_fee(String delivery_fee) {
    this.delivery_fee = delivery_fee;
  }

  public String getRecord_delivery_fee() {
    return record_delivery_fee;
  }

  public void setRecord_delivery_fee(String record_delivery_fee) {
    this.record_delivery_fee = record_delivery_fee;
  }

  public String getEcv_money() {
    return ecv_money;
  }

  public void setEcv_money(String ecv_money) {
    this.ecv_money = ecv_money;
  }

  public String getPromote_arr() {
    return promote_arr;
  }

  public void setPromote_arr(String promote_arr) {
    this.promote_arr = promote_arr;
  }

  public String getPayment_fee() {
    return payment_fee;
  }

  public void setPayment_fee(String payment_fee) {
    this.payment_fee = payment_fee;
  }

  public String getBuy_type() {
    return buy_type;
  }

  public void setBuy_type(String buy_type) {
    this.buy_type = buy_type;
  }

  public String getIs_delete() {
    return is_delete;
  }

  public void setIs_delete(String is_delete) {
    this.is_delete = is_delete;
  }

  public String getIs_refund() {
    return is_refund;
  }

  public void setIs_refund(String is_refund) {
    this.is_refund = is_refund;
  }

  public String getIs_groupbuy_or_pick() {
    return is_groupbuy_or_pick;
  }

  public void setIs_groupbuy_or_pick(String is_groupbuy_or_pick) {
    this.is_groupbuy_or_pick = is_groupbuy_or_pick;
  }

  public String getIs_check_logistics() {
    return is_check_logistics;
  }

  public void setIs_check_logistics(String is_check_logistics) {
    this.is_check_logistics = is_check_logistics;
  }

  public String getC() {
    return c;
  }

  public void setC(String c) {
    this.c = c;
  }

  public String getIs_coupon() {
    return is_coupon;
  }

  public void setIs_coupon(String is_coupon) {
    this.is_coupon = is_coupon;
  }

  public String getIs_dp() {
    return is_dp;
  }

  public void setIs_dp(String is_dp) {
    this.is_dp = is_dp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getYouhui_price() {
    return youhui_price;
  }

  public void setYouhui_price(String youhui_price) {
    this.youhui_price = youhui_price;
  }

  public List<RefundGoodsItemModelOuter> getDeal_order_item() {
    return deal_order_item;
  }

  public void setDeal_order_item(
      List<RefundGoodsItemModelOuter> deal_order_item) {
    this.deal_order_item = deal_order_item;
  }
}
