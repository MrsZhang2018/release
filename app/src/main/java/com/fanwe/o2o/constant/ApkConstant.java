package com.fanwe.o2o.constant;

import com.fanwe.o2o.BuildConfig;

public class ApkConstant {

    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static final boolean AUTO_REGISTER = false;

    // 链接格式应该为schemes(协议)+domain(域名)+path(路径)

    /**
     * 协议
     */
//  public static final String SERVER_URL_SCHEMES = "https://";
    public static final String SERVER_URL_SCHEMES = "https://";
    /**
     * 域名
     */
    public static final String SERVER_URL_DOMAIN = BuildConfig.SERVER_URL_DOMAIN;
    /**
     * 首页路径
     */
    public static final String SERVER_URL_PATH = "/index.php";
    /**
     * 接口路径
     */
    public static final String SERVER_URL_PATH_API = "/mapi/index.php";

    public static final String SERVER_URL_PATH_WAP = "/wap/index.php";

    public static final String SERVER_URL_WAP = SERVER_URL_SCHEMES + SERVER_URL_DOMAIN + SERVER_URL_PATH_WAP;
    /**
     * 首页完整地址
     */
    public static final String SERVER_URL = SERVER_URL_SCHEMES + SERVER_URL_DOMAIN + SERVER_URL_PATH;
    /**
     * 接口完整地址
     */
    public static final String SERVER_URL_API = SERVER_URL_SCHEMES + SERVER_URL_DOMAIN + SERVER_URL_PATH_API;

    /**
     * 初始化接口完整地址
     */
    public static final String SERVER_URL_INIT_URL = SERVER_URL_API + "?ctl=app&act=init";

    public static final String SERVER_URL_ADD_PARAMS = "?show_prog=1";
    /**
     * 首页显示加载动画的完整地址
     */
    public static final String SERVER_URL_SHOW_ANIM = SERVER_URL + SERVER_URL_ADD_PARAMS;

    public static final String KEY_AES = "FANWE5LMUQC436IM";

    /**
     * 订单拼接
     */
    public static final String ORDER = "?ctl=uc_order&tuan=1";
}
