package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/2/11.
 */

public class AppHaveReadMsgActModel extends BaseActModel
{
    private int count;
    private int user_login_status;

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    @Override
    public int getUser_login_status()
    {
        return user_login_status;
    }

    @Override
    public void setUser_login_status(int user_login_status)
    {
        this.user_login_status = user_login_status;
    }
}
