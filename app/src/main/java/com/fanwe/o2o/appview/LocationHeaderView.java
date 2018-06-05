package com.fanwe.o2o.appview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.SearchCityNameAdapter;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.dialog.LocationCityDialog;
import com.fanwe.o2o.model.CityModel;
import com.fanwe.o2o.work.AppRuntimeWorker;

import java.util.List;

/**
 * 定位头部
 * Created by Administrator on 2016/12/12.
 */

public class LocationHeaderView extends SDAppView {

    private LinearLayout ll_search;
    public EditText search_et_input;
    private TextView tv_reset;
    private TextView tv_no_content;
    private FlowLayout flowlayout;

    private PopupWindow popupWindow;
    private List<CityModel> list;
    private LocationCityDialog dialog;

    public LocationHeaderView(Context context, LocationCityDialog dialog) {
        super(context);
        this.dialog = dialog;
        init();
    }

    public LocationHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LocationHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.view_location_header);
        initView();
        register();
    }

    private void initView() {
        ll_search = find(R.id.ll_search);
        search_et_input = find(R.id.search_et_input);
        tv_reset = find(R.id.tv_reset);
        tv_no_content = find(R.id.tv_no_content);
        flowlayout = find(R.id.flowlayout);
    }

    private void register() {
        search_et_input.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        search_et_input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    SDViewUtil.show(tv_no_content);
                    SDViewUtil.show(tv_reset);
                }
            }
        });
        search_et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = search_et_input.getText().toString();
                if (!TextUtils.isEmpty(content))
                {
                    SDViewUtil.show(tv_reset);
                }
                else
                {
                    SDViewUtil.hide(tv_reset);
                    SDViewUtil.hide(tv_no_content);
                }
                showPopupWindow(content);
            }
        });

        //
        search_et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if(list==null||list.size()==0){
                   SDToast.showToast("没有该城市");
                   return false;
               }else {
                   SDViewUtil.hideInputMethod(search_et_input);
                   return true;
               }
            }
        });
    }

    private void getContainsCityList(String string) {
        list = AppRuntimeWorker.getContainsCityList(string);
    }

    private void showPopupWindow(String value) {
        if(popupWindow!=null) {
            popupWindow.dismiss();
        }
        getContainsCityList(value);
        if (list != null && list.size() > 0) {
            SDViewUtil.hide(tv_no_content);
            View view = getPopListView(getActivity(), list, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popupWindow.dismiss();
                    CityModel model = list.get(position);
                    setCityName(model.getName());
                }
            });
            view.setBackgroundResource(R.drawable.bg_spinner_rectangle_radius);
            if (popupWindow == null) {
                popupWindow = new PopupWindow(view, ll_search.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//                popupWindow.setFocusable(true);
//                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
            }
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(ll_search, 0, 0);
        }else
            SDViewUtil.show(tv_no_content);
    }

    private View getPopListView(Activity activity, List<CityModel> list, AdapterView.OnItemClickListener itemClickListener) {
        View view = LayoutInflater.from(App.getApplication()).inflate(R.layout.pop_list, null);
        ListView lsv = (ListView) view.findViewById(R.id.list);
        SearchCityNameAdapter adapter = new SearchCityNameAdapter(list, activity);
        lsv.setAdapter(adapter);
        lsv.setOnItemClickListener(itemClickListener);
        return view;
    }

    public void setFlowlayout(List<CityModel> list) {
        flowlayout.removeAllViews();
        for (CityModel model : list) {
            View view = createView(model);
            if (view != null) {
                flowlayout.addView(view);
            }
        }

    }



    private View createView(final CityModel model) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_o2o_search_tag, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(model.getName());
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCityName(model.getName());
            }
        });
        return view;
    }

    private void setCityName(String value) {
        if (AppRuntimeWorker.setCity_name(value)) {
            dialog.dismiss();
        } else {
            SDToast.showToast("设置城市失败");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tv_reset) {
            clearPop();
        }else if (v == search_et_input)
        {
            search_et_input.setFocusable(true);
            SDViewUtil.showInputMethod(v);
        }
    }

    public void clearPop()
    {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
        SDViewUtil.hideInputMethod(search_et_input);
        search_et_input.setText("");
        search_et_input.setFocusable(false);
        SDViewUtil.hide(tv_reset);
        SDViewUtil.hide(tv_no_content);
    }
}
