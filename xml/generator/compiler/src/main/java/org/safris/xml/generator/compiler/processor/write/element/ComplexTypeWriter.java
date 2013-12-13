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
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ComplexTypePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ComplexTypeWriter<T extends ComplexTypePlan> extends SimpleTypeWriter<T> {
  protected void appendDeclaration(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a declaration");
  }

  protected void appendGetMethod(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a get method");
  }

  protected void appendSetMethod(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a set method");
  }

  protected void appendMarshal(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a marshal method");
  }

  protected void appendParse(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a parse method");
  }

  public void appendCopy(StringWriter writer, T plan, Plan parent, String variable) {
    throw new CompilerError("complexType cannot have a copy statement");
  }

  protected void appendEquals(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a equals statement");
  }

  protected void appendHashCode(StringWriter writer, T plan, Plan parent) {
    throw new CompilerError("complexType cannot have a hashCode statement");
  }

  protected void appendClass(StringWriter writer, T plan, Plan parent) {
  writer.write("package " + plan.getPackageName() + ";\n");

    writer.write("@" + SuppressWarnings.class.getSimpleName() + "(\"unchecked\")\n");
    writer.write("public abstract class " + plan.getClassSimpleName() + "<T extends " + ComplexType.class.getName() + "> extends " + plan.getSuperClassNameWithType() + "\n");
    writer.write("{\n");
    writer.write("private static final " + QName.class.getName() + " NAME = new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\");\n");

    writer.write("static\n");
    writer.write("{\n");
    writer.write("_$$registerType(NAME, " + plan.getClassName(parent) + ".class);\n");
    writer.write("_$$registerSchemaLocation(NAME.getNamespaceURI(), " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
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

    // ID LOOKUP
    writeIdLookup(writer, plan, parent);

    // MIXED
    if ((!plan.hasSimpleContent() || plan.getNativeItemClassNameInterface() == null) && plan.getMixed() != null && plan.getMixed())
      writer.write("private " + String.class.getName() + " text = null;\n");

    for (final Object attribute : plan.getAttributes())
      Writer.writeDeclaration(writer, (AttributePlan)attribute, plan);

    for (final Object element : plan.getElements())
      Writer.writeDeclaration(writer, (ElementPlan)element, plan);

    // ENUMERATIONS CONSTRUCTOR
    getRestrictions(writer, plan, parent);

    // COPY CONSTRUCTOR
    writer.write(plan.getDocumentation());
    writer.write("public " + plan.getClassSimpleName() + "(" + plan.getClassName(null) + "<T> copy)\n");
    writer.write("{\n");
    writer.write("super(copy);\n");
    if (plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())
      writer.write("this.text = copy.text;\n");
    for (final Object attribute : plan.getAttributes())
      Writer.writeCopy(writer, (AttributePlan)attribute, plan, "copy");
    for (final Object element : plan.getElements())
      Writer.writeCopy(writer, (ElementPlan)element, plan, "copy");
    writer.write("}\n");

    // MIXED CONSTRUCTOR
    if (plan.getNativeItemClassNameInterface() != null) {
      if (!plan.hasEnumerations()) {
        writer.write(plan.getDocumentation());
        writer.write("public ");
      }
      else
        writer.write("protected ");
      writer.write(plan.getClassSimpleName() + "(" + plan.getNativeItemClassNameInterface() + " text)\n");
      writer.write("{\n");
      writer.write("super(text);\n");
      writer.write("}\n");
    }
    else if (plan.getMixedType()) {
      writer.write(plan.getDocumentation());
      writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
      writer.write("{\n");
      writer.write("super(text);\n");
      writer.write("}\n");
    }
    else if (plan.getMixed() != null && plan.getMixed()) {
      writer.write(plan.getDocumentation());
      writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
      writer.write("{\n");
      writer.write("this.text = text;\n");
      writer.write("}\n");
    }

    // DEFAULT CONSTRUCTOR
    if (plan.hasEnumerations())
      writer.write("protected ");
    else {
      writer.write(plan.getDocumentation());
      writer.write("public ");
    }
    writer.write(plan.getClassSimpleName() + "()\n");
    writer.write("{\n");
    writer.write("super();\n");
    for (final Object attribute : plan.getAttributes())
      writer.write(((AttributePlan)attribute).getFixedInstanceCall(plan));
    writer.write("}\n");

    if (plan.hasSimpleContent() && plan.getNativeItemClassNameInterface() != null) {
      if (plan.getNativeItemClassName() == null && XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName())) {
        writer.write("public " + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> getText()\n");
        writer.write("{\n");
        writer.write("return (" + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + ">)super.getText();\n");
        writer.write("}\n");

        writer.write("public " + plan.getNativeItemClassNameInterface() + " getText(final int index)\n");
        writer.write("{\n");
        writer.write("final " + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> values = getText();\n");
        writer.write("return values != null && -1 < index && index < values.size() ? values.get(index) : null;\n");
        writer.write("}\n");

        writer.write("public void setText(" + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> text)\n");
        writer.write("{\n");
        writer.write("super.setText(text);\n");
        writer.write("}\n");
      }
      else {
        writer.write("public " + plan.getNativeItemClassNameInterface() + " getText()\n");
        writer.write("{\n");
        if (!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
          writer.write("return (" + plan.getNativeItemClassNameInterface() + ")super.getText();\n");
        else
          writer.write("return super.getText();\n");
        writer.write("}\n");

        if (plan.hasEnumerations()) {
          writer.write("public void setText(final RESTRICTION restriction)\n");
          writer.write("{\n");
          writer.write("super.setText(restriction.getText());\n");
          writer.write("}\n");
        }
        else {
          writer.write("public void setText(" + plan.getNativeItemClassNameInterface() + " text)\n");
          writer.write("{\n");
          writer.write("super.setText(text);\n");
          writer.write("}\n");
        }
      }
    }
    else if (plan.getMixed() != null && plan.getMixed()) {
      writer.write("public " + String.class.getName() + " getText()\n");
      writer.write("{\n");
      writer.write("return text;\n");
      writer.write("}\n");
      writer.write("public void setText(" + String.class.getName() + " text)\n");
      writer.write("{\n");
      writer.write("this.text = text;\n");
      writer.write("}\n");
    }
    else if (plan.getMixedType()) {
      writer.write("public " + String.class.getName() + " getText()\n");
      writer.write("{\n");
      writer.write("return (" + String.class.getName() + ")super.getText();\n");
      writer.write("}\n");
      writer.write("public void setText(" + String.class.getName() + " text)\n");
      writer.write("{\n");
      writer.write("super.setText(text);\n");
      writer.write("}\n");
    }

    for (final Object attribute : plan.getAttributes()) {
      Writer.writeSetMethod(writer, (AttributePlan)attribute, plan);
      Writer.writeGetMethod(writer, (AttributePlan)attribute, plan);
    }

    for (final Object element : plan.getElements()) {
      Writer.writeSetMethod(writer, (ElementPlan)element, plan);
      Writer.writeGetMethod(writer, (ElementPlan)element, plan);
    }

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

    // ELEMENT ITERATORS
    if (plan.getElements() != null && plan.getElements().size() != 0) {
      writer.write("public " + Iterator.class.getName() + "<" + Binding.class.getName() + "> elementIterator()\n");
      writer.write("{\n");
      writer.write("return super.elementIterator();\n");
      writer.write("}\n");

//          writer.write("public " + ListIterator.class.getName() + "<" + Binding.class.getName() + "> elementListIterator()\n");
//          writer.write("{\n");
//          writer.write("return super.elementListIterator();\n");
//          writer.write("}\n");

//          writer.write("public " + ListIterator.class.getName() + "<" + Binding.class.getName() + "> elementListIterator(int index)\n");
//          writer.write("{\n");
//          writer.write("return super.elementListIterator(index);\n");
//          writer.write("}\n");
    }

    // MARSHAL
    writer.write("protected " + Element.class.getName() + " marshal() throws " + MarshalException.class.getName() + ", " + ValidationException.class.getName() + "\n");
    writer.write("{\n");
    writer.write(Element.class.getName() + " root = createElementNS(_$$getName().getNamespaceURI(), _$$getName().getLocalPart());\n");
    writer.write(Element.class.getName() + " node = marshal(root, _$$getName(), _$$getTypeName(_$$inheritsInstance()));\n");
    if (plan.getElements().size() != 0)
      writer.write("_$$marshalElements(node);\n");
    writer.write("if(" + Validator.class.getName() + ".getSystemValidator() != null)\n");
    writer.write(Validator.class.getName() + ".getSystemValidator().validateMarshal(node);\n");
    writer.write("return node;\n");
    writer.write("}\n");

    writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
    writer.write("{\n");
    if (plan.getElements() != null || plan.getAttributes() != null || plan.getMixed()) {
      writer.write(Element.class.getName() + " node = super.marshal(parent, name, typeName);\n");
      if (plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed()) {
        writer.write("if(text != null)\n");
        writer.write("node.appendChild(node.getOwnerDocument().createTextNode(text.toString()));\n");
      }
      for (final Object attribute : plan.getAttributes())
        Writer.writeMarshal(writer, (AttributePlan)attribute, plan);

      writer.write("return node;\n");
    }
    else
      writer.write("return super.marshal(parent, name, typeName);\n");

    writer.write("}\n");

    // PARSE ATTRIBUTE
    if (plan.getAttributes() != null) {
      writer.write("protected boolean parseAttribute(" + Attr.class.getName() + " attribute) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
      writer.write("{\n");
      writer.write("if(attribute == null || XMLNS.getLocalPart().equals(attribute.getPrefix()))\n");
      writer.write("{\n");
      writer.write("return true;\n");
      writer.write("}\n");
      AttributePlan any = null;
      for (final Object attribute : plan.getAttributes()) {
        if (attribute instanceof AnyAttributePlan)
          any = (AttributePlan)attribute;
        else
          Writer.writeParse(writer, (AttributePlan)attribute, plan);
      }

      writer.write("else\n");
      writer.write("{\n");
      writer.write("return super.parseAttribute(attribute);\n");
      writer.write("}\n");
      writer.write("}\n");

      if (any != null) {
        writer.write("protected void parseAnyAttribute(" + Attr.class.getName() + " attribute) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
        writer.write("{\n");
        Writer.writeParse(writer, any, plan);
        writer.write("}\n");
      }
    }

    // PARSE ELEMENT
    if (plan.getElements() != null || (plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())) {
      writer.write("protected boolean parseElement(" + Element.class.getName() + " element) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
      writer.write("{\n");
      if (plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed()) {
        writer.write("if(element.getNodeType() == " + Node.class.getName() + ".TEXT_NODE)\n");
        writer.write("{\n");
        writer.write("if(element.getNodeValue().length() != 0)\n");
        writer.write("{\n");
        writer.write(String.class.getName() + " value = element.getNodeValue().trim();\n");
        writer.write("if(text == null && value.length() != 0)\n");
        writer.write("text = new " + String.class.getName() + "(value);\n");
        writer.write("}\n");
        writer.write("return true;\n");
        writer.write("}\n");
      }
      else {
        writer.write("if(false)\n");
        writer.write("{\n");
        writer.write("return false;\n");
        writer.write("}\n");
      }

      ElementPlan any = null;
      for (final Object element : plan.getElements()) {
        if (element instanceof AnyPlan)
          any = (ElementPlan)element;
        else
          Writer.writeParse(writer, (ElementPlan)element, plan);
      }

      writer.write("else\n{\n");
      writer.write("return super.parseElement(element);\n");
      writer.write("}\n");
      writer.write("}\n");

      if (any != null) {
        writer.write("protected void parseAny(" + Element.class.getName() + " element) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
        writer.write("{\n");
        Writer.writeParse(writer, any, plan);
        writer.write("}\n");
      }
    }

    // CLONE
    writer.write("public " + plan.getClassName(parent) + " clone()\n");
    writer.write("{\n");
    writer.write("return " + plan.getClassName(parent) + ".newInstance(this);\n");
    writer.write("}\n");

    // EQUALS
    writer.write("public boolean equals(" + Object.class.getName() + " obj)\n");
    writer.write("{\n");
    writer.write("if(this == obj)\n");
    writer.write("return true;\n");
    writer.write("if(!(obj instanceof " + plan.getClassName(parent) + "))\n");
    writer.write("return _$$failEquals();\n");
    if ((plan.getAttributes() != null && plan.getAttributes().size() != 0) || (plan.getNativeItemClassNameInterface() == null && plan.getElements() != null && plan.getElements().size() != 0) || (plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())) {
      writer.write("final " + plan.getClassName(parent) + " that = (" + plan.getClassName(parent) + ")obj;\n");
      if (plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed()) {
        writer.write("if(text != null ? !text.equals(that.text) : that.text != null)\n");
        writer.write("return _$$failEquals();\n");
      }
      for (final Object attribute : plan.getAttributes())
        Writer.writeEquals(writer, (AttributePlan)attribute, plan);
      for (final Object element : plan.getElements())
        Writer.writeEquals(writer, (ElementPlan)element, plan);
    }
    writer.write("return super.equals(obj);\n");
    writer.write("}\n");

    // HASHCODE
    writer.write("public int hashCode()\n");
    writer.write("{\n");
    writer.write("int hashCode = super.hashCode();\n");
    writer.write("hashCode += NAME.hashCode();\n");
    for (final Object attribute : plan.getAttributes())
      Writer.writeHashCode(writer, (AttributePlan)attribute, plan);
    for (final Object element : plan.getElements())
      Writer.writeHashCode(writer, (ElementPlan)element, plan);
    writer.write("return hashCode;\n");
    writer.write("}\n");

    // ATTRIBUTES
    for (final Object attribute : plan.getAttributes())
      Writer.writeClass(writer, (AttributePlan)attribute, plan);

    // ELEMENTS
    for (final Object element : plan.getElements())
      Writer.writeClass(writer, (ElementPlan)element, plan);

    writer.write("}\n");
  }
}
