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
import com.fanwe.o2o.event.EGroupPurchaseList;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.model.Bcate_listModel;
import com.fanwe.o2o.model.Quan_listModel;
import com.sunday.eventbus.SDEventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class GroupPurchaseListTitleView extends SDAppView
{
    private SD2LvCategoryView lcv_left;
    private SD2LvCategoryView lcv_middle;
    private LinearLayout ll_highest_eval;
    private TextView tv_highest_eval;
    private LinearLayout ll_nearest;
    private TextView tv_nearest;
    private TextView tv_cur_address;
    private ImageView iv_location;

    // ====================提交服务端参数
    /** 大分类id */
    private int big_id = -1;
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

    public GroupPurchaseListTitleView(Context context)
    {
        super(context);
        init();
    }

    public GroupPurchaseListTitleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GroupPurchaseListTitleView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setData(List<Bcate_listModel> bacte_list,List<Quan_listModel> quan_list,int cate_id,int big_id)
    {
        this.cate_id = cate_id;
        this.small_id=big_id;
        bindLeftCategoryViewData(bacte_list);
        bindMiddleCategoryViewData(quan_list);
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.view_group_pur_list_title);
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
        ll_highest_eval = find(R.id.ll_highest_eval);
        tv_highest_eval = find(R.id.tv_highest_eval);
        ll_nearest = find(R.id.ll_nearest);
        tv_nearest = find(R.id.tv_nearest);
        tv_cur_address = find(R.id.tv_cur_address);
        iv_location = find(R.id.iv_location);
    }

    private void initData()
    {
        order_type = "avg_point";
        SDViewUtil.startAnimationDrawable(iv_location.getDrawable());
        SDViewUtil.setTextViewColorResId(tv_highest_eval,R.color.main_color);
        SDViewUtil.setTextViewColorResId(tv_nearest,R.color.text_content_deep);
        ll_highest_eval.setOnClickListener(this);
        ll_nearest.setOnClickListener(this);
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
                    SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
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
                SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
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
                    SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
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
                SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
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
                    SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
                }
            }
        });
    }

    private void bindLeftCategoryViewData(List<Bcate_listModel> list)
    {
        Bcate_listModel leftModel=new Bcate_listModel();
        Bcate_listModel rightModel=new Bcate_listModel();
        List<Bcate_listModel> listRight=new ArrayList<>();
        int[] arrIndex;
//        if (order_type != null && order_type.equals("distance"))
//        {
            arrIndex = Bcate_listModel.findIndex(small_id, cate_id, list);
//        } else
//        {
//            arrIndex = Bcate_listModel.findIndex(cate_id, small_id, list);
//        }

        int leftIndex = arrIndex[0];
        int rightIndex = arrIndex[1];
        if (!SDCollectionUtil.isEmpty(list)) {
            leftModel = list.get(leftIndex);
            listRight = leftModel.getBcate_type();
        }else {
            rightModel.setCount(0);
            rightModel.setName("全部分类");
            leftModel.setCount(0);
            leftModel.setName("全部分类");
            list.add(leftModel);
            listRight.add(rightModel);
            leftModel.setBcate_type(listRight);


        }

        CategoryCateLeftAdapter adapterLeft = new CategoryCateLeftAdapter(list, getActivity());
        adapterLeft.setmDefaultIndex(leftIndex);

        CategoryCateRightAdapter adapterRight = new CategoryCateRightAdapter(listRight, getActivity());
        adapterRight.setmDefaultIndex(rightIndex);

        lcv_left.setLeftAdapter(adapterLeft);
        lcv_left.setRightAdapter(adapterRight);
        lcv_left.setAdapterFinish();

        if(TextUtils.isEmpty(lcv_left.getTitle().getText())){
            lcv_left.getTitle().setText("全部分类");
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
        if (v == ll_nearest)
        {
            order_type = "distance";
            SDViewUtil.setTextViewColorResId(tv_nearest,R.color.main_color);
            SDViewUtil.setTextViewColorResId(tv_highest_eval,R.color.text_content_deep);
            SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
        }else if (v == ll_highest_eval)
        {
            order_type = "avg_point";
            SDViewUtil.setTextViewColorResId(tv_nearest,R.color.text_content_deep);
            SDViewUtil.setTextViewColorResId(tv_highest_eval,R.color.main_color);
            SDEventManager.post(new EGroupPurchaseList(small_id,cate_id,qid,order_type));
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
