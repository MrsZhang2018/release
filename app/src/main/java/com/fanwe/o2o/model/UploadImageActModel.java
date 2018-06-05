package com.fanwe.o2o.model;

/**
 * Created by heyucan on 2017/1/22.
 */

public class UploadImageActModel extends BaseActModel {

  /**
   * small_url : https://o2owap.fanwe.net/public/avatar/000/00/00/73virtual_avatar_small.jpg?0.018018018018018
   * middle_url : https://o2owap.fanwe.net/public/avatar/000/00/00/73virtual_avatar_middle.jpg?0.0043668122270742
   * big_url : https://o2owap.fanwe.net/public/avatar/000/00/00/73virtual_avatar_big.jpg?0.0024271844660194
   */

  private String small_url;
  private String middle_url;
  private String big_url;

  public String getSmall_url() {
    return small_url;
  }

  public void setSmall_url(String small_url) {
    this.small_url = small_url;
  }

  public String getMiddle_url() {
    return middle_url;
  }

  public void setMiddle_url(String middle_url) {
    this.middle_url = middle_url;
  }

  public String getBig_url() {
    return big_url;
  }

  public void setBig_url(String big_url) {
    this.big_url = big_url;
  }
}
