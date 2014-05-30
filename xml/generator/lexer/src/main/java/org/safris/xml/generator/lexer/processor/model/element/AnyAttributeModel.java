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

import org.safris.xml.generator.lexer.processor.model.AnyableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.schema.attribute.Namespace;
import org.safris.xml.generator.lexer.schema.attribute.ProcessContents;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class AnyAttributeModel extends AttributeModel implements AnyableModel {
  private Namespace namespace = Namespace.ANY;
  private ProcessContents processContents = ProcessContents.STRICT;

  protected AnyAttributeModel(final Node node, final Model parent) {
    super(node, parent);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("namespace".equals(attribute.getLocalName()))
        namespace = Namespace.parseNamespace(attribute.getNodeValue());
      else if ("processContents".equals(attribute.getLocalName()))
        processContents = ProcessContents.parseProcessContents(attribute.getNodeValue());
    }
  }

  public final Namespace getNamespace() {
    return namespace;
  }

  public final ProcessContents getProcessContents() {
    return processContents;
  }

  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof AnyAttributeModel))
      return false;

    final AnyAttributeModel anyAttribute = (AnyAttributeModel)obj;
    return namespace.equals(anyAttribute.namespace) && processContents.equals(anyAttribute.processContents);
  }
}