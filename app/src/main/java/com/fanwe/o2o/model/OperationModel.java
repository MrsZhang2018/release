package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/2.
 */

public class OperationModel extends BaseActModel {

  /**
   * name : 查看消费券
   * type : j-coupon
   * url : /wap/index.php?ctl=uc_coupon&order_id=392
   */

  private String name;
  private String type;
  private String url;
  private ParamModel param;

  public ParamModel getParam() {
    return param;
  }

  public void setParam(ParamModel param) {
    this.param = param;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
