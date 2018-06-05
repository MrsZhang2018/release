package com.fanwe.o2o.model;


/**
 * Created by luod on 2017/3/3.
 */

public class FeeinfoModel extends BaseActModel {

  /**
   * name : 商品金额
   * symbol : 1
   * value : 704
   * buy_type : 0
   */

  private String name;
  private int symbol;
  private String value;
  private int buy_type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSymbol() {
    return symbol;
  }

  public void setSymbol(int symbol) {
    this.symbol = symbol;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getBuy_type() {
    return buy_type;
  }

  public void setBuy_type(int buy_type) {
    this.buy_type = buy_type;
  }
}
