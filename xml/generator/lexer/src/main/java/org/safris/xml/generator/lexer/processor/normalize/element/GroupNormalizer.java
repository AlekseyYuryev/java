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
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.GroupModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class GroupNormalizer extends Normalizer<GroupModel> {
  private final Map<UniqueQName,GroupModel> all = new HashMap<UniqueQName,GroupModel>();

  public GroupNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  public GroupModel parseGroup(UniqueQName name) {
    return all.get(name);
  }

  protected void stage1(GroupModel model) {
    if (model.getName() == null)
      return;

    if (!all.containsKey(model.getName()))
      all.put(model.getName(), model);
  }

  protected void stage2(GroupModel model) {
    if (model.getRef() == null || !(model.getRef() instanceof GroupModel.Reference))
      return;

    GroupModel ref = parseGroup(model.getRef().getName());
    if (ref == null)
      ref = parseGroup(model.getName());

    if (ref == null)
      throw new LexerError("ref == null for " + model.getName());

    model.setRef(ref);
  }

  protected void stage3(GroupModel model) {
    if (model.getRef() == null)
      return;

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent.getParent() instanceof RedefineModel && parent instanceof GroupModel && model.getRef().getName().equals(((GroupModel)parent).getName())) {
        model.getRef().setRedefine((GroupModel)parent);
        break;
      }
    }
  }

  protected void stage4(GroupModel model) {
    if (model.getRef() == null)
      return;

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      if (parent instanceof ElementableModel) {
        ((ElementableModel)parent).addMultiplicableModel(model.getRef());
        break;
      }
    }
  }

  protected void stage5(GroupModel model) {
  }

  protected void stage6(GroupModel model) {
  }
}
