package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WapIndexEventListModel
{
    private int id;//活动ID
    private String name;//动名称
    private String icon;//活动图片 300x182
    private String submit_begin_time_format;//格式化活动报名开始时间
    private String submit_end_time_format;//格式化活动报名结束时间
    private String sheng_time_format;//活动报名剩余时间

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSheng_time_format() {
        return sheng_time_format;
    }

    public void setSheng_time_format(String sheng_time_format) {
        this.sheng_time_format = sheng_time_format;
    }

    public String getSubmit_begin_time_format() {
        return submit_begin_time_format;
    }

    public void setSubmit_begin_time_format(String submit_begin_time_format) {
        this.submit_begin_time_format = submit_begin_time_format;
    }

    public String getSubmit_end_time_format() {
        return submit_end_time_format;
    }

    public void setSubmit_end_time_format(String submit_end_time_format) {
        this.submit_end_time_format = submit_end_time_format;
    }
}
