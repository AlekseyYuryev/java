/* Copyright (c) 2008 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.cf.xsb.compiler.processor.model.element;

import org.safris.cf.xsb.compiler.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DocumentationModel extends Model {
  private String source = null;
  private String lang = null;
  private String text = " ";

  protected DocumentationModel(final Node node, final Model parent) {
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

  public final void merge(final DocumentationModel model) {
    this.text += "\n" + model.text;
  }
}