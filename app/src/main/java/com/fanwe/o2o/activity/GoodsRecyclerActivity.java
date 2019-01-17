package com.fanwe.o2o.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SD2LvCategoryView;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.listener.SDItemClickListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.CategoryCateLeftAdapter;
import com.fanwe.o2o.adapter.CategoryCateRightAdapter;
import com.fanwe.o2o.adapter.GoodsRecyclerAdapter;
import com.fanwe.o2o.appview.CommonTitleSearchView;
import com.fanwe.o2o.appview.GoodsBrandPopWindowGridView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.EDismissBrandPopWindow;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppGoodsWapIndexActModel;
import com.fanwe.o2o.model.Bcate_listModel;
import com.fanwe.o2o.model.Brand_listModel;
import com.fanwe.o2o.model.GoodsModel;
import com.fanwe.o2o.model.GroupPurChaseBrandListDaoModel;
import com.fanwe.o2o.model.PageModel;
import com.fanwe.o2o.view.SDProgressPullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 商品列表
 * 这里的Bcate_listModel有两层 外层(显示在lcv_left的dialog左边)cate_id(外层并没有所谓的cate_id 只有id)和id后台没有传反,内层(显示在lcv_left的dialog右边),后台将cate_id和id的值传反了
 * Created by Administrator on 2017/1/19.
 */

