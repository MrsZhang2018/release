package com.fanwe.o2o.model;

import java.io.Serializable;
import java.util.List;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-02
 * Time: 09:46
 * 功能:
 */
public class ShippingAddressModel extends BaseActModel implements Serializable{
    private List<ShippingAddressModel> consignee_list; //地址数组
    private int id; //地址id
    private String region_lv1_name; //国家
    private String region_lv2_name; //省
    private String region_lv3_name; //市
    private String region_lv4_name; //区/县
    private int region_lv1; //国家
    private int region_lv2; //省
    private int region_lv3; //市
    private int region_lv4; //区/县
    private String address; //详细地址
    private int is_default; //是否为默认地址 0:否 1:是
    private String consignee; //收货人姓名
    private String mobile; //收货人手机
    private String zip; //邮编
    private String full_address; //地址全称
    private String street; //小区街道信息
    private String xpoint; //收货地址经度
    private String ypoint; //收货地址纬度
    private String doorplate; //地址门牌号

    public List<ShippingAddressModel> getConsignee_list() {
        return consignee_list;
    }

    public void setConsignee_list(List<ShippingAddressModel> consignee_list) {
        this.consignee_list = consignee_list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion_lv1_name() {
        return region_lv1_name;
    }

    public void setRegion_lv1_name(String region_lv1_name) {
        this.region_lv1_name = region_lv1_name;
    }

    public String getRegion_lv2_name() {
        return region_lv2_name;
    }

    public void setRegion_lv2_name(String region_lv2_name) {
        this.region_lv2_name = region_lv2_name;
    }

    public String getRegion_lv3_name() {
        return region_lv3_name;
    }

    public void setRegion_lv3_name(String region_lv3_name) {
        this.region_lv3_name = region_lv3_name;
    }

    public String getRegion_lv4_name() {
        return region_lv4_name;
    }

    public void setRegion_lv4_name(String region_lv4_name) {
        this.region_lv4_name = region_lv4_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getXpoint() {
        return xpoint;
    }

    public void setXpoint(String xpoint) {
        this.xpoint = xpoint;
    }

    public String getYpoint() {
        return ypoint;
    }

    public void setYpoint(String ypoint) {
        this.ypoint = ypoint;
    }

    public String getDoorplate() {
        return doorplate;
    }

    public void setDoorplate(String doorplate) {
        this.doorplate = doorplate;
    }

    public int getRegion_lv1() {
        return region_lv1;
    }

    public void setRegion_lv1(int region_lv1) {
        this.region_lv1 = region_lv1;
    }

    public int getRegion_lv2() {
        return region_lv2;
    }

    public void setRegion_lv2(int region_lv2) {
        this.region_lv2 = region_lv2;
    }

    public int getRegion_lv3() {
        return region_lv3;
    }

    public void setRegion_lv3(int region_lv3) {
        this.region_lv3 = region_lv3;
    }

    public int getRegion_lv4() {
        return region_lv4;
    }

    public void setRegion_lv4(int region_lv4) {
        this.region_lv4 = region_lv4;
    }
}
