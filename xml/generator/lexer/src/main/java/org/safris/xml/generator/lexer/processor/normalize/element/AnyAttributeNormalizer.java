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

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AnyAttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AnyAttributeNormalizer extends Normalizer<AnyAttributeModel> {
  public AnyAttributeNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(AnyAttributeModel model) {
  }

  protected void stage2(AnyAttributeModel model) {
    // add the handler attribute to the attributeGroup
    if (model.getParent() instanceof AttributeGroupModel)
      ((AttributeGroupModel)model.getParent()).addAttribute(model);
  }

  protected void stage3(AnyAttributeModel model) {
  }

  protected void stage4(AnyAttributeModel model) {
    // Add the handler to the Attributable class with a name
    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null) {
        ((AttributableModel)parent).addAttribute(model);
        break;
      }
    }
  }

  protected void stage5(AnyAttributeModel model) {
  }

  protected void stage6(AnyAttributeModel model) {
  }
}
