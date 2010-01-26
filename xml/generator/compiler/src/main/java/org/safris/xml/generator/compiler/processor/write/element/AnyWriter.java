/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import java.util.List;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ElementAudit;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AnyWriter extends ElementWriter<AnyPlan> {
    protected void appendDeclaration(StringWriter writer, AnyPlan plan, Plan parent) {
//      if(plan.getMaxOccurs() > 1)
        writer.write("private " + ElementAudit.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">> any = new " + ElementAudit.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>(this, " + plan.getDefaultInstance(parent) + ", null, null, true, " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
//      else
//          writer.write("private " + ElementAudit.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">> any = new " + ElementAudit.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>(" + plan.getDefaultInstance(parent) + ", null, null, true, " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
    }

    protected void appendGetMethod(StringWriter writer, AnyPlan plan, Plan parent) {
//      if(plan.getMaxOccurs() > 1)
        writer.write("public " + List.class.getName() + "<" +  Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">> getAny()\n");
//      else
//          writer.write("public " + Binding.class.getName() + " getANY()\n");

        writer.write("{\n");
        writer.write("return any.getElements();\n");
        writer.write("}\n");
    }

    protected void appendSetMethod(StringWriter writer, AnyPlan plan, Plan parent) {
//      if(plan.getMaxOccurs() > 1)
        writer.write("public void addAny(" +  Binding.class.getName() + " any)\n");
//      else
//          writer.write("public void setANY(" +  Binding.class.getName() + " any)\n");

        writer.write("{\n");
//      if(plan.getMaxOccurs() > 1)
//      {
        writer.write("_$$addElement(this.any, any);\n");
//      }
//      else
//      {
//          writer.write("if(this." + plan.getInstanceName() + ".$value() != null)\n");
//          writer.write("_$$dequeueElement(this." + plan.getInstanceName() + ");\n");
//          writer.write("this.any.$value(any);\n");
//      }

        writer.write("}\n");
    }

    protected void appendMarshal(StringWriter writer, AnyPlan plan, Plan parent) {
//      writer.write("any.marshal(element);\n");
    }

    protected void appendParse(StringWriter writer, AnyPlan plan, Plan parent) {
//      if(plan.getMaxOccurs() > 1)
//      {
        writer.write("if(element.getNodeType() != " + Node.class.getName() + ".ELEMENT_NODE)\n");
        writer.write("return;\n");
        writer.write("_$$addElement(this.any, " + Bindings.class.getName() + ".parse((" + Element.class.getName() + ")element));\n");
//      }
//      else
//          writer.write("this.any.$value(" + Bindings.class.getName() + ".parse((" + Element.class.getName() + ")childNode));\n");
    }

    public void appendCopy(StringWriter writer, AnyPlan plan, Plan parent, String variable) {
        writer.write("this.any = " + variable + ".any;\n");
    }

    protected void appendEquals(StringWriter writer, AnyPlan plan, Plan parent) {
        writer.write("if(any != null ? !any.equals(that.any) : that.any != null)\n");
        writer.write("return _$$failEquals();\n");
    }

    protected void appendHashCode(StringWriter writer, AnyPlan plan, Plan parent) {
        writer.write("hashCode += any != null ? any.hashCode() : -1;\n");
    }

    protected void appendClass(StringWriter writer, AnyPlan plan, Plan parent) {
    }
}
