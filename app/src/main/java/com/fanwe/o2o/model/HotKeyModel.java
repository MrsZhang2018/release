package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by hyc on 2017/5/25.
 */

public class HotKeyModel extends BaseActModel
{

    /**
     * hot_kw : ["啊手机都很高","萨科技风和","啊啊"]
     * city_name : 福州
     * return : 1
     * ref_uid : null
     */

    private String city_name;
    private List<String> hot_kw;

    public String getCity_name()
    {
        return city_name;
    }

    public void setCity_name(String city_name)
    {
        this.city_name = city_name;
    }

    public List<String> getHot_kw()
    {
        return hot_kw;
    }

    public void setHot_kw(List<String> hot_kw)
    {
        this.hot_kw = hot_kw;
    }
}
