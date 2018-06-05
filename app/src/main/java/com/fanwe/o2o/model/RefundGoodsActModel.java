package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RefundGoodsActModel extends BaseActModel {
  private PageModel page;
  private List<String> deal_ids;
  private RefundGoodsItemModel item;

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public List<String> getDeal_ids() {
    return deal_ids;
  }

  public void setDeal_ids(List<String> deal_ids) {
    this.deal_ids = deal_ids;
  }

  public RefundGoodsItemModel getItem() {
    return item;
  }

  public void setItem(RefundGoodsItemModel item) {
    this.item = item;
  }
}
