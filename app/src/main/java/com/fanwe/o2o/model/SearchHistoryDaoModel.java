package com.fanwe.o2o.model;

import com.fanwe.library.utils.SDCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luodong on 2016/12/14.
 */
public class SearchHistoryDaoModel implements Serializable
{
    private static final long serialVersionUID = 0L;

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    /**
     * 保存搜索记录
     *
     * @param account
     */
    public static void set(String account)
    {
        SearchHistoryDaoModel model = SDCache.getObject(SearchHistoryDaoModel.class);
        if (model == null)
        {
            model = new SearchHistoryDaoModel();
        }
        if (!model.getList().contains(account))
        {
            model.getList().add(0, account);
        }
        SDCache.setObject(model);
    }

    /**
     * 获得搜索记录
     *
     * @return
     */
    public static List<String> get()
    {
        SearchHistoryDaoModel model = SDCache.getObject(SearchHistoryDaoModel.class);
        if (model == null)
        {
            return null;
        }
        return model.getList();
    }

    /**
     * 移除搜索记录
     *
     * @param account
     */
    public static void remove(String account)
    {
        SearchHistoryDaoModel model = SDCache.getObject(SearchHistoryDaoModel.class);
        if (model != null)
        {
            model.getList().remove(account);
            SDCache.setObject(model);
        }
    }

    /**
     * 移除再添加
     *
     * @param account
     */
    public static void removeAfterSet(String account)
    {
        remove(account);
        set(account);
    }

    /**
     * 情况搜索记录
     */
    public static void clear()
    {
        SDCache.removeObject(SearchHistoryDaoModel.class);
    }

}
