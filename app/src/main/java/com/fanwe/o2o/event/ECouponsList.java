package com.fanwe.o2o.event;

/**
 * Created by Administrator on 2017/2/8.
 */

public class ECouponsList
{
    /** 商圈id */
    private int qid;
    /** 小分类请求参数id */
    private int cate_id;
    /** 排序类型 */
    private String order_type;
    private int tid;

    public ECouponsList(int tid,int cate_id, int qid, String order_type)
    {
        this.cate_id = cate_id;
        this.qid = qid;
        this.tid = tid;
        this.order_type = order_type;
    }

    public int getTid()
    {
        return tid;
    }

    public void setTid(int tid)
    {
        this.tid = tid;
    }

    public int getCate_id()
    {
        return cate_id;
    }

    public void setCate_id(int cate_id)
    {
        this.cate_id = cate_id;
    }

    public int getQid()
    {
        return qid;
    }

    public void setQid(int qid)
    {
        this.qid = qid;
    }

    public String getOrder_type()
    {
        return order_type;
    }

    public void setOrder_type(String order_type)
    {
        this.order_type = order_type;
    }
}
