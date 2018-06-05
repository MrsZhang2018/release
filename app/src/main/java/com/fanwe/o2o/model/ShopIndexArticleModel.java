package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2016/12/15.
 */

public class ShopIndexArticleModel
{
    private String id;//公告的ID
    private String name;//公告名称
    private String content;//公告内容
    private String create_time;//公告发布时间
    private int sort;//公告排序
    private int is_effect;//公告状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIs_effect() {
        return is_effect;
    }

    public void setIs_effect(int is_effect) {
        this.is_effect = is_effect;
    }
}
