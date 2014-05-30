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

import java.util.LinkedHashSet;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.MultiplicableModel;
import org.safris.xml.generator.lexer.schema.attribute.Occurs;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class AllModel extends Model implements MultiplicableModel {
  private final LinkedHashSet<MultiplicableModel> multiplicableModels = new LinkedHashSet<MultiplicableModel>();

  private Occurs maxOccurs = Occurs.parseOccurs("1");
  private Occurs minOccurs = Occurs.parseOccurs("1");

  protected AllModel(final Node node, final Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("maxOccurs".equals(attribute.getLocalName()))
        maxOccurs = Occurs.parseOccurs(attribute.getNodeValue());
      else if ("minOccurs".equals(attribute.getLocalName()))
        minOccurs = Occurs.parseOccurs(attribute.getNodeValue());
    }
  }

  public void addMultiplicableModel(final MultiplicableModel multiplicableModel) {
    if (!this.equals(multiplicableModel))
      this.multiplicableModels.add(multiplicableModel);
  }

  public LinkedHashSet<MultiplicableModel> getMultiplicableModels() {
    return multiplicableModels;
  }

  public Occurs getMaxOccurs() {
    return maxOccurs;
  }

  public Occurs getMinOccurs() {
    return minOccurs;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    return super.toString().replace(TO_STRING_DELIMITER, "maxOccurs=\"" + maxOccurs + "\" minOccurs=\"" + minOccurs + "\"");
  }
}