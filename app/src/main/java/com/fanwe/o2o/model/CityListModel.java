package com.fanwe.o2o.model;

import java.util.ArrayList;

/**
 * Created by hyc on 2017/4/17.
 */

public class CityListModel
{

    private ArrayList<RegionModel> provinces;

    private ArrayList<ArrayList<RegionModel>> cities;

    private ArrayList<ArrayList<ArrayList<RegionModel>>> counties;

    public ArrayList<RegionModel> getProvinces()
    {
        return provinces;
    }

    public void setProvinces(ArrayList<RegionModel> provinces)
    {
        this.provinces = provinces;
    }

    public ArrayList<ArrayList<RegionModel>> getCities()
    {
        return cities;
    }

    public void setCities(ArrayList<ArrayList<RegionModel>> cities)
    {
        this.cities = cities;
    }

    public ArrayList<ArrayList<ArrayList<RegionModel>>> getCounties()
    {
        return counties;
    }

    public void setCounties(ArrayList<ArrayList<ArrayList<RegionModel>>> counties)
    {
        this.counties = counties;
    }
}
