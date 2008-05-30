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

package org.safris.xml.generator.compiler.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ListIterator;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.safris.commons.xml.validator.ValidationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public abstract class Binding<T extends BindingType> extends AbstractBinding
{
	protected static DocumentBuilder newDocumentBuilder()
	{
		final DocumentBuilder documentBuilder;
		try
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e)
		{
			throw new BindingError(e);
		}

		return documentBuilder;
	}

	protected static Element createElementNS(String namespaceURI, String localName)
	{
		return newDocumentBuilder().getDOMImplementation().createDocument(namespaceURI, localName, null).getDocumentElement();
	}

	protected static String _$$getPrefix(Element parent, QName name)
	{
		String prefix = name.getPrefix();
		if(prefix == null || prefix.length() == 0)
		{
			if(name.getNamespaceURI() == null || name.getNamespaceURI().length() == 0)
				return null;

			parent = parent.getOwnerDocument().getDocumentElement();
			prefix = parent.lookupPrefix(name.getNamespaceURI());
			if(prefix != null)
				return prefix;

			short i = 0;
			while(parent.lookupNamespaceURI("ns" + ++i) != null);
			prefix = "ns" + i;
		}

		parent.getOwnerDocument().getDocumentElement().setAttributeNS(XMLNS.getNamespaceURI(), XMLNS.getLocalPart() + ":" + prefix, name.getNamespaceURI());
		return prefix;
	}

	protected static Binding _$$parseAttr(Class xmlClass, Element parent, Node attribute)
	{
		final Binding binding;
		try
		{
			final Constructor constructor = xmlClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			binding = (Binding)constructor.newInstance();
			binding._$$decode(parent, attribute.getNodeValue());
		}
		catch(Exception e)
		{
			throw new BindingError(e);
		}

		return binding;
	}

	protected static void _$$decode(Binding binding, Element element, String value) throws ParseException
	{
		binding._$$decode(element, value);
	}

	protected static String _$$encode(Binding binding, Element parent) throws MarshalException
	{
		return binding._$$encode(parent);
	}

	protected static void parse(Binding binding, Element node) throws ParseException, ValidationException
	{
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
			if(attributes.item(i) instanceof Attr && !binding.parseAttribute((Attr)attributes.item(i)))
				binding.parseAnyAttribute((Attr)attributes.item(i));

		final NodeList elements = node.getChildNodes();
		for(int i = 0; i < elements.getLength(); i++)
		{
			final Node child = elements.item(i);
			if(child instanceof Text)
				binding.parseText((Text)child);
			else if(child instanceof Element && !binding.parseElement((Element)elements.item(i)))
				binding.parseAny((Element)elements.item(i));
		}
	}

	protected static QName _$$getName(Binding binding)
	{
		if(binding == null)
			return null;

		return binding._$$getName();
	}

	protected static QName _$$getTypeName(Binding binding)
	{
		return binding._$$getTypeName();
	}

	protected static Binding parse(Element element, Class<? extends Binding> defaultClass, QName name) throws ParseException, ValidationException
	{
		return parseElement(element, defaultClass, name);
	}

	protected static Binding parseAttr(Element element, Node node) throws ParseException
	{
		final String localName = node.getLocalName();
		final String namespaceURI = node.getNamespaceURI();
		final Class classBinding = lookupElement(new QName(namespaceURI, localName));
		if(classBinding == null)
		{
			if(namespaceURI != null)
				throw new ParseException("Unable to find class binding for <" + localName + " xmlns=\"" + namespaceURI + "\">");
			else
				throw new ParseException("Unable to find class binding for <" + localName + "/>");
		}

		return Binding._$$parseAttr(classBinding, element, node);
	}

	protected static Binding parseElement(Element node, Class<? extends Binding> defaultClass, QName name) throws ParseException, ValidationException
	{
		final String localName = node.getLocalName();
		String namespaceURI = node.getNamespaceURI();

		String xsiTypeName = null;
		String xsiPrefix = null;

		final NamedNodeMap rootAttributes = node.getAttributes();
		for(int i = 0; i < rootAttributes.getLength(); i++)
		{
			final Node attribute = rootAttributes.item(i);
			if(XSI_TYPE.getNamespaceURI().equals(attribute.getNamespaceURI()) && XSI_TYPE.getLocalPart().equals(attribute.getLocalName()))
			{
				xsiPrefix = parsePrefix(attribute.getNodeValue());
				xsiTypeName = parseLocalName(attribute.getNodeValue());
				if(xsiPrefix == null)
					xsiPrefix = XMLNS.getLocalPart();
			}
		}

		Class<? extends Binding> classBinding = null;
		try
		{
			if(defaultClass == null)
			{
				classBinding = lookupElement(new QName(namespaceURI, localName));
				if(classBinding == null)
				{
					if(namespaceURI != null)
						throw new ParseException("Unable to find class binding for <" + localName + " xmlns=\"" + namespaceURI + "\">");
					else
						throw new ParseException("Unable to find class binding for <" + localName + "/>");
				}
			}
			else
				classBinding = defaultClass;

			final Constructor constructor = classBinding.getDeclaredConstructor();
			constructor.setAccessible(true);
			Binding binding = (Binding)constructor.newInstance();
			if(xsiTypeName != null)
			{
				namespaceURI = node.getOwnerDocument().getDocumentElement().lookupNamespaceURI(xsiPrefix);
				final Class<? extends Binding> xsiBinding = lookupType(new QName(namespaceURI, xsiTypeName));
				if(xsiBinding == null)
				{
					if(namespaceURI != null)
						throw new ParseException("Unable to find class binding for xsi:type <" + xsiTypeName + " xmlns=\"" + namespaceURI + "\">");
					else
						throw new ParseException("Unable to find class binding for xsi:type <" + xsiTypeName + "/>");
				}

				Method method = null;
				final Method[] methods = xsiBinding.getDeclaredMethods();
				for(int i = 0; i < methods.length; i++)
				{
					if("newInstance".equals(methods[i].getName()))
					{
						method = methods[i];
						break;
					}
				}

				method.setAccessible(true);
				binding = (Binding)method.invoke(null, binding);
			}

			Binding.parse(binding, node);
			return binding;
		}
		catch(ParseException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new ParseException(e);
		}
	}

	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	static
	{
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setValidating(false);
	}

	private final Object elementsLock = new Object();
	private CompositeElementStore elementDirectory = null;
	private Binding inherits;

	protected Binding(Binding binding)
	{
		this();
	}

	protected Binding()
	{
		if((inherits = inherits()) == null)
			return;

		if(this instanceof NotationType)
			return;

		boolean legalInheritance = false;
		final Constructor[] constructors = _$$inheritsInstance().getClass().getDeclaredConstructors();
		for(int i = 0; i < constructors.length; i++)
		{
			if(constructors[i].getParameterTypes().length > 0 && constructors[i].getParameterTypes()[0].isAssignableFrom(getClass()))
			{
				legalInheritance = true;
				break;
			}
		}

		if(!legalInheritance)
			throw new IllegalArgumentException("Invalid inheritance hierarchy.");
	}

	protected final boolean _$$hasElements()
	{
		return elementDirectory != null && elementDirectory.size() != 0;
	}

	protected final void _$$marshalElements(Element parent) throws MarshalException
	{
		if(elementDirectory == null || elementDirectory.size() == 0)
			return;

		synchronized(elementsLock)
		{
			for(int i = 0; i < elementDirectory.size(); i++)
			{
				final Binding element = elementDirectory.getElement(i);
				final ElementAudit elementAudit = elementDirectory.getElementAudits(i);
				elementAudit.marshal(parent, element);
			}
		}
	}

	protected final void _$$replaceElement(Binding original, ElementAudit elementAudit, Binding element)
	{
		if(elementDirectory != null)
			elementDirectory.replace(original, element, elementAudit);
	}

	protected final void _$$addElementBefore(Binding before, ElementAudit elementAudit, Binding element)
	{
		if(elementDirectory != null)
			elementDirectory.addBefore(before, element, elementAudit);
	}

	protected final boolean _$$addElement(ElementAudit elementAudit, Binding element)
	{
		return _$$addElement(elementAudit, element, true);
	}

	protected final boolean _$$addElementNoAudit(ElementAudit elementAudit, Binding element)
	{
		return _$$addElement(elementAudit, element, false);
	}

	private boolean _$$addElement(ElementAudit elementAudit, Binding element, boolean addToAudit)
	{
		if(elementDirectory == null)
		{
			synchronized(elementsLock)
			{
				if(elementDirectory == null)
				{
					elementDirectory = new CompositeElementStore(2);
					if(!elementDirectory.add(element, elementAudit, addToAudit))
						throw new RuntimeBindingException("Elements list should have changed here.");
				}
				else
				{
					if(!elementDirectory.add(element, elementAudit, addToAudit))
						throw new RuntimeBindingException("Elements list should have changed here.");
				}
			}
		}
		else
		{
			if(!elementDirectory.add(element, elementAudit, addToAudit))
				throw new RuntimeBindingException("Elements list should have changed here.");
		}

		return true;
	}

	protected Iterator<Binding> elementIterator()
	{
		return elementDirectory.getElements().iterator();
	}

	protected ListIterator<Binding> elementListIterator()
	{
		return elementDirectory.getElements().listIterator();
	}

	protected ListIterator<Binding> elementListIterator(int index)
	{
		return elementDirectory.getElements().listIterator(index);
	}

	protected final boolean _$$removeElement(Binding element)
	{
		synchronized(elementsLock)
		{
			return elementDirectory.remove(element);
		}
	}

	protected abstract Binding inherits();

	protected final Binding _$$inheritsInstance()
	{
		if(inherits != null)
			return inherits;

		return inherits = inherits();
	}

	protected abstract QName _$$getName();

	protected QName _$$getTypeName()
	{
		return null;
	}

	protected Attr marshalAttr(String name, Element parent) throws MarshalException
	{
		throw new BindingError("This is a template that must be overriden");
	}

	protected Element marshal(Element parent, QName name, QName typeName) throws MarshalException
	{
		boolean substitutionGroup = _$$isSubstitutionGroup(name) || _$$isSubstitutionGroup(_$$getName(inherits()));
		if(substitutionGroup)
			name = _$$getName();

		Element element = parent;
		if(parent.getPrefix() != null)
			element = parent.getOwnerDocument().createElementNS(name.getNamespaceURI(), name.getLocalPart());

		element.setPrefix(_$$getPrefix(parent, name));

		// There is 1 way to exclude an xsi:type attribute:
		// 1. The element being marshaled is a substitutionGroup for the expected element
		// There are 2 ways to require an xsi:type attribute:
		// 1. The element being marshaled is not global, and its typeName comes from its containing complexType
		// 2. The complexType being marshaled is global, and its name comes from the element it inherits from
		if(!substitutionGroup && _$$getTypeName() != null && ((typeName != null && !_$$getTypeName().equals(typeName)) || !_$$getTypeName().equals(_$$getTypeName(inherits()))))
		{
			final String prefix = _$$getPrefix(parent, _$$getTypeName());
			parent.getOwnerDocument().getDocumentElement().setAttributeNS(XMLNS.getNamespaceURI(), XMLNS.getLocalPart() + ":" + XSI_TYPE.getPrefix(), XSI_TYPE.getNamespaceURI());
			element.setAttributeNS(XML.getNamespaceURI(), XSI_TYPE.getPrefix() + ":" + XSI_TYPE.getLocalPart(), prefix + ":" + _$$getTypeName().getLocalPart());
		}

		return element;
	}

	protected boolean _$$isSubstitutionGroup(QName name)
	{
		return false;
	}

	protected Element marshal() throws MarshalException, ValidationException
	{
		final Element root = createElementNS(_$$getName().getNamespaceURI(), _$$getName().getLocalPart());
		return marshal(root, _$$getName(), _$$getTypeName());
	}

	protected boolean parseElement(Element element) throws ParseException, ValidationException
	{
		return false;
	}

	protected boolean parseAttribute(Attr attribute) throws ParseException, ValidationException
	{
		return false;
	}

	protected void parseText(Text text) throws ParseException, ValidationException
	{
	}

	protected void parseAny(Element element) throws ParseException, ValidationException
	{
	}

	protected void parseAnyAttribute(Attr attribute) throws ParseException, ValidationException
	{
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		throw new BindingError("This is a template that must be overridden, otherwise it shouldn't be called");
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		throw new BindingError("This is a template that must be overridden, otherwise it shouldn't be called");
	}

	protected String[] _$$getPattern()
	{
		return null;
	}

	protected final boolean _$$failEquals()
	{
		return false;
	}

	protected Object getText()
	{
		return null;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof Binding))
			return false;

		return true;
	}

	public int hashCode()
	{
		return getClass().getName().hashCode();
	}

	public Binding clone()
	{
		return null;
	}
}
