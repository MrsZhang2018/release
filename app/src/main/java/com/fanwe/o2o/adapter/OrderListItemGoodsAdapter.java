package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.UcOrderItemDealOrderItemListModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

/**
 * 商品适配器
 *
 * @author luodong
 * @version 创建时间：2017-3-2
 */
public class OrderListItemGoodsAdapter extends SDSimpleAdapter<UcOrderItemDealOrderItemListModel> {

    public OrderListItemGoodsAdapter(List<UcOrderItemDealOrderItemListModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_order_list_item_goods;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, UcOrderItemDealOrderItemListModel model) {
        binddefaultData(position, convertView, parent, model);

    }

    private void binddefaultData(final int position, final View convertView, final ViewGroup parent, final UcOrderItemDealOrderItemListModel model) {
        ImageView iv_deal_icon_for_more = get(R.id.iv_deal_icon_for_more, convertView);
        TextView tv_number = get(R.id.tv_number, convertView);
        GlideUtil.load(model.getDeal_icon()).into(iv_deal_icon_for_more);
        SDViewBinder.setTextView(tv_number, model.getNumber());

    }

}
