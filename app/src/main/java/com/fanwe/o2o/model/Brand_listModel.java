package com.fanwe.o2o.model;

import com.fanwe.library.common.SDSelectManager;

import java.io.Serializable;

public class Brand_listModel implements SDSelectManager.SDSelectable,Serializable
{

	private String id;
	private String name;
	private boolean isSelect;

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	@Override
	public boolean isSelected()
	{
		return isSelect;
	}

	@Override
	public void setSelected(boolean selected)
	{
		this.isSelect = selected;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

//	public static int findIndex(int bid, List<Brand_listModel> listModel)
//	{
//		int index = 0;
//		if (bid > 0 && !SDCollectionUtil.isEmpty(listModel))
//		{
//			Brand_listModel model = null;
//			for (int i = 0; i < listModel.size(); i++)
//			{
//				model = listModel.get(i);
//				if (model.getId() == bid)
//				{
//					index = i;
//					break;
//				}
//			}
//		}
//		return index;
//	}
}
