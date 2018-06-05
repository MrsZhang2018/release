package com.fanwe.o2o.fragment;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.O2oHomeGoodsGuessLikeAdapter;
import com.fanwe.o2o.appview.MainSearchView;
import com.fanwe.o2o.appview.TabHomeHeaderView;
import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.event.EChangeCity;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.event.ERetryInitSuccess;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppHaveReadMsgActModel;
import com.fanwe.o2o.model.AppWapIndexActModel;
import com.fanwe.o2o.model.AppWapIndexDealListActModel;
import com.fanwe.o2o.model.LocalUserModel;
import com.fanwe.o2o.model.WapIndexAdvs2Model;
import com.fanwe.o2o.model.WapIndexAdvsModel;
import com.fanwe.o2o.model.WapIndexArticleModel;
import com.fanwe.o2o.model.WapIndexDealListModel;
import com.fanwe.o2o.model.WapIndexIndexsListModel;
import com.fanwe.o2o.model.WapIndexSupplierListModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaoneng.uiapi.Ntalker;

/**
 * 首页
 * Created by Administrator on 2016/12/8.
 */

public class HomeFragment extends BaseFragment
{
    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;
    @ViewInject(R.id.ll_search)
    private LinearLayout ll_search;
    @ViewInject(R.id.lv_content)
    protected PullToRefreshListView lv_content;
    @ViewInject(R.id.thhv_header_view)
    private TabHomeHeaderView tabHomeHeaderView;
    @ViewInject(R.id.scroll_container)
    private ScrollView scroll_container;
    private O2oHomeGoodsGuessLikeAdapter adapter;
    private List<List<WapIndexDealListModel>> listModel;

    private MainSearchView searchView;
    private TabHomeHeaderView headerView;

    protected int page;
    protected int has_next;
    private boolean isFirstApp=true; //是否初次打开app进程()
    private String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


