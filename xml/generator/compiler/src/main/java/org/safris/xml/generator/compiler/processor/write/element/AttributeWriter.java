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
import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.annotation.AttributeSpec;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.runtime.AttributeAudit;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.w3c.dom.Element;

public class AttributeWriter extends SimpleTypeWriter<AttributePlan> {
  protected void appendDeclaration(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRestriction())
      return;

    writer.write("private " + AttributeAudit.class.getName() + "<" + plan.getThisClassNameWithType(parent) + "> " + plan.getInstanceName() + " = new " + AttributeAudit.class.getName() + "<" + plan.getThisClassNameWithType(parent) + ">(" + plan.getDefaultInstance(parent) + ", new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), " + Form.QUALIFIED.equals(plan.getFormDefault()) + ", " + Use.REQUIRED.equals(plan.getUse()) + ");\n");
  }

  protected void appendGetMethod(StringWriter writer, AttributePlan plan, Plan parent) {
    writer.write("public " + plan.getDeclarationRestrictionGeneric(parent) + " get" + plan.getClassSimpleName() + "()\n");
    writer.write("{\n");
    if (plan.isRestriction())
      writer.write("return super.get" + plan.getDeclarationRestrictionSimpleName() + "();\n");
    else
      writer.write("return " + plan.getInstanceName() + ".getAttribute();\n");
    writer.write("}\n");
  }

  protected void appendSetMethod(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRestriction()) {
      writer.write("/**\n");
      writer.write(" * NOTE: This method has been restricted by a more specific signature.\n");
      writer.write(" * Use of this method WILL CAUSE an IllegalArgumentException!\n");
      writer.write(" * Please correct your argument to use the alternate method signature.\n");
      writer.write(" */\n");
      writer.write("public void set" + plan.getDeclarationRestrictionSimpleName() + "(" + plan.getDeclarationRestrictionGeneric(parent) + " " + plan.getInstanceName() + ")\n");
      writer.write("{\n");
      writer.write("throw new " + IllegalArgumentException.class.getName() + "(\"This method has been restricted by a more specific signature. Please correct your argument to use the alternate method signature.\");\n");
      writer.write("}\n");
    }

    writer.write("@" + AttributeSpec.class.getName() + "(use=\"" + plan.getUse().getValue() + "\")\n");
    writer.write("public void set" + plan.getClassSimpleName() + "(" + plan.getThisClassNameWithType(parent) + " " + plan.getInstanceName() + ")\n");
    writer.write("{\n");
    if (plan.isRestriction())
      writer.write("super.set" + plan.getDeclarationRestrictionSimpleName() + "(" + plan.getInstanceName() + ");\n");
    else
      writer.write("_$$setAttribute(this." + plan.getInstanceName() + ", this, " + plan.getInstanceName() + ");\n");
    writer.write("}\n");
  }

  protected void appendMarshal(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRestriction()) {
      if (!plan.isFixed())
        return;

      if (Form.QUALIFIED.equals(plan.getFormDefault())) {
        writer.write("if(!node.hasAttributeNS(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\"))\n");
        writer.write("{\n");
        if (XSTypeDirectory.QNAME.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
          writer.write("node.setAttributeNS(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getPrefix() + "\" + \":" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getLocalPart() + "\");\n");
        else
          writer.write("node.setAttributeNS(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getPrefix() + "\" + \":\" + \"" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getLocalPart() + "\");\n");
        writer.write("}\n");
      }
      else {
        writer.write("if(!node.hasAttribute(\"" + plan.getName().getLocalPart() + "\"))\n");
        if (XSTypeDirectory.QNAME.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName())) {
          writer.write("{\n");
          writer.write("node.setAttribute(\"" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getPrefix() + "\" + \":" + plan.getDefault().getLocalPart() + "\");\n");
          writer.write("}\n");
        }
        else
          writer.write("node.setAttribute(\"" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getLocalPart() + "\");\n");
      }

      return;
    }

    writer.write(plan.getInstanceName() + ".marshal(node);\n");
  }

  protected void appendParse(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRestriction())
      return;

    if (Form.QUALIFIED.equals(plan.getFormDefault()))
      writer.write("else if(\"" + plan.getName().getNamespaceURI() + "\".equals(attribute.getNamespaceURI()) && \"" + plan.getName().getLocalPart() + "\".equals(attribute.getLocalName()))\n");
    else
      writer.write("else if(attribute.getNamespaceURI() == null && \"" + plan.getName().getLocalPart() + "\".equals(attribute.getLocalName()))\n");

    writer.write("{\n");
    writer.write("return _$$setAttribute(this." + plan.getInstanceName() + ", this, (" + plan.getThisClassNameWithType(parent) + ")" + Binding.class.getName() + "._$$parseAttr(" + plan.getClassName(parent) + ".class, (" + Element.class.getName() + ")attribute.getOwnerElement(), attribute));\n");
    writer.write("}\n");
  }

  public void appendCopy(StringWriter writer, AttributePlan plan, Plan parent, String variable) {
    if (plan.isRestriction())
      return;

    writer.write("this." + plan.getInstanceName() + " = " + variable + "." + plan.getInstanceName() + ";\n");
  }

  protected void appendEquals(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRestriction())
      return;

    writer.write("if(" + plan.getInstanceName() + " != null ? !" + plan.getInstanceName() + ".equals(that." + plan.getInstanceName() + ") : that." + plan.getInstanceName() + " != null)\n");
    writer.write("return _$$failEquals();\n");
  }

  protected void appendHashCode(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRestriction())
      return;

    writer.write("hashCode += " + plan.getInstanceName() + " != null ? " + plan.getInstanceName() + ".hashCode() : -1;\n");
  }

  protected void appendClass(StringWriter writer, AttributePlan plan, Plan parent) {
    if (plan.isRef())
      return;

    if (!plan.isNested())
      writer.write("package " + plan.getPackageName() + ";\n");

    writer.write("@" + SuppressWarnings.class.getSimpleName() + "(\"unchecked\")\n");
    writer.write("public ");
    if (plan.isNested())
      writer.write("static ");

    writer.write("class " + plan.getClassSimpleName() + " extends " + plan.getSuperClassNameWithType() + "\n");
    writer.write("{\n");
    writer.write("private static final " + QName.class.getName() + " NAME = new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\");\n");

    // REASON: Attributes that are not defined globally do not need to be resolvable globally.
    if (!plan.isNested()) {
      writer.write("static\n");
      writer.write("{\n");
      writer.write("_$$registerSchemaLocation(NAME.getNamespaceURI(), " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
      writer.write("}\n");
    }

    // ID LOOKUP
    writeIdLookup(writer, plan, parent);

    // ENUMERATIONS CONSTRUCTOR
    getRestrictions(writer, plan, parent);

    // COPY CONSTRUCTOR
    writer.write(plan.getDocumentation());
    if (plan.hasEnumerations())
      writer.write("public " + plan.getClassSimpleName() + "(" + plan.getClassName(parent) + " copy)\n");
    else
      writer.write("public " + plan.getClassSimpleName() + "(" + plan.getCopyClassName(parent) + " copy)\n");
    writer.write("{\n");
    writer.write("super(copy);\n");
    writer.write("}\n");

    // NATIVE CONSTRUCTORS
    getNativeConstructors(writer, plan, parent);

    // DEFAULT CONSTRUCTOR
    if (plan.hasEnumerations())
      writer.write("protected ");
    else {
      // DOCUMENTATION
      writer.write(plan.getDocumentation());
      writer.write("public ");
    }
    writer.write(plan.getClassSimpleName() + "()\n");
    writer.write("{\n");
    writer.write("super();\n");
    writer.write("}\n");

    // INHERITS
    writer.write("protected " + plan.getClassSimpleName() + " inherits()\n");
    writer.write("{\n");
    writer.write("return this;\n");
    writer.write("}\n");

    // OWNER
    appendOwner(writer);

    // GETNAME
    writer.write("protected " + QName.class.getName() + " _$$getName()\n");
    writer.write("{\n");
    writer.write("return NAME;\n");
    writer.write("}\n");

    // PATTERN
    appendPattern(writer, plan.getPatterns());

    // GETVALUE
    writer.write("public " + plan.getNativeItemClassNameInterface() + " getText()\n");
    writer.write("{\n");
    if (plan.isRestriction())
      writer.write("return super.getText();\n");
    else if (!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
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

    // SETVALUE
    if (!plan.hasEnumerations()) {
      writer.write("public void setText(" + plan.getNativeItemClassNameInterface() + " text)\n");
      writer.write("{\n");
      writer.write("super.setText(text);\n");
      writer.write("}\n");
    }

    // CLONE
    writer.write("public " + plan.getCopyClassName(parent) + " clone()\n");
    writer.write("{\n");
    writer.write("return new " + plan.getClassName(parent) + "(this);\n");
    writer.write("}\n");

    // EQUALS
    writer.write("public boolean equals(" + Object.class.getName() + " obj)\n");
    writer.write("{\n");
    // NOTE: This is not checking whether getValue() is equal between this and obj
    // NOTE: because this class does not contain the value field.
    writer.write("return super.equals(obj);\n");
    writer.write("}\n");

    // HASHCODE
    writer.write("public int hashCode()\n");
    writer.write("{\n");
    writer.write("int hashCode = super.hashCode();\n");
    writer.write("hashCode += NAME.hashCode();\n");
//      writer.write("hashCode += getValue() != null ? getValue().hashCode() : -1;\n");
    writer.write("return hashCode;\n");
    writer.write("}\n");

    writer.write("}\n");
  }
}
