package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/2/11.
 */

public class AppUserSettingActModel extends BaseActModel
{
    private String DB_VERSION;
    private String SHOP_TEL;
    private String REPLY_ADDRESS;
    private String APP_ABOUT_US;
    private String url;
    private int user_login_status;

    public String getAPP_ABOUT_US() {
        return APP_ABOUT_US;
    }

    public void setAPP_ABOUT_US(String APP_ABOUT_US) {
        this.APP_ABOUT_US = APP_ABOUT_US;
    }

    public String getDB_VERSION() {
        return DB_VERSION;
    }

    public void setDB_VERSION(String DB_VERSION) {
        this.DB_VERSION = DB_VERSION;
    }

    public String getREPLY_ADDRESS() {
        return REPLY_ADDRESS;
    }

    public void setREPLY_ADDRESS(String REPLY_ADDRESS) {
        this.REPLY_ADDRESS = REPLY_ADDRESS;
    }

    public String getSHOP_TEL() {
        return SHOP_TEL;
    }

    public void setSHOP_TEL(String SHOP_TEL) {
        this.SHOP_TEL = SHOP_TEL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getUser_login_status() {
        return user_login_status;
    }

    @Override
    public void setUser_login_status(int user_login_status) {
        this.user_login_status = user_login_status;
    }
}
