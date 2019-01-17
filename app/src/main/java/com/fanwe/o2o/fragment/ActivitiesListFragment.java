package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.adapter.ActivitiesListAdapter;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppActivitesActModel;
import com.fanwe.o2o.model.EventModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/1/22.
 */

public class ActivitiesListFragment extends BaseFragment
{
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;

    private ActivitiesListAdapter adapter;
    private List<EventModel> listModel = new ArrayList<>();

    private String cate_id = null;
    private String keyWord;
    private PageModel page = new PageModel();

    public void setTabItem(String cate_id,String keyWord)
    {
        this.cate_id = cate_id;
        this.keyWord = keyWord;
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_activities_list;
    }

    @Override
    protected void init()
    {
        super.init();
        setAdapter();
        initPullToRefresh();
    }

    private void setAdapter()
    {
        adapter = new ActivitiesListAdapter(listModel,getActivity());
        lv_content.setAdapter(adapter);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<EventModel>()
        {
            @Override
            public void onClick(int position, EventModel item, View view)
            {
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=event&data_id=" + item.getId();
                clickWebView(url);
            }
        });
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }

    private void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
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
                    lv_content.addFooter(getActivity(),page.getPage_size());
                    lv_content.onRefreshComplete();
                } else
                {
                    requestData(true);
                }
            }
        });
        requestData(false);
    }

    public void requestData(final boolean isLoadMore)
    {
        showProgressDialog("");
        CommonInterface.requestActivitesWapIndex(page.getPage(), cate_id, keyWord, new AppRequestCallback<AppActivitesActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    page.update(actModel.getPage());
                    List<EventModel> item = actModel.getItem();
                    if (item != null && item.size() > 0)
                    {
                        SDViewUtil.hide(ll_no_content);
                        SDViewUtil.updateAdapterByList(listModel, item, adapter, isLoadMore);
                    }else
                        SDViewUtil.show(ll_no_content);
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
}
