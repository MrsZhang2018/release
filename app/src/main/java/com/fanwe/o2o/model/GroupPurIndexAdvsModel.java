package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/15.
 */

public class GroupPurIndexAdvsModel
{
    private String id;//广告的ID
    private String name;//广告名称
    private String img;//广告图片
    private String type;//
    private GroupPurIndexAdvsDataModel  data;//以key->value方式存储的内容 用于url参数组装
    private String ctl;//定义的ctl

    public String getCtl() {
        return ctl;
    }

    public void setCtl(String ctl) {
        this.ctl = ctl;
    }

    public GroupPurIndexAdvsDataModel getData() {
        return data;
    }

    public void setData(GroupPurIndexAdvsDataModel data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
