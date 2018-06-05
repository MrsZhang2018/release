package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WapIndexAdvs2Model
{
    private int id;//广告的ID
    private String name;//广告名称
    private String img;//广告图片
    private int type;//
    private WapIndexAdvsDataModel  data;//以key->value方式存储的内容 用于url参数组装
    private String ctl;//定义的ctl

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WapIndexAdvsDataModel getData() {
        return data;
    }

    public void setData(WapIndexAdvsDataModel data) {
        this.data = data;
    }

    public String getCtl() {
        return ctl;
    }

    public void setCtl(String ctl) {
        this.ctl = ctl;
    }
}
