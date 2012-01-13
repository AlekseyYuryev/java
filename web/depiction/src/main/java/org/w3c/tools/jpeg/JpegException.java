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

// JpegException.java
// $Id: JpegException.java,v 1.2 2005/04/27 10:19:00 nwalsh Exp $
// (c) COPYRIGHT MIT, INRIA and Keio, 1999.
// Please first read the full copyright statement in file COPYRIGHT.html

package org.w3c.tools.jpeg;

/**
 * @version $Revision: 1.2 $
 * @author  Beno&icirc;t Mah&eacute; (bmahe@w3.org)
 */
public class JpegException extends Exception {

    public JpegException(String msg) {
  super(msg);
    }

}
