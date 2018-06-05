package com.fanwe.o2o.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.o2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.DISABLED;

/**
 * Created by Administrator on 2016/7/5.
 */
public class SDProgressPullToRefreshListView extends PullToRefreshListView
{

  private View firstItem;
  private View footer;

    public SDProgressPullToRefreshListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //addFooter(context);
    }

    public SDProgressPullToRefreshListView(Context context, Mode mode, AnimationStyle style)
    {
        super(context, mode, style);
        //addFooter(context);
    }

    public SDProgressPullToRefreshListView(Context context, Mode mode)
    {
        super(context, mode);
        //addFooter(context);
    }

    public SDProgressPullToRefreshListView(Context context)
    {
        super(context);
        //addFooter(context);
    }

  /**
   * 用于下拉刷新onPullDownToRefresh时移除footer
   */
  public void removeFooter()
  {
    if (footer != null)
    {
      getRefreshableView().removeFooterView(footer);
    }
  }

    public void addFooter(Context context,int pageSize){
        int visibleCount=getRefreshableView().getLastVisiblePosition()-getRefreshableView().getFirstVisiblePosition();

        if(context!=null&&getRefreshableView().getFooterViewsCount()<2&&getMode()!=DISABLED){
          if(visibleCount==getRefreshableView().getAdapter().getCount()-1){
            SDToast.showToast("没有更多数据了");
          }else {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            footer=inflater.inflate(R.layout.view_refresh_footer,null);
            if(footer!=null) {
              getRefreshableView().addFooterView(footer);
            }
          }

        }
    }

  public int getScrolledY() {
    firstItem = getRefreshableView().getChildAt(0);
    if (firstItem == null) {
      return 0;
    }
    int firstVisiblePosition = getRefreshableView().getFirstVisiblePosition();
    int top = firstItem.getTop();
    return -top + firstVisiblePosition * firstItem.getHeight();
  }

  /**
   * 默认的列表滚动监听器 功能: 滑动到一定距离时展示iv_back_to_top,点击返回列表顶部
   * @param view_back_to_top
   */
  public void setDefalutScrollListener(final View view_back_to_top){
    if(view_back_to_top==null){
      throw new NullPointerException("view_back_to_top is null");
    }
    view_back_to_top.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getRefreshableView().smoothScrollToPosition(0);
      }
    });

    getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        if (getScrolledY() >= 700) {
          SDViewUtil.show(view_back_to_top);
        } else {
          SDViewUtil.hide(view_back_to_top);
        }
      }

    });
  }


    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs)
    {
        return new SDProgressLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
    }
}
