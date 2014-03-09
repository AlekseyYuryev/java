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
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class ComplexTypeNormalizer extends Normalizer<ComplexTypeModel> {
  protected final Map<UniqueQName,ComplexTypeModel> all = new HashMap<UniqueQName,ComplexTypeModel>();

  public ComplexTypeNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  public ComplexTypeModel parseComplexType(UniqueQName name) {
    return all.get(name);
  }

  protected void stage1(ComplexTypeModel model) {
    if (model.getName() == null || model.getParent() instanceof RedefineModel)
      return;

    ComplexTypeModel complexTypeModel = parseComplexType(model.getName());
    if (complexTypeModel == null)
      all.put(model.getName(), model);
  }

  protected void stage2(ComplexTypeModel model) {
    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof ElementModel && parent instanceof Nameable && ((Nameable)parent).getName() != null) {
        ((ElementModel)parent).setExtension(true);
        break;
      }
    }
  }

  protected void stage3(ComplexTypeModel model) {
  }

  protected void stage4(ComplexTypeModel model) {
  }

  protected void stage5(ComplexTypeModel model) {
  }

  protected void stage6(ComplexTypeModel model) {
    if (model.getName() == null)
      return;

    if (model.getSuperType() == null)
      model.setSuperType(ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType")));
  }
}