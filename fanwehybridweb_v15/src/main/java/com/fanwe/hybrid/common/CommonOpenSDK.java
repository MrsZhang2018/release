package com.fanwe.hybrid.common;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.baofoo.sdk.vip.BaofooPayActivity;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.model.BfappModel;
import com.fanwe.hybrid.model.BfupwapModel;
import com.fanwe.hybrid.model.IAppPayModel;
import com.fanwe.hybrid.model.JuBaoSdkModel;
import com.fanwe.hybrid.model.MalipayModel;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.hybrid.model.WxappModel;
import com.fanwe.hybrid.wxapp.SDWxappPay;
import com.fanwe.library.iapppay.IAppPayInstance;
import com.fanwe.library.pay.alipay.PayResult;
import com.fanwe.library.pay.alipay.SDAlipayer;
import com.fanwe.library.pay.alipay.SDAlipayer.SDAlipayerListener;
import com.fanwe.library.utils.SDToast;
import com.fanwei.jubaosdk.shell.FWPay;
import com.fanwei.jubaosdk.shell.PayOrder;
import com.iapppay.interfaces.callback.IPayResultCallback;
import com.iapppay.sdk.main.IAppPay;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-1-25 下午4:43:54 类说明
 */
public class CommonOpenSDK
{
	/** 宝付SDK */
	public static void payBaofoo(PaySdkModel actModel, Activity context, int requestCode, final PayResultListner listner)
	{
		if (actModel == null)
		{
			return;
		}

		BfappModel model = actModel.getBfappModel();
		if (model == null)
		{
			SDToast.showToast("支付参数获取失败");
			listner.onOther();
			return;
		}

		String respCode = model.getRetCode();

		if (!"0000".equals(respCode))
		{
			SDToast.showToast(respCode + model.getRetMsg());
			listner.onOther();
			return;
		}

		String tradeNo = model.getTradeNo();
		if (TextUtils.isEmpty(tradeNo))
		{
			SDToast.showToast("tradeNo为空");
			listner.onOther();
			return;
		}

		boolean isDebug = actModel.getBfappModel().getIs_debug() == 1 ? false : true;
		Intent payintent = new Intent(context, BaofooPayActivity.class);
		payintent.putExtra(BaofooPayActivity.PAY_TOKEN, tradeNo);
		payintent.putExtra(BaofooPayActivity.PAY_BUSINESS, isDebug);
		context.startActivityForResult(payintent, requestCode);
	}

	/** 银联SDK */
	public static void payUpApp(PaySdkModel actModel, Activity context, final PayResultListner listner)
	{
		if (actModel == null)
		{
			return;
		}

		BfupwapModel model = actModel.getBfupwapModel();
		if (model == null)
		{
			SDToast.showToast("支付参数获取失败");
			listner.onOther();
			return;
		}

		String tradeNo = model.getTn();
		if (TextUtils.isEmpty(tradeNo))
		{
			SDToast.showToast("tn为空");
			listner.onOther();
			return;
		}
		String isDebug;
		if(model.getIs_debug()==1)
        {
			isDebug="01";
		}else
        {
			isDebug="00";
		}

		UPPayAssistEx.startPayByJAR(context, PayActivity.class, null, null, tradeNo, isDebug);
	}

	/**
	 * 支付宝sdk支付(新)
	 */
	public static void payAlipay(PaySdkModel model, final Activity activity, final PayResultListner listner)
	{
		if (model == null)
		{
			return;
		}
		MalipayModel mainpayModel = model.getMalipay();
		if (mainpayModel == null)
		{
			SDToast.showToast("获取支付参数失败");
			listner.onOther();
			return;
		}

		String orderSpec = mainpayModel.getOrder_spec();
		String sign = mainpayModel.getSign();
		String signType = mainpayModel.getSign_type();

		if (TextUtils.isEmpty(orderSpec))
		{
			SDToast.showToast("order_spec为空");
			listner.onOther();
			return;
		}

		if (TextUtils.isEmpty(sign))
		{
			SDToast.showToast("sign为空");
			listner.onOther();
			return;
		}

		if (TextUtils.isEmpty(signType))
		{
			SDToast.showToast("signType为空");
			listner.onOther();
			return;
		}

		SDAlipayer payer = new SDAlipayer(activity);
		payer.setListener(new SDAlipayerListener()
		{

			@Override
			public void onResult(PayResult result)
			{
				String info = result.getMemo();
				String status = result.getResultStatus();

				if ("9000".equals(status)) // 支付成功
				{
					SDToast.showToast("支付成功");
					listner.onSuccess();
				} else if ("8000".equals(status)) // 支付结果确认中
				{
					SDToast.showToast("支付结果确认中");
					listner.onDealing();
				} else if ("4000".equals(status))
				{
					if (!TextUtils.isEmpty(info))
					{
						SDToast.showToast(info);
					}
					listner.onFail();
				} else if ("6001".equals(status))
				{
					if (!TextUtils.isEmpty(info))
					{
						SDToast.showToast(info);
					}
					listner.onCancel();
				} else if ("6002".equals(status))
				{
					if (!TextUtils.isEmpty(info))
					{
						SDToast.showToast(info);
					}
					listner.onNetWork();
				} else
				{
					if (!TextUtils.isEmpty(info))
					{
						SDToast.showToast(info);
					}
					listner.onOther();
				}
			}

			@Override
			public void onFailure(Exception e, String msg)
			{
				if (e != null)
				{
					listner.onOther();
					SDToast.showToast("错误:" + e.toString());
				} else
				{
					if (!TextUtils.isEmpty(msg))
					{
						listner.onOther();
						SDToast.showToast(msg);
					}
				}
			}
		});
		payer.pay(orderSpec, sign, signType);
	}

