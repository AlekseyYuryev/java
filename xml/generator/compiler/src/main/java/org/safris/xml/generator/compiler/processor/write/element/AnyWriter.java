package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ElementAudit;
import org.w3c.dom.Element;

public class AnyWriter extends ElementWriter<AnyPlan>
{
	protected void appendDeclaration(StringWriter writer, AnyPlan plan, Plan parent)
	{
		if(plan.getMaxOccurs() > 1)
			writer.write("private " + ElementAudit.class.getName() + "<" + List.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>> any = new " + ElementAudit.class.getName() + "<" + List.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>>(" + plan.getDefaultInstance(parent) + ", null, null, true, " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
		else
			writer.write("private " + ElementAudit.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">> any = new " + ElementAudit.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>(" + plan.getDefaultInstance(parent) + ", null, null, true, " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
	}

	protected void appendGetMethod(StringWriter writer, AnyPlan plan, Plan parent)
	{
		if(plan.getMaxOccurs() > 1)
			writer.write("public " + List.class.getName() + "<" +  Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">> getANY()\n");
		else
			writer.write("public " + Binding.class.getName() + " getANY()\n");

		writer.write("{\n");
		writer.write("return any.getValue();\n");
		writer.write("}\n");
	}

	protected void appendSetMethod(StringWriter writer, AnyPlan plan, Plan parent)
	{
		if(plan.getMaxOccurs() > 1)
			writer.write("public void addANY(" +  Binding.class.getName() + " any)\n");
		else
			writer.write("public void setANY(" +  Binding.class.getName() + " any)\n");

		writer.write("{\n");
		if(plan.getMaxOccurs() > 1)
		{
			writer.write("if(this.any.getValue() == null)\n");
			writer.write("this.any.setValue(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>(" + (plan.getMaxOccurs() != Integer.MAX_VALUE ? String.valueOf(plan.getMaxOccurs()) : "") + "));\n");
			writer.write("this.any.getValue().add(any);\n");
		}
		else
			writer.write("this.any.setValue(any);\n");

		writer.write("}\n");
	}

	protected void appendMarshal(StringWriter writer, AnyPlan plan, Plan parent)
	{
		writer.write("any.marshal(element);\n");
	}

	protected void appendParse(StringWriter writer, AnyPlan plan, Plan parent)
	{
		writer.write("else\n");
		writer.write("{\n");
		if(plan.getMaxOccurs() > 1)
		{
			writer.write("if(this.any.getValue() == null)\n");
			writer.write("this.any.setValue(new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<? extends " + BindingType.class.getName() + ">>(" + (plan.getMaxOccurs() != Integer.MAX_VALUE ? String.valueOf(plan.getMaxOccurs()) : "") + "));\n");
			writer.write("this.any.getValue().add(" + Bindings.class.getName() + ".parse((" + Element.class.getName() + ")childNode));\n");
		}
		else
			writer.write("this.any.setValue(" + Bindings.class.getName() + ".parse((" + Element.class.getName() + ")childNode));\n");

		writer.write("element.removeChild(childNode);\n");
		writer.write("i--;\n");
		writer.write("}\n");
	}

	public void appendCopy(StringWriter writer, AnyPlan plan, Plan parent, String variable)
	{
		writer.write("this.any = " + variable + ".any;\n");
	}

	protected void appendEquals(StringWriter writer, AnyPlan plan, Plan parent)
	{
		writer.write("if((this.any == null && binding.any != null) || (this.any != null && !this.any.equals(binding.any)))\n");
		writer.write("return _failEquals();\n");
	}

	protected void appendHashCode(StringWriter writer, AnyPlan plan, Plan parent)
	{
		writer.write("stringBuffer.append(any != null ? any.hashCode() : 0).append(\"-\");\n");
	}

	protected void appendClass(StringWriter writer, AnyPlan plan, Plan parent)
	{
	}
}
