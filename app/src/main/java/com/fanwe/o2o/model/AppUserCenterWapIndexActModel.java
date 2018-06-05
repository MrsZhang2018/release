package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/1/11.
 */

public class AppUserCenterWapIndexActModel extends BaseActModel
{
    private int user_login_status;
//    private String is_fx;//0未开通分销 1开通分销
    private String is_user_fx;//0未开通分销 1开通分销
    private String uid;
    private String user_name;
    private String not_read_msg;//未读消息数
    private String user_group;//会员等级
    private String user_money;//会员账户余额
    private int user_score;//会员积分
    private String user_avatar;//会员头像图路径
    private int user_mobile_empty;//0:已绑定手机 1未绑定手机
    private String new_ecv;//新增红包数
    private String new_youhui;//新发放给用户优惠券数
    private String new_coupon;//新发放给用户消费券数
    private String new_event;//新增活动券数
    private String coupon_name;//消费券那项的名称,根据后台来修改
    private String not_pay_order_count;//未付款的订单数
    private String wait_confirm;//待确认订单数
    private String wait_dp_count;//待点评的条数

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

//    public String getIs_fx() {
//        return is_fx;
//    }
//
//    public void setIs_fx(String is_fx) {
//        this.is_fx = is_fx;
//    }


    public String getIs_user_fx()
    {
        return is_user_fx;
    }

    public void setIs_user_fx(String is_user_fx)
    {
        this.is_user_fx = is_user_fx;
    }

    public String getNew_coupon() {
        return new_coupon;
    }

    public void setNew_coupon(String new_coupon) {
        this.new_coupon = new_coupon;
    }

    public String getNew_ecv() {
        return new_ecv;
    }

    public void setNew_ecv(String new_ecv) {
        this.new_ecv = new_ecv;
    }

    public String getNew_event() {
        return new_event;
    }

    public void setNew_event(String new_event) {
        this.new_event = new_event;
    }

    public String getNew_youhui() {
        return new_youhui;
    }

    public void setNew_youhui(String new_youhui) {
        this.new_youhui = new_youhui;
    }

    public String getNot_pay_order_count() {
        return not_pay_order_count;
    }

    public void setNot_pay_order_count(String not_pay_order_count) {
        this.not_pay_order_count = not_pay_order_count;
    }

    public String getNot_read_msg() {
        return not_read_msg;
    }

    public void setNot_read_msg(String not_read_msg) {
        this.not_read_msg = not_read_msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    public int getUser_mobile_empty() {
        return user_mobile_empty;
    }

    public void setUser_mobile_empty(int user_mobile_empty) {
        this.user_mobile_empty = user_mobile_empty;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_score() {
        return user_score;
    }

    public void setUser_score(int user_score) {
        this.user_score = user_score;
    }

    public String getWait_confirm() {
        return wait_confirm;
    }

    public void setWait_confirm(String wait_confirm) {
        this.wait_confirm = wait_confirm;
    }

    public String getWait_dp_count() {
        return wait_dp_count;
    }

    public void setWait_dp_count(String wait_dp_count) {
        this.wait_dp_count = wait_dp_count;
    }

    @Override
    public int getUser_login_status()
    {
        return user_login_status;
    }

    @Override
    public void setUser_login_status(int user_login_status)
    {
        this.user_login_status = user_login_status;
    }
}
