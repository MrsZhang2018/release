package com.fanwe.o2o.model;

import java.util.List;

/**
 * 活动券
 * Created by Administrator on 2017/3/5.
 */

public class ActivityCouponActModel extends BaseActModel {
  private List<ActivityCouponModel> item;
  private PageModel page;

  public List<ActivityCouponModel> getItem() {
    return item;
  }

  public void setItem(List<ActivityCouponModel> item) {
    this.item = item;
  }

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public static class ActivityCouponModel {
    private int id;
    private String confirm_time;
    private String event_id;
    private String is_read;
    private String event_begin_time;
    private String event_end_time;  //无限时
    private String sn;
    private String eName; //桥亭试吃
    private String img;
    private String sId;
    private String spName; //桥亭活鱼小镇
    private String status;
    private String info;  //已使用
    private String icon;
    private String qrcode;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getConfirm_time() {
      return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
      this.confirm_time = confirm_time;
    }

    public String getEvent_id() {
      return event_id;
    }

    public void setEvent_id(String event_id) {
      this.event_id = event_id;
    }

    public String getIs_read() {
      return is_read;
    }

    public void setIs_read(String is_read) {
      this.is_read = is_read;
    }

    public String getEvent_begin_time() {
      return event_begin_time;
    }

    public void setEvent_begin_time(String event_begin_time) {
      this.event_begin_time = event_begin_time;
    }

    public String getEvent_end_time() {
      return event_end_time;
    }

    public void setEvent_end_time(String event_end_time) {
      this.event_end_time = event_end_time;
    }

    public String getSn() {
      return sn;
    }

    public void setSn(String sn) {
      this.sn = sn;
    }

    public String geteName() {
      return eName;
    }

    public void seteName(String eName) {
      this.eName = eName;
    }

    public String getImg() {
      return img;
    }

    public void setImg(String img) {
      this.img = img;
    }

    public String getsId() {
      return sId;
    }

    public void setsId(String sId) {
      this.sId = sId;
    }

    public String getSpName() {
      return spName;
    }

    public void setSpName(String spName) {
      this.spName = spName;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getInfo() {
      return info;
    }

    public void setInfo(String info) {
      this.info = info;
    }

    public String getIcon() {
      return icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public String getQrcode() {
      return qrcode;
    }

    public void setQrcode(String qrcode) {
      this.qrcode = qrcode;
    }
  }
}
