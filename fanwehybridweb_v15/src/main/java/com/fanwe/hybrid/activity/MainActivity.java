package com.fanwe.hybrid.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baofoo.sdk.vip.BaofooPayActivity;
import com.fanwe.gesture.activity.CreateGesturePasswordActivity;
import com.fanwe.hybrid.common.AppConfigParam;
import com.fanwe.hybrid.common.CommonOpenLoginSDK;
import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.constant.Constant.JsFunctionName;
import com.fanwe.hybrid.constant.Constant.LoginSdkType;
import com.fanwe.hybrid.constant.Constant.PaymentType;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.dao.LoginSuccessModelDao;
import com.fanwe.hybrid.dialog.BotPhotoPopupView;
import com.fanwe.hybrid.dialog.DialogCropPhoto;
import com.fanwe.hybrid.dialog.DialogCropPhoto.OnCropBitmapListner;
import com.fanwe.hybrid.event.EJsSdkShare;
import com.fanwe.hybrid.event.EnumEventTag;
import com.fanwe.hybrid.jshandler.FenDaAppJsHandler;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode.Geo2addressListener;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.hybrid.model.CutPhotoModel;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.model.LoginSuccessModel;
import com.fanwe.hybrid.model.OpenTypeModel;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.hybrid.model.QrCodeScan2Model;
import com.fanwe.hybrid.model.SdkShareModel;
import com.fanwe.hybrid.service.AppUpgradeService;
import com.fanwe.hybrid.umeng.UmengPushManager;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.hybrid.utils.FileUtils;
import com.fanwe.hybrid.utils.IntentUtil;
import com.fanwe.hybrid.utils.SDImageUtil;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebChromeClient;
import com.fanwe.library.webview.DefaultWebViewClient;
import com.fanwei.jubaosdk.common.util.SdkConst;
import com.iapppay.interfaces.callback.IPayResultCallback;
import com.iapppay.sdk.main.IAppPay;
import com.sunday.eventbus.SDBaseEvent;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject.ReverseAddressResult;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.mapsdk.raster.model.LatLng;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.fanwe.yi.R;
import cn.fanwe.yi.wxapi.WXPayEntryActivity.RespErrCode;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-1-5 下午4:08:24 类说明
 */
@SuppressWarnings("deprecation")
public class MainActivity extends BaseActivity implements OnCropBitmapListner, PayResultListner
{
    public static final String SAVE_CURRENT_URL = "url";

    private static final String mPath = "/sdcard/myImage/";
    private static final String mFileName = "avatar.jpg";

    public static final String EXTRA_URL = "extra_url";

    private static final int FILECHOOSER_RESULTCODE = 1;// 选择照片
    private static final int REQUEST_CODE_UPAPP_SDK = 10;// 银联支付
    private static final int REQUEST_CODE_BAOFOO_SDK_RZ = 100;// 宝付支付
    private static final int REQUEST_CODE_WEB_ACTIVITY = 2;// WEB回调
    private static final int REQUEST_CODE_QR = 99;// 二维码

    @ViewInject(R.id.ll_fl)
    private FrameLayout mll_fl;
    @ViewInject(R.id.cus_webview)
    private CustomWebView mWebViewCustom;

    private BotPhotoPopupView mBotPhotoPopupView;
    private ValueCallback<Uri> mUploadMessage;
    private String mCameraFilePath;
    private String mCurrentUrl;
    private CutPhotoModel mCut_model;
    private SDTencentGeoCode mGeoCode;

    /**
     * 销毁保存当前URL
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        String url = mWebViewCustom.getUrl();
        Log.e("TAG","url    1**"+url);
        savedInstanceState.putString(SAVE_CURRENT_URL, url);
    }

    /**
     * 恢复时候加载销毁时候的URL
     */
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        String url = savedInstanceState.getString(SAVE_CURRENT_URL);
        Log.e("TAG","url    2**"+url);
        mWebViewCustom.get(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        mIsExitApp = true;
        x.view().inject(this);
        init();
    }

    private void init()
    {
        UmengSocialManager.init(this);// 友盟初始化
        mHandler = new Handler();
        mWebViewCustom.addJavascriptInterface(new FenDaAppJsHandler(this, mWebViewCustom));
        mGeoCode = new SDTencentGeoCode(this);
        checkVersion();
        getIntentInfo();
        initWebView();
    }

