package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by heyucan on 2017/3/10.
 */

public class RefundRequestActModel extends BaseActModel {

  /**
   * item : [{"supplier_name":"平台自营","list":[{"name":"商户004","deal_icon":"./public/attachment/201609/24/10/57e5de5656589.jpg","attr_str":"","unit_price":{"bai":150,"fei":"00"},"number":"1"}]}]
   * placeholder : 请输入退款理由
   * action : /wap/index.php?ctl=uc_order&act=do_refund
   * city_name : 福州
   * return : 1
   */

  private String placeholder;
  private String action;
  private String city_name;
  private int returnX;

  private List<UcOrderItemDealOrderItemModel> item;

  public List<UcOrderItemDealOrderItemModel> getItem() {
    return item;
  }

  public void setItem(List<UcOrderItemDealOrderItemModel> item) {
    this.item = item;
  }

  public String getPlaceholder() {
    return placeholder;
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
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
