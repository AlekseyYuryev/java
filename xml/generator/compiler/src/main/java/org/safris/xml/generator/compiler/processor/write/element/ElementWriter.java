package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.ElementAudit;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.ValidationException;
import org.safris.xml.generator.compiler.util.Validator;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementWriter<T extends ElementPlan> extends ComplexTypeWriter<T>
{
	protected void appendDeclaration(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		if(plan.getMaxOccurs() > 1)
			writer.write("private " + ElementAudit.class.getName() + "<" + List.class.getName() + "<" + plan.getDeclarationGeneric(parent) + ">> " + plan.getInstanceName() + " = new " + ElementAudit.class.getName() + "<" + List.class.getName() + "<" + plan.getDeclarationGeneric(parent) + ">>(" + plan.getDefaultInstance(parent) + ", new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), new " + QName.class.getName() + "(\"" + plan.getTypeName().getNamespaceURI() + "\", \"" + plan.getTypeName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), " + (!plan.isNested() || Form.QUALIFIED.equals(plan.getFormDefault())) + ", " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
		//			writer.write("private " + List.class.getName() + "<" + plan.getDeclarationGeneric(parent) + "> " + plan.getInstanceName() + " = null;\n");
		else
			writer.write("private " + ElementAudit.class.getName() + "<" + plan.getDeclarationGeneric(parent) + "> " + plan.getInstanceName() + " = new " + ElementAudit.class.getName() + "<" + plan.getDeclarationGeneric(parent) + ">(" + plan.getDefaultInstance(parent) + ", new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), new " + QName.class.getName() + "(\"" + plan.getTypeName().getNamespaceURI() + "\", \"" + plan.getTypeName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), " + (!plan.isNested() || Form.QUALIFIED.equals(plan.getFormDefault())) + ", " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
		//			writer.write("private " + plan.getDeclarationGeneric(parent) + " " + plan.getInstanceName() + " = null;\n");
	}

	protected void appendGetMethod(StringWriter writer, T plan, Plan parent)
	{
		if(plan.getMaxOccurs() > 1)
			writer.write("public " + List.class.getName() + "<" + plan.getDeclarationRestrictionGeneric(parent) + "> get" + plan.getClassSimpleName() + "()\n");
		else
			writer.write("public " + plan.getDeclarationRestrictionGeneric(parent) + " get" + plan.getClassSimpleName() + "()\n");

		writer.write("{\n");
		if(plan.isRestriction())
			writer.write("return super.get" + plan.getClassSimpleName() + "();\n");
		else
			writer.write("return " + plan.getInstanceName() + ".getValue();\n");
		writer.write("}\n");
	}

	protected void appendSetMethod(StringWriter writer, T plan, Plan parent)
	{
		if(plan.getMaxOccurs() > 1)
		{
			writer.write("public void add" + plan.getClassSimpleName() + "(" + plan.getDeclarationGeneric(parent) + " " + plan.getInstanceName() + ")\n");
			writer.write("{\n");
			if(plan.isRestriction())
				writer.write("super.set" + plan.getClassSimpleName() + "(" + plan.getInstanceName() + ");\n");
			else
			{
				writer.write("if(this." + plan.getInstanceName() + ".getValue() == null)\n");
				writer.write("this." + plan.getInstanceName() + ".setValue(new " + ArrayList.class.getName() + "<" + plan.getDeclarationGeneric(parent) + ">(" + (plan.getMaxOccurs() != Integer.MAX_VALUE ? plan.getMaxOccurs() + "" : "") + "));\n");
				writer.write("this." + plan.getInstanceName() + ".getValue().add(" + plan.getInstanceName() + ");\n");
			}
		}
		else
		{
			writer.write("public void set" + plan.getClassSimpleName() + "(" + plan.getDeclarationGeneric(parent) + " " + plan.getInstanceName() + ")\n");
			writer.write("{\n");
			if(plan.isRestriction())
				writer.write("super.set" + plan.getClassSimpleName() + "(" + plan.getInstanceName() + ");\n");
			else
				writer.write("this." + plan.getInstanceName() + ".setValue(" + plan.getInstanceName() + ");\n");
		}

		writer.write("}\n");
	}

	protected void appendMarshal(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		writer.write(plan.getInstanceName() + ".marshal(element);\n");
	}

	protected void appendParse(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		if(!plan.isNested() || Form.QUALIFIED.equals(plan.getFormDefault()))
			writer.write("else if(\"" + plan.getName().getNamespaceURI() + "\".equals(childNode.getNamespaceURI()) && \"" + plan.getName().getLocalPart() + "\".equals(childNode.getLocalName()))\n");
		else
			writer.write("else if(\"" + plan.getName().getLocalPart() + "\".equals(childNode.getLocalName()))\n");
		writer.write("{\n");
		if(plan.getMaxOccurs() > 1)
		{
			writer.write("if(this." + plan.getInstanceName() + ".getValue() == null)\n");
			writer.write("this." + plan.getInstanceName() + ".setValue(new " + ArrayList.class.getName() + "<" + plan.getDeclarationGeneric(parent) + ">(" + (plan.getMaxOccurs() != Integer.MAX_VALUE ? String.valueOf(plan.getMaxOccurs()) : "") + "));\n");
			writer.write("this." + plan.getInstanceName() + ".getValue().add((" + plan.getDeclarationGeneric(parent) + ")" + Binding.class.getName() + ".parse((" + Element.class.getName() + ")childNode, " + plan.getClassName(parent) + ".class, new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\")));\n");
		}
		else
			writer.write("this." + plan.getInstanceName() + ".setValue((" + plan.getDeclarationGeneric(parent) + ")" + Binding.class.getName() + ".parse((" + Element.class.getName() + ")childNode, " + plan.getClassName(parent) + ".class, new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\")));\n");

		writer.write("element.removeChild(childNode);\n");
		writer.write("i--;\n");
		writer.write("}\n");
	}

	public void appendCopy(StringWriter writer, T plan, Plan parent, String variable)
	{
		if(plan.isRestriction())
			return;

		writer.write("this." + plan.getInstanceName() + " = " + variable + "." + plan.getInstanceName() + ";\n");
	}

	protected void appendEquals(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		writer.write("if((this." + plan.getInstanceName() + " == null && binding." + plan.getInstanceName() + " != null) || (this." + plan.getInstanceName() + " != null && !this." + plan.getInstanceName() + ".equals(binding." + plan.getInstanceName() + ")))\n");
		writer.write("return _failEquals();\n");
	}

	protected void appendHashCode(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction())
			return;

		writer.write("stringBuffer.append(" + plan.getInstanceName() + " != null ? " + plan.getInstanceName() + ".hashCode() : 0).append(\"-\");\n");
	}

	protected void appendClass(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRef())
			return;

		if(!plan.isNested())
			writer.write("package " + plan.getPackageName() + ";\n");

		writer.write("public ");
		if(plan.isNested())
			writer.write("static ");
		else if(plan.isAbstract())
			writer.write("abstract ");

		writer.write("class " + plan.getClassSimpleName() + " extends " + plan.getSuperClassNameWithType() + "\n");
		writer.write("{\n");

		writer.write("private static final " + QName.class.getName() + " NAME = new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\");\n");

		if(!plan.isNested())
		{
			writer.write("static\n");
			writer.write("{\n");
			writer.write("_registerElement(NAME, " + plan.getClassName(parent) + ".class);\n");
			writer.write("_registerSchemaLocation(\"" + plan.getName().getNamespaceURI() + "\", " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
			writer.write("}\n");
		}

		// ID LOOKUP
		getIdLookup(writer, plan, parent);

		if(plan.getMixed() != null && plan.getMixed())
			writer.write("private " + String.class.getName() + " text = null;\n");

		for(AttributePlan attribute : plan.getAttributes())
			Writer.writeDeclaration(writer, attribute, plan);

		for(ElementPlan element : plan.getElements())
			Writer.writeDeclaration(writer, element, plan);

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
		if((plan.getMixed() != null && plan.getMixed()) || plan.getAttributes().size() != 0 || plan.getElements().size() != 0)
		{
			writer.write("if(!(copy instanceof " + plan.getClassName(parent) + "))\n");
			writer.write("return;\n");
			writer.write(plan.getClassName(parent) + " binding = (" + plan.getClassName(parent) + ")copy;\n");
			if(plan.getMixed() != null && plan.getMixed())
				writer.write("this.text = binding.text;\n");
			for(AttributePlan attribute : plan.getAttributes())
				Writer.writeCopy(writer, attribute, plan, "binding");
			for(ElementPlan element : plan.getElements())
				Writer.writeCopy(writer, element, plan, "binding");
		}
		writer.write("}\n");

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

		// MIXED CONSTRUCTOR
		if(!plan.isComplexType() || (plan.getMixed() == null && plan.getMixedType()) || (plan.getMixed() != null && plan.getMixed()))
		{
			if(plan.getNativeItemClassNameInterface() != null)
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

				writer.write("public " + plan.getNativeItemClassNameInterface() + " getTEXT()\n");
				writer.write("{\n");
				if(!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
					writer.write("return (" + plan.getNativeItemClassNameInterface() + ")super.getTEXT();\n");
				else
					writer.write("return super.getTEXT();\n");
				writer.write("}\n");
			}
			else
			{
				if(plan.getMixedType())
				{
					writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("super(text);\n");
					writer.write("}\n");

					writer.write("public " + String.class.getName() + " getTEXT()\n");
					writer.write("{\n");
					writer.write("return (" + String.class.getName() + ")super.getTEXT();\n");
					writer.write("}\n");

					writer.write("public void setTEXT(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("super.setTEXT(text);\n");
					writer.write("}\n");
				}
				else if(plan.getMixed() != null && plan.getMixed())
				{
					writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("this.text = text;\n");
					writer.write("}\n");

					writer.write("public " + String.class.getName() + " getTEXT()\n");
					writer.write("{\n");
					writer.write("return text;\n");
					writer.write("}\n");

					writer.write("public void setTEXT(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("this.text = text;\n");
					writer.write("}\n");
				}
				else if(plan.hasEnumerations())
				{
					writer.write("public " + String.class.getName() + " getTEXT()\n");
					writer.write("{\n");
					writer.write("return (" + String.class.getName() + ")super.getTEXT();\n");
					writer.write("}\n");
				}
			}
		}

		// NATIVE CONSTRUCTORS
		if(plan.writeNativeConstructor())
			getNativeConstructors(writer, plan, parent);

		for(AttributePlan attribute : plan.getAttributes())
		{
			Writer.writeSetMethod(writer, attribute, plan);
			Writer.writeGetMethod(writer, attribute, plan);
		}

		for(ElementPlan element : plan.getElements())
		{
			Writer.writeSetMethod(writer, element, plan);
			Writer.writeGetMethod(writer, element, plan);
		}

		// INHERITS
		writer.write("protected " + plan.getClassSimpleName() + " inherits()\n");
		writer.write("{\n");
		writer.write("return this;\n");
		writer.write("}\n");

		// SUBSTITUTION GROUP
		if(plan.getSubstitutionGroup() != null)
		{
			writer.write("protected boolean _isSubstitutionGroup(" + QName.class.getName() + " name)\n");
			writer.write("{\n");
			writer.write("return name != null && \"" + plan.getSubstitutionGroup().getNamespaceURI() + "\".equals(name.getNamespaceURI()) && \"" + plan.getSubstitutionGroup().getLocalPart() + "\".equals(name.getLocalPart());\n");
			writer.write("}\n");
		}

		// GETNAME
		writer.write("protected " + QName.class.getName() + " _getName()\n");
		writer.write("{\n");
		writer.write("return NAME;\n");
		writer.write("}\n");

		// PATTERN
		appendPattern(writer, plan.getPatterns());

		// MARSHAL
		if(plan.getElements() != null || plan.getAttributes() != null)
		{
			if(plan.isNested())
				writer.write("protected ");
			else
				writer.write("public ");

			writer.write(Element.class.getName() + " marshal() throws " + MarshalException.class.getName() + ", " + ValidationException.class.getName() + "\n");
			writer.write("{\n");
			writer.write(Element.class.getName() + " root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());\n");
			writer.write(Element.class.getName() + " element = marshal(root, _getName(), _getTypeName(inheritsInstance()));\n");
			writer.write("if(" + Validator.class.getName() + ".getSystemValidator() != null)\n");
			writer.write(Validator.class.getName() + ".getSystemValidator().validateMarshal(element);\n");
			writer.write("return element;\n");
			writer.write("}\n");
			writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
			writer.write("{\n");
			writer.write(Element.class.getName() + " element = super.marshal(parent, name, typeName);\n");
			if(plan.getMixed() != null && plan.getMixed())
			{
				writer.write("if(text != null)\n");
				writer.write("element.appendChild(element.getOwnerDocument().createTextNode(text));\n");
			}
			for(AttributePlan attribute : plan.getAttributes())
				Writer.writeMarshal(writer, attribute, plan);
			for(ElementPlan element : plan.getElements())
				Writer.writeMarshal(writer, element, plan);

			writer.write("return element;\n");
			writer.write("}\n");
		}
		else
		{
			writer.write("protected " + Attr.class.getName() + " marshalAttr(" + String.class.getName() + " name, " + Element.class.getName() + " parent) throws " + MarshalException.class.getName() + "\n");
			writer.write("{\n");
			writer.write("return super.marshalAttr(name, parent);\n");
			writer.write("}\n");

			if(plan.isNested())
				writer.write("protected ");
			else
				writer.write("public ");

			writer.write(Element.class.getName() + " marshal() throws " + MarshalException.class.getName() + ", " + ValidationException.class.getName() + "\n");
			writer.write("{\n");
			writer.write(Element.class.getName() + " root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());\n");
			writer.write(Element.class.getName() + " element = marshal(root, _getName(), _getTypeName(inheritsInstance()));\n");
			writer.write("if(" + Validator.class.getName() + ".getSystemValidator() != null)\n");
			writer.write(Validator.class.getName() + ".getSystemValidator().validateMarshal(element);\n");
			writer.write("return element;\n");
			writer.write("}\n");
			writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
			writer.write("{\n");
			writer.write("return super.marshal(parent, name, typeName);\n");
			writer.write("}\n");
		}

		// PARSE
		if((plan.getElements() != null && plan.getElements().size() != 0) || (plan.getAttributes() != null && plan.getAttributes().size() != 0))
		{
			writer.write("protected void parse(" + Element.class.getName() + " element) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
			writer.write("{\n");
			if(plan.getAttributes() != null && plan.getAttributes().size() != 0)
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
				for(AttributePlan attribute : plan.getAttributes())
				{
					if(attribute instanceof AnyAttributePlan)
						any = attribute;
					else
						Writer.writeParse(writer, attribute, plan);
				}

				if(any != null)
					Writer.writeParse(writer, any, plan);

				writer.write("}\n");
			}

			if((plan.getElements() != null && plan.getElements().size() != 0) || (plan.getMixed() != null && plan.getMixed()))
			{
				writer.write(NodeList.class.getName() + " nodeList = element.getChildNodes();\n");
				writer.write(Node.class.getName() + " childNode = null;\n");
				writer.write("for(int i = 0; i < nodeList.getLength(); i++)\n");
				writer.write("{\n");
				writer.write("childNode = nodeList.item(i);\n");
				if(plan.getMixed() != null && plan.getMixed())
				{
					writer.write("if(" + Node.class.getName() + ".TEXT_NODE == childNode.getNodeType())\n");
					writer.write("{\n");
					writer.write("if(childNode.getNodeValue().length() != 0)\n");
					writer.write("{\n");
					writer.write("if(text == null)\n");
					writer.write("text = childNode.getNodeValue();\n");
					writer.write("else\n");
					writer.write("text += childNode.getNodeValue();\n");
					writer.write("}\n");
					writer.write("}\n");
				}
				else
				{
					writer.write("if(childNode.getLocalName() == null || childNode.getNodeType() == " + Node.class.getName() + ".TEXT_NODE)\n");
					writer.write("{\n");
					writer.write("continue;\n");
					writer.write("}\n");
				}

				ElementPlan any = null;
				for(ElementPlan element : plan.getElements())
				{
					if(element instanceof AnyPlan)
						any = element;
					else
						Writer.writeParse(writer, element, plan);
				}

				if(any != null)
					Writer.writeParse(writer, any, plan);

				writer.write("}\n");
			}

			writer.write("super.parse(element);\n");
			writer.write("}\n");
		}

		// CLONE
		writer.write("public " + plan.getClassName(parent) + " clone()\n");
		writer.write("{\n");
		String anonymousClass = plan.isAbstract() ? "{}" : "";
		writer.write("return new " + plan.getClassName(parent) + "(this)" + anonymousClass + ";\n");
		writer.write("}\n");

		// EQUALS
		writer.write("public boolean equals(" + Object.class.getName() + " obj)\n");
		writer.write("{\n");
		if(plan.getAttributes() != null || plan.getElements() != null || plan.getMixed())
		{
			writer.write("if(this == obj)\n");
			writer.write("return true;\n");
			if(plan.getElements().size() == 0 && plan.getAttributes().size() == 0 && (plan.getMixed() == null || !plan.getMixed()))
			{
				writer.write("if(!(obj instanceof " + plan.getCopyClassName(parent) + "))\n");
				writer.write("return _failEquals();\n");

				writer.write(plan.getCopyClassName(parent) + " binding = (" + plan.getCopyClassName(parent) + ")obj;\n");
			}
			else
			{
				writer.write("if(!(obj instanceof " + plan.getClassName(parent) + "))\n");
				writer.write("return _failEquals();\n");
				writer.write(plan.getClassName(parent) + " binding = (" + plan.getClassName(parent) + ")obj;\n");
				if(plan.getMixed() != null && plan.getMixed())
				{
					writer.write("if((this.text == null && binding.text != null) || (this.text != null && !this.text.equals(binding.text)))\n");
					writer.write("return _failEquals();\n");
				}
				for(AttributePlan attribute : plan.getAttributes())
					Writer.writeEquals(writer, attribute, plan);
				for(ElementPlan element : plan.getElements())
					Writer.writeEquals(writer, element, plan);
			}
		}

		if(plan.getCopyClassName(parent).equals(plan.getClassName(parent)))
			writer.write("return true;\n");
		else
			writer.write("return super.equals(obj);\n");
		writer.write("}\n");

		// HASHCODE
		writer.write("public int hashCode()\n");
		writer.write("{\n");
		writer.write(StringBuffer.class.getName() + " stringBuffer = new " + StringBuffer.class.getName() + "(" + String.class.getName() + ".valueOf(super.hashCode())).append(\"-\");\n");
		if(plan.getMixed() != null && plan.getMixed())
			writer.write("stringBuffer.append(text != null ? text.hashCode() : 0).append(\"-\");\n");
		for(AttributePlan attribute : plan.getAttributes())
			Writer.writeHashCode(writer, attribute, plan);
		for(ElementPlan element : plan.getElements())
			Writer.writeHashCode(writer, element, plan);
		writer.write("return stringBuffer.toString().hashCode();\n");
		writer.write("}\n");

		// ATTRIBUTES
		for(AttributePlan attribute : plan.getAttributes())
			Writer.writeClass(writer, attribute, plan);

		// ELEMENTS
		for(ElementPlan element : plan.getElements())
			Writer.writeClass(writer, element, plan);

		writer.write("}\n");
	}
}
