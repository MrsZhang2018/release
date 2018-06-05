package com.fanwe.o2o.model;

import java.util.List;

/**
 * 定位城市
 * Created by luodong on 2016/12/12.
 */
public class CityFirstModel {
    private String key;
    private List<CityModel> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<CityModel> getValue() {
        return value;
    }

    public void setValue(List<CityModel> value) {
        this.value = value;
    }
}
