package com.fanwe.o2o.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.fanwe.o2o.adapter.MailClassMenuAdapter;
import com.fanwe.o2o.adapter.MailClassMenuDetailsAdapter;
import com.fanwe.o2o.appview.CommonTitleSearchView;
import com.fanwe.o2o.common.CommonInterface;
import com.fanwe.o2o.dialog.MoreTitleDialog;
import com.fanwe.o2o.event.ERefreshRequest;
import com.fanwe.o2o.http.AppRequestCallback;
import com.fanwe.o2o.model.AppShopCateActModel;
import com.fanwe.o2o.model.ShopCateListModel;
import com.fanwe.o2o.model.ShopCateTypeModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.fanwe.o2o.activity.GoodsRecyclerActivity.EXTRA_BIG_ID;
import static com.fanwe.o2o.activity.GoodsRecyclerActivity.EXTRA_CATE_ID;

/**
 * 商城分类
 * Created by luodong on 2016/12/12
 */
public class MallClassificationActivity extends BaseTitleActivity
{

    @ViewInject(R.id.lv_content)
    private ListView lv_content;
    @ViewInject(R.id.gv_content)
    private GridView gv_content;
    private MailClassMenuAdapter adapter;
    private MailClassMenuDetailsAdapter gridAdapter;
    private List<ShopCateTypeModel> gridModel;

    private MoreTitleDialog titleDialog;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        setContentView(R.layout.act_mall_classification);
        initTitle();

        requestShopCate();
//        setListAdapter();
        setGridAdapter();
    }

    private void initTitle()
    {
//        View view = getLayoutInflater().inflate(R.layout.include_search_textview, null);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(layoutParams);
//        view.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MallClassificationActivity.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });
//        title.setCustomViewMiddle(view);
        CommonTitleSearchView titleSearchView = new CommonTitleSearchView(this);
        title.setCustomViewMiddle(titleSearchView);
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_title_more);
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

    private void setListAdapter(List<ShopCateListModel> listModel)
    {
        adapter = new MailClassMenuAdapter(listModel, this);
        adapter.getSelectManager().setSelected(0,true);
        adapter.setItemClickListener(new SDAdapter.ItemClickListener<ShopCateListModel>()
        {
            @Override
            public void onClick(int position, ShopCateListModel item, View view)
            {
                setGridData(item.getBcate_type());
            }
        });
        lv_content.setAdapter(adapter);
    }

    private void setGridAdapter()
    {
        gridModel = new ArrayList<ShopCateTypeModel>();
        gridAdapter = new MailClassMenuDetailsAdapter(gridModel, this);
        gridAdapter.setItemClickListener(new SDAdapter.ItemClickListener<ShopCateTypeModel>()
        {
            @Override
            public void onClick(int position, ShopCateTypeModel item, View view)
            {
                //clickWebView(item.getApp_url());
                final String url=item.getApp_url();
                if(!TextUtils.isEmpty(url)){
                    int startCate_id=url.indexOf("cate_id=");
                    int startBig_id=url.indexOf("&id=");
                    if(startCate_id<=0||startBig_id<=0){
                        return;
                    }

                    final String cata_id=url.substring(startCate_id+8,startBig_id);
                    final String big_id=url.substring(startBig_id+4,url.length());
                    Intent intent=new Intent(MallClassificationActivity.this,GoodsRecyclerActivity.class);
                    intent.putExtra(EXTRA_CATE_ID,Integer.parseInt(cata_id));
                    intent.putExtra(EXTRA_BIG_ID,Integer.parseInt(big_id));
                    startActivity(intent);
                }
            }
        });
        gv_content.setAdapter(gridAdapter);
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

    private void setGridData(List<ShopCateTypeModel> bcate_type)
    {
        SDViewUtil.updateAdapterByList(gridModel, bcate_type, gridAdapter, false);
    }

    private void requestShopCate()
    {
        showProgressDialog("");
        CommonInterface.requestShopCate(new AppRequestCallback<AppShopCateActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    List<ShopCateListModel> bcate_list = actModel.getBcate_list();
                    if (bcate_list != null && bcate_list.size() > 0)
                    {
                        setListAdapter(bcate_list);
                        setGridData(bcate_list.get(0).getBcate_type());
                    }
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
            }
        });
    }

    public void onEventMainThread(ERefreshRequest event)
    {
        requestShopCate();
    }
}
