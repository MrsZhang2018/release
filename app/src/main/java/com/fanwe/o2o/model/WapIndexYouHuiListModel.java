package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WapIndexYouHuiListModel
{
    private int id;//优惠券ID
    private String name;//优惠券名称
    private String list_brief;//优惠券列表简介
    private String icon;//优惠券图片 140x85
    private int down_count;//下载量
    private String begin_time;//时间

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public int getDown_count() {
        return down_count;
    }

    public void setDown_count(int down_count) {
        this.down_count = down_count;
    }

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

    public String getList_brief() {
        return list_brief;
    }

    public void setList_brief(String list_brief) {
        this.list_brief = list_brief;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
