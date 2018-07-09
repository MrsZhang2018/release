package com.fanwe.hybrid.constant;

import cn.fanwe.yi.BuildConfig;

public class ApkConstant {
    /**
     * 协议
     */
    public static final String SERVER_URL_SCHEMES = "https://";
    /**
     * 域名
     */
    public static final String SERVER_URL_DOMAIN = BuildConfig.SERVER_URL_DOMAIN;// （需要修改）
    /**
     * 首页路径
     */
    public static final String SERVER_URL_PATH = BuildConfig.SERVER_URL_PATH;// （需要修改）
    /**
     * 初始化接口完整地址
     */
    public static final String SERVER_URL_INIT_URL = BuildConfig.SERVER_URL_INIT_URL;// （需要修改）
    /**
     * 首页完整地址
     */
    public static final String SERVER_URL = SERVER_URL_SCHEMES + SERVER_URL_DOMAIN + SERVER_URL_PATH;

    /**
     * 动画添加参数
     */
    public static final String SERVER_URL_ADD_PARAMS = "?show_prog=1";
    /**
     * 首页显示加载动画的完整地址
     */
    public static final String SERVER_URL_SHOW_ANIM = SERVER_URL + SERVER_URL_ADD_PARAMS;

    /**
     * 接口路径
     */
    public static final String SERVER_URL_PATH_API = "/wap/index.php";
    /**
     * 接口完整地址
     */
    public static final String SERVER_URL_API = SERVER_URL_SCHEMES + SERVER_URL_DOMAIN + SERVER_URL_PATH_API;
}
