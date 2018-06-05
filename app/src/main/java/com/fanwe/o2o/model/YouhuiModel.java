package com.fanwe.o2o.model;

import com.fanwe.o2o.baidumap.BaiduMapManager;
import com.fanwe.o2o.utils.SDDistanceUtil;

import java.io.Serializable;

public class YouhuiModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int cid; // 收藏记录id
	private String name;
	private String list_brief;
	private String icon;
	private int down_count;// 下载量
	private String begin_time; // 起止时间
	private String youhui_type;// 0:满立减 1:折扣券
	private double xpoint;
	private double ypoint;
	private double distance;
	private String location_name;
	private int status;
	private String info;
	private float youhui_value;
	private int order_status;
	private String jump;

	// add
	private String distanceFormat;

	public String getJump() {
		return jump;
	}

	public void setJump(String jump) {
		this.jump = jump;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public float getYouhui_value() {
		return youhui_value;
	}

	public void setYouhui_value(float youhui_value) {
		this.youhui_value = youhui_value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
		calculateDistance();
	}

	public String getDistanceFormat()
	{
		return distanceFormat;
	}

	public int getCid()
	{
		return cid;
	}

	public void setCid(int cid)
	{
		this.cid = cid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getList_brief()
	{
		return list_brief;
	}

	public void setList_brief(String list_brief)
	{
		this.list_brief = list_brief;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public int getDown_count()
	{
		return down_count;
	}

	public void setDown_count(int down_count)
	{
		this.down_count = down_count;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getBegin_time()
	{
		return begin_time;
	}

	public void setBegin_time(String begin_time)
	{
		this.begin_time = begin_time;
	}

	public String getYouhui_type()
	{
		return youhui_type;
	}

	public void setYouhui_type(String youhui_type)
	{
		this.youhui_type = youhui_type;
	}

	public double getXpoint()
	{
		return xpoint;
	}

	public void setXpoint(double xpoint)
	{
		this.xpoint = xpoint;
//		calculateDistance();
	}

	public double getYpoint()
	{
		return ypoint;
	}

	public void setYpoint(double ypoint)
	{
		this.ypoint = ypoint;
//		calculateDistance();
	}

	public void calculateDistance()
	{
//		double dis = 0;
//		if (xpoint != 0 && ypoint != 0)
//		{
//			dis = BaiduMapManager.getInstance().getDistanceFromMyLocation(ypoint, xpoint);
//		}
		this.distanceFormat = SDDistanceUtil.getKmDistanceString(distance);
	}

	public MapSearchBaseModel createMapSearchModel()
	{
		MapSearchBaseModel model = new MapSearchBaseModel();
		model.setId(id);
		model.setName(name);
		model.setXpoint(xpoint);
		model.setYpoint(ypoint);
//		model.setAddress(address);
		return model;
	}

}
