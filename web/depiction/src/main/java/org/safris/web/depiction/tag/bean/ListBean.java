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
