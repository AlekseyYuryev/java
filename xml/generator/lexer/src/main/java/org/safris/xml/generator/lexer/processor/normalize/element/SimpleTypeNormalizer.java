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

import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public final class SimpleTypeNormalizer extends Normalizer<SimpleTypeModel<?>> {
  private final Map<UniqueQName,SimpleTypeModel<?>> all = new HashMap<UniqueQName,SimpleTypeModel<?>>();

  public SimpleTypeNormalizer(final NormalizerDirectory directory) {
    super(directory);
  }

  public SimpleTypeModel<?> parseSimpleType(final UniqueQName name) {
    return all.get(name);
  }

  protected void stage1(final SimpleTypeModel<?> model) {
    if (model.getName() == null || !(model.getParent() instanceof SchemaModel))
      return;

    if (parseSimpleType(model.getName()) == null)
      all.put(model.getName(), model);
  }

  protected void stage2(final SimpleTypeModel<?> model) {
    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof AttributeModel && parent instanceof Nameable && ((Nameable<?>)parent).getName() != null) {
        ((AttributeModel)parent).setRestriction(true);
        break;
      }
    }

    parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof ElementModel && parent instanceof Nameable && ((Nameable<?>)parent).getName() != null) {
        ((ElementModel)parent).setExtension(true);
        break;
      }
    }
  }

  protected void stage3(final SimpleTypeModel<?> model) {
  }

  protected void stage4(final SimpleTypeModel<?> model) {
  }

  protected void stage5(final SimpleTypeModel<?> model) {
  }

  protected void stage6(final SimpleTypeModel<?> model) {
  }
}