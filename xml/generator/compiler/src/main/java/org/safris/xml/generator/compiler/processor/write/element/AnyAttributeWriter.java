package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.Attribute;
import org.safris.xml.generator.compiler.runtime.AttributeAudit;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.w3c.dom.Element;

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
		writer.write("return anyAttribute.getAttribute();\n");
		writer.write("}\n");
	}

	protected void appendSetMethod(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("public void addAny$(" + Binding.class.getName() + "<" + Attribute.class.getName() + "> anyAttribute)\n");
		writer.write("{\n");
		writer.write("if(this.anyAttribute.getAttribute() == null)\n");
		writer.write("this.anyAttribute.setAttribute(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<" + Attribute.class.getName() + ">>());\n");
		writer.write("this.anyAttribute.getAttribute().add(anyAttribute);\n");
		writer.write("}\n");
	}

	protected void appendMarshal(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("anyAttribute.marshal(node);\n");
	}

	protected void appendParse(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
//		writer.write("else\n");
//		writer.write("{\n");
		writer.write("if(this.anyAttribute.getAttribute() == null)\n");
		writer.write("this.anyAttribute.setAttribute(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<" + Attribute.class.getName() + ">>());\n");
		writer.write("this.anyAttribute.getAttribute().add(" + Binding.class.getName() + ".parseAttr((" + Element.class.getName() + ")attribute.getParentNode(), attribute));\n");
//		writer.write("}\n");
	}

	public void appendCopy(StringWriter writer, AnyAttributePlan plan, Plan parent, String variable)
	{
		writer.write("this.anyAttribute = " + variable + ".anyAttribute;\n");
	}

	protected void appendEquals(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("if(anyAttribute != null ? !anyAttribute.equals(that.anyAttribute) : that.anyAttribute != null)\n");
		writer.write("return _$$failEquals();\n");
	}

	protected void appendHashCode(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
		writer.write("hashCode += anyAttribute != null ? anyAttribute.hashCode() : -1;\n");
	}

	protected void appendClass(StringWriter writer, AnyAttributePlan plan, Plan parent)
	{
	}
}
