package com.fanwe.o2o.work;


import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDCache;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AccountManageAcitivty;
import com.fanwe.o2o.activity.ActivitiesListActivity;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.ConsumeCouponActivity;
import com.fanwe.o2o.activity.CouponListActivity;
import com.fanwe.o2o.activity.CouponsListActivity;
import com.fanwe.o2o.activity.GoodsRecyclerActivity;
import com.fanwe.o2o.activity.GroupPurchaseActivity;
import com.fanwe.o2o.activity.GroupPurchaseListActivity;
import com.fanwe.o2o.activity.LoginActivity;
import com.fanwe.o2o.activity.MainActivity;
import com.fanwe.o2o.activity.MallMainActivity;
import com.fanwe.o2o.activity.OrderDetailsActivity;
import com.fanwe.o2o.activity.OrderListActivity;
import com.fanwe.o2o.activity.PushEvaluateActivity;
import com.fanwe.o2o.activity.RefundGoodsActivity;
import com.fanwe.o2o.activity.StoresListActivity;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.dao.InitActModelDao;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.event.EChangeCity;
import com.fanwe.o2o.event.EIntentAppMain;
import com.fanwe.o2o.event.EIntentDiscover;
import com.fanwe.o2o.event.EIntentUserCenter;
import com.fanwe.o2o.event.EOrderListRefresh;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AdvsDataModel;
import com.fanwe.o2o.model.App_RegionListActModel;
import com.fanwe.o2o.model.BaseActModel;
import com.fanwe.o2o.model.CityModel;
import com.fanwe.o2o.model.Init_indexActModel;
import com.fanwe.o2o.model.LocalUserModel;
import com.sunday.eventbus.SDEventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fanwe.o2o.activity.LoginActivity.EXTRA_WEB_URL;

public class AppRuntimeWorker
{
    public static final String HASCITYLIST = "hascitylist";

    public static String getApiUrl(String ctl, String act)
    {
        return ApkConstant.SERVER_URL_API;
    }

    public static boolean isLogin()
    {
        return isLogin(null);
    }

    public static boolean isLogin(Activity activity)
    {
        if (LocalUserModelDao.queryModel() == null)
        {
            if (activity != null)
            {
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
            return false;
        } else
        {
            return true;
        }
    }

    public static boolean isLogin(Activity activity, String webUrl)
    {
        if (LocalUserModelDao.queryModel() == null)
        {
            if (activity != null)
            {
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.putExtra(EXTRA_WEB_URL, webUrl);
                activity.startActivity(intent);
            }
            return false;
        } else
        {
            return true;
        }
    }

    public static Constant.EnumLoginState getLoginState()
    {
        Constant.EnumLoginState state = null;

        LocalUserModel user = LocalUserModelDao.queryModel();
        if (user != null)
        {

            String mobile = user.getUser_mobile();
            if (user.getIs_tmp() == 1)
            {
                if (TextUtils.isEmpty(mobile))
                {
                    // TODO 绑定手机号
                    state = Constant.EnumLoginState.LOGIN_NEED_BIND_PHONE;
                } else
                {
                    state = Constant.EnumLoginState.LOGIN_NEED_VALIDATE;
                }
            } else
            {
                if (TextUtils.isEmpty(mobile))
                {
                    state = Constant.EnumLoginState.LOGIN_EMPTY_PHONE;
                } else
                {
                    state = Constant.EnumLoginState.LOGIN_NEED_VALIDATE;
                }
            }
        } else
        {
            state = Constant.EnumLoginState.UN_LOGIN;
        }
        return state;
    }


    /**
     * 获得当前城市id
     *
     * @return
     */
    public static String getCity_id()
    {
        String cityId = "0";
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            cityId = model.getCity_id();
        }
        return cityId;
    }

