package com.fanwe.o2o.model;


import java.util.List;

/**
 * 
 * @author yhz
 * @create time 2014-7-16
 */
public class Init_indexActModel extends BaseActModel
{


	/**
	 * city_id : 15
	 * citylist : [{"id":"18","name":"北京","py":"beijing"},{"id":"24","name":"重庆","py":"chongqing"},{"id":"40","name":"长治","py":"changzhi"},{"id":"31","name":"大同","py":"datong"},{"id":"15","name":"福州","py":"fuzhou"},{"id":"44","name":"佛山","py":"foshan"},{"id":"27","name":"惠州","py":"huizhou"},{"id":"46","name":"河源","py":"heyuan"},{"id":"41","name":"晋城","py":"jincheng"},{"id":"49","name":"江门","py":"jiangmen"},{"id":"51","name":"济南","py":"jinan"},{"id":"33","name":"龙岩","py":"longyan"},{"id":"47","name":"梅州","py":"meizhou"},{"id":"34","name":"南平","py":"nanping"},{"id":"35","name":"宁德","py":"ningde"},{"id":"36","name":"莆田","py":"putian"},{"id":"32","name":"泉州","py":"quanzhou"},{"id":"19","name":"上海","py":"shanghai"},{"id":"22","name":"石家庄","py":"shijiazhuang"},{"id":"26","name":"深圳","py":"shenzhen"},{"id":"37","name":"三明","py":"sanming"},{"id":"42","name":"朔州","py":"shuozhou"},{"id":"43","name":"汕头","py":"shantou"},{"id":"45","name":"韶关","py":"shaoguan"},{"id":"39","name":"太原","py":"taiyuan"},{"id":"20","name":"厦门","py":"xiamen"},{"id":"52","name":"烟台","py":"yantou"},{"id":"28","name":"湛江","py":"zhanjiang"},{"id":"29","name":"珠海","py":"zhuhai"},{"id":"38","name":"漳州","py":"zhangzhou"},{"id":"48","name":"中山","py":"zhongshan"}]
	 * hot_city : [{"id":"51","name":"济南"},{"id":"26","name":"深圳"},{"id":"52","name":"烟台"},{"id":"29","name":"珠海"}]
	 * region_version : 1
	 * only_one_delivery : 0
	 * version : 3.05
	 * page_size : 10
	 * program_title : 方维O2O
	 * index_logo : http://o2o.fanwe.net/public/attachment/201202/04/16/4f2ce8336d784.png
	 * api_sina : 0
	 * sina_app_key :
	 * sina_app_secret :
	 * sina_bind_url :
	 * api_qq : 0
	 * qq_app_key :
	 * qq_app_secret :
	 * api_wx : 1
	 * wx_app_key : wx125bfb1866fd0d4d
	 * wx_app_secret : e07effc12efc7f72a709ee3d7e354a51
	 * start_page : null
	 * start_page_new : null
	 * user : null
	 * about_info : 1
	 * kf_phone : 400-000-0000
	 * kf_email : qq@fanwe.com
	 * is_fx : 1
	 * is_store_pay : 1
	 * is_dc : 1
	 * menu_user_withdraw : 1
	 * menu_user_charge : 1
	 * city_name : 福州
	 * return : 1
   * is_open_distribution :
   */

	private String city_id;
	private int region_version;
	private int only_one_delivery;
	private double version;
	private int page_size;
	private String program_title;
	private String index_logo;
	private int api_sina;
	private String sina_app_key;
	private String sina_app_secret;
	private String sina_bind_url;
	private int api_qq;
	private String qq_app_key;
	private String qq_app_secret;
	private int api_wx;
	private String wx_app_key;
	private String wx_app_secret;
	private List<InitActStart_pageModel> start_page_new;
	private User_infoModel user;
	private int about_info;
	private String kf_phone;
	private String kf_email;
	private int is_fx;  //1有分销功能 0无
	private int is_store_pay;
	private int is_dc;
	private int menu_user_withdraw;
	private int menu_user_charge;
	private String city_name;
	private int returnX;
  private int is_open_distribution;  //驿站是否开启 1开启 0关闭

