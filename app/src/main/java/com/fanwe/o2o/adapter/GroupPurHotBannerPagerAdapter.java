package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.GroupPurIndexAdvsModel;
import com.fanwe.o2o.model.WapIndexAdvsModel;
import com.fanwe.o2o.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class GroupPurHotBannerPagerAdapter extends SDPagerAdapter<GroupPurIndexAdvsModel>
{

    public GroupPurHotBannerPagerAdapter(List<GroupPurIndexAdvsModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public View getView(final ViewGroup container, final int position)
    {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_o2o_tab_hot_banner_pager, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_image);

        final GroupPurIndexAdvsModel model = getItemModel(position);
        GlideUtil.load(model.getImg()).placeholder(0).into(iv);

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (itemClicklistener != null)
                {
                    itemClicklistener.onClick(position, model, v);
                }
            }
        });

        return view;
    }
}
