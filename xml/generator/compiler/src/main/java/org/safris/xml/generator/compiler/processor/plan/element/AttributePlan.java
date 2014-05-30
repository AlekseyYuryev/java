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

import javax.xml.namespace.QName;

import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.RestrictablePlan;
import org.safris.xml.generator.lexer.processor.Formable;
import org.safris.xml.generator.lexer.processor.model.AnyableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;

public class AttributePlan extends SimpleTypePlan<AttributeModel> implements EnumerablePlan, ExtensiblePlan, Formable<Plan<?>>, NativeablePlan, NestablePlan, RestrictablePlan {
  private final AttributeModel attribute;
  private final boolean ref;
  private final boolean restriction;
  private final boolean fixed;
  private final QName _default;
  private final Use use;

  private String thisClassNameWithType = null;
  private String declarationRestrictionGeneric = null;
  private String declarationRestrictionSimpleName = null;

  private String superClassNameWithType = null;
  private String superClassNameWithoutType = null;

  private boolean nested = false;
  private Form formDefault = null;

  public AttributePlan(final AttributeModel model, final Plan<?> parent) {
    super(model, parent);
    ref = (attribute = getModel()) != model;
    restriction = model.getRestrictionOwner() != null;
    fixed = model.getFixed() != null;
    _default = fixed ? model.getFixed() : model.getDefault();
    use = model.getUse();
    if (model instanceof AnyableModel)
      return;

    if (isRestriction())
      superClassNameWithoutType = AliasPlan.getClassName(attribute.getRestrictionOwner(), null) + "." + JavaBinding.getClassSimpleName((SimpleTypeModel<?>)getModel().getRestriction());
    else
      superClassNameWithoutType = AliasPlan.getClassName(attribute.getSuperType(), attribute.getSuperType().getParent());

    superClassNameWithType = superClassNameWithoutType;

    thisClassNameWithType = !ref && attribute.getParent() instanceof SchemaModel ? superClassNameWithType : null;

    nested = ref || !(attribute.getParent() instanceof SchemaModel);
    formDefault = model.getForm() == Form.QUALIFIED || ref ? Form.QUALIFIED : attribute.getFormDefault();
  }

  public final Use getUse() {
    return use;
  }

  public final boolean isFixed() {
    return fixed;
  }

  public final String getDefaultInstance(final Plan<?> parent) {
    if (getDefault() == null)
      return null;

    String _default = XSTypeDirectory.QNAME.getNativeBinding().getName().equals(getBaseXSItemTypeName()) ? getDefault().toString() : getDefault().getLocalPart();
    if (hasEnumerations() && (!(getSuperType() instanceof SimpleTypePlan) || !((SimpleTypePlan<?>)getSuperType()).isUnion()))
      _default = getClassName(parent) + "." + EnumerationPlan.getDeclarationName(getDefault());
    else
      _default = "\"" + _default + "\"";

    String defaultInstance = "new " + getClassName(parent) + "(";
    if (!hasEnumerations() && getNativeFactory() != null)
      defaultInstance += getNativeFactory() + "(" + _default + "))";
    else
      defaultInstance += "" + _default + ")";

    return defaultInstance;
  }

  public final String getFixedInstanceCall(final Plan<?> parent) {
    if (!fixed)
      return "";

    String defaultInstance = getDefaultInstance(parent);
    if (isRestriction())
      return "super." + getDeclarationRestrictionSimpleName() + "(" + defaultInstance + ");\n";

    return "_$$setAttribute(" + getInstanceName() + ", this, " + defaultInstance + ");\n";
  }

  public final QName getDefault() {
    return _default;
  }

  public final boolean isRestriction() {
    return restriction;
  }

  public final AttributeModel getModel() {
    return super.getModel().getRef() != null ? super.getModel().getRef() : super.getModel();
  }

  public final String getSuperClassNameWithoutType() {
    return superClassNameWithoutType;
  }

  public final boolean isRef() {
    return ref;
  }

  public final String getThisClassNameWithType(final Plan<?> parent) {
    if (thisClassNameWithType != null)
      return thisClassNameWithType;

    //if (!UniqueQName.XS.getNamespaceURI().equals(getModel().getSuperType().getName().getNamespaceURI()))
    //final AliasModel model = !getModel().isRestriction() ? getModel().getSuperType() : getModel();

    return AliasPlan.getClassName(getModel(), parent.getModel());
  }

  public final String getThisClassNameWithTypeWithInconvertible(final Plan<?> parent) {
    if (thisClassNameWithType != null)
      return thisClassNameWithType;

    //if (!UniqueQName.XS.getNamespaceURI().equals(getModel().getSuperType().getName().getNamespaceURI()))
    //final AliasModel model = !getModel().isRestriction() ? getModel().getSuperType() : getModel();

    return AliasPlan.getClassName(getModel(), parent.getModel());
  }

  public final String getDeclarationRestrictionGeneric(final Plan<?> parent) {
    if (!isRestriction())
      return getThisClassNameWithTypeWithInconvertible(parent);

    if (declarationRestrictionGeneric != null)
      return declarationRestrictionGeneric;

    RestrictableModel<?> first = null;
    RestrictableModel<?> prior = getModel();
    while (prior.getRestriction() != null) {
      first = prior;
      prior = prior.getRestriction();
    }

    return declarationRestrictionGeneric = AliasPlan.getClassName(first.getRestrictionOwner(), null) + "." + JavaBinding.getClassSimpleName((Model)prior);
  }

  public final String getDeclarationRestrictionSimpleName() {
    if (!isRestriction())
      return getClassSimpleName();

    if (declarationRestrictionSimpleName != null)
      return declarationRestrictionSimpleName;

    RestrictableModel<?> prior = getModel();
    while (prior.getRestriction() != null)
      prior = prior.getRestriction();

    return declarationRestrictionSimpleName = JavaBinding.getClassSimpleName((Model)prior);
  }

  public final String getSuperClassNameWithType() {
    return superClassNameWithType;
  }

  public final String getCopyClassName(final Plan<?> parent) {
    if (!getModel().getSuperType().getName().equals(XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName()))
      return AliasPlan.getClassName(getModel().getSuperType(), null);

    if (getModel().getRef() != null && getModel().getRef().getName() != null)
      return AliasPlan.getClassName(getModel().getRef(), parent != null ? parent.getModel() : null);

    return AliasPlan.getClassName(getModel(), parent != null ? parent.getModel() : null);
  }

  public final boolean isNested() {
    return nested;
  }

  public final Form getFormDefault() {
    return formDefault;
  }
}