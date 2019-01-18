package com.fanwe.o2o.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.fanwe.o2o.dao.LocalUserModelDao;
import com.fanwe.o2o.event.ELoginSuccess;
import com.sunday.eventbus.SDEventManager;

public class LocalUserModel implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int _id;

	private int user_id;
	private String user_email;
	private String user_name;
	private String user_pwd;
	private String user_money;
	private String user_money_format;
	private String user_score;
	private String user_mobile;
	private int is_tmp;
	private boolean is_set_pass;
	private String msg_id;
	private String token;

	public static void dealLoginSuccess(User_infoModel model, boolean postEvent)
	{
		if (model != null)
		{
			LocalUserModel localModel = new LocalUserModel();

			localModel.setUser_id(model.getId());
			localModel.setUser_name(model.getUser_name());
			localModel.setUser_pwd(model.getUser_pwd());
			localModel.setUser_email(model.getEmail());
			localModel.setUser_mobile(model.getMobile());
			localModel.setIs_tmp(model.getIs_tmp());
			localModel.setMsg_id(model.getMsg_id());
            localModel.setIs_set_pass(model.isIs_set_pass());
			localModel.setToken(model.getToken());
			dealLoginSuccess(localModel, postEvent);
		}
	}

	public static void dealLoginSuccess(LocalUserModel model, boolean postEvent)
	{
		LocalUserModelDao.insertModel(model);
		if (postEvent)
		{
			SDEventManager.post(new ELoginSuccess());
		}
	}

	public int getIs_tmp()
	{
		return is_tmp;
	}

	public void setIs_tmp(int is_tmp)
	{
		this.is_tmp = is_tmp;
	}

	public String getUser_mobile()
	{
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile)
	{
		this.user_mobile = user_mobile;
	}

	public String getUser_money()
	{
		return user_money;
	}

	public void setUser_money(String user_money)
	{
		this.user_money = user_money;
	}

	public String getUser_money_format()
	{
		return user_money_format;
	}

	public void setUser_money_format(String user_money_format)
	{
		this.user_money_format = user_money_format;
	}

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public String getUser_email()
	{
		return user_email;
	}

	public void setUser_email(String user_email)
	{
		this.user_email = user_email;
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

	public String getUser_score()
	{
		return user_score;
	}

	public void setUser_score(String user_score)
	{
		this.user_score = user_score;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalUserModel deepClone()
	{
		try
		{
			// 将对象写到流里
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (LocalUserModel) (oi.readObject());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
