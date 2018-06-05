package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class AppShopIndexDealListActModel extends BaseActModel
{
    private int page;
    private int has_next;
    private List<ShopIndexSupplierDealListModel> deal_list;

    public List<ShopIndexSupplierDealListModel> getDeal_list() {
        return deal_list;
    }

    public void setDeal_list(List<ShopIndexSupplierDealListModel> deal_list) {
        this.deal_list = deal_list;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