    private void checkVersion()
    {
        Intent updateIntent = new Intent(this, AppUpgradeService.class);
        startService(updateIntent);
    }

    private void getIntentInfo()
    {
        if (getIntent().hasExtra(EXTRA_URL))
        {
            mCurrentUrl = getIntent().getExtras().getString(EXTRA_URL);
        }
    }

    private void initWebView()
    {
        String url;
        if (!TextUtils.isEmpty(mCurrentUrl))
        {
            url = mCurrentUrl;
        } else
        {
            InitActModel model = InitActModelDao.query();
            if (model == null)
            {
                url = ApkConstant.SERVER_URL_SHOW_ANIM;
            } else
            {
                String site_url = model.getSite_url();
                if (!TextUtils.isEmpty(site_url))
                {
                    url = site_url;
                } else
                {
                    url = ApkConstant.SERVER_URL_SHOW_ANIM;
                }
            }

        }

        mWebViewCustom.setWebViewClient(new DefaultWebViewClient()
        {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                boolean is_use_no_network_activity = getResources().getBoolean(R.bool.is_use_no_network_activity);
                if (is_use_no_network_activity)
                {
                    view.loadUrl("about:blank");
                    Intent intent = new Intent(MainActivity.this, NoNetWorkActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_WEB_ACTIVITY);
                } else
                {
                    failingUrl = "file:///android_asset/new_no_network.html";
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                if (AppConfigParam.isShowingConfig() == 1)
                {
                    showDialog();
                } else if (url.contains("show_prog=1"))
                {
                    showDialog();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                mCurrentUrl = url;
                dimissDialog();
                putCookieSP(url);
            }
        });

        mWebViewCustom.setWebChromeClient(new DefaultWebChromeClient()
        {
            public void onGeolocationPermissionsShowPrompt(String origin,
                    GeolocationPermissions.Callback callback
            )
            {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
            {
                if (mUploadMessage != null)
                    return;
                mUploadMessage = uploadFile;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            }
        });
        Log.e("TAG","url    3**"+url);
        mWebViewCustom.get(url);
    }

    private void putCookieSP(String url)
    {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
        if (!TextUtils.isEmpty(cookie))
        {
            SDConfig.getInstance().setString("cookie", cookie);
        } else
        {
            SDConfig.getInstance().setString("cookie", "");
        }
    }

    private Intent createDefaultOpenableIntent()
    {
        Intent intentSysAction = IntentUtil.openSysAppAction();
        mCameraFilePath = IntentUtil.getCamerFilePath();
        Intent chooser = IntentUtil.createChooserIntent(IntentUtil.createCameraIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, intentSysAction);
        return chooser;
    }

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case EVENT_ONPEN_NETWORK:
                IntentUtil.openNetwork(this);
                break;
            case EVENT_REFRESH_RELOAD:
                mWebViewCustom.get(ApkConstant.SERVER_URL_SHOW_ANIM);
                mWebViewCustom.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mWebViewCustom.clearHistory();
                    }
                }, 1000);
                break;
            case EVENT_PAY_SDK:
                PaySdkModel uc_Save_InchargeActModel = (PaySdkModel) event.getData();
                openSDKPAY(uc_Save_InchargeActModel);
                break;
            case EVENT_LOGIN_SUCCESS:
                boolean is_open_adv = getResources().getBoolean(R.bool.is_open_gesture);
                LoginSuccessModel loginSuccessModel = LoginSuccessModelDao.queryModelCurrentLogin();
                if (loginSuccessModel != null)
                {
                    if (is_open_adv && TextUtils.isEmpty(loginSuccessModel.getPatternpassword()))
                    {
                        Intent intent = new Intent(MainActivity.this, CreateGesturePasswordActivity.class);
                        intent.putExtra(CreateGesturePasswordActivity.EXTRA_CODE, CreateGesturePasswordActivity.ExtraCodel.EXTRA_CODE_1);
                        startActivity(intent);
                    }
                }

                break;
            case EVENT_OPEN_TYPE:
                OpenTypeModel model = (OpenTypeModel) event.getData();
                Intent intent_open_type = null;
                if (model.getOpen_url_type() == 0)
                {
                    intent_open_type = new Intent(MainActivity.this, AppWebViewActivity.class);
                    intent_open_type.putExtra(AppWebViewActivity.EXTRA_URL, model.getUrl());
                } else if (model.getOpen_url_type() == 2)
                {
                    intent_open_type = new Intent(MainActivity.this, AppWebViewActivity.class);
                    intent_open_type.putExtra(AppWebViewActivity.EXTRA_URL, model.getUrl());
                    intent_open_type.putExtra(AppWebViewActivity.EXTRA_CODE, model.getOpen_url_type());
                } else
                {
                    intent_open_type = IntentUtil.showHTML(model.getUrl());
                }
                startActivityForResult(intent_open_type, REQUEST_CODE_WEB_ACTIVITY);
                break;
            case EVENT_QR_CODE_SCAN:
                Intent intent_qr_code_scan = new Intent(MainActivity.this, MyCaptureActivity.class);
                startActivityForResult(intent_qr_code_scan, REQUEST_CODE_QR);
                break;
            case EVENT_CUTPHOTO:
                mCut_model = (CutPhotoModel) event.getData();
                clickll_head();
                break;
            case EVENT_CLIPBOARDTEXT:
                String text = (String) event.getData();
                mWebViewCustom.loadJsFunction(JsFunctionName.GET_CLIP_BOARD, text);
                break;
            case TENCENT_LOCATION_MAP:
                startLocation(false);
                break;
            case TENCENT_LOCATION_ADDRESS:
                startLocation(true);
                break;
            case EVENT_APNS:
                String dev_token = UmengPushManager.getPushAgent().getRegistrationId();
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_APNS, "android", dev_token);
                break;
            case EVENT_WX_PAY_JS_BACK:
                event_wx_pay(event);
                break;
            case EVENT_LOGIN_SDK:
                String type = (String) event.getData();
                showDialog();
                if (LoginSdkType.WXLOGIN.equals(type))
                {
                    CommonOpenLoginSDK.loginWx(this);
                } else if (LoginSdkType.QQLOGIN.equals(type))
                {
                    CommonOpenLoginSDK.loginQQ(this, mWebViewCustom);
                } else if (LoginSdkType.SINAWEIBO.equals(type))
                {
                    CommonOpenLoginSDK.loginSina(this, mWebViewCustom);
                }
                break;
            case EVENT_WX_LOGIN_JS_BACK:
                dimissDialog();
                String json = (String) event.getData();
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_LOGIN_SDK, json);
                break;
            case EVENT_IS_EXIST_INSTALLED:
                String is_exist_sdk = (String) event.getData();
                int is_exist;
                if (SDPackageUtil.isPackageExist(is_exist_sdk))
                {
                    is_exist = 1;
                } else
                {
                    is_exist = 0;
                }
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_IS_EXIST_INSTALLED, is_exist_sdk, is_exist);
                break;
            case EVENT_QR_CODE_SCAN_2:
                qrCodeScan2Model = (QrCodeScan2Model) event.getData();
                Intent intent_qr_code_scan2 = new Intent(MainActivity.this, MyCaptureActivity.class);
                startActivityForResult(intent_qr_code_scan2, REQUEST_CODE_QR);
                break;
            case EVENT_RELOAD_WEBVIEW:
                break;
            default:
                break;
        }
    }

    private QrCodeScan2Model qrCodeScan2Model;

    private void event_wx_pay(SDBaseEvent event)
    {
        int code = (Integer) event.getData();
        switch (code)
        {
            case RespErrCode.CODE_CANCEL:
                onCancel();
                break;
            case RespErrCode.CODE_FAIL:
                onFail();
                break;
            case RespErrCode.CODE_SUCCESS:
                onSuccess();
                break;
        }
    }

    /**
     * 先定位在反编译地址 isStartAddressReverse 是否反解析地址
     */
    private void startLocation(final boolean isStartAddressReverse)
    {
        SDTencentMapManager.getInstance().startLocation(new TencentLocationListener()
        {
            @Override
            public void onStatusUpdate(String arg0, int arg1, String arg2)
            {

            }

            @Override
            public void onLocationChanged(TencentLocation location, int error, String reason)
            {
                if (location != null)
                {
                    final double lat = location.getLatitude();
                    final double lng = location.getLongitude();
                    mWebViewCustom.loadJsFunction(JsFunctionName.JS_POSITION, lat, lng);

                    if (isStartAddressReverse)
                    {
                        startAddressRe(lat, lng);
                    }
                } else
                {
                    SDToast.showToast("定位失败");
                }
            }

        });
    }

    /**
     * 反解析地址
     */
    private void startAddressRe(final double lat, final double lng)
    {
        mGeoCode.location(new LatLng(lat, lng)).geo2address(new Geo2addressListener()
        {
            @Override
            public void onSuccess(ReverseAddressResult result)
            {
                if (result.formatted_addresses != null)
                {
                    Map<String, String> map = new HashMap<String, String>();

                    String nation = result.ad_info.nation;
                    String province = result.ad_info.province;
                    String city = result.ad_info.city;
                    String district = result.ad_info.district;
                    String adcode = result.ad_info.adcode;
                    String recommend = result.formatted_addresses.recommend;

                    map.put("nation", nation);
                    map.put("province", province);
                    map.put("city", city);
                    map.put("district", district);
                    map.put("adcode", adcode);
                    map.put("recommend", recommend);
                    String json = JSON.toJSONString(map);

                    mWebViewCustom.loadJsFunction(JsFunctionName.JS_POSITION2, lat, lng, json);
                }
            }

            @Override
            public void onFailure(String msg)
            {
                SDToast.showToast("解析地址失败");
            }
        });
    }

    private void clickll_head()
    {
        if (mBotPhotoPopupView != null)
        {
            if (mBotPhotoPopupView.isShowing())
            {
                mBotPhotoPopupView.dismiss();
            } else
            {
                mBotPhotoPopupView.showAtLocation(mll_fl, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        } else
        {
            mBotPhotoPopupView = new BotPhotoPopupView(this);
            mBotPhotoPopupView.showAtLocation(mll_fl, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
        }
    }

    public void openSDKPAY(PaySdkModel model)
    {
        String payCode = model.getPay_sdk_type();
        if (!TextUtils.isEmpty(payCode))
        {
            if (PaymentType.UPAPP.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payUpApp(model, this, this);
            } else if (PaymentType.BAOFOO.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payBaofoo(model, this, REQUEST_CODE_BAOFOO_SDK_RZ, this);
            } else if (PaymentType.ALIPAY.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payAlipay(model, this, this);
            } else if (PaymentType.WXPAY.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payWxPay(model, this, this);
            } else if (PaymentType.IAPPPAY.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payIApp(model, this, this, iPayResultCallback);
            } else if (PaymentType.JUBAOPAY.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payJuBaoSdkPay(model, this, this);
            }
        } else
        {
            SDToast.showToast("payCode为空");
            onOther();
        }

    }

    /**
     * 爱贝支付结果回调
     */
    IPayResultCallback iPayResultCallback = new IPayResultCallback()
    {

        @Override
        public void onPayResult(int resultCode, String signvalue, String resultInfo)
        {
            switch (resultCode)
            {
                case IAppPay.PAY_SUCCESS:
                    // 调用 IAppPayOrderUtils 的验签方法进行支付结果验证
                    // boolean payState =
                    // IAppPayOrderUtils.checkPayResult(signvalue,
                    // IAppPayConfig.i_publicKey);
                    // if (payState)
                    // {
                    onSuccess();
                    Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                    // }
                    break;
                case IAppPay.PAY_ING:
                    onDealing();
                    Toast.makeText(MainActivity.this, "成功下单", Toast.LENGTH_LONG).show();
                    break;
                default:
                    onOther();
                    Toast.makeText(MainActivity.this, resultInfo, Toast.LENGTH_LONG).show();
                    break;
            }
            Log.d("MainActivity", "requestCode:" + resultCode + ",signvalue:" + signvalue + ",resultInfo:" + resultInfo);
        }
    };

    public void onEventMainThread(EJsSdkShare event)
    {
        SdkShareModel model = JSON.parseObject(event.json, SdkShareModel.class);
        openShare(model);
    }

    private void openShare(SdkShareModel model)
    {
        String title = model.getShare_title();
        String content = model.getShare_content();
        String imageUrl = model.getShare_imageUrl();
        String clickUrl = model.getShare_url();
        final String key = model.getShare_key();

        UmengSocialManager.openShare(title, content, imageUrl, clickUrl, this, new UMShareListener()
        {

            @Override
            public void onResult(SHARE_MEDIA platform)
            {
                //Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                mWebViewCustom.post(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        mWebViewCustom.loadJsFunction(JsFunctionName.SHARE_COMPLEATE, key);
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t)
            {
                // Toast.makeText(MainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform)
            {
                //Toast.makeText(MainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void callbackbitmap(Bitmap bit)
    {
        String base64img = SDImageUtil.bitmapToBase64(bit);
        base64img = "data:image/jpg;base64," + base64img;
        mWebViewCustom.loadJsFunction(JsFunctionName.CUTCALLBACK, base64img);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case FILECHOOSER_RESULTCODE:
                fileChooserResultcode(data, resultCode);
                break;
            case REQUEST_CODE_BAOFOO_SDK_RZ:
                requestCodeBaofooSdkRz(data);
                break;
            case REQUEST_CODE_UPAPP_SDK:
                requestUpAppSdk(data);
                break;
            case REQUEST_CODE_WEB_ACTIVITY:
                requestCodeWebActivity(data, resultCode);
                break;
            case BotPhotoPopupView.REQUEST_CODE_TAKE_PHOTO:
                requestCodeTakePhoto(data, resultCode);
                break;
            case BotPhotoPopupView.REQUEST_CODE_SELECT_PHOTO:
                requestCodeSelectPhoto(data, resultCode);
                break;
            case REQUEST_CODE_QR:
                if (resultCode == MyCaptureActivity.RESULT_CODE_SCAN_SUCCESS)
                {
                    if (qrCodeScan2Model != null)
                    {
                        if (qrCodeScan2Model.getType() == 1)
                        {
                            String str_qr = data.getExtras().getString(MyCaptureActivity.EXTRA_RESULT_SUCCESS_STRING);
                            if (str_qr.contains("uid="))
                            {
                                String uid = str_qr.substring(str_qr.lastIndexOf("uid=") + 4, str_qr.length());
                                String url = qrCodeScan2Model.getPrefix_data() + uid;
                                mWebViewCustom.loadUrl(url);
                                qrCodeScan2Model = null;
                            } else
                            {
                                SDToast.showToast("uid=不存在");
                            }
                        } else
                        {
                            SDToast.showToast("qrCodeScan2Model其他Type");
                        }
                    } else
                    {
                        String str_qr = data.getExtras().getString(MyCaptureActivity.EXTRA_RESULT_SUCCESS_STRING);
                        mWebViewCustom.loadJsFunction(JsFunctionName.JS_QR_CODE_SCAN, str_qr);
                    }
                }
                break;
            case SdkConst.REQUESTCODE:
                if (resultCode == SdkConst.RESULTCODE)
                {
                    int code = Integer.valueOf(data.getStringExtra("code"));
                    if (code == 0)
                    {
                        onSuccess();
                    } else if (code == 1)
                    {
                        onFail();
                    } else if (code == 2)
                    {
                        onCancel();
                    }
                    //SDToast.showToast("[code=" + data.getStringExtra("code") + "]" + "[message=" + data.getStringExtra("message") + "]");

                }
                break;

        }
    }

    private void fileChooserResultcode(Intent data, int resultCode)
    {
        if (null == mUploadMessage)
            return;
        Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
        if (result == null && data == null && resultCode == Activity.RESULT_OK)
        {
            File cameraFile = new File(mCameraFilePath);
            if (cameraFile.exists())
            {
                result = Uri.fromFile(cameraFile);
                // Broadcast to the media scanner that we have a new photo
                // so it will be added into the gallery for the user.
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
            }
        }
        if (result == null)
        {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
            return;
        }
        String file_path = FileUtils.getPath(this, result);
        if (TextUtils.isEmpty(file_path))
        {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
            return;
        }
        Uri uri = Uri.fromFile(new File(file_path));

        mUploadMessage.onReceiveValue(uri);
        mUploadMessage = null;
    }

    private void requestCodeBaofooSdkRz(Intent data)
    {
        String result_baofoo = "";
        String msg = "";
        if (data == null || data.getExtras() == null)
        {
            msg = "支付已被取消";
            onCancel();
        } else
        {

            msg = data.getExtras().getString(BaofooPayActivity.PAY_MESSAGE);
            if (!TextUtils.isEmpty(msg))
            {
                SDToast.showToast(msg);
            }
            // -1:失败，0:取消，1:成功，10:处理中
            result_baofoo = data.getExtras().getString(BaofooPayActivity.PAY_RESULT);

            if ("1".equals(result_baofoo))
            {
                onSuccess();
            } else if ("-1".equals(result_baofoo))
            {
                onFail();
            } else if ("0".equals(result_baofoo))
            {
                onCancel();
            } else if ("10".equals(result_baofoo))
            {
                onDealing();
            } else
            {
                onOther();
            }

        }
    }

    private void requestCodeWebActivity(Intent data, int resultCode)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (data != null && data.hasExtra(EXTRA_URL))
            {
                String url = data.getExtras().getString(EXTRA_URL);
                mWebViewCustom.get(url);
            } else
            {
                onCancel();
            }
        }
    }

    private void requestCodeTakePhoto(Intent data, int resultCode)
    {
        if (resultCode == RESULT_OK)
        {
            String path = BotPhotoPopupView.getmTakePhotoPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            dealImageSize(bitmap);
        }
    }

    private void dealImageSize(Bitmap bitmap)
    {
        SDImageUtil.dealImageCompress(mPath, mFileName, bitmap, 100);
        File file = new File(mPath, mFileName);
        if (file != null && file.exists())
        {
            DialogCropPhoto dialog = new DialogCropPhoto(this, file.getPath(), this, mCut_model);
            dialog.show();
        }
        onDismissPop();
    }

    private void requestCodeSelectPhoto(Intent data, int resultCode)
    {
        if (resultCode == RESULT_OK)
        {
            String path = SDImageUtil.getImageFilePathFromIntent(data, this);
            DialogCropPhoto dialog = new DialogCropPhoto(this, path, this, mCut_model);
            dialog.show();
            onDismissPop();
        }
    }

    private void requestUpAppSdk(Intent data)
    {
        // 银联支付回调
        if (data != null)
        {
            Bundle bundle = data.getExtras();
            if (bundle != null)
            {
                /*
                 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
				 */
                String payResult = bundle.getString("pay_result");
                if ("success".equalsIgnoreCase(payResult))
                {
                    SDToast.showToast("支付成功");
                    onSuccess();
                } else if ("fail".equalsIgnoreCase(payResult))
                {
                    SDToast.showToast("支付失败");
                    onFail();
                } else if ("cancel".equalsIgnoreCase(payResult))
                {
                    onCancel();
                } else
                {
                    onOther();
                }
            }
        }
    }

    private void onDismissPop()
    {
        if (mBotPhotoPopupView != null && mBotPhotoPopupView.isShowing())
        {
            mBotPhotoPopupView.dismiss();
        }
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);

        if (getIntent().hasExtra(EXTRA_URL))
        {
            mCurrentUrl = getIntent().getExtras().getString(EXTRA_URL);
            mWebViewCustom.get(mCurrentUrl);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        AudioManager audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_BACK:
                InitActModel model = InitActModelDao.query();
                if (model != null)
                {
                    judgeUrlBack(model);
                } else
                {
                    exitApp();
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * 判断url是否要退出应用
     */
    private void judgeUrlBack(InitActModel model)
    {
        String curUrl = mWebViewCustom.getUrl();
        curUrl = InitActModel.filterparam(curUrl);
        if (model.getTop_url() != null && model.getTop_url().size() > 0)
        {
            ArrayList<String> top_url = model.getTop_url();
            boolean isEqualsUrl = false;
            for (int i = 0; i < top_url.size(); i++)
            {
                String url = top_url.get(i);
                if (curUrl.equals(url))
                {
                    exitApp();
                    isEqualsUrl = true;
                    break;
                } else
                {
                    if (i == top_url.size() - 1)
                    {
                        isEqualsUrl = false;
                    }
                }
            }
            if (!isEqualsUrl)
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_BACK);
            } else
            {
                isEqualsUrl = false;
            }
        } else
        {
            exitApp();
        }
    }

    private Handler mHandler;

    @Override
    public void onSuccess()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 1);
            }
        });
    }

    @Override
    public void onDealing()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 2);
            }
        });
    }

    @Override
    public void onFail()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 3);
            }
        });
    }

    @Override
    public void onCancel()
    {
        mHandler.post(new Runnable()
        {

            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 4);
            }
        });
    }

    @Override
    public void onNetWork()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 5);
            }
        });
    }

    @Override
    public void onOther()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 6);
            }
        });
    }

}
