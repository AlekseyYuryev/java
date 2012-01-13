/*  Copyright Safris Software 2008
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

package org.safris.xml.generator.lexer.processor.model.element;

import javax.xml.namespace.QName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class EnumerationModel extends Model {
  private QName value = null;

  protected EnumerationModel(Node node, Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("value".equals(attribute.getLocalName()))
        value = parseQNameValue(attribute.getNodeValue(), node);
    }
  }

  public EnumerationModel(QName value) {
    super(null, null);
    this.value = value;
  }

  public final QName getValue() {
    return value;
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof EnumerationModel))
      return false;

    final EnumerationModel that = (EnumerationModel)obj;
    return (getValue() == null && that.getValue() == null) || (getValue() != null && getValue().equals(that.getValue()));
  }

  public int hashCode() {
    return getValue().hashCode();
  }
}