    /**
     * 获得当前城市
     *
     * @return
     */
    public static String getCity_name()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getCity_name();
            return content;
        }
        return "";
    }

    /**
     * 设置当前城市，也会设置当前城市id
     *
     * @param cityName
     * @return
     */
    public static boolean setCity_name(String cityName)
    {
        int cityId = getCityIdByCityName(cityName);
        Init_indexActModel model = getInitActModel();
        model.setCity_name(cityName);
        model.setCity_id(cityId + "");
        boolean insertSuccess = InitActModelDao.insertOrUpdate(model);
        if (insertSuccess)
        {
            SDEventManager.post(new EChangeCity());
        }
        return insertSuccess;
    }

    /**
     * 根据城市名字获得城市id，如果未找到返回-1
     *
     * @return
     */
    public static int getCityIdByCityName(String cityName)
    {
        int cityId = -1;
        if (!TextUtils.isEmpty(cityName))
        {
            List<CityModel> listCity = getCitylist();
            if (listCity != null && listCity.size() > 0)
            {
                CityModel cityModel = null;
                for (int i = 0; i < listCity.size(); i++)
                {
                    cityModel = listCity.get(i);
                    if (cityModel != null)
                    {
                        if (cityName.equals(cityModel.getName()))
                        {
                            cityId = cityModel.getId();
                            break;
                        }
                    }
                }
            }
        }
        return cityId;
    }

    /**
     * 获得城市列表
     *
     * @return
     */
    public static List<CityModel> getCitylist()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            List<CityModel> content = model.getCitylist();
            return content;
        }
        return null;
    }

    /**
     * 获得城市列表
     *
     * @return
     */
    public static List<CityModel> getContainsCityList(String value)
    {
        List<CityModel> list = new ArrayList<CityModel>();
        if (!TextUtils.isEmpty(value))
        {
            for (int i = 0; i < getCitylist().size(); i++)
            {
                if (AppRuntimeWorker.getCitylist().get(i).getName().contains(value))
                {
                    CityModel model = new CityModel();
                    model.setName(AppRuntimeWorker.getCitylist().get(i).getName());
                    model.setId(AppRuntimeWorker.getCitylist().get(i).getId());
                    model.setPy(AppRuntimeWorker.getCitylist().get(i).getPy());
                    list.add(model);
                }
            }
        }
        return list;
    }

    /**
     * 获得热门城市列表
     *
     * @return
     */
    public static List<CityModel> getCitylistHot()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            List<CityModel> content = model.getHot_city();
            return content;
        }
        return null;
    }

    public static Init_indexActModel getInitActModel()
    {
        return InitActModelDao.query();
    }

    public static Intent createIntentByType(int type, String url, String data, int cate_id)
    {
        Intent intent = null;
        switch (type)
        {
            case Constant.IndexType.URL:
                if (!TextUtils.isEmpty(url))
                {
                    intent = new Intent(App.getApplication(), AppWebViewActivity.class);
                    intent.putExtra(AppWebViewActivity.EXTRA_URL, new UrlLinkBuilder(url).add("from", "app").add("r_type", "5").add("page_finsh", "1").build());
                }
                break;
            case Constant.IndexType.MALL_MAIN:
                intent = new Intent(App.getApplication(), MallMainActivity.class);
                break;
            case Constant.IndexType.TUAN_MAIN:
                intent = new Intent(App.getApplication(), GroupPurchaseActivity.class);
                break;
            case Constant.IndexType.GROUP_PURCHASE_LIST:
                intent = new Intent(App.getApplication(), GroupPurchaseListActivity.class);
                intent.putExtra(GroupPurchaseListActivity.EXTRA_CATE_ID, cate_id);
                break;
            case Constant.IndexType.GOODS_LIST:
                intent = new Intent(App.getApplication(), GoodsRecyclerActivity.class);
                intent.putExtra(GoodsRecyclerActivity.EXTRA_BIG_ID, cate_id);
                break;
            case Constant.IndexType.ACTIVITIES_LIST:
                intent = new Intent(App.getApplication(), ActivitiesListActivity.class);
                break;
            case Constant.IndexType.COUPONS_LIST:
                intent = new Intent(App.getApplication(), CouponsListActivity.class);
                intent.putExtra(CouponsListActivity.EXTRA_CATE_ID, cate_id);
                break;
            case Constant.IndexType.STORES_LIST:
                intent = new Intent(App.getApplication(), StoresListActivity.class);
                intent.putExtra(StoresListActivity.EXTRA_CATE_ID, cate_id);
                break;
            case Constant.IndexType.ACCOUNT_MANAGE:
                intent = new Intent(App.getApplication(), AccountManageAcitivty.class);
                break;
            case Constant.IndexType.USER_YOUHUI:
                intent = new Intent(App.getApplication(), CouponListActivity.class);
                break;
            case Constant.IndexType.USER_COUPONS:
                intent = new Intent(App.getApplication(), ConsumeCouponActivity.class);
                break;
            case Constant.IndexType.ORDER_DETAILS:
                intent = new Intent(App.getApplication(), OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.EXTRA_ID, data);
                break;
            case Constant.IndexType.APP_MAIN:
                SDEventManager.post(new EIntentAppMain());
                intent = new Intent(App.getApplication(), MainActivity.class);
                break;
            case Constant.IndexType.USER_CENTER:
                SDEventManager.post(new EIntentUserCenter());
                intent = new Intent(App.getApplication(), MainActivity.class);
                break;
            case Constant.IndexType.DISCOVER:
                SDEventManager.post(new EIntentDiscover());
                intent = new Intent(App.getApplication(), MainActivity.class);
                break;
            default:
                if (!TextUtils.isEmpty(url))
                {
                    intent = new Intent(App.getApplication(), AppWebViewActivity.class);
                    intent.putExtra(AppWebViewActivity.EXTRA_URL, new UrlLinkBuilder(url).add("from", "app").add("r_type", "5").add("page_finsh", "1").build());
                }
                break;
        }
        return intent;
    }


