package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.app.App;
import com.fanwe.o2o.appview.ClassificationSearchView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.HotKeyModel;
import com.fanwe.o2o.model.SearchHistoryDaoModel;

import java.util.List;

/**
 * 搜索
 * Created by luodong on 2016/12/9
 */
public class SearchActivity extends BaseTitleActivity
{
    private FlowLayout flowlayout;
    private ClassificationSearchView classificationSearchView;
    private LinearLayout ll_search_history;
    private ImageView iv_clear;
    private SDDialogConfirm dialog;
    private LinearLayout ll_search_hot;
    private FlowLayout flowlayout_hot;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_o2o_search);
        initTitle();
        initView();
        register();
        requestHot();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setFlowlayout();
    }

    private void register()
    {
        iv_clear.setOnClickListener(this);
        classificationSearchView.getSearch_et_input().setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                startSearch();
                return false;
            }
        });
    }

    private void requestHot()
    {
        CommonInterface.reuqestHotSearch(new AppRequestCallback<HotKeyModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    List<String> hot_kw = actModel.getHot_kw();
                    if (hot_kw != null && hot_kw.size() > 0)
                    {
                        SDViewUtil.show(ll_search_hot);
                        for (String model : hot_kw)
                        {
                            View view = createView(model);
                            if (view != null)
                            {
                                flowlayout_hot.addView(view);
                            }
                        }
                    }
                }
            }
        });
    }

    private void initView()
    {
        ll_search_history = find(R.id.ll_search_history);
        flowlayout = find(R.id.flowlayout);
        iv_clear = find(R.id.iv_clear);
        ll_search_hot = find(R.id.ll_search_hot);
        flowlayout_hot = find(R.id.flowlayout_hot);
    }

    private void setFlowlayout()
    {
        List<String> list = SearchHistoryDaoModel.get();
        flowlayout.removeAllViews();
        if (list != null)
        {
            SDViewUtil.show(ll_search_history);
            for (String model : list)
            {
                View view = createView(model);
                if (view != null)
                {
                    flowlayout.addView(view);
                }
            }
        } else
            SDViewUtil.hide(ll_search_history);
    }

    private View createView(final String model)
    {
        final View view = getLayoutInflater().inflate(R.layout.item_o2o_search_tag, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(model);
        tv_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (view.getParent() != null)
                {
                    if (view.getParent() == flowlayout)
                    {
                        intentAndChangeHistory(model);
                    }else
                    {
                        intentListActivity(classificationSearchView.getTv_type().getTag().toString(), model);      //跳转wap搜索结果页面
                    }
                }
            }
        });
        return view;
    }

    private void initTitle()
    {
        title.initRightItem(1);
        title.getItemRight(0).setTextBot("搜索");

        classificationSearchView = new ClassificationSearchView(this);
        title.setCustomViewMiddle(classificationSearchView);
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        super.onCLickRight_SDTitleSimple(v, index);
        startSearch();
    }

    private void startSearch()
    {
        String searchString = classificationSearchView.getSearch_et_input().getText().toString().trim();
        if (TextUtils.isEmpty(searchString))
        {
            SDToast.showToast("请输入要搜索的内容");
        } else
        {
            intentAndChangeHistory(searchString);//跳转以及修改历史记录
            classificationSearchView.getSearch_et_input().setText("");
            SDViewUtil.hideInputMethod(classificationSearchView.getSearch_et_input());
        }
    }

    private void intentAndChangeHistory(String searchString)
    {
        intentListActivity(classificationSearchView.getTv_type().getTag().toString(), searchString);      //跳转wap搜索结果页面
        SearchHistoryDaoModel.removeAfterSet(searchString);
        setFlowlayout();//更新搜索记录
    }

    private void intentListActivity(String ctl, String searchString)
    {
//        Intent intent = new Intent(this, AppWebViewActivity.class);
//        String url = ApkConstant.SERVER_URL_WAP;
//        UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
//        urlBuilder.add("ctl", ctl);
//        urlBuilder.add("keyword", searchString);
//        intent.putExtra(AppWebViewActivity.EXTRA_URL, urlBuilder.build());
//        startActivity(intent);

        clickSearchBtn(ctl, searchString);
    }

    /**
     * 搜索
     */
    private void clickSearchBtn(String ctl, String search_text)
    {
        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        switch (ctl)
        {
//            case "youhui": // 优惠
//                intent.setClass(App.getApplication(), CouponsListActivity.class);
//                startActivity(intent);
//                break;
            case "tuan": // 团购
                intent.setClass(App.getApplication(), GroupPurchaseListActivity.class);
                intent.putExtra(GroupPurchaseListActivity.EXTRA_KEYWORD, search_text);
                startActivity(intent);
                break;
            case "stores": // 商家
                intent.setClass(App.getApplication(), StoresListActivity.class);
                intent.putExtra(StoresListActivity.EXTRA_KEYWORD, search_text);
                startActivity(intent);
                break;
            case "events": // 活动
                intent.setClass(App.getApplication(), ActivitiesListActivity.class);
                intent.putExtra(ActivitiesListActivity.EXTRA_KEYWORD, search_text);
                startActivity(intent);
                break;
            case "goods": // 商城
                intent.setClass(App.getApplication(), GoodsRecyclerActivity.class);
                intent.putExtra(GoodsRecyclerActivity.EXTRA_KEYWORD, search_text);
                startActivity(intent);
                break;
            default:
                SDToast.showToast("未知搜索类型");
                break;
        }
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_clear:
                showClearDialog();
                break;
        }
    }

    private void showClearDialog()
    {
        if (dialog == null)
        {
            dialog = new SDDialogConfirm(this);
            dialog.setTextGravity(Gravity.CENTER);
            dialog.setTextContent("确定要清空历史数据？");
            dialog.setTextTitle("");
            dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
            {
                @Override
                public void onClickCancel(View v, SDDialogCustom dialog)
                {

                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog)
                {
                    SearchHistoryDaoModel.clear();
                    setFlowlayout();//更新搜索记录
                }

                @Override
                public void onDismiss(SDDialogCustom dialog)
                {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }
}
