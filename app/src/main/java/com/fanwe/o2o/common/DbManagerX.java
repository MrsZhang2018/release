package com.fanwe.o2o.common;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.db.table.TableEntity;

/**
 * xutils数据库管理类
 * 
 * @author Administrator
 * @date 2016-4-6 上午9:13:30
 */
public class DbManagerX
{

	public static DbManager getDb()
	{
		return x.getDb(configDefault);
	}

	/** 默认数据库配置 */
	private static final DbManager.DaoConfig configDefault = new DbManager.DaoConfig().setDbName("fanwe_o2o.db").setDbVersion(1)
			.setAllowTransaction(true).setDbOpenListener(new DbManager.DbOpenListener()
			{
				@Override
				public void onDbOpened(DbManager db)
				{

				}
			}).setTableCreateListener(new DbManager.TableCreateListener()
			{
				@Override
				public void onTableCreated(DbManager db, TableEntity<?> table)
				{

				}
			}).setDbUpgradeListener(new DbManager.DbUpgradeListener()
			{
				@Override
				public void onUpgrade(DbManager db, int oldVersion, int newVersion)
				{

				}
			});
}
