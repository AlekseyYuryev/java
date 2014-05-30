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
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public final class EnumerationNormalizer extends Normalizer<EnumerationModel> {
  public EnumerationNormalizer(final NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(final EnumerationModel model) {
  }

  protected void stage2(final EnumerationModel model) {
  }

  protected void stage3(final EnumerationModel model) {
  }

  protected void stage4(final EnumerationModel model) {
    if (model.getValue() == null || model.getValue().getLocalPart().length() == 0)
      return;

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof EnumerableModel && parent instanceof Nameable) {
        ((EnumerableModel)parent).addEnumeration(model);
        if (((Nameable<?>)parent).getName() != null)
          break;
      }
    }
  }

  protected void stage5(final EnumerationModel model) {
  }

  protected void stage6(final EnumerationModel model) {
  }
}