//    public static Intent createIntentByType(int type, int cate_id)
//    {
//        Intent intent = null;
//        switch (type)
//        {
//            case Constant.IndexType.GROUP_PURCHASE_LIST:
//                intent = new Intent(App.getApplication(), GroupPurchaseListActivity.class);
//                intent.putExtra(GroupPurchaseListActivity.EXTRA_CATE_ID,cate_id);
//                break;
//            case Constant.IndexType.GOODS_LIST:
//                intent = new Intent(App.getApplication(), GoodsRecyclerActivity.class);
//                intent.putExtra(GoodsRecyclerActivity.EXTRA_CATE_ID,cate_id);
//                break;
//            default:
//                break;
//        }
//        return intent;
//    }

    public static Intent createIntentByType(int type, AdvsDataModel data, boolean createDefaultIntent)
    {
        Intent intent = null;
        switch (type)
        {
            case Constant.IndexType.URL:
                if (data != null && !TextUtils.isEmpty(data.getUrl()))
                {
                    intent = new Intent(App.getApplication(), AppWebViewActivity.class);
                    intent.putExtra(AppWebViewActivity.EXTRA_URL, data.getUrl());
                }
                break;
//            case IndexType.NOTICE_LIST:
//                intent = new Intent(App.getApplication(), NoticeListActivity.class);
//                break;
//            case IndexType.STORE_PAY_LIST:
//                intent = new Intent(App.getApplication(), StorePayListActivity.class);
//                if (data != null)
//                {
//                    intent.putExtra(StorePayListFragment.EXTRA_CATE_ID, data.getCate_id());
//                    intent.putExtra(StorePayListFragment.EXTRA_TID, data.getTid());
//                    intent.putExtra(StorePayListFragment.EXTRA_QID, data.getQid());
//                }
//                break;
//            case IndexType.DEAL_DETAIL:
//                if (data != null)
//                {
//                    intent = new Intent(App.getApplication(), TuanDetailActivity.class);
//                    intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, data.getData_id());
//                }
//                break;
//            case IndexType.EVENT_DETAIL:
//                if (data != null)
//                {
//                    intent = new Intent(App.getApplication(), EventDetailActivity.class);
//                    intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, data.getData_id());
//                }
//                break;
//            case IndexType.YOUHUI_DETAIL:
//                if (data != null)
//                {
//                    intent = new Intent(App.getApplication(), YouHuiDetailActivity.class);
//                    intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, data.getData_id());
//                }
//                break;
//            case IndexType.STORE_DETAIL:
//                if (data != null)
//                {
//                    intent = new Intent(App.getApplication(), StoreDetailActivity.class);
//                    intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, data.getData_id());
//                }
//                break;
//            case IndexType.NOTICE_DETAIL:
//                if (data != null)
//                {
//                    intent = new Intent(App.getApplication(), NoticeDetailActivity.class);
//                    intent.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, data.getData_id());
//                }
//                break;
//            case IndexType.SCAN:
//                SDEventManager.post(SDActivityManager.getInstance().getLastActivity().getClass(), EnumEventTag.START_SCAN_QRCODE.ordinal());
//                break;
//            case IndexType.NEARUSER:
//                intent = new Intent(App.getApplication(), NearbyVipActivity.class);
//                break;
//            case IndexType.DISTRIBUTION_STORE:
//                intent = new Intent(App.getApplication(), DistributionStoreWapActivity.class);
//                break;
//            case IndexType.DISTRIBUTION_MANAGER:
//                intent = new Intent(App.getApplication(), DistributionManageActivity.class);
//                break;
//            case IndexType.DISCOVERY:
//                intent = new Intent(App.getApplication(), DiscoveryActivity.class);
//                break;
//            case IndexType.TAKEAWAY_LIST:
//                // TODO 跳到外卖主界面，定位到外卖列表
//                // intent = new Intent(App.getApplication(), MainActivity_dc.class);
//                // intent.putExtra(MainActivity_dc.EXTRA_SELECT_INDEX, 0);
//                break;
//            case IndexType.RESERVATION_LIST:
//                // TODO 跳到外卖主界面，定位到订座列表
//                // intent = new Intent(App.getApplication(), MainActivity_dc.class);
//                // intent.putExtra(MainActivity_dc.EXTRA_SELECT_INDEX, 1);
//                break;
            default:
//                if (createDefaultIntent)
//                {
//                    intent = new Intent(App.getApplication(), .class);
//                }
                break;
        }
        return intent;
    }


    public static String getSina_app_key()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getSina_app_key();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    public static String getSina_app_secret()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getSina_app_secret();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    public static String getSina_redirectUrl()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getSina_bind_url();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    public static String getQq_app_key()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getQq_app_key();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    public static String getQq_app_secret()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getQq_app_secret();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    public static String getWx_app_key()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getWx_app_key();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    public static String getWx_app_secret()
    {
        Init_indexActModel model = getInitActModel();
        if (model != null)
        {
            String content = model.getWx_app_secret();
            if (!TextUtils.isEmpty(content))
            {
                return content;
            }
        }
        return "";
    }

    /**
     * @param type          按钮类型
     * @param id            订单ID
     * @param url           跳转web的URL
     * @param coupon_status 消费券的类型（0：团购；1：自提）
     */
    public static void clickOrderBtn(String type, final String id, String url, int coupon_status)
    {
        Intent intent = null;
        switch (type)
        {
            case Constant.OrderBtnType.LOGISTICS_GOODSRECEIPT:
                //物流&收货
                intent = new Intent(App.getApplication(), AppWebViewActivity.class);
                intent.putExtra(AppWebViewActivity.EXTRA_URL, new UrlLinkBuilder(ApkConstant.SERVER_URL_SCHEMES + ApkConstant.SERVER_URL_DOMAIN + url).build());
                SDActivityManager.getInstance().getLastActivity().startActivity(intent);
                break;
            case Constant.OrderBtnType.COUPON:
                //查看消费券
                intent = new Intent(App.getApplication(), ConsumeCouponActivity.class);
                intent.putExtra(ConsumeCouponActivity.EXTRA_COUPON_STATUS, coupon_status);
                intent.putExtra(ConsumeCouponActivity.EXTRA_ID, id);
                SDActivityManager.getInstance().getLastActivity().startActivity(intent);
                break;
            case Constant.OrderBtnType.DP:
                //评价
                intent = new Intent(App.getApplication(), PushEvaluateActivity.class);
                intent.putExtra(PushEvaluateActivity.EXTRA_ID, id);
                SDActivityManager.getInstance().getLastActivity().startActivity(intent);
                break;
            case Constant.OrderBtnType.DEL:
                //删除订单
                SDDialogConfirm deleteDialog = new SDDialogConfirm(SDActivityManager.getInstance().getLastActivity());
                deleteDialog.setTextContent("确认删除订单？");
                deleteDialog.setTextGravity(Gravity.CENTER);
                deleteDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
                {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog)
                    {

                    }

                    @Override
                    public void onClickConfirm(View v, SDDialogCustom dialog)
                    {
                        requestUcOrderCancel(id, 0);
                    }

                    @Override
                    public void onDismiss(SDDialogCustom dialog)
                    {

                    }
                });
                deleteDialog.showCenter();
                break;
            case Constant.OrderBtnType.PAYMENT:
                //去支付
                intent = new Intent(App.getApplication(), AppWebViewActivity.class);
                intent.putExtra(AppWebViewActivity.EXTRA_URL, new UrlLinkBuilder(ApkConstant.SERVER_URL_SCHEMES + ApkConstant.SERVER_URL_DOMAIN + url).build());
                SDActivityManager.getInstance().getLastActivity().startActivity(intent);
                break;
            case Constant.OrderBtnType.CANCEL:
                //取消订单
                SDDialogConfirm cancelDialog = new SDDialogConfirm(SDActivityManager.getInstance().getLastActivity());
                cancelDialog.setTextContent("确认取消订单？");
                cancelDialog.setTextGravity(Gravity.CENTER);
                cancelDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
                {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog)
                    {

                    }

                    @Override
                    public void onClickConfirm(View v, SDDialogCustom dialog)
                    {
                        requestUcOrderCancel(id, 1);
                    }

                    @Override
                    public void onDismiss(SDDialogCustom dialog)
                    {

                    }
                });
                cancelDialog.showCenter();
                break;
            case Constant.OrderBtnType.REFUND:
                //退款
                Intent intent1 = new Intent(App.getApplication(), RefundGoodsActivity.class);
                intent1.putExtra(RefundGoodsActivity.DATA_ID, id);
                SDActivityManager.getInstance().getLastActivity().startActivity(intent1);
                break;
            default:
                break;
        }
    }

    /**
     * 取消/删除订单
     */
    public static void requestUcOrderCancel(String id, final int is_cancel)
    {
        CommonInterface.requestUcOrderCancel(id, is_cancel, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    if (is_cancel == 1)
                    {
                        SDToast.showToast("取消订单成功");
                    } else
                    {
                        SDToast.showToast("删除订单成功");
                    }
//                        ERefreshOrderList event = new ERefreshOrderList();
//                        SDEventManager.post(event);
                    if (SDActivityManager.getInstance().getLastActivity().getClass() == OrderDetailsActivity.class)
                    {
                        ((OrderDetailsActivity) SDActivityManager.getInstance().getLastActivity()).setNeedRefreshList(true);
                        ((OrderDetailsActivity)SDActivityManager.getInstance().getLastActivity()).requestData();
                    }
                    if (SDActivityManager.getInstance().getLastActivity().getClass() == OrderListActivity.class)
                    {
                        EOrderListRefresh event = new EOrderListRefresh();
                        SDEventManager.post(event);
                        ((OrderListActivity) SDActivityManager.getInstance()
                                .getLastActivity()).setOrder_type(0);
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
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
     * 打开wap页面
     *  @param activity
     * @param ctl
     * @param act
     * @param params
     */
    public static void jump2Wap(Activity activity, String ctl, String act, Map<String, String> params)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(ApkConstant.SERVER_URL_WAP);
        if (TextUtils.isEmpty(ctl)||activity==null)
        {
            return;
        }
        sb.append("?ctl=").append(ctl);

        if (!TextUtils.isEmpty(act))
        {
            sb.append("&act=").append(act);
        }
        if (params != null && params.size() > 0)
        {
            for (String key : params.keySet())
            {
                sb.append("&").append(key).append("=").append(params.get(key));
            }
        }

        String url = String.valueOf(sb);
        Intent intent2 = new Intent(activity, AppWebViewActivity.class);
        intent2.putExtra(AppWebViewActivity.EXTRA_URL, url);
        activity.startActivity(intent2);
    }


    public static App_RegionListActModel getRegionListActModel()
    {
        App_RegionListActModel actModel = null;
        int localVersion = SDConfig.getInstance().getInt(R.string.config_region_version, 0);
//        if (localVersion != 0)
//        {
//            InitActModel initActModel = InitActModelDao.query();
//            if (initActModel != null)
//            {
//                if (initActModel.getRegion_versions() > localVersion)
//                {
//                    需要升级
//                } else
//                {
        actModel = SDCache.getObject(App_RegionListActModel.class);
//                }
//            }
//        }
        return actModel;
    }
}
