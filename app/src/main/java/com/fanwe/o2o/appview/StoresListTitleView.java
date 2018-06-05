package com.fanwe.o2o.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.library.customview.SD2LvCategoryView;
import com.fanwe.library.customview.SDViewBase;
import com.fanwe.library.customview.SDViewNavigatorManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.CategoryCateLeftAdapter;
import com.fanwe.o2o.adapter.CategoryCateRightAdapter;
import com.fanwe.o2o.adapter.CategoryQuanLeftAdapter;
import com.fanwe.o2o.adapter.CategoryQuanRightAdapter;
import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.event.EStoresList;
import com.fanwe.o2o.model.Bcate_listModel;
import com.fanwe.o2o.model.Quan_listModel;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class StoresListTitleView extends SDAppView
{
    private SD2LvCategoryView lcv_left;
    private SD2LvCategoryView lcv_middle;
    private LinearLayout ll_distance;
    private TextView tv_distance;
    private LinearLayout ll_newest;
    private TextView tv_newest;
    private LinearLayout ll_score;
    private TextView tv_score;
    private TextView tv_cur_address;
    private ImageView iv_location;

    // ====================提交服务端参数
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

    private SDViewNavigatorManager viewManager = new SDViewNavigatorManager();
    private boolean locSuc;

    public StoresListTitleView(Context context)
    {
        super(context);
        init();
    }

    public StoresListTitleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public StoresListTitleView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<Bcate_listModel> bacte_list,List<Quan_listModel> quan_list,int cate_id,int big_id)
    {
        this.cate_id = big_id;
        this.small_id=cate_id;
        bindLeftCategoryViewData(bacte_list);
        bindMiddleCategoryViewData(quan_list);
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_stores_list);
        initView();
        initData();
        locationAddress();
        initCategoryView();
        initCategoryViewNavigatorManager();
    }

    private void initView()
    {
        lcv_left = find(R.id.lcv_left);
        lcv_middle = find(R.id.lcv_middle);
        ll_distance = find(R.id.ll_distance);
        tv_distance = find(R.id.tv_distance);
        ll_newest = find(R.id.ll_newest);
        tv_newest = find(R.id.tv_newest);
        ll_score = find(R.id.ll_score);
        tv_score = find(R.id.tv_score);
        tv_cur_address = find(R.id.tv_cur_address);
        iv_location = find(R.id.iv_location);
    }

    private void initData()
    {
        order_type = "distance";
        SDViewUtil.startAnimationDrawable(iv_location.getDrawable());
        SDViewUtil.setTextViewColorResId(tv_distance,R.color.main_color);
        SDViewUtil.setTextViewColorResId(tv_newest,R.color.text_content_deep);
        SDViewUtil.setTextViewColorResId(tv_score,R.color.text_content_deep);
        ll_distance.setOnClickListener(this);
        ll_newest.setOnClickListener(this);
        ll_score.setOnClickListener(this);
        iv_location.setOnClickListener(this);
    }

    /**
     * 定位地址
     */
    private void locationAddress()
    {
        // 开始定位
        setCurrentLocation("定位中", false);
        BaiduMapManager.getInstance().startLocation(new BDLocationListener()
        {
            @Override
            public void onReceiveLocation(BDLocation location)
            {
                if (BaiduMapManager.getInstance().hasLocationSuccess(location))
                {
                    setCurrentLocation(BaiduMapManager.getInstance().getCurAddressShort(), true);
                }
            }
        });
    }

    private void setCurrentLocation(String string, boolean isLocationSuccess)
    {
        if (!TextUtils.isEmpty(string))
        {
            SDViewBinder.setTextView(tv_cur_address,string);
            if (isLocationSuccess)
            {
                SDViewUtil.stopAnimationDrawable(iv_location.getDrawable());
                if (locSuc)
                {
                    locSuc = false;
                    SDEventManager.post(new EStoresList(cate_id,qid,order_type));
                }
            }
        }
    }

    private void initCategoryViewNavigatorManager()
    {
        SDViewBase[] items = new SDViewBase[] { lcv_left, lcv_middle};
        viewManager.setItems(items);
        viewManager.setmMode(SDViewNavigatorManager.Mode.CAN_NONE_SELECT);
    }

    private void initCategoryView()
    {
//        lcv_left.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down);
//        lcv_left.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up);

        lcv_left.getmAttr().setmTextColorNormalResId(R.color.text_content_deep);
        lcv_left.getmAttr().setmTextColorSelectedResId(R.color.main_color);
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
                big_id = left.getId();
                small_id = right.getId();
                cate_id = right.getCate_id();
                SDEventManager.post(new EStoresList(cate_id,qid,order_type));
            }

            @Override
            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
            {
                if (isNotifyDirect)
                {
                    Bcate_listModel left = (Bcate_listModel) leftModel;
                    Bcate_listModel right = SDCollectionUtil.get(left.getBcate_type(), 0);
                    big_id = left.getId();
                    if (right != null)
                    {
                        small_id = right.getId();
                        cate_id = right.getCate_id();
                    } else
                    {
                        small_id = 0;
                        cate_id = 0;
                    }
                    SDEventManager.post(new EStoresList(cate_id,qid,order_type));
                }
            }
        });

