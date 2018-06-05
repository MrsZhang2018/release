package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ConsumeCouponDistOuter extends BaseActModel {
  private PageModel page;
  private String coupon_status;
  private String is_open_distribution;
  private List<ConsumeCouponDistMiddle> dist_item;
  private List<Object> tuan_item;
  private List<Object> pick_item;

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public String getCoupon_status() {
    return coupon_status;
  }

  public void setCoupon_status(String coupon_status) {
    this.coupon_status = coupon_status;
  }

  public String getIs_open_distribution() {
    return is_open_distribution;
  }

  public void setIs_open_distribution(String is_open_distribution) {
    this.is_open_distribution = is_open_distribution;
  }

  public List<ConsumeCouponDistMiddle> getDist_item() {
    return dist_item;
  }

  public void setDist_item(List<ConsumeCouponDistMiddle> dist_item) {
    this.dist_item = dist_item;
  }

  public List<Object> getTuan_item() {
    return tuan_item;
  }

  public void setTuan_item(List<Object> tuan_item) {
    this.tuan_item = tuan_item;
  }

  public List<Object> getPick_item() {
    return pick_item;
  }

  public void setPick_item(List<Object> pick_item) {
    this.pick_item = pick_item;
  }
}
