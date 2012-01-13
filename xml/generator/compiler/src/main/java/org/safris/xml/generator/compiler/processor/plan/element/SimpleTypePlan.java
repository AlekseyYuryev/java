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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.NamedPlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.AnyableModel;
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.NamedModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.UnionModel;

public class SimpleTypePlan<T extends SimpleTypeModel> extends AliasPlan<T> implements EnumerablePlan, ExtensiblePlan, NativeablePlan {
  private static SimpleTypeModel getGreatestCommonType(Collection<SimpleTypeModel> types, boolean includeEnums) {
    if (types == null || types.size() == 0)
      return null;

    if (types.size() == 1)
      return types.iterator().next();

    final Iterator<SimpleTypeModel> iterator = types.iterator();
    SimpleTypeModel gct = getGreatestCommonType(iterator.next(), iterator.next(), includeEnums);
    while (iterator.hasNext() && gct != null)
      gct = getGreatestCommonType(gct, iterator.next(), includeEnums);

    return gct;
  }

  private static SimpleTypeModel getGreatestCommonType(SimpleTypeModel model1, SimpleTypeModel model2, boolean includeEnums) {
    if (model1 == null || model2 == null)
      return null;

    if (!includeEnums) {
      // First we ignore enumeration types because their native types dont
      // get exposed to the user
      if (model1.getEnumerations().size() != 0) {
        if (model2.getEnumerations().size() != 0)
          return null;

        return model2;
      }

      if (model2.getEnumerations().size() != 0)
        return model1;
    }

    UniqueQName name1;
    UniqueQName name2;

    // Second try to see if we can match using the knowledge of type inheritance
    SimpleTypeModel type1 = model1;
    do
    {
      name1 = type1.getName();
      if (name1 == null)
        name1 = NamedModel.getNameOfRestrictionBase(type1);

      SimpleTypeModel type2 = model2;
      do
      {
        name2 = type2.getName();
        if (name2 == null)
          name2 = NamedModel.getNameOfRestrictionBase(type2);

        if (name1.equals(name2))
          return type1;
      }
      while((type2 = type2.getSuperType()) != null || (type2 = ComplexTypeModel.Undefined.parseComplexType(XSTypeDirectory.lookupSuperType(name2))) != null);
    }
    while((type1 = type1.getSuperType()) != null || (type1 = ComplexTypeModel.Undefined.parseComplexType(XSTypeDirectory.lookupSuperType(name1))) != null);

    return null;
  }

  private static boolean digList(SimpleTypeModel model) {
    boolean list = model.isList();
    if (!list) {
      SimpleTypeModel type = model;
      while ((type = type.getSuperType()) != null)
        if (list = type.isList())
          break;
    }

    return list;
  }

  private static SimpleTypeModel digBaseXSItemTypeName(SimpleTypeModel model, boolean includeEnums) {
    SimpleTypeModel itemType;
    SimpleTypeModel type = model;
    do
    {
      itemType = getGreatestCommonType(type.getItemTypes(), includeEnums);
      if (itemType == null)
        continue;

      UniqueQName itemName = itemType.getName();
      if (itemName == null)
        itemName = NamedModel.getNameOfRestrictionBase(itemType);

      if (UniqueQName.XS.getNamespaceURI().equals(itemName.getNamespaceURI()))
        return itemType;
    }
    while((type = type.getSuperType()) != null);

    return null;
  }

  private static SimpleTypeModel digBaseNonXSType(SimpleTypeModel model) {
    SimpleTypeModel type = model;
    SimpleTypeModel retval = null;
    do
    {
      if (type.getName() != null && UniqueQName.XS.getNamespaceURI().equals(type.getName().getNamespaceURI()))
        break;

      retval = type;
    }
    while((type = type.getSuperType()) != null);

    return retval;
  }

  private String superClassNameWithType;
  private String superClassNameWithoutType;

