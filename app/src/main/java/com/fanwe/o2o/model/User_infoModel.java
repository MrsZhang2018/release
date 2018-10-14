package com.fanwe.o2o.model;

public class User_infoModel extends BaseActModel
{
	private int id;
	private String user_name;
	private String user_pwd;
	private String email;
	private int is_tmp;
	private String user_avatar;
	private String mobile;
	private String mobile_format;
	private String money;
	private String money_format;
	private String consignee;
	private int is_luck;
	private boolean is_set_pass;
	private String msg_id;
	private String truck_number;
	private String wallet_gas;
	private String wallet_any;

	public String getNew_user() {
		return new_user;
	}

	public void setNew_user(String new_user) {
		this.new_user = new_user;
	}

	private String new_user;
	public int getIs_luck() {
		return is_luck;
	}

	public void setIs_luck(int is_luck) {
		this.is_luck = is_luck;
	}

	public String getMobile_format()
	{
		return mobile_format;
	}

	public void setMobile_format(String mobile_format)
	{
		this.mobile_format = mobile_format;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getUser_pwd()
	{
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd)
	{
		this.user_pwd = user_pwd;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public int getIs_tmp()
	{
		return is_tmp;
	}

	public void setIs_tmp(int is_tmp)
	{
		this.is_tmp = is_tmp;
	}

	public String getUser_avatar()
	{
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar)
	{
		this.user_avatar = user_avatar;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getConsignee()
	{
		return consignee;
	}

	public void setConsignee(String consignee)
	{
		this.consignee = consignee;
	}

	public String getMoney()
	{
		return money;
	}

	public void setMoney(String money)
	{
		this.money = money;
	}

	public String getMoney_format()
	{
		return money_format;
	}

	public void setMoney_format(String money_format)
	{
		this.money_format = money_format;
	}

	public boolean isIs_set_pass() {
		return is_set_pass;
	}

	public void setIs_set_pass(boolean is_set_pass) {
		this.is_set_pass = is_set_pass;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getTruck_number() {
		return truck_number;
	}

	public void setTruck_number(String truck_number) {
		this.truck_number = truck_number;
	}

	public String getWallet_gas() {
		return wallet_gas;
	}

	public void setWallet_gas(String wallet_gas) {
		this.wallet_gas = wallet_gas;
	}

	public String getWallet_any() {
		return wallet_any;
	}

	public void setWallet_any(String wallet_any) {
		this.wallet_any = wallet_any;
	}
}
