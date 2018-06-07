package com.fanwe.library.task;

public class SDTaskRequest
{

	private Object tag;
	private Object data;

	public SDTaskRequest()
	{
	}

	public SDTaskRequest(Object tag, Object data)
	{
		super();
		this.tag = tag;
		this.data = data;
	}

	public Object getTag()
	{
		return tag;
	}

	public void setTag(Object tag)
	{
		this.tag = tag;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

}
