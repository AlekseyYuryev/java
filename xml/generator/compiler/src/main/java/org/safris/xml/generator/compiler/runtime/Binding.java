package org.safris.xml.generator.compiler.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class Binding<T extends BindingType> extends AbstractBinding
{
	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private Binding inherits;

	static
	{
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setValidating(false);
	}

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

	protected static String _getPrefix(Element parent, QName name)
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

	protected static Binding _parseAttr(Class xmlClass, Element parent, Node node)
	{
		final Binding binding;
		try
		{
			final Constructor constructor = xmlClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			binding = (Binding)constructor.newInstance();
			binding._decode(parent, node.getNodeValue());
		}
		catch(Exception e)
		{
			throw new BindingError(e);
		}

		return binding;
	}

	protected final boolean _failEquals()
	{
		return false;
	}

	protected static void _decode(Binding binding, Element element, String value) throws ParseException
	{
		binding._decode(element, value);
	}

	protected static String _encode(Binding binding, Element parent) throws MarshalException
	{
		return binding._encode(parent);
	}

	protected static void parse(Binding binding, Element element) throws ParseException, ValidationException
	{
		binding.parse(element);
	}

	protected static QName _getName(Binding binding)
	{
		if(binding == null)
			return null;

		return binding._getName();
	}

	protected static QName _getTypeName(Binding binding)
	{
		return binding._getTypeName();
	}

	protected static Binding inherits(Binding binding)
	{
		return binding.inherits();
	}

	protected static Binding parse(Element element, Class<? extends Binding> defaultClass, QName name) throws ParseException, ValidationException
	{
		return parseElement((Element)element.cloneNode(true), defaultClass, name);
	}

	protected static Binding parseAttr(Element element, Node node) throws ParseException
	{
		final String nodeLocalName = node.getLocalName();
		final String namespaceURI = node.getNamespaceURI();
		final Class classBinding = lookupElement(new QName(namespaceURI, nodeLocalName));
		if(classBinding == null)
			throw new ParseException("Unable to find class binding for {" + namespaceURI + "}:" + nodeLocalName);

		return Binding._parseAttr(classBinding, element, node);
	}

	protected static Binding parseElement(Element element, Class<? extends Binding> defaultClass, QName name) throws ParseException, ValidationException
	{
		final String localName = element.getLocalName();
		String namespaceURI = element.getNamespaceURI();

		String xsiTypeName = null;
		String xsiPrefix = null;

		NamedNodeMap rootAttributes = element.getAttributes();
		for(int i = 0; i < rootAttributes.getLength(); i++)
		{
			Node attribute = rootAttributes.item(i);
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
					throw new ParseException("Unable to find class binding for {" + namespaceURI + "}:" + localName);
			}
			else
				classBinding = defaultClass;

			final Constructor constructor = classBinding.getDeclaredConstructor();
			constructor.setAccessible(true);
			Binding binding = (Binding)constructor.newInstance();
			if(xsiTypeName != null)
			{
				namespaceURI = element.getOwnerDocument().getDocumentElement().lookupNamespaceURI(xsiPrefix);
				final Class<? extends Binding> xsiBinding = lookupType(new QName(namespaceURI, xsiTypeName));
				if(xsiBinding == null)
					throw new ParseException("Unable to find class binding for xsi:type={" + namespaceURI + "}:" + xsiTypeName);

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

			binding.parse(element);
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

	protected Binding(Binding binding)
	{
		this();
	}

	protected Binding()
	{
		if((inherits = inherits()) == null)
			return;

		boolean legalInheritance = false;
		final Constructor[] constructors = inheritsInstance().getClass().getDeclaredConstructors();
		for(int i = 0; i < constructors.length; i++)
		{
			if(constructors[i].getParameterTypes().length > 0 && constructors[i].getParameterTypes()[0].isAssignableFrom(getClass()))
			{
				legalInheritance = true;
				break;
			}
		}

		if(!legalInheritance)
			throw new IllegalArgumentException("Inheritance structure is not valid.");
	}

	protected abstract Binding inherits();

	protected final Binding inheritsInstance()
	{
		if(inherits != null)
			return inherits;

		return inherits = inherits();
	}

	protected abstract QName _getName();

	protected QName _getTypeName()
	{
		return null;
	}

	protected Attr marshalAttr(String name, Element parent) throws MarshalException
	{
		throw new BindingError("This is a template that must be overriden");
	}

	protected Element marshal(Element parent, QName name, QName typeName) throws MarshalException
	{
		boolean substitutionGroup = _isSubstitutionGroup(name) || _isSubstitutionGroup(_getName(inherits()));
		if(substitutionGroup)
			name = _getName();

		Element element = parent;
		if(parent.getPrefix() != null)
			element = parent.getOwnerDocument().createElementNS(name.getNamespaceURI(), name.getLocalPart());

		element.setPrefix(_getPrefix(parent, name));

		// There is 1 way to exclude an xsi:type attribute:
		// 1. The element being marshaled is a substitutionGroup for the expected element
		// There are 2 ways to require an xsi:type attribute:
		// 1. The element being marshaled is not global, and its typeName comes from its containing complexType
		// 2. The complexType being marshaled is global, and its name comes from the element it inherits from
		if(!substitutionGroup && _getTypeName() != null && ((typeName != null && !_getTypeName().equals(typeName)) || !_getTypeName().equals(_getTypeName(inherits()))))
		{
			final String prefix = _getPrefix(parent, _getTypeName());
			parent.getOwnerDocument().getDocumentElement().setAttributeNS(XMLNS.getNamespaceURI(), XMLNS.getLocalPart() + ":" + XSI_TYPE.getPrefix(), XSI_TYPE.getNamespaceURI());
			element.setAttributeNS(XML.getNamespaceURI(), XSI_TYPE.getPrefix() + ":" + XSI_TYPE.getLocalPart(), prefix + ":" + _getTypeName().getLocalPart());
		}

		return element;
	}

	protected boolean _isSubstitutionGroup(QName name)
	{
		return false;
	}

	protected Element marshal() throws MarshalException, ValidationException
	{
		org.w3c.dom.Element root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());
		return marshal(root, _getName(), _getTypeName());
	}

	protected void parse(Element element) throws ParseException, ValidationException
	{
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		throw new BindingError("This is a template that must be overridden");
	}

	protected String _encode(Element parent) throws MarshalException
	{
		throw new BindingError("This is a template that must be overridden");
	}

	protected String[] _getPattern()
	{
		return null;
	}

	protected Object getTEXT()
	{
		return null;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(obj instanceof Binding)
			return true;

		return false;
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
