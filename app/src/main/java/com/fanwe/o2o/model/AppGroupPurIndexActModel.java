package com.fanwe.o2o.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class AppGroupPurIndexActModel extends BaseActModel
{
    private String city_id;
    private String city_name;
    private int is_banner_square;
    private int advs_count;
    private String zt_html3;
    private String zt_html4;
    private String zt_html5;
    private String zt_html6;
    private List<GroupPurIndexArticleModel> article;
    private List<GroupPurIndexAdvsModel> advs;
    private GroupPurIndexIndexsModel indexs;
    private List<GroupPurIndexDealListModel> deal_list;
    private List<GroupPurRecommendDealCateModel> recommend_deal_cate;

    public List<GroupPurRecommendDealCateModel> getRecommend_deal_cate()
    {
        return recommend_deal_cate;
    }

    public void setRecommend_deal_cate(List<GroupPurRecommendDealCateModel> recommend_deal_cate)
    {
        this.recommend_deal_cate = recommend_deal_cate;
    }

    public List<GroupPurIndexAdvsModel> getAdvs() {
        return advs;
    }

    public void setAdvs(List<GroupPurIndexAdvsModel> advs) {
        this.advs = advs;
    }

    public int getAdvs_count() {
        return advs_count;
    }

    public void setAdvs_count(int advs_count) {
        this.advs_count = advs_count;
    }

    public List<GroupPurIndexArticleModel> getArticle() {
        return article;
    }

    public void setArticle(List<GroupPurIndexArticleModel> article) {
        this.article = article;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public List<GroupPurIndexDealListModel> getDeal_list() {
        return deal_list;
    }

    public void setDeal_list(List<GroupPurIndexDealListModel> deal_list) {
        this.deal_list = deal_list;
    }

    public GroupPurIndexIndexsModel getIndexs() {
        return indexs;
    }

    public void setIndexs(GroupPurIndexIndexsModel indexs) {
        this.indexs = indexs;
    }

    public int getIs_banner_square() {
        return is_banner_square;
    }

    public void setIs_banner_square(int is_banner_square) {
        this.is_banner_square = is_banner_square;
    }

    public String getZt_html3() {
        return zt_html3;
    }

    public void setZt_html3(String zt_html3) {
        this.zt_html3 = zt_html3;
    }

    public String getZt_html4() {
        return zt_html4;
    }

    public void setZt_html4(String zt_html4) {
        this.zt_html4 = zt_html4;
    }

    public String getZt_html5() {
        return zt_html5;
    }

    public void setZt_html5(String zt_html5) {
        this.zt_html5 = zt_html5;
    }

    public String getZt_html6() {
        return zt_html6;
    }

    public void setZt_html6(String zt_html6) {
        this.zt_html6 = zt_html6;
    }
}
