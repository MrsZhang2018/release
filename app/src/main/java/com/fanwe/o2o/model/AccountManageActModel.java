package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by heyucan on 2017/1/16.
 */

public class AccountManageActModel extends BaseActModel {
  private AccountManageUserInfoModel user_info;
  private List<AccountManageGroupInfoModel> group_info;
  private List<AccountManageLevelInfoModel> level_info;
  private String ghighest; //1:会员等级最高级， 0：非最高级
  private String phighest;//1:经验最高级， 0：非最高级
  private Currdis currdis;

  public static class Currdis{  //显示在对话框里的折扣信息
    private String id;
    private String name;
    private String score;
    private String discount;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getDiscount() {
      return discount;
    }

    public void setDiscount(String discount) {
      this.discount = discount;
    }
  }

  public AccountManageUserInfoModel getUser_info() {
    return user_info;
  }

  public void setUser_info(AccountManageUserInfoModel user_info) {
    this.user_info = user_info;
  }

  public List<AccountManageGroupInfoModel> getGroup_info() {
    return group_info;
  }

  public void setGroup_info(List<AccountManageGroupInfoModel> group_info) {
    this.group_info = group_info;
  }

  public List<AccountManageLevelInfoModel> getLevel_info() {
    return level_info;
  }

  public void setLevel_info(List<AccountManageLevelInfoModel> level_info) {
    this.level_info = level_info;
  }

  public String getGhighest() {
    return ghighest;
  }

  public void setGhighest(String ghighest) {
    this.ghighest = ghighest;
  }

  public Currdis getCurrdis() {
    return currdis;
  }

  public void setCurrdis(Currdis currdis) {
    this.currdis = currdis;
  }

  public String getPhighest() {
    return phighest;
  }

  public void setPhighest(String phighest) {
    this.phighest = phighest;
  }
}
