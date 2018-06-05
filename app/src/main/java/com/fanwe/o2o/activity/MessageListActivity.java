package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.MessageListAdapter;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.model.UcMsgCateActModel;
import com.fanwe.o2o.model.UcMsgCateDataModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表
 * Created by luod on 2017/3/10.
 */

public class MessageListActivity extends BaseTitleActivity
{
    public static final String EXTRA_TYPE = "extra_type";
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;
    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;
    private MessageListAdapter adapter;
    private List<UcMsgCateDataModel> listModel = new ArrayList<>();
    private String type;

    private PageModel page = new PageModel();
    private MoreTitleDialog titleDialog;
    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_message_list);

        getIntentData();
        initTitle();
        setAdapter();
        initPullToRefresh();
        requestData(false);
    }

    private void getIntentData() {
        type = getIntent().getStringExtra(MessageListActivity.EXTRA_TYPE);
    }

    private void setAdapter()
    {
        adapter = new MessageListAdapter(listModel,this);
        lv_content.setAdapter(adapter);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<UcMsgCateDataModel>()
        {
            @Override
            public void onClick(int position, UcMsgCateDataModel item, View view)
            {
                Intent intent = AppRuntimeWorker.createIntentByType(item.getApp().getType(),item.getLink(),item.getData_id(),0);
                SDActivityUtil.startActivity(MessageListActivity.this, intent);
            }
        });
    }

    private void initPullToRefresh()
    {
        lv_content.setDefalutScrollListener(iv_back_to_top);
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                page.resetPage();
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                if (!page.increment())
                {
                    lv_content.addFooter(MessageListActivity.this,page.getPage_size());
                    lv_content.onRefreshComplete();
                } else
                {
                    requestData(true);
                }
            }
        });
    }

    public void requestData(final boolean isLoadMore)
    {
        showProgressDialog("");
        CommonInterface.requestUcMsgCate(type, new AppRequestCallback<UcMsgCateActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    page.update(actModel.getPage());
                    SDViewUtil.updateAdapterByList(listModel, actModel.getData(), adapter, isLoadMore);
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
                lv_content.onRefreshComplete();
            }
        });
    }


    private void initTitle()
    {
        switch (type){
            case "delivery":
                title.setMiddleTextTop("物流消息");
                break;
            case "notify":
                title.setMiddleTextTop("通知消息");
                break;
            case "account":
                title.setMiddleTextTop("资产消息");
                break;
            case "confirm":
                title.setMiddleTextTop("验证消息");
                break;
            default:
                title.setMiddleTextTop("消息列表");
                break;

        }
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

    public void onEventMainThread(ERefreshRequest event)
    {
        requestData(false);
        lv_content.getRefreshableView().smoothScrollToPosition(0);
    }

}
