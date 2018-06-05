package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.ShippingAddressModel;

import java.util.List;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-02
 * Time: 10:50
 * 功能:
 */
public class ShippingAddressAdapter extends SDSimpleAdapter<ShippingAddressModel> {
    private AddressClick listener;
    private ShippingAddressModel default_model;

    public ShippingAddressAdapter(List<ShippingAddressModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_shipping_address;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final ShippingAddressModel model) {
        TextView name = get(R.id.address_name, convertView);
        TextView phone = get(R.id.address_phone, convertView);
        TextView address = get(R.id.address_address, convertView);
        Button is_default = get(R.id.address_default, convertView);
        LinearLayout exit = get(R.id.address_edit, convertView);
        LinearLayout delete = get(R.id.address_delete, convertView);

        if (model == null) {
            return;
        }
        String address_name = model.getConsignee();
        String address_phone = model.getMobile();
        String address_address = model.getFull_address();
        int isdefault = model.getIs_default();

        SDViewBinder.setTextView(name, address_name);
        SDViewBinder.setTextView(phone, address_phone);
        SDViewBinder.setTextView(address, address_address);
        if (isdefault == 1) {
            default_model = model;
            is_default.setBackgroundResource(R.drawable.ic_address_selected);
        } else if (isdefault == 0) {
            is_default.setBackgroundResource(R.drawable.ic_address_default);
        }

        is_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDefaultClick(default_model,model);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(position, model);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(position, model);
            }
        });

    }

    public void setListener(AddressClick listener) {
        this.listener = listener;
    }

    public interface AddressClick {
        void onEditClick(int position, ShippingAddressModel model);

        void onDefaultClick(ShippingAddressModel default_model,ShippingAddressModel model);

        void onDeleteClick(int position, ShippingAddressModel model);
    }

}
