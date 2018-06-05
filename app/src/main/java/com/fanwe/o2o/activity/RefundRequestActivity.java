package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.OrderDetailsStoreAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.BaseActModel;
import com.fanwe.o2o.model.RefundRequestActModel;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemModel;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 退款申请
 * Created by luod on 2017/3/10.
 */

public class RefundRequestActivity extends BaseTitleActivity
{
    @ViewInject(R.id.gv_stroe)
    private SDGridLinearLayout gv_stroe;
    @ViewInject(R.id.tv_push)
    private TextView tv_push;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    private MoreTitleDialog titleDialog;
    private String deal_id;
    private String coupon_id;
    public static final String EXTRA_DEAL_ID = "extra_deal_id";
    public static final String EXTRA_COUPON_ID = "extra_coupon_id";
    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_refund_request);
        getIntentData();
        initTitle();
        register();
        requestData();
    }

    private void register() {
        tv_push.setOnClickListener(this);
    }


    private void getIntentData() {
        deal_id = getIntent().getStringExtra(EXTRA_DEAL_ID);
        coupon_id = getIntent().getStringExtra(EXTRA_COUPON_ID);
    }

    public void requestData() {
        showProgressDialog("");
        CommonInterface.requestUcOrderRefund(deal_id,coupon_id, new AppRequestCallback<RefundRequestActModel>() {
                    @Override
                    protected void onSuccess(SDResponse sdResponse) {
                        if (actModel.isOk()) {
                            setStroeAdapter(gv_stroe, actModel);
                        }
                    }

                    @Override
                    protected void onError(SDResponse resp) {
                        super.onError(resp);
                    }

                    @Override
                    protected void onFinish(SDResponse resp) {
                        super.onFinish(resp);
                        dismissProgressDialog();
                    }
                }

        );
    }

    private void setStroeAdapter(SDGridLinearLayout gv_content, RefundRequestActModel model) {
        List<UcOrderItemDealOrderItemModel> itemList = model.getItem();
        OrderDetailsStoreAdapter itemAdapter = new OrderDetailsStoreAdapter(itemList, this);
        gv_content.setColNumber(1);
        gv_content.setAdapter(itemAdapter);
        gv_content.postInvalidateDelayed(200);
    }

    private void initTitle() {
        title.setMiddleTextTop("退款申请");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        super.onCLickRight_SDTitleSimple(v, index);
        if (titleDialog == null)
            titleDialog = new MoreTitleDialog(this);
        titleDialog.requestData();
        titleDialog.showTop();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_push:
                requestUcOrderDoRefund();
                break;
        }
    }


    public void requestUcOrderDoRefund() {
        showProgressDialog("");
        CommonInterface.requestUcOrderDoRefund(deal_id,coupon_id,et_content.getText().toString(), new AppRequestCallback<BaseActModel>() {
                    @Override
                    protected void onSuccess(SDResponse sdResponse) {
                        if (actModel.isOk()) {
                            SDToast.showToast("提交成功，请等待审核");
                            finish();
                          if(SDActivityManager.getInstance().getLastActivity().getClass()==RefundGoodsActivity.class) { //回到订单详情
                            SDActivityManager.getInstance().getLastActivity().finish();
                          }
                        }
                    }

                    @Override
                    protected void onError(SDResponse resp) {
                        super.onError(resp);
                    }

                    @Override
                    protected void onFinish(SDResponse resp) {
                        super.onFinish(resp);
                        dismissProgressDialog();
                    }
                }

        );
    }

  public void onEventMainThread(ERefreshRequest event)
  {
    requestData();
  }

}
