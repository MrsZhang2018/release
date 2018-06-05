package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class AppGroupPurListIndexActModel extends BaseActModel {
    private int city_id; // 城市ID
    private int area_id; // 大区ID
    private int quan_id; // 商圈ID
    private int cate_id; // 大分类ID
    private String page_title; // 页面标题
    private PageModel page;
    private List<GroupPurListItemModel> item;
    private List<Bcate_listModel> bcate_list;
    private List<Quan_listModel> quan_list;
    private List<ShippingAddressModel> consignee_list; //收货地址列表

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public List<Bcate_listModel> getBcate_list() {
        return bcate_list;
    }

    public void setBcate_list(List<Bcate_listModel> bcate_list) {
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

    public List<GroupPurListItemModel> getItem() {
        return item;
    }

    public void setItem(List<GroupPurListItemModel> item) {
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

    public List<Quan_listModel> getQuan_list() {
        return quan_list;
    }

    public void setQuan_list(List<Quan_listModel> quan_list) {
        this.quan_list = quan_list;
    }

    public List<ShippingAddressModel> getConsignee_list() {
        return consignee_list;
    }

    public void setConsignee_list(List<ShippingAddressModel> consignee_list) {
        this.consignee_list = consignee_list;
    }
}
