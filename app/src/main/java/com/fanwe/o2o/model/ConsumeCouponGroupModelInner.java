package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ConsumeCouponGroupModelInner extends ConsumeCouponModelInner {
  private String begin_time;
  private String refund_status;

  public String getBegin_time() {
    return begin_time;
  }

  public void setBegin_time(String begin_time) {
    this.begin_time = begin_time;
  }
}
