package com.fanwe.o2o.model;


import java.util.List;

/**
 * Created by luod on 2017/3/3.
 */

public class UcOrderWapViewItemActModel extends BaseActModel {


  /**
   * id : 392
   * order_sn : 2017022804540815
   * is_cancel : 0
   * order_status : 0
   * pay_status : 2
   * delivery_status : 5
   * delivery_id : 0
   * memo :
   * location_id : 0
   * create_time : 2017-02-28 16:54:08
   * pay_amount : 563.2
   * app_format_pay_amount : 563.20
   * total_price : 563.2
   * app_format_total_price : 563.20
   * deal_total_price : 704
   * discount_price : 140.8
   * delivery_fee : 0
   * record_delivery_fee : 0
   * ecv_money : 0
   * promote_arr : null
   * payment_fee : 0
   * buy_type : 0
   * is_delete : 0
   * fact_return_total_score : 0
   * is_coupon : 1
   * is_groupbuy_or_pick : 1
   * is_refund : 1
   * deal_order_item : [{"supplier_name":"明视眼镜","count":1,"list":[{"id":"630","deal_id":"299","deal_icon":"https://o2owap.fanwe.net/public/attachment/201609/20/15/57e0e2f54140e_244x148.jpg","name":"明视眼镜1","sub_name":"明视眼镜1","number":"8","unit_price":88,"app_format_unit_price":"88.00","total_price":704,"buy_type":"0","consume_count":0,"dp_id":0,"delivery_status":5,"is_arrival":0,"deal_orders":"待使用","is_refund":1,"refund_status":1,"supplier_id":54,"attr_str":""}]}]
   * c : 8
   * is_check_logistics : 0
   * is_dp : 0
   * is_del : 0
   * status_name : 待确认
   * youhui_price : 140.8
   * is_delivery : null
   * order_total_price : 704
   * order_pay_price : 563.2
   * feeinfo : [{"name":"商品金额","symbol":1,"value":704,"buy_type":0}]
   * paid : [{"name":"会员折扣","symbol":-1,"value":140.8,"buy_type":0}]
   * operation : [{"name":"查看消费券","icon":"j-coupon","url":"/wap/index.php?ctl=uc_coupon&act=pay&order_id=392"},{"name":"退款","icon":"j-refund","url":"/wap/index.php?ctl=uc_order&act=order_refund&data_id=392"}]
   */

  private String id;
  /** 订单类型(0:商品订单 1:用户充值单,2:积分兑换订单，3:平台自营物流配送订单，4:平台自营驿站配送订单,5:商家团购订单,6:商家商品订单,7:积分充值订单)',
   * 346返回商城单   5  返回团购单
   */
  private String type;
  private String order_sn;
  private String is_cancel;
  private String order_status;
  private String pay_status;
  private String delivery_status;
  private String delivery_id;
  private String memo;
  private String location_id;
  private String location_name;
  private String tel;
  private String location_address;
  private String create_time;
  private double pay_amount;
  private String app_format_pay_amount;
  private double total_price;
  private String app_format_total_price;
  private int deal_total_price;
  private double discount_price;
  private int delivery_fee;
  private int record_delivery_fee;
  private int ecv_money;
  private Object promote_arr;
  private int payment_fee;
  private int buy_type;
  private String is_delete;
  private String fact_return_total_score;
  private String is_coupon;
  private int is_groupbuy_or_pick;
  private int is_refund;
  private int count;
  private int is_check_logistics;
  private int is_dp;
  private int is_del;
  private String status_name;
  private double youhui_price;
  private Object is_delivery;
  private int order_total_price;
  private double order_pay_price;
  private String app_format_youhui_price;
  private String app_format_order_total_price;
  private String app_format_order_pay_price;
  private String address;
  private String mobile;
  private String consignee;
  private int is_pay;
  private int existence_expire_refund;
  private List<UcOrderItemDealOrderItemModel> deal_order_item;
  private List<OperationModel> operation;
  private List<FeeinfoModel> feeinfo;
  private List<PaidModel> paid;
  private DeliveryInfoModel delivery_info;
  private paymentInfoModel payment_info;
  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getLocation_name()
  {
    return location_name;
  }

  public void setLocation_name(String location_name)
  {
    this.location_name = location_name;
  }

  public String getTel()
  {
    return tel;
  }

  public void setTel(String tel)
  {
    this.tel = tel;
  }

  public String getLocation_address()
  {
    return location_address;
  }

  public void setLocation_address(String location_address)
  {
    this.location_address = location_address;
  }

  public int getExistence_expire_refund() {
    return existence_expire_refund;
  }

  public void setExistence_expire_refund(int existence_expire_refund) {
    this.existence_expire_refund = existence_expire_refund;
  }

  public DeliveryInfoModel getDelivery_info() {
    return delivery_info;
  }

  public void setDelivery_info(DeliveryInfoModel delivery_info) {
    this.delivery_info = delivery_info;
  }

  public int getIs_pay() {
    return is_pay;
  }

