package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.NotationPlan;
import org.safris.xml.generator.compiler.runtime.NotationType;

public class NotationWriter extends Writer<NotationPlan>
{
	protected void appendDeclaration(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a declaration");
	}

	protected void appendGetMethod(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a get method");
	}

	protected void appendSetMethod(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a set method");
	}

	protected void appendMarshal(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a marshal method");
	}

	protected void appendParse(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a parse method");
	}

	public void appendCopy(StringWriter writer, NotationPlan plan, Plan parent, String variable)
	{
		throw new CompilerError("notation cannot have a copy statement");
	}

	protected void appendEquals(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a equals statement");
	}

	protected void appendHashCode(StringWriter writer, NotationPlan plan, Plan parent)
	{
		throw new CompilerError("notation cannot have a hashCode statement");
	}

	protected void appendClass(StringWriter writer, NotationPlan plan, Plan parent)
	{
		writer.write("package " + plan.getPackageName() + ";\n");

		// DOCUMENTATION
		writer.write(plan.getDocumentation());

		writer.write("public final class " + plan.getClassSimpleName() + " extends " + NotationType.class.getName() + "\n");
		writer.write("{\n");

		writer.write("static\n");
		writer.write("{\n");
		writer.write("_registerNotation(new " + plan.getClassSimpleName() + "());\n");
		writer.write("_registerSchemaLocation(\"" + plan.getName().getNamespaceURI() + "\", " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
		writer.write("}\n");

		writer.write("private final " + String.class.getName() + " _name = \"" + plan.getName().getLocalPart() + "\";\n");
		writer.write("private final " + String.class.getName() + " _public = \"" + plan.getPublic() + "\";\n");
		writer.write("private final " + String.class.getName() + " _system = \"" + plan.getSystem() + "\";\n");

		writer.write("protected " + plan.getClassSimpleName() + "()\n");
		writer.write("{\n");
		writer.write("super();\n");
		writer.write("}\n");

		// NAME
		writer.write("public " +  String.class.getName() + " getName()\n");
		writer.write("{\n");
		writer.write("return _name;\n");
		writer.write("}\n");

		// PUBLIC
		writer.write("public " +  String.class.getName() + " getPublic()\n");
		writer.write("{\n");
		writer.write("return _public;\n");
		writer.write("}\n");

		// SYSTEM
		writer.write("public " +  String.class.getName() + " getSystem()\n");
		writer.write("{\n");
		writer.write("return _system;\n");
		writer.write("}\n");

		// CLONE
		writer.write("public " + plan.getClassName(null) + " clone()\n");
		writer.write("{\n");
		writer.write("return this;\n");
		writer.write("}\n");

		// EQUALS
		writer.write("public boolean equals(" + Object.class.getName() + " obj)\n");
		writer.write("{\n");
		writer.write("if(this == obj)\n");
		writer.write("return true;\n");
		writer.write("if(!(obj instanceof " + plan.getClassSimpleName() + "))\n");
		writer.write("return _failEquals();\n");
		writer.write(plan.getClassSimpleName() + " equals = (" + plan.getClassSimpleName() + ")obj;\n");
		writer.write("return ((_name == null && equals._name == null) || (_name != null && _name.equals(equals._name))) && ((_public == null && equals._public == null) || (_public != null && _public.equals(equals._public))) && ((_system == null && equals._system == null) || (_system != null && _system.equals(equals._system)));\n");
		writer.write("}\n");

		// HASHCODE
		writer.write("public int hashCode()\n");
		writer.write("{\n");
		writer.write(StringBuffer.class.getName() + " stringBuffer = new " + StringBuffer.class.getName() + "(" + String.class.getName() + ".valueOf(super.hashCode())).append(\"-\");\n");
		writer.write("stringBuffer.append(_name != null ? _name.hashCode() : 0).append(\"-\");\n");
		writer.write("stringBuffer.append(_public != null ? _public.hashCode() : 0).append(\"-\");\n");
		writer.write("stringBuffer.append(_system != null ? _system.hashCode() : 0).append(\"-\");\n");
		writer.write("return stringBuffer.toString().hashCode();\n");
		writer.write("}\n");

		writer.write("}\n");
	}
}
