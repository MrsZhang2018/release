package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/3.
 */

public class UcOrderWapViewActModel extends BaseActModel {


  /**
   * item : {"id":"392","order_sn":"2017022804540815","is_cancel":"0","order_status":"0","pay_status":"2","delivery_status":"5","delivery_id":"0","memo":"","location_id":"0","create_time":"2017-02-28 16:54:08","pay_amount":563.2,"total_price":563.2,"deal_total_price":704,"discount_price":140.8,"delivery_fee":0,"record_delivery_fee":0,"ecv_money":0,"promote_arr":null,"payment_fee":0,"buy_type":0,"is_delete":"0","fact_return_total_score":"0","is_coupon":"1","is_groupbuy_or_pick":1,"is_refund":1,"deal_order_item":{"54":{"supplier_name":"明视眼镜","count":1,"list":[{"id":"630","deal_id":"299","deal_icon":"https://o2owap.fanwe.net/public/attachment/201609/20/15/57e0e2f54140e_244x148.jpg","name":"明视眼镜1","sub_name":"明视眼镜1","number":"8","unit_price":88,"total_price":704,"buy_type":"0","consume_count":0,"dp_id":0,"delivery_status":5,"is_arrival":0,"deal_orders":"待使用","is_refund":1,"refund_status":1,"supplier_id":54,"attr_str":""}]}},"c":8,"is_check_logistics":0,"is_dp":0,"is_del":0,"status":"待确认","youhui_price":140.8,"is_delivery":null,"order_total_price":704,"order_pay_price":563.2,"feeinfo":[{"name":"商品金额","symbol":1,"value":704,"buy_type":0}],"paid":[{"name":"会员折扣","symbol":-1,"value":140.8,"buy_type":0}],"operation":[{"name":"查看消费券","icon":"j-coupon","url":"/wap/index.php?ctl=uc_coupon&act=pay&order_id=392"},{"name":"退款","icon":"j-refund","url":"/wap/index.php?ctl=uc_order&act=order_refund&data_id=392"}]}
   * not_pay : 1
   * buy_type : 0
   * city_name : 福州
   * return : 1
   */

  private String not_pay;
  private int buy_type;
  private String city_name;
  private int returnX;
  private UcOrderWapViewItemActModel item;

  public UcOrderWapViewItemActModel getItem() {
    return item;
  }

  public void setItem(UcOrderWapViewItemActModel item) {
    this.item = item;
  }

  public String getNot_pay() {
    return not_pay;
  }

  public void setNot_pay(String not_pay) {
    this.not_pay = not_pay;
  }

  public int getBuy_type() {
    return buy_type;
  }

  public void setBuy_type(int buy_type) {
    this.buy_type = buy_type;
  }

  public String getCity_name() {
    return city_name;
  }

  public void setCity_name(String city_name) {
    this.city_name = city_name;
  }

  public int getReturnX() {
    return returnX;
  }

  public void setReturnX(int returnX) {
    this.returnX = returnX;
  }
}
