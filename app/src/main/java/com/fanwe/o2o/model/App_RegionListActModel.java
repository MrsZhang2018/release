package com.fanwe.o2o.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by luod on 2017/3/8.
 */
public class App_RegionListActModel extends BaseActModel implements Serializable {

    private static final long serialVersionUID = 0L;

    private ArrayList<RegionModel> region_list ;

    private int region_versions;

    public int getRegion_versions() {
        return region_versions;
    }

    public void setRegion_versions(int region_versions) {
        this.region_versions = region_versions;
    }

    public ArrayList<RegionModel> getRegion_list() {
        return region_list;
    }

    public void setRegion_list(ArrayList<RegionModel> region_list) {
        this.region_list = region_list;
    }
}
