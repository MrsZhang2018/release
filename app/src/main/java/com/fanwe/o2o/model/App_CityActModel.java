package com.fanwe.o2o.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 城市列表展示接口
 * Created by luodong on 2016/12/14.
 */
public class App_CityActModel extends BaseActModel {

    private LinkedHashMap<String, List<CityModel>> city_list;
    private List<CityModel> hot_city;

    public LinkedHashMap<String, List<CityModel>> getCity_list() {
        return city_list;
    }

    public void setCity_list(LinkedHashMap<String, List<CityModel>> city_list) {
        this.city_list = city_list;
    }

    public List<CityModel> getHot_city() {
        return hot_city;
    }

    public void setHot_city(List<CityModel> hot_city) {
        this.hot_city = hot_city;
    }
}
