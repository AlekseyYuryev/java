package org.safris.xml.generator.compiler.phase.write.element;

import java.io.StringWriter;
import javax.xml.namespace.QName;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.compiler.phase.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.runtime.AttributeAudit;
import org.safris.xml.generator.compiler.runtime.Binding;

public class AttributeWriter extends SimpleTypeWriter<AttributePlan>
{
	protected void appendDeclaration(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		writer.write("private " + AttributeAudit.class.getName() + "<" + plan.getThisClassNameWithType(parent) + "> " + plan.getInstanceName() + " = new " + AttributeAudit.class.getName() + "<" + plan.getThisClassNameWithType(parent) + ">(" + plan.getDefaultInstance(parent) + ", new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), " + Form.QUALIFIED.equals(plan.getFormDefault()) + ", " + Use.REQUIRED.equals(plan.getUse()) + ");\n");
	}

	protected void appendGetMethod(StringWriter writer, AttributePlan plan, Plan parent)
	{
		writer.write("public " + plan.getDeclarationRestrictionGeneric(parent) + " get" + plan.getClassSimpleName() + "()\n");
		writer.write("{\n");
		if(plan.isRestriction())
			writer.write("return super.get" + plan.getDeclarationRestrictionSimpleName() + "();\n");
		else
			writer.write("return " + plan.getInstanceName() + ".getValue();\n");
		writer.write("}\n");
	}

	protected void appendSetMethod(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRestriction())
		{
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

		writer.write("public void set" + plan.getClassSimpleName() + "(" + plan.getThisClassNameWithType(parent) + " " + plan.getInstanceName() + ")\n");
		writer.write("{\n");
		if(plan.isRestriction())
			writer.write("super.set" + plan.getDeclarationRestrictionSimpleName() + "(" + plan.getInstanceName() + ");\n");
		else
			writer.write("this." + plan.getInstanceName() + ".setValue(" + plan.getInstanceName() + ");\n");
		writer.write("}\n");
	}

