package com.fanwe.o2o.model;


import java.util.List;

/**
 * Created by luod on 2017/3/2.
 */

public class UcOrderItemModel extends BaseActModel {

  /**
   * id : 392
   * order_sn : 2017022804540815
   * order_status : 0
   * pay_status : 2
   * delivery_status : 5
   * create_time : 2017-02-28 16:54:08
   * pay_amount : 563.2
   * total_price : 563.2
   * buy_type : 0
   * is_delete : 0
   * is_coupon : 1
   * is_groupbuy_or_pick : 1
   * deal_order_item : {"54":{"supplier_name":"明视眼镜","count":1,"list":[{"id":"630","deal_id":"299","deal_icon":"https://o2owap.fanwe.net/public/attachment/201609/20/15/57e0e2f54140e_244x148.jpg","name":"明视眼镜1","sub_name":"明视眼镜1","number":"8","unit_price":88,"total_price":704,"buy_type":"0","consume_count":0,"dp_id":0,"delivery_status":5,"is_arrival":0,"deal_orders":"待使用","is_refund":1,"refund_status":1,"supplier_id":54,"attr_str":"","app_format_unit_price":"88.00"}]}}
   * count : 8
   * is_check_logistics : 0
   * is_dp : 0
   * is_del : 0
   * status_name : 待确认
   * app_format_total_price : 563.20
   */

  private String id;
  private String order_sn;
  private String order_status;
  private String pay_status;
  private String delivery_status;
  private String create_time;
  private double pay_amount;
  private double total_price;
  private int buy_type;
  private String is_delete;
  private String is_coupon;
  private int is_groupbuy_or_pick;
  private int count;
  private int is_check_logistics;
  private int is_dp;
  private int is_del;
  private int is_pay;
  private String status_name;
  private String app_format_total_price;
  private List<UcOrderItemDealOrderItemModel> deal_order_item;
  private List<OperationModel> operation;

  public int getIs_pay() {
    return is_pay;
  }

  public void setIs_pay(int is_pay) {
    this.is_pay = is_pay;
  }

  public List<OperationModel> getOperation() {
    return operation;
  }

  public void setOperation(List<OperationModel> operation) {
    this.operation = operation;
  }

  public List<UcOrderItemDealOrderItemModel> getDeal_order_item() {
    return deal_order_item;
  }

  public void setDeal_order_item(List<UcOrderItemDealOrderItemModel> deal_order_item) {
    this.deal_order_item = deal_order_item;
  }

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

  public double getPay_amount() {
    return pay_amount;
  }

  public void setPay_amount(double pay_amount) {
    this.pay_amount = pay_amount;
  }

  public double getTotal_price() {
    return total_price;
  }

  public void setTotal_price(double total_price) {
    this.total_price = total_price;
  }

  public int getBuy_type() {
    return buy_type;
  }

  public void setBuy_type(int buy_type) {
    this.buy_type = buy_type;
  }

  public String getIs_delete() {
    return is_delete;
  }

  public void setIs_delete(String is_delete) {
    this.is_delete = is_delete;
  }

  public String getIs_coupon() {
    return is_coupon;
  }

  public void setIs_coupon(String is_coupon) {
    this.is_coupon = is_coupon;
  }

  public int getIs_groupbuy_or_pick() {
    return is_groupbuy_or_pick;
  }

  public void setIs_groupbuy_or_pick(int is_groupbuy_or_pick) {
    this.is_groupbuy_or_pick = is_groupbuy_or_pick;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getIs_check_logistics() {
    return is_check_logistics;
  }

  public void setIs_check_logistics(int is_check_logistics) {
    this.is_check_logistics = is_check_logistics;
  }

  public int getIs_dp() {
    return is_dp;
  }

  public void setIs_dp(int is_dp) {
    this.is_dp = is_dp;
  }

  public int getIs_del() {
    return is_del;
  }

  public void setIs_del(int is_del) {
    this.is_del = is_del;
  }

  public String getStatus_name() {
    return status_name;
  }

  public void setStatus_name(String status_name) {
    this.status_name = status_name;
  }

  public String getApp_format_total_price() {
    return app_format_total_price;
  }

  public void setApp_format_total_price(String app_format_total_price) {
    this.app_format_total_price = app_format_total_price;
  }
}
