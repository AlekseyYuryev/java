package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
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
import org.safris.xml.generator.compiler.runtime.ComplexType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComplexTypeWriter<T extends ComplexTypePlan> extends SimpleTypeWriter<T>
{
	protected void appendDeclaration(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a declaration");
	}

	protected void appendGetMethod(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a get method");
	}

	protected void appendSetMethod(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a set method");
	}

	protected void appendMarshal(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a marshal method");
	}

	protected void appendParse(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a parse method");
	}

	public void appendCopy(StringWriter writer, T plan, Plan parent, String variable)
	{
		throw new CompilerError("complexType cannot have a copy statement");
	}

	protected void appendEquals(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a equals statement");
	}

	protected void appendHashCode(StringWriter writer, T plan, Plan parent)
	{
		throw new CompilerError("complexType cannot have a hashCode statement");
	}

	protected void appendClass(StringWriter writer, T plan, Plan parent)
	{
		writer.write("package " + plan.getPackageName() + ";\n");

		writer.write("public abstract class " + plan.getClassSimpleName() + "<T extends " + ComplexType.class.getName() + "> extends " + plan.getSuperClassNameWithType() + "\n");
		writer.write("{\n");
		writer.write("private static final " + QName.class.getName() + " NAME = new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\");\n");

		writer.write("static\n");
		writer.write("{\n");
		writer.write("_registerType(NAME, " + plan.getClassName(parent) + ".class);\n");
		writer.write("_registerSchemaLocation(\"" + plan.getName().getNamespaceURI() + "\", " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
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
		getIdLookup(writer, plan, parent);

		// MIXED
		if(plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())
			writer.write("private " + String.class.getName() + " text = null;\n");

		for(Object attribute : plan.getAttributes())
			Writer.writeDeclaration(writer, (AttributePlan)attribute, plan);

		for(Object element : plan.getElements())
			Writer.writeDeclaration(writer, (ElementPlan)element, plan);

		// ENUMERATIONS CONSTRUCTOR
		if(plan.hasEnumerations())
			getRestrictions(writer, plan, parent);

		// COPY CONSTRUCTOR
		writer.write(plan.getDocumentation());
		writer.write("public " + plan.getClassSimpleName() + "(" + plan.getClassName(null) + "<T> copy)\n");
		writer.write("{\n");
		writer.write("super(copy);\n");
		if(plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())
			writer.write("this.text = copy.text;\n");
		for(Object attribute : plan.getAttributes())
			Writer.writeCopy(writer, (AttributePlan)attribute, plan, "copy");
		for(Object element : plan.getElements())
			Writer.writeCopy(writer, (ElementPlan)element, plan, "copy");
		writer.write("}\n");

		// MIXED CONSTRUCTOR
		if(plan.getNativeItemClassNameInterface() != null)
		{
			if(!plan.hasEnumerations())
			{
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
		else if(plan.getMixedType())
		{
			writer.write(plan.getDocumentation());
			writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
			writer.write("{\n");
			writer.write("super(text);\n");
			writer.write("}\n");
		}
		else if(plan.getMixed() != null && plan.getMixed())
		{
			writer.write(plan.getDocumentation());
			writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
			writer.write("{\n");
			writer.write("this.text = text;\n");
			writer.write("}\n");
		}

		// DEFAULT CONSTRUCTOR
		if(plan.hasEnumerations())
			writer.write("protected ");
		else
		{
			writer.write(plan.getDocumentation());
			writer.write("public ");
		}
		writer.write(plan.getClassSimpleName() + "()\n");
		writer.write("{\n");
		writer.write("super();\n");
		for(Object attribute : plan.getAttributes())
			writer.write(((AttributePlan)attribute).getFixedInstanceCall(plan));
		writer.write("}\n");

		if(plan.getNativeItemClassNameInterface() != null)
		{
			writer.write("public " + plan.getNativeItemClassNameInterface() + " getTEXT()\n");
			writer.write("{\n");
			if(!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
				writer.write("return (" + plan.getNativeItemClassNameInterface() + ")super.getTEXT();\n");
			else
				writer.write("return super.getTEXT();\n");
			writer.write("}\n");

			if(!plan.hasEnumerations())
			{
				writer.write("public void setTEXT(" + plan.getNativeItemClassNameInterface() + " text)\n");
				writer.write("{\n");
				writer.write("super.setTEXT(text);\n");
				writer.write("}\n");

				if(plan.getNativeItemClassName() == null && XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
				{
					writer.write("public void setTEXT(" + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> text)\n");
					writer.write("{\n");
					writer.write("super.setTEXT(text);\n");
					writer.write("}\n");
				}
			}
			else
			{
				writer.write("public void setTEXT(RESTRICTION restriction)\n");
				writer.write("{\n");
				writer.write("super.setTEXT(restriction.getValue());\n");
				writer.write("}\n");
			}
		}
		else if(plan.getMixed() != null && plan.getMixed())
		{
			writer.write("public " + String.class.getName() + " getTEXT()\n");
			writer.write("{\n");
			writer.write("return text;\n");
			writer.write("}\n");
			writer.write("public void setTEXT(" + String.class.getName() + " text)\n");
			writer.write("{\n");
			writer.write("this.text = text;\n");
			writer.write("}\n");
		}
		else if(plan.getMixedType())
		{
			writer.write("public " + String.class.getName() + " getTEXT()\n");
			writer.write("{\n");
			writer.write("return (" + String.class.getName() + ")super.getTEXT();\n");
			writer.write("}\n");
			writer.write("public void setTEXT(" + String.class.getName() + " text)\n");
			writer.write("{\n");
			writer.write("super.setTEXT(text);\n");
			writer.write("}\n");
		}

		for(Object attribute : plan.getAttributes())
		{
			Writer.writeSetMethod(writer, (AttributePlan)attribute, plan);
			Writer.writeGetMethod(writer, (AttributePlan)attribute, plan);
		}

		for(Object element : plan.getElements())
		{
			Writer.writeSetMethod(writer, (ElementPlan)element, plan);
			Writer.writeGetMethod(writer, (ElementPlan)element, plan);
		}

		// INHERITS
		writer.write("protected abstract " + plan.getBaseNonXSTypeClassName() + " inherits();\n");

		// GETNAME
		writer.write("protected " + QName.class.getName() + " _getName()\n");
		writer.write("{\n");
		writer.write("return _getName(inheritsInstance());\n");
		writer.write("}\n");

		// GETTYPE
		writer.write("protected " + QName.class.getName() + " _getTypeName()\n");
		writer.write("{\n");
		writer.write("return NAME;\n");
		writer.write("}\n");

		// MARSHAL
		writer.write("protected " + Element.class.getName() + " marshal() throws " + MarshalException.class.getName() + ", " + ValidationException.class.getName() + "\n");
		writer.write("{\n");
		writer.write(Element.class.getName() + " root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());\n");
		writer.write(Element.class.getName() + " element = marshal(root, _getName(), _getTypeName(inheritsInstance()));\n");
		writer.write("if(" + Validator.class.getName() + ".getSystemValidator() != null)\n");
		writer.write(Validator.class.getName() + ".getSystemValidator().validateMarshal(element);\n");
		writer.write("return element;\n");
		writer.write("}\n");

		writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
		writer.write("{\n");
		if(plan.getElements() != null || plan.getAttributes() != null || plan.getMixed())
		{
			writer.write(Element.class.getName() + " element = super.marshal(parent, name, typeName);\n");
			if(plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())
			{
				writer.write("if(text != null)\n");
				writer.write("element.appendChild(element.getOwnerDocument().createTextNode(text.toString()));\n");
			}
			for(Object attribute : plan.getAttributes())
				Writer.writeMarshal(writer, (AttributePlan)attribute, plan);
			for(Object element : plan.getElements())
				Writer.writeMarshal(writer, (ElementPlan)element, plan);

			writer.write("return element;\n");
		}
		else
			writer.write("return super.marshal(parent, name, typeName);\n");

		writer.write("}\n");

		// PARSE
		writer.write("protected void parse(" + org.w3c.dom.Element.class.getName() + " element) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
		writer.write("{\n");
		if(plan.getElements() != null || plan.getAttributes() != null || (plan.getNativeItemClassNameInterface() == null && plan.getMixed()))
		{
			if(plan.getAttributes() != null)
			{
				writer.write(NamedNodeMap.class.getName() + " namedNodeMap = element.getAttributes();\n");
				writer.write(Node.class.getName() + " attribute = null;\n");
				writer.write("for(int i = 0; i < namedNodeMap.getLength(); i++)\n");
				writer.write("{\n");
				writer.write("attribute = namedNodeMap.item(i);\n");
				writer.write("if(attribute == null || XMLNS.getLocalPart().equals(attribute.getPrefix()) || XSI_TYPE.getNamespaceURI().equals(attribute.getNamespaceURI()))\n");
				writer.write("{\n");
				writer.write("continue;\n");
				writer.write("}\n");
				AttributePlan any = null;
				for(Object attribute : plan.getAttributes())
				{
					if(attribute instanceof AnyAttributePlan)
						any = (AttributePlan)attribute;
					else
						Writer.writeParse(writer, (AttributePlan)attribute, plan);
				}

				if(any != null)
					Writer.writeParse(writer, any, plan);

				writer.write("}\n");
			}

			if(plan.getElements() != null || (plan.getNativeItemClassNameInterface() == null && plan.getMixed()))
			{
				writer.write(NodeList.class.getName() + " nodeList = element.getChildNodes();\n");
				writer.write(Node.class.getName() + " childNode = null;\n");
				writer.write("for(int i = 0; i < nodeList.getLength(); i++)\n");
				writer.write("{\n");
				writer.write("childNode = nodeList.item(i);\n");
				if(plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())
				{
					writer.write("if(childNode.getNodeType() == " + Node.class.getName() + ".TEXT_NODE)\n");
					writer.write("{\n");
					writer.write("if(childNode.getNodeValue().length() != 0)\n");
					writer.write("{\n");
					writer.write(String.class.getName() + " value = childNode.getNodeValue().trim();\n");
					writer.write("if(text == null && value.length() != 0)\n");
					writer.write("text = new " + String.class.getName() + "(value);\n");
					writer.write("}\n");
					writer.write("}\n");
				}
				else
				{
					writer.write("if(childNode == null || childNode.getNodeType() == " + Node.class.getName() + ".TEXT_NODE)\n");
					writer.write("{\n");
					writer.write("continue;\n");
					writer.write("}\n");
				}

				ElementPlan any = null;
				for(Object element : plan.getElements())
				{
					if(element instanceof AnyPlan)
						any = (ElementPlan)element;
					else
						Writer.writeParse(writer, (ElementPlan)element, plan);
				}

				if(any != null)
					Writer.writeParse(writer, any, plan);

				writer.write("}\n");
			}
		}
		writer.write("super.parse(element);\n");
		writer.write("}\n");

		// CLONE
		writer.write("public " + plan.getClassName(parent) + " clone()\n");
		writer.write("{\n");
		writer.write("return " + plan.getClassName(parent) + ".newInstance(this);\n");
		writer.write("}\n");

		// EQUALS
		writer.write("public boolean equals(" + Object.class.getName() + " obj)\n");
		writer.write("{\n");
		if(plan.getAttributes() != null || plan.getElements() != null || (plan.getNativeItemClassNameInterface() == null && plan.getMixed()))
		{
			writer.write("if(this == obj)\n");
			writer.write("return true;\n");
			writer.write("if(!(obj instanceof " + plan.getClassName(parent) + "))\n");
			writer.write("return _failEquals();\n");
			writer.write(plan.getClassName(parent) + " binding = (" + plan.getClassName(parent) + ")obj;\n");
			for(Object attribute : plan.getAttributes())
				Writer.writeEquals(writer, (AttributePlan)attribute, plan);
			for(Object element : plan.getElements())
				Writer.writeEquals(writer, (ElementPlan)element, plan);
		}
		writer.write("return super.equals(obj);\n");
		writer.write("}\n");

		// HASHCODE
		writer.write("public int hashCode()\n");
		writer.write("{\n");
		writer.write(StringBuffer.class.getName() + " stringBuffer = new " + StringBuffer.class.getName() + "(" + String.class.getName() + ".valueOf(super.hashCode())).append(\"-\");\n");
		for(Object attribute : plan.getAttributes())
			Writer.writeHashCode(writer, (AttributePlan)attribute, plan);
		for(Object element : plan.getElements())
			Writer.writeHashCode(writer, (ElementPlan)element, plan);
		writer.write("return stringBuffer.toString().hashCode();\n");
		writer.write("}\n");

		// ATTRIBUTES
		for(Object attribute : plan.getAttributes())
			Writer.writeClass(writer, (AttributePlan)attribute, plan);

		// ELEMENTS
		for(Object element : plan.getElements())
			Writer.writeClass(writer, (ElementPlan)element, plan);

		writer.write("}\n");
	}
}
