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

import org.safris.commons.lang.Paths;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.processor.plan.NamedPlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.DocumentationPlan;
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.safris.xml.generator.compiler.runtime.SimpleType;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public abstract class AliasPlan<T extends AliasModel> extends NamedPlan<T> implements DocumentablePlan, Nameable<Plan> {
  private static String getInconvertibleType(AliasModel model) {
    if (model instanceof ElementModel || model instanceof AttributeModel)
      return "";

    final StringBuffer invariableType = new StringBuffer("<?");
    if (model instanceof ComplexTypeModel)
      invariableType.append(" extends " + ComplexType.class.getName());
    else if (model instanceof SimpleTypeModel)
      invariableType.append(" extends " + SimpleType.class.getName());

    return invariableType.append(">").toString();
  }

  protected static String getClassName(AliasModel model, Model parent) {
    return getClassName(model, parent, false);
  }

  protected static String getClassNameWithInconvertible(AliasModel model, Model parent) {
    return getClassName(model, parent, true);
  }

  private static String getClassName(AliasModel model, Model parent, boolean inconvertible) {
    if (model == null || model.getName() == null)
      return null;

    if (model.getParent() instanceof SchemaModel || UniqueQName.XS.getNamespaceURI().equals(model.getName().getNamespaceURI()))
      return model.getName().getNamespaceURI().getPackageName().toString() + "." + JavaBinding.getClassSimpleName(model) + (inconvertible ? getInconvertibleType((AliasModel)model) : "");


    Model check = model;
    while ((check = check.getParent()) != null)
      if (check instanceof AliasModel && ((AliasModel)check).getName() != null)
        return getClassName((AliasModel)check, null) + "." + JavaBinding.getClassSimpleName(model);

    if (parent != null) {
      do {
        if (parent instanceof AliasModel && ((AliasModel)parent).getName() != null)
          return getClassName((AliasModel)parent, null) + "." + JavaBinding.getClassSimpleName(model);
      }
      while((parent = parent.getParent()) != null);
    }

    return model.getName().getNamespaceURI().getPackageName().toString() + "." + JavaBinding.getClassSimpleName(model) + (inconvertible ? getInconvertibleType((AliasModel)model) : "");
  }

  private DocumentationPlan documentation = null;

  private String packageName = null;
  private String instanceName = null;

  private String className = null;
  private String classInconvertibleName = null;
  private String classSimpleName = null;
  private String schemaReference = null;
  private String xsdLocation = null;

  public AliasPlan(T model, Plan parent) {
    super(model, parent);
    if (getModel() != null)
      documentation = Plan.<DocumentationPlan>analyze(getModel().getDocumentation(), this);

    schemaReference = model.getSchema().getURL().toString();
    xsdLocation = Paths.getName(model.getSchema().getTargetNamespace().getPackageName().toString().replace('.', '/') + ".xsd");
  }

  public final String getXsdLocation() {
    return xsdLocation;
  }

  public final String getDocumentation() {
    if (documentation == null)
      return "";

    return documentation.getDocumentation();
  }

  public final String getInstanceName() {
    if (instanceName != null)
      return instanceName;

    return instanceName = JavaBinding.getInstanceName(getModel());
  }

  public final String getClassName(Plan parent) {
    if (className != null)
      return className;

    if (parent != null)
      return className = AliasPlan.getClassName(getModel(), parent.getModel());

    return className = AliasPlan.getClassName(getModel(), null);
  }

  public final String getClassWithInconvertible(Plan parent) {
    if (classInconvertibleName != null)
      return classInconvertibleName;

    if (parent != null)
      return classInconvertibleName = AliasPlan.getClassNameWithInconvertible(getModel(), parent.getModel());

    return classInconvertibleName = AliasPlan.getClassNameWithInconvertible(getModel(), null);
  }

  public final String getClassSimpleName() {
    if (classSimpleName != null)
      return classSimpleName;

    return classSimpleName = JavaBinding.getClassSimpleName(getModel());
  }

  public final String getPackageName() {
    if (packageName != null)
      return packageName;

    return packageName = getName().getNamespaceURI().getPackageName().toString();
  }
}
