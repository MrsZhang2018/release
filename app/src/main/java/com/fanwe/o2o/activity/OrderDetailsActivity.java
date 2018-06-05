package com.fanwe.o2o.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.HorizontalScrollViewPageIndicator;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.OrderDetailsFeeinfoAdapter;
import com.fanwe.o2o.adapter.OrderDetailsPaidAdapter;
import com.fanwe.o2o.adapter.OrderDetailsStoreAdapter;
import com.fanwe.o2o.adapter.OrderListBtnAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.EOrderListRefresh;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.FeeinfoModel;
import com.fanwe.o2o.model.OperationModel;
import com.fanwe.o2o.model.PaidModel;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemModel;
import com.fanwe.o2o.model.UcOrderWapViewActModel;
import com.fanwe.o2o.model.UcOrderWapViewItemActModel;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情页面
 * Created by Administrator on 2017/3/3.
 */

public class OrderDetailsActivity extends BaseTitleActivity
{
    @ViewInject(R.id.tv_order_sn)
    private TextView tv_order_sn;
    @ViewInject(R.id.tv_status_name)
    private TextView tv_status_name;
    @ViewInject(R.id.tv_create_time)
    private TextView tv_create_time;
    @ViewInject(R.id.tv_youhui)
    private TextView tv_youhui;
    @ViewInject(R.id.tv_app_format_order_total_price)
    private TextView tv_app_format_order_total_price;
    @ViewInject(R.id.tv_app_format_youhui_price)
    private TextView tv_app_format_youhui_price;
    @ViewInject(R.id.tv_app_format_order_pay_price)
    private TextView tv_app_format_order_pay_price;
    @ViewInject(R.id.gv_stroe)
    private SDGridLinearLayout gv_stroe;
    @ViewInject(R.id.gv_feeinfo)
    private SDGridLinearLayout gv_feeinfo;
    @ViewInject(R.id.gv_paid)
    private SDGridLinearLayout gv_paid;
    @ViewInject(R.id.sv_btn)
    private HorizontalScrollViewPageIndicator sv_btn;
    @ViewInject(R.id.tv_no_operation)
    TextView tv_no_operation;
    @ViewInject(R.id.tv_count)
    private TextView tv_count;
    @ViewInject(R.id.tv_red_bag)
    private TextView tv_red_bag;
    @ViewInject(R.id.tv_store_name)
    private TextView tv_store_name;
    @ViewInject(R.id.tv_app_format_total_price)
    private TextView tv_app_format_total_price;
    @ViewInject(R.id.ll_wait_pay)
    private LinearLayout ll_wait_pay;
    @ViewInject(R.id.ll_consignee)
    private LinearLayout ll_consignee;
    @ViewInject(R.id.tv_consignee)
    private TextView tv_consignee;
    @ViewInject(R.id.tv_mobile)
    private TextView tv_mobile;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.ll_delivery)
    private LinearLayout ll_delivery;
    @ViewInject(R.id.tv_delivery_name)
    private TextView tv_delivery_name;
    @ViewInject(R.id.tv_delivery_fee)
    private TextView tv_delivery_fee;
    @ViewInject(R.id.ll_memo)
    LinearLayout ll_memo;
    @ViewInject(R.id.tv_memo)
    private TextView tv_memo;
    @ViewInject(R.id.ll_store)
    private TextView ll_store;
    @ViewInject(R.id.rl_store)
    RelativeLayout rl_store;
    @ViewInject(R.id.tv_store)
    TextView tv_store;
    @ViewInject(R.id.ll_existence_expire_refund)
    private LinearLayout ll_existence_expire_refund;
    @ViewInject(R.id.tv_existence_expire_refund)
    private TextView tv_existence_expire_refund;
    @ViewInject(R.id.ll_cash_on_delivery)
    private LinearLayout ll_cash_on_delivery;
    @ViewInject(R.id.tv_cash_on_delivery_name)
    private TextView tv_cash_on_delivery_name;
    @ViewInject(R.id.tv_cash_on_delivery_money)
    private TextView tv_cash_on_delivery_money;
    public static final String EXTRA_ID = "extra_id";
    private MoreTitleDialog titleDialog;
    private String id;
    private boolean isNeedRefreshList;//是否在返回时刷新订单列表

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_order_details);

        getIntentData();
        initTitle();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        requestData();
    }

    private void getIntentData()
    {
        id = getIntent().getStringExtra(EXTRA_ID);
    }

    public void setNeedRefreshList(boolean needRefreshList)
    {
        isNeedRefreshList = needRefreshList;
    }

    public void requestData()
    {
        showProgressDialog("");
        CommonInterface.requestUcOrderWapView(id, new AppRequestCallback<UcOrderWapViewActModel>()
                {
                    @Override
                    protected void onSuccess(SDResponse sdResponse)
                    {
                        if (actModel.isOk())
                        {
                            SDViewBinder.setTextView(tv_order_sn, actModel.getItem().getOrder_sn());
                            SDViewBinder.setTextView(tv_status_name, actModel.getItem().getStatus_name());
                            SDViewBinder.setTextView(tv_create_time, actModel.getItem().getCreate_time());
                            SDViewBinder.setTextView(tv_app_format_order_total_price, "￥" + actModel.getItem().getApp_format_order_total_price());
                            final String youhui = actModel.getItem().getApp_format_youhui_price();

                            if (!TextUtils.isEmpty(youhui) )
                            {
                                final double y = Double.parseDouble(youhui);
                                if (y > 0)
                                {
                                    SDViewUtil.show(tv_youhui);
                                    SDViewUtil.show(tv_app_format_youhui_price);
                                    SDViewBinder.setTextView(tv_app_format_youhui_price, "￥" + youhui);
                                }else
                                {
                                    SDViewUtil.hide(tv_youhui);
                                    SDViewUtil.hide(tv_app_format_youhui_price);
                                }
                            } else
                            {
                                SDViewUtil.hide(tv_youhui);
                                SDViewUtil.hide(tv_app_format_youhui_price);
                            }
                            SDViewBinder.setTextView(tv_app_format_order_pay_price, "￥" + actModel.getItem().getApp_format_order_pay_price());
                            if (!TextUtils.isEmpty(actModel.getItem().getMemo()))
                            {
                                SDViewUtil.show(ll_memo);
                                final String memo = actModel.getItem().getMemo();
                                String newMemo = SDOtherUtil.getTrimContent(memo);
                                SDViewBinder.setTextView(tv_memo,newMemo );
                            }
                            final String locationid = actModel.getItem().getLocation_id();
                            if (!TextUtils.isEmpty(locationid))
                            {
                                if (locationid.equals("0"))
                                {
                                    SDViewUtil.hide(rl_store);
                                } else
                                {
                                    final String location_name = actModel.getItem().getLocation_name();
                                    final String tel = actModel.getItem().getTel();
                                    final String location_address = actModel.getItem().getLocation_address();
                                    SDViewUtil.show(rl_store);
                                    SDViewBinder.setTextView(tv_store_name, location_name + "\n" + tel + "\n" + location_address);
                                    rl_store.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("location_id", locationid);
                                            AppRuntimeWorker.jump2Wap(OrderDetailsActivity.this, "position", "", params);
                                        }
                                    });
                                }
                            }

                            setStroeAdapter(gv_stroe, actModel.getItem());
                            setFeeinfoAdapter(gv_feeinfo, actModel.getItem());
                            setPaidAdapter(gv_paid, actModel.getItem());
                            setBtnAdapter(sv_btn, tv_no_operation, actModel.getItem());

                            /**是否支持过期立即退款**/
                            setExistenceExpireRefund(actModel.getItem().getExistence_expire_refund());

                            /**需付款**/
                            if (actModel.getItem().getIs_pay() == 1)
                            {
                                if ("待付款".equals(actModel.getItem().getStatus_name()))
                                {
                                    SDViewUtil.show(ll_wait_pay);
                                    SDViewBinder.setTextView(tv_count, "共" + actModel.getItem().getCount() + "件商品需付款:");
                                    SDViewBinder.setTextView(tv_app_format_total_price, "￥" + actModel.getItem().getApp_format_total_price());
                                } else
                                {
                                    SDViewUtil.hide(ll_wait_pay);
                                }
                            }

                            /**是否使用红包**/
                            final String amount = actModel.getItem().getApp_format_pay_amount();
                            if (!TextUtils.isEmpty(amount) && !amount.equals("0.00"))
                            {
                                SDViewUtil.show(tv_red_bag);
                                SDViewBinder.setTextView(tv_red_bag, ",已付 ¥ " + amount);
                            } else
                            {
                                SDViewUtil.hide(tv_red_bag);
                            }


                            /**收件人（状态不等于5，并且收货人不为空）**/
                            if (!"5".equals(actModel.getItem().getDelivery_status()) && !TextUtils.isEmpty(actModel.getItem().getConsignee()))
                            {
                                SDViewUtil.show(ll_consignee);
                                SDViewBinder.setTextView(tv_consignee, actModel.getItem().getConsignee());
                                SDViewBinder.setTextView(tv_mobile, actModel.getItem().getMobile());
                                SDViewBinder.setTextView(tv_address, actModel.getItem().getAddress());
                            } else
                            {
                                SDViewUtil.hide(ll_consignee);
                            }

                            /**快递（快递id不等于0）**/
                            if (!TextUtils.isEmpty(actModel.getItem().getDelivery_id()))
                            {
                                if (!"0".equals(actModel.getItem().getDelivery_id()))
                                {
                                    SDViewUtil.show(ll_delivery);
                                    SDViewBinder.setTextView(tv_delivery_name, actModel.getItem().getDelivery_info().getName());
                                    SDViewBinder.setTextView(tv_delivery_fee, "运费" + actModel.getItem().getDelivery_fee() + "元");
                                } else
                                {
                                    SDViewUtil.hide(ll_delivery);
                                }
                            }

                            if (actModel.getItem().getPayment_info() != null)
                            {
                                SDViewUtil.show(ll_cash_on_delivery);
                                SDViewBinder.setTextViewsVisibility(tv_cash_on_delivery_name, actModel.getItem().getPayment_info().getName());
                                SDViewBinder.setTextViewsVisibility(tv_cash_on_delivery_money, actModel.getItem().getPayment_info().getMoney());
                            } else
                            {
                                SDViewUtil.hide(ll_cash_on_delivery);
                            }
                        }else
                        {
                            finish();
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
                        dismissProgressDialog();
                    }
                }

        );
    }

    private void setExistenceExpireRefund(int existence_expire_refund)
    {
        if (existence_expire_refund == 1)
        {
            SDViewUtil.show(ll_existence_expire_refund);
            tv_existence_expire_refund.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            tv_existence_expire_refund.setOnClickListener(this);
        } else
        {
            SDViewUtil.hide(ll_existence_expire_refund);
        }
    }

    private void setBtnAdapter(HorizontalScrollViewPageIndicator sv_btn, TextView tv_no_operation, UcOrderWapViewItemActModel model)
    {

        List<OperationModel> list = new ArrayList<>();
        for (int i = 0; i < model.getOperation().size(); i++)
        {
            OperationModel operationModel = new OperationModel();
            list.add(operationModel);
        }
        Collections.copy(list, model.getOperation());
        Collections.reverse(list);
        if (list.size() > 0 && list.get(0).getName().equals("暂无操作"))
        {
            SDViewUtil.show(tv_no_operation);
            SDViewUtil.hide(sv_btn);
        } else
        {
            SDViewUtil.show(sv_btn);
            SDViewUtil.hide(tv_no_operation);
        }
        OrderListBtnAdapter adapter = new OrderListBtnAdapter(list, this);
        adapter.setId(model.getId());
        sv_btn.setAdapter(adapter);
    }

    private void setPaidAdapter(SDGridLinearLayout gv_content, UcOrderWapViewItemActModel model)
    {
        List<PaidModel> itemList = model.getPaid();
        OrderDetailsPaidAdapter itemAdapter = new OrderDetailsPaidAdapter(itemList, this);
        gv_content.setColNumber(1);
        gv_content.setAdapter(itemAdapter);
        gv_content.postInvalidateDelayed(200);
    }

    private void setFeeinfoAdapter(SDGridLinearLayout gv_content, UcOrderWapViewItemActModel model)
    {
        List<FeeinfoModel> itemList = model.getFeeinfo();
        OrderDetailsFeeinfoAdapter itemAdapter = new OrderDetailsFeeinfoAdapter(itemList, this);
        gv_content.setColNumber(1);
        gv_content.setAdapter(itemAdapter);
        gv_content.postInvalidateDelayed(200);
    }

    private void setStroeAdapter(SDGridLinearLayout gv_content, UcOrderWapViewItemActModel model)
    {
        List<UcOrderItemDealOrderItemModel> itemList = model.getDeal_order_item();
        OrderDetailsStoreAdapter itemAdapter = new OrderDetailsStoreAdapter(itemList, this);
        gv_content.setColNumber(1);
        gv_content.setAdapter(itemAdapter);
        gv_content.postInvalidateDelayed(200);
    }

    private void initTitle()
    {
        title.setMiddleTextTop("订单详情");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        super.onCLickRight_SDTitleSimple(v, index);
        if (titleDialog == null)
            titleDialog = new MoreTitleDialog(this);
        titleDialog.requestData();
        titleDialog.showTop();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_existence_expire_refund:
                AppRuntimeWorker.clickOrderBtn(Constant.OrderBtnType.REFUND, id, "", 0);
                break;
        }
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        requestData();
    }

    @Override
    public void finish()
    {
        super.finish();
        if (SDActivityManager.getInstance().getLastActivity() instanceof MessageListActivity)
        {
            return;
        }
        if (!(SDActivityManager.getInstance().getLastActivity()instanceof OrderListActivity)){
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        } else if (isNeedRefreshList)
        {
            SDEventManager.post(new EOrderListRefresh());
        }
    }
}
