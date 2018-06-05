package com.fanwe.o2o.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by luodong on 2016/10/28.
 */
public class TabModel extends BaseActModel implements SDSelectManager.SDSelectable
{
    private String id;
    private String name;
    private boolean selected;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
