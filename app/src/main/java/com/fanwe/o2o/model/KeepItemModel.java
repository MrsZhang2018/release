package com.fanwe.o2o.model;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-08
 * Time: 16:05
 * 功能:
 */
public class KeepItemModel extends BaseActModel{
    private String url;
    private int is_select;
    private int keep_status;
    private int id;
    private String name;
    private String sub_name;
    private int buy_count;
    private String icon;
    private String img;
    private float current_price;
    private int out_time;
    private int user_count;
    private int score_limit;
    private int point_limit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getOut_time() {
        return out_time;
    }

    public void setOut_time(int out_time) {
        this.out_time = out_time;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }


    public int getScore_limit() {
        return score_limit;
    }

    public void setScore_limit(int score_limit) {
        this.score_limit = score_limit;
    }

    public int getUser_count() {
        return user_count;
    }

    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }

    public int getPoint_limit() {
        return point_limit;
    }

    public void setPoint_limit(int point_limit) {
        this.point_limit = point_limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKeep_status() {
        return keep_status;
    }

    public void setKeep_status(int keep_status) {
        this.keep_status = keep_status;
    }

    public int getIs_select() {
        return is_select;
    }

    public void setIs_select(int is_select) {
        this.is_select = is_select;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
