package com.fanwe.o2o.jshandler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.handler.js.BaseJsHandler;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDImageUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AccountManageAcitivty;
import com.fanwe.o2o.activity.ActivitiesListActivity;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.activity.BuyOrderActivity;
import com.fanwe.o2o.activity.CouponsListActivity;
import com.fanwe.o2o.activity.GoodsRecyclerActivity;
import com.fanwe.o2o.activity.GroupPurchaseActivity;
import com.fanwe.o2o.activity.GroupPurchaseListActivity;
import com.fanwe.o2o.activity.MainActivity;
import com.fanwe.o2o.activity.MallMainActivity;
import com.fanwe.o2o.activity.MessageCenterActivity;
import com.fanwe.o2o.activity.OrderDetailsActivity;
import com.fanwe.o2o.activity.OrderListActivity;
import com.fanwe.o2o.activity.PushEvaluateActivity;
import com.fanwe.o2o.activity.SearchActivity;
import com.fanwe.o2o.activity.StoresListActivity;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.event.EIntentAppMain;
import com.fanwe.o2o.event.EIntentUserCenter;
import com.fanwe.o2o.event.ERefreshReload;
import com.fanwe.o2o.event.EUnLogin;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.listener.listenerimpl.XNSDKListenerImpl;
import com.fanwe.o2o.model.AppUserLoginOutActModel;
import com.fanwe.o2o.model.JSOrderDetailModel;
import com.fanwe.o2o.model.PaySdkModel;
import com.fanwe.o2o.model.ShareModel;
import com.fanwe.o2o.model.WapPayMethod;
import com.fanwe.o2o.model.XiaoNengGoodsModel;
import com.fanwe.o2o.umeng.UmengSocialManager;
import com.fanwe.o2o.utils.GlideUtil;
import com.fanwe.o2o.utils.JsonUtil;
import com.fanwe.o2o.utils.StorageFileUtils;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

import java.io.File;

import cn.xiaoneng.coreapi.ChatParamsBody;
import cn.xiaoneng.uiapi.Ntalker;
import cn.xiaoneng.utils.CoreData;
import de.greenrobot.event.EventBus;

/**
 * app详情页js回调处理类
 *
 * @author Administrator
 */
public class AppJsHandler extends BaseJsHandler
{
    private static final String DEFAULT_NAME = "App";
    private String url;

    public AppJsHandler(String name, Activity activity)
    {
        super(name, activity);
    }

    public AppJsHandler(Activity activity)
    {
        this(DEFAULT_NAME, activity);
    }

    @JavascriptInterface
    public void check_network()
    {
        openNetwork(getActivity());
    }

    @JavascriptInterface
    public void refresh_reload()
    {
        ERefreshReload event = new ERefreshReload();
        SDEventManager.post(event);
    }

    @JavascriptInterface
    public void app_refresh()
    {
        ERefreshReload event = new ERefreshReload();
        SDEventManager.post(event);
    }

