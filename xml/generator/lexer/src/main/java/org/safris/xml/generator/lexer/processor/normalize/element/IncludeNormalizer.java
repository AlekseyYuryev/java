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

import java.util.Collection;
import java.util.HashSet;
import org.safris.xml.generator.lexer.processor.model.element.IncludeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class IncludeNormalizer extends Normalizer<IncludeModel> {
  private final Collection<String> messages = new HashSet<String>();

  public IncludeNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(IncludeModel model) {
  }

  protected void stage2(IncludeModel model) {
    final String message = "Including " + model.getSchemaLocation() + " for {" + model.getTargetNamespace() + "}";
    if (messages.contains(message))
      return;

    messages.add(message);
    logger.info(message);
  }

  protected void stage3(IncludeModel model) {
  }

  protected void stage4(IncludeModel model) {
  }

  protected void stage5(IncludeModel model) {
  }

  protected void stage6(IncludeModel model) {
  }
}
