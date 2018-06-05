package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class AppWapIndexDealListActModel extends BaseActModel
{
    private int page;
    private int has_next;
    private List<WapIndexDealListModel> deal_list;

    public List<WapIndexDealListModel> getDeal_list() {
        return deal_list;
    }

    public void setDeal_list(List<WapIndexDealListModel> deal_list) {
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
