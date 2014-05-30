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

import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.element.DocumentationPlan;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;

public abstract class AliasPlan<T extends AliasModel> extends NamedPlan<T> implements DocumentablePlan, Nameable<Plan<T>> {
  protected static String getClassName(final AliasModel model, Model parent) {
    if (model == null || model.getName() == null)
      return null;

    if (model.getParent() instanceof SchemaModel || XSTypeDirectory.parseType(model.getName()) != null)
      return model.getName().getNamespaceURI().getPackageName().toString() + "." + JavaBinding.getClassSimpleName(model);

    Model check = model;
    while ((check = check.getParent()) != null)
      if (check instanceof AliasModel && ((AliasModel)check).getName() != null)
        return getClassName((AliasModel)check, null) + "." + JavaBinding.getClassSimpleName(model);

    if (parent != null)
      do
        if (parent instanceof AliasModel && ((AliasModel)parent).getName() != null)
          return getClassName((AliasModel)parent, null) + "." + JavaBinding.getClassSimpleName(model);
      while((parent = parent.getParent()) != null);

    return model.getName().getNamespaceURI().getPackageName().toString() + "." + JavaBinding.getClassSimpleName(model);
  }

  private DocumentationPlan documentation = null;

  private String packageName = null;
  private String instanceName = null;

  private String className = null;
  private String classInconvertibleName = null;
  private String classSimpleName = null;
  private String schemaReference = null;
  private String xsdLocation = null;

  public AliasPlan(final T model, final Plan<?> parent) {
    super(model, parent);
    if (getModel() != null)
      documentation = Plan.<DocumentationPlan>analyze(getModel().getDocumentation(), this);

    schemaReference = model.getSchema().getURL().toString();
    xsdLocation = model.getSchema().getTargetNamespace().getPackageName().toString() + ".xsd";
  }

  public final String getXsdLocation() {
    return xsdLocation;
  }

  public final String getDocumentation() {
    return documentation != null ? documentation.getDocumentation() : "";
  }

  public final String getInstanceName() {
    return instanceName == null ? instanceName = JavaBinding.getInstanceName(getModel()) : instanceName;
  }

  public final String getClassName(final Plan<?> parent) {
    if (className != null)
      return className;

    if (parent != null)
      return className = AliasPlan.getClassName(getModel(), parent.getModel());

    return className = AliasPlan.getClassName(getModel(), null);
  }

  public final String getClassWithInconvertible(final Plan<?> parent) {
    if (classInconvertibleName != null)
      return classInconvertibleName;

    if (parent != null)
      return classInconvertibleName = AliasPlan.getClassName(getModel(), parent.getModel());

    return classInconvertibleName = AliasPlan.getClassName(getModel(), null);
  }

  public final String getClassSimpleName() {
    return classSimpleName == null ? classSimpleName = JavaBinding.getClassSimpleName(getModel()) : classSimpleName;
  }

  public final String getPackageName() {
    return packageName == null ? packageName = getName().getNamespaceURI().getPackageName().toString() : packageName;
  }
}