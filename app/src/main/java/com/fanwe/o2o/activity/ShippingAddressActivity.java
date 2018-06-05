package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.ShippingAddressAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.AddAddressDialog;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshAddressDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppGroupPurListIndexActModel;
import com.fanwe.o2o.model.ShippingAddressModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-01
 * Time: 17:37
 * 功能:
 */
public class ShippingAddressActivity extends BaseTitleActivity {
    private ListView address_list;
    private List<ShippingAddressModel> mShippingAddressModelList;
    private ShippingAddressAdapter mAddressAdapter;
    private RelativeLayout new_address;
    private MoreTitleDialog mMoreTitleDialog;
    private AddAddressDialog mSDDialogBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shipping_addres);
        init();
    }

    private void init() {
        initTitle();
        initView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        requestData();
    }

    private void initTitle() {
        title.setMiddleTextTop("我的收货地址");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
    }

    private void initView() {
        address_list = (ListView) findViewById(R.id.lv_address);
        new_address = (RelativeLayout) findViewById(R.id.address_new_address);
        new_address.bringToFront();
        new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    mSDDialogBase = new AddAddressDialog(ShippingAddressActivity.this);
//                    mSDDialogBase.showBottom();

                Intent intent = new Intent(ShippingAddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void requestData() {
        mShippingAddressModelList = new ArrayList<ShippingAddressModel>();
        showProgressDialog("");
        CommonInterface.requestShippingAddressListIndex(new AppRequestCallback<AppGroupPurListIndexActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    mShippingAddressModelList = actModel.getConsignee_list();
                    setAdapter();
                    dismissProgressDialog();
                }
            }
        });
    }

    private void setAdapter() {
        if (mShippingAddressModelList == null) {
            return;
        }
        mAddressAdapter = new ShippingAddressAdapter(mShippingAddressModelList, this);
        address_list.setAdapter(mAddressAdapter);
        mAddressAdapter.setListener(new ShippingAddressAdapter.AddressClick() {
            //编辑
            @Override
            public void onEditClick(int position, ShippingAddressModel model) {
//                    mSDDialogBase = new AddAddressDialog(ShippingAddressActivity.this, model);
//                    mSDDialogBase.showBottom();

                Intent intent = new Intent(ShippingAddressActivity.this,AddAddressActivity.class);
                intent.putExtra(AddAddressActivity.EXTRA_MODEL,model);
                startActivity(intent);
            }

            //设置默认
            @Override
            public void onDefaultClick(final ShippingAddressModel default_model, final ShippingAddressModel model) {
                if(default_model == model){
                    return;
                }else {
                    if(model.getIs_default() ==1){
                        return;
                    }else if(model.getIs_default() == 0){
                        showProgressDialog("");
                        CommonInterface.requestDefaultShippingAddressListIndex(model.getId(), new AppRequestCallback<AppGroupPurListIndexActModel>() {
                            @Override
                            protected void onSuccess(SDResponse sdResponse) {
                                model.setIs_default(1);
                                default_model.setIs_default(0);
                                mAddressAdapter.notifyDataSetChanged();
                                dismissProgressDialog();
                            }
                        });
                    }
                }

            }

            //删除
            @Override
            public void onDeleteClick(final int position, ShippingAddressModel model) {
                if(model.getIs_default() == 1){
                    Toast.makeText(ShippingAddressActivity.this,"无法删除默认地址",Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("");
                CommonInterface.requestDeleteShippingAddressListIndex(model.getId(), new AppRequestCallback<AppGroupPurListIndexActModel>() {
                    @Override
                    protected void onSuccess(SDResponse sdResponse) {
                        mAddressAdapter.removeItem(position);
                        mAddressAdapter.notifyDataSetChanged();
                        Toast.makeText(ShippingAddressActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    }

                });
            }
        });
    }

    /**
     * 刷新listview
     */
    public void onEventMainThread(ERefreshAddressDialog event) {
        requestData();
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        super.onCLickRight_SDTitleSimple(v, index);
        if (mMoreTitleDialog == null) {
            mMoreTitleDialog = new MoreTitleDialog(this);
            mMoreTitleDialog.requestData();
            mMoreTitleDialog.showTop();
        } else {
            mMoreTitleDialog.showTop();
        }
    }


    public void onEventMainThread(ERefreshRequest event)
    {
        requestData();
    }
}
