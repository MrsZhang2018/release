package com.fanwe.o2o.model;

import java.util.List;

/**
 * wap首页接口
 * Created by Administrator on 2016/12/14.
 */

public class AppWapIndexActModel extends BaseActModel
{
    private String not_read_msg;
    private String city_id;
    private String city_name;
    private int is_banner_square;
    private String zt_html3;
    private String zt_html4;
    private String zt_html5;
    private String zt_html6;
    private List<WapIndexArticleModel> article;
    private List<WapIndexAdvsModel> advs;
    private List<WapIndexAdvs2Model> advs2;
    private WapIndexIndexsModel indexs;
    private List<WapIndexSupplierListModel> supplier_list;
    private List<WapIndexDealListModel> deal_list;
    private List<WapIndexSupplierDealList> supplier_deal_list;
    private List<WapIndexCateListModel> cate_list;
    private List<WapIndexEventListModel> event_list;
    private List<WapIndexYouHuiListModel> youhui_list;

    public String getNot_read_msg()
    {
        return not_read_msg;
    }

    public void setNot_read_msg(String not_read_msg)
    {
        this.not_read_msg = not_read_msg;
    }

    public List<WapIndexAdvs2Model> getAdvs2() {
        return advs2;
    }

    public void setAdvs2(List<WapIndexAdvs2Model> advs2) {
        this.advs2 = advs2;
    }

    public List<WapIndexAdvsModel> getAdvs() {
        return advs;
    }

    public void setAdvs(List<WapIndexAdvsModel> advs) {
        this.advs = advs;
    }

    public List<WapIndexArticleModel> getArticle() {
        return article;
    }

    public void setArticle(List<WapIndexArticleModel> article) {
        this.article = article;
    }

    public List<WapIndexCateListModel> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<WapIndexCateListModel> cate_list) {
        this.cate_list = cate_list;
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

    public List<WapIndexDealListModel> getDeal_list() {
        return deal_list;
    }

    public void setDeal_list(List<WapIndexDealListModel> deal_list) {
        this.deal_list = deal_list;
    }

    public List<WapIndexEventListModel> getEvent_list() {
        return event_list;
    }

    public void setEvent_list(List<WapIndexEventListModel> event_list) {
        this.event_list = event_list;
    }

    public WapIndexIndexsModel getIndexs() {
        return indexs;
    }

    public void setIndexs(WapIndexIndexsModel indexs) {
        this.indexs = indexs;
    }

    public int getIs_banner_square() {
        return is_banner_square;
    }

    public void setIs_banner_square(int is_banner_square) {
        this.is_banner_square = is_banner_square;
    }

    public List<WapIndexSupplierDealList> getSupplier_deal_list() {
        return supplier_deal_list;
    }

    public void setSupplier_deal_list(List<WapIndexSupplierDealList> supplier_deal_list) {
        this.supplier_deal_list = supplier_deal_list;
    }

    public List<WapIndexSupplierListModel> getSupplier_list() {
        return supplier_list;
    }

    public void setSupplier_list(List<WapIndexSupplierListModel> supplier_list) {
        this.supplier_list = supplier_list;
    }

    public List<WapIndexYouHuiListModel> getYouhui_list() {
        return youhui_list;
    }

    public void setYouhui_list(List<WapIndexYouHuiListModel> youhui_list) {
        this.youhui_list = youhui_list;
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
