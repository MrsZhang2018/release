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
import com.fanwe.o2o.adapter.CouponsListAdapter;
import com.fanwe.o2o.appview.CouponsListTitleView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.constant.ApkConstant;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ECouponsList;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppCouponReceiveActModel;
import com.fanwe.o2o.model.AppCouponWapIndexActModel;
import com.fanwe.o2o.model.Bcate_listModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.model.Quan_listModel;
import com.fanwe.o2o.model.YouhuiModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshListView;
import com.fanwe.o2o.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sunday.eventbus.SDBaseEvent;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券列表
 * Created by Administrator on 2017/1/16.
 */

public class CouponsListActivity extends BaseTitleActivity
{

    ///**
    // * 关键字搜索
    // */
    //public static final String EXTRA_KEYWORD = "extra_keyword";
    /**
     * 小分类请求参数cate_id
     */
    public static final String EXTRA_CATE_ID = "extra_cate_id";
    /**
     * 大分类的id
     */
    public static final String EXTRA_BIG_ID = "extra_big_id";
//    @ViewInject(R.id.ll_title)
//    private LinearLayout ll_title;
//    @ViewInject(R.id.lcv_left)
//    private SD2LvCategoryView lcv_left;
//    @ViewInject(R.id.lcv_middle)
//    private SD2LvCategoryView lcv_middle;
//    @ViewInject(R.id.ll_highest_eval)
//    private LinearLayout ll_highest_eval;
//    @ViewInject(R.id.tv_highest_eval)
//    private TextView tv_highest_eval;
//    @ViewInject(R.id.ll_nearest)
//    private LinearLayout ll_nearest;
//    @ViewInject(R.id.tv_nearest)
//    private TextView tv_nearest;
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshListView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;
    @ViewInject(R.id.iv_back_to_top)
    private ImageView iv_back_to_top;

    private int tid;
//    // ====================提交服务端参数
    /** 大分类id */
    private int big_id;
//    /** 小分类id */
//    private int small_id;
    /** 商圈id */
    private int qid;
    /** 排序类型 */
    private String order_type = null;
    /** 小分类请求参数id */
    private int cate_id;

    private List<YouhuiModel> listModel;
    private CouponsListAdapter adapter;

//    private SDViewNavigatorManager viewManager = new SDViewNavigatorManager();
//    private boolean isNeedBindLeft = true;
//    private boolean isNeedBindMiddle = true;
    private PageModel page = new PageModel();

    private CouponsListTitleView titleView;
    private MoreTitleDialog titleDialog;
    //private String keyWord;

    @Override
    protected void onResume()
    {
        super.onResume();
//        requestData(false);
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_coupons_list);
        initData();
        setScrollChange();
//        initCategoryView();
//        initCategoryViewNavigatorManager();
        setAdapter();
        initPullToRefresh();
        requestData(false);
    }

    private void initData()
    {
        //keyWord = getIntent().getStringExtra(EXTRA_KEYWORD);
        cate_id = getIntent().getIntExtra(EXTRA_CATE_ID,0);
        big_id =getIntent().getIntExtra(EXTRA_BIG_ID,0);
        title.setMiddleTextTop("领券中心");
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
        listModel = new ArrayList<>();
        order_type = "distance";
//        SDViewUtil.setTextViewColorResId(tv_nearest,R.color.main_color);
//        SDViewUtil.setTextViewColorResId(tv_highest_eval,R.color.text_content_deep);
//        ll_highest_eval.setOnClickListener(this);
//        ll_nearest.setOnClickListener(this);

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

    private void couponListTitleView(List<Bcate_listModel> bacte_list,List<Quan_listModel> quan_list)
    {
        if (titleView == null)
        {
            titleView = new CouponsListTitleView(this/*,bacte_list,quan_list*/);
            lv_content.getRefreshableView().addHeaderView(titleView);
        }
        titleView.setData(bacte_list,quan_list,cate_id,big_id);
    }

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
        adapter = new CouponsListAdapter(listModel,this);
        lv_content.setAdapter(adapter);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<YouhuiModel>()
        {
            @Override
            public void onClick(int position, YouhuiModel item, View view)
            {
                String url = ApkConstant.SERVER_URL_WAP + "?ctl=youhui&data_id=" + item.getId();
                clickWebView(url);
            }
        });

        /**
         * 马上领取
         */
        adapter.setListener(new CouponsListAdapter.ClickReceive()
        {
            @Override
            public void onClickReceive(int position,YouhuiModel model)
            {
                if (!AppRuntimeWorker.isLogin())
                    clickLogin();
                else
                {
                    int status = model.getStatus();
                    if (status == 1)
                        clickCouponReceive(position,model.getId());
                    else if (status == 2)
                    {
                        String url = ApkConstant.SERVER_URL_WAP + model.getJump();
                        clickWebView(url);
                    }
                }
            }
        });
    }

    private void clickLogin()
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void clickCouponReceive(final int position, int id)
    {
        showProgressDialog("");
        CommonInterface.requestCouponReceive(id, new AppRequestCallback<AppCouponReceiveActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    YouhuiModel model = listModel.get(position);
                    YouhuiModel newModel = actModel.getData();
                    model.setStatus(newModel.getStatus());
                    model.setInfo(newModel.getInfo());
                    model.setOrder_status(newModel.getOrder_status());
                    adapter.updateData(position,model);
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
                lv_content.removeFooter();
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                if (!page.increment())
                {
                    lv_content.addFooter(CouponsListActivity.this,page.getPage_size());
                    lv_content.onRefreshComplete();
                } else
                {
                    requestData(true);
                }
            }
        });
    }

    protected void requestData(final boolean isLoadMore)
    {
        showProgressDialog("");
        CommonInterface.requestCouponWapIndex(page.getPage(), tid,cate_id, qid, order_type, new AppRequestCallback<AppCouponWapIndexActModel>()
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

                    couponListTitleView(actModel.getBcate_list(),actModel.getQuan_list());

                    page.update(actModel.getPage());
                    List<YouhuiModel> item = actModel.getItem();
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
//        if (v == ll_nearest)
//        {
//            order_type = "distance";
//            SDViewUtil.setTextViewColorResId(tv_nearest,R.color.main_color);
//            SDViewUtil.setTextViewColorResId(tv_highest_eval,R.color.text_content_deep);
//            requestData(false);
//        }else if (v == ll_highest_eval)
//        {
//            order_type = "avg_point";
//            SDViewUtil.setTextViewColorResId(tv_nearest,R.color.text_content_deep);
//            SDViewUtil.setTextViewColorResId(tv_highest_eval,R.color.main_color);
//            requestData(false);
//        }
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

    public void onEventMainThread(ECouponsList event)
    {
        if (event != null)
        {

            cate_id = event.getCate_id();
            qid = event.getQid();
            order_type = event.getOrder_type();
            tid = event.getTid();
            requestData(false);
        }
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        requestData(false);
        lv_content.getRefreshableView().smoothScrollToPosition(0);
    }
}
