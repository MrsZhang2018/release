package com.fanwe.o2o.common;

import android.text.TextUtils;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.dao.InitActModelDao;
import com.fanwe.o2o.dialog.InputImageCodeDialog;
import com.fanwe.o2o.http.AppHttpUtil;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.http.AppRequestCallbackWrapper;
import com.fanwe.o2o.http.AppRequestParams;
import com.fanwe.o2o.model.AccountManageActModel;
import com.fanwe.o2o.model.ActivityCouponActModel;
import com.fanwe.o2o.model.AppActivitesActModel;
import com.fanwe.o2o.model.AppCouponReceiveActModel;
import com.fanwe.o2o.model.AppGoodsWapIndexActModel;
import com.fanwe.o2o.model.AppGroupPurIndexActModel;
import com.fanwe.o2o.model.AppGroupPurIndexDealListActModel;
import com.fanwe.o2o.model.AppGroupPurListIndexActModel;
import com.fanwe.o2o.model.AppHaveReadMsgActModel;
import com.fanwe.o2o.model.AppKeepListActModel;
import com.fanwe.o2o.model.AppShopCateActModel;
import com.fanwe.o2o.model.AppShopIndexActModel;
import com.fanwe.o2o.model.AppShopIndexDealListActModel;
import com.fanwe.o2o.model.AppStoresIndexActModel;
import com.fanwe.o2o.model.AppUpdateNickNameActModel;
import com.fanwe.o2o.model.AppUserCenterMessageActModel;
import com.fanwe.o2o.model.AppUserCenterWapIndexActModel;
import com.fanwe.o2o.model.AppUserLoginOutActModel;
import com.fanwe.o2o.model.AppUserSettingActModel;
import com.fanwe.o2o.model.AppWapIndexActModel;
import com.fanwe.o2o.model.AppWapIndexDealListActModel;
import com.fanwe.o2o.model.App_CityActModel;
import com.fanwe.o2o.model.AppCouponWapIndexActModel;
import com.fanwe.o2o.model.BaseActModel;
import com.fanwe.o2o.model.App_RegionListActModel;
import com.fanwe.o2o.model.BuyOrderActModel;
import com.fanwe.o2o.model.CouponActModel;
import com.fanwe.o2o.model.Events_indexActModel;
import com.fanwe.o2o.model.Goods_indexActModel;
import com.fanwe.o2o.model.HotKeyModel;
import com.fanwe.o2o.model.Init_indexActModel;
import com.fanwe.o2o.model.KeepItemModel;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.OrderRefundDetailModel;
import com.fanwe.o2o.model.OrderRefundListModel;
import com.fanwe.o2o.model.RefundGoodsActModel;
import com.fanwe.o2o.model.RefundRequestActModel;
import com.fanwe.o2o.model.Sms_send_sms_codeActModel;
import com.fanwe.o2o.model.Stores_indexActModel;
import com.fanwe.o2o.model.Tuan_indexActModel;
import com.fanwe.o2o.model.UcMsgCateActModel;
import com.fanwe.o2o.model.UcOrderActModel;
import com.fanwe.o2o.model.UcOrderOrderDpActModel;
import com.fanwe.o2o.model.UcOrderWapViewActModel;
import com.fanwe.o2o.model.UploadImageActModel;
import com.fanwe.o2o.model.User_infoModel;
import com.fanwe.o2o.model.Youhuis_indexActModel;
import com.fanwe.o2o.work.RetryInitWorker;

import java.io.File;

public class CommonInterface
{

