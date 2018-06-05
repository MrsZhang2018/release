package com.fanwe.o2o.appview;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.ShopIndexSupplierDealListModel;
import com.fanwe.o2o.utils.GlideUtil;

/**
 * Created by Administrator on 2016/12/11.
 */

public class ItemShopGoodGuessLikeView extends SDAppView
{
    private ImageView iv_good;
    private TextView tv_good_name;
    private TextView tv_price;
    private TextView tv_org_price;
    private TextView tv_sold;

    private ShopIndexSupplierDealListModel model;

    public ItemShopGoodGuessLikeView(Context context)
    {
        super(context);
        init();
    }

    public ItemShopGoodGuessLikeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ItemShopGoodGuessLikeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void init()
    {
        super.init();
        setContentView(R.layout.item_mall_guess_like);
        initView();
    }

    private void initView()
    {
        iv_good = find(R.id.iv_good);
        tv_good_name = find(R.id.tv_good_name);
        tv_price = find(R.id.tv_price);
        tv_org_price = find(R.id.tv_org_price);
        tv_sold = find(R.id.tv_sold);

        ViewGroup.LayoutParams para = iv_good.getLayoutParams();
        para.width = SDViewUtil.getScreenWidth() / 2 - SDViewUtil.dp2px(2.5f);
        para.height = SDViewUtil.getScreenWidth() / 2 - SDViewUtil.dp2px(2.5f);
        iv_good.setLayoutParams(para);
        tv_org_price.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG );
    }

    public ShopIndexSupplierDealListModel getModel()
    {
        return model;
    }

    public void setModel(ShopIndexSupplierDealListModel model)
    {
        this.model = model;
        if (model != null)
        {
            String buy_count = model.getBuy_count();
            float org_price = model.getOrigin_price();
            GlideUtil.load(model.getIcon()).into(iv_good);
            SDViewBinder.setTextView(tv_good_name,model.getName());
            SDViewBinder.setTextView(tv_price,"￥ " + model.getCurrent_price());
            if (org_price != 0)
                SDViewBinder.setTextView(tv_org_price, "￥ " + String.valueOf(org_price));
            if (!TextUtils.isEmpty(buy_count) && !buy_count.equals("0"))
                SDViewBinder.setTextView(tv_sold,"已售" + buy_count);
        }
    }
}
