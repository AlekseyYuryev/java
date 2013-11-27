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

import java.util.ArrayList;
import java.util.Collection;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.ListModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.UnionModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class UnionNormalizer extends Normalizer<UnionModel> {
  private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

  public UnionNormalizer(NormalizerDirectory directory) {
    super(directory);
  }

  protected void stage1(UnionModel model) {
  }

  protected void stage2(UnionModel model) {
    final Collection<SimpleTypeModel> memberTypes = model.getMemberTypes();
    final Collection<SimpleTypeModel> resolvedMemberTypes = new ArrayList<SimpleTypeModel>(memberTypes.size());
    SimpleTypeModel resolvedMemberType;
    if (memberTypes != null) {
      for (final SimpleTypeModel memberType : memberTypes) {
        if (memberType instanceof SimpleTypeModel.Reference) {
          resolvedMemberType = simpleTypeNormalizer.parseSimpleType(memberType.getName());
          if (resolvedMemberType == null) {
            if (!UniqueQName.XS.getNamespaceURI().equals(memberType.getName().getNamespaceURI()))
              throw new LexerError("type == null for " + memberType.getName());

            resolvedMemberType = SimpleTypeModel.Undefined.parseSimpleType(memberType.getName());
          }
        }
        else
          resolvedMemberType = memberType;

        resolvedMemberTypes.add(resolvedMemberType);
      }

      model.getMemberTypes().clear();
      model.getMemberTypes().addAll(resolvedMemberTypes);
    }
  }

  protected void stage3(UnionModel model) {
  }

  protected void stage4(UnionModel model) {
  }

  protected void stage5(UnionModel model) {
    if (model.getMemberTypes() == null || model.getMemberTypes().size() == 0)
      throw new LexerError("I dont think this can happen.");

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      // Either there is a higher level union that we want to combine into this union
      if (parent instanceof UnionModel) {
        ((UnionModel)parent).addUnion(model);
        break;
      }
      // Or there is a list that of a union
      else if (parent instanceof ListModel) {
        if (((ListModel)parent).getItemType() != null)
          throw new LexerError("Dont know how this happened, but the <list> has a memberType already and it has a union under it also.");

        ((ListModel)parent).setItemType(model);
        break;
      }
    }
  }

  protected void stage6(UnionModel model) {
    if (model.getMemberTypes() == null || model.getMemberTypes().size() == 0)
      throw new LexerError("I dont think this can happen.");

    Model parent = model;
    while ((parent = parent.getParent()) != null) {
      // If this union defines a named simpleType
      if (parent instanceof SimpleTypeModel && ((SimpleTypeModel)parent).getName() != null) {
        final SimpleTypeModel simpleTypeModel = (SimpleTypeModel)parent;
        simpleTypeModel.setSuperType(SimpleTypeModel.Undefined.parseSimpleType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType")));
        simpleTypeModel.setItemTypes(model.getNormalizedMemberTypes());
        break;
      }
    }
  }
}
