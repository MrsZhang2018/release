package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.o2o.R;
import com.fanwe.o2o.activity.AppWebViewActivity;
import com.fanwe.o2o.appview.ItemO2oGoodGuessLikeView;
import com.fanwe.o2o.config.AppConfig;
import com.fanwe.o2o.http.AppHttpUtil;
import com.fanwe.o2o.model.WapIndexDealListModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11.
 */

public class O2oHomeGoodsGuessLikeAdapter extends SDSimpleAdapter<List<WapIndexDealListModel>>
{
    public O2oHomeGoodsGuessLikeAdapter(List<List<WapIndexDealListModel>> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_o2o_good_guess_like;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, List<WapIndexDealListModel> model)
    {
        ItemO2oGoodGuessLikeView item0 = get(R.id.item_view0, convertView);
        ItemO2oGoodGuessLikeView item2 = get(R.id.item_view2, convertView);

        item0.setModel(SDCollectionUtil.get(model, 0));
        item2.setModel(SDCollectionUtil.get(model, 1));

        item0.setOnClickListener(clickHeadImageListener);
        item2.setOnClickListener(clickHeadImageListener);
    }

    private View.OnClickListener clickHeadImageListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            ItemO2oGoodGuessLikeView view = (ItemO2oGoodGuessLikeView) v;
            WapIndexDealListModel model = view.getModel();
            if (model == null)
            {
                SDToast.showToast("数据为空");
                return;
            }

            String app_url = model.getApp_url();
            if (!TextUtils.isEmpty(app_url))
            {
                if(!TextUtils.isEmpty(AppConfig.getSessionId())) {
                    AppHttpUtil.syncCookie(app_url, AppConfig.getSessionId());
                }
                Intent intent  = new Intent(getActivity(), AppWebViewActivity.class);
                intent.putExtra(AppWebViewActivity.EXTRA_IS_SHOW_TITLE,false);
                intent.putExtra(AppWebViewActivity.EXTRA_URL,app_url);
                getActivity().startActivity(intent);
            }else
                SDToast.showToast(" url为空");
        }
    };
}
