package com.fanwe.o2o.model;


import com.fanwe.o2o.utils.SDDistanceUtil;

public class StoreModel
{
	private int id;
	private String preview;
	private int is_verify; // 是否为认证商户 0:否 1:是
	private String name;
	private float avg_point;
	private String address;
	private String tel;
	private double distance;
	private int deal_count;
	private int youhui_count;
	private double xpoint;
	private double ypoint;
	private String promote_description;
	private String promote_info;
	private String dealcate_name;
	private String deal_cate_id;
	private String open_store_payment;
	private String supplier_id;
	private String app_url;
	private String quan_name;
	private String store_type;
	private int format_point;

	// ===============add
	private String distanceFormat;

	public String getPromote_info() {
		return promote_info;
	}

	public void setPromote_info(String promote_info) {
		this.promote_info = promote_info;
	}

	public String getApp_url() {
		return app_url;
	}

	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}

	public String getDeal_cate_id() {
		return deal_cate_id;
	}

	public void setDeal_cate_id(String deal_cate_id) {
		this.deal_cate_id = deal_cate_id;
	}

	public String getDealcate_name() {
		return dealcate_name;
	}

	public void setDealcate_name(String dealcate_name) {
		this.dealcate_name = dealcate_name;
	}

	public void setDistanceFormat(String distanceFormat) {
		this.distanceFormat = distanceFormat;
	}

	public int getFormat_point() {
		return format_point;
	}

	public void setFormat_point(int format_point) {
		this.format_point = format_point;
	}

	public String getOpen_store_payment() {
		return open_store_payment;
	}

	public void setOpen_store_payment(String open_store_payment) {
		this.open_store_payment = open_store_payment;
	}

	public String getQuan_name() {
		return quan_name;
	}

	public void setQuan_name(String quan_name) {
		this.quan_name = quan_name;
	}

	public String getStore_type() {
		return store_type;
	}

	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getPromote_description()
	{
		return promote_description;
	}

	public void setPromote_description(String promote_description)
	{
		this.promote_description = promote_description;
	}

	public int getDeal_count()
	{
		return deal_count;
	}

	public void setDeal_count(int deal_count)
	{
		this.deal_count = deal_count;
	}

	public int getYouhui_count()
	{
		return youhui_count;
	}

	public void setYouhui_count(int youhui_count)
	{
		this.youhui_count = youhui_count;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
		calculateDistance();
	}

	public double getDistance()
	{
		return distance;
	}

	public String getDistanceFormat()
	{
		return distanceFormat;
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

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getPreview()
	{
		return preview;
	}

	public void setPreview(String preview)
	{
		this.preview = preview;
	}

	public int getIs_verify()
	{
		return is_verify;
	}

	public void setIs_verify(int is_verify)
	{
		this.is_verify = is_verify;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getAvg_point()
	{
		return avg_point;
	}

	public void setAvg_point(float avg_point)
	{
		this.avg_point = avg_point;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public MapSearchBaseModel createMapSearchModel()
	{
		MapSearchBaseModel model = new MapSearchBaseModel();
		model.setId(id);
		model.setName(name);
		model.setXpoint(xpoint);
		model.setYpoint(ypoint);
		model.setAddress(address);
		return model;
	}

}