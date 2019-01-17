package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SD2LvCategoryView;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.CategoryCateLeftAdapter;
import com.fanwe.o2o.adapter.CategoryCateRightAdapter;
import com.fanwe.o2o.adapter.CategoryQuanLeftAdapter;
import com.fanwe.o2o.adapter.CategoryQuanRightAdapter;
import com.fanwe.o2o.adapter.StoresListAdapter;
import com.fanwe.o2o.appview.CommonTitleSearchView;
import com.fanwe.o2o.appview.StoresListTitleView;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.event.EStoresList;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppStoresIndexActModel;
import com.fanwe.o2o.model.Bcate_listModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.model.Quan_listModel;
import com.fanwe.o2o.model.StoreModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近好店
 * Created by Administrator on 2017/1/18.
 */

public class StoresListActivity extends BaseTitleActivity
{
    /**
     * 关键字搜索
     */
    public static final String EXTRA_KEYWORD = "extra_keyword";
    /**
     * 小分类请求参数cate_id
     */
    public static final String EXTRA_CATE_ID = "extra_cate_id";
    /**
     * 大分类的id
     */
    public static final String EXTRA_BIG_ID = "extra_big_id";

//    @ViewInject(R.id.lcv_left)
//    private SD2LvCategoryView lcv_left;
//    @ViewInject(R.id.lcv_middle)
//    private SD2LvCategoryView lcv_middle;
//    @ViewInject(R.id.ll_distance)
//    private LinearLayout ll_distance;
//    @ViewInject(R.id.tv_distance)
//    private TextView tv_distance;
//    @ViewInject(R.id.ll_newest)
//    private LinearLayout ll_newest;
//    @ViewInject(R.id.tv_newest)
//    private TextView tv_newest;
//    @ViewInject(R.id.ll_score)
//    private LinearLayout ll_score;
//    @ViewInject(R.id.tv_score)
//    private TextView tv_score;
//    @ViewInject(R.id.tv_cur_address)
//    private TextView tv_cur_address;
//    @ViewInject(R.id.iv_location)
//    private ImageView iv_location;
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;
    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;

    private StoresListAdapter adapter;
    private List<StoreModel> listModel;

//    // ====================提交服务端参数
    /** 大分类id */
    private int big_id;
    /** 小分类id */
    private int small_id;
    /** 商圈id */
    private int qid;
    /** 排序类型 */
    private String order_type = null;
    /** 小分类请求参数id */
    private int cate_id;

//    private SDViewNavigatorManager viewManager = new SDViewNavigatorManager();
//    private boolean isNeedBindLeft = true;
//    private boolean isNeedBindMiddle = true;
    private PageModel page = new PageModel();

    private StoresListTitleView titleView;
    private MoreTitleDialog titleDialog;
    private String keyWord;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_stores_list);
        initData();
        setScrollChange();
//        locationAddress();
//        initCategoryView();
//        initCategoryViewNavigatorManager();
        setAdapter();
        initPullToRefresh();
    }

    private void initData()
    {
        keyWord = getIntent().getStringExtra(EXTRA_KEYWORD);
        cate_id = getIntent().getIntExtra(EXTRA_CATE_ID,0);
        big_id =getIntent().getIntExtra(EXTRA_BIG_ID,0);
        CommonTitleSearchView titleSearchView = new CommonTitleSearchView(this);
        title.setCustomViewMiddle(titleSearchView);
        if (!TextUtils.isEmpty(keyWord))
            titleSearchView.setHintTip(keyWord);
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
//        title.setMiddleTextTop("附近好店");
        listModel = new ArrayList<>();
        order_type = "distance";
//        SDViewUtil.startAnimationDrawable(iv_location.getDrawable());
//        SDViewUtil.setTextViewColorResId(tv_distance,R.color.main_color);
//        SDViewUtil.setTextViewColorResId(tv_newest,R.color.text_content_deep);
//        SDViewUtil.setTextViewColorResId(tv_score,R.color.text_content_deep);
//        ll_distance.setOnClickListener(this);
//        ll_newest.setOnClickListener(this);
//        ll_score.setOnClickListener(this);
//        iv_location.setOnClickListener(this);

        iv_back_to_top.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lv_content.getRefreshableView().smoothScrollToPosition(0);
            }
        });
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

    private void storesListTitleView(List<Bcate_listModel> bacte_list,List<Quan_listModel> quan_list)
    {
        if (titleView == null)
        {
            titleView = new StoresListTitleView(this);
            lv_content.getRefreshableView().addHeaderView(titleView);
        }
        titleView.setData(bacte_list,quan_list,cate_id,big_id);
    }

    /**
     * 定位地址
     */
