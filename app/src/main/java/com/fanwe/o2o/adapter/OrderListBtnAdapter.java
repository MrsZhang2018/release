package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.model.OperationModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.List;

/**
 * 订单按钮适配器
 *
 * @author luodong
 * @version 创建时间：2017-3-2
 */
public class OrderListBtnAdapter extends SDSimpleAdapter<OperationModel> {

    private String id;
    public OrderListBtnAdapter(List<OperationModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_order_list_btn;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, OperationModel model) {
        binddefaultData(position, convertView, parent, model);

    }

    private void binddefaultData(final int position, final View convertView, final ViewGroup parent, final OperationModel model) {
        TextView btn_name = get(R.id.btn_name, convertView);
        SDViewBinder.setTextView(btn_name, model.getName());

        if(!TextUtils.isEmpty(model.getName())) {
            if ("去支付".equals(model.getName())) {
                btn_name.setBackgroundResource(R.drawable.selector_main_color);
                btn_name.setTextColor(SDResourcesUtil.getColor(R.color.white));
            }else {
                btn_name.setBackgroundResource(R.drawable.selector_white_gray_stroke_all);
                btn_name.setTextColor(SDResourcesUtil.getColor(R.color.text_content_deep));
            }
        }

        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppRuntimeWorker.clickOrderBtn(model.getType(),id,model.getUrl(),model.getParam().getCoupon_status());
            }
        });

    }

}
