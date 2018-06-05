package com.fanwe.o2o.model;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;

public class BaiduSuggestionSearchModel
{

	private int _id;

	private String name;
	private String address;
	private String district;
	private String latitude;
	private String longitude;

	private long time;

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public void updateTime()
	{
		setTime(System.currentTimeMillis());
	}

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public static List<BaiduSuggestionSearchModel> createList(SuggestionResult result)
	{
		List<BaiduSuggestionSearchModel> listModel = new ArrayList<BaiduSuggestionSearchModel>();

		if (result != null)
		{
			List<SuggestionInfo> listInfo = result.getAllSuggestions();
			if (listInfo != null)
			{
				for (SuggestionInfo info : listInfo)
				{
					BaiduSuggestionSearchModel model = new BaiduSuggestionSearchModel();
					model.setAddress(info.key);
					model.setDistrict(info.district);

					listModel.add(model);
				}
			}
		}
		return listModel;
	}

	public void setLatLng(LatLng latLng)
	{
		if (latLng != null)
		{
			setLatitude(String.valueOf(latLng.latitude));
			setLongitude(String.valueOf(latLng.longitude));
		}
	}

	@Override
	public String toString()
	{
		return address;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