//    private void locationAddress()
//    {
//        // 开始定位
//        setCurrentLocation("定位中", false);
//        BaiduMapManager.getInstance().startLocation(new BDLocationListener()
//        {
//            @Override
//            public void onReceiveLocation(BDLocation location)
//            {
//                if (BaiduMapManager.getInstance().hasLocationSuccess(location))
//                {
//                    setCurrentLocation(BaiduMapManager.getInstance().getCurAddressShort(), true);
//                }
//            }
//        });
//    }
//
//    private void setCurrentLocation(String string, boolean isLocationSuccess)
//    {
//        if (!TextUtils.isEmpty(string))
//        {
//            SDViewBinder.setTextView(tv_cur_address,string);
//            if (isLocationSuccess)
//            {
//                SDViewUtil.stopAnimationDrawable(iv_location.getDrawable());
//                requestData(false);
//            }
//        }
//    }
//
//    private void initCategoryViewNavigatorManager()
//    {
//        SDViewBase[] items = new SDViewBase[] { lcv_left, lcv_middle};
//        viewManager.setItems(items);
//        viewManager.setmMode(SDViewNavigatorManager.Mode.CAN_NONE_SELECT);
//    }
//
//    private void initCategoryView()
//    {
//        lcv_left.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down);
//        lcv_left.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up);
//
//        lcv_left.getmAttr().setmTextColorNormalResId(R.color.text_content_deep);
//        lcv_left.getmAttr().setmTextColorSelectedResId(R.color.main_color);
//        lcv_left.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener()
//        {
//
//            @Override
//            public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel)
//            {
//                Bcate_listModel left = (Bcate_listModel) leftModel;
//                Bcate_listModel right = (Bcate_listModel) rightModel;
//                big_id = left.getId();
//                small_id = right.getId();
//                cate_id = right.getCate_id();
//                requestData(false);
//            }
//
//            @Override
//            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
//            {
//                if (isNotifyDirect)
//                {
//                    Bcate_listModel left = (Bcate_listModel) leftModel;
//                    Bcate_listModel right = SDCollectionUtil.get(left.getBcate_type(), 0);
//                    big_id = left.getId();
//                    if (right != null)
//                    {
//                        small_id = right.getId();
//                        cate_id = right.getCate_id();
//                    } else
//                    {
//                        small_id = 0;
//                        cate_id = 0;
//                    }
//                    requestData(false);
//                }
//            }
//        });
//
//        lcv_middle.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down_2);
//        lcv_middle.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up_2);
//
//        lcv_middle.getmAttr().setmTextColorNormalResId(R.color.text_content_deep);
//        lcv_middle.getmAttr().setmTextColorSelectedResId(R.color.main_color);
//        lcv_middle.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener()
//        {
//
//            @Override
//            public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel)
//            {
//                Quan_listModel right = (Quan_listModel) rightModel;
//                qid = right.getId();
//                requestData(false);
//            }
//
//            @Override
//            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
//            {
//                if (isNotifyDirect)
//                {
//                    Quan_listModel left = (Quan_listModel) leftModel;
//                    Quan_listModel right = SDCollectionUtil.get(left.getQuan_sub(), 0);
//                    if (right != null)
//                    {
//                        qid = right.getId();
//                    }
//                    if (qid <= 0)
//                    {
//                        qid = left.getId();
//                    }
//                    requestData(false);
//                }
//            }
//        });
//
//    }

    private void setAdapter()
    {
        adapter = new StoresListAdapter(listModel, this);
        lv_content.setAdapter(adapter);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<StoreModel>()
        {
            @Override
            public void onClick(int position, StoreModel item, View view)
            {
                clickWebView(item.getApp_url());
            }
        });
        adapter.setListener(new StoresListAdapter.OnClickStore()
        {
            @Override
            public void onBill(StoreModel model)
            {
                //买单
                clickWebView(model.getApp_url());
            }

            @Override
            public void onPurCoupon(StoreModel model)
            {
                //购买优惠券
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=store_pay&id=" + model.getId();
                clickWebView(url);
            }
        });
    }

    private void clickWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(this, AppWebViewActivity.class);
            intent.putExtra(AppWebViewActivity.EXTRA_URL,url);
            startActivity(intent);
        }else
            SDToast.showToast("url为空");
    }

    protected void initPullToRefresh()
    {
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
                    lv_content.addFooter(StoresListActivity.this,page.getPage_size());
                    lv_content.onRefreshComplete();
                } else
                {
                    requestData(true);
                }
            }
        });
        requestData(false);
    }

    protected void requestData(final boolean isLoadMore)
    {
        showProgressDialog("");
        CommonInterface.requestStoreWapIndex(page.getPage(), cate_id, qid, order_type, keyWord, new AppRequestCallback<AppStoresIndexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
//                    if (isNeedBindLeft)
//                    {
//                        bindLeftCategoryViewData(actModel.getBcate_list());
//                        isNeedBindLeft = false;
//                    }
//
//                    if (isNeedBindMiddle)
//                    {
//                        bindMiddleCategoryViewData(actModel.getQuan_list());
//                        isNeedBindMiddle = false;
//                    }

                    storesListTitleView(actModel.getBcate_list(),actModel.getQuan_list());

                    page.update(actModel.getPage());
                    List<StoreModel> item = actModel.getItem();
                    if (item != null && item.size() > 0)
                        SDViewUtil.hide(ll_no_content);
                    else
                        SDViewUtil.show(ll_no_content);
                    SDViewUtil.updateAdapterByList(listModel, item, adapter, isLoadMore);
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

//    private void bindLeftCategoryViewData(List<Bcate_listModel> list)
//    {
//        if (!SDCollectionUtil.isEmpty(list))
//        {
//            int[] arrIndex = Bcate_listModel.findIndex(big_id, small_id, list);
//            int leftIndex = arrIndex[0];
//            int rightIndex = arrIndex[1];
//
//            Bcate_listModel leftModel = list.get(leftIndex);
//            List<Bcate_listModel> listRight = leftModel.getBcate_type();
//
//            CategoryCateLeftAdapter adapterLeft = new CategoryCateLeftAdapter(list, this);
//            adapterLeft.setmDefaultIndex(leftIndex);
//
//            CategoryCateRightAdapter adapterRight = new CategoryCateRightAdapter(listRight, this);
//            adapterRight.setmDefaultIndex(rightIndex);
//
//            lcv_left.setLeftAdapter(adapterLeft);
//            lcv_left.setRightAdapter(adapterRight);
//            lcv_left.setAdapterFinish();
//        }
//    }
//
//    private void bindMiddleCategoryViewData(List<Quan_listModel> list)
//    {
//        if (!SDCollectionUtil.isEmpty(list))
//        {
//            int[] arrIndex = Quan_listModel.findIndex(qid, list);
//            int leftIndex = arrIndex[0];
//            int rightIndex = arrIndex[1];
//
//            Quan_listModel leftModel = list.get(leftIndex);
//            List<Quan_listModel> listRight = leftModel.getQuan_sub();
//
//            CategoryQuanLeftAdapter adapterLeft = new CategoryQuanLeftAdapter(list, this);
//            adapterLeft.setmDefaultIndex(leftIndex);
//
//            CategoryQuanRightAdapter adapterRight = new CategoryQuanRightAdapter(listRight, this);
//            adapterRight.setmDefaultIndex(rightIndex);
//
//            lcv_middle.setLeftAdapter(adapterLeft);
//            lcv_middle.setRightAdapter(adapterRight);
//            lcv_middle.setAdapterFinish();
//        }
//    }


//    @Override
//    public void onClick(View v)
//    {
//        super.onClick(v);
//        if (v == ll_distance)
//        {
//            order_type = "distance";
//            SDViewUtil.setTextViewColorResId(tv_distance,R.color.main_color);
//            SDViewUtil.setTextViewColorResId(tv_newest,R.color.text_content_deep);
//            SDViewUtil.setTextViewColorResId(tv_score,R.color.text_content_deep);
//            requestData(false);
//        }else if (v == ll_newest)
//        {
//            order_type = "newest";
//            SDViewUtil.setTextViewColorResId(tv_distance,R.color.text_content_deep);
//            SDViewUtil.setTextViewColorResId(tv_newest,R.color.main_color);
//            SDViewUtil.setTextViewColorResId(tv_score,R.color.text_content_deep);
//            requestData(false);
//        }else if (v == ll_score)
//        {
//            order_type = "avg_point";
//            SDViewUtil.setTextViewColorResId(tv_distance,R.color.text_content_deep);
//            SDViewUtil.setTextViewColorResId(tv_newest,R.color.text_content_deep);
//            SDViewUtil.setTextViewColorResId(tv_score,R.color.main_color);
//            requestData(false);
//        }else if (v == iv_location)
//        {
//            clickLocation();
//        }
//    }
//
//    private void clickLocation()
//    {
//        SDViewUtil.startAnimationDrawable(iv_location.getDrawable());
//        locationAddress();
//    }

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
                if(getScrollY() >= 50)
                {
                    SDViewUtil.show(iv_back_to_top);
                }else
                {
                    SDViewUtil.hide(iv_back_to_top);
                }
            }

            public int getScrollY()
            {
                int top = 0;
                if (titleView != null)
                    top = titleView.getTop();
                return SDViewUtil.px2dp(Math.abs(top));
            }

        });
    }

    public void onEventMainThread(EStoresList event)
    {
        if (event != null)
        {
            cate_id = event.getCate_id();
            qid = event.getQid();
            order_type = event.getOrder_type();
            requestData(false);
        }
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        requestData(false);
    }
}
