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

package org.safris.web.depiction;

public abstract class HTMLDecode
{
  public static String decode(String string)
  {
    string = string.replace("&quot;", "\"");
    string = string.replace("&39;", "'");
    string = string.replace("&39;", "'");
    string = string.replace("&lt;", "<");
    string = string.replace("&gt;", ">");
    return string;
  }
}
