package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.OrderDpAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.UcOrderOrderDpActModel;
import com.fanwe.o2o.model.UcOrderOrderDpItemModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xutils.view.annotation.ViewInject;

/**
 * 发表评价
 * Created by luod on 2017/3/7.
 */

public class PushEvaluateActivity extends BaseTitleActivity {
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.tv_push)
    private TextView tv_push;
    private OrderDpAdapter adapter;
    private List<UcOrderOrderDpItemModel> listModel = new ArrayList<>();
    private MoreTitleDialog titleDialog;
    private String id;
    private String order_id;
    public static final String EXTRA_ID = "extra_id";

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setContentView(R.layout.act_push_evaluate);
        getIntentData();
        initTitle();
        setAdapter();
        initPullToRefresh();
        register();
        requestData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_push:
                sendEvaluate();
                break;
        }
    }

    private void sendEvaluate() {
        String[] item_id = new String[adapter.getData().size()];
        for (int i = 0; i < item_id.length; i++) {
            item_id[i] = adapter.getData().get(i).getId();
        }

        String item_id_json="";
        for (int i = 0; i < item_id.length; i++) {
            item_id_json += item_id[i]+",";
        }
        String item_id_string = item_id_json.substring(0,item_id_json.length() - 1);


        Map content = new HashMap();
        for(int i=0;i<adapter.getData().size();i++){
            if(!TextUtils.isEmpty(adapter.getData().get(i).getContent())){
              content.put(item_id[i],adapter.getData().get(i).getContent());
            }else {
                SDToast.showToast("请填写评价内容");
                return;
            }
        }
        String content_json = SDJsonUtil.object2Json(content);


        Map point = new HashMap();
        for(int i=0;i<adapter.getData().size();i++){
            if(adapter.getData().get(i).getRating()>0) {
                point.put(item_id[i], adapter.getData().get(i).getRating());
            }else {
                SDToast.showToast("请选择评分");
                return;
            }
        }
        String point_json = SDJsonUtil.object2Json(point);

        sendData(item_id_string,content_json,point_json);
    }

    public void sendData(String item_id_json, String content_json, String point_json) {
        showProgressDialog("");
        CommonInterface.requestUcOrderOrderDpDo(order_id,item_id_json,content_json,point_json, new AppRequestCallback<UcOrderOrderDpActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    finish();
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
        });
    }

    private void register() {
        tv_push.setOnClickListener(this);
    }

    private void getIntentData() {
        id = getIntent().getStringExtra(EXTRA_ID);
    }

    private void setAdapter() {
        adapter = new OrderDpAdapter(listModel, PushEvaluateActivity.this);
        lv_content.setAdapter(adapter);
    }

    private void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.DISABLED);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
    }

    public void requestData() {
        showProgressDialog("");
        CommonInterface.requestUcOrderOrderDp(id, new AppRequestCallback<UcOrderOrderDpActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    SDViewUtil.updateAdapterByList(listModel, actModel.getItem(), adapter, false);
                    order_id = actModel.getOrder_id();
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
                lv_content.onRefreshComplete();
            }
        });
    }

    private void initTitle() {
        title.setMiddleTextTop("发表评价");
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

    public void onEventMainThread(ERefreshRequest event)
    {
        requestData();
        lv_content.getRefreshableView().smoothScrollToPosition(0);
    }

}
