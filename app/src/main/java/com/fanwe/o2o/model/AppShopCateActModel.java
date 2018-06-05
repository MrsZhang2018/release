package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class AppShopCateActModel extends BaseActModel
{
    private List<ShopCateListModel> bcate_list;

    public List<ShopCateListModel> getBcate_list() {
        return bcate_list;
    }

    public void setBcate_list(List<ShopCateListModel> bcate_list) {
        this.bcate_list = bcate_list;
    }
}
