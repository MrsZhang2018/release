package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BuyOrderActModel extends BaseActModel {
  private List<BuyOrderModel> item;
  private PageModel page;

  public List<BuyOrderModel> getItem() {
    return item;
  }

  public void setItem(List<BuyOrderModel> item) {
    this.item = item;
  }

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public static class BuyOrderModel {
    private int id;
    private String order_sn;
    private String total_price; //消费金额
    private String order_status;
    private String pay_status;
    private String pay_amount; //实付金额
    private String create_time;//买单时间
    private String discount_price; //优惠金额
    private String other_money;
    private String location_name;//商户名称
    private String status;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getOrder_sn() {
      return order_sn;
    }

    public void setOrder_sn(String order_sn) {
      this.order_sn = order_sn;
    }

    public String getTotal_price() {
      return total_price;
    }

    public void setTotal_price(String total_price) {
      this.total_price = total_price;
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

    public String getPay_amount() {
      return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
      this.pay_amount = pay_amount;
    }

    public String getCreate_time() {
      return create_time;
    }

    public void setCreate_time(String create_time) {
      this.create_time = create_time;
    }

    public String getDiscount_price() {
      return discount_price;
    }

    public void setDiscount_price(String discount_price) {
      this.discount_price = discount_price;
    }

    public String getOther_money() {
      return other_money;
    }

    public void setOther_money(String other_money) {
      this.other_money = other_money;
    }

    public String getLocation_name() {
      return location_name;
    }

    public void setLocation_name(String location_name) {
      this.location_name = location_name;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }
}

