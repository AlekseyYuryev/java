/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.web.depiction.schema.xhtml.strict;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class Map extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "map");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Map.class.getName());
	}

	private org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr _idAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr _classAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr _styleAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr _titleAttr = null;
	private org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr _nameAttr = null;
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
	private org.safris.web.depiction.schema.xhtml.strict.IPreContent _pre = null;
	private org.safris.web.depiction.schema.xhtml.strict.Hr _hr = null;
	private org.safris.web.depiction.schema.xhtml.strict.IBlock _blockquote = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _address = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline p = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _h1 = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _h2 = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _h3 = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _h4 = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _h5 = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _h6 = null;
	private org.safris.web.depiction.schema.xhtml.strict.IFlow _div = null;
	private org.safris.web.depiction.schema.xhtml.strict.Ul _ul = null;
	private org.safris.web.depiction.schema.xhtml.strict.Ol _ol = null;
	private org.safris.web.depiction.schema.xhtml.strict.Dl _dl = null;
	private org.safris.web.depiction.schema.xhtml.strict.Fieldset _fieldset = null;
	private org.safris.web.depiction.schema.xhtml.strict.Table _table = null;
	private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.IFormContent,org.safris.xml.bind.runtime.lang.ComplexType> _form = null;
	private org.safris.web.depiction.schema.xhtml.strict.IBlock _noscript = null;
	private org.safris.web.depiction.schema.xhtml.strict.IFlow _ins = null;
	private org.safris.web.depiction.schema.xhtml.strict.IFlow _del = null;
	private org.safris.web.depiction.schema.xhtml.strict.Script _script = null;
	private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Area,org.safris.xml.bind.runtime.lang.ComplexType> _area = null;

	public Map(org.safris.web.depiction.schema.xhtml.strict.Map binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Map)
		{
			org.safris.web.depiction.schema.xhtml.strict.Map element = (org.safris.web.depiction.schema.xhtml.strict.Map)binding;
			this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
			this._classAttr = element._classAttr != null ? element._classAttr.clone() : null;
			this._styleAttr = element._styleAttr != null ? element._styleAttr.clone() : null;
			this._titleAttr = element._titleAttr != null ? element._titleAttr.clone() : null;
			this._nameAttr = element._nameAttr != null ? element._nameAttr.clone() : null;
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
			this._pre = element._pre != null ? element._pre.clone() : null;
			this._hr = element._hr != null ? element._hr.clone() : null;
			this._blockquote = element._blockquote != null ? element._blockquote.clone() : null;
			this._address = element._address != null ? element._address.clone() : null;
			this.p = element.p != null ? element.p.clone() : null;
			this._h1 = element._h1 != null ? element._h1.clone() : null;
			this._h2 = element._h2 != null ? element._h2.clone() : null;
			this._h3 = element._h3 != null ? element._h3.clone() : null;
			this._h4 = element._h4 != null ? element._h4.clone() : null;
			this._h5 = element._h5 != null ? element._h5.clone() : null;
			this._h6 = element._h6 != null ? element._h6.clone() : null;
			this._div = element._div != null ? element._div.clone() : null;
			this._ul = element._ul != null ? element._ul.clone() : null;
			this._ol = element._ol != null ? element._ol.clone() : null;
			this._dl = element._dl != null ? element._dl.clone() : null;
			this._fieldset = element._fieldset != null ? element._fieldset.clone() : null;
			this._table = element._table != null ? element._table.clone() : null;
			this._form = element._form != null ? element._form.clone() : null;
			this._noscript = element._noscript != null ? element._noscript.clone() : null;
			this._ins = element._ins != null ? element._ins.clone() : null;
			this._del = element._del != null ? element._del.clone() : null;
			this._script = element._script != null ? element._script.clone() : null;
			this._area = element._area != null ? element._area.clone() : null;
		}
	}

	public Map()
	{
		super();
	}

	public void setIdAttr(org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr _idAttr)
	{
		this._idAttr = _idAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr getIdAttr()
	{
		return _idAttr;
	}

	public void setClassAttr(org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr _classAttr)
	{
		this._classAttr = _classAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr getClassAttr()
	{
		return _classAttr;
	}

	public void setStyleAttr(org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr _styleAttr)
	{
		this._styleAttr = _styleAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr getStyleAttr()
	{
		return _styleAttr;
	}

	public void setTitleAttr(org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr _titleAttr)
	{
		this._titleAttr = _titleAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr getTitleAttr()
	{
		return _titleAttr;
	}

	public void setNameAttr(org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr _nameAttr)
	{
		this._nameAttr = _nameAttr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr getNameAttr()
	{
		return _nameAttr;
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

	public void setPre(org.safris.web.depiction.schema.xhtml.strict.IPreContent _pre)
	{
		this._pre = _pre;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IPreContent getPre()
	{
		return _pre;
	}

	public void setHr(org.safris.web.depiction.schema.xhtml.strict.Hr _hr)
	{
		this._hr = _hr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Hr getHr()
	{
		return _hr;
	}

	public void setBlockquote(org.safris.web.depiction.schema.xhtml.strict.IBlock _blockquote)
	{
		this._blockquote = _blockquote;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IBlock getBlockquote()
	{
		return _blockquote;
	}

	public void setAddress(org.safris.web.depiction.schema.xhtml.strict.IInline _address)
	{
		this._address = _address;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getAddress()
	{
		return _address;
	}

	public void setP(org.safris.web.depiction.schema.xhtml.strict.IInline p)
	{
		this.p = p;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getP()
	{
		return p;
	}

	public void setH1(org.safris.web.depiction.schema.xhtml.strict.IInline _h1)
	{
		this._h1 = _h1;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getH1()
	{
		return _h1;
	}

	public void setH2(org.safris.web.depiction.schema.xhtml.strict.IInline _h2)
	{
		this._h2 = _h2;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getH2()
	{
		return _h2;
	}

	public void setH3(org.safris.web.depiction.schema.xhtml.strict.IInline _h3)
	{
		this._h3 = _h3;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getH3()
	{
		return _h3;
	}

	public void setH4(org.safris.web.depiction.schema.xhtml.strict.IInline _h4)
	{
		this._h4 = _h4;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getH4()
	{
		return _h4;
	}

	public void setH5(org.safris.web.depiction.schema.xhtml.strict.IInline _h5)
	{
		this._h5 = _h5;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getH5()
	{
		return _h5;
	}

	public void setH6(org.safris.web.depiction.schema.xhtml.strict.IInline _h6)
	{
		this._h6 = _h6;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getH6()
	{
		return _h6;
	}

	public void setDiv(org.safris.web.depiction.schema.xhtml.strict.IFlow _div)
	{
		this._div = _div;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IFlow getDiv()
	{
		return _div;
	}

	public void setUl(org.safris.web.depiction.schema.xhtml.strict.Ul _ul)
	{
		this._ul = _ul;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Ul getUl()
	{
		return _ul;
	}

	public void setOl(org.safris.web.depiction.schema.xhtml.strict.Ol _ol)
	{
		this._ol = _ol;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Ol getOl()
	{
		return _ol;
	}

	public void setDl(org.safris.web.depiction.schema.xhtml.strict.Dl _dl)
	{
		this._dl = _dl;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Dl getDl()
	{
		return _dl;
	}

	public void setFieldset(org.safris.web.depiction.schema.xhtml.strict.Fieldset _fieldset)
	{
		this._fieldset = _fieldset;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Fieldset getFieldset()
	{
		return _fieldset;
	}

	public void setTable(org.safris.web.depiction.schema.xhtml.strict.Table _table)
	{
		this._table = _table;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Table getTable()
	{
		return _table;
	}

	public void addForm(org.safris.web.depiction.schema.xhtml.strict.IFormContent _form)
	{
		if(this._form == null)
			this._form = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.IFormContent,org.safris.xml.bind.runtime.lang.ComplexType>();

		this._form.add(_form);
	}

	public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.IFormContent,org.safris.xml.bind.runtime.lang.ComplexType> getForm()
	{
		return _form;
	}

	public void setNoscript(org.safris.web.depiction.schema.xhtml.strict.IBlock _noscript)
	{
		this._noscript = _noscript;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IBlock getNoscript()
	{
		return _noscript;
	}

	public void setIns(org.safris.web.depiction.schema.xhtml.strict.IFlow _ins)
	{
		this._ins = _ins;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IFlow getIns()
	{
		return _ins;
	}

	public void setDel(org.safris.web.depiction.schema.xhtml.strict.IFlow _del)
	{
		this._del = _del;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IFlow getDel()
	{
		return _del;
	}

	public void setScript(org.safris.web.depiction.schema.xhtml.strict.Script _script)
	{
		this._script = _script;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Script getScript()
	{
		return _script;
	}

	public void addArea(org.safris.web.depiction.schema.xhtml.strict.Area _area)
	{
		if(this._area == null)
			this._area = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Area,org.safris.xml.bind.runtime.lang.ComplexType>();

		this._area.add(_area);
	}

	public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Area,org.safris.xml.bind.runtime.lang.ComplexType> getArea()
	{
		return _area;
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

		if(_nameAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_nameAttr, "name", element));
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

		if(_pre != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _pre, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "pre"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "pre.content")));
		}

		if(_hr != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _hr, _getName(_hr), _getTypeName(_hr)));
		}

		if(_blockquote != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _blockquote, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "blockquote"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Block")));
		}

		if(_address != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _address, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "address"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(p != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, p, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "p"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_h1 != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _h1, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h1"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_h2 != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _h2, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h2"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_h3 != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _h3, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h3"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_h4 != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _h4, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h4"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_h5 != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _h5, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h5"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_h6 != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _h6, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h6"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_div != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _div, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "div"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Flow")));
		}

		if(_ul != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _ul, _getName(_ul), _getTypeName(_ul)));
		}

		if(_ol != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _ol, _getName(_ol), _getTypeName(_ol)));
		}

		if(_dl != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _dl, _getName(_dl), _getTypeName(_dl)));
		}

		if(_fieldset != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _fieldset, _getName(_fieldset), _getTypeName(_fieldset)));
		}

		if(_table != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _table, _getName(_table), _getTypeName(_table)));
		}

		if(_form != null)
		{
			for(org.safris.web.depiction.schema.xhtml.strict.IFormContent _form : this._form)
				element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _form, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "form"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "form.content")));
		}

		if(_noscript != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _noscript, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "noscript"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Block")));
		}

		if(_ins != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _ins, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "ins"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Flow")));
		}

		if(_del != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _del, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "del"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Flow")));
		}

		if(_script != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _script, _getName(_script), _getTypeName(_script)));
		}

		if(_area != null)
		{
			for(org.safris.web.depiction.schema.xhtml.strict.Area _area : this._area)
				element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _area, _getName(_area), _getTypeName(_area)));
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
				this._idAttr = (org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr.class, element, attribute);
			}
			else if("class".equals(attribute.getLocalName()))
			{
				this._classAttr = (org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr.class, element, attribute);
			}
			else if("style".equals(attribute.getLocalName()))
			{
				this._styleAttr = (org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr.class, element, attribute);
			}
			else if("title".equals(attribute.getLocalName()))
			{
				this._titleAttr = (org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr.class, element, attribute);
			}
			else if("name".equals(attribute.getLocalName()))
			{
				this._nameAttr = (org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr.class, element, attribute);
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
			if(childNode.getLocalName() == null || childNode.getNodeType() == org.w3c.dom.Node.TEXT_NODE)
			{
				continue;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "pre".equals(childNode.getLocalName()))
			{
				this._pre = (org.safris.web.depiction.schema.xhtml.strict.IPreContent)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Pre.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "pre"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "hr".equals(childNode.getLocalName()))
			{
				this._hr = (org.safris.web.depiction.schema.xhtml.strict.Hr)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Hr.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "hr"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "blockquote".equals(childNode.getLocalName()))
			{
				this._blockquote = (org.safris.web.depiction.schema.xhtml.strict.IBlock)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Blockquote.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "blockquote"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "address".equals(childNode.getLocalName()))
			{
				this._address = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Address.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "address"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "p".equals(childNode.getLocalName()))
			{
				this.p = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.P.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "p"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "h1".equals(childNode.getLocalName()))
			{
				this._h1 = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.H1.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h1"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "h2".equals(childNode.getLocalName()))
			{
				this._h2 = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.H2.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h2"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "h3".equals(childNode.getLocalName()))
			{
				this._h3 = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.H3.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h3"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "h4".equals(childNode.getLocalName()))
			{
				this._h4 = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.H4.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h4"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "h5".equals(childNode.getLocalName()))
			{
				this._h5 = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.H5.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h5"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "h6".equals(childNode.getLocalName()))
			{
				this._h6 = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.H6.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "h6"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "div".equals(childNode.getLocalName()))
			{
				this._div = (org.safris.web.depiction.schema.xhtml.strict.IFlow)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Div.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "div"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "ul".equals(childNode.getLocalName()))
			{
				this._ul = (org.safris.web.depiction.schema.xhtml.strict.Ul)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Ul.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "ul"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "ol".equals(childNode.getLocalName()))
			{
				this._ol = (org.safris.web.depiction.schema.xhtml.strict.Ol)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Ol.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "ol"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "dl".equals(childNode.getLocalName()))
			{
				this._dl = (org.safris.web.depiction.schema.xhtml.strict.Dl)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Dl.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "dl"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "fieldset".equals(childNode.getLocalName()))
			{
				this._fieldset = (org.safris.web.depiction.schema.xhtml.strict.Fieldset)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Fieldset.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "fieldset"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "table".equals(childNode.getLocalName()))
			{
				this._table = (org.safris.web.depiction.schema.xhtml.strict.Table)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Table.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "table"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "form".equals(childNode.getLocalName()))
			{
				if(this._form == null)
					this._form = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.IFormContent,org.safris.xml.bind.runtime.lang.ComplexType>();

				this._form.add((org.safris.web.depiction.schema.xhtml.strict.IFormContent)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Form.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "form")));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "noscript".equals(childNode.getLocalName()))
			{
				this._noscript = (org.safris.web.depiction.schema.xhtml.strict.IBlock)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Noscript.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "noscript"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "ins".equals(childNode.getLocalName()))
			{
				this._ins = (org.safris.web.depiction.schema.xhtml.strict.IFlow)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Ins.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "ins"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "del".equals(childNode.getLocalName()))
			{
				this._del = (org.safris.web.depiction.schema.xhtml.strict.IFlow)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Del.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "del"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "script".equals(childNode.getLocalName()))
			{
				this._script = (org.safris.web.depiction.schema.xhtml.strict.Script)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Script.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "script"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "area".equals(childNode.getLocalName()))
			{
				if(this._area == null)
					this._area = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Area,org.safris.xml.bind.runtime.lang.ComplexType>();

				this._area.add((org.safris.web.depiction.schema.xhtml.strict.Area)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Area.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "area")));
				element.removeChild(childNode);
				i--;
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map clone()
	{
		return new org.safris.web.depiction.schema.xhtml.strict.Map(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Map))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.Map binding = (org.safris.web.depiction.schema.xhtml.strict.Map)obj;
		if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
			return false;

		if((this._classAttr == null && binding._classAttr != null) || (this._classAttr != null && !this._classAttr.equals(binding._classAttr)))
			return false;

		if((this._styleAttr == null && binding._styleAttr != null) || (this._styleAttr != null && !this._styleAttr.equals(binding._styleAttr)))
			return false;

		if((this._titleAttr == null && binding._titleAttr != null) || (this._titleAttr != null && !this._titleAttr.equals(binding._titleAttr)))
			return false;

		if((this._nameAttr == null && binding._nameAttr != null) || (this._nameAttr != null && !this._nameAttr.equals(binding._nameAttr)))
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

		if((this._pre == null && binding._pre != null) || (this._pre != null && !this._pre.equals(binding._pre)))
			return false;

		if((this._hr == null && binding._hr != null) || (this._hr != null && !this._hr.equals(binding._hr)))
			return false;

		if((this._blockquote == null && binding._blockquote != null) || (this._blockquote != null && !this._blockquote.equals(binding._blockquote)))
			return false;

		if((this._address == null && binding._address != null) || (this._address != null && !this._address.equals(binding._address)))
			return false;

		if((this.p == null && binding.p != null) || (this.p != null && !this.p.equals(binding.p)))
			return false;

		if((this._h1 == null && binding._h1 != null) || (this._h1 != null && !this._h1.equals(binding._h1)))
			return false;

		if((this._h2 == null && binding._h2 != null) || (this._h2 != null && !this._h2.equals(binding._h2)))
			return false;

		if((this._h3 == null && binding._h3 != null) || (this._h3 != null && !this._h3.equals(binding._h3)))
			return false;

		if((this._h4 == null && binding._h4 != null) || (this._h4 != null && !this._h4.equals(binding._h4)))
			return false;

		if((this._h5 == null && binding._h5 != null) || (this._h5 != null && !this._h5.equals(binding._h5)))
			return false;

		if((this._h6 == null && binding._h6 != null) || (this._h6 != null && !this._h6.equals(binding._h6)))
			return false;

		if((this._div == null && binding._div != null) || (this._div != null && !this._div.equals(binding._div)))
			return false;

		if((this._ul == null && binding._ul != null) || (this._ul != null && !this._ul.equals(binding._ul)))
			return false;

		if((this._ol == null && binding._ol != null) || (this._ol != null && !this._ol.equals(binding._ol)))
			return false;

		if((this._dl == null && binding._dl != null) || (this._dl != null && !this._dl.equals(binding._dl)))
			return false;

		if((this._fieldset == null && binding._fieldset != null) || (this._fieldset != null && !this._fieldset.equals(binding._fieldset)))
			return false;

		if((this._table == null && binding._table != null) || (this._table != null && !this._table.equals(binding._table)))
			return false;

		if((this._form == null && binding._form != null) || (this._form != null && !this._form.equals(binding._form)))
			return false;

		if((this._noscript == null && binding._noscript != null) || (this._noscript != null && !this._noscript.equals(binding._noscript)))
			return false;

		if((this._ins == null && binding._ins != null) || (this._ins != null && !this._ins.equals(binding._ins)))
			return false;

		if((this._del == null && binding._del != null) || (this._del != null && !this._del.equals(binding._del)))
			return false;

		if((this._script == null && binding._script != null) || (this._script != null && !this._script.equals(binding._script)))
			return false;

		if((this._area == null && binding._area != null) || (this._area != null && !this._area.equals(binding._area)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
		stringBuffer.append(_classAttr != null ? _classAttr.hashCode() : 0).append("-");
		stringBuffer.append(_styleAttr != null ? _styleAttr.hashCode() : 0).append("-");
		stringBuffer.append(_titleAttr != null ? _titleAttr.hashCode() : 0).append("-");
		stringBuffer.append(_nameAttr != null ? _nameAttr.hashCode() : 0).append("-");
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
		stringBuffer.append(_pre != null ? _pre.hashCode() : 0).append("-");
		stringBuffer.append(_hr != null ? _hr.hashCode() : 0).append("-");
		stringBuffer.append(_blockquote != null ? _blockquote.hashCode() : 0).append("-");
		stringBuffer.append(_address != null ? _address.hashCode() : 0).append("-");
		stringBuffer.append(p != null ? p.hashCode() : 0).append("-");
		stringBuffer.append(_h1 != null ? _h1.hashCode() : 0).append("-");
		stringBuffer.append(_h2 != null ? _h2.hashCode() : 0).append("-");
		stringBuffer.append(_h3 != null ? _h3.hashCode() : 0).append("-");
		stringBuffer.append(_h4 != null ? _h4.hashCode() : 0).append("-");
		stringBuffer.append(_h5 != null ? _h5.hashCode() : 0).append("-");
		stringBuffer.append(_h6 != null ? _h6.hashCode() : 0).append("-");
		stringBuffer.append(_div != null ? _div.hashCode() : 0).append("-");
		stringBuffer.append(_ul != null ? _ul.hashCode() : 0).append("-");
		stringBuffer.append(_ol != null ? _ol.hashCode() : 0).append("-");
		stringBuffer.append(_dl != null ? _dl.hashCode() : 0).append("-");
		stringBuffer.append(_fieldset != null ? _fieldset.hashCode() : 0).append("-");
		stringBuffer.append(_table != null ? _table.hashCode() : 0).append("-");
		stringBuffer.append(_form != null ? _form.hashCode() : 0).append("-");
		stringBuffer.append(_noscript != null ? _noscript.hashCode() : 0).append("-");
		stringBuffer.append(_ins != null ? _ins.hashCode() : 0).append("-");
		stringBuffer.append(_del != null ? _del.hashCode() : 0).append("-");
		stringBuffer.append(_script != null ? _script.hashCode() : 0).append("-");
		stringBuffer.append(_area != null ? _area.hashCode() : 0).append("-");
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

		public org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Map.IdAttr(this);
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

	public static class ClassAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "class");

		public ClassAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public ClassAttr(java.lang.String value)
		{
			super(value);
		}

		protected ClassAttr()
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

		public org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Map.ClassAttr(this);
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

		public org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Map.StyleAttr(this);
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

		public org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Map.TitleAttr(this);
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

	public static class NameAttr extends org.safris.xml.bind.runtime.types.NMTokenType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "name");

		public NameAttr(org.safris.xml.bind.runtime.types.NMTokenType<org.safris.xml.bind.runtime.lang.Attribute> binding)
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

		public org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr clone()
		{
			return new org.safris.web.depiction.schema.xhtml.strict.Map.NameAttr(this);
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