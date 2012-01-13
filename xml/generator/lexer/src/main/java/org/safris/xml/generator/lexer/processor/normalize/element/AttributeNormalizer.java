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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.processor.normalize.element.SimpleTypeNormalizer;

public class AttributeNormalizer extends Normalizer<AttributeModel> {
  private final Map<UniqueQName,AttributeModel> all = new HashMap<UniqueQName,AttributeModel>();
  private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

  public AttributeNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  public final AttributeModel parseAttribute(UniqueQName name) {
    return all.get(name);
  }

  protected void stage1(AttributeModel model) {
    if (model.getName() == null || !(model.getParent() instanceof SchemaModel))
      return;

    if (parseAttribute(model.getName()) == null)
      all.put(model.getName(), model);
  }

  protected void stage2(AttributeModel model) {
    // First set the attributeFormDefault value
    Model schema = model.getParent();
    if (schema != null)
      while (!((schema = schema.getParent()) instanceof SchemaModel) && schema != null);

    if (schema != null)
      model.setFormDefault(((SchemaModel)schema).getAttributeFormDefault());

    if (model.getRef() instanceof AttributeModel.Reference) {
      final AttributeModel ref = parseAttribute(model.getRef().getName());
      model.setRef(ref);
    }

    if (model.getSuperType() instanceof SimpleTypeModel.Reference) {
      SimpleTypeModel type = simpleTypeNormalizer.parseSimpleType(model.getSuperType().getName());
      if (type == null)
        type = SimpleTypeModel.Undefined.parseSimpleType(model.getSuperType().getName());

      model.setSuperType(type);
    }

    // Add the attribute to the attributeGroup
    if (model.getParent() instanceof AttributeGroupModel)
      ((AttributeGroupModel)model.getParent()).addAttribute(model);
  }

  protected void stage3(AttributeModel model) {
  }

  protected void stage4(AttributeModel model) {
    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null) {
        ((AttributableModel)parent).addAttribute(model);
        break;
      }
    }

    if (model.getFixed() != null)
      model.addEnumeration(new EnumerationModel(model.getFixed()));
  }

  protected void stage5(AttributeModel model) {
  }

  protected void stage6(AttributeModel model) {
    if (model.getName() == null)
      return;

    if (model.getSuperType() == null) {
      final SimpleTypeModel type = ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType"));
      model.setSuperType(type);
      model.setItemTypes(Arrays.<SimpleTypeModel>asList(type));
    }
  }
}
