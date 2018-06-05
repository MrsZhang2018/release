package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/2.
 */

public class UcOrderItemDealOrderItemListModel{


  /**
   * id : 630
   * deal_id : 299
   * deal_icon : https://o2owap.fanwe.net/public/attachment/201609/20/15/57e0e2f54140e_244x148.jpg
   * name : 明视眼镜1
   * sub_name : 明视眼镜1
   * number : 8
   * unit_price : 88
   * total_price : 704
   * buy_type : 0
   * consume_count : 0
   * dp_id : 0
   * delivery_status : 5
   * is_arrival : 0
   * deal_orders : 待使用
   * is_refund : 1
   * refund_status : 1
   * supplier_id : 54
   * attr_str :
   * app_format_unit_price : 88.00
   */

  private String id;
  private String deal_id;
  private String deal_icon;
  private String name;
  private String sub_name;
  private String number;
  private int unit_price;
  private int total_price;
  private String buy_type;
  private int consume_count;
  private int dp_id;
  private int delivery_status;
  private int is_arrival;
  private String deal_orders;
  private int is_refund;
  private int refund_status;
  private int supplier_id;
  private String attr_str;
  private String password;
  private String app_format_unit_price;

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

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

  public int getUnit_price() {
    return unit_price;
  }

  public void setUnit_price(int unit_price) {
    this.unit_price = unit_price;
  }

  public int getTotal_price() {
    return total_price;
  }

  public void setTotal_price(int total_price) {
    this.total_price = total_price;
  }

  public String getBuy_type() {
    return buy_type;
  }

  public void setBuy_type(String buy_type) {
    this.buy_type = buy_type;
  }

  public int getConsume_count() {
    return consume_count;
  }

  public void setConsume_count(int consume_count) {
    this.consume_count = consume_count;
  }

  public int getDp_id() {
    return dp_id;
  }

  public void setDp_id(int dp_id) {
    this.dp_id = dp_id;
  }

  public int getDelivery_status() {
    return delivery_status;
  }

  public void setDelivery_status(int delivery_status) {
    this.delivery_status = delivery_status;
  }

  public int getIs_arrival() {
    return is_arrival;
  }

  public void setIs_arrival(int is_arrival) {
    this.is_arrival = is_arrival;
  }

  public String getDeal_orders() {
    return deal_orders;
  }

  public void setDeal_orders(String deal_orders) {
    this.deal_orders = deal_orders;
  }

  public int getIs_refund() {
    return is_refund;
  }

  public void setIs_refund(int is_refund) {
    this.is_refund = is_refund;
  }

  public int getRefund_status() {
    return refund_status;
  }

  public void setRefund_status(int refund_status) {
    this.refund_status = refund_status;
  }

  public int getSupplier_id() {
    return supplier_id;
  }

  public void setSupplier_id(int supplier_id) {
    this.supplier_id = supplier_id;
  }

  public String getAttr_str() {
    return attr_str;
  }

  public void setAttr_str(String attr_str) {
    this.attr_str = attr_str;
  }

  public String getApp_format_unit_price() {
    return app_format_unit_price;
  }

  public void setApp_format_unit_price(String app_format_unit_price) {
    this.app_format_unit_price = app_format_unit_price;
  }
}
