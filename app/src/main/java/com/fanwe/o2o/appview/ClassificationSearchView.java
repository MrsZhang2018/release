package com.fanwe.o2o.appview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.O2oClassificationAdapter;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.model.SearchClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类搜索view
 * Created by Administrator on 2016/12/10.
 */

public class ClassificationSearchView extends SDAppView {
    private PopupWindow popupWindow;
    private TextView tv_type;
    private EditText search_et_input;
    private List<SearchClassModel> list;

    public ClassificationSearchView(Context context) {
        super(context);
        init();
    }

    public ClassificationSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClassificationSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.include_title_mid_search);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        tv_type = find(R.id.tv_type);
        search_et_input = find(R.id.search_et_input);
        search_et_input.setHint("搜索团购");

        setData();

        tv_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupWindow();
            }
        });
    }

    public EditText getSearch_et_input() {
        return search_et_input;
    }

    public TextView getTv_type() {
        return tv_type;
    }

    private void setData() {
        list = new ArrayList<SearchClassModel>();
        for (int i = 0; i < 4; i++) {
            SearchClassModel model = new SearchClassModel();
            if (i == 0) {
                model.setName("团购");
                model.setTag("tuan");
            }
            if (i == 1) {
                model.setName("商城");
                model.setTag("goods");
            }
            if (i == 2) {
                model.setName("活动");
                model.setTag("events");
            }
            if (i == 3) {
                model.setName("店铺");
                model.setTag("stores");
            }
//            if (i == 4) {
//                model.setName("优惠");
//                model.setTag("youhui");
//            }
            list.add(model);
        }
        tv_type.setTag(list.get(0).getTag());

    }

    private void setPopupWindow() {
        if (list != null && list.size() > 0) {
            if (popupWindow == null) {
                View view = getPopListView(getActivity(), list, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.dismiss();
                        SearchClassModel model = list.get(position);
                        String name = model.getName();
                        SDViewBinder.setTextView(tv_type, name);
                        tv_type.setTag(model.getTag());
                        search_et_input.setHint("搜索" + name);
                    }
                });
                view.setBackgroundResource(R.drawable.bg_spinner_rectangle_radius);

                popupWindow = new PopupWindow(view, tv_type.getMeasuredWidth() + SDViewUtil.dp2px(10), ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
            }
            popupWindow.showAsDropDown(tv_type, 0, 10);
        }

    }

    private View getPopListView(Activity activity, List<SearchClassModel> list, AdapterView.OnItemClickListener itemClickListener) {
        View view = LayoutInflater.from(App.getApplication()).inflate(R.layout.pop_list, null);
        ListView lsv = (ListView) view.findViewById(R.id.list);
        O2oClassificationAdapter adapter = new O2oClassificationAdapter(list, activity);
        lsv.setAdapter(adapter);
        lsv.setOnItemClickListener(itemClickListener);
        return view;
    }

}
