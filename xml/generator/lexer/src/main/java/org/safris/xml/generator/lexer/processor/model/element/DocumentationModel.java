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

import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentationModel extends Model {
  private String source = null;
  private String lang = null;
  private String text = " ";

  protected DocumentationModel(Node node, Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if (attribute.getNodeValue() == null)
        continue;

      if ("source".equals(attribute.getLocalName()))
        source = attribute.getNodeValue();
      else if ("lang".equals(attribute.getLocalName()))
        lang = attribute.getNodeValue();
    }

    final NodeList nodes = node.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++)
      if (Node.TEXT_NODE == nodes.item(i).getNodeType() && nodes.item(i).getNodeValue().length() != 0)
        text += "\n" + nodes.item(i).getNodeValue();

    if (text.length() > 1)
      this.text = text.substring(1);
  }

  public final String getText() {
    return text;
  }

  public final String getSource() {
    return source;
  }

  public final String getLang() {
    return lang;
  }

  public final void merge(DocumentationModel model) {
    this.text += "\n" + model.text;
  }
}