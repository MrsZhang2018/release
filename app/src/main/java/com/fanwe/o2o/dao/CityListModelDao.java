package com.fanwe.o2o.dao;


import com.fanwe.o2o.model.CityListModel;

/**
 * Created by hyc on 2017/4/17.
 */

public class CityListModelDao
{
    public static boolean insertOrUpdate(CityListModel model)
    {
        return JsonDbModelDaoX.getInstance().insertOrUpdate(model);
    }

    public static CityListModel query()
    {
        CityListModel model = JsonDbModelDaoX.getInstance().query(CityListModel.class);
        return model;
    }

    public static void deleteModel()
    {
        JsonDbModelDaoX.getInstance().delete(CityListModel.class);
    }

}

