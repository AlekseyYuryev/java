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
import java.util.List;

import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.AttributeAudit;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.w3c.dom.Element;

public final class AnyAttributeWriter extends Writer<AnyAttributePlan> {
  protected void appendDeclaration(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
    writer.write("private " + AttributeAudit.class.getName() + "<" + List.class.getName() + "<" + Binding.class.getName() + ">> anyAttribute = new " + AttributeAudit.class.getName() + "<" + List.class.getName() + "<" + Binding.class.getName() + ">>(this, null, null, " + Form.QUALIFIED.equals(plan.getFormDefault()) + ", " + Use.REQUIRED.equals(plan.getUse()) + ");\n");
  }

  protected void appendGetMethod(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
    writer.write("public " + List.class.getName() + "<" +  Binding.class.getName() + "> getAny$()\n");
    writer.write("{\n");
    writer.write("return anyAttribute.getAttribute();\n");
    writer.write("}\n");

    writer.write("public " + Binding.class.getName() + " any$(final int index)\n");
    writer.write("{\n");
    writer.write("final " + List.class.getName() + "<" + Binding.class.getName() + "> values = getAny$();\n");
    writer.write("return values != null && -1 < index && index < values.size() ? values.get(index) : null;\n");
    writer.write("}\n");
  }

  protected void appendSetMethod(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
    writer.write("public void addAny$(" + Binding.class.getName() + " anyAttribute)\n");
    writer.write("{\n");
    writer.write("if(this.anyAttribute.getAttribute() == null)\n");
    writer.write("this.anyAttribute.setAttribute(new " + ArrayList.class.getName() + ">());\n");
    writer.write("this.anyAttribute.getAttribute().add(anyAttribute);\n");
    writer.write("}\n");
  }

  protected void appendMarshal(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
    writer.write("anyAttribute.marshal(node);\n");
  }

  protected void appendParse(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
//      writer.write("else\n");
//      writer.write("{\n");
    writer.write("if(this.anyAttribute.getAttribute() == null)\n");
    writer.write("this.anyAttribute.setAttribute(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + ">());\n");
    writer.write("this.anyAttribute.getAttribute().add(" + Binding.class.getName() + ".parseAttr((" + Element.class.getName() + ")attribute.getParentNode(), attribute));\n");
//      writer.write("}\n");
  }

  public void appendCopy(final StringWriter writer, final AnyAttributePlan plan, Plan<?> parent, final String variable) {
    writer.write("this.anyAttribute = " + variable + ".anyAttribute;\n");
  }

  protected void appendEquals(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
    writer.write("if(anyAttribute != null ? !anyAttribute.equals(that.anyAttribute) : that.anyAttribute != null)\n");
    writer.write("return _$$failEquals();\n");
  }

  protected void appendHashCode(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
    writer.write("hashCode += anyAttribute != null ? anyAttribute.hashCode() : -1;\n");
  }

  protected void appendClass(final StringWriter writer, final AnyAttributePlan plan, final Plan<?> parent) {
  }
}