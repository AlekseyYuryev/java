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
import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class NotationNormalizer extends Normalizer<NotationModel> {
  private final Map<UniqueQName,NotationModel> all = new HashMap<UniqueQName,NotationModel>();

  public NotationNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  public NotationModel parseNotation(UniqueQName name) {
    return all.get(name);
  }

  protected void stage1(NotationModel model) {
    if (model.getName() == null)
      return;

    final NotationModel notationModel = parseNotation(model.getName());
    if (notationModel == null)
      all.put(model.getName(), model);
  }

  protected void stage2(NotationModel model) {
  }

  protected void stage3(NotationModel model) {
  }

  protected void stage4(NotationModel model) {
  }

  protected void stage5(NotationModel model) {
  }

  protected void stage6(NotationModel model) {
  }
}
