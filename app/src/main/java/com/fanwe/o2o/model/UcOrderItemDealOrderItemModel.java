package com.fanwe.o2o.model;


import java.util.List;

/**
 * Created by luod on 2017/3/2.
 */

public class UcOrderItemDealOrderItemModel{


  /**
   * supplier_name : 明视眼镜
   * count : 1
   * list : [{"id":"630","deal_id":"299","deal_icon":"https://o2owap.fanwe.net/public/attachment/201609/20/15/57e0e2f54140e_244x148.jpg","name":"明视眼镜1","sub_name":"明视眼镜1","number":"8","unit_price":88,"total_price":704,"buy_type":"0","consume_count":0,"dp_id":0,"delivery_status":5,"is_arrival":0,"deal_orders":"待使用","is_refund":1,"refund_status":1,"supplier_id":54,"attr_str":"","app_format_unit_price":"88.00"}]
   */

  private String supplier_name;
  private int count;
  private String status_name;
  private List<UcOrderItemDealOrderItemListModel> list;

  public List<UcOrderItemDealOrderItemListModel> getList() {
    return list;
  }

  public void setList(List<UcOrderItemDealOrderItemListModel> list) {
    this.list = list;
  }

  public String getSupplier_name() {
    return supplier_name;
  }

  public void setSupplier_name(String supplier_name) {
    this.supplier_name = supplier_name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getStatus_name() {
    return status_name;
  }

  public void setStatus_name(String status_name) {
    this.status_name = status_name;
  }
}
