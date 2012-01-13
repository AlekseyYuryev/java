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

package org.safris.xml.generator.lexer.processor.normalize.element;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AttributeGroupNormalizer extends Normalizer<AttributeGroupModel> {
  private final Map<UniqueQName,AttributeGroupModel> all = new HashMap<UniqueQName,AttributeGroupModel>();

  public AttributeGroupNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  public AttributeGroupModel parseAttributeGroup(UniqueQName name) {
    return all.get(name);
  }

  protected void stage1(AttributeGroupModel model) {
    if (model.getName() == null || model.getParent() instanceof RedefineModel)
      return;

    if (parseAttributeGroup(model.getName()) == null)
      all.put(model.getName(), model);
  }

  protected void stage2(AttributeGroupModel model) {
    if (!(model.getRef() instanceof AttributeGroupModel.Reference))
      return;

    final AttributeGroupModel ref = parseAttributeGroup(model.getRef().getName());
    if (ref == null)
      throw new LexerError("ref == null for " + model.getRef().getName());

    model.setRef(ref);

    // If the parent of this attributeGroup is also an attributeGroup, then propogate the attributes
    if (model.getParent() instanceof AttributeGroupModel)
      ((AttributeGroupModel)model.getParent()).getAttributes().addAll(model.getRef().getAttributes());
  }

  protected void stage3(AttributeGroupModel model) {
    if (model.getRef() == null)
      return;

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent.getParent() instanceof RedefineModel && parent instanceof AttributeGroupModel && model.getRef().getName().equals(((AttributeGroupModel)parent).getName())) {
        model.getRef().setRedefine((AttributeGroupModel)parent);
        break;
      }
    }
  }

  protected void stage4(AttributeGroupModel model) {
    if (model.getRef() == null)
      return;

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null) {
        if (model.getRef().getRedefine() != null && model.getRef().getRedefine() != parent)
          ((AttributableModel)parent).getAttributes().addAll(model.getRef().getRedefine().getAttributes());
        else
          ((AttributableModel)parent).getAttributes().addAll(model.getRef().getAttributes());

        break;
      }
    }
  }

  protected void stage5(AttributeGroupModel model) {
  }

  protected void stage6(AttributeGroupModel model) {
  }
}
