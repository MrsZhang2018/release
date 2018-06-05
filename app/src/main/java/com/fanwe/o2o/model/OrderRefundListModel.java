package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OrderRefundListModel extends BaseActModel {
  private PageModel page;
  private List<ItemOrderRefundModel> item;
  private String city_name;
  private int mReturn;

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public List<ItemOrderRefundModel> getItem() {
    return item;
  }

  public void setItem(
      List<ItemOrderRefundModel> itemEntities) {
    this.item = itemEntities;
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
