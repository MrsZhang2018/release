package com.fanwe.o2o.model;

import com.fanwe.library.utils.SDCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/08.
 */
public class GroupPurChaseBrandListDaoModel implements Serializable
{
    private static final long serialVersionUID = 0L;

    private List<Brand_listModel> listModel = new ArrayList<>();

    public void setlistModel(List<Brand_listModel> listModel)
    {
        this.listModel = listModel;
    }

    public List<Brand_listModel> getlistModel()
    {
        return listModel;
    }

    /**
     * 保存团购品牌信息
     * @param model
     */
    public static void put(Brand_listModel model)
    {
        GroupPurChaseBrandListDaoModel modelDao = SDCache.getObject(GroupPurChaseBrandListDaoModel.class);
        if (modelDao == null)
        {
            modelDao = new GroupPurChaseBrandListDaoModel();
        }
        if (!(modelDao.getlistModel()).contains(model))
        {
            modelDao.getlistModel().add(model);
        }
        SDCache.setObject(modelDao);
    }

    /**
     * 获得所有团购品牌
     *
     * @return
     */
    public static List<Brand_listModel> get()
    {
        GroupPurChaseBrandListDaoModel modelDao = SDCache.getObject(GroupPurChaseBrandListDaoModel.class);
        if (modelDao == null)
        {
            return null;
        }
        return modelDao.getlistModel();
    }

    /**
     * 移除所有团购品牌
     *
     * @param model
     */
    public static void remove(Brand_listModel model)
    {
        GroupPurChaseBrandListDaoModel modelDao = SDCache.getObject(GroupPurChaseBrandListDaoModel.class);
        if (modelDao != null)
        {
            for (int i = 0; i < modelDao.getlistModel().size(); i++)
            {
                if (modelDao.getlistModel().get(i).getId() == model.getId())
                {
                    modelDao.getlistModel().remove(modelDao.getlistModel().get(i));
                }
            }
            SDCache.setObject(modelDao);
        }
    }
    /**
     *先移除重复记录在存贮
     * @param model
     */
    public static void removeAfterPut(Brand_listModel model)
    {
        remove(model);
        put(model);
    }

    /**
     * 清除所有团购品牌
     */
    public static void clear()
    {
        SDCache.removeObject(GroupPurChaseBrandListDaoModel.class);
    }

}