  private LinkedHashSet<PatternPlan> patterns = null;
  private LinkedHashSet<EnumerationPlan> enumerations = null;
  private Boolean hasEnumerations;
  private Boolean hasSuperEnumerations;

  private String nativeItemClassName = null;
  private String nativeNonEnumItemClassName = null;
  private String nativeInterface = null;
  private String nativeNonEnumInterface = null;
  private String nativeImplementation = null;
  private String nativeNonEnumImplementation = null;
  private String nativeFactory = null;
  private String nonEnumNativeFactory = null;
  private boolean list = false;
  private String baseNonXSTypeClassName = null;

  private UniqueQName superTypeName = null;
  private UniqueQName baseNonXSTypeName = null;
  private UniqueQName baseXSTypeName = null;
  private UniqueQName baseXSItemTypeName = null;
  private UniqueQName baseXSNonEnumItemTypeName = null;

  private boolean parsedSuperType = false;
  private NamedPlan superType = null;

  private boolean isUnionWithNonEnumeration;

  private void digItemTypes(SimpleTypeModel baseNonXSType) {
    SimpleTypeModel baseXSItemType = digBaseXSItemTypeName(getModel(), true);
    SimpleTypeModel baseXSNonEnumItemType = digBaseXSItemTypeName(getModel(), false);

    if (baseXSItemType != null)
      baseXSItemTypeName = getItemName(baseXSItemType);
    else
      baseXSItemTypeName = getItemName(baseNonXSType.getSuperType());

    if (baseXSNonEnumItemType != null)
      baseXSNonEnumItemTypeName = getItemName(baseXSNonEnumItemType);
    else
      baseXSNonEnumItemTypeName = getItemName(baseNonXSType.getSuperType());
  }

  private static UniqueQName getItemName(SimpleTypeModel model) {
    UniqueQName name = model.getName();
    if (name == null)
      name = NamedModel.getNameOfRestrictionBase(model);

    return name;
  }

  public SimpleTypePlan(T model, Plan parent) {
    super(model.getRedefine() != null ? (T)model.getRedefine() : model, parent);
    if (model instanceof AnyableModel)
      return;

    // Gets the XS pre-simpleType name of the type
    final SimpleTypeModel baseNonXSType = digBaseNonXSType(model);
    if (baseNonXSType != null)
      baseNonXSTypeName = baseNonXSType.getName();

    superTypeName = model.getSuperType().getName();
    superClassNameWithoutType = AliasPlan.getClassName(model.getSuperType(), null);
    superClassNameWithType = superClassNameWithoutType + "<T>";

    // Gets the XS simpleType name of the itemType
    digItemTypes(baseNonXSType);

    baseNonXSTypeClassName = JavaBinding.getClassName(baseNonXSType);

    final XSTypeDirectory baseXSItemTypeDirectory = XSTypeDirectory.parseType(baseXSItemTypeName);
    final XSTypeDirectory baseXSNonEnumItemTypeDirectory = XSTypeDirectory.parseType(baseXSNonEnumItemTypeName);
    if (baseXSItemTypeDirectory == null)
      throw new CompilerError("Should always be able to resolve the type for name: " + getName());

    if (this.list = baseXSItemTypeDirectory.getNativeBinding().isList())
      nativeItemClassName = baseXSItemTypeDirectory.getNativeBinding().getNativeClass().getType().getName();
    else {
      this.list = digList(getModel());
      nativeItemClassName = baseXSItemTypeDirectory.getNativeBinding().getNativeClass().getCls().getName();
    }

    if (this.list = baseXSNonEnumItemTypeDirectory.getNativeBinding().isList())
      nativeNonEnumItemClassName = baseXSNonEnumItemTypeDirectory.getNativeBinding().getNativeClass().getType().getName();
    else {
      this.list = digList(getModel());
      nativeNonEnumItemClassName = baseXSNonEnumItemTypeDirectory.getNativeBinding().getNativeClass().getCls().getName();
    }

    nativeFactory = baseXSItemTypeDirectory.getNativeFactory();
    nonEnumNativeFactory = baseXSNonEnumItemTypeDirectory.getNativeFactory();
    if (isList()) {
      nativeInterface = List.class.getName() + "<" + nativeItemClassName + ">";
      nativeImplementation = ArrayList.class.getName() + "<" + nativeItemClassName + ">";
      nativeNonEnumInterface = List.class.getName() + "<" + nativeNonEnumItemClassName + ">";
      nativeNonEnumImplementation = ArrayList.class.getName() + "<" + nativeNonEnumItemClassName + ">";
    }
    else {
      nativeInterface = nativeItemClassName;
      nativeImplementation = nativeItemClassName;
      nativeNonEnumInterface = nativeNonEnumItemClassName;
      nativeNonEnumImplementation = nativeNonEnumItemClassName;
    }

    isUnionWithNonEnumeration = getSuperType() != null && ((SimpleTypePlan)getSuperType()).isUnionWithNonEnumeration();
    for (Model child : model.getChildren()) {
      if (!(child instanceof UnionModel))
        continue;

      for (SimpleTypeModel memberType : ((UnionModel)child).getMemberTypes()) {
        if (memberType.getEnumerations().size() != 0)
          continue;

        isUnionWithNonEnumeration = true;
        break;
      }
    }
  }

