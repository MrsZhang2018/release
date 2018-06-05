package com.fanwe.o2o.model;

import com.fanwe.library.common.SDSelectManager;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class ShopCateListModel implements SDSelectManager.SDSelectable
{
    private String id;
    private String name;
    private String cate_img;
    private List<ShopCateTypeModel> bcate_type;

    private boolean isSelected;

    public List<ShopCateTypeModel> getBcate_type() {
        return bcate_type;
    }

    public void setBcate_type(List<ShopCateTypeModel> bcate_type) {
        this.bcate_type = bcate_type;
    }

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