	protected void appendMarshal(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRestriction())
		{
			if(!plan.isFixed())
				return;

			if(Form.QUALIFIED.equals(plan.getFormDefault()))
			{
				writer.write("if(!element.hasAttributeNS(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\"))\n");
				writer.write("{\n");
				if(XSTypeDirectory.QNAME.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
					writer.write("element.setAttributeNS(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getPrefix() + "\" + \":" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getLocalPart() + "\");\n");
				else
					writer.write("element.setAttributeNS(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getPrefix() + "\" + \":\" + \"" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getLocalPart() + "\");\n");
				writer.write("}\n");
			}
			else
			{
				writer.write("if(!element.hasAttribute(\"" + plan.getName().getLocalPart() + "\"))\n");
				if(XSTypeDirectory.QNAME.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
				{
					writer.write("{\n");
					writer.write("element.setAttribute(\"" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getPrefix() + "\" + \":" + plan.getDefault().getLocalPart() + "\");\n");
					writer.write("}\n");
				}
				else
					writer.write("element.setAttribute(\"" + plan.getName().getLocalPart() + "\", \"" + plan.getDefault().getLocalPart() + "\");\n");
			}

			return;
		}

		writer.write(plan.getInstanceName() + ".marshal(element);\n");
	}

	protected void appendParse(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		if(Form.QUALIFIED.equals(plan.getFormDefault()))
			writer.write("else if(\"" + plan.getName().getNamespaceURI() + "\".equals(attribute.getNamespaceURI()) && \"" + plan.getName().getLocalPart() + "\".equals(attribute.getLocalName()))\n");
		else
			writer.write("else if(\"" + plan.getName().getLocalPart() + "\".equals(attribute.getLocalName()))\n");

		writer.write("{\n");
		writer.write("this." + plan.getInstanceName() + ".setValue((" + plan.getThisClassNameWithType(parent) + ")" + Binding.class.getName() + "._parseAttr(" + plan.getClassName(parent) + ".class, element, attribute));\n");
		writer.write("}\n");
	}

	public void appendCopy(StringWriter writer, AttributePlan plan, Plan parent, String variable)
	{
		if(plan.isRestriction())
			return;

		writer.write("this." + plan.getInstanceName() + " = " + variable + "." + plan.getInstanceName() + ";\n");
	}

	protected void appendEquals(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		writer.write("if((this." + plan.getInstanceName() + " == null && binding." + plan.getInstanceName() + " != null) || (this." + plan.getInstanceName() + " != null && !this." + plan.getInstanceName() + ".equals(binding." + plan.getInstanceName() + ")))\n");
		writer.write("return _failEquals();\n");
	}

	protected void appendHashCode(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		writer.write("stringBuffer.append(" + plan.getInstanceName() + " != null ? " + plan.getInstanceName() + ".hashCode() : 0).append(\"-\");\n");
	}

	protected void appendClass(StringWriter writer, AttributePlan plan, Plan parent)
	{
		if(plan.isRef())
			return;

		if(!plan.isNested())
			writer.write("package " + plan.getPackageName() + ";\n");

		writer.write("public ");
		if(plan.isNested())
			writer.write("static ");

		writer.write("class " + plan.getClassSimpleName() + " extends " + plan.getSuperClassNameWithType() + "\n");
		writer.write("{\n");
		writer.write("private static final " + QName.class.getName() + " NAME = new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\");\n");

		// REASON: Attributes that are not defined globally do not need to be resolvable globally.
		if(!plan.isNested())
		{
			writer.write("static\n");
			writer.write("{\n");
			writer.write("_registerSchemaLocation(\"" + plan.getName().getNamespaceURI() + "\", " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
			writer.write("}\n");
		}

		// ID LOOKUP
		getIdLookup(writer, plan, parent);

		// ENUMERATIONS CONSTRUCTOR
		if(plan.hasEnumerations())
			getRestrictions(writer, plan, parent);

		// COPY CONSTRUCTOR
		writer.write(plan.getDocumentation());
		if(plan.hasEnumerations())
			writer.write("public " + plan.getClassSimpleName() + "(" + plan.getClassName(parent) + " copy)\n");
		else
			writer.write("public " + plan.getClassSimpleName() + "(" + plan.getCopyClassName(parent) + " copy)\n");
		writer.write("{\n");
		writer.write("super(copy);\n");
		writer.write("}\n");

		// NATIVE CONSTRUCTORS
		getNativeConstructors(writer, plan, parent);

		// DEFAULT CONSTRUCTOR
		if(plan.hasEnumerations())
			writer.write("protected ");
		else
		{
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

		// GETNAME
		writer.write("protected " + QName.class.getName() + " _getName()\n");
		writer.write("{\n");
		writer.write("return NAME;\n");
		writer.write("}\n");

		// PATTERN
		appendPattern(writer, plan.getPatterns());

		// GETVALUE
		writer.write("public " + plan.getNativeItemClassNameInterface() + " getValue()\n");
		writer.write("{\n");
		if(plan.isRestriction())
			writer.write("return super.getValue();\n");
		else if(!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
			writer.write("return (" + plan.getNativeItemClassNameInterface() + ")super.getTEXT();\n");
		else
			writer.write("return super.getTEXT();\n");
		writer.write("}\n");

		// SETVALUE
		if(!plan.hasEnumerations())
		{
			writer.write("public void setValue(" + plan.getNativeItemClassNameInterface() + " value)\n");
			writer.write("{\n");
			if(plan.isRestriction())
				writer.write("super.setValue(value);\n");
			//			else if(plan.isList())
			//				writer.write("this.text = value;\n");
			else
				writer.write("super.setTEXT(value);\n");
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
		writer.write("return super.equals(obj);\n");
		writer.write("}\n");

		// HASHCODE
		writer.write("public int hashCode()\n");
		writer.write("{\n");
		writer.write(StringBuffer.class.getName() + " stringBuffer = new " + StringBuffer.class.getName() + "(" + String.class.getName() + ".valueOf(super.hashCode())).append(\"-\");\n");
		writer.write("return stringBuffer.toString().hashCode();\n");
		writer.write("}\n");

		writer.write("}\n");
	}
}
