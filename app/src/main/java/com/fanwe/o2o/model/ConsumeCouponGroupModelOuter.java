package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ConsumeCouponGroupModelOuter extends BaseActModel {
  private PageModel page;

  private List<ConsumeCouponGroupModelMiddle> tuan_item;

  public List<ConsumeCouponGroupModelMiddle> getTuan_item() {
    return tuan_item;
  }

  public void setTuan_item(List<ConsumeCouponGroupModelMiddle> tuan_item) {
    this.tuan_item = tuan_item;
  }

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

}
