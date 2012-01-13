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
import org.safris.xml.generator.lexer.processor.model.element.ImportModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class ImportNormalizer extends Normalizer<ImportModel> {
  private final Collection<String> messages = new HashSet<String>();

  public ImportNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(ImportModel model) {
  }

  protected void stage2(ImportModel model) {
    final String message = "Importing " + model.getSchemaLocation() + " for {" + model.getTargetNamespace() + "}";
    if (messages.contains(message))
      return;

    messages.add(message);
    logger.info(message);
  }

  protected void stage3(ImportModel model) {
  }

  protected void stage4(ImportModel model) {
  }

  protected void stage5(ImportModel model) {
  }

  protected void stage6(ImportModel model) {
  }
}
