package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class OrderRefundDetailModel extends BaseActModel {
  private PageModel page;
  private List<ItemRefundDetailModel> item;
  private String city_name;
  private int mReturn;

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public List<ItemRefundDetailModel> getItem() {
    return item;
  }

  public void setItem(List<ItemRefundDetailModel> item) {
    this.item = item;
  }

  public String getCity_name() {
    return city_name;
  }

  public void setCity_name(String city_name) {
    this.city_name = city_name;
  }

  public int getmReturn() {
    return mReturn;
  }

  public void setmReturn(int mReturn) {
    this.mReturn = mReturn;
  }
}
