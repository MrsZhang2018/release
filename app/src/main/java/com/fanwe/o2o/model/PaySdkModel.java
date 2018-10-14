package com.fanwe.o2o.model;

import com.alibaba.fastjson.JSON;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.o2o.utils.JsonUtil;
import java.util.Map;

@SuppressWarnings("serial")
public class PaySdkModel extends BaseActModel
{
	private String pay_sdk_type;

	private Map<String, Object> config;

	public String getPay_sdk_type()
	{
		return pay_sdk_type;
	}

	public BfupwapModel getBfupwapModel()
	{
		String s_config = JSON.toJSONString(config);
		return JSON.parseObject(s_config, BfupwapModel.class);
	}

//  public MalipayModel getMalipay()
//  {
//    return JsonUtil.map2Object(config, MalipayModel.class);
//  }

	public void setPay_sdk_type(String pay_sdk_type)
	{
		this.pay_sdk_type = pay_sdk_type;
	}

	public WxappModel getWxapp()
	{
		return SDJsonUtil.map2Object(config, WxappModel.class);
	}

	public Map<String, Object> getConfig()
	{
		return config;
	}

	public void setConfig(Map<String, Object> config)
	{
		this.config = config;
	}

}
