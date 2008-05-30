/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
