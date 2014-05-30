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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class UnknownModel extends Model {
  private String text = " ";

  protected UnknownModel(final Node node, final Model parent) {
    super(node, parent);
    final NodeList nodes = node.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++)
      if (Node.TEXT_NODE == nodes.item(i).getNodeType() && nodes.item(i).getNodeValue().length() != 0)
        text += "\n" + nodes.item(i).getNodeValue();

    if (text.length() > 1)
      this.text = text.substring(1);
  }

  public String getText() {
    return text;
  }

  public void merge(final UnknownModel model) {
    this.text += "\n" + model.text;
  }
}