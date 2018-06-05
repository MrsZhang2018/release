package com.fanwe.o2o.model;

/**
 * 分享
 * Created by luodong on 2017/1/3.
 */
public class ShareModel extends BaseActModel {

    /**
     * share_content : http://192.168.1.74/o2onew/index.php?ctl=deal&act=268
     * share_url : http://192.168.1.74/o2onew/index.php?ctl=deal&act=268
     * key :
     * sina_app_api : 1
     * qq_app_api : 1
     * share_imageUrl : http://192.168.1.74/o2onew/public/attachment/201610/10/11/57fb0b3b40428_600x364.jpg
     * share_title : 仅售19.9元，价值100元少儿创意手工体验套餐！
     */

    private String share_content;
    private String share_url;
    private String key;
    private int sina_app_api;
    private int qq_app_api;
    private String share_imageUrl;
    private String share_title;

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSina_app_api() {
        return sina_app_api;
    }

    public void setSina_app_api(int sina_app_api) {
        this.sina_app_api = sina_app_api;
    }

    public int getQq_app_api() {
        return qq_app_api;
    }

    public void setQq_app_api(int qq_app_api) {
        this.qq_app_api = qq_app_api;
    }

    public String getShare_imageUrl() {
        return share_imageUrl;
    }

    public void setShare_imageUrl(String share_imageUrl) {
        this.share_imageUrl = share_imageUrl;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }
}
