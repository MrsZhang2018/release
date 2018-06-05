package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/13.
 */

public class AppTypeModel{


  /**
   * name : 订单详情
   * type : 302
   * fname :
   * field :
   */

  private String name;
  private int type;
  private String fname;
  private String field;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