  @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_o2o_tab_home;
    }

    @Override
    protected void init()
    {
        super.init();
        initData();
    }

    @Override
    public void onResume()
    {
      super.onResume();
      if(isFirstApp){
        isFirstApp=false;
        //延迟4秒 确保首次进入app,百度地图成功初始化 服务端会在requestHaveReadMsg这个请求中获取经纬度,用在wap商品详情里的定位
        SDHandlerManager.getMainHandler().postDelayed(new Runnable() {
          @Override public void run() {
            requestHaveReadMsg();
          }
        },4000);
      }else {
        requestHaveReadMsg();
      }
    }

    private void initData()
    {
        requestIndexData();
        setAdapter();
        initPullToRefresh();
        setScrollChange();
        register();
        initXiaoNeng();
    }

    private void initXiaoNeng()
    {
        int xiaoneng_login_status = -1;
        LocalUserModel localUserModel = LocalUserModelDao.queryModel();
        if (localUserModel!= null)
        {
            xiaoneng_login_status= Ntalker.getInstance().login(String.valueOf(localUserModel.getUser_id()), localUserModel.getUser_name(),0 );
        }

        Ntalker.getInstance().getPermissions(getActivity(),  200, permissions);
    }

    private void register()
    {
        iv_back_to_top.setOnClickListener(this);
    }

    private void searchView()
    {
        if (searchView == null)
        {
            searchView = new MainSearchView(getActivity());
            SDViewUtil.replaceView(ll_search, searchView);
        }
    }

    private void addHeaderView(List<WapIndexAdvsModel> advs, List<WapIndexAdvs2Model> advs2, List<WapIndexIndexsListModel> list, List<WapIndexArticleModel> article,
                               List<WapIndexSupplierListModel> supplier_list, String html3, String html4, String html5, String html6)
    {
        if (headerView == null)
        {
            headerView = new TabHomeHeaderView(getActivity(), this);
            lv_content.getRefreshableView().addHeaderView(headerView);
        }
        headerView.setData(advs, advs2,list, article, supplier_list, html3, html4, html5, html6);
    }

    private void setAdapter()
    {
        listModel = new ArrayList<List<WapIndexDealListModel>>();
        adapter = new O2oHomeGoodsGuessLikeAdapter(listModel, getActivity());
        lv_content.setAdapter(adapter);
    }

    protected void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestIndexData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData(true);
            }
        });
    }

    protected void requestData(final boolean isLoadMore)
    {
        if (isLoadMore)
        {
            if (has_next == 1)
            {
                page++;
            } else
            {
                lv_content.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            page = 1;
        }

        CommonInterface.requestLoadMoreData(page, new AppRequestCallback<AppWapIndexDealListActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    page = actModel.getPage();
                    has_next = actModel.getHas_next();
                    List<WapIndexDealListModel> deal_list = actModel.getDeal_list();
                    if (deal_list != null)
                        SDViewUtil.updateAdapterByList(listModel, SDCollectionUtil.splitList(deal_list, 2), adapter, isLoadMore);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                lv_content.onRefreshComplete();
            }
        });
    }

    private void requestIndexData()
    {
        showProgressDialog("");
        CommonInterface.requestWapIndex(new AppRequestCallback<AppWapIndexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    page = 1;
                    has_next = 1;
                    List<WapIndexAdvsModel> advs = actModel.getAdvs();
                    List<WapIndexAdvs2Model> advs2 = actModel.getAdvs2();
                    List<WapIndexIndexsListModel> list = actModel.getIndexs().getList();
                    List<WapIndexArticleModel> article = actModel.getArticle();
                    List<WapIndexSupplierListModel> supplier_list = actModel.getSupplier_list();
                    List<WapIndexDealListModel> deal_list = actModel.getDeal_list();
                    String html3 = actModel.getZt_html3();
                    String html4 = actModel.getZt_html4();
                    String html5 = actModel.getZt_html5();
                    String html6 = actModel.getZt_html6();

                    searchView();
                    if (searchView != null)
                        searchView.setCityName(actModel.getCity_name());

                    Log.i("info", "size:"+deal_list.size());
                    if (deal_list == null || deal_list.size() == 0)
                    {
                        tabHomeHeaderView.setHomeFragment(HomeFragment.this);
                        tabHomeHeaderView.ll_like.setVisibility(View.GONE);
                        SDViewUtil.show(scroll_container);
                        tabHomeHeaderView.setData(advs, advs2,list, article, supplier_list, html3, html4, html5, html6);
//                        tabHomeHeaderView.ll_like.setVisibility(View.GONE);
                        SDViewUtil.hide(lv_content);
                    }else
                    {
                        SDViewUtil.hide(scroll_container);
                        SDViewUtil.show(lv_content);
                        addHeaderView(advs, advs2, list, article, supplier_list, html3, html4, html5, html6);
                        SDViewUtil.updateAdapterByList(listModel, SDCollectionUtil.splitList(deal_list, 2), adapter, false);
                    }
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

    private void requestHaveReadMsg()
    {
        CommonInterface.requestHaveReadMsg(new AppRequestCallback<AppHaveReadMsgActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    int count = actModel.getCount();
                    searchView();
                    if (searchView != null)
                        searchView.setData(count);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    /**
     * listView的滑动监听
     */
    private void setScrollChange()
    {
        lv_content.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                float alpha = (float) getScrollY() / 200;
                if (alpha <= 0.6)
                {
                    if (searchView != null)
                        searchView.getViewTitleBg().setAlpha(alpha);
                }

                if (getScrollY() >= 200)
                {
                    SDViewUtil.show(iv_back_to_top);
                } else
                {
                    SDViewUtil.hide(iv_back_to_top);
                }
            }

            public int getScrollY()
            {
                int top = 0;
                if (headerView != null)
                    top = headerView.getTop();
                return SDViewUtil.px2dp(Math.abs(top));
            }

        });
    }

    private void changeCity()
    {
        searchView.showCityText();//切换城市
        requestIndexData();
    }

    public void onEventMainThread(EChangeCity event)
    {
        changeCity();
    }

    public void onEventMainThread(ERetryInitSuccess event)
    {
        searchView.dealChangeLocation();//重新判断地理位置
        changeCity();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back_to_top:
                adapter.notifyDataSetChanged();
                lv_content.getRefreshableView().setSelection(0);
                break;
        }
    }
}
