package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class AppGoodsWapIndexActModel extends BaseActModel
{
    private String cate_id;
    private String bid;
    private String cate_name;
    private PageModel page;
    private List<GoodsModel> item;
    private List<Bcate_listModel> bcate_list;
    private List<Brand_listModel> brand_list;

    public List<Bcate_listModel> getBcate_list() {
        return bcate_list;
    }

    public void setBcate_list(List<Bcate_listModel> bcate_list) {
        this.bcate_list = bcate_list;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public List<Brand_listModel> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<Brand_listModel> brand_list) {
        this.brand_list = brand_list;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public List<GoodsModel> getItem() {
        return item;
    }

    public void setItem(List<GoodsModel> item) {
        this.item = item;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
