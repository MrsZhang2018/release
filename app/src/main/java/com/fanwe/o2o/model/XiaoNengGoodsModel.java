package com.fanwe.o2o.model;

/**
 * Created by hyc on 2017/6/8.
 */

public class XiaoNengGoodsModel
{

    /**
     * goods_id : 64
     * goods_showURL : http://192.168.1.95/svn/o2oNew/public/attachment/201609/21/15/57e2362df0f16_600x364.jpg
     * goodsTitle : 仅售69元！价值398元的龙中龙男士棉服1件，可脱卸帽保暖加厚棉衣，青年休闲外套。
     * goodsPrice : 97
     * goods_URL : http://192.168.1.95/svn/o2oNew/index.php?ctl=deal&act=64
     * settingid : md_198_1496913879749
     */

    private int goods_id;
    private String goods_showURL;
    private String goodsTitle;
    private String goodsPrice;
    private String goods_URL;
    private String settingid;

    public int getGoods_id()
    {
        return goods_id;
    }

    public void setGoods_id(int goods_id)
    {
        this.goods_id = goods_id;
    }

    public String getGoods_showURL()
    {
        return goods_showURL;
    }

    public void setGoods_showURL(String goods_showURL)
    {
        this.goods_showURL = goods_showURL;
    }

    public String getGoodsTitle()
    {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle)
    {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsPrice()
    {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice)
    {
        this.goodsPrice = goodsPrice;
    }

    public String getGoods_URL()
    {
        return goods_URL;
    }

    public void setGoods_URL(String goods_URL)
    {
        this.goods_URL = goods_URL;
    }

    public String getSettingid()
    {
        return settingid;
    }

    public void setSettingid(String settingid)
    {
        this.settingid = settingid;
    }
}
