package com.fanwe.o2o.constant;


public class Constant
{

	public static final String DOWN_LOAD_DIR_NAME = "fanwe";

	public static final String EARN_SUB_CHAR = "市";

	public static final String JS_AJAX_LOAD = "js_ajax_load";
	public static final String JS_THIRD_PARTY_LOGIN_SDK = "js_third_party_login_sdk";//第三方回调
	public static final String JS_CUT_CALL_BACK = "CutCallBack";//头像回调
	public static final String JS_POSITION = "js_position";//回调js定位
	/** 支付回调JS 1 支付成功 2 支付中 3 支付失败 4 取消支付 5 网络原因 6 其他原因 */
	public static final String JS_PAY_SDK = "js_pay_sdk";// js_pay_sdk(1-6)

	/**
	 * sdk支付方式
	 * 
	 */
	public static final class PaymentType
	{
		///** 支付宝（新） */
		//public static final String MALIPAY = "Malipay";
		/** 银联SDK */
		public static final String UPAPP = "uppay";
		/** 支付宝 */
		public static final String ALIPay = "ALIPay";
		/** 微信支付 */
		public static final String WXPAY = "WXPAY";
		///** 银联支付 */
		//public static final String UPACPAPP = "Upacpapp";
	}

	public static final class LoadImageType
	{
		/** 任何网络下都加载图片 */
		public static final int ALL = 1;
		/** wifi网络下加载图片,移动网络下不允许加载图片 */
		public static final int ONLY_WIFI = 0;
	}

	public static final class PushType
	{
		/** app主页*/
		public static final int MAIN = 1;
		public static final int URL = 2;
		public static final int TUAN_DETAIL = 3;
		public static final int GOODS_DETAIL = 4;
		public static final int EVENT_DETAIL = 5;
		public static final int YOUHUI_DETAIL = 6;
		public static final int STORE_DETAIL = 7;
		public static final int NOTICE_DETAIL = 8;
	}

	public enum TitleType
	{
		TITLE_NONE, TITLE;
	}

	public static final class SearchTypeMap
	{
		/** 全部 */
		public static final int ALL = -1;
		/** 优惠券 */
		public static final int YOU_HUI = 0;
		/** 活动 */
		public static final int EVENT = 1;
		/** 团购 */
		public static final int TUAN = 2;
		/** 门店 */
		public static final int STORE = 3;
	}

	public static final class SearchTypeNormal
	{
		/** 优惠券 */
		public static final int YOU_HUI = 0;
		/** 团购 */
		public static final int TUAN = 2;
		/** 商家 */
		public static final int MERCHANT = 3;
		/** 活动 */
		public static final int EVENT = 4;
		/** 商城 */
		public static final int SHOP = 5;
	}

	public static final class SearchTypeNormalString
	{
//		public static final String YOU_HUI = SDResourcesUtil.getString(R.string.youhui_coupon);
//		public static final String TUAN = SDResourcesUtil.getString(R.string.tuan_gou);
//		public static final String MERCHANT = SDResourcesUtil.getString(R.string.supplier);
//		public static final String EVENT = SDResourcesUtil.getString(R.string.event);
//		public static final String SHOP = SDResourcesUtil.getString(R.string.goods);
	}

	public static final class CommentType
	{
		public static final String DEAL = "deal";
		public static final String YOUHUI = "youhui";
		public static final String EVENT = "event";
		public static final String STORE = "store";
	}

	public static final class JSDetailType
	{
		public static final int TUAN = 0;
		public static final int GOODS = 1;
		public static final int SUPPLIER = 2;
		public static final int EVENT = 3;
		public static final int YOU_HUI = 4;
	}

	public static final class UserLoginState
	{
		public static final int UN_LOGIN = 0;  //未登录
		public static final int LOGIN = 1;
		public static final int TEMP_LOGIN = 2;
	}

	public static final class DealTag
	{
		/** 0元抽奖 */
		public static final int FREE_LOTTERY = 0;
		/** 免预约 */
		public static final int FREE_RESERVATION = 1;
		/** 多套餐 */
		public static final int MULTI_PACKAGES = 2;
		/** 可订座 */
		public static final int CAN_RESERVATION = 3;
		/** 折扣券 */
		public static final int DISCOUNT_TICKETS = 4;
		/** 过期退 */
		public static final int EXPIRED_REFUND = 5;
		/** 随时退 */
		public static final int ANY_REFUND = 6;
		/** 七天退 */
		public static final int WEEK_REFUND = 7;
		/** 免运费 */
		public static final int FREE_FREIGHT = 8;
		/** 满立减 */
		public static final int FULL_MINUS = 9;
	}

	public enum EnumEvent
	{
		/**
		 * 支付SDK
		 */
		EVENT_PAY_SDK,