  public void setIs_pay(int is_pay) {
    this.is_pay = is_pay;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getConsignee() {
    return consignee;
  }

  public void setConsignee(String consignee) {
    this.consignee = consignee;
  }

  public String getApp_format_order_total_price() {
    return app_format_order_total_price;
  }

  public void setApp_format_order_total_price(String app_format_order_total_price) {
    this.app_format_order_total_price = app_format_order_total_price;
  }

  public String getApp_format_order_pay_price() {
    return app_format_order_pay_price;
  }

  public void setApp_format_order_pay_price(String app_format_order_pay_price) {
    this.app_format_order_pay_price = app_format_order_pay_price;
  }

  public String getApp_format_youhui_price() {
    return app_format_youhui_price;
  }

  public void setApp_format_youhui_price(String app_format_youhui_price) {
    this.app_format_youhui_price = app_format_youhui_price;
  }

  public List<UcOrderItemDealOrderItemModel> getDeal_order_item() {
    return deal_order_item;
  }

  public void setDeal_order_item(List<UcOrderItemDealOrderItemModel> deal_order_item) {
    this.deal_order_item = deal_order_item;
  }

  public List<OperationModel> getOperation() {
    return operation;
  }

  public void setOperation(List<OperationModel> operation) {
    this.operation = operation;
  }

  public List<FeeinfoModel> getFeeinfo() {
    return feeinfo;
  }

  public void setFeeinfo(List<FeeinfoModel> feeinfo) {
    this.feeinfo = feeinfo;
  }

  public List<PaidModel> getPaid() {
    return paid;
  }

  public void setPaid(List<PaidModel> paid) {
    this.paid = paid;
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

  public String getIs_cancel() {
    return is_cancel;
  }

  public void setIs_cancel(String is_cancel) {
    this.is_cancel = is_cancel;
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

  public String getDelivery_id() {
    return delivery_id;
  }

  public void setDelivery_id(String delivery_id) {
    this.delivery_id = delivery_id;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getLocation_id() {
    return location_id;
  }

  public void setLocation_id(String location_id) {
    this.location_id = location_id;
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

  public String getApp_format_pay_amount() {
    return app_format_pay_amount;
  }

  public void setApp_format_pay_amount(String app_format_pay_amount) {
    this.app_format_pay_amount = app_format_pay_amount;
  }

  public double getTotal_price() {
    return total_price;
  }

  public void setTotal_price(double total_price) {
    this.total_price = total_price;
  }

  public String getApp_format_total_price() {
    return app_format_total_price;
  }

  public void setApp_format_total_price(String app_format_total_price) {
    this.app_format_total_price = app_format_total_price;
  }

  public int getDeal_total_price() {
    return deal_total_price;
  }

  public void setDeal_total_price(int deal_total_price) {
    this.deal_total_price = deal_total_price;
  }

  public double getDiscount_price() {
    return discount_price;
  }

  public void setDiscount_price(double discount_price) {
    this.discount_price = discount_price;
  }

  public int getDelivery_fee() {
    return delivery_fee;
  }

  public void setDelivery_fee(int delivery_fee) {
    this.delivery_fee = delivery_fee;
  }

  public int getRecord_delivery_fee() {
    return record_delivery_fee;
  }

  public void setRecord_delivery_fee(int record_delivery_fee) {
    this.record_delivery_fee = record_delivery_fee;
  }

  public int getEcv_money() {
    return ecv_money;
  }

  public void setEcv_money(int ecv_money) {
    this.ecv_money = ecv_money;
  }

  public Object getPromote_arr() {
    return promote_arr;
  }

  public void setPromote_arr(Object promote_arr) {
    this.promote_arr = promote_arr;
  }

  public int getPayment_fee() {
    return payment_fee;
  }

  public void setPayment_fee(int payment_fee) {
    this.payment_fee = payment_fee;
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

  public String getFact_return_total_score() {
    return fact_return_total_score;
  }

  public void setFact_return_total_score(String fact_return_total_score) {
    this.fact_return_total_score = fact_return_total_score;
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

  public int getIs_refund() {
    return is_refund;
  }

  public void setIs_refund(int is_refund) {
    this.is_refund = is_refund;
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

  public double getYouhui_price() {
    return youhui_price;
  }

  public void setYouhui_price(double youhui_price) {
    this.youhui_price = youhui_price;
  }

  public Object getIs_delivery() {
    return is_delivery;
  }

  public void setIs_delivery(Object is_delivery) {
    this.is_delivery = is_delivery;
  }

  public int getOrder_total_price() {
    return order_total_price;
  }

  public void setOrder_total_price(int order_total_price) {
    this.order_total_price = order_total_price;
  }

  public double getOrder_pay_price() {
    return order_pay_price;
  }

  public void setOrder_pay_price(double order_pay_price) {
    this.order_pay_price = order_pay_price;
  }

  public paymentInfoModel getPayment_info()
  {
    return payment_info;
  }

  public void setPayment_info(paymentInfoModel payment_info)
  {
    this.payment_info = payment_info;
  }

  public static class paymentInfoModel {
    private String id;
    private String money;
    private String payment_config;
    private String class_name;
    private String name;

    public String getId()
    {
      return id;
    }

    public void setId(String id)
    {
      this.id = id;
    }

    public String getMoney()
    {
      return money;
    }

    public void setMoney(String money)
    {
      this.money = money;
    }

    public String getPayment_config()
    {
      return payment_config;
    }

    public void setPayment_config(String payment_config)
    {
      this.payment_config = payment_config;
    }

    public String getClass_name()
    {
      return class_name;
    }

    public void setClass_name(String class_name)
    {
      this.class_name = class_name;
    }

    public String getName()
    {
      return name;
    }

    public void setName(String name)
    {
      this.name = name;
    }
  }

}
