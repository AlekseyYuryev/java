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

package org.safris.xml.generator.compiler.processor.plan.element;

import java.util.LinkedHashSet;

import org.safris.xml.generator.compiler.lang.ElementWrapper;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AttributablePlan;
import org.safris.xml.generator.compiler.processor.plan.ElementablePlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.MixablePlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.MixableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.TypeableModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleContentModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public class ComplexTypePlan<T extends ComplexTypeModel<?>> extends SimpleTypePlan<T> implements AttributablePlan, ElementablePlan, EnumerablePlan, ExtensiblePlan, MixablePlan, NativeablePlan {
  private final Boolean mixed;

  private Boolean mixedType = null;
  private boolean simpleContent = false;

  private LinkedHashSet<AttributePlan> attributes;
  private LinkedHashSet<ElementPlan> elements;

  public ElementPlan elementRefExistsInParent(final UniqueQName name) {
    // FIXME: This is slow!
    if (getElements() != null)
      for (final ElementPlan element : getElements())
        if (element.getName() != null && element.getName().equals(name))
          return element;

    if (getSuperType() == null)
      return null;

    return getSuperType().elementRefExistsInParent(name);
  }

  public ComplexTypePlan(final T model, final Plan<?> parent) {
    super(model.getRedefine() != null ? (T)model.getRedefine() : model, parent);
    mixed = getModel().getMixed();
    for (final Model child : model.getChildren()) {
      if (child instanceof SimpleContentModel) {
        simpleContent = true;
        break;
      }
    }
  }

  public final boolean hasSimpleContent() {
    return simpleContent;
  }

  public final LinkedHashSet<AttributePlan> getAttributes() {
    return attributes == null ? attributes = Plan.<AttributePlan>analyze(getModel().getAttributes(), this) : attributes;
  }

  public final LinkedHashSet<ElementPlan> getElements() {
    return elements == null ? elements = Plan.<ElementPlan>analyze(ElementWrapper.asSet(getModel().getMultiplicableModels()), this) : elements;
  }

  public final Boolean getMixed() {
    return mixed;
  }

  public final Boolean getMixedType() {
    if (mixedType != null)
      return mixedType;

    // this flag may be a HACK. I am using it to see if I have a restriction in the chain of
    // dependencies. If I do, then mixed="true" has to be stated explicitly.
    boolean isEverRestriction = false;
    TypeableModel<?> parent = getModel();
    boolean restriction = getModel().isRestriction();
    while ((parent = parent.getSuperType()) != null) {
      if (parent instanceof MixableModel && !restriction && ((MixableModel)parent).getMixed() != null && ((MixableModel)parent).getMixed())
        return mixedType = true;

      restriction = ((SimpleTypeModel<?>)parent).isRestriction();
      isEverRestriction = isEverRestriction || restriction;
    }

    parent = getModel();
    while ((parent = parent.getSuperType()) != null)
      if (parent instanceof MixableModel && ((MixableModel)parent).getMixed() != null)
        return mixedType = ((MixableModel)parent).getMixed();

    if (!isEverRestriction && XSTypeDirectory.ANYTYPE.getNativeBinding().getName().equals(getBaseXSItemTypeName()))
      return mixedType = true;

    return mixedType = false;
  }
}