/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