	private List<CityModel> citylist;
	private List<CityModel> hot_city;
	/**
	 * serverVersion : 2015021001
	 * android_biz_filename : http://o2o.fanwe.net/o2ofanwe_biz.apk
	 * android_biz_forced_upgrade : 0
	 * android_biz_upgrade : 商家android升级测试
	 * android_version : 2015021001
	 * android_filename : http://o2o.fanwe.net/o2ofanwe_app.apk
	 * android_upgrade : android升级测试
	 * android_forced_upgrade : 0
	 * android_biz_master_secret :
	 * android_biz_app_key :
	 * android_master_secret :
	 * android_app_key :
	 * android_dist_version :
	 * android_dist_down_url :
	 * android_dist_upgrade :
	 * android_dist_forced_upgrade : 0
	 */

	private String serverVersion;
	private String android_biz_filename;
	private String android_biz_forced_upgrade;
	private String android_biz_upgrade;
	private String android_version;
	private String android_filename;
	private String android_upgrade;
	private String android_forced_upgrade;
	private String android_biz_master_secret;
	private String android_biz_app_key;
	private String android_master_secret;
	private String android_app_key;
	private String android_dist_version;
	private String android_dist_down_url;
	private String android_dist_upgrade;
	private String android_dist_forced_upgrade;