    /**
     * 打开小能客服聊天窗口
     * @param data
     */
    @JavascriptInterface
    public void xnOpenSdk(String data)
    {
        if (TextUtils.isEmpty(data))
        {
            SDToast.showToast("获取商品信息失败");
            return;
        }
        XiaoNengGoodsModel model = SDJsonUtil.json2Object(data, XiaoNengGoodsModel.class);

        ChatParamsBody chatparams = new ChatParamsBody();

//**** 咨询发起页，用于PC客服端显示用户发起咨询的位置 统计和显示访客首次发起咨询时候的入口名称，如果是商品页面可以填入商品名和商品的地址

        chatparams.startPageTitle = model.getGoodsTitle();  // 咨询发起页标题(必填)
        chatparams.startPageUrl = model.getGoods_URL();//咨询发起页URL，必须以"http://"开头 （必填）

//**** 域名匹配,企业特殊需求********
//        chatparams.matchstr = "http://img.meicicdn.com";//默认传空

//****erp参数, 被用参数,小能只负责经由SDK传到客服端,不做任何修改,处理*******
        chatparams.erpParam = "";

//***** 商品详情,如果页面含有商品,建议传入,用于sdk访客端和PC客服显示当前商品******

        chatparams.itemparams.appgoodsinfo_type = CoreData.SHOW_GOODS_BY_WIDGET; //sdk显示商品信息模式. 建议使用id模式
        //  值0: 设置在SDK端不显示商品(默认)
        //  值1: 设置在SDK端以商品ID显示商品
        //  值2: 设置在SDK端以商品URL显示商品
        //  值3: 设置在SDK端以独立控件显示商品
                chatparams.itemparams.clientgoodsinfo_type = CoreData.SHOW_GOODS_BY_ID; // pc端显示商品信息模式
//                // 值0: 设置在PC客服端不显示商品
//                // 值1: 设置在PC客服端以商品ID显示商品
//                // 值2: 设置在PC客服端以商品URL显示商品
        chatparams.itemparams.clicktoshow_type = CoreData.CLICK_TO_APP_COMPONENT;// 点击SDK商品详情,
        chatparams.clickurltoshow_type = CoreData.CLICK_TO_APP_COMPONENT;
        // 值0: 点击商品详情在SDK内打开(默认)
        // 值1: 点击商品详情在APP内打开URL

//使用id模式
        chatparams.itemparams.itemparam = "";//用于区分PC和移动端商品存在差价问题,其他模式不支持
        chatparams.itemparams.goods_id = String.valueOf(model.getGoods_id());//  商品ID ,贵公司提供

        String price = model.getGoodsPrice();
        if (!TextUtils.isEmpty(price))
        {
            if (price.contains("¥"))
            {
                chatparams.itemparams.goods_price = model.getGoodsPrice();
            } else
            {
                chatparams.itemparams.goods_price = "¥ " + model.getGoodsPrice();
            }
        }

        chatparams.itemparams.goods_image = model.getGoods_showURL();
        chatparams.itemparams.goods_name = model.getGoodsTitle();
        chatparams.itemparams.goods_url = model.getGoods_URL();
//        chatparams.itemparams.goods_showurl = model.getGoods_showURL();

        Ntalker.getInstance().setSDKListener(new XNSDKListenerImpl(getActivity()));

        //settingid  md_198_1496913879749
        Ntalker.getInstance().startChat(getActivity(), model.getSettingid(), null, null, null, chatparams);

    }

