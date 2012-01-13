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

package org.w3c.tools.jpeg;

import java.util.Map;

/**
 * The EXIF decoder interface.
 *
 * <p>Special purpose field decoders for use by the Exif class must implement
 * this interface.</p>
 *
 * <p>The <code>getNamespace()</code> and <code>setPrefix()</code> methods
 * are probably only relevant if you're planning to use RDF.</p>
 *
 * @version $Revision: 1.1 $
 * @author  Norman Walsh
 * @see Exif
 */
public interface TagDecoder
{
  /** Returns the namespace that should be used for fields added by this class.
   *
   * Implementations <em>must</em> define a namespace URI.
   */
  public String getNamespace();

  /** Sets the prefix that should be used for fields added by this class.
   *
   * If no prefix is specified, unqualified names must be used.
   */
  public void setPrefix(String prefix);

  /** Decodes a field from the EXIF data and possibly augments the exif hash.
   *
   * @param exif The hash of field name/value pairs. This method should update
   *             this has with information extracted from data.
   * @param data The EXIF data.
   * @param format The EXIF format value for the field to be decoded.
   * @param offset The offset of the start of the field in the EXIF data.
   * @param length The length of the field.
   */
  public void decode(Map<String,String> exif, ExifData data, int format, int offset, int length);
}
