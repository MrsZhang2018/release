package com.fanwe.library.task;

public class SDTaskResponse
{

	private Object tag;
	private Object data;

	public SDTaskResponse()
	{
	}

	public SDTaskResponse(Object tag, Object data)
	{
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
