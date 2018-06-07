package com.fanwe.hybrid.event;

public enum EnumEventTag
{
    /**
     * 退出app事件
     */
    EVENT_EXIT_APP,

    /**
     * 微信支付回调发送事件
     */
    EVENT_WX_PAY_JS_BACK,
    /**
     * 微信登录返回信息给PC
     */
    EVENT_WX_LOGIN_JS_BACK,

    // 本地html的EVENT事件===========================
    /**
     * 打开网络
     */
    EVENT_ONPEN_NETWORK,
    /**
     * 刷新重载
     */
    EVENT_REFRESH_RELOAD,
    // 本地html发送的EVENT事件===========================
    // 服务端调用本地发送的EVENT事件===========================
    /**
     * 退出账户成功
     */
    EVENT_LOGOUT_SUCCESS,
    /**
     * 登录成功
     */
    EVENT_LOGIN_SUCCESS,

    /**
     * 关闭Activiti并刷新url
     */
    EVENT_FINSHI_ACTIVITY,
    /**
     * 支付SDK
     */
    EVENT_PAY_SDK,
    /**
     * 刷新MainActvity url
     */
    EVENT_ONCONFIRM,
    /**
     * 打开方式WEBVIEW打开或者浏览器打开
     */
    EVENT_OPEN_TYPE,
    /**
     * 打开二维码扫描
     */
    EVENT_QR_CODE_SCAN,
    /**
     * 打开二维码扫描_2
     */
    EVENT_QR_CODE_SCAN_2,
    /**
     * 剪切图片
     */
    EVENT_CUTPHOTO,
    /**
     * 剪切文本
     */
    EVENT_CLIPBOARDTEXT,
    /**
     * 获取经纬度
     */
    TENCENT_LOCATION_MAP,
    /**
     * 反编译地址
     */
    TENCENT_LOCATION_ADDRESS,
    /**
     * 推送APNS
     */
    EVENT_APNS,
    /**
     * 第三方登录
     */
    EVENT_LOGIN_SDK,
    /**
     * 判断微信是否安装
     */
    EVENT_IS_EXIST_INSTALLED,
    /**
     * 点击分享弹出指定分享未完成
     */
    EVENT_JS_SHARE_SDK,
    /**
     * 分答刷新页面
     */
    EVENT_RELOAD_WEBVIEW;
    // 服务端调用本地发送的EVENT事件===========================

    public static EnumEventTag valueOf(int index)
    {
        if (index >= 0 && index < values().length)
        {
            return values()[index];
        } else
        {
            return null;
        }
    }

}
