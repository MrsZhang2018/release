package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/15.
 */

public class ShopIndexIndexsListModel
{
    private String id;//菜单ID
    private String name;//菜单名称
    private String icon_name;//菜单图标
    private String app_icon_img;//菜单图标
    private String color;//菜单颜色
    private String bg_color;
    private AdvsDataModel data;
    private String ctl;
    private String url;
    private String img;
    private int type;

    public String getCtl()
    {
        return ctl;
    }

    public void setCtl(String ctl)
    {
        this.ctl = ctl;
    }

    public AdvsDataModel getData()
    {
        return data;
    }

    public void setData(AdvsDataModel data)
    {
        this.data = data;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getApp_icon_img() {
        return app_icon_img;
    }

    public void setApp_icon_img(String app_icon_img) {
        this.app_icon_img = app_icon_img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
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
}
