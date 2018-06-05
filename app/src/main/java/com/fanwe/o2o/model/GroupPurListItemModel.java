package com.fanwe.o2o.model;

import com.fanwe.o2o.utils.SDDistanceUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class GroupPurListItemModel
{
    private int location_id;
    private String location_name;
    private float avg_point;
    private String location_dp_count;
    private int bfb;
    private String area_name;
    private double distance;
    private List<GoodsModel> deal;

    // ===============add
    private String distanceFormat;

    private String is_verify;
    private String open_store_payment;

    public String getDistanceFormat()
    {
        return distanceFormat;
    }

    public void setDistanceFormat(String distanceFormat)
    {
        this.distanceFormat = distanceFormat;
    }

    public float getAvg_point() {
        return avg_point;
    }

    public void setAvg_point(float avg_point) {
        this.avg_point = avg_point;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public int getBfb() {
        return bfb;
    }

    public void setBfb(int bfb) {
        this.bfb = bfb;
    }

    public List<GoodsModel> getDeal() {
        return deal;
    }

    public void setDeal(List<GoodsModel> deal) {
        this.deal = deal;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
        calculateDistance();
    }

    public String getLocation_dp_count() {
        return location_dp_count;
    }

    public void setLocation_dp_count(String location_dp_count) {
        this.location_dp_count = location_dp_count;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public void calculateDistance()
    {
        this.distanceFormat = SDDistanceUtil.getKmDistanceString(distance);
    }
    public String getIs_verify()
    {
        return is_verify;
    }

    public void setIs_verify(String is_verify)
    {
        this.is_verify = is_verify;
    }

    public String getOpen_store_payment()
    {
        return open_store_payment;
    }

    public void setOpen_store_payment(String open_store_payment)
    {
        this.open_store_payment = open_store_payment;
    }
}
