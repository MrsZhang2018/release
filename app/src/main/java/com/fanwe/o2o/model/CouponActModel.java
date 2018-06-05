package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/4.
 */

public class CouponActModel extends BaseActModel {
  private List<CouponModel> item;
  private PageModel page;

  public List<CouponModel> getItem() {
    return item;
  }

  public void setItem(List<CouponModel> item) {
    this.item = item;
  }

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public static class CouponModel {
    private int id;
    private String youhui_id;
    private String youhui_sn; //券码
    private String total_fee;
    private String confirm_time;
    private String expire_time;
    private String create_time;
    private String yName;
    private String youhui_type; //减免0/折扣1
    private String youhui_value; //优惠值
    private String icon;
    private String end_time;
    private String is_read;
    private String status; //0已过期 1可用 2已使用
    private String type;
    private String value;
    private String qrcode;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getYouhui_id() {
      return youhui_id;
    }

    public void setYouhui_id(String youhui_id) {
      this.youhui_id = youhui_id;
    }

    public String getYouhui_sn() {
      return youhui_sn;
    }

    public void setYouhui_sn(String youhui_sn) {
      this.youhui_sn = youhui_sn;
    }

    public String getTotal_fee() {
      return total_fee;
    }

    public void setTotal_fee(String total_fee) {
      this.total_fee = total_fee;
    }

    public String getConfirm_time() {
      return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
      this.confirm_time = confirm_time;
    }

    public String getExpire_time() {
      return expire_time;
    }

    public void setExpire_time(String expire_time) {
      this.expire_time = expire_time;
    }

    public String getCreate_time() {
      return create_time;
    }

    public void setCreate_time(String create_time) {
      this.create_time = create_time;
    }

    public String getyName() {
      return yName;
    }

    public void setyName(String yName) {
      this.yName = yName;
    }

    public String getYouhui_type() {
      return youhui_type;
    }

    public void setYouhui_type(String youhui_type) {
      this.youhui_type = youhui_type;
    }

    public String getYouhui_value() {
      return youhui_value;
    }

    public void setYouhui_value(String youhui_value) {
      this.youhui_value = youhui_value;
    }

    public String getIcon() {
      return icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public String getEnd_time() {
      return end_time;
    }

    public void setEnd_time(String end_time) {
      this.end_time = end_time;
    }

    public String getIs_read() {
      return is_read;
    }

    public void setIs_read(String is_read) {
      this.is_read = is_read;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getQrcode() {
      return qrcode;
    }

    public void setQrcode(String qrcode) {
      this.qrcode = qrcode;
    }
  }
}
