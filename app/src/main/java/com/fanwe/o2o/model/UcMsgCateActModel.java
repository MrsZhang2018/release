package com.fanwe.o2o.model;


import java.util.List;

/**
 * Created by luod on 2017/3/10.
 */

public class UcMsgCateActModel extends BaseActModel {


  /**
   * data : [{"id":"1248","content":"您的优惠券<248780730258>已过期","user_id":"73","create_time":"2017-03-03 15:48","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券已过期","link":"/wap/index.php?ctl=uc_youhui"},{"id":"1212","content":"您的优惠券<248780730258>即将过期，快去使用吧~","user_id":"73","create_time":"2017-03-02 15:48","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券即将过期","link":"/wap/index.php?ctl=uc_youhui"},{"id":"1149","content":"您的退款申请被驳回,商品<明视眼镜1>恢复<>","user_id":"73","create_time":"2017-02-28 16:11","is_read":"1","is_delete":"0","type":"notify","data_id":"385","title":"退款失败","link":"/wap/index.php?ctl=uc_order&act=view&data_id=385"},{"id":"1148","content":"您的退款申请被驳回,商品<明视眼镜1>恢复<>","user_id":"73","create_time":"2017-02-28 16:11","is_read":"1","is_delete":"0","type":"notify","data_id":"385","title":"退款失败","link":"/wap/index.php?ctl=uc_order&act=view&data_id=385"},{"id":"1098","content":"您的优惠券<248789604496>已过期","user_id":"73","create_time":"2017-02-27 17:11","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券已过期","link":"/wap/index.php?ctl=uc_youhui"},{"id":"1084","content":"您的商品<仅售39元！价值99元的魅货莫代尔不规则衫1件，魅货莫代尔不规则开衫>退款申请被驳回","user_id":"73","create_time":"2017-02-27 14:01","is_read":"1","is_delete":"0","type":"notify","data_id":"346","title":"退款失败","link":"/wap/index.php?ctl=uc_order&act=view&data_id=346"},{"id":"1083","content":"您的优惠券<248787463453>已过期","user_id":"73","create_time":"2017-02-27 10:30","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券已过期","link":"/wap/index.php?ctl=uc_youhui"},{"id":"1081","content":"您的优惠券<248787462499>已过期","user_id":"73","create_time":"2017-02-27 10:30","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券已过期","link":"/wap/index.php?ctl=uc_youhui"},{"id":"1077","content":"您的优惠券<248789604496>即将过期，快去使用吧~","user_id":"73","create_time":"2017-02-27 09:12","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券即将过期","link":"/wap/index.php?ctl=uc_youhui"},{"id":"1076","content":"您的优惠券<248787463453>即将过期，快去使用吧~","user_id":"73","create_time":"2017-02-27 09:12","is_read":"1","is_delete":"0","type":"notify","data_id":"0","title":"优惠券即将过期","link":"/wap/index.php?ctl=uc_youhui"}]
   * page : {"page":1,"page_total":8,"page_size":10,"data_total":"79"}
   * city_name : 福州
   * return : 1
   */

  private String city_name;
  private int returnX;
  private PageModel page;
  private List<UcMsgCateDataModel> data;

  public PageModel getPage() {
    return page;
  }

  public void setPage(PageModel page) {
    this.page = page;
  }

  public List<UcMsgCateDataModel> getData() {
    return data;
  }

  public void setData(List<UcMsgCateDataModel> data) {
    this.data = data;
  }

  public String getCity_name() {
    return city_name;
  }

  public void setCity_name(String city_name) {
    this.city_name = city_name;
  }

  public int getReturnX() {
    return returnX;
  }

  public void setReturnX(int returnX) {
    this.returnX = returnX;
  }
}
