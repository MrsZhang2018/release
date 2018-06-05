package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.model.TabModel;

import java.util.List;

/**
 * 交易明细Tab适配器
 *
 * @author luodong
 * @version 创建时间：2016-10-29
 */
public class TabAdapter extends SDSimpleAdapter<TabModel>
{
    private OnActiviteTabsClickListener tabsClickListener;

    public TabAdapter(List<TabModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_activites_tab;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, TabModel model)
    {
        binddefaultData(position, convertView, parent, model);
    }

    private void binddefaultData(final int position, final View convertView, final ViewGroup parent, final TabModel model)
    {
        TextView tv_tab = get(R.id.tv_tab, convertView);
        View v_line = get(R.id.v_line, convertView);

        SDViewBinder.setTextView(tv_tab,model.getName());
        if (model.isSelected())
        {
            tv_tab.setTextColor(SDResourcesUtil.getColor(R.color.text_content_deep));
            SDViewUtil.show(v_line);
        }else
        {
            tv_tab.setTextColor(SDResourcesUtil.getColor(R.color.text_content));
            SDViewUtil.invisible(v_line);
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSelectManager().performClick(position);
                if(tabsClickListener != null)
                {
                    tabsClickListener.clickItem(v,position,model);
                }
            }
        });
    }

    public void setTabsClickListener(OnActiviteTabsClickListener tabsClickListener)
    {
        this.tabsClickListener = tabsClickListener;
    }

    public interface OnActiviteTabsClickListener
    {
        public void clickItem(View view, int position, TabModel model);
    }
}