//        lcv_middle.getmAttr().setmBackgroundDrawableNormalResId(R.drawable.bg_choosebar_press_down_2);
//        lcv_middle.getmAttr().setmBackgroundDrawableSelectedResId(R.drawable.bg_choosebar_press_up_2);

        lcv_middle.getmAttr().setmTextColorNormalResId(R.color.text_content_deep);
        lcv_middle.getmAttr().setmTextColorSelectedResId(R.color.main_color);
        lcv_middle.setmListener(new SD2LvCategoryView.SD2LvCategoryViewListener()
        {
            @Override
            public void onSelect(boolean isSelected)
            {
                if (isSelected)
                    lcv_middle.mIvRight.setImageResource(R.drawable.ic_arrow_up_gray);
                else
                    lcv_middle.mIvRight.setImageResource(R.drawable.ic_arrow_down);
            }

            @Override
            public void onRightItemSelect(int leftIndex, int rightIndex, Object leftModel, Object rightModel)
            {
                Quan_listModel right = (Quan_listModel) rightModel;
                qid = right.getId();
                SDEventManager.post(new EStoresList(cate_id,qid,order_type));
            }

            @Override
            public void onLeftItemSelect(int leftIndex, Object leftModel, boolean isNotifyDirect)
            {
                if (isNotifyDirect)
                {
                    Quan_listModel left = (Quan_listModel) leftModel;
                    Quan_listModel right = SDCollectionUtil.get(left.getQuan_sub(), 0);
                    if (right != null)
                    {
                        qid = right.getId();
                    }
                    if (qid <= 0)
                    {
                        qid = left.getId();
                    }
                    SDEventManager.post(new EStoresList(cate_id,qid,order_type));
                }
            }
        });

    }

    private void bindLeftCategoryViewData(List<Bcate_listModel> list)
    {
        if (!SDCollectionUtil.isEmpty(list))
        {
            int[] arrIndex = Bcate_listModel.findIndex(cate_id, small_id, list);
            int leftIndex = arrIndex[0];
            int rightIndex = arrIndex[1];

            Bcate_listModel leftModel = list.get(leftIndex);
            List<Bcate_listModel> listRight = leftModel.getBcate_type();

            CategoryCateLeftAdapter adapterLeft = new CategoryCateLeftAdapter(list, getActivity());
            adapterLeft.setmDefaultIndex(leftIndex);

            CategoryCateRightAdapter adapterRight = new CategoryCateRightAdapter(listRight, getActivity());
            adapterRight.setmDefaultIndex(rightIndex);

            lcv_left.setLeftAdapter(adapterLeft);
            lcv_left.setRightAdapter(adapterRight);
            lcv_left.setAdapterFinish();
        }
    }

    private void bindMiddleCategoryViewData(List<Quan_listModel> list)
    {
        if (!SDCollectionUtil.isEmpty(list))
        {
            int[] arrIndex = Quan_listModel.findIndex(qid, list);
            int leftIndex = arrIndex[0];
            int rightIndex = arrIndex[1];

            Quan_listModel leftModel = list.get(leftIndex);
            List<Quan_listModel> listRight = leftModel.getQuan_sub();

            CategoryQuanLeftAdapter adapterLeft = new CategoryQuanLeftAdapter(list, getActivity());
            adapterLeft.setmDefaultIndex(leftIndex);

            CategoryQuanRightAdapter adapterRight = new CategoryQuanRightAdapter(listRight, getActivity());
            adapterRight.setmDefaultIndex(rightIndex);

            lcv_middle.setLeftAdapter(adapterLeft);
            lcv_middle.setRightAdapter(adapterRight);
            lcv_middle.setAdapterFinish();
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ll_distance)
        {
            order_type = "distance";
            SDViewUtil.setTextViewColorResId(tv_distance,R.color.main_color);
            SDViewUtil.setTextViewColorResId(tv_newest,R.color.text_content_deep);
            SDViewUtil.setTextViewColorResId(tv_score,R.color.text_content_deep);
            SDEventManager.post(new EStoresList(cate_id,qid,order_type));
        }else if (v == ll_newest)
        {
            order_type = "newest";
            SDViewUtil.setTextViewColorResId(tv_distance,R.color.text_content_deep);
            SDViewUtil.setTextViewColorResId(tv_newest,R.color.main_color);
            SDViewUtil.setTextViewColorResId(tv_score,R.color.text_content_deep);
            SDEventManager.post(new EStoresList(cate_id,qid,order_type));
        }else if (v == ll_score)
        {
            order_type = "avg_point";
            SDViewUtil.setTextViewColorResId(tv_distance,R.color.text_content_deep);
            SDViewUtil.setTextViewColorResId(tv_newest,R.color.text_content_deep);
            SDViewUtil.setTextViewColorResId(tv_score,R.color.main_color);
            SDEventManager.post(new EStoresList(cate_id,qid,order_type));
        }else if (v == iv_location)
        {
            clickLocation();
        }
    }

    private void clickLocation()
    {
        locSuc = true;
        SDViewUtil.startAnimationDrawable(iv_location.getDrawable());
        locationAddress();
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        lcv_left.disMissPopWindow();
        lcv_middle.disMissPopWindow();
    }
}
