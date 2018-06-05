package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WapIndexSupplierListModel
{
    private String preview;//商家图片 92x82
    private String preview_v1;//商家图片 180x130
    private String preview_v2;//商家编号
    private String id;//商家编号
    private String is_verify;//是否认证
    private String avg_point;//平均分
    private String address;//地址
    private String name;//名称
    private String distance;//距离
    private float xpoint;//门店所在经度
    private float ypoint;//门店所在纬度
    private String tel;//电话
    private String dealcate_name;//分类名称
    private String area_name;//地区名称
    private String app_url;

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPreview_v1() {
        return preview_v1;
    }

    public void setPreview_v1(String preview_v1) {
        this.preview_v1 = preview_v1;
    }

    public String getPreview_v2() {
        return preview_v2;
    }

    public void setPreview_v2(String preview_v2) {
        this.preview_v2 = preview_v2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }

    public String getAvg_point() {
        return avg_point;
    }

    public void setAvg_point(String avg_point) {
        this.avg_point = avg_point;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public float getXpoint() {
        return xpoint;
    }

    public void setXpoint(float xpoint) {
        this.xpoint = xpoint;
    }

    public float getYpoint() {
        return ypoint;
    }

    public void setYpoint(float ypoint) {
        this.ypoint = ypoint;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDealcate_name() {
        return dealcate_name;
    }

    public void setDealcate_name(String dealcate_name) {
        this.dealcate_name = dealcate_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

}
