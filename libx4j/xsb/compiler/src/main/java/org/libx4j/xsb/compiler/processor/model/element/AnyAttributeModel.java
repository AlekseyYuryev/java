/* Copyright (c) 2008 lib4j
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

package org.libx4j.xsb.compiler.processor.model.element;

import org.libx4j.xsb.compiler.processor.model.AnyableModel;
import org.libx4j.xsb.compiler.processor.model.Model;
import org.libx4j.xsb.compiler.schema.attribute.Namespace;
import org.libx4j.xsb.compiler.schema.attribute.ProcessContents;
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

  @Override
  public final Namespace getNamespace() {
    return namespace;
  }

  @Override
  public final ProcessContents getProcessContents() {
    return processContents;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof AnyAttributeModel))
      return false;

    final AnyAttributeModel anyAttribute = (AnyAttributeModel)obj;
    return namespace.equals(anyAttribute.namespace) && processContents.equals(anyAttribute.processContents);
  }
}