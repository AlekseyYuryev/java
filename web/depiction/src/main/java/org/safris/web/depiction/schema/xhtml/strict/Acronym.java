package org.safris.web.depiction.schema.xhtml.strict;

/**
 * acronym
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public class Acronym extends org.safris.web.depiction.schema.xhtml.strict.IInline<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "acronym");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Acronym.class.getName());
	}

	private java.lang.String text = null;
	private IdAttr _idAttr = null;
	private org.safris.xml.bind.runtime.lang.CloneableList<ClassAttr,org.safris.xml.bind.runtime.lang.Attribute> _classAttr = null;
	private StyleAttr _styleAttr = null;
	private TitleAttr _titleAttr = null;
	private LangAttr _langAttr = null;
	private org.safris.web.depiction.schema.xml.namespace.XmlLangAttr _xmlLangAttr = null;
	private DirAttr _dirAttr = null;
	private OnclickAttr _onclickAttr = null;
	private OndblclickAttr _ondblclickAttr = null;
	private OnmousedownAttr _onmousedownAttr = null;
	private OnmouseupAttr _onmouseupAttr = null;
	private OnmouseoverAttr _onmouseoverAttr = null;
	private OnmousemoveAttr _onmousemoveAttr = null;
	private OnmouseoutAttr _onmouseoutAttr = null;
	private OnkeypressAttr _onkeypressAttr = null;
	private OnkeydownAttr _onkeydownAttr = null;
	private OnkeyupAttr _onkeyupAttr = null;

	public Acronym(java.lang.String text)
	{
		super(text);
	}

	public Acronym(org.safris.web.depiction.schema.xhtml.strict.IInline<org.safris.xml.bind.runtime.lang.ComplexType> binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Acronym)
		{
			org.safris.web.depiction.schema.xhtml.strict.Acronym element = (org.safris.web.depiction.schema.xhtml.strict.Acronym)binding;
			this.text = element.text;
			this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
			this._classAttr = element._classAttr != null ? element._classAttr.clone() : null;
			this._styleAttr = element._styleAttr != null ? element._styleAttr.clone() : null;
			this._titleAttr = element._titleAttr != null ? element._titleAttr.clone() : null;
			this._langAttr = element._langAttr != null ? element._langAttr.clone() : null;
			this._xmlLangAttr = element._xmlLangAttr != null ? element._xmlLangAttr.clone() : null;
			this._dirAttr = element._dirAttr != null ? element._dirAttr.clone() : null;
			this._onclickAttr = element._onclickAttr != null ? element._onclickAttr.clone() : null;
			this._ondblclickAttr = element._ondblclickAttr != null ? element._ondblclickAttr.clone() : null;
			this._onmousedownAttr = element._onmousedownAttr != null ? element._onmousedownAttr.clone() : null;
			this._onmouseupAttr = element._onmouseupAttr != null ? element._onmouseupAttr.clone() : null;
			this._onmouseoverAttr = element._onmouseoverAttr != null ? element._onmouseoverAttr.clone() : null;
			this._onmousemoveAttr = element._onmousemoveAttr != null ? element._onmousemoveAttr.clone() : null;
			this._onmouseoutAttr = element._onmouseoutAttr != null ? element._onmouseoutAttr.clone() : null;
			this._onkeypressAttr = element._onkeypressAttr != null ? element._onkeypressAttr.clone() : null;
			this._onkeydownAttr = element._onkeydownAttr != null ? element._onkeydownAttr.clone() : null;
			this._onkeyupAttr = element._onkeyupAttr != null ? element._onkeyupAttr.clone() : null;
		}
	}

	public Acronym()
	{
		super();
	}

	public java.lang.String getTEXT()
	{
		return text;
	}

	public void setTEXT(java.lang.String text)
	{
		this.text = text;
	}

	public void setIdAttr(IdAttr _idAttr)
	{
		this._idAttr = _idAttr;
	}

	public IdAttr getIdAttr()
	{
		return _idAttr;
	}

	public void addClassAttr(ClassAttr _classAttr)
	{
		if(this._classAttr == null)
			this._classAttr = new org.safris.xml.bind.runtime.lang.CloneableList<ClassAttr,org.safris.xml.bind.runtime.lang.Attribute>();

		this._classAttr.add(_classAttr);
	}

	public org.safris.xml.bind.runtime.lang.CloneableList<ClassAttr,org.safris.xml.bind.runtime.lang.Attribute> getClassAttr()
	{
		return _classAttr;
	}

	public void setStyleAttr(StyleAttr _styleAttr)
	{
		this._styleAttr = _styleAttr;
	}

	public StyleAttr getStyleAttr()
	{
		return _styleAttr;
	}

	public void setTitleAttr(TitleAttr _titleAttr)
	{
		this._titleAttr = _titleAttr;
	}

	public TitleAttr getTitleAttr()
	{
		return _titleAttr;
	}

	public void setLangAttr(LangAttr _langAttr)
	{
		this._langAttr = _langAttr;
	}

	public LangAttr getLangAttr()
	{
		return _langAttr;
	}

	public void setXmlLangAttr(org.safris.web.depiction.schema.xml.namespace.XmlLangAttr _xmlLangAttr)
	{
		this._xmlLangAttr = _xmlLangAttr;
	}

	public org.safris.web.depiction.schema.xml.namespace.XmlLangAttr getXmlLangAttr()
	{
		return _xmlLangAttr;
	}

	public void setDirAttr(DirAttr _dirAttr)
	{
		this._dirAttr = _dirAttr;
	}

	public DirAttr getDirAttr()
	{
		return _dirAttr;
	}

	public void setOnclickAttr(OnclickAttr _onclickAttr)
	{
		this._onclickAttr = _onclickAttr;
	}

	public OnclickAttr getOnclickAttr()
	{
		return _onclickAttr;
	}

	public void setOndblclickAttr(OndblclickAttr _ondblclickAttr)
	{
		this._ondblclickAttr = _ondblclickAttr;
	}

	public OndblclickAttr getOndblclickAttr()
	{
		return _ondblclickAttr;
	}

	public void setOnmousedownAttr(OnmousedownAttr _onmousedownAttr)
	{
		this._onmousedownAttr = _onmousedownAttr;
	}

	public OnmousedownAttr getOnmousedownAttr()
	{
		return _onmousedownAttr;
	}

	public void setOnmouseupAttr(OnmouseupAttr _onmouseupAttr)
	{
		this._onmouseupAttr = _onmouseupAttr;
	}

	public OnmouseupAttr getOnmouseupAttr()
	{
		return _onmouseupAttr;
	}

	public void setOnmouseoverAttr(OnmouseoverAttr _onmouseoverAttr)
	{
		this._onmouseoverAttr = _onmouseoverAttr;
	}

	public OnmouseoverAttr getOnmouseoverAttr()
	{
		return _onmouseoverAttr;
	}

	public void setOnmousemoveAttr(OnmousemoveAttr _onmousemoveAttr)
	{
		this._onmousemoveAttr = _onmousemoveAttr;
	}

	public OnmousemoveAttr getOnmousemoveAttr()
	{
		return _onmousemoveAttr;
	}

	public void setOnmouseoutAttr(OnmouseoutAttr _onmouseoutAttr)
	{
		this._onmouseoutAttr = _onmouseoutAttr;
	}

	public OnmouseoutAttr getOnmouseoutAttr()
	{
		return _onmouseoutAttr;
	}

	public void setOnkeypressAttr(OnkeypressAttr _onkeypressAttr)
	{
		this._onkeypressAttr = _onkeypressAttr;
	}

	public OnkeypressAttr getOnkeypressAttr()
	{
		return _onkeypressAttr;
	}

	public void setOnkeydownAttr(OnkeydownAttr _onkeydownAttr)
	{
		this._onkeydownAttr = _onkeydownAttr;
	}

	public OnkeydownAttr getOnkeydownAttr()
	{
		return _onkeydownAttr;
	}

	public void setOnkeyupAttr(OnkeyupAttr _onkeyupAttr)
	{
		this._onkeyupAttr = _onkeyupAttr;
	}

	public OnkeyupAttr getOnkeyupAttr()
	{
		return _onkeyupAttr;
	}

	protected javax.xml.namespace.QName _getName()
	{
		return NAME;
	}

	public org.w3c.dom.Element marshal() throws org.safris.xml.bind.runtime.lang.MarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.Element root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());
		org.w3c.dom.Element element = marshal(root, _getName(), _getTypeName());
		if(org.safris.xml.bind.runtime.util.Validator.getSystemValidator() != null)
			org.safris.xml.bind.runtime.util.Validator.getSystemValidator().validateMarshal(element);

		return element;
	}

	protected org.w3c.dom.Element marshal(org.w3c.dom.Element parent, javax.xml.namespace.QName name, javax.xml.namespace.QName typeName) throws org.safris.xml.bind.runtime.lang.MarshalException
	{
		org.w3c.dom.Element element = super.marshal(parent, name, typeName);
		if(text != null)
			element.appendChild(element.getOwnerDocument().createTextNode(text));

		if(_idAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_idAttr, "id", element));
		}

		if(_classAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_classAttr, "class", element));
		}

		if(_styleAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_styleAttr, "style", element));
		}

		if(_titleAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_titleAttr, "title", element));
		}

		if(_langAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_langAttr, "lang", element));
		}

		if(_xmlLangAttr != null)
		{
			java.lang.String prefix = _getPrefix(element, org.safris.xml.bind.runtime.lang.Binding._getName(_xmlLangAttr).getNamespaceURI());
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_xmlLangAttr, prefix + ":" + org.safris.xml.bind.runtime.lang.Binding._getName(_xmlLangAttr).getLocalPart(), element));
		}

		if(_dirAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_dirAttr, "dir", element));
		}

		if(_onclickAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onclickAttr, "onclick", element));
		}

		if(_ondblclickAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_ondblclickAttr, "ondblclick", element));
		}

		if(_onmousedownAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onmousedownAttr, "onmousedown", element));
		}

		if(_onmouseupAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onmouseupAttr, "onmouseup", element));
		}

		if(_onmouseoverAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onmouseoverAttr, "onmouseover", element));
		}

		if(_onmousemoveAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onmousemoveAttr, "onmousemove", element));
		}

		if(_onmouseoutAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onmouseoutAttr, "onmouseout", element));
		}

		if(_onkeypressAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onkeypressAttr, "onkeypress", element));
		}

		if(_onkeydownAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onkeydownAttr, "onkeydown", element));
		}

		if(_onkeyupAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onkeyupAttr, "onkeyup", element));
		}

		return element;
	}

	protected void parse(org.w3c.dom.Element element) throws org.safris.xml.bind.runtime.lang.UnmarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.NamedNodeMap namedNodeMap = element.getAttributes();
		org.w3c.dom.Node attribute = null;
		for(int i = 0; i < namedNodeMap.getLength(); i++)
		{
			attribute = namedNodeMap.item(i);
			if(attribute == null || XMLNS.getLocalPart().equals(attribute.getPrefix()))
			{
				continue;
			}
			else if("id".equals(attribute.getLocalName()))
			{
				this._idAttr = (IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(IdAttr.class, element, attribute);
			}
			else if("class".equals(attribute.getLocalName()))
			{
				this._classAttr = org.safris.xml.bind.runtime.lang.Binding._parseAttrList(ClassAttr.class, element, attribute);
			}
			else if("style".equals(attribute.getLocalName()))
			{
				this._styleAttr = (StyleAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(StyleAttr.class, element, attribute);
			}
			else if("title".equals(attribute.getLocalName()))
			{
				this._titleAttr = (TitleAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(TitleAttr.class, element, attribute);
			}
			else if("lang".equals(attribute.getLocalName()))
			{
				this._langAttr = (LangAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(LangAttr.class, element, attribute);
			}
			else if("http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI()) && "lang".equals(attribute.getLocalName()))
			{
				this._xmlLangAttr = (org.safris.web.depiction.schema.xml.namespace.XmlLangAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xml.namespace.XmlLangAttr.class, element, attribute);
			}
			else if("dir".equals(attribute.getLocalName()))
			{
				this._dirAttr = (DirAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(DirAttr.class, element, attribute);
			}
			else if("onclick".equals(attribute.getLocalName()))
			{
				this._onclickAttr = (OnclickAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnclickAttr.class, element, attribute);
			}
			else if("ondblclick".equals(attribute.getLocalName()))
			{
				this._ondblclickAttr = (OndblclickAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OndblclickAttr.class, element, attribute);
			}
			else if("onmousedown".equals(attribute.getLocalName()))
			{
				this._onmousedownAttr = (OnmousedownAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnmousedownAttr.class, element, attribute);
			}
			else if("onmouseup".equals(attribute.getLocalName()))
			{
				this._onmouseupAttr = (OnmouseupAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnmouseupAttr.class, element, attribute);
			}
			else if("onmouseover".equals(attribute.getLocalName()))
			{
				this._onmouseoverAttr = (OnmouseoverAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnmouseoverAttr.class, element, attribute);
			}
			else if("onmousemove".equals(attribute.getLocalName()))
			{
				this._onmousemoveAttr = (OnmousemoveAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnmousemoveAttr.class, element, attribute);
			}
			else if("onmouseout".equals(attribute.getLocalName()))
			{
				this._onmouseoutAttr = (OnmouseoutAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnmouseoutAttr.class, element, attribute);
			}
			else if("onkeypress".equals(attribute.getLocalName()))
			{
				this._onkeypressAttr = (OnkeypressAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnkeypressAttr.class, element, attribute);
			}
			else if("onkeydown".equals(attribute.getLocalName()))
			{
				this._onkeydownAttr = (OnkeydownAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnkeydownAttr.class, element, attribute);
			}
			else if("onkeyup".equals(attribute.getLocalName()))
			{
				this._onkeyupAttr = (OnkeyupAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnkeyupAttr.class, element, attribute);
			}
		}

		org.w3c.dom.NodeList nodeList = element.getChildNodes();
		org.w3c.dom.Node childNode = null;
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			childNode = nodeList.item(i);
			if(org.w3c.dom.Node.TEXT_NODE == childNode.getNodeType())
			{
				if(childNode.getNodeValue().length() != 0)
				{
					if(text == null)
						text = childNode.getNodeValue();

					else
						text += childNode.getNodeValue();
				}
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.Acronym clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.Acronym(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Acronym))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.Acronym binding = (org.safris.web.depiction.schema.xhtml.strict.Acronym)obj;
		if((this.text == null && binding.text != null) || (this.text != null && !this.text.equals(binding.text)))
			return false;

		if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
			return false;

		if((this._classAttr == null && binding._classAttr != null) || (this._classAttr != null && !this._classAttr.equals(binding._classAttr)))
			return false;

		if((this._styleAttr == null && binding._styleAttr != null) || (this._styleAttr != null && !this._styleAttr.equals(binding._styleAttr)))
			return false;

		if((this._titleAttr == null && binding._titleAttr != null) || (this._titleAttr != null && !this._titleAttr.equals(binding._titleAttr)))
			return false;

		if((this._langAttr == null && binding._langAttr != null) || (this._langAttr != null && !this._langAttr.equals(binding._langAttr)))
			return false;

		if((this._xmlLangAttr == null && binding._xmlLangAttr != null) || (this._xmlLangAttr != null && !this._xmlLangAttr.equals(binding._xmlLangAttr)))
			return false;

		if((this._dirAttr == null && binding._dirAttr != null) || (this._dirAttr != null && !this._dirAttr.equals(binding._dirAttr)))
			return false;

		if((this._onclickAttr == null && binding._onclickAttr != null) || (this._onclickAttr != null && !this._onclickAttr.equals(binding._onclickAttr)))
			return false;

		if((this._ondblclickAttr == null && binding._ondblclickAttr != null) || (this._ondblclickAttr != null && !this._ondblclickAttr.equals(binding._ondblclickAttr)))
			return false;

		if((this._onmousedownAttr == null && binding._onmousedownAttr != null) || (this._onmousedownAttr != null && !this._onmousedownAttr.equals(binding._onmousedownAttr)))
			return false;

		if((this._onmouseupAttr == null && binding._onmouseupAttr != null) || (this._onmouseupAttr != null && !this._onmouseupAttr.equals(binding._onmouseupAttr)))
			return false;

		if((this._onmouseoverAttr == null && binding._onmouseoverAttr != null) || (this._onmouseoverAttr != null && !this._onmouseoverAttr.equals(binding._onmouseoverAttr)))
			return false;

		if((this._onmousemoveAttr == null && binding._onmousemoveAttr != null) || (this._onmousemoveAttr != null && !this._onmousemoveAttr.equals(binding._onmousemoveAttr)))
			return false;

		if((this._onmouseoutAttr == null && binding._onmouseoutAttr != null) || (this._onmouseoutAttr != null && !this._onmouseoutAttr.equals(binding._onmouseoutAttr)))
			return false;

		if((this._onkeypressAttr == null && binding._onkeypressAttr != null) || (this._onkeypressAttr != null && !this._onkeypressAttr.equals(binding._onkeypressAttr)))
			return false;

		if((this._onkeydownAttr == null && binding._onkeydownAttr != null) || (this._onkeydownAttr != null && !this._onkeydownAttr.equals(binding._onkeydownAttr)))
			return false;

		if((this._onkeyupAttr == null && binding._onkeyupAttr != null) || (this._onkeyupAttr != null && !this._onkeyupAttr.equals(binding._onkeyupAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(text != null ? text.hashCode() : 0).append("-");
		stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
		stringBuffer.append(_classAttr != null ? _classAttr.hashCode() : 0).append("-");
		stringBuffer.append(_styleAttr != null ? _styleAttr.hashCode() : 0).append("-");
		stringBuffer.append(_titleAttr != null ? _titleAttr.hashCode() : 0).append("-");
		stringBuffer.append(_langAttr != null ? _langAttr.hashCode() : 0).append("-");
		stringBuffer.append(_xmlLangAttr != null ? _xmlLangAttr.hashCode() : 0).append("-");
		stringBuffer.append(_dirAttr != null ? _dirAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onclickAttr != null ? _onclickAttr.hashCode() : 0).append("-");
		stringBuffer.append(_ondblclickAttr != null ? _ondblclickAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onmousedownAttr != null ? _onmousedownAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onmouseupAttr != null ? _onmouseupAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onmouseoverAttr != null ? _onmouseoverAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onmousemoveAttr != null ? _onmousemoveAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onmouseoutAttr != null ? _onmouseoutAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onkeypressAttr != null ? _onkeypressAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onkeydownAttr != null ? _onkeydownAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onkeyupAttr != null ? _onkeyupAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class IdAttr extends org.safris.xml.bind.runtime.types.IdType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "id");

		public IdAttr(org.safris.xml.bind.runtime.types.IdType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public IdAttr(java.lang.String value)
		{
			super(value);
		}

		protected IdAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public IdAttr clone()
		{
			return new IdAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class ClassAttr extends org.safris.xml.bind.runtime.lang.CloneableList<org.safris.xml.bind.runtime.types.NMTokenType,org.safris.xml.bind.runtime.lang.SimpleType>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "class");

		public ClassAttr(org.safris.xml.bind.runtime.lang.CloneableList<org.safris.xml.bind.runtime.types.NMTokenType,org.safris.xml.bind.runtime.lang.SimpleType> binding)
		{
			super(binding);
		}

		public ClassAttr(java.util.List<java.lang.String> value)
		{
			super(value);
		}

		protected ClassAttr(java.lang.String value)
		{
			super(value);
		}

		protected ClassAttr()
		{
			super();
		}

		public java.util.List<java.lang.String> getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.util.List<java.lang.String> value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public ClassAttr clone()
		{
			return new ClassAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class StyleAttr extends org.safris.web.depiction.schema.xhtml.strict.IStyleSheet<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "style");

		public StyleAttr(org.safris.web.depiction.schema.xhtml.strict.IStyleSheet<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public StyleAttr(java.lang.String value)
		{
			super(value);
		}

		protected StyleAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public StyleAttr clone()
		{
			return new StyleAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class TitleAttr extends org.safris.web.depiction.schema.xhtml.strict.IText<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "title");

		public TitleAttr(org.safris.web.depiction.schema.xhtml.strict.IText<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public TitleAttr(java.lang.String value)
		{
			super(value);
		}

		protected TitleAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public TitleAttr clone()
		{
			return new TitleAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class LangAttr extends org.safris.web.depiction.schema.xhtml.strict.ILanguageCode<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "lang");

		public LangAttr(org.safris.web.depiction.schema.xhtml.strict.ILanguageCode<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public LangAttr(org.safris.xml.bind.runtime.types.lang.Language value)
		{
			super(value);
		}

		protected LangAttr(java.lang.String value)
		{
			super(value);
		}

		protected LangAttr()
		{
			super();
		}

		public org.safris.xml.bind.runtime.types.lang.Language getValue()
		{
			return super.getTEXT();
		}

		public void setValue(org.safris.xml.bind.runtime.types.lang.Language value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public LangAttr clone()
		{
			return new LangAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class DirAttr extends org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "dir");

		protected DirAttr(org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		protected DirAttr(java.lang.String value)
		{
			super(value);
		}

		protected static class RESTRICTION
		{
			protected static RESTRICTION parseRESTRICTION(java.lang.String value)
			{
				return singletons.get(value);
			}

			protected final static java.util.Map<java.lang.String,RESTRICTION> singletons = new java.util.HashMap<java.lang.String,RESTRICTION>();
			protected final java.lang.String value;

			protected RESTRICTION(java.lang.String value)
			{
				this.value = value;
				singletons.put(this.value, this);
			}

			public java.lang.String getValue()
			{
				return value;
			}
		}

		public static final RESTRICTION LTR = new RESTRICTION("ltr");
		public static final RESTRICTION RTL = new RESTRICTION("rtl");

		public DirAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected DirAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public DirAttr clone()
		{
			return new DirAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnclickAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onclick");

		public OnclickAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnclickAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnclickAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnclickAttr clone()
		{
			return new OnclickAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OndblclickAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "ondblclick");

		public OndblclickAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OndblclickAttr(java.lang.String value)
		{
			super(value);
		}

		protected OndblclickAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OndblclickAttr clone()
		{
			return new OndblclickAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnmousedownAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onmousedown");

		public OnmousedownAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnmousedownAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnmousedownAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnmousedownAttr clone()
		{
			return new OnmousedownAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnmouseupAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onmouseup");

		public OnmouseupAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnmouseupAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnmouseupAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnmouseupAttr clone()
		{
			return new OnmouseupAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnmouseoverAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onmouseover");

		public OnmouseoverAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnmouseoverAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnmouseoverAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnmouseoverAttr clone()
		{
			return new OnmouseoverAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnmousemoveAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onmousemove");

		public OnmousemoveAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnmousemoveAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnmousemoveAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnmousemoveAttr clone()
		{
			return new OnmousemoveAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnmouseoutAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onmouseout");

		public OnmouseoutAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnmouseoutAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnmouseoutAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnmouseoutAttr clone()
		{
			return new OnmouseoutAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnkeypressAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onkeypress");

		public OnkeypressAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnkeypressAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnkeypressAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnkeypressAttr clone()
		{
			return new OnkeypressAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnkeydownAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onkeydown");

		public OnkeydownAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnkeydownAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnkeydownAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnkeydownAttr clone()
		{
			return new OnkeydownAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class OnkeyupAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onkeyup");

		public OnkeyupAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnkeyupAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnkeyupAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public OnkeyupAttr clone()
		{
			return new OnkeyupAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}
}