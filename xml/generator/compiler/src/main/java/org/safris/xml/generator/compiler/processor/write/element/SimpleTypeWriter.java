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

package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.xml.namespace.QName;
import org.safris.commons.util.Collections;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.EnumerationPlan;
import org.safris.xml.generator.compiler.processor.plan.element.PatternPlan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleTypePlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.$xs_ID;
import org.w3.x2001.xmlschema.$xs_anySimpleType;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class SimpleTypeWriter<T extends SimpleTypePlan> extends Writer<T> {
  protected static void writeIdLookup(StringWriter writer, SimpleTypePlan plan, Plan parent) {
    if (!$xs_ID.class.getName().equals(plan.getSuperClassNameWithoutType()))
      return;

    final String className;
    final String instanceName;
    if (plan.hasEnumerations()) {
      if (((EnumerablePlan)plan).hasSuperEnumerations())
        className = ((ExtensiblePlan)plan).getSuperClassNameWithoutType() + ".RESTRICTION";
      else
        className = "RESTRICTION";

      instanceName = "id.getText()";
    }
    else {
      className = String.class.getName();
      instanceName = "id";
    }

    writer.write("public static " + plan.getClassName(parent) + " lookupId(" + className + " id)\n");
    writer.write("{\n");
    writer.write("final " + Map.class.getName() + "<" + Object.class.getName() + ",? extends " + $xs_ID.class.getName() + "> idMap = namespaceIds.get(NAME.getNamespaceURI());\n");
    writer.write("if(idMap == null)\n");
    writer.write("return null;\n");
    writer.write("final " + $xs_ID.class.getName() + " value = idMap.get(" + instanceName + ");\n");
    writer.write("if(value instanceof " + plan.getClassName(parent) + ")\n");
    writer.write("return (" + plan.getClassName(parent) + ")value;\n");
    writer.write("return null;\n");
    writer.write("}\n");

    writer.write("public static " + Collection.class.getName() + "<" + plan.getClassName(parent) + "> lookupId()\n");
    writer.write("{\n");
    writer.write("final " + Map.class.getName() + "<" + Object.class.getName() + ",? extends " + $xs_ID.class.getName() + "> idMap = namespaceIds.get(NAME.getNamespaceURI());\n");
    writer.write("if(idMap == null)\n");
    writer.write("return null;\n");
    writer.write("final " + Collection.class.getName() + "<" + plan.getClassName(parent) + "> ids = new " + ArrayList.class.getName() + "<" + plan.getClassName(parent) + ">();\n");
    writer.write("for(" + $xs_ID.class.getName() + " id : idMap.values())\n");
    writer.write("if(id.getClass().equals(" + plan.getClassName(parent) + ".class))\n");
    writer.write("ids.add((" + plan.getClassName(parent) + ")id);\n");
    writer.write("return ids;\n");
    writer.write("}\n");
  }

  protected static void getNativeConstructors(StringWriter writer, SimpleTypePlan plan, Plan parent) {
    if (plan.getNativeItemClassNameInterface() == null || (plan.isList() && plan.hasEnumerations()))
      return;

    String accessibility;
    if (plan instanceof EnumerablePlan && ((EnumerablePlan)plan).hasEnumerations() && !plan.isUnionWithNonEnumeration()) {
      accessibility = "protected ";
    }
    else {
      // DOCUMENTATION
      writer.write(plan.getDocumentation());

      accessibility = "public ";
    }

    writer.write(accessibility + plan.getClassSimpleName() + "(" + plan.getNativeNonEnumItemClassNameInterface() + " text)\n");
    writer.write("{\n");
    writer.write("super(text);\n");
    writer.write("}\n");

    if (plan.isList()) {
      writer.write(accessibility + plan.getClassSimpleName() + "(" + plan.getNativeNonEnumItemClassName() + " ... text)\n");
      writer.write("{\n");
      writer.write("super(" + Arrays.class.getName() + ".asList(text));\n");
      writer.write("}\n");

    }

//      if(plan.getNativeItemClassName() == null && XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
//      {
//          writer.write(accessibility + plan.getClassSimpleName() + "(" + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> text)\n");
//          writer.write("{\n");
//          writer.write("super(text);\n");
//          writer.write("}\n");
//      }
  }

  protected static void getRestrictions(StringWriter writer, SimpleTypePlan plan, Plan parent) {
    if (!plan.hasEnumerations())
      return;

    if (!(plan instanceof EnumerablePlan) || !((EnumerablePlan)plan).hasEnumerations())
      return;

    boolean hasEnumerations = ((EnumerablePlan)plan).hasEnumerations();
    boolean hasSuperEnumerations = ((EnumerablePlan)plan).hasSuperEnumerations();

    if (hasEnumerations) {
      for (final EnumerationPlan enumeration : ((EnumerablePlan)plan).getEnumerations()) {
        if (XSTypeDirectory.QNAME.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
          writer.write("public static final RESTRICTION " + enumeration.getDeclarationName() + " = new RESTRICTION(\"" +  enumeration.getValue() + "\");\n");
        else
          writer.write("public static final RESTRICTION " + enumeration.getDeclarationName() + " = new RESTRICTION(\"" +  enumeration.getValue().getLocalPart() + "\");\n");
      }
    }

    writer.write("public static class RESTRICTION");
    if (hasSuperEnumerations)
      writer.write(" extends " + ((ExtensiblePlan)plan).getSuperClassNameWithoutType() + ".RESTRICTION\n");
    else
      writer.write("\n");

    writer.write("{\n");

    if (!hasSuperEnumerations && hasEnumerations)
      writer.write("protected final " + plan.getNativeItemClassName() + " text;\n");

    writer.write("protected RESTRICTION(" + String.class.getName() + " text)\n");
    writer.write("{\n");

    if (hasSuperEnumerations)
      writer.write("super(text);\n");
    else if (hasEnumerations) {
      if (plan.getNativeFactory() != null) {
        writer.write("this.text = " + plan.getNativeFactory() + "(text);\n");
      }
      else
        writer.write("this.text = text;\n");
    }

    writer.write("}\n");

    if (hasEnumerations && !hasSuperEnumerations) {
      writer.write("public " + plan.getNativeItemClassName() + " getText()\n");
      writer.write("{\n");
      writer.write("return text;\n");
      writer.write("}\n");
    }

    writer.write("}\n");

    final String restrictionClassName;
    if (hasSuperEnumerations)
      restrictionClassName = ((ExtensiblePlan)plan).getSuperClassNameWithoutType() + ".RESTRICTION";
    else
      restrictionClassName = "RESTRICTION";

    // DOCUMENTATION
    writer.write(plan.getDocumentation());

    if (plan.isList()) {
      writer.write("public " + plan.getClassSimpleName() + "(" + List.class.getName() + "<" + restrictionClassName + "> restrictions)\n");
      writer.write("{\n");
      writer.write("super.setText(new " + plan.getNativeItemClassNameImplementation() + "());\n");
      writer.write("for(" + restrictionClassName + " temp : restrictions)\n");
      writer.write("if(temp != null)\n");
      writer.write("((" + List.class.getName() + ")super.getText()).add(temp.text);\n");
      writer.write("}\n");

      writer.write("public " + plan.getClassSimpleName() + "(" + restrictionClassName + " ... restrictions)\n");
      writer.write("{\n");
      writer.write("super.setText(new " + plan.getNativeItemClassNameImplementation() + "());\n");
      writer.write("for(" + restrictionClassName + " temp : restrictions)\n");
      writer.write("if(temp != null)\n");
      writer.write("((" + List.class.getName() + ")super.getText()).add(temp.text);\n");
      writer.write("}\n");
    }
    else {
      writer.write("public " + plan.getClassSimpleName() + "(" + restrictionClassName + " restriction)\n");
      writer.write("{\n");
      if (!hasSuperEnumerations)
        writer.write("super(restriction.getText());\n");
      else
        writer.write("super(restriction);\n");
      writer.write("}\n");
    }
  }

  protected static void getEncodeDecode(StringWriter writer, SimpleTypePlan plan, Plan parent) {
    // DECODE & ENCODE
    if (plan.isList()) {
      writer.write("protected void _$$decode(" + Element.class.getName() + " node, " + String.class.getName() + " value) throws " + ParseException.class.getName() + "\n");
      writer.write("{\n");
      writer.write("if(value == null || value.length() == 0)\n");
      writer.write("return;\n");
      writer.write("super.setText(new " + plan.getNativeItemClassNameImplementation() + "());\n");
      writer.write(StringTokenizer.class.getName() + " tokenizer = new " + StringTokenizer.class.getName() + "(value);\n");
      writer.write("while(tokenizer.hasMoreTokens())\n");
      String factoryEntry = "tokenizer.nextToken()";
      if (plan.getNativeFactory() != null)
        factoryEntry = plan.getNativeFactory() + "(" + factoryEntry + ")";

      writer.write("((" + List.class.getName() + ")super.getText()).add(" + factoryEntry + ");\n");
      writer.write("}\n");

      writer.write("protected " + String.class.getName() + " _$$encode(" + Element.class.getName() + " parent) throws " + MarshalException.class.getName() + "\n");
      writer.write("{\n");
      writer.write("return super.getText() != null && ((" + List.class.getName() + ")super.getText()).size() != 0 ? " + Collections.class.getName() + ".toString((" + List.class.getName() + "<" + plan.getNativeItemClassName() + ">)super.getText(), \" \") : null;\n");
      writer.write("}\n");
    }
  }

  protected void appendDeclaration(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a declaration");
  }

  protected void appendGetMethod(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a get method");
  }

  protected void appendSetMethod(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a set method");
  }

  protected void appendMarshal(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a marshal method");
  }

  protected void appendParse(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a parse method");
  }

  public void appendCopy(StringWriter writer, T plan, Plan parent, String variable) {
    throw new CompilerError("simpleType cannot have a copy statement");
  }

  protected void appendEquals(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a equals statement");
  }

  protected void appendHashCode(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("simpleType cannot have a hashCode statement");
  }

  protected final void appendPattern(StringWriter writer, Collection<PatternPlan> patterns) {
    if (patterns == null || patterns.size() == 0)
      return;

    writer.write("protected " + String.class.getName() + "[] _$$getPattern()\n");
    writer.write("{\n");
    writer.write("return new " + String.class.getName() + "[]\n");
    writer.write("{\n");
    String buf = "";
    for (final PatternPlan pattern : patterns)
      buf += ",\n\"" + pattern.getValue() + "\"";
    writer.write(buf.substring(2) + "\n");
    writer.write("};\n");
    writer.write("}\n");
  }

  protected final void appendOwner(StringWriter writer) {
    writer.write("public " + $xs_anySimpleType.class.getName() + "<? extends " + BindingType.class.getName() + "> owner()\n");
    writer.write("{\n");
    writer.write("return _$$getOwner();\n");
    writer.write("}\n");
  }

  protected void appendClass(StringWriter writer, T plan, Plan parent) {
    if (plan.getName() == null)
      throw new CompilerError("Why are we trying to write a class that has no name?");

    writer.write("package " + plan.getPackageName() + ";\n");

    writer.write("@" + SuppressWarnings.class.getSimpleName() + "(\"unchecked\")\n");
    writer.write("public abstract class " + plan.getClassSimpleName() + "<T extends " + BindingType.class.getName() + "> extends " + plan.getSuperClassNameWithType() + "\n");
    writer.write("{\n");

    writer.write("private static final " + QName.class.getName() + " NAME = new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\");\n");

    // ID LOOKUP
    writeIdLookup(writer, plan, parent);

    writer.write("static\n");
    writer.write("{\n");
    writer.write("_$$registerType(NAME, " + plan.getClassName(parent) + ".class);\n");
    writer.write("_$$registerSchemaLocation(NAME.getNamespaceURI(), " + plan.getClassSimpleName() + ".class, \"" + plan.getXsdLocation() + "\");\n");
    writer.write("}\n");

    // FACTORY METHOD
    writer.write("protected static " + plan.getClassSimpleName() + " newInstance(final " + plan.getBaseNonXSTypeClassName() + " inherits)\n");
    writer.write("{\n");
    writer.write("return new " + plan.getClassName(parent) + "()\n");
    writer.write("{\n");
    writer.write("protected " + plan.getBaseNonXSTypeClassName() + " inherits()\n");
    writer.write("{\n");
    writer.write("return inherits;\n");
    writer.write("}\n");
    writer.write("};\n");
    writer.write("}\n");

    // DOCUMENTATION
    writer.write(plan.getDocumentation());

    // COPY CONSTRUCTOR
    writer.write("public " + plan.getClassSimpleName() + "(" + plan.getClassName(null) + " copy)\n");
    writer.write("{\n");
    writer.write("super(copy);\n");
    writer.write("}\n");

    // ENUMERATIONS CONSTRUCTOR
    getRestrictions(writer, plan, parent);

    // NATIVE CONSTRUCTORS
    getNativeConstructors(writer, plan, parent);

    // DEFAULT CONSTRUCTOR
    if (!plan.hasEnumerations()) {
      // DOCUMENTATION
      writer.write(plan.getDocumentation());

      writer.write("public ");
    }
    else
      writer.write("protected ");
    writer.write(plan.getClassSimpleName() + "()\n");
    writer.write("{\n");
    writer.write("super();\n");
    writer.write("}\n");

    if (plan.getNativeItemClassNameInterface() != null) {
      writer.write("public " + plan.getNativeItemClassNameInterface() + " getText()\n");
      writer.write("{\n");
      if (!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
        writer.write("return (" + plan.getNativeItemClassNameInterface() + ")super.getText();\n");
      else
        writer.write("return super.getText();\n");
      writer.write("}\n");

      if (plan.isList()) {
        writer.write("public " + plan.getNativeItemClassName() + " getText(final int index)\n");
        writer.write("{\n");
        writer.write("final " + plan.getNativeItemClassNameInterface() + " values = getText();\n");
        writer.write("return values != null && -1 < index && index < values.size() ? values.get(index) : null;\n");
        writer.write("}\n");
      }

      if (plan.hasEnumerations()) {
        if (plan.isList()) {
          writer.write("public void setText(" + List.class.getName() + "<" + plan.getClassName(parent) + ".RESTRICTION> restriction)\n");
          writer.write("{\n");
          writer.write("super.setText(new " + plan.getNativeItemClassNameImplementation() + "());\n");
          writer.write("for(" + plan.getClassName(parent) + ".RESTRICTION temp : restriction)\n");
          writer.write("if(temp != null)\n");
          writer.write("((" + List.class.getName() + ")super.getText()).add(temp.text);\n");
          writer.write("}\n");
        }
        else {
          writer.write("public void setText(RESTRICTION restriction)\n");
          writer.write("{\n");
          writer.write("super.setText(restriction.text);\n");
          writer.write("}\n");

          if (plan.isUnionWithNonEnumeration()) {
            writer.write("public void setText(" + plan.getNativeNonEnumItemClassNameInterface() + " text)\n");
            writer.write("{\n");
            writer.write("super.setText(text);\n");
            writer.write("}\n");
          }
        }
      }
      else {
        writer.write("public void setText(" + plan.getNativeItemClassNameInterface() + " text)\n");
        writer.write("{\n");
        writer.write("super.setText(text);\n");
        writer.write("}\n");

        if (plan.getNativeItemClassName() == null && XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName())) {
          writer.write("public void setText(" + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> text)\n");
          writer.write("{\n");
          writer.write("super.setText(text);\n");
          writer.write("}\n");
        }
      }
    }

    // DECODE & ENCODE
    getEncodeDecode(writer, plan, parent);

    // INHERITS
    writer.write("protected abstract " + plan.getBaseNonXSTypeClassName() + " inherits();\n");

    // GETNAME
    writer.write("protected " + QName.class.getName() + " _$$getName()\n");
    writer.write("{\n");
    writer.write("return _$$getName(_$$inheritsInstance());\n");
    writer.write("}\n");

    // GETTYPE
    writer.write("protected " + QName.class.getName() + " _$$getTypeName()\n");
    writer.write("{\n");
    writer.write("return NAME;\n");
    writer.write("}\n");

    // PATTERN
    appendPattern(writer, plan.getPatterns());

    // MARSHAL
    writer.write("protected " + Attr.class.getName() + " marshalAttr(" + String.class.getName() + " name, " + Element.class.getName() + " parent) throws " + MarshalException.class.getName() + "\n");
    writer.write("{\n");
    writer.write("return super.marshalAttr(name, parent);\n");
    writer.write("}\n");
    writer.write("protected " + Element.class.getName() + " marshal() throws " + MarshalException.class.getName() + ", " + ValidationException.class.getName() + "\n");
    writer.write("{\n");
    writer.write(Element.class.getName() + " root = createElementNS(_$$getName().getNamespaceURI(), _$$getName().getLocalPart());\n");
    writer.write("return marshal(root, _$$getName(), _$$getTypeName(_$$inheritsInstance()));\n");
    writer.write("}\n");
    writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
    writer.write("{\n");
    writer.write("return super.marshal(parent, name, typeName);\n");
    writer.write("}\n");

    // CLONE
    writer.write("public " + plan.getClassName(parent) + " clone()\n");
    writer.write("{\n");
    writer.write("return " + plan.getClassName(parent) + ".newInstance(this);\n");
    writer.write("}\n");

    // EQUALS
    writer.write("public boolean equals(" + Object.class.getName() + " obj)\n");
    writer.write("{\n");
    // NOTE: This is not checking whether getTEXT() is equal between this and obj
    // NOTE: because this class does not contain the text field.
    writer.write("return super.equals(obj);\n");
    writer.write("}\n");

    // HASHCODE
    writer.write("public int hashCode()\n");
    writer.write("{\n");
    writer.write("int hashCode = super.hashCode();\n");
    writer.write("hashCode += NAME.hashCode();\n");
    writer.write("return hashCode;\n");
    writer.write("}\n");

    writer.write("}\n");
  }
}