	/** 微信SDK支付 */
	public static void payWxPay(PaySdkModel paySdkModel, Activity activity, final PayResultListner listner)
	{
		if (paySdkModel == null)
		{
			return;
		}

		WxappModel model = paySdkModel.getWxapp();
		if (model == null)
		{
			SDToast.showToast("获取支付参数失败");
			listner.onOther();
			return;
		}

		String appId = model.getAppid();
		if (TextUtils.isEmpty(appId))
		{
			SDToast.showToast("appId为空");
			listner.onOther();
			return;
		}

		String partnerId = model.getPartnerid();
		if (TextUtils.isEmpty(partnerId))
		{
			SDToast.showToast("partnerId为空");
			listner.onOther();
			return;
		}

		String prepayId = model.getPrepayid();
		if (TextUtils.isEmpty(prepayId))
		{
			SDToast.showToast("prepayId为空");
			listner.onOther();
			return;
		}

		String nonceStr = model.getNoncestr();
		if (TextUtils.isEmpty(nonceStr))
		{
			SDToast.showToast("nonceStr为空");
			listner.onOther();
			return;
		}

		String timeStamp = model.getTimestamp();
		if (TextUtils.isEmpty(timeStamp))
		{
			SDToast.showToast("timeStamp为空");
			listner.onOther();
			return;
		}

		String packageValue = model.getPackagevalue();
		if (TextUtils.isEmpty(packageValue))
		{
			SDToast.showToast("packageValue为空");
			listner.onOther();
			return;
		}

		String sign = model.getSign();
		if (TextUtils.isEmpty(sign))
		{
			SDToast.showToast("sign为空");
			listner.onOther();
			return;
		}

		SDWxappPay.getInstance().setAppId(appId);

		PayReq req = new PayReq();
		req.appId = appId;
		req.partnerId = partnerId;
		req.prepayId = prepayId;
		req.nonceStr = nonceStr;
		req.timeStamp = timeStamp;
		req.packageValue = packageValue;
		req.sign = sign;

		SDWxappPay.getInstance().pay(req);
	}

	public static void payIApp(PaySdkModel paySdkModel, Activity activity, final PayResultListner listner, final IPayResultCallback iPayResultCallback)
	{
		if (listner == null || paySdkModel == null)
		{
			return;
		}

		IAppPayModel iAppPayModel = paySdkModel.getIAppPayModel();
		if (iAppPayModel == null)
		{
			SDToast.showToast("iAppPayModel为空");
			listner.onOther();
			return;
		}

		String transid = iAppPayModel.getTransid();
		if (TextUtils.isEmpty(transid))
		{
			SDToast.showToast("transid为空");
			listner.onOther();
			return;
		}

		String appid = iAppPayModel.getAppid();
		if (TextUtils.isEmpty(appid))
		{
			SDToast.showToast("appid为空");
			listner.onOther();
			return;
		}

		if (!IAppPayInstance.getInstance().isIs_init())
		{
			IAppPayInstance.getInstance().setIs_init(true, iAppPayModel.getAppid());
		}

		String param = "transid=" + transid + "&appid=" + appid;
		IAppPay.startPay(activity, param, iPayResultCallback);
	}

	public static void payJuBaoSdkPay(PaySdkModel paySdkModel, Activity activity, final PayResultListner listner)
	{
		if (listner == null || paySdkModel == null)
		{
			return;
		}

		JuBaoSdkModel juBaoSdkModel = paySdkModel.getJuBaoSdkModel();
		if (juBaoSdkModel == null)
		{
			SDToast.showToast("juBaoSdkModel为空");
			listner.onOther();
			return;
		}

		String appId = juBaoSdkModel.getAppId();
		if (TextUtils.isEmpty(appId))
		{
			SDToast.showToast("appId为空");
			listner.onOther();
			return;
		}

		String amount = juBaoSdkModel.getAmount();
		if (TextUtils.isEmpty(amount))
		{
			SDToast.showToast("amount为空");
			listner.onOther();
			return;
		}

		String goodsName = juBaoSdkModel.getGoodsName();
		if (TextUtils.isEmpty(goodsName))
		{
			SDToast.showToast("goodsName为空");
			listner.onOther();
			return;
		}

		String payId = juBaoSdkModel.getPayId();
		if (TextUtils.isEmpty(payId))
		{
			SDToast.showToast("payId为空");
			listner.onOther();
			return;
		}

		String playerId = juBaoSdkModel.getPlayerId();
		if (TextUtils.isEmpty(playerId))
		{
			SDToast.showToast("playerId为空");
			listner.onOther();
			return;
		}

		PayOrder order = new PayOrder()
		// AppId(必需)
				.setAppId(appId)
				// 金额(必需)
				.setAmount(amount)
				// 商品名称(必需)
				.setGoodsName(goodsName)
				// 商户订单号(必需)
				.setPayId(payId)
				// 玩家Id(必需)
				.setPlayerId(playerId);
		FWPay.pay(activity, order);
	}

}
