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

import java.io.File;
import org.safris.commons.io.Files;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class SchemaNormalizer extends Normalizer<SchemaModel> {
  private static File CWD = null;

  public SchemaNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(SchemaModel model) {
    if (CWD == null)
      CWD = Files.getCwd();

    if (model.getURL() == null)
      return;

    final String display = Files.relativePath(CWD.getAbsoluteFile(), new File(model.getURL().getFile()).getAbsoluteFile());
    logger.info("Lexing {" + model.getTargetNamespace() + "} from " + display);
  }

  protected void stage2(SchemaModel model) {
  }

  protected void stage3(SchemaModel model) {
  }

  protected void stage4(SchemaModel model) {
  }

  protected void stage5(SchemaModel model) {
  }

  protected void stage6(SchemaModel model) {
  }
}