    /**
     * 打开WIFI设置界面
     */
    public static void openNetwork(final Activity context)
    {

        SDDialogConfirm dialogConfirm = new SDDialogConfirm(context);
        dialogConfirm.setTextTitle("网络设置提示");
        dialogConfirm.setTextContent("网络连接不可用,是否进行设置?");
        dialogConfirm.setTextGravity(Gravity.CENTER);
        dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                dialog.dismiss();
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                context.startActivity(createNetWorkIntent());
            }

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
                dialog.dismiss();
            }
        });
        dialogConfirm.show();
    }

    /**
     * 使用系统默认的浏览器打开wap
     */
    public void jumpByDefaultChrome(String url)
    {
        if (TextUtils.isEmpty(url))
        {
            SDToast.showToast("URL不能为空");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 网络配置
     */
    public static Intent createNetWorkIntent()
    {
        Intent intent = null;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10)
        {
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        } else
        {
            intent = new Intent();
            ComponentName component =
                    new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        return intent;
    }

    /**
     * 支付时唤起的第三方wap支付页面启动方式
     */
    @JavascriptInterface
    public void open_type(String json)
    {
//        String s = json;
//        WapPayMethod wpm = JsonUtil.json2Object(json, WapPayMethod.class);
//        final String open_url_type = wpm.getOpen_url_type();
//        final String url = wpm.getUrl();
//        final String titile = wpm.getTitle();
//        if (TextUtils.isEmpty(open_url_type))
//        {
//            return;
//        }
//        //TODO 0打开带WebActicity的标题变为url,需要h5改
//        if (open_url_type.equals("0"))
//        {//0打开带WebActicity 1打开系统浏览器
//            Intent intent = new Intent(App.getApplication(), AppWebViewActivity.class);
//            intent.putExtra(AppWebViewActivity.EXTRA_TITLE, titile);
//            intent.putExtra(AppWebViewActivity.EXTRA_URL, url);
//            startActivity(intent);
//        } else if (open_url_type.equals("1"))
//        {
//            jumpByDefaultChrome(url);
//        }
    }

    /**
     * @param type 此为整形 若后台传String  则接收到的type会变成0  如"后台传308" 则接收到的 type=0  ios不会
     * @param data
     */
    @JavascriptInterface
    public void app_detail(int type, String data)
    {
        Intent intent = null;
        switch (type)
        {
            case Constant.IndexType.COMMENT:
                intent = new Intent(App.getApplication(), PushEvaluateActivity.class);
                intent.putExtra(PushEvaluateActivity.EXTRA_ID, data);
                startActivity(intent);
                break;
            case Constant.IndexType.URL:
                intent = new Intent(App.getApplication(), AppWebViewActivity.class);
                intent.putExtra(AppWebViewActivity.EXTRA_URL, data);
                startActivity(intent);

                if (!TextUtils.isEmpty(getUrl()) && getUrl().contains(
                        "ctl=cart"))
                { //若在上一个AppWebViewActivity打开购物车并且继续在该购物车中继续打开购物车进入结算页面,此时按返回会出现多次结算页面
                    getActivity().finish();
                }
                break;
            case Constant.IndexType.APP_MAIN:
                if (getActivity().getClass()
                        == MainActivity.class)
                { //防止活动栈里只有MainActivity时调用本方法导致"app闪退", 因为MainActivity为singleTask,当MainActivity再次入栈时会被自动移除 getActivity().finish()会直接清空栈;
                    return;
                }
                SDEventManager.post(new EIntentAppMain());
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.APP_MAIN);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
                getActivity().finish();
                break;
            case Constant.IndexType.MALL_MAIN:
                intent = new Intent(App.getApplication(), MallMainActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.BUY_ORDER:
                intent = new Intent(App.getApplication(), BuyOrderActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.TUAN_MAIN:
                intent = new Intent(App.getApplication(), GroupPurchaseActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.SCORE_MAIN:
                //                intent = new Intent(App.getApplication(), GroupPurchaseActivity.class);
                break;
            case Constant.IndexType.ACCOUNT_MANAGE:
                intent = new Intent(App.getApplication(), AccountManageAcitivty.class);
                startActivity(intent);
                break;
            case Constant.IndexType.DISCOVER:
                /**跳转首页的发现搜索**/
                //                SDEventManager.post(new EIntentDiscover());
                //                intent = new Intent(getActivity(), MainActivity.class);
                //                intent.putExtra(MainActivity.EXTRA_TAB,Constant.MainIntentTab.DISCOVER);
                //                startActivity(intent);
                //                getActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
                //                getActivity().finish();

                /**跳转二级页面的发现搜索**/
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.DISCOVER);
                getActivity().startActivity(intent);
//        getActivity().finish();
                break;
            case Constant.IndexType.SHOP_CART:
                //                SDEventManager.post(new EIntentShopCart());
                //                intent = new Intent(getActivity(), MainActivity.class);
                //                intent.putExtra(MainActivity.EXTRA_TAB,Constant.MainIntentTab.SHOP_CART);
                //                startActivity(intent);

                intent = new Intent(App.getApplication(), AppWebViewActivity.class);//wap
                String url = ApkConstant.SERVER_URL_WAP;
                UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
                urlBuilder.add("ctl", "cart");
                intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE, false);
                intent.putExtra(AppWebViewActivity.EXTRA_URL, urlBuilder.build());
                startActivity(intent);

                if (!TextUtils.isEmpty(getUrl()) && getUrl().contains(
                        "ctl=cart"))
                { //若在上一个AppWebViewActivity打开购物车并且继续在该购物车中继续打开购物车进入结算页面,此时按返回会出现多次结算页面
                    getActivity().finish();
                }
                break;
            case Constant.IndexType.USER_CENTER:
                SDEventManager.post(new EIntentUserCenter());
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_TAB, Constant.MainIntentTab.USER_CENTER);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_nothing, R.anim.anim_nothing);
                getActivity().finish();
                break;
            case Constant.IndexType.MSG_CENTER:
                intent = new Intent(getActivity(), MessageCenterActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.GROUP_PURCHASE_LIST:
                intent = new Intent(App.getApplication(), GroupPurchaseListActivity.class);
                if (!TextUtils.isEmpty(data))
                {
                    intent.putExtra(GroupPurchaseListActivity.EXTRA_CATE_ID, Integer.parseInt(data));
                }
                startActivity(intent);
                break;
            case Constant.IndexType.GOODS_LIST:
                intent = new Intent(App.getApplication(), GoodsRecyclerActivity.class);
                if (!TextUtils.isEmpty(data))
                {
                    intent.putExtra(GoodsRecyclerActivity.EXTRA_CATE_ID, Integer.parseInt(data));
                    intent.putExtra(GoodsRecyclerActivity.EXTRA_BIG_ID, -1);
                }
                startActivity(intent);
                break;
            case Constant.IndexType.ACTIVITIES_LIST:
                intent = new Intent(App.getApplication(), ActivitiesListActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.COUPONS_LIST:
                intent = new Intent(App.getApplication(), CouponsListActivity.class);
                if (!TextUtils.isEmpty(data))
                {
                    intent.putExtra(CouponsListActivity.EXTRA_CATE_ID, Integer.parseInt(data));
                }
                startActivity(intent);
                break;
            case Constant.IndexType.STORES_LIST:
                intent = new Intent(App.getApplication(), StoresListActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.ORDER_LIST:
                intent = new Intent(App.getApplication(), OrderListActivity.class);
                startActivity(intent);
                break;
            case Constant.IndexType.ORDER_DETAILS:

                intent = new Intent(App.getApplication(), OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.EXTRA_ID, data);
                break;
            case Constant.IndexType.ORDER_DETAILS2:
//                //jump2DealWap(getActivity(),"deal",)
//                SDActivityManager.getInstance().getLastActivity().finish(); //结束掉AppWebViewActivity
//                JSOrderDetailModel detailModel = JsonUtil.json2Object(data, JSOrderDetailModel.class);
//                String data_id = String.valueOf(detailModel.getData_id());
////        if()
//                intent = new Intent(App.getApplication(), OrderDetailsActivity.class);
//                intent.putExtra(OrderDetailsActivity.EXTRA_ID, data_id);
//                if (SDActivityManager.getInstance().getLastActivity() instanceof OrderDetailsActivity)
//                {  //若上个页面是订单详情则关闭上个页面
//                    SDActivityManager.getInstance().getLastActivity().finish();
//                }
//                SDActivityManager.getInstance().getLastActivity().startActivity(intent);
                break;
            //		case IndexType.STORE_PAY_LIST:
            //			intent = new Intent(App.getApplication(), StorePayListActivity.class);
            //			intent.putExtra(StorePayListFragment.EXTRA_CATE_ID, id);
            //			break;
            //		case IndexType.NOTICE_LIST:
            //			intent = new Intent(App.getApplication(), NoticeListActivity.class);
            //			break;
            //		case IndexType.DEAL_DETAIL:
            //			intent = new Intent(App.getApplication(), TuanDetailActivity.class);
            //			intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, id);
            //			break;
            //		case IndexType.EVENT_DETAIL:
            //			intent = new Intent(App.getApplication(), EventDetailActivity.class);
            //			intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, id);
            //			break;
            //		case IndexType.YOUHUI_DETAIL:
            //			intent = new Intent(App.getApplication(), YouHuiDetailActivity.class);
            //			intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, id);
            //			break;
            //		case IndexType.STORE_DETAIL:
            //			intent = new Intent(App.getApplication(), StoreDetailActivity.class);
            //			intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, id);
            //			break;
            //		case IndexType.NOTICE_DETAIL:
            //			intent = new Intent(App.getApplication(), NoticeDetailActivity.class);
            //			intent.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, id);
            //			break;
            //		case IndexType.SCAN:
            //			SDEventManager.post(SDActivityManager.getInstance().getLastActivity().getClass(), EnumEventTag.START_SCAN_QRCODE.ordinal());
            //			return;
            //		case IndexType.NEARUSER:
            //			intent = new Intent(App.getApplication(), NearbyVipActivity.class);
            //			break;
            //		case IndexType.DISTRIBUTION_STORE:
            //			intent = new Intent(App.getApplication(), DistributionStoreWapActivity.class);
            //			break;
            //		case IndexType.DISTRIBUTION_MANAGER:
            //			intent = new Intent(App.getApplication(), DistributionManageActivity.class);
            //			break;
            //		case IndexType.DISCOVERY:
            //			intent = new Intent(App.getApplication(), DiscoveryActivity.class);
            //			break;
            //		case IndexType.TAKEAWAY_LIST:
            //			// TODO 跳到外卖主界面，定位到外卖列表
            //			// intent = new Intent(App.getApplication(), MainActivity_dc.class);
            //			// intent.putExtra(MainActivity_dc.EXTRA_SELECT_INDEX, 0);
            //			break;
            //		case IndexType.RESERVATION_LIST:
            //			// TODO 跳到外卖主界面，定位到订座列表
            //			// intent = new Intent(App.getApplication(), MainActivity_dc.class);
            //			// intent.putExtra(MainActivity_dc.EXTRA_SELECT_INDEX, 1);
            //			break;
            default:

                break;
        }
    }

    @JavascriptInterface
    public void page_finsh()
    {
        finish();
    }

    @JavascriptInterface
    public void login_sdk()
    {
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        boolean isLogin = AppRuntimeWorker.isLogin(activity, url);
    }

    @JavascriptInterface
    public void third_party_login_sdk(String value)
    {
    }

    @JavascriptInterface
    public void logout()
    {
        CommonInterface.requestUserLoginOut(new AppRequestCallback<AppUserLoginOutActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    App.getApplication().clearAppsLocalUserModel();
                    SDEventManager.post(new EUnLogin());
                }
            }
        });
    }

    @JavascriptInterface
    public void page_title(String title)
    {

    }

    @JavascriptInterface
    public void sdk_share(final String data)
    {
        SDHandlerManager.getMainHandler().post(new Runnable()
        {
            @Override
            public void run()
            {
                ShareModel model = SDJsonUtil.json2Object(data, ShareModel.class);
                UmengSocialManager.openShare(model.getShare_title(), model.getShare_content(),
                        model.getShare_imageUrl(), model.getShare_url(), getActivity(), null);
            }
        });
    }

    @JavascriptInterface
    public void save_image(final String imageUrl)
    {
        SDHandlerManager.getMainHandler().post(new Runnable()
        {
            @Override
            public void run()
            {
                GlideUtil.loadHeadImage(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>()
                {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                GlideAnimation<? super Bitmap> glideAnimation)
                    {
                        long time = System.currentTimeMillis();
                        File dir = StorageFileUtils.createDirPackageName();
                        String dirPath = dir.getAbsolutePath();
                        String filename = File.separator + time + ".png";
                        SDImageUtil.dealImageCompress(dirPath, filename, bitmap, 100);
                        SDOtherUtil.scanFile(getActivity(), SDFileUtil.getCacheDir(getActivity(), dirPath));
                        SDToast.showToast("保存图片路径:" + dirPath + filename);
                    }
                });
            }
        });
    }

    /**
     * 头像
     */
    @JavascriptInterface
    public void CutPhoto()
    {

    }

    @JavascriptInterface
    public void pay_sdk(String json)
    {
        PaySdkModel model = JSON.parseObject(json, PaySdkModel.class);
        if (model != null)
        {
            EventBus.getDefault().post(new SDBaseEvent(model, Constant.EnumEvent.EVENT_PAY_SDK.ordinal()));
        }
    }

    /**
     * 定位
     */
    @JavascriptInterface
    public void position()
    {
    }

    /**
     * 跳转到参数中带 data_id的wap页面,如商品详情
     */
    public static void jump2DealWap(Context context, String ctl, String deal_id)
    {
        if (context == null)
        {
            return;
        }
        if (!TextUtils.isEmpty(ctl) && !TextUtils.isEmpty(deal_id))
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(ApkConstant.SERVER_URL_WAP)
                    .append("?ctl=").append(ctl)
                    .append("&data_id=").append(deal_id);
            String url = String.valueOf(sb);
            Intent intent = new Intent(context.getApplicationContext(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE, false);
            intent.putExtra(AppWebViewActivity.EXTRA_URL, url);
            context.startActivity(intent);
        } else
        {
            SDToast.showToast("url链接不正确");
        }
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