    /**
     * 初始化
     *
     * @param listener
     */
    public static void requestInit(AppRequestCallback<Init_indexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("init");
        params.putUser();
        params.put("device_type", "android");
        params.put("sdk_version", "android");
        params.put("r_type", "0");
        params.setIsNeedShowErrorTip(false);
        AppHttpUtil.getInstance().post(params, new AppRequestCallbackWrapper<Init_indexActModel>(listener)
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    // TODO:对初始化返回结果进行处理
                    InitActModelDao.insertOrUpdate(actModel);
                    LocalUserModel.dealLoginSuccess(actModel.getUser(), false);
//                    UmengSocialManager.initHandler();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                InitActModelDao.deleteModel();
                RetryInitWorker.getInstance().start(); // 如果初始化失败重试
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    /**
     * wap首页接口
     *
     * @param listener
     */
    public static void requestWapIndex(AppRequestCallback<AppWapIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("wap");
        params.putCtl("index");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * wap首页上拉加载分页接口
     *
     * @param page
     * @param listener
     */
    public static void requestLoadMoreData(int page, AppRequestCallback<AppWapIndexDealListActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("load_index_list_data");
        params.putCtl("index");
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 城市列表展示接口
     *
     * @param listener
     */
    public static void requestCity(AppRequestCallback<App_CityActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("city");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 商品首页接口
     *
     * @param listener
     */
    public static void requestShopIndex(AppRequestCallback<AppShopIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 商品首页接口上拉加载分页接口
     *
     * @param page
     * @param listener
     */
    public static void requestShopLoadMore(int page, AppRequestCallback<AppShopIndexDealListActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("load_index_list_data");
        params.putCtl("shop");
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 团购首页接口
     *
     * @param listener
     */
    public static void requestGroupPurchaseIndex(AppRequestCallback<AppGroupPurIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("main");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 团购首页接口上拉加载分页接口
     *
     * @param page
     * @param listener
     */
    public static void requestGroupPurLoadMore(int page, AppRequestCallback<AppGroupPurIndexDealListActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("load_index_list_data");
        params.putCtl("main");
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 商城分类
     *
     * @param listener
     */
    public static void requestShopCate(AppRequestCallback<AppShopCateActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("cate");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户登录接口
     *
     * @param user_key
     * @param user_pwd
     * @param listener
     */
    public static void requestLoginNormal(String user_key, String user_pwd, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("dologin");
        params.putCtl("user");
        params.put("user_key", user_key);
        params.put("user_pwd", user_pwd);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 短信验证码
     *
     * @param mobile
     * @param unique   是否需要检测被占用 0:不检测 1:要检测是否被抢占（用于注册，绑定时使用）2:要检测是否存在（取回密码）3 检测会员是否绑定手机
     * @param listener
     */
    public static void requestValidateCode(String mobile, int unique, AppRequestCallback<Sms_send_sms_codeActModel> listener)
    {
        final AppRequestParams params = new AppRequestParams();
        params.putAct("send_sms_code");
        params.putCtl("sms");
        params.put("mobile", mobile);
        params.put("unique", unique);
        params.put("verify_code", AppConfig.getImageCode());
        params.setIsNeedShowErrorTip(false);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, new AppRequestCallbackWrapper<Sms_send_sms_codeActModel>(listener)
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                switch (actModel.getStatus())
                {
                    case -1:
                        InputImageCodeDialog dialog = new InputImageCodeDialog(SDActivityManager.getInstance().getLastActivity());
                        dialog.setImage(actModel.getVerify_image());
                        dialog.show();

                        if (getOriginalCallback() != null)
                        {
                            getOriginalCallback().showToast();
                        }
                        break;
                    case 1:
                        AppConfig.setImageCode("");
                        params.setNeedShowActInfo(true);
                        break;

                    default:
                        params.setNeedShowActInfo(true);
                        break;
                }
            }
        });
    }

    /**
     * 账户管理接口
     */
    public static void requestAccountManage(AppRequestCallback<AccountManageActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("wap_index");
        params.putCtl("uc_account");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 快速登陆接口
     *
     * @param mobile
     * @param sms_verify
     * @param listener
     */
    public static void requestShortcutLogin(String msg_id, String mobile, String sms_verify, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.put("msg_id", msg_id);
        params.putAct("dophlogin");
        params.putCtl("user");
        params.put("is_login", 1);
        params.put("mobile", mobile);
        params.put("sms_verify", sms_verify);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 注册
     *
     * @param user_mobile
     * @param user_pwd
     * @param sms_verify
     * @param listener
     */
    public static void requestRegister(String user_mobile, String user_pwd, String sms_verify, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("dophregister");
        params.putCtl("user");
        params.put("user_mobile", user_mobile);
        params.put("user_pwd", user_pwd);
        params.put("sms_verify", sms_verify);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 找回密码
     *
     * @param mobile
     * @param sms_verify
     * @param new_pwd
     * @param listener
     */
    public static void requestModifyPassword(String mobile, String sms_verify, String new_pwd, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("phmodifypassword");
        params.putCtl("user");
        params.put("mobile", mobile);
        params.put("sms_verify", sms_verify);
        params.put("new_pwd", new_pwd);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 手机号码绑定和更改绑定接口
     *
     * @param mobile
     * @param sms_verify 验证码
     * @param step       1:已有绑定手机的第一步 2:无绑定手机或已绑定手机的第二步
     * @param is_luck    已绑定手机的第二步验证参数
     * @param listener
     */
    public static void requestBindMobile(String mobile, String sms_verify, int step, int is_luck, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("bindphone");
        params.putCtl("uc_account");
        params.put("mobile", mobile);
        params.put("sms_verify", sms_verify);
        params.put("step", step);
        if (is_luck > 0)
            params.put("is_luck", is_luck);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 微信登录
     *
     * @param openId
     * @param access_token
     * @param nickName
     * @param unionid
     * @param headimgurl
     * @param listener
     */
    public static void requestLoginWx(String openId, String access_token, String nickName, String unionid, String headimgurl, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("synclogin");
        params.put("login_type", "Wechat");
        params.put("openid", openId);
        params.put("access_token", access_token);
        params.put("nickname", nickName);
        params.put("unionid", unionid);
        params.put("headimgurl", headimgurl);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * QQ登录
     *
     * @param openId
     * @param access_token
     * @param nickname
     * @param listener
     */
    public static void requestQQLogin(String openId, String access_token, String nickname, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("synclogin");
        params.put("login_type", "Qq");
        params.put("qqv2_id", openId);
        params.put("access_token", access_token);
        if (nickname != null)
        {
            params.put("nickname", nickname);
        }
        AppHttpUtil.getInstance().post(params, listener);

    }

    /**
     * sina登录
     *
     * @param uid
     * @param access_token
     * @param nickname
     * @param listener
     */
    public static void requestSinaLogin(String uid, String access_token, String nickname, AppRequestCallback<User_infoModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("synclogin");
        params.put("login_type", "Sina");
        params.put("sina_id", uid);
        params.put("access_token", access_token);
        if (nickname != null)
        {
            params.put("nickname", nickname);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 会员中心首页接口
     *
     * @param listener
     */
    public static void requestUserCenterWapIndex(AppRequestCallback<AppUserCenterWapIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putAct("wap_index");
        params.putCtl("user_center");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 消息中心接口（需登录）
     *
     * @param listener
     */
    public static void requestUserCenterMessage(AppRequestCallback<AppUserCenterMessageActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_msg");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户设置接口
     *
     * @param listener
     */
    public static void requestUserSetting(AppRequestCallback<AppUserSettingActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("setting");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户名修改接口
     *
     * @param user_name
     * @param listener
     */
    public static void requestUpdateNickName(String user_name, AppRequestCallback<AppUpdateNickNameActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("dochangeuname");
        params.put("user_name", user_name);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 统计会员未读消息数量
     *
     * @param listener
     */
    public static void requestHaveReadMsg(AppRequestCallback<AppHaveReadMsgActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_msg");
        params.putAct("countNotRead");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 注销 接口
     *
     * @param listener
     */
    public static void requestUserLoginOut(AppRequestCallback<AppUserLoginOutActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("loginout");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 团购搜索接口
     *
     * @param page
     * @param order_type
     * @param tid
     * @param cate_id
     * @param qid
     * @param keyword
     * @param mNotice
     * @param listener
     */
    public static void requestTuanIndex(int page, String order_type, int tid, int cate_id, int qid, String keyword, int mNotice, AppRequestCallback<Tuan_indexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("tuan");
        params.put("page", page);
        params.put("order_type", order_type); // 排序类型
        params.put("tid", tid);// 小分类ID
        params.put("cate_id", cate_id);// 大分类ID
        params.put("qid", qid); // 商圈
        params.put("keyword", keyword);
        if (mNotice > 0)
        {
            params.put("notice", 1);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 店铺搜索接口
     *
     * @param page
     * @param order_type
     * @param tid
     * @param cate_id
     * @param qid
     * @param keyword
     * @param store_type
     * @param listener
     */
    public static void requestStoresIndex(int page, String order_type, int tid, int cate_id, int qid, String keyword, int store_type, AppRequestCallback<Stores_indexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("stores");
        params.put("keyword", keyword);
        params.put("order_type", order_type); // 排序类型
        params.put("tid", tid);// 小分类ID
        params.put("cate_id", cate_id);// 大分类ID
        params.put("qid", qid);
        params.put("store_type", store_type);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 事件搜索接口
     *
     * @param page
     * @param order_type
     * @param tid
     * @param cate_id
     * @param qid
     * @param keyword
     * @param listener
     */
    public static void requestEventsIndex(int page, String order_type, int tid, int cate_id, int qid, String keyword, AppRequestCallback<Events_indexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("events");
        params.put("keyword", keyword);
        params.put("order_type", order_type); // 排序类型
        params.put("tid", tid);// 小分类ID
        params.put("cate_id", cate_id);// 大分类ID
        params.put("qid", qid);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 商品搜索接口
     *
     * @param page
     * @param order_type
     * @param bid
     * @param cate_id
     * @param keyword
     * @param listener
     */
    public static void requestGoodsIndex(int page, String order_type, int bid, int cate_id, String keyword, AppRequestCallback<Goods_indexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("goods");
        params.put("keyword", keyword);
        params.put("order_type", order_type); // 排序类型
        params.put("bid", bid);// 小分类ID
        params.put("cate_id", cate_id);// 大分类ID
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 优惠搜索接口
     *
     * @param page
     * @param order_type
     * @param tid
     * @param cate_id
     * @param qid
     * @param keyword
     * @param listener
     */
    public static void requestYouhuisIndex(int page, String order_type, int tid, int cate_id, int qid, String keyword, AppRequestCallback<Youhuis_indexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("youhuis");
        params.put("keyword", keyword);
        params.put("order_type", order_type); // 排序类型
        params.put("tid", tid);// 小分类ID
        params.put("cate_id", cate_id);// 大分类ID
        params.put("qid", qid);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 优惠券列表接口
     *
     * @param cate_id
     * @param qid
     * @param order_type
     * @param listener
     */
    public static void requestCouponWapIndex(int page, int tid,int cate_id, int qid, String order_type, AppRequestCallback<AppCouponWapIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("youhuis");
        params.putAct("wap_index");
        params.put("page", page);
        if (tid > 0)
        {
            params.put("tid", tid);// 分类ID
        }
        if (cate_id > 0)
            params.put("cate_id", cate_id);// 分类ID
        if (qid > 0)
            params.put("qid", qid);//地区id
        params.put("order_type", order_type);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 优惠券领取
     *
     * @param data_id
     * @param listener
     */
    public static void requestCouponReceive(int data_id, AppRequestCallback<AppCouponReceiveActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("youhui");
        params.putAct("download_youhui");
        params.put("data_id", data_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 优惠券列表
     *
     * @param page
     * @param listener
     */
    public static void requestCouponList(int page, AppRequestCallback<CouponActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_youhui");
        params.putAct("wap_index");
        params.put("page", page);
        AppHttpUtil.getInstance().get(params, listener);
    }

    /**
     * 活动券列表
     *
     * @param page
     * @param listener
     */
    public static void requestActivityCouponList(int page, AppRequestCallback<ActivityCouponActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_event");
        params.putAct("wap_index");
        params.put("page", page);
        AppHttpUtil.getInstance().get(params, listener);
    }

    /**
     * 买单订单
     *
     * @param page
     * @param listener
     */
    public static void requestBuyOrder(int page, AppRequestCallback<BuyOrderActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_store_pay_order");
        params.putAct("wap_index");
        params.put("page", page);
        AppHttpUtil.getInstance().get(params, listener);
    }

    /**
     * 订单退款列表
     *
     * @param page
     * @param listener
     */
    public static void requestOrderRefundList(int page, AppRequestCallback<OrderRefundListModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("refund_list");
        params.put("page", page);
        AppHttpUtil.getInstance().get(params, listener);
    }

    /**
     * 订单退款详情
     *
     * @param listener
     */
    public static void requestOrderRefundDetail(String mid, String id, AppRequestCallback<OrderRefundDetailModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("refund_view");
        params.put("data_id", mid);
        if (id != null && id.length() > 0)
        {
            params.put("did", id);
        }
        AppHttpUtil.getInstance().get(params, listener);
    }

    /**
     * 附件好店
     *
     * @param page
     * @param cate_id
     * @param qid
     * @param order_type
     * @param keyword
     * @param listener
     */
    public static void requestStoreWapIndex(int page, int cate_id, int qid, String order_type, String keyword, AppRequestCallback<AppStoresIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("stores");
        params.putAct("wap_index");
        params.put("page", page);
        if (cate_id > 0)
            params.put("cate_id", cate_id);// 分类ID
        if (qid > 0)
            params.put("qid", qid);//地区id
        params.put("order_type", order_type);
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 商品列表
     *
     * @param page
     * @param cate_id
     * @param bid
     * @param order_type
     * @param keyword
     * @param listener
     */
    public static void requestGoodsWapIndex(int page, int cate_id, String bid, String order_type, String keyword, AppRequestCallback<AppGoodsWapIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("goods");
        params.putAct("wap_index");
        params.put("page", page);
        if (cate_id > 0)
            params.put("cate_id", cate_id);// 分类ID
        if (!TextUtils.isEmpty(bid) && !bid.equals("0"))
            params.put("bid", bid);//品牌id
        if (!TextUtils.isEmpty(order_type))
            params.put("order_type", order_type);//价格升降序 销量
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 活动列表接口
     *
     * @param page
     * @param cate_id
     * @param keyword
     * @param listener
     */
    public static void requestActivitesWapIndex(int page, String cate_id, String keyword, AppRequestCallback<AppActivitesActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("events");
        params.putAct("wap_index");
        params.put("page", page);
        if (!TextUtils.isEmpty(cate_id))
            params.put("cate_id", cate_id);
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 图片上传(通用返回服务器路径)
     *
     * @param file     图片
     * @param listener
     */
    public static void requestUploadImage(File file, AppRequestCallback<UploadImageActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_account");
        params.putAct("upload_avatar");
        params.putFile("file", file, "image/jpg", "image_head.jpg");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 退款商品列表
     */
    public static void requestRefundGoodsList(String dataId,
                                              AppRequestCallback<RefundGoodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("order_refund");
        if (!TextUtils.isEmpty(dataId))
        {
            params.put("data_id", dataId);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 消费券列表
     *
     * @param page
     * @param coupon_status 0-团购，1-自提 2-取货
     * @param order_id      订单详情页查看消费券时使用
     * @param listener
     */
    public static <T extends BaseActModel> void requestConsumeCouponList(int page, String coupon_status, String order_id,
                                                                         AppRequestCallback<T> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_coupon");
        params.putAct("wap_index");
        params.put("page", page);
        params.put("coupon_status", coupon_status);
        if (!TextUtils.isEmpty(order_id))
        {
            params.put("order_id", order_id);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 团购列表接口
     *
     * @param page
     * @param cate_id
     * @param qid
     * @param order_type
     * @param keyword
     * @param listener
     */
    public static void requestGroupPurListIndex(int page, int tid, int cate_id, int qid, String order_type, String keyword, AppRequestCallback<AppGroupPurListIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("tuan");
        params.putAct("index_v2");
        params.put("page", page);
        if (tid > 0)
            params.put("tid", tid);// 大分类ID
        if (cate_id > 0)
            params.put("cate_id", cate_id);// 小分类ID
        if (qid > 0)
            params.put("qid", qid);//地区id
        params.put("order_type", order_type);
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 收货地址列表接口
     *
     * @param listener
     */
    public static void requestShippingAddressListIndex(AppRequestCallback<AppGroupPurListIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_address");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 新增收货地址接口
     *
     * @param consignee
     * @param is_default
     * @param mobile
     * @param address
     * @param doorplate
     * @param region_lv1
     * @param region_lv2
     * @param region_lv3
     * @param region_lv4
     * @param street
     * @param xpoint
     * @param ypoint
     * @param listener
     */
    public static void requestAddShippingAddressListIndex(String address, int is_default, String consignee, String mobile, String street, String doorplate,
                                                          int region_lv1, int region_lv2, int region_lv3, int region_lv4, double xpoint, double ypoint, AppRequestCallback<AppGroupPurListIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_address");
        params.putAct("save");
        params.put("address", address);
        params.put("is_default", is_default);
        params.put("consignee", consignee);
        params.put("mobile", mobile);
        params.put("street", street);
        params.put("doorplate", doorplate);
        params.put("region_lv1", region_lv1);
        params.put("region_lv2", region_lv2);
        params.put("region_lv3", region_lv3);
        params.put("region_lv4", region_lv4);
        params.put("xpoint", xpoint);
        params.put("ypoint", ypoint);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 编辑收货地址接口
     *
     * @param consignee
     * @param id
     * @param is_default
     * @param mobile
     * @param address
     * @param doorplate
     * @param region_lv1
     * @param region_lv2
     * @param region_lv3
     * @param region_lv4
     * @param street
     * @param xpoint
     * @param ypoint
     * @param listener
     */
    public static void requestEditShippingAddressListIndex(String address, int id, int is_default, String consignee, String mobile, String street, String doorplate,
                                                           int region_lv1, int region_lv2, int region_lv3, int region_lv4, double xpoint, double ypoint, AppRequestCallback<AppGroupPurListIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_address");
        params.putAct("save");
        params.put("address", address);
        params.put("is_default", is_default);
        params.put("consignee", consignee);
        params.put("mobile", mobile);
        params.put("street", street);
        params.put("doorplate", doorplate);
        params.put("region_lv1", region_lv1);
        params.put("region_lv2", region_lv2);
        params.put("region_lv3", region_lv3);
        params.put("region_lv4", region_lv4);
        params.put("xpoint", xpoint);
        params.put("ypoint", ypoint);
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除收货地址接口
     *
     * @param id
     * @param listener
     */
    public static void requestDeleteShippingAddressListIndex(int id, AppRequestCallback<AppGroupPurListIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_address");
        params.putAct("del");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 设置默认收货地址接口
     *
     * @param id
     * @param listener
     */
    public static void requestDefaultShippingAddressListIndex(int id, AppRequestCallback<AppGroupPurListIndexActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_address");
        params.putAct("set_default");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 订单列表接口
     *
     * @param page       分页
     * @param pay_status 状态
     */
    public static void requestUcOrderWapIndex(int page, int pay_status, AppRequestCallback<UcOrderActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
//        params.putAct("wap_index");
        params.putAct("index");
        params.put("page", page);
        params.put("pay_status", pay_status);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 订单详情接口
     *
     * @param data_id 订单id
     */
    public static void requestUcOrderWapView(String data_id, AppRequestCallback<UcOrderWapViewActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("wap_view");
        params.put("data_id", data_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 评论列表
     *
     * @param id 订单id
     */
    public static void requestUcOrderOrderDp(String id, AppRequestCallback<UcOrderOrderDpActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("order_dp");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 发表评论
     */
    public static void requestUcOrderOrderDpDo(String order_id, String item_id, String content, String point, AppRequestCallback<UcOrderOrderDpActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("order_dp_do");
        params.put("order_id", order_id);
        params.put("item_id", item_id);
        params.put("content", content);
        params.put("point", point);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 我的收藏
     *
     * @param sc_status
     * @param listener
     * @param page
     */
    public static void requestKeepListIndex(int page, int sc_status, AppRequestCallback<AppKeepListActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_collect");
        params.putAct("wap_index");
        params.put("sc_status", sc_status);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 取消收藏
     *
     * @param listener
     * @param id
     * @param type
     */
    public static void requestCancelKeepIndex(String id, String type, AppRequestCallback<AppKeepListActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_collect");
        params.putAct("del_collect");
        params.put("type", type);
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 获取地区
     */
    public static void requestDeliveryRegion(AppRequestCallback<App_RegionListActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("delivery_region");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 取消订单
     *
     * @param id
     * @param is_cancel 1:取消订单  0：删除订单
     * @param listener
     */
    public static void requestUcOrderCancel(String id, int is_cancel, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("cancel");
        params.put("id", id);
        params.put("is_cancel", is_cancel);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 退款申请列表
     *
     * @param deal_id 订单id
     */
    public static void requestUcOrderRefund(String deal_id, String coupon_id, AppRequestCallback<RefundRequestActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("refund");
        params.put("deal_id", deal_id);
        params.put("coupon_id", coupon_id);
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 退款申请
     *
     * @param deal_id 订单id
     */
    public static void requestUcOrderDoRefund(String deal_id, String coupon_id, String content, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_order");
        params.putAct("do_refund");
        params.put("deal_id", deal_id);
        params.put("coupon_id", coupon_id);
        params.put("content", content);
        AppHttpUtil.getInstance().post(params, listener);
    }

    public static void reuqestHotSearch(AppRequestCallback<HotKeyModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("search");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 消息列表
     *
     * @param type 消息类型
     */
    public static void requestUcMsgCate(String type, AppRequestCallback<UcMsgCateActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("uc_msg");
        params.putAct("cate");
        params.put("msgType", type);
        AppHttpUtil.getInstance().post(params, listener);
    }
}

