package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/9.
 */

public class ParamModel{

  /**
   * id : 392
   * order_id : 425
   * coupon_status : 1
   */

  private String id;
  private String order_id;
  private int coupon_status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public int getCoupon_status() {
    return coupon_status;
  }

  public void setCoupon_status(int coupon_status) {
    this.coupon_status = coupon_status;
  }
}
