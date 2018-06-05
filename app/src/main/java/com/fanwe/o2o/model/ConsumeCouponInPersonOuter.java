package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ConsumeCouponInPersonOuter extends BaseActModel {
  private PageModel page;
  private List<ConsumeCouponInPersonMiddle> pick_item;

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public List<ConsumeCouponInPersonMiddle> getPick_item() {
    return pick_item;
  }

  public void setPick_item(List<ConsumeCouponInPersonMiddle> pick_item) {
    this.pick_item = pick_item;
  }
}

