package com.fanwe.o2o.model;

import java.util.List;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-10
 * Time: 15:55
 * 功能:
 */
public class AppKeepListActModel extends BaseActModel {
    private List<KeepItemModel> item; //收藏列表
    private PageModel page;

    public List<KeepItemModel> getItem() {
        return item;
    }

    public void setItem(List<KeepItemModel> item) {
        this.item = item;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