    /**
     * 微信支付回调发送事件
     */
    EVENT_WX_PAY_JS_BACK,
    /**
     * 微信登录返回信息给PC
     */
    EVENT_WX_LOGIN_JS_BACK;

		public static EnumEvent valueOf(int index)
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



	public enum EnumLoginState
	{
		/** 未登录 */
		UN_LOGIN,
		/** 临时登录，需要绑定手机号 */
		LOGIN_NEED_BIND_PHONE,
		/** 已登录，手机号为空 */
		LOGIN_EMPTY_PHONE,
		/** 已登录 */
		LOGIN,
		/** 已登录,需要验证 */
		LOGIN_NEED_VALIDATE;
	}

	public static final class IndexType
	{
		/** 自定义url */
		public static final int URL = 0;
		/** main首页 */
		public static final int APP_MAIN = 1;
		/** 积分商品列表 */
		public static final int SCORE_LIST = 13;
		/** 文章列表 */
		public static final int NOTICE_LIST = 17;
		/** 到店付门店列表 */
		public static final int STORE_PAY_LIST = 18;

		/** 购物明细 */
		public static final int DEAL_DETAIL = 21;
		/** 活动明细 */
		public static final int EVENT_DETAIL = 24;
		/** 优惠明细 */
		public static final int YOUHUI_DETAIL = 25;
		/** 门店明细 */
		public static final int STORE_DETAIL = 26;
		/** 文章明细 */
		public static final int NOTICE_DETAIL = 27;

		/** 分销分享管理 */
		public static final int DISTRIBUTION_MANAGER = 41;

		/** 扫一扫 */
		public static final int SCAN = 31;
		/** 附近会员 */
		public static final int NEARUSER = 32;
		/** 分销小店 */
		public static final int DISTRIBUTION_STORE = 51;
		/** 发现 */
		public static final int DISCOVERY = 61;

		/** 外卖列表 */
		public static final int TAKEAWAY_LIST = 100;
		/** 订座列表 */
		public static final int RESERVATION_LIST = 101;


		/** 团购列表 */
		public static final int GROUP_PURCHASE_LIST = 11;
		/** 商品列表 */
		public static final int GOODS_LIST = 12;
		/** 活动列表 */
		public static final int ACTIVITIES_LIST = 14;
		/** 优惠券列表 */
		public static final int COUPONS_LIST = 15;
		/** 门店列表 */
		public static final int STORES_LIST = 16;
		/** 商城首页 */
		public static final int MALL_MAIN = 102;
		/** 团购首页 */
		public static final int TUAN_MAIN = 103;
		/** 积分商城首页 */
		public static final int SCORE_MAIN = 104;
		/** 发现页 */
		public static final int DISCOVER = 105;
		/** 购物车 */
		public static final int SHOP_CART = 106;
		/** 会员中心 */
		public static final int USER_CENTER = 107;
		/** 消息中心 */
		public static final int MSG_CENTER = 108;
		/** 账户管理 */
		public static final int ACCOUNT_MANAGE = 201;
		/** 优惠券 */
		public static final int USER_YOUHUI = 203;
		/** 消费券 */
		public static final int USER_COUPONS = 207;
		/** 订单详情 */
		public static final int ORDER_DETAILS = 302;
		/** 订单列表 */
		public static final int ORDER_LIST = 305;
        /**买单列表*/
		public static final int BUY_ORDER = 307;
		/** 订单详情 */
		public static final int ORDER_DETAILS2 = 308;
		/** 去评价 */
		public static final int  COMMENT= 310;
	}

	public static final class MainIntentTab
	{
		/** 首页 */
		public static final int APP_MAIN = 0;
		/** 发现页 */
		public static final int DISCOVER = 1;
		/** 购物车 */
		public static final int SHOP_CART = 2;
		/** 会员中心 */
		public static final int USER_CENTER = 3;
	}

	public static final class OrderType
	{
		/** 全部 */
		public static final int ALL = 0;
		/** 待付款 */
		public static final int WAIT_PAY = 1;
		/** 待发货 */
		public static final int WAIT_SEND = 2;
		/** 待确认 */
		public static final int WAIT_CONFIRM = 3;
		/** 待评价 */
		public static final int WAIT_EVALUATE = 4;
	}

	public static final class OrderBtnType
	{
		/** 物流&收货 */
		public static final String LOGISTICS_GOODSRECEIPT = "j-logistics|goodsreceipt";
		/** 查看消费券 */
		public static final String COUPON = "j-coupon";
		/** 评价 */
		public static final String DP = "j-dp";
		/** 删除订单 */
		public static final String DEL = "j-del";
		/** 去支付 */
		public static final String PAYMENT = "j-payment";
		/** 取消订单 */
		public static final String CANCEL = "j-cancel";
		/** 退款 */
		public static final String REFUND = "j-refund";

	}

}
