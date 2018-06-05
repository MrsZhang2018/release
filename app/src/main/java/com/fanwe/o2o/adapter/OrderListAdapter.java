package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.HorizontalScrollViewPageIndicator;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.OperationModel;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemModel;
import com.fanwe.o2o.model.UcOrderItemModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by luod on 2017/3/2.
 */

public class OrderListAdapter extends SDSimpleAdapter<UcOrderItemModel> {
    public OrderListAdapter(List<UcOrderItemModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_order_list;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UcOrderItemModel model) {
        TextView tv_count = get(R.id.tv_count, convertView);
        TextView tv_app_format_total_price = get(R.id.tv_app_format_total_price, convertView);
        LinearLayout ll_wait_pay = get(R.id.ll_wait_pay, convertView);
        HorizontalScrollViewPageIndicator sv_btn = get(R.id.sv_btn, convertView);
        SDGridLinearLayout gv_deal_order_content = get(R.id.gv_deal_order_content, convertView);

        if (model.getIs_pay() == 1) {
            SDViewUtil.show(ll_wait_pay);
            SDViewBinder.setTextView(tv_count, "共" + model.getCount() + "件商品需付款:");
            SDViewBinder.setTextView(tv_app_format_total_price, "￥" + model.getApp_format_total_price());
        } else {
            SDViewUtil.hide(ll_wait_pay);
        }

        setItemAdapter(gv_deal_order_content, position, model, convertView);
        setHorizontalScrollViewAdapter(sv_btn, model);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position, model, convertView);
                }
            }
        });
    }

    private void setHorizontalScrollViewAdapter(HorizontalScrollViewPageIndicator sv_btn, UcOrderItemModel model) {
        List<OperationModel> list = new ArrayList<>();
        for (int i = 0; i < model.getOperation().size(); i++) {
            OperationModel operationModel = new OperationModel();
            list.add(operationModel);
        }
        Collections.copy(list, model.getOperation());
        Collections.reverse(list);
        OrderListBtnAdapter adapter = new OrderListBtnAdapter(list, getActivity());
        adapter.setId(model.getId());
        sv_btn.setAdapter(adapter);
    }


    private void setItemAdapter(SDGridLinearLayout gv_content, final int position, final UcOrderItemModel model, final View convertView) {
        List<UcOrderItemDealOrderItemModel> itemList = model.getDeal_order_item();
        OrderListItemAdapter itemAdapter = new OrderListItemAdapter(itemList, getActivity());
        itemAdapter.setOnHsvClickListener(new OrderListItemAdapter.OnHsvClickListener() {
            @Override
            public void onHsvClickReceive(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(position, model, convertView);
                }
            }
        });
        gv_content.setColNumber(1);
        gv_content.setAdapter(itemAdapter);
        gv_content.postInvalidateDelayed(200);
    }
}
