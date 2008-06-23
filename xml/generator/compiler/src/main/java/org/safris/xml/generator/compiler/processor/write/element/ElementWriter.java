/*  Copyright 2008 Safris Technologies Inc.
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
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyAttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.AnyPlan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.ElementAudit;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.w3.x2001.xmlschema.$xs_anySimpleType;
import org.w3.x2001.xmlschema.$xs_boolean;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ElementWriter<T extends ElementPlan> extends ComplexTypeWriter<T>
{
	protected static void writeNilMarshal(StringWriter writer)
	{
		writer.write("if(nil != null && !node.hasAttributeNS(XSI_NIL.getNamespaceURI(), XSI_NIL.getLocalPart()))\n");
		writer.write("{\n");
		writer.write("node.setAttributeNS(XSI_NIL.getNamespaceURI(), XSI_NIL.getPrefix() + \":\" + XSI_NIL.getLocalPart(), " + String.class.getName() + ".valueOf(nil));\n");
		writer.write("if(!parent.getOwnerDocument().getDocumentElement().hasAttributeNS(XMLNS.getNamespaceURI(), XSI_NIL.getPrefix()))\n");
		writer.write("parent.getOwnerDocument().getDocumentElement().setAttributeNS(XMLNS.getNamespaceURI(), XMLNS.getLocalPart() + \":\" + XSI_NIL.getPrefix(), XSI_NIL.getNamespaceURI());\n");
		writer.write("}\n");
	}

	protected void appendDeclaration(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction() || plan.getRepeatedExtension() != null)
			return;

		writer.write("private " + ElementAudit.class.getName() + "<" + plan.getDeclarationGenericWithInconvertible(parent) + "> " + plan.getInstanceName() + " = new " + ElementAudit.class.getName() + "<" + plan.getDeclarationGenericWithInconvertible(parent) + ">(this, " + plan.getDefaultInstance(parent) + ", new " + QName.class.getName() + "(\"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), new " + QName.class.getName() + "(\"" + plan.getTypeName().getNamespaceURI() + "\", \"" + plan.getTypeName().getLocalPart() + "\", \"" + plan.getName().getPrefix() + "\"), " + (!plan.isNested() || Form.QUALIFIED.equals(plan.getFormDefault())) + ", " + plan.isNillable() + ", " + plan.getMinOccurs() + ", " + plan.getMaxOccurs() + ");\n");
	}

	protected void appendGetMethod(StringWriter writer, T plan, Plan parent)
	{
		if(plan.getRepeatedExtension() != null)
			return;

		writer.write("public " + List.class.getName() + "<" + plan.getDeclarationRestrictionGeneric(parent) + "> get" + plan.getClassSimpleName() + "()\n");
		writer.write("{\n");
		if(plan.isRestriction())
			writer.write("return super.get" + plan.getClassSimpleName() + "();\n");
		else
			writer.write("return " + plan.getInstanceName() + ".getElements();\n");
		writer.write("}\n");
	}

	protected void appendSetMethod(StringWriter writer, T plan, Plan parent)
	{
		if(plan.getRepeatedExtension() != null)
			return;

		writer.write("public void add" + plan.getClassSimpleName() + "(" + plan.getDeclarationGeneric(parent) + " " + plan.getInstanceName() + ")\n");
		writer.write("{\n");
		if(plan.isRestriction())
			writer.write("super.add" + plan.getClassSimpleName() + "(" + plan.getInstanceName() + ");\n");
		else
			writer.write("_$$addElement(this." + plan.getInstanceName() + ", " + plan.getInstanceName() + ");\n");
		writer.write("}\n");
	}

	protected void appendMarshal(StringWriter writer, T plan, Plan parent)
	{
	}

	protected void appendParse(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction() || plan.getRepeatedExtension() != null)
			return;

		if(!plan.isNested() || Form.QUALIFIED.equals(plan.getFormDefault()))
		{
			writer.write("else if(\"" + plan.getName().getNamespaceURI() + "\".equals(element.getNamespaceURI()) && \"" + plan.getName().getLocalPart() + "\".equals(element.getLocalName()))\n");
			writer.write("{\n");
			writer.write("return _$$addElement(this." + plan.getInstanceName() + ", (" + plan.getDeclarationGeneric(parent) + ")" + Binding.class.getName() + ".parse(element, " + plan.getClassName(parent) + ".class));\n");
			writer.write("}\n");
			writer.write("else if(" + Binding.class.getName() + "._$$iSsubstitutionGroup(new " + QName.class.getName() + "(element.getNamespaceURI(), element.getLocalName()), \"" + plan.getName().getNamespaceURI() + "\", \"" + plan.getName().getLocalPart() + "\"))\n");
			writer.write("{\n");
			writer.write("return _$$addElement(this." + plan.getInstanceName() + ", (" + plan.getDeclarationGeneric(parent) + ")" + Binding.class.getName() + ".parse(element));\n");
			writer.write("}\n");
		}
		else
		{
			writer.write("else if(\"" + plan.getName().getLocalPart() + "\".equals(element.getLocalName()))\n");
			writer.write("{\n");
			writer.write("return _$$addElement(this." + plan.getInstanceName() + ", (" + plan.getDeclarationGeneric(parent) + ")" + Binding.class.getName() + ".parse(element, " + plan.getClassName(parent) + ".class));\n");
			writer.write("}\n");
		}
	}

	public void appendCopy(StringWriter writer, T plan, Plan parent, String variable)
	{
		if(plan.isRestriction() || plan.getRepeatedExtension() != null)
			return;

		writer.write("this." + plan.getInstanceName() + " = " + variable + "." + plan.getInstanceName() + ";\n");
	}

	protected void appendEquals(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction() || plan.getRepeatedExtension() != null)
			return;

		writer.write("if(" + plan.getInstanceName() + " != null ? !" + plan.getInstanceName() + ".equals(that." + plan.getInstanceName() + ") : that." + plan.getInstanceName() + " != null)\n");
		writer.write("return _$$failEquals();\n");
	}

	protected void appendHashCode(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRestriction() || plan.getRepeatedExtension() != null)
			return;

		writer.write("hashCode += " + plan.getInstanceName() + " != null ? " + plan.getInstanceName() + ".hashCode() : -1;\n");
	}

	protected void appendClass(StringWriter writer, T plan, Plan parent)
	{
		if(plan.isRef() || plan.getRepeatedExtension() != null)
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

		// SUBSTITUTION GROUP
		if(plan.getSubstitutionGroup() != null)
			writer.write("private static final " + QName.class.getName() + " SUBSTITUTION_GROUP =  new " + QName.class.getName() + "(\"" + plan.getSubstitutionGroup().getNamespaceURI() + "\", \"" + plan.getSubstitutionGroup().getLocalPart() + "\");\n");

		if(!plan.isNested())
		{
			writer.write("static\n");
			writer.write("{\n");
			writer.write("_$$registerElement(NAME, " + plan.getClassName(parent) + ".class);\n");
			writer.write("_$$registerSchemaLocation(NAME.getNamespaceURI(), " + plan.getClassName(null) + ".class, \"" + plan.getXsdLocation() + "\");\n");
			writer.write("}\n");
		}

		// ID LOOKUP
		writeIdLookup(writer, plan, parent);

		if(plan.getMixed() != null && plan.getMixed())
			writer.write("private " + String.class.getName() + " text = null;\n");

		if(plan.isNillable())
			writer.write("private " + Boolean.class.getName() + " nil = null;\n");

		for(AttributePlan attribute : plan.getAttributes())
			Writer.writeDeclaration(writer, attribute, plan);

		for(ElementPlan element : plan.getElements())
			Writer.writeDeclaration(writer, element, plan);

		// ENUMERATIONS CONSTRUCTOR
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
				writer.write("public void setText(" + plan.getNativeItemClassNameInterface() + " text)\n");
				writer.write("{\n");
				writer.write("super.setText(text);\n");
				writer.write("}\n");

				if(plan.getNativeItemClassName() == null && XSTypeDirectory.ANYSIMPLETYPE.getNativeBinding().getName().equals(plan.getBaseXSItemTypeName()))
				{
					writer.write("public void setText(" + List.class.getName() + "<" + plan.getNativeItemClassNameInterface() + "> text)\n");
					writer.write("{\n");
					writer.write("super.setText(text);\n");
					writer.write("}\n");
				}

				writer.write("public " + plan.getNativeItemClassNameInterface() + " getText()\n");
				writer.write("{\n");
				if(!Object.class.getName().equals(plan.getNativeItemClassNameInterface()))
					writer.write("return (" + plan.getNativeItemClassNameInterface() + ")super.getText();\n");
				else
					writer.write("return super.getText();\n");
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

					writer.write("public " + String.class.getName() + " getText()\n");
					writer.write("{\n");
					writer.write("return (" + String.class.getName() + ")super.getText();\n");
					writer.write("}\n");

					writer.write("public void setText(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("super.setText(text);\n");
					writer.write("}\n");
				}
				else if(plan.getMixed() != null && plan.getMixed())
				{
					writer.write("public " + plan.getClassSimpleName() + "(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("this.text = text;\n");
					writer.write("}\n");

					writer.write("public " + String.class.getName() + " getText()\n");
					writer.write("{\n");
					writer.write("return text;\n");
					writer.write("}\n");

					writer.write("public void setText(" + String.class.getName() + " text)\n");
					writer.write("{\n");
					writer.write("this.text = text;\n");
					writer.write("}\n");
				}
				else if(plan.hasEnumerations())
				{
					writer.write("public " + String.class.getName() + " getText()\n");
					writer.write("{\n");
					writer.write("return (" + String.class.getName() + ")super.getText();\n");
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

		// OWNER
		appendOwner(writer);

		// SUBSTITUTION GROUP
		if(plan.getSubstitutionGroup() != null)
		{
			writer.write("protected boolean _$$isSubstitutionGroup(" + QName.class.getName() + " name)\n");
			writer.write("{\n");
			writer.write("return name != null && SUBSTITUTION_GROUP.getNamespaceURI().equals(name.getNamespaceURI()) && SUBSTITUTION_GROUP.getLocalPart().equals(name.getLocalPart());\n");
			writer.write("}\n");
		}

		// GETNAME
		writer.write("protected " + QName.class.getName() + " _$$getName()\n");
		writer.write("{\n");
		writer.write("return NAME;\n");
		writer.write("}\n");

		// PATTERN
		appendPattern(writer, plan.getPatterns());

		// ELEMENT ITERATORS
		if(plan.getElements() != null && plan.getElements().size() != 0)
		{
			writer.write("public " + Iterator.class.getName() + "<" + Binding.class.getName() + "> elementIterator()\n");
			writer.write("{\n");
			writer.write("return super.elementIterator();\n");
			writer.write("}\n");

//			writer.write("public " + ListIterator.class.getName() + "<" + Binding.class.getName() + "> elementListIterator()\n");
//			writer.write("{\n");
//			writer.write("return super.elementListIterator();\n");
//			writer.write("}\n");

//			writer.write("public " + ListIterator.class.getName() + "<" + Binding.class.getName() + "> elementListIterator(int index)\n");
//			writer.write("{\n");
//			writer.write("return super.elementListIterator(index);\n");
//			writer.write("}\n");
		}

		// MARSHAL
		if(plan.getElements().size() != 0 || plan.getAttributes().size() != 0)
		{
			if(plan.isNested())
				writer.write("protected ");
			else
				writer.write("public ");

			writer.write(Element.class.getName() + " marshal() throws " + MarshalException.class.getName() + ", " + ValidationException.class.getName() + "\n");
			writer.write("{\n");
			writer.write(Element.class.getName() + " root = createElementNS(_$$getName().getNamespaceURI(), _$$getName().getLocalPart());\n");
			writer.write(Element.class.getName() + " node = marshal(root, _$$getName(), _$$getTypeName(_$$inheritsInstance()));\n");
			writer.write("_$$marshalElements(node);\n");
			writer.write("if(" + Validator.class.getName() + ".getSystemValidator() != null)\n");
			writer.write(Validator.class.getName() + ".getSystemValidator().validateMarshal(node);\n");
			writer.write("return node;\n");
			writer.write("}\n");
			writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
			writer.write("{\n");
			writer.write("final " + Element.class.getName() + " node = super.marshal(parent, name, typeName);\n");
			if(plan.getMixed() != null && plan.getMixed())
			{
				writer.write("if(text != null)\n");
				writer.write("node.appendChild(node.getOwnerDocument().createTextNode(text));\n");
			}

			if(plan.isNillable())
				writeNilMarshal(writer);

			for(AttributePlan attribute : plan.getAttributes())
				Writer.writeMarshal(writer, attribute, plan);

//			if(plan.getElements().size() != 0)
//				writer.write("_$$marshalElements(node);\n");
//			for(ElementPlan element : plan.getElements())
//				Writer.writeMarshal(writer, element, plan);

			writer.write("return node;\n");
			writer.write("}\n");
		}
		else
		{
			// FIXME: What's the point of this??
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
			writer.write(Element.class.getName() + " root = createElementNS(_$$getName().getNamespaceURI(), _$$getName().getLocalPart());\n");
			writer.write(Element.class.getName() + " node = marshal(root, _$$getName(), _$$getTypeName(_$$inheritsInstance()));\n");
			writer.write("_$$marshalElements(node);\n");
			writer.write("if(" + Validator.class.getName() + ".getSystemValidator() != null)\n");
			writer.write(Validator.class.getName() + ".getSystemValidator().validateMarshal(node);\n");
			writer.write("return node;\n");
			writer.write("}\n");

			// FIXME: Check here if we need nillable and remove this entry otherwise.
			writer.write("protected " + Element.class.getName() + " marshal(" + Element.class.getName() + " parent, " + QName.class.getName() + " name, " + QName.class.getName() + " typeName) throws " + MarshalException.class.getName() + "\n");
			writer.write("{\n");
			writer.write("final " + Element.class.getName() + " node = super.marshal(parent, name, typeName);\n");
			if(plan.isNillable())
				writeNilMarshal(writer);

			writer.write("return node;\n");
			writer.write("}\n");
		}

		// PARSE ATTRIBUTE
		if((plan.getAttributes() != null && plan.getAttributes().size() != 0) || plan.isNillable())
		{
			writer.write("protected boolean parseAttribute(" + Attr.class.getName() + " attribute) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
			writer.write("{\n");
			writer.write("if(attribute == null || XMLNS.getLocalPart().equals(attribute.getPrefix()))\n");
			writer.write("{\n");
			writer.write("return true;\n");
			writer.write("}\n");
			AttributePlan any = null;
			for(AttributePlan attribute : plan.getAttributes())
			{
				if(attribute instanceof AnyAttributePlan)
					any = attribute;
				else
					Writer.writeParse(writer, attribute, plan);
			}

			if(plan.isNillable())
			{
				writer.write("else if(XSI_NIL.getNamespaceURI().equals(attribute.getNamespaceURI()) && XSI_NIL.getLocalPart().equals(attribute.getLocalName()))\n");
				writer.write("{\n");
				writer.write("this.nil = " + $xs_boolean.class.getName() + ".parseBoolean(attribute.getNodeValue());\n");
				writer.write("return true;\n");
				writer.write("}\n");
			}

			writer.write("else\n");
			writer.write("{\n");
			writer.write("return super.parseAttribute(attribute);\n");
			writer.write("}\n");
			writer.write("}\n");

			if(any != null)
			{
				writer.write("protected void parseAnyAttribute(" + Attr.class.getName() + " attribute) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
				writer.write("{\n");
				Writer.writeParse(writer, any, plan);
				writer.write("}\n");
			}
		}

		// PARSE ELEMENT
		if((plan.getElements() != null && plan.getElements().size() != 0) || (plan.getMixed() != null && plan.getMixed()))
		{
			writer.write("protected boolean parseElement(" + Element.class.getName() + " element) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
			writer.write("{\n");
			if(plan.getMixed() != null && plan.getMixed())
			{
				writer.write("if(" + Node.class.getName() + ".TEXT_NODE == element.getNodeType())\n");
				writer.write("{\n");
				writer.write("if(element.getNodeValue().length() != 0)\n");
				writer.write("{\n");
				writer.write("if(text == null)\n");
				writer.write("text = element.getNodeValue();\n");
				writer.write("else\n");
				writer.write("text += element.getNodeValue();\n");
				writer.write("}\n");
				writer.write("}\n");
			}
			else
			{
				writer.write("if(false)\n");
				writer.write("{\n");
				writer.write("return false;\n");
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

			writer.write("else\n");
			writer.write("{\n");
			writer.write("return super.parseElement(element);\n");
			writer.write("}\n");
			writer.write("}\n");

			if(any != null)
			{
				writer.write("protected void parseAny(" + Element.class.getName() + " element) throws " + ParseException.class.getName() + ", " + ValidationException.class.getName() + "\n");
				writer.write("{\n");
				Writer.writeParse(writer, any, plan);
				writer.write("}\n");
			}
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
		writer.write("if(this == obj)\n");
		writer.write("return true;\n");
		writer.write("if(!(obj instanceof " + plan.getCopyClassName(parent) + "))\n");
		writer.write("return _$$failEquals();\n");
		if((plan.getAttributes() != null && plan.getAttributes().size() != 0) || (plan.getNativeItemClassNameInterface() == null && plan.getElements() != null && plan.getElements().size() != 0) || (plan.getMixed() != null && plan.getMixed()))
		{
			writer.write("final " + plan.getClassName(parent) + " that = (" + plan.getClassName(parent) + ")obj;\n");
			if(plan.getNativeItemClassNameInterface() == null && plan.getMixed() != null && plan.getMixed())
			{
				writer.write("if(text != null ? !text.equals(that.text) : that.text != null)\n");
				writer.write("return _$$failEquals();\n");
			}
			for(AttributePlan attribute : plan.getAttributes())
				Writer.writeEquals(writer, attribute, plan);
			for(ElementPlan element : plan.getElements())
				Writer.writeEquals(writer, element, plan);
		}

		if(plan.getCopyClassName(parent).equals(plan.getClassName(parent)))
			writer.write("return true;\n");
		else
			writer.write("return super.equals(obj);\n");
		writer.write("}\n");

		// HASHCODE
		writer.write("public int hashCode()\n");
		writer.write("{\n");
		writer.write("int hashCode = super.hashCode();\n");
		writer.write("hashCode += NAME.hashCode();\n");
//		if(plan.getMixed() != null && plan.getMixed())
//			writer.write("hashCode += text != null ? text.hashCode() : -1;\n");
		for(AttributePlan attribute : plan.getAttributes())
			Writer.writeHashCode(writer, attribute, plan);
		for(ElementPlan element : plan.getElements())
			Writer.writeHashCode(writer, element, plan);
		writer.write("return hashCode;\n");
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
