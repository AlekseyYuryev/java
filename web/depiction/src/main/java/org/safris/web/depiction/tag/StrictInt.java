/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.web.depiction.tag;

import javax.servlet.jsp.tagext.TagSupport;

public class StrictInt extends TagSupport
{
	private Integer value = null;
	private int min = Integer.MIN_VALUE;
	private int max = Integer.MAX_VALUE;
	private String var = null;
	
	public void setValue(Object value)
	{
		if(value == null)
		{
			this.value = null;
			return;
		}
		
		try
		{
			this.value = Integer.valueOf(value.toString());
		}
		catch(NumberFormatException e)
		{
			this.value = null;
		}
	}
	
	public void setMin(Object min)
	{
		if(min == null)
		{
			this.min = Integer.MIN_VALUE;
			return;
		}
		
		try
		{
			this.min = Integer.parseInt(min.toString());
		}
		catch(NumberFormatException e)
		{
			this.min = Integer.MIN_VALUE;
		}
	}
	
	public void setMax(Object max)
	{
		if(max == null)
		{
			this.max = Integer.MAX_VALUE;
			return;
		}
		
		try
		{
			this.max = Integer.parseInt(max.toString());
		}
		catch(NumberFormatException e)
		{
			this.max = Integer.MAX_VALUE;
		}
	}
	
	public void setVar(Object var)
	{
		if(var == null)
		{
			this.var = null;
			return;
		}
		
		this.var = var.toString();
	}
	
	public int doStartTag()
	{
		if(value == null || max < min)
			return EVAL_BODY_INCLUDE;
		
		if(value < min)
			pageContext.setAttribute(var, min);
		else if(max < value)
			pageContext.setAttribute(var, max);
		else
			pageContext.setAttribute(var, value);
		
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag()
	{
		pageContext.removeAttribute(var);
		return EVAL_BODY_INCLUDE;
	}
}
