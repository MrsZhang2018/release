package com.fanwe.o2o.model;


import java.util.List;

/**
 * Created by luod on 2017/3/8.
 */

public class UcOrderOrderDpActModel extends BaseActModel {


  /**
   * order_id : 330
   * item : [{"id":"539","deal_id":"288","name":"仅售39元！价值69元的梦舒纷高领打底衫1件，2014年新款简约大方，高端定制面料 ，百搭款式，秋冬美女必备打底衫，成就自己的美丽，就从这开始.... [红,S]","sub_name":"梦舒纷高领打底衫 [红,S]","number":"1","deal_icon":"https://o2owap.fanwe.net/public/images/no-image.png","is_coupon":"0","consume_count":"1","dp_id":"0","is_arrival":"1","delivery_status":"1","is_shop":"1"},{"id":"540","deal_id":"287","name":"仅售39元！价值99元的魅货莫代尔不规则衫1件，魅货莫代尔不规则开衫","sub_name":"魅货莫代尔不规则衫","number":"1","deal_icon":"https://o2owap.fanwe.net/public/images/no-image.png","is_coupon":"0","consume_count":"1","dp_id":"0","is_arrival":"1","delivery_status":"1","is_shop":"1"},{"id":"541","deal_id":"285","name":"榭都中长款毛呢大衣 格子茧型冬季外套 秋冬韩版呢子呢大衣品牌女 [红色,XL]","sub_name":"榭都中长款毛呢大衣 [红色,XL]","number":"1","deal_icon":"https://o2owap.fanwe.net/public/images/no-image.png","is_coupon":"0","consume_count":"1","dp_id":"0","is_arrival":"1","delivery_status":"1","is_shop":"1"}]
   * city_name : 福州
   * return : 1
   */

  private String order_id;
  private String city_name;
  private int returnX;
  private List<UcOrderOrderDpItemModel> item;

  public List<UcOrderOrderDpItemModel> getItem() {
    return item;
  }

  public void setItem(List<UcOrderOrderDpItemModel> item) {
    this.item = item;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
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
