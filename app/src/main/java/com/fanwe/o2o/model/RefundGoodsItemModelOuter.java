package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RefundGoodsItemModelOuter {

  private String supplier_name;
  private String count;
  private List<RefundGoodsItemModelInner> list;

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

  public List<RefundGoodsItemModelInner> getList() {
    return list;
  }

  public void setList(
      List<RefundGoodsItemModelInner> list) {
    this.list = list;
  }

  public static class RefundGoodsItemModelInner {

    private String id;   // id代表coupon-id的只有两种情况:is_shop=1&&is_pick=1  或者 is_shop=0 ,否则id代表deal_id
    private String password;
    private String deal_id;   //这个字段没用
    private String deal_icon;
    private String name;
    private String sub_name;
    private String number;
    private String unit_price;
    private String total_price;
    private String buy_type;
    private String is_pick;
    private String expire_refund;
    private String any_refund;
    private String consume_count;
    private String dp_id;
    private String delivery_status;
    private String is_shop;
    private String is_arrival;
    private String deal_orders;
    private String is_refund;
    private String refund_status;
    private String supplier_id;
    private String attr_str;

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

    public String getDeal_id() {
      return deal_id;
    }

    public void setDeal_id(String deal_id) {
      this.deal_id = deal_id;
    }

    public String getDeal_icon() {
      return deal_icon;
    }

    public void setDeal_icon(String deal_icon) {
      this.deal_icon = deal_icon;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSub_name() {
      return sub_name;
    }

    public void setSub_name(String sub_name) {
      this.sub_name = sub_name;
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

    public String getBuy_type() {
      return buy_type;
    }

    public void setBuy_type(String buy_type) {
      this.buy_type = buy_type;
    }

    public String getIs_pick() {
      return is_pick;
    }

    public void setIs_pick(String is_pick) {
      this.is_pick = is_pick;
    }

    public String getExpire_refund() {
      return expire_refund;
    }

    public void setExpire_refund(String expire_refund) {
      this.expire_refund = expire_refund;
    }

    public String getAny_refund() {
      return any_refund;
    }

    public void setAny_refund(String any_refund) {
      this.any_refund = any_refund;
    }

    public String getConsume_count() {
      return consume_count;
    }

    public void setConsume_count(String consume_count) {
      this.consume_count = consume_count;
    }

    public String getDp_id() {
      return dp_id;
    }

    public void setDp_id(String dp_id) {
      this.dp_id = dp_id;
    }

    public String getDelivery_status() {
      return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
      this.delivery_status = delivery_status;
    }

    public String getIs_shop() {
      return is_shop;
    }

    public void setIs_shop(String is_shop) {
      this.is_shop = is_shop;
    }

    public String getIs_arrival() {
      return is_arrival;
    }

    public void setIs_arrival(String is_arrival) {
      this.is_arrival = is_arrival;
    }

    public String getDeal_orders() {
      return deal_orders;
    }

    public void setDeal_orders(String deal_orders) {
      this.deal_orders = deal_orders;
    }

    public String getIs_refund() {
      return is_refund;
    }

    public void setIs_refund(String is_refund) {
      this.is_refund = is_refund;
    }

    public String getRefund_status() {
      return refund_status;
    }

    public void setRefund_status(String refund_status) {
      this.refund_status = refund_status;
    }

    public String getSupplier_id() {
      return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
      this.supplier_id = supplier_id;
    }

    public String getAttr_str() {
      return attr_str;
    }

    public void setAttr_str(String attr_str) {
      this.attr_str = attr_str;
    }
  }
}
