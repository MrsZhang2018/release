package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/8.
 */

public class UcOrderOrderDpItemModel extends BaseActModel {


  /**
   * id : 539
   * deal_id : 288
   * name : 仅售39元！价值69元的梦舒纷高领打底衫1件，2014年新款简约大方，高端定制面料 ，百搭款式，秋冬美女必备打底衫，成就自己的美丽，就从这开始.... [红,S]
   * sub_name : 梦舒纷高领打底衫 [红,S]
   * number : 1
   * deal_icon : https://o2owap.fanwe.net/public/images/no-image.png
   * is_coupon : 0
   * consume_count : 1
   * dp_id : 0
   * is_arrival : 1
   * delivery_status : 1
   * is_shop : 1
   */

  private String id;
  private String deal_id;
  private String name;
  private String sub_name;
  private String number;
  private String deal_icon;
  private String is_coupon;
  private String consume_count;
  private String dp_id;
  private String is_arrival;
  private String delivery_status;
  private String is_shop;
  private String content;
  private float rating;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
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

  public String getDeal_icon() {
    return deal_icon;
  }

  public void setDeal_icon(String deal_icon) {
    this.deal_icon = deal_icon;
  }

  public String getIs_coupon() {
    return is_coupon;
  }

  public void setIs_coupon(String is_coupon) {
    this.is_coupon = is_coupon;
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

  public String getIs_arrival() {
    return is_arrival;
  }

  public void setIs_arrival(String is_arrival) {
    this.is_arrival = is_arrival;
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
}
