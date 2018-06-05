package com.fanwe.o2o.event;

import com.fanwe.o2o.model.Brand_listModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class EDismissBrandPopWindow
{
    private List<Brand_listModel> list;
    private String brandId;

    public EDismissBrandPopWindow(List<Brand_listModel> list)
    {
        this.list = list;
        setBrandId();
    }

    public EDismissBrandPopWindow(String brandId)
    {
        this.brandId = brandId;
    }

    /**
     * 设置品牌ID拼接
     */
    public void setBrandId()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
        {
            sb.append(list.get(i).getId()).append(",");
        }
        brandId = sb.toString();
    }

    public String getBrandId()
    {
        return brandId.substring(0,brandId.length() - 1);
    }

    public List<Brand_listModel> getList()
    {
        return list;
    }

    public void setList(List<Brand_listModel> list)
    {
        this.list = list;
    }

}
