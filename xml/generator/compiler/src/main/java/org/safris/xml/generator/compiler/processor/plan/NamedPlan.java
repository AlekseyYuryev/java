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

package org.safris.xml.generator.compiler.processor.plan;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.xml.Prefix;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.AnyablePlan;
import org.safris.xml.generator.compiler.processor.plan.NamedPlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.NamedModel;

public abstract class NamedPlan<T extends NamedModel> extends Plan<T> implements Nameable<Plan> {
  protected static NamedPlan parseNamedPlan(UniqueQName name) {
    return all.get(name);
  }

  private static final Map<UniqueQName,NamedPlan> all = new HashMap<UniqueQName,NamedPlan>();

  private final UniqueQName name;

  public NamedPlan(T model, Plan parent) {
    super(model, parent);
    if (model.getName() != null) {
      final Prefix prefix = model.getName().getPrefix();
      if (prefix == null)
        throw new CompilerError("[ERROR] No prefix exists for namespace {" + model.getName().getNamespaceURI() + "}. Is the binding for this namespace defined in the bindings configuration?");

      name = UniqueQName.getInstance(model.getName().getNamespaceURI(), model.getName().getLocalPart(), prefix.toString());
      all.put(name, this);
    }
    else
      name = null;

    if (this instanceof AnyablePlan)
      return;

    if (name == null)
      throw new IllegalArgumentException(getClass().getSimpleName() + " with no name? what's going on?");
  }

  public final UniqueQName getName() {
    return name;
  }
}
