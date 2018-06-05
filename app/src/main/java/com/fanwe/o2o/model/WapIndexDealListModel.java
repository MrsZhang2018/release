package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WapIndexDealListModel
{
    private String id;//团购ID
    private String name;//团购名称
    private String sub_name;//团购短名称
    private String brief;//团购简介
    private String buy_count;//销量
    private float current_price;//现价
    private float origin_price;//原价
    private String icon;//团购图片 140x85
    private String icon_v1;
    private String f_icon;
    private String f_icon_v1;
    private String end_time_format;//格式化的结束时间
    private String begin_time_format;//格式化的开始时间
    private String begin_time;//开始时间戳
    private String end_time;//结束时间戳
    private String auto_order;//免预约 0:否 1:是
    private String is_lottery;//是否抽奖 0:否 1:是
    private String is_refund;
    private int deal_score;
    private int buyin_app;
    private int allow_promote;
    private int location_id;
    private String location_name;
    private String location_address;
    private String location_avg_point;
    private String location_dp_count;
    private String location_dp_xpoint;
    private String location_dp_ypoint;
    private String is_verify;
    private String open_store_payment;
    private String area_name;
    private int is_today;//是否为今日团购 0否 1是
    private String url;
    private String share_url;
    private String app_url;

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAllow_promote() {
        return allow_promote;
    }

    public void setAllow_promote(int allow_promote) {
        this.allow_promote = allow_promote;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAuto_order() {
        return auto_order;
    }

    public void setAuto_order(String auto_order) {
        this.auto_order = auto_order;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getBegin_time_format() {
        return begin_time_format;
    }

    public void setBegin_time_format(String begin_time_format) {
        this.begin_time_format = begin_time_format;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(String buy_count) {
        this.buy_count = buy_count;
    }

    public int getBuyin_app() {
        return buyin_app;
    }

    public void setBuyin_app(int buyin_app) {
        this.buyin_app = buyin_app;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }

    public int getDeal_score() {
        return deal_score;
    }

    public void setDeal_score(int deal_score) {
        this.deal_score = deal_score;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEnd_time_format() {
        return end_time_format;
    }

    public void setEnd_time_format(String end_time_format) {
        this.end_time_format = end_time_format;
    }

    public String getF_icon() {
        return f_icon;
    }

    public void setF_icon(String f_icon) {
        this.f_icon = f_icon;
    }

    public String getF_icon_v1() {
        return f_icon_v1;
    }

    public void setF_icon_v1(String f_icon_v1) {
        this.f_icon_v1 = f_icon_v1;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_v1() {
        return icon_v1;
    }

    public void setIcon_v1(String icon_v1) {
        this.icon_v1 = icon_v1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_lottery() {
        return is_lottery;
    }

    public void setIs_lottery(String is_lottery) {
        this.is_lottery = is_lottery;
    }

    public String getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(String is_refund) {
        this.is_refund = is_refund;
    }

    public int getIs_today() {
        return is_today;
    }

    public void setIs_today(int is_today) {
        this.is_today = is_today;
    }

    public String getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLocation_avg_point() {
        return location_avg_point;
    }

    public void setLocation_avg_point(String location_avg_point) {
        this.location_avg_point = location_avg_point;
    }

    public String getLocation_dp_count() {
        return location_dp_count;
    }

    public void setLocation_dp_count(String location_dp_count) {
        this.location_dp_count = location_dp_count;
    }

    public String getLocation_dp_xpoint() {
        return location_dp_xpoint;
    }

    public void setLocation_dp_xpoint(String location_dp_xpoint) {
        this.location_dp_xpoint = location_dp_xpoint;
    }

    public String getLocation_dp_ypoint() {
        return location_dp_ypoint;
    }

    public void setLocation_dp_ypoint(String location_dp_ypoint) {
        this.location_dp_ypoint = location_dp_ypoint;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen_store_payment() {
        return open_store_payment;
    }

    public void setOpen_store_payment(String open_store_payment) {
        this.open_store_payment = open_store_payment;
    }

    public float getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(float origin_price) {
        this.origin_price = origin_price;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
}
