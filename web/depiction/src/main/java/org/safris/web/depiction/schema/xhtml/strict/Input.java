package org.safris.web.depiction.schema.xhtml.strict;

/**
 * form control the name attribute is required for all but submit & reset
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public class Input extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "input");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Input.class.getName());
	}

	private org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr _typeAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr _nameAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr _valueAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr _checkedAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr _disabledAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr _readonlyAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr _sizeAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr _maxlengthAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr _srcAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr _altAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr _usemapAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr _onselectAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr _onchangeAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr _acceptAttr = null;
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
	private AccesskeyAttr _accesskeyAttr = null;
	private TabindexAttr _tabindexAttr = null;
	private OnfocusAttr _onfocusAttr = null;
	private OnblurAttr _onblurAttr = null;

	public Input(org.safris.web.depiction.schema.xhtml.strict.Input binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Input)
		{
			org.safris.web.depiction.schema.xhtml.strict.Input element = (org.safris.web.depiction.schema.xhtml.strict.Input)binding;
			this._typeAttr = element._typeAttr != null ? element._typeAttr.clone() : null;
			this._nameAttr = element._nameAttr != null ? element._nameAttr.clone() : null;
			this._valueAttr = element._valueAttr != null ? element._valueAttr.clone() : null;
			this._checkedAttr = element._checkedAttr != null ? element._checkedAttr.clone() : null;
			this._disabledAttr = element._disabledAttr != null ? element._disabledAttr.clone() : null;
			this._readonlyAttr = element._readonlyAttr != null ? element._readonlyAttr.clone() : null;
			this._sizeAttr = element._sizeAttr != null ? element._sizeAttr.clone() : null;
			this._maxlengthAttr = element._maxlengthAttr != null ? element._maxlengthAttr.clone() : null;
			this._srcAttr = element._srcAttr != null ? element._srcAttr.clone() : null;
			this._altAttr = element._altAttr != null ? element._altAttr.clone() : null;
			this._usemapAttr = element._usemapAttr != null ? element._usemapAttr.clone() : null;
			this._onselectAttr = element._onselectAttr != null ? element._onselectAttr.clone() : null;
			this._onchangeAttr = element._onchangeAttr != null ? element._onchangeAttr.clone() : null;
			this._acceptAttr = element._acceptAttr != null ? element._acceptAttr.clone() : null;
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
			this._accesskeyAttr = element._accesskeyAttr != null ? element._accesskeyAttr.clone() : null;
			this._tabindexAttr = element._tabindexAttr != null ? element._tabindexAttr.clone() : null;
			this._onfocusAttr = element._onfocusAttr != null ? element._onfocusAttr.clone() : null;
			this._onblurAttr = element._onblurAttr != null ? element._onblurAttr.clone() : null;
		}
	}

	public Input()
	{
		super();
	}

	public void setTypeAttr(org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr _typeAttr)
	{
		this._typeAttr = _typeAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr getTypeAttr()
	{
		return _typeAttr;
	}

	public void setNameAttr(org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr _nameAttr)
	{
		this._nameAttr = _nameAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr getNameAttr()
	{
		return _nameAttr;
	}

	public void setValueAttr(org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr _valueAttr)
	{
		this._valueAttr = _valueAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr getValueAttr()
	{
		return _valueAttr;
	}

	public void setCheckedAttr(org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr _checkedAttr)
	{
		this._checkedAttr = _checkedAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr getCheckedAttr()
	{
		return _checkedAttr;
	}

	public void setDisabledAttr(org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr _disabledAttr)
	{
		this._disabledAttr = _disabledAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr getDisabledAttr()
	{
		return _disabledAttr;
	}

	public void setReadonlyAttr(org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr _readonlyAttr)
	{
		this._readonlyAttr = _readonlyAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr getReadonlyAttr()
	{
		return _readonlyAttr;
	}

	public void setSizeAttr(org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr _sizeAttr)
	{
		this._sizeAttr = _sizeAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr getSizeAttr()
	{
		return _sizeAttr;
	}

	public void setMaxlengthAttr(org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr _maxlengthAttr)
	{
		this._maxlengthAttr = _maxlengthAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr getMaxlengthAttr()
	{
		return _maxlengthAttr;
	}

	public void setSrcAttr(org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr _srcAttr)
	{
		this._srcAttr = _srcAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr getSrcAttr()
	{
		return _srcAttr;
	}

	public void setAltAttr(org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr _altAttr)
	{
		this._altAttr = _altAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr getAltAttr()
	{
		return _altAttr;
	}

	public void setUsemapAttr(org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr _usemapAttr)
	{
		this._usemapAttr = _usemapAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr getUsemapAttr()
	{
		return _usemapAttr;
	}

	public void setOnselectAttr(org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr _onselectAttr)
	{
		this._onselectAttr = _onselectAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr getOnselectAttr()
	{
		return _onselectAttr;
	}

	public void setOnchangeAttr(org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr _onchangeAttr)
	{
		this._onchangeAttr = _onchangeAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr getOnchangeAttr()
	{
		return _onchangeAttr;
	}

	public void setAcceptAttr(org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr _acceptAttr)
	{
		this._acceptAttr = _acceptAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr getAcceptAttr()
	{
		return _acceptAttr;
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

	public void setAccesskeyAttr(AccesskeyAttr _accesskeyAttr)
	{
		this._accesskeyAttr = _accesskeyAttr;
	}

	public AccesskeyAttr getAccesskeyAttr()
	{
		return _accesskeyAttr;
	}

	public void setTabindexAttr(TabindexAttr _tabindexAttr)
	{
		this._tabindexAttr = _tabindexAttr;
	}

	public TabindexAttr getTabindexAttr()
	{
		return _tabindexAttr;
	}

	public void setOnfocusAttr(OnfocusAttr _onfocusAttr)
	{
		this._onfocusAttr = _onfocusAttr;
	}

	public OnfocusAttr getOnfocusAttr()
	{
		return _onfocusAttr;
	}

	public void setOnblurAttr(OnblurAttr _onblurAttr)
	{
		this._onblurAttr = _onblurAttr;
	}

	public OnblurAttr getOnblurAttr()
	{
		return _onblurAttr;
	}

	protected javax.xml.namespace.QName _getName()
	{
		return NAME;
	}

	protected javax.xml.namespace.QName _getTypeName()
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
		if(_typeAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_typeAttr, "type", element));
		}

		if(_nameAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_nameAttr, "name", element));
		}

		if(_valueAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_valueAttr, "value", element));
		}

		if(_checkedAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_checkedAttr, "checked", element));
		}

		if(_disabledAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_disabledAttr, "disabled", element));
		}

		if(_readonlyAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_readonlyAttr, "readonly", element));
		}

		if(_sizeAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_sizeAttr, "size", element));
		}

		if(_maxlengthAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_maxlengthAttr, "maxlength", element));
		}

		if(_srcAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_srcAttr, "src", element));
		}

		if(_altAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_altAttr, "alt", element));
		}

		if(_usemapAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_usemapAttr, "usemap", element));
		}

		if(_onselectAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onselectAttr, "onselect", element));
		}

		if(_onchangeAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onchangeAttr, "onchange", element));
		}

		if(_acceptAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_acceptAttr, "accept", element));
		}

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

		if(_accesskeyAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_accesskeyAttr, "accesskey", element));
		}

		if(_tabindexAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_tabindexAttr, "tabindex", element));
		}

		if(_onfocusAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onfocusAttr, "onfocus", element));
		}

		if(_onblurAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_onblurAttr, "onblur", element));
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
			else if("type".equals(attribute.getLocalName()))
			{
				this._typeAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr.class, element, attribute);
			}
			else if("name".equals(attribute.getLocalName()))
			{
				this._nameAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr.class, element, attribute);
			}
			else if("value".equals(attribute.getLocalName()))
			{
				this._valueAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr.class, element, attribute);
			}
			else if("checked".equals(attribute.getLocalName()))
			{
				this._checkedAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr.class, element, attribute);
			}
			else if("disabled".equals(attribute.getLocalName()))
			{
				this._disabledAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr.class, element, attribute);
			}
			else if("readonly".equals(attribute.getLocalName()))
			{
				this._readonlyAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr.class, element, attribute);
			}
			else if("size".equals(attribute.getLocalName()))
			{
				this._sizeAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr.class, element, attribute);
			}
			else if("maxlength".equals(attribute.getLocalName()))
			{
				this._maxlengthAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr.class, element, attribute);
			}
			else if("src".equals(attribute.getLocalName()))
			{
				this._srcAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr.class, element, attribute);
			}
			else if("alt".equals(attribute.getLocalName()))
			{
				this._altAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr.class, element, attribute);
			}
			else if("usemap".equals(attribute.getLocalName()))
			{
				this._usemapAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr.class, element, attribute);
			}
			else if("onselect".equals(attribute.getLocalName()))
			{
				this._onselectAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr.class, element, attribute);
			}
			else if("onchange".equals(attribute.getLocalName()))
			{
				this._onchangeAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr.class, element, attribute);
			}
			else if("accept".equals(attribute.getLocalName()))
			{
				this._acceptAttr = (org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr.class, element, attribute);
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
			else if("accesskey".equals(attribute.getLocalName()))
			{
				this._accesskeyAttr = (AccesskeyAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(AccesskeyAttr.class, element, attribute);
			}
			else if("tabindex".equals(attribute.getLocalName()))
			{
				this._tabindexAttr = (TabindexAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(TabindexAttr.class, element, attribute);
			}
			else if("onfocus".equals(attribute.getLocalName()))
			{
				this._onfocusAttr = (OnfocusAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnfocusAttr.class, element, attribute);
			}
			else if("onblur".equals(attribute.getLocalName()))
			{
				this._onblurAttr = (OnblurAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(OnblurAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.Input(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Input))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.Input binding = (org.safris.web.depiction.schema.xhtml.strict.Input)obj;
		if((this._typeAttr == null && binding._typeAttr != null) || (this._typeAttr != null && !this._typeAttr.equals(binding._typeAttr)))
			return false;

		if((this._nameAttr == null && binding._nameAttr != null) || (this._nameAttr != null && !this._nameAttr.equals(binding._nameAttr)))
			return false;

		if((this._valueAttr == null && binding._valueAttr != null) || (this._valueAttr != null && !this._valueAttr.equals(binding._valueAttr)))
			return false;

		if((this._checkedAttr == null && binding._checkedAttr != null) || (this._checkedAttr != null && !this._checkedAttr.equals(binding._checkedAttr)))
			return false;

		if((this._disabledAttr == null && binding._disabledAttr != null) || (this._disabledAttr != null && !this._disabledAttr.equals(binding._disabledAttr)))
			return false;

		if((this._readonlyAttr == null && binding._readonlyAttr != null) || (this._readonlyAttr != null && !this._readonlyAttr.equals(binding._readonlyAttr)))
			return false;

		if((this._sizeAttr == null && binding._sizeAttr != null) || (this._sizeAttr != null && !this._sizeAttr.equals(binding._sizeAttr)))
			return false;

		if((this._maxlengthAttr == null && binding._maxlengthAttr != null) || (this._maxlengthAttr != null && !this._maxlengthAttr.equals(binding._maxlengthAttr)))
			return false;

		if((this._srcAttr == null && binding._srcAttr != null) || (this._srcAttr != null && !this._srcAttr.equals(binding._srcAttr)))
			return false;

		if((this._altAttr == null && binding._altAttr != null) || (this._altAttr != null && !this._altAttr.equals(binding._altAttr)))
			return false;

		if((this._usemapAttr == null && binding._usemapAttr != null) || (this._usemapAttr != null && !this._usemapAttr.equals(binding._usemapAttr)))
			return false;

		if((this._onselectAttr == null && binding._onselectAttr != null) || (this._onselectAttr != null && !this._onselectAttr.equals(binding._onselectAttr)))
			return false;

		if((this._onchangeAttr == null && binding._onchangeAttr != null) || (this._onchangeAttr != null && !this._onchangeAttr.equals(binding._onchangeAttr)))
			return false;

		if((this._acceptAttr == null && binding._acceptAttr != null) || (this._acceptAttr != null && !this._acceptAttr.equals(binding._acceptAttr)))
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

		if((this._accesskeyAttr == null && binding._accesskeyAttr != null) || (this._accesskeyAttr != null && !this._accesskeyAttr.equals(binding._accesskeyAttr)))
			return false;

		if((this._tabindexAttr == null && binding._tabindexAttr != null) || (this._tabindexAttr != null && !this._tabindexAttr.equals(binding._tabindexAttr)))
			return false;

		if((this._onfocusAttr == null && binding._onfocusAttr != null) || (this._onfocusAttr != null && !this._onfocusAttr.equals(binding._onfocusAttr)))
			return false;

		if((this._onblurAttr == null && binding._onblurAttr != null) || (this._onblurAttr != null && !this._onblurAttr.equals(binding._onblurAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_typeAttr != null ? _typeAttr.hashCode() : 0).append("-");
		stringBuffer.append(_nameAttr != null ? _nameAttr.hashCode() : 0).append("-");
		stringBuffer.append(_valueAttr != null ? _valueAttr.hashCode() : 0).append("-");
		stringBuffer.append(_checkedAttr != null ? _checkedAttr.hashCode() : 0).append("-");
		stringBuffer.append(_disabledAttr != null ? _disabledAttr.hashCode() : 0).append("-");
		stringBuffer.append(_readonlyAttr != null ? _readonlyAttr.hashCode() : 0).append("-");
		stringBuffer.append(_sizeAttr != null ? _sizeAttr.hashCode() : 0).append("-");
		stringBuffer.append(_maxlengthAttr != null ? _maxlengthAttr.hashCode() : 0).append("-");
		stringBuffer.append(_srcAttr != null ? _srcAttr.hashCode() : 0).append("-");
		stringBuffer.append(_altAttr != null ? _altAttr.hashCode() : 0).append("-");
		stringBuffer.append(_usemapAttr != null ? _usemapAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onselectAttr != null ? _onselectAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onchangeAttr != null ? _onchangeAttr.hashCode() : 0).append("-");
		stringBuffer.append(_acceptAttr != null ? _acceptAttr.hashCode() : 0).append("-");
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
		stringBuffer.append(_accesskeyAttr != null ? _accesskeyAttr.hashCode() : 0).append("-");
		stringBuffer.append(_tabindexAttr != null ? _tabindexAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onfocusAttr != null ? _onfocusAttr.hashCode() : 0).append("-");
		stringBuffer.append(_onblurAttr != null ? _onblurAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class TypeAttr extends org.safris.web.depiction.schema.xhtml.strict.IInputType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "type");

		public TypeAttr(org.safris.web.depiction.schema.xhtml.strict.IInputType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public TypeAttr(java.lang.String value)
		{
			super(value);
		}

		public TypeAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected TypeAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.TypeAttr(this);
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
	 * the name attribute is required for all but submit & reset
	 *
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	 */

	public static class NameAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "name");

		public NameAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public NameAttr(java.lang.String value)
		{
			super(value);
		}

		protected NameAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.NameAttr(this);
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

	public static class ValueAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "value");

		public ValueAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public ValueAttr(java.lang.String value)
		{
			super(value);
		}

		protected ValueAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.ValueAttr(this);
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

	public static class CheckedAttr extends org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "checked");

		protected CheckedAttr(org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		protected CheckedAttr(java.lang.String value)
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

		public static final RESTRICTION CHECKED = new RESTRICTION("checked");

		public CheckedAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected CheckedAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.CheckedAttr(this);
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

	public static class DisabledAttr extends org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "disabled");

		protected DisabledAttr(org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		protected DisabledAttr(java.lang.String value)
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

		public static final RESTRICTION DISABLED = new RESTRICTION("disabled");

		public DisabledAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected DisabledAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.DisabledAttr(this);
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

	public static class ReadonlyAttr extends org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "readonly");

		protected ReadonlyAttr(org.safris.xml.bind.runtime.types.TokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		protected ReadonlyAttr(java.lang.String value)
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

		public static final RESTRICTION READONLY = new RESTRICTION("readonly");

		public ReadonlyAttr(RESTRICTION restriction)
		{
			super(restriction.value);
		}

		protected ReadonlyAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.ReadonlyAttr(this);
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

	public static class SizeAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "size");

		public SizeAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public SizeAttr(java.lang.String value)
		{
			super(value);
		}

		protected SizeAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.SizeAttr(this);
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

	public static class MaxlengthAttr extends org.safris.web.depiction.schema.xhtml.strict.INumber<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "maxlength");

		public MaxlengthAttr(org.safris.web.depiction.schema.xhtml.strict.INumber<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public MaxlengthAttr(java.lang.Integer value)
		{
			super(value);
		}

		protected MaxlengthAttr(java.lang.String value)
		{
			super(value);
		}

		protected MaxlengthAttr()
		{
			super();
		}

		public java.lang.Integer getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.Integer value)
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.MaxlengthAttr(this);
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

	public static class SrcAttr extends org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "src");

		public SrcAttr(org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public SrcAttr(java.lang.String value)
		{
			super(value);
		}

		protected SrcAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.SrcAttr(this);
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

	public static class AltAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "alt");

		public AltAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public AltAttr(java.lang.String value)
		{
			super(value);
		}

		protected AltAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.AltAttr(this);
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

	public static class UsemapAttr extends org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "usemap");

		public UsemapAttr(org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public UsemapAttr(java.lang.String value)
		{
			super(value);
		}

		protected UsemapAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.UsemapAttr(this);
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

	public static class OnselectAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onselect");

		public OnselectAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnselectAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnselectAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.OnselectAttr(this);
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

	public static class OnchangeAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onchange");

		public OnchangeAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnchangeAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnchangeAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.OnchangeAttr(this);
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

	public static class AcceptAttr extends org.safris.web.depiction.schema.xhtml.strict.IContentTypes<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "accept");

		public AcceptAttr(org.safris.web.depiction.schema.xhtml.strict.IContentTypes<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public AcceptAttr(java.lang.String value)
		{
			super(value);
		}

		protected AcceptAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Input.AcceptAttr(this);
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

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class AccesskeyAttr extends org.safris.web.depiction.schema.xhtml.strict.ICharacter<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "accesskey");

		public AccesskeyAttr(org.safris.web.depiction.schema.xhtml.strict.ICharacter<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public AccesskeyAttr(java.lang.String value)
		{
			super(value);
		}

		protected AccesskeyAttr()
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

		public AccesskeyAttr clone()
		{
			return new AccesskeyAttr(this);
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

	public static class TabindexAttr extends org.safris.web.depiction.schema.xhtml.strict.ITabindexNumber<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "tabindex");

		public TabindexAttr(org.safris.web.depiction.schema.xhtml.strict.ITabindexNumber<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public TabindexAttr(java.lang.Integer value)
		{
			super(value);
		}

		protected TabindexAttr(java.lang.String value)
		{
			super(value);
		}

		protected TabindexAttr()
		{
			super();
		}

		public java.lang.Integer getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.Integer value)
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

		public TabindexAttr clone()
		{
			return new TabindexAttr(this);
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

	public static class OnfocusAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onfocus");

		public OnfocusAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnfocusAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnfocusAttr()
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

		public OnfocusAttr clone()
		{
			return new OnfocusAttr(this);
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

	public static class OnblurAttr extends org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "onblur");

		public OnblurAttr(org.safris.web.depiction.schema.xhtml.strict.IScript<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public OnblurAttr(java.lang.String value)
		{
			super(value);
		}

		protected OnblurAttr()
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

		public OnblurAttr clone()
		{
			return new OnblurAttr(this);
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