  public boolean isUnionWithNonEnumeration() {
    return isUnionWithNonEnumeration;
  }

  public final String getBaseNonXSTypeClassName() {
    return baseNonXSTypeClassName;
  }

  public final UniqueQName getBaseXSTypeName() {
    return baseXSTypeName;
  }

  public final UniqueQName getBaseXSItemTypeName() {
    return baseXSItemTypeName;
  }

  public final boolean isList() {
    return list;
  }

  public final String getNativeItemClassNameInterface() {
    return nativeInterface;
  }

  public final String getNativeNonEnumItemClassNameInterface() {
    return nativeNonEnumInterface;
  }

  public final String getNativeItemClassNameImplementation() {
    return nativeImplementation;
  }

  public final String getNativeNonEnumItemClassNameImplementation() {
    return nativeNonEnumImplementation;
  }

  public final String getNativeFactory() {
    return nativeFactory;
  }

  public final String getNativeFactoryNonEnum() {
    return nonEnumNativeFactory;
  }

  public String getSuperClassNameWithoutType() {
    return superClassNameWithoutType;
  }

  public final LinkedHashSet<EnumerationPlan> getEnumerations() {
    if (enumerations != null)
      return enumerations;

    return enumerations = Plan.<EnumerationPlan>analyze(getModel().getEnumerations(), this);
  }

  public final String getNativeItemClassName() {
    return nativeItemClassName;
  }

  public final String getNativeNonEnumItemClassName() {
    return nativeNonEnumItemClassName;
  }

  public final boolean hasEnumerations() {
    if (hasEnumerations != null)
      return hasEnumerations;

    return hasEnumerations = hasEnumerations(getModel());
  }

  public final boolean hasSuperEnumerations() {
    if (hasSuperEnumerations != null)
      return hasSuperEnumerations;

    return hasSuperEnumerations = getModel().getSuperType() instanceof EnumerableModel ? hasEnumerations((EnumerableModel)getModel().getSuperType()) : false;
  }

  public final Collection<PatternPlan> getPatterns() {
    if (patterns != null)
      return patterns;

    return patterns = Plan.<PatternPlan>analyze(getModel().getPatterns(), this);
  }

  public String getSuperClassNameWithType() {
    return superClassNameWithType;
  }

  public Plan getSuperType() {
    if (parsedSuperType)
      return superType;

    superType = parseNamedPlan(superTypeName);
    if (superType == this)
      return superType = null;

    parsedSuperType = true;
    return superType;
  }
}
