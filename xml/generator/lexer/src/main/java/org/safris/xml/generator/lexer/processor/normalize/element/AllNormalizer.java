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
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AllModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AllNormalizer extends Normalizer<AllModel> {
  public AllNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(AllModel model) {
  }

  protected void stage2(AllModel model) {
  }

  protected void stage3(AllModel model) {
  }

  protected void stage4(AllModel model) {
    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof ElementableModel && (!(parent instanceof Nameable) || ((Nameable)parent).getName() != null)) {
        ((ElementableModel)parent).addMultiplicableModel(model);
        break;
      }
    }
  }

  protected void stage5(AllModel model) {
  }

  protected void stage6(AllModel model) {
  }
}
