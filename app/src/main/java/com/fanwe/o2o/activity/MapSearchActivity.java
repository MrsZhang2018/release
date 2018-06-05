package com.fanwe.o2o.activity;

import android.os.Bundle;

import com.fanwe.o2o.R;
import com.fanwe.o2o.constant.Constant;
//import com.fanwe.o2o.fragment.MapSearchEventFragment;
//import com.fanwe.o2o.fragment.MapSearchFragment;
//import com.fanwe.o2o.fragment.MapSearchStoreFragment;
//import com.fanwe.o2o.fragment.MapSearchTuanFragment;
//import com.fanwe.o2o.fragment.MapSearchYouhuiFragment;

/**
 * 地图附近
 * 
 * @author js02
 * 
 */
public class MapSearchActivity extends BaseActivity
{
	/**
	 * 传进来的值请引用SearchTypeMap类的常量
	 */
	public static final String EXTRA_SEARCH_TYPE = "extra_search_type";

	private int mSearchType;

//	private MapSearchFragment mFragMapSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_container);
		init();
	}

	private void init()
	{
		getIntentData();
		addFragment();
	}

	private void addFragment()
	{
//		switch (mSearchType)
//		{
//		case Constant.SearchTypeMap.EVENT:
//			mFragMapSearch = new MapSearchEventFragment();
//			break;
//		case Constant.SearchTypeMap.STORE:
//			mFragMapSearch = new MapSearchStoreFragment();
//			break;
//		case Constant.SearchTypeMap.TUAN:
//			mFragMapSearch = new MapSearchTuanFragment();
//			break;
//		case Constant.SearchTypeMap.YOU_HUI:
//			mFragMapSearch = new MapSearchYouhuiFragment();
//			break;
//
//		default:
//			break;
//		}
//		if (mFragMapSearch != null)
//		{
//			mFragMapSearch.setmSearchType(mSearchType);
//			getSDFragmentManager().replace(R.id.view_container_fl_content,mFragMapSearch);
//		}
	}

	private void getIntentData()
	{
		mSearchType = getIntent().getIntExtra(EXTRA_SEARCH_TYPE, Constant.SearchTypeMap.TUAN);
	}
}