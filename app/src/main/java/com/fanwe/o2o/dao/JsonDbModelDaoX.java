package com.fanwe.o2o.dao;

import java.util.List;

import org.xutils.db.sqlite.WhereBuilder;

import com.fanwe.library.utils.AESUtil;
import com.fanwe.o2o.model.JsonDbModel;
import com.fanwe.o2o.common.DbManagerX;
import com.fanwe.o2o.utils.JsonUtil;

public class JsonDbModelDaoX
{

	private static JsonDbModelDaoX mInstance = null;

	private JsonDbModelDaoX()
	{
	}

	public static JsonDbModelDaoX getInstance()
	{
		if (mInstance == null)
		{
			synchronized (JsonDbModelDaoX.class)
			{
				if (mInstance == null)
				{
					mInstance = new JsonDbModelDaoX();
				}
			}
		}
		return mInstance;
	}

	private <T> boolean insert(T model, boolean encrypt)
	{
		if (model != null)
		{
			try
			{
				JsonDbModel jsonDbModel = new JsonDbModel();
				jsonDbModel.setKey(model.getClass().getName());
				String json = JsonUtil.object2Json(model);
				if (encrypt) // 需要加密
				{
					json = AESUtil.encrypt(json);
				}
				jsonDbModel.setValue(json);
				DbManagerX.getDb().save(jsonDbModel);
				return true;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T> boolean insertOrUpdate(T model, boolean encrypt)
	{
		if (model != null)
		{
			delete(model.getClass());
			return insert(model, encrypt);
		} else
		{
			return false;
		}
	}

	public <T> boolean insertOrUpdate(T model)
	{
		return insertOrUpdate(model, false);
	}

	public <T> boolean delete(Class<T> clazz)
	{
		if (clazz != null)
		{
			try
			{
				DbManagerX.getDb().delete(JsonDbModel.class, WhereBuilder.b("key", "=", clazz.getName()));
				return true;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T> T query(Class<T> clazz, boolean decrypt)
	{
		if (clazz != null)
		{
			try
			{
				List<JsonDbModel> listJsonDbModel = DbManagerX.getDb().selector(JsonDbModel.class).where("key", "=", clazz.getName()).findAll();
				if (listJsonDbModel != null && listJsonDbModel.size() == 1)
				{
					JsonDbModel jsonDbModel = listJsonDbModel.get(0);
					String value = jsonDbModel.getValue();
					if (value != null)
					{
						if (decrypt) // 需要解密
						{
							value = AESUtil.decrypt(value);
						}
						return JsonUtil.json2Object(value, clazz);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public <T> T query(Class<T> clazz)
	{
		return query(clazz, false);
	}

}