	public List<CityModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<CityModel> citylist) {
		this.citylist = citylist;
	}

	public List<CityModel> getHot_city() {
		return hot_city;
	}

	public void setHot_city(List<CityModel> hot_city) {
		this.hot_city = hot_city;
	}

	public List<InitActStart_pageModel> getStart_page_new() {
		return start_page_new;
	}

	public void setStart_page_new(List<InitActStart_pageModel> start_page_new) {
		this.start_page_new = start_page_new;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public int getRegion_version() {
		return region_version;
	}

	public void setRegion_version(int region_version) {
		this.region_version = region_version;
	}

	public int getOnly_one_delivery() {
		return only_one_delivery;
	}

	public void setOnly_one_delivery(int only_one_delivery) {
		this.only_one_delivery = only_one_delivery;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public String getProgram_title() {
		return program_title;
	}

	public void setProgram_title(String program_title) {
		this.program_title = program_title;
	}

	public String getIndex_logo() {
		return index_logo;
	}

	public void setIndex_logo(String index_logo) {
		this.index_logo = index_logo;
	}

	public int getApi_sina() {
		return api_sina;
	}

	public void setApi_sina(int api_sina) {
		this.api_sina = api_sina;
	}

	public String getSina_app_key() {
		return sina_app_key;
	}

	public void setSina_app_key(String sina_app_key) {
		this.sina_app_key = sina_app_key;
	}

	public String getSina_app_secret() {
		return sina_app_secret;
	}

	public void setSina_app_secret(String sina_app_secret) {
		this.sina_app_secret = sina_app_secret;
	}

	public String getSina_bind_url() {
		return sina_bind_url;
	}

	public void setSina_bind_url(String sina_bind_url) {
		this.sina_bind_url = sina_bind_url;
	}

	public int getApi_qq() {
		return api_qq;
	}

	public void setApi_qq(int api_qq) {
		this.api_qq = api_qq;
	}

	public String getQq_app_key() {
		return qq_app_key;
	}

	public void setQq_app_key(String qq_app_key) {
		this.qq_app_key = qq_app_key;
	}

	public String getQq_app_secret() {
		return qq_app_secret;
	}

	public void setQq_app_secret(String qq_app_secret) {
		this.qq_app_secret = qq_app_secret;
	}

	public int getApi_wx() {
		return api_wx;
	}

	public void setApi_wx(int api_wx) {
		this.api_wx = api_wx;
	}

	public String getWx_app_key() {
		return wx_app_key;
	}

	public void setWx_app_key(String wx_app_key) {
		this.wx_app_key = wx_app_key;
	}

	public String getWx_app_secret() {
		return wx_app_secret;
	}

	public void setWx_app_secret(String wx_app_secret) {
		this.wx_app_secret = wx_app_secret;
	}

	public User_infoModel getUser() {
		return user;
	}

	public void setUser(User_infoModel user) {
		this.user = user;
	}

	public int getAbout_info() {
		return about_info;
	}

	public void setAbout_info(int about_info) {
		this.about_info = about_info;
	}

	public String getKf_phone() {
		return kf_phone;
	}

	public void setKf_phone(String kf_phone) {
		this.kf_phone = kf_phone;
	}

	public String getKf_email() {
		return kf_email;
	}

	public void setKf_email(String kf_email) {
		this.kf_email = kf_email;
	}

	public int getIs_fx() {
		return is_fx;
	}

	public void setIs_fx(int is_fx) {
		this.is_fx = is_fx;
	}

	public int getIs_store_pay() {
		return is_store_pay;
	}

	public void setIs_store_pay(int is_store_pay) {
		this.is_store_pay = is_store_pay;
	}

	public int getIs_dc() {
		return is_dc;
	}

	public void setIs_dc(int is_dc) {
		this.is_dc = is_dc;
	}

	public int getMenu_user_withdraw() {
		return menu_user_withdraw;
	}

	public void setMenu_user_withdraw(int menu_user_withdraw) {
		this.menu_user_withdraw = menu_user_withdraw;
	}

	public int getMenu_user_charge() {
		return menu_user_charge;
	}

	public void setMenu_user_charge(int menu_user_charge) {
		this.menu_user_charge = menu_user_charge;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public int getReturnX() {
		return returnX;
	}

	public void setReturnX(int returnX) {
		this.returnX = returnX;
  }

  public int getIs_open_distribution() {
    return is_open_distribution;
  }

  public void setIs_open_distribution(int is_open_distribution) {
    this.is_open_distribution = is_open_distribution;
  }



	public String getServerVersion()
	{
		return serverVersion;
	}

	public void setServerVersion(String serverVersion)
	{
		this.serverVersion = serverVersion;
	}

	public String getAndroid_biz_filename()
	{
		return android_biz_filename;
	}

	public void setAndroid_biz_filename(String android_biz_filename)
	{
		this.android_biz_filename = android_biz_filename;
	}

	public String getAndroid_biz_forced_upgrade()
	{
		return android_biz_forced_upgrade;
	}

	public void setAndroid_biz_forced_upgrade(String android_biz_forced_upgrade)
	{
		this.android_biz_forced_upgrade = android_biz_forced_upgrade;
	}

	public String getAndroid_biz_upgrade()
	{
		return android_biz_upgrade;
	}

	public void setAndroid_biz_upgrade(String android_biz_upgrade)
	{
		this.android_biz_upgrade = android_biz_upgrade;
	}

	public String getAndroid_version()
	{
		return android_version;
	}

	public void setAndroid_version(String android_version)
	{
		this.android_version = android_version;
	}

	public String getAndroid_filename()
	{
		return android_filename;
	}

	public void setAndroid_filename(String android_filename)
	{
		this.android_filename = android_filename;
	}

	public String getAndroid_upgrade()
	{
		return android_upgrade;
	}

	public void setAndroid_upgrade(String android_upgrade)
	{
		this.android_upgrade = android_upgrade;
	}

	public String getAndroid_forced_upgrade()
	{
		return android_forced_upgrade;
	}

	public void setAndroid_forced_upgrade(String android_forced_upgrade)
	{
		this.android_forced_upgrade = android_forced_upgrade;
	}

	public String getAndroid_biz_master_secret()
	{
		return android_biz_master_secret;
	}

	public void setAndroid_biz_master_secret(String android_biz_master_secret)
	{
		this.android_biz_master_secret = android_biz_master_secret;
	}

	public String getAndroid_biz_app_key()
	{
		return android_biz_app_key;
	}

	public void setAndroid_biz_app_key(String android_biz_app_key)
	{
		this.android_biz_app_key = android_biz_app_key;
	}

	public String getAndroid_master_secret()
	{
		return android_master_secret;
	}

	public void setAndroid_master_secret(String android_master_secret)
	{
		this.android_master_secret = android_master_secret;
	}

	public String getAndroid_app_key()
	{
		return android_app_key;
	}

	public void setAndroid_app_key(String android_app_key)
	{
		this.android_app_key = android_app_key;
	}

	public String getAndroid_dist_version()
	{
		return android_dist_version;
	}

	public void setAndroid_dist_version(String android_dist_version)
	{
		this.android_dist_version = android_dist_version;
	}

	public String getAndroid_dist_down_url()
	{
		return android_dist_down_url;
	}

	public void setAndroid_dist_down_url(String android_dist_down_url)
	{
		this.android_dist_down_url = android_dist_down_url;
	}

	public String getAndroid_dist_upgrade()
	{
		return android_dist_upgrade;
	}

	public void setAndroid_dist_upgrade(String android_dist_upgrade)
	{
		this.android_dist_upgrade = android_dist_upgrade;
	}

	public String getAndroid_dist_forced_upgrade()
	{
		return android_dist_forced_upgrade;
	}

	public void setAndroid_dist_forced_upgrade(String android_dist_forced_upgrade)
	{
		this.android_dist_forced_upgrade = android_dist_forced_upgrade;
	}


}
