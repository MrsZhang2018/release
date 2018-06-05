package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22.
 */

public class AppActivitesActModel extends BaseActModel
{
    private int city_id; // 城市ID
    private int area_id; // 大区ID
    private int quan_id; // 商圈ID
    private int cate_id; // 大分类ID
    private String page_title; // 页面标题
    private PageModel page;
    private List<EventModel> item;
    private List<TabModel> bcate_list;

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public List<TabModel> getBcate_list() {
        return bcate_list;
    }

    public void setBcate_list(List<TabModel> bcate_list) {
        this.bcate_list = bcate_list;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public List<EventModel> getItem() {
        return item;
    }

    public void setItem(List<EventModel> item) {
        this.item = item;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    @Override
    public String getPage_title() {
        return page_title;
    }

    @Override
    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public int getQuan_id() {
        return quan_id;
    }

    public void setQuan_id(int quan_id) {
        this.quan_id = quan_id;
    }
}