public class GoodsRecyclerActivity extends BaseTitleActivity
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

    @ViewInject(R.id.ll_all)
    private LinearLayout ll_all;
    @ViewInject(R.id.lcv_left)
    private SD2LvCategoryView lcv_left;//分类
    @ViewInject(R.id.ll_brand)
    private LinearLayout ll_brand;//品牌
    @ViewInject(R.id.tv_brand)
    private TextView tv_brand;
    @ViewInject(R.id.ll_price)
    private LinearLayout ll_price;//价格
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.ll_sales)
    private LinearLayout ll_sales;//销量
    @ViewInject(R.id.tv_sales)
    private TextView tv_sales;
    @ViewInject(R.id.ll_show)
    private LinearLayout ll_show;//排列
    @ViewInject(R.id.iv_show)
    private ImageView iv_show;
    @ViewInject(R.id.lv_content)
    private SDProgressPullToRefreshRecyclerView lv_content;
    @ViewInject(R.id.ll_no_content)
    private LinearLayout ll_no_content;

    private PopupWindow popupWindow;
    private GoodsBrandPopWindowGridView brandView;

    private GoodsRecyclerAdapter adapter;

    private boolean hasInit=false;
    private boolean isFirstLoadData = true;  //是否为刚进页面时第一次加载数据

    // ====================提交服务端参数


    /** 大分类对应的id  //这里注意:后台cate_id和id的值传反了,故要调换回来 */
    private int big_id;
    /** 小分类对应的id //这里注意:后台cate_id和id的值传反了,故要调换回来 */
    private int small_id;
    /** 品牌id */
    private String bid;
    /** 价格排序 */
    private String order_type;
    /** 横向纵向展示 */
    private String isShow = SHOW_HOR;

    private static final String SHOW_HOR = "horizontal";
    private static final String SHOW_VER = "vertical";
    private boolean isHor;
    private boolean price_type;
    private SDViewNavigatorManager viewManager = new SDViewNavigatorManager();
    private boolean isNeedBindMiddle = true;
    private PageModel page = new PageModel();
    private MoreTitleDialog titleDialog;
    private String keyWord;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_goods_list);
        initData();
        initCategoryView();
        initCategoryViewNavigatorManager();
        setAdapterVer(2);
        initPullToRefresh();
    }

    private void initData()
    {
        keyWord = getIntent().getStringExtra(EXTRA_KEYWORD);
        small_id = getIntent().getIntExtra(EXTRA_CATE_ID,0);
        big_id =getIntent().getIntExtra(EXTRA_BIG_ID,0);  //big_id为-1则说明是通过AppJsHandler跳转过来的
        if(small_id==0&&big_id!=0){  //从首页和商城的菜单进入要作此处理//   小范围里的'全部分类' 特征是小范围id等于大范围id
            small_id=big_id;
        }
//        if(cate_id!=-1&&big_id==-1){  //首页菜单传来的EXTRA_CATE_ID 是大范围的
//            int tmp=cate_id;
//            cate_id=0;
//            big_id=tmp;
//        }
//
//        cate_id=cate_id>=0?cate_id:0;
//        big_id=big_id>=0?big_id:0;
        CommonTitleSearchView titleSearchView = new CommonTitleSearchView(this);
        title.setCustomViewMiddle(titleSearchView);
        if (!TextUtils.isEmpty(keyWord))
            titleSearchView.setHintTip(keyWord);
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
//        title.setMiddleTextTop("商品列表");

        ll_brand.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        ll_sales.setOnClickListener(this);
        ll_show.setOnClickListener(this);
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

    private void initCategoryViewNavigatorManager()
    {
        SDViewBase[] items = new SDViewBase[] { lcv_left};
        viewManager.setItems(items);
        viewManager.setmMode(SDViewNavigatorManager.Mode.CAN_NONE_SELECT);
    }

    private void initCategoryView()
    {
//        lcv_left.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down);
//        lcv_left.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up);

        lcv_left.getmAttr().setmTextColorNormalResId(R.color.text_content_deep);
        lcv_left.getmAttr().setmTextColorSelectedResId(R.color.main_color);
        lcv_left.setmListenerState(new SDViewBase.SDViewBaseListener()
        {
            @Override
            public void onNormal_SDViewBase(View v)
            {

            }

            @Override
            public void onSelected_SDViewBase(View v)
            {
                dismissBrandPopWindow();
            }

            @Override
            public void onFocus_SDViewBase(View v)
            {

            }

            @Override
            public void onPressed_SDViewBase(View v)
            {

            }
        });

        lcv_left.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener()
        {
            @Override
            public void onSelect(boolean isSelected)
            {
                if (isSelected)
                    lcv_left.mIvRight.setImageResource(R.drawable.ic_arrow_up_gray);
                else
                    lcv_left.mIvRight.setImageResource(R.drawable.ic_arrow_down);
            }

            @Override
            public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel)
            {
                Bcate_listModel left = (Bcate_listModel) leftModel;
                Bcate_listModel right = (Bcate_listModel) rightModel;
                big_id = right.getCate_id();  //这里注意:内层Bcate_listModel(right)后台cate_id和id的值传反了,故此处调换回来
                small_id = right.getId(); //这里注意:内层Bcate_listModel(right)后台cate_id和id的值传反了,故此处调换回来
                bid = "0";
                isNeedBindMiddle = true;
                page.resetPage();
                requestData(false);
            }

            @Override
            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
            {
                if (isNotifyDirect)
                {
                    Bcate_listModel left = (Bcate_listModel) leftModel;
                    Bcate_listModel right = SDCollectionUtil.get(left.getBcate_type(), 0);
                    if (right != null)
                    {
                        big_id = right.getCate_id(); //这里注意:内层Bcate_listModel(即right)后台cate_id和id的值传反了,故要调换回来
                        small_id = right.getId();//这里注意:内层Bcate_listModel(即right)后台cate_id和id的值传反了,故要调换回来
                    } else
                    {
                        big_id = 0;
                        small_id = 0;
                    }
                    requestData(false);
                }
            }
        });
    }

    private void setAdapterVer(int num)
    {
        adapter = new GoodsRecyclerAdapter(isShow,this);
        lv_content.getRefreshableView().setGridVertical(num);
        lv_content.getRefreshableView().setAdapter(adapter);
        adapter.setItemClickListener(new SDItemClickListener<GoodsModel>()
        {
            @Override
            public boolean onClick(int position, GoodsModel item, View view)
            {
                clickWebView(item.getApp_url());
                return false;
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
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase)
            {
                page.resetPage();
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase)
            {
                if (!page.increment())
                {
                    SDToast.showToast("没有更多数据了");
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
        int id=0;
        CommonInterface.requestGoodsWapIndex(page.getPage(), small_id, bid, order_type, keyWord, new AppRequestCallback<AppGoodsWapIndexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    bindLeftCategoryViewData(actModel.getBcate_list());

                    List<Brand_listModel> listDao = GroupPurChaseBrandListDaoModel.get();
                    if (!SDCollectionUtil.isEmpty(listDao))
                    {
                        GroupPurChaseBrandListDaoModel.clear();
                    }

                    List<Brand_listModel> brandList = actModel.getBrand_list();
                    if (brandList != null && brandList.size() > 0)
                    {
                        for (int i = 0; i < brandList.size(); i++)
                        {
                            GroupPurChaseBrandListDaoModel.removeAfterPut(brandList.get(i));
                        }
                    }

                    SDViewUtil.setTextViewColorResId(tv_price,R.color.text_content_deep);

                    List<GoodsModel> item = actModel.getItem();
                    page.update(actModel.getPage());
                    if (item != null && item.size() > 0)
                        SDViewUtil.hide(ll_no_content);
                    else
                        SDViewUtil.show(ll_no_content);
                    if (isLoadMore)
                        adapter.appendData(item);
                    else
                        adapter.updateData(item);
                    adapter.notifyDataSetChanged();
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

    private void bindLeftCategoryViewData(List<Bcate_listModel> list)
    {
        int[] arrIndex;
        if (!SDCollectionUtil.isEmpty(list))
        {
            if (big_id==-1)
            {
                arrIndex = Bcate_listModel.findIndex( small_id, list);
            }else
            {
                arrIndex = Bcate_listModel.findIndex(small_id, big_id, list);
            }
            int leftIndex = arrIndex[0];
            int rightIndex = arrIndex[1];

            Bcate_listModel leftModel = list.get(leftIndex);
            List<Bcate_listModel> listRight = leftModel.getBcate_type();

            CategoryCateLeftAdapter adapterLeft = new CategoryCateLeftAdapter(list, this);
            adapterLeft.setmDefaultIndex(leftIndex);

            CategoryCateRightAdapter adapterRight = new CategoryCateRightAdapter(listRight, this);
            adapterRight.setmDefaultIndex(rightIndex);

            lcv_left.setLeftAdapter(adapterLeft);
            lcv_left.setRightAdapter(adapterRight);
            lcv_left.setAdapterFinish();

            if(!hasInit){
                hasInit=true;
                //lcv_left.setSelectIndex(leftIndex,rightIndex);
                lcv_left.setAdapterFinish();
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ll_show)
        {
            adapterOrientation();
        }else if (v == ll_price)
        {
            priceSort();
        }else if (v == ll_sales)
        {
            sales();
        }else if (v == ll_brand)
        {
            clickBrand();
        }
    }

    private void clickBrand()
    {
        lcv_left.disMissPopWindow();
        SDViewUtil.setTextViewColorResId(tv_brand,R.color.main_color);
        tv_brand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_arrow_up_gray),null);
        if (popupWindow != null && popupWindow.isShowing())
        {
            popupWindow.dismiss();
            setTvBrand();
        }else
            showBrandPopupWindow();
    }

    private void setTvBrand()
    {
        SDViewUtil.setTextViewColorResId(tv_brand,R.color.text_content_deep);
        tv_brand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_arrow_down),null);
    }

    /**
     * 销量
     */
    private void sales()
    {
        order_type = "buy_count";
        SDViewUtil.setTextViewColorResId(tv_price,R.color.text_content_deep);
        SDViewUtil.setTextViewColorResId(tv_sales,R.color.main_color);
        requestData(false);
    }

    /**
     * 价格升序，降序
     */
    private void priceSort()
    {
        if(!price_type)
        {
            order_type = "price_asc";
            price_type = true;
            SDViewUtil.setTextViewColorResId(tv_price,R.color.main_color);
            tv_price.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_o2o_sales_up),null);
            SDViewUtil.setTextViewColorResId(tv_sales,R.color.text_content_deep);
            requestData(false);
        }else
        {
            order_type = "price_desc";
            price_type = false;
            SDViewUtil.setTextViewColorResId(tv_price,R.color.main_color);
            tv_price.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_o2o_sales_down),null);
            SDViewUtil.setTextViewColorResId(tv_sales,R.color.text_content_deep);
            requestData(false);
        }
    }

    /**
     * 列表横向，纵向排列
     */
    private void adapterOrientation()
    {
        if (!isHor)
        {
            isHor = true;
            isShow = SHOW_VER;
            iv_show.setImageResource(R.drawable.ic_o2o_class);
            page.resetPage();
            setAdapterVer(1);
            requestData(false);
        }else
        {
            isHor = false;
            isShow = SHOW_HOR;
            iv_show.setImageResource(R.drawable.ic_o2o_list);
            page.resetPage();
            setAdapterVer(2);
            requestData(false);
        }
    }

    /**
     * 品牌PopupWindow
     */
    private void showBrandPopupWindow()
    {
        List<Brand_listModel> brand_list = GroupPurChaseBrandListDaoModel.get();

        if (brandView == null)
        {
            brandView = new GoodsBrandPopWindowGridView(this);
            brandView.setBackgroundResource(R.drawable.bg_spinner_rectangle_radius);
        }
        if (isNeedBindMiddle)
        {
            brandView.setData(brand_list);
            isNeedBindMiddle = false;
        }
        if (popupWindow == null)
        {
            popupWindow = new PopupWindow(brandView, ll_all.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//                popupWindow.setFocusable(true);
//                popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
        }
        popupWindow.setContentView(brandView);
        popupWindow.showAsDropDown(ll_all, 0, 0);
    }

    public void onEventMainThread(EDismissBrandPopWindow event)
    {
        if (event != null)
        {
            dismissBrandPopWindow();
            bid = event.getBrandId();
            requestData(false);
        }
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        lcv_left.disMissPopWindow();
        dismissBrandPopWindow();
        requestData(false);
    }

    private void dismissBrandPopWindow()
    {
        if (popupWindow != null && popupWindow.isShowing())
        {
            popupWindow.dismiss();
            setTvBrand();
        }
    }
}
