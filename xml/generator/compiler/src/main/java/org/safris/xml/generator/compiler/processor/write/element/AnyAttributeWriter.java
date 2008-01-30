package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.Attribute;
import org.safris.xml.generator.compiler.runtime.AttributeAudit;
import org.safris.xml.generator.compiler.runtime.Binding;

public class AnyAttributeWriter extends Writer<AnyAttributePlan>
{
	protected void appendDeclaration(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("private " + AttributeAudit.class.getName() + "<" + List.class.getName() + "<" + Binding.class.getName() + "<" + Attribute.class.getName() + ">>> anyAttribute = new " + AttributeAudit.class.getName() + "<" + List.class.getName() + "<" + Binding.class.getName() + "<" + Attribute.class.getName() + ">>>(null, null, " + Form.QUALIFIED.equals(plan.getFormDefault()) + ", " + Use.REQUIRED.equals(plan.getUse()) + ");\n");
	}

	protected void appendGetMethod(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("public " + List.class.getName() + "<" +  Binding.class.getName() + "<" + Attribute.class.getName() + ">> getANYATTR()\n");
		writer.write("{\n");
		writer.write("return anyAttribute.getValue();\n");
		writer.write("}\n");
	}

	protected void appendSetMethod(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("public void addANYATTR(" + Binding.class.getName() + "<" + Attribute.class.getName() + "> anyAttribute)\n");
		writer.write("{\n");
		writer.write("if(this.anyAttribute == null)\n");
		writer.write("this.anyAttribute.setValue(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<" + Attribute.class.getName() + ">>());\n");
		writer.write("this.anyAttribute.getValue().add(anyAttribute);\n");
		writer.write("}\n");
	}

	protected void appendMarshal(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("anyAttribute.marshal(element);\n");
	}

	protected void appendParse(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("else\n");
		writer.write("{\n");
		writer.write("if(this.anyAttribute == null)\n");
		writer.write("this.anyAttribute.setValue(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<" + Attribute.class.getName() + ">>());\n");
		writer.write("this.anyAttribute.getValue().add(" + Binding.class.getName() + ".parseAttr(element, attribute));\n");
		writer.write("}\n");
	}

	public void appendCopy(StringWriter writer, AnyAttributePlan plan, Plan parent, String variable)
	{
		writer.write("this.anyAttribute = " + variable + ".anyAttribute;\n");
	}

	protected void appendEquals(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("if((this.anyAttribute == null && binding.anyAttribute != null) || (this.anyAttribute != null && !this.anyAttribute.equals(binding.anyAttribute)))\n");
		writer.write("return _failEquals();\n");
	}

	protected void appendHashCode(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("stringBuffer.append(anyAttribute != null ? anyAttribute.hashCode() : 0).append(\"-\");\n");
	}

	protected void appendClass(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
	}
}
