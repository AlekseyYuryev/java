/* Copyright (c) 2006 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.xml.generator.compiler.lang;

import org.safris.commons.lang.Strings;
import org.safris.commons.xml.Prefix;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.ReferableModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.lexer.processor.model.element.RedefineModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;

public final class JavaBinding {
  private final static String ATTRIBUTE_SUFFIX = "$";
  private final static String NOTATION_MIDFIX = "$";
  private final static String COMPLEXTYPE_PREFIX = "$";

  public static String getInstanceName(final Model model) {
    if (!(model instanceof Nameable) || ((Nameable<?>)model).getName() == null)
      throw new CompilerError("Method being called on a model with no name");

    final Prefix prefix = JavaBinding.getPrefix(model);
    if (model instanceof AttributeModel)
      return "_" + prefix.toString() + "_" + Strings.toJavaCase(((SimpleTypeModel<?>)model).getName().getLocalPart()) + ATTRIBUTE_SUFFIX;

    if (model instanceof ElementModel)
      return "_" + prefix.toString() + "_" + Strings.toJavaCase(((SimpleTypeModel<?>)model).getName().getLocalPart());

    if (model instanceof NotationModel)
      return "_" + prefix.toString() + "_" + NOTATION_MIDFIX + Strings.toJavaCase(((NotationModel)model).getName().getLocalPart());

    if (model instanceof SimpleTypeModel)
      return "_" + COMPLEXTYPE_PREFIX.toLowerCase() + prefix.toString() + "_" + Strings.toJavaCase(((SimpleTypeModel<?>)model).getName().getLocalPart());

    throw new CompilerError("model is not instanceof {AttributeModel,ElementModel,NotationModel,SimpleTypeModel}");
  }

  public static String getClassName(final Model model) {
    if (model == null)
      return null;

    if (!(model instanceof Nameable) || ((Nameable<?>)model).getName() == null)
      throw new CompilerError("Method being called on a model with no name");

    final String pkg = ((Nameable<?>)model).getName().getNamespaceURI().getPackageName().toString();
    final String simpleName = getClassSimpleName(model);
    return pkg + "." + simpleName;
  }

  private static boolean isRef(final Model model) {
    return model instanceof ReferableModel && ((ReferableModel<?>)model).getRef() != null;
  }

  private static boolean isNested(final Model model) {
    return !(model.getParent() instanceof SchemaModel || (model.getParent() instanceof RedefineModel && model.getParent().getParent() instanceof SchemaModel) || (model instanceof Nameable && XSTypeDirectory.parseType(((Nameable<?>)model).getName()) != null));
  }

  private static Prefix getPrefix(final Model model) {
    return !JavaBinding.isNested(model) || JavaBinding.isRef(model) || model instanceof AttributeModel && Form.QUALIFIED.equals(((AttributeModel)model).getForm()) ? ((Nameable<?>)model).getName().getPrefix() : Prefix.EMPTY;
  }

  public static String getClassSimpleName(final Model model) {
    if (!(model instanceof Nameable) || ((Nameable<?>)model).getName() == null)
      throw new CompilerError("Method being called on a model with no name");

    final Prefix prefix = JavaBinding.getPrefix(model);
    if (model instanceof AttributeModel)
      return prefix.toString() + "_" + Strings.toJavaCase(((SimpleTypeModel<?>)model).getName().getLocalPart()) + ATTRIBUTE_SUFFIX;

    if (model instanceof ElementModel)
      return prefix.toString() + "_" + Strings.toJavaCase(((SimpleTypeModel<?>)model).getName().getLocalPart());

    if (model instanceof NotationModel)
      return prefix.toString() + "_" + NOTATION_MIDFIX + Strings.toJavaCase(((NotationModel)model).getName().getLocalPart());

    if (model instanceof SimpleTypeModel)
      return COMPLEXTYPE_PREFIX + prefix.toString() + "_" + Strings.toJavaCase(((SimpleTypeModel<?>)model).getName().getLocalPart());

    throw new CompilerError("model is not instanceof {AttributeModel,ElementModel,NotationModel,SimpleTypeModel}");
  }

  private JavaBinding() {
  }
}