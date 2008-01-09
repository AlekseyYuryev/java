package org.safris.web.depiction.tag.bean;

import java.util.List;
import org.safris.web.depiction.tag.bean.Bean;

public class ListBean<E> implements Bean
{
	private final List<? extends Bean> list;
	
	public ListBean(List<? extends Bean> list)
	{
		this.list = list;
	}
	
	public List<? extends Bean> getObject()
	{
		return list;
	}
	
	public List<? extends Bean> getList()
	{
		return list;
	}
	
	public int getSize()
	{
		if(list == null)
			return 0;
		
		return list.size();
	}
}
