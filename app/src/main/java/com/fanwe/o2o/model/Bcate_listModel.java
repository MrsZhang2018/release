package com.fanwe.o2o.model;

import java.util.List;

import com.fanwe.library.utils.SDCollectionUtil;

public class Bcate_listModel
{
	private int id;
	private int cate_id;
	private String name;
	private String icon_img;
	private int count;
	private List<Bcate_listModel> bcate_type;

	// /////////////////////////add
	private boolean isSelect;
	private boolean isHasChild;

	public int getCate_id() {
		return cate_id;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getIcon_img()
	{
		return icon_img;
	}

	public void setIcon_img(String icon_img)
	{
		this.icon_img = icon_img;
	}

	public boolean isHasChild()
	{
		return isHasChild;
	}

	public void setHasChild(boolean isHasChild)
	{
		this.isHasChild = isHasChild;
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public List<Bcate_listModel> getBcate_type()
	{
		return bcate_type;
	}

	public void setBcate_type(List<Bcate_listModel> bcate_type)
	{
		this.bcate_type = bcate_type;
		if (bcate_type != null && bcate_type.size() > 1)
		{
			setHasChild(true);
		} else
		{
			setHasChild(false);
		}
	}


	/**
	 * 当大范围id不为-1时,可以以此先遍历外层list找到大分类 再找到小分类
	 * @param smallId
	 * @param bigId
	 * @param listModel
	 * @return
	 */
	public static int[] findIndex(int smallId, int bigId, List<Bcate_listModel> listModel)
	{
		int leftIndex = 0;
		int rightIndex = 0;
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			if (smallId > 0 || bigId > 0)
			{
				Bcate_listModel model = null;
				for (int i = 0; i < listModel.size(); i++)
				{
					model = listModel.get(i);
					if (model.getId() == bigId) // 找到大分类  注意外层的Bcate_listModel的id后台并没有传反
					{
						leftIndex = i;
						List<Bcate_listModel> listChildModel = model.getBcate_type();
						if (!SDCollectionUtil.isEmpty(listChildModel))
						{
							for (int j = 0; j < listChildModel.size(); j++)
							{
								model = listChildModel.get(j);
								if (model.getId() == smallId) // 找到小分类  注意内层的Bcate_listModel的id后台传反了
								{
									rightIndex = j;
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
		return new int[] { leftIndex, rightIndex };
	}

	public static int[] findIndex(int cate_id, List<Bcate_listModel> listModel)
	{
		int leftIndex = 0;
		int rightIndex = 0;
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			if (cate_id > 0)
			{
				Bcate_listModel model = null;
				for (int i = 0; i < listModel.size(); i++)
				{
					model = listModel.get(i);
					if (model.getId() == cate_id)
					{
						leftIndex = i;
						break;
					} else
					{
						List<Bcate_listModel> listChildModel = model.getBcate_type();
						if (!SDCollectionUtil.isEmpty(listChildModel))
						{
							for (int j = 0; j < listChildModel.size(); j++)
							{
								model = listChildModel.get(j);
								if (model.getId() == cate_id)
								{
									leftIndex = i;
									rightIndex = j;
									break;
								}
							}
						}
					}
				}
			}
		}
		return new int[] { leftIndex, rightIndex };
	}

}