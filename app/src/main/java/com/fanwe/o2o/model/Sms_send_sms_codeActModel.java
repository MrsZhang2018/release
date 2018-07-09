package com.fanwe.o2o.model;

public class Sms_send_sms_codeActModel extends BaseActModel
{

	private int lesstime;
	private String verify_image;
	private int width;
	private int height;

	public String msg_id;
	public String ctl;
	public String act;
	public int status;
	public String info;
	public String city_name;
	//	public int return;
	public String sess_id;
	public String ref_uid;

	public String getVerify_image()
	{
		return verify_image;
	}

	public void setVerify_image(String verify_image)
	{
		this.verify_image = verify_image;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getLesstime()
	{
		return lesstime;
	}

	public void setLesstime(int lesstime)
	{
		this.lesstime = lesstime;
	}

}
