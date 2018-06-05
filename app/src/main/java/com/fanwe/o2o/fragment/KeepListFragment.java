package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.ActivityCouponActivity;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.adapter.KeepListAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.event.EKeepListSelected;
import com.fanwe.o2o.event.ERefreshKeepActivity;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppKeepListActModel;
import com.fanwe.o2o.model.KeepItemModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.fanwe.o2o.adapter.KeepListAdapter.*;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2017-03-07
 * Time: 15:31
 * 功能:
 */

public class KeepListFragment extends BaseFragment {

    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;
    @ViewInject(R.id.tv_empty_content)
    private TextView tv_empty_content;

    private int keep_status;
    private PageModel page = new PageModel();
    private KeepListAdapter adapter;
    private List<KeepItemModel> listModel = new ArrayList<>();
    private String cancel_id;
    private String type;
    public boolean is_null ;

    public void setKeepStatus(int keep_status) {
        this.keep_status = keep_status;
    }

    public void setIs_select(int is_select) {
        for (int i = 0; i < listModel.size(); i++) {
            listModel.get(i).setIs_select(is_select);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.frg_keep_list;
    }

    @Override
    protected void init() {
        super.init();
        initPullToRefresh();
        setAdapter();
        requestData(false);
    }

    private void setAdapter() {
        adapter = new KeepListAdapter(listModel, getActivity(), keep_status);
        lv_content.setAdapter(adapter);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<KeepItemModel>() {
            @Override
            public void onClick(int position, KeepItemModel item, View view) {
                if (item.getIs_select() == 0) {
                    //// TODO: 17/3/14 未编辑状态下 点击item 
                    Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
                    intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
                    intent.putExtra(AppWebViewActivity.EXTRA_URL,item.getUrl());
                    getActivity().startActivity(intent);
                }
            }
        });
        adapter.setListener(new SelectAllListener() {
            @Override
            public void Selected() {
                EKeepListSelected event = new EKeepListSelected();
                if(SelectAllListener()){
                    event.select = true;
                }else {
                    event.select = false;
                }
                SDEventManager.post(event);
            }
        });

    }

    private void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page.resetPage();
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (!page.increment()) {
//                    SDToast.showToast("没有更多数据了");
                    lv_content.addFooter(getActivity(),page.getPage_size());
                    lv_content.onRefreshComplete();
                } else {
                    requestData(true);
                }
            }
        });
    }

    public void requestData(final boolean isLoadMore) {
        showProgressDialog("");
        CommonInterface.requestKeepListIndex(page.getPage(), keep_status, new AppRequestCallback<AppKeepListActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
//                    page.setPage(actModel.getPage().getPage_size());
                    page.update(actModel.getPage());
                    if ( actModel.getItem() != null &&  actModel.getItem().size() > 0) {
                        SDViewUtil.hide(ll_no_content);
                        is_null = false;
                    } else {
                        tv_empty_content.setText("暂无收藏");
                        SDViewUtil.show(ll_no_content);
                        is_null = true;
                    }
                    SDViewUtil.updateAdapterByList(listModel, actModel.getItem(), adapter, isLoadMore);
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

    public void selectAll(boolean is_select) {
        if (is_select) {
            for (int i = 0; i < listModel.size(); i++) {
                listModel.get(i).setIs_select(2);
            }
        } else {
            for (int i = 0; i < listModel.size(); i++) {
                listModel.get(i).setIs_select(1);
            }
        }
        adapter.notifyDataSetChanged();
    }
    private boolean SelectAllListener(){
        for (int i = 0; i < listModel.size(); i++) {
            if( listModel.get(i).getIs_select() == 1){
                return false;
            }
        }
        return true;
    }
    String cancel_id_all = "";

    public void CancelKeepList() {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).getIs_select() == 2) {
                cancel_id = listModel.get(i).getId() + ",";
                cancel_id_all += cancel_id;
            }
        }
        if (cancel_id_all != "") {
            cancel_id_all = cancel_id_all.substring(0, cancel_id_all.length() - 1);
        } else {
            Toast.makeText(getContext(),
                    "请选择要取消的收藏!!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (keep_status == 0) {
            type = "deal";
        } else if (keep_status == 1) {
            type = "youhui";
        } else if (keep_status == 2) {
            type = "event";
        }

        CommonInterface.requestCancelKeepIndex(cancel_id_all, type, new AppRequestCallback<AppKeepListActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                requestData(false);
                cancel_id_all = "";
                ERefreshKeepActivity event = new ERefreshKeepActivity();
                SDEventManager.post(event);
            }
        });
    }
}
