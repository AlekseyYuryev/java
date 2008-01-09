package org.safris.web.depiction.schema.xhtml.strict;

/**
 * "Flow" mixes block and inline and is used for list items etc.
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public abstract class IFlow<T extends org.safris.xml.bind.runtime.lang.ComplexType> extends org.safris.xml.bind.runtime.lang.Binding<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Flow");

	protected static IFlow newInstance(final javax.xml.namespace.QName name)
	{
		return new IFlow()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.IFlow.class.getName());
	}

	private java.lang.String text = null;
	private org.safris.web.depiction.schema.xhtml.strict.IBlock _noscript = null;
	private org.safris.web.depiction.schema.xhtml.strict.IFlow _ins = null;
	private org.safris.web.depiction.schema.xhtml.strict.IFlow _del = null;
	private org.safris.web.depiction.schema.xhtml.strict.Script _script = null;
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
	private org.safris.xml.bind.runtime.lang.Binding any = null;
	private org.safris.web.depiction.schema.xhtml.strict.Input _input = null;
	private org.safris.web.depiction.schema.xhtml.strict.Select _select = null;
	private org.safris.web.depiction.schema.xhtml.strict.Textarea _textarea = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _label = null;
	private org.safris.web.depiction.schema.xhtml.strict.IButtonContent _button = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _em = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _strong = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _dfn = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _code = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline q = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _samp = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _kbd = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _var = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _cite = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _abbr = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _acronym = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _sub = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _sup = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _tt = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline i = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline b = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _big = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _small = null;
	private org.safris.web.depiction.schema.xhtml.strict.IAContent a = null;
	private org.safris.web.depiction.schema.xhtml.strict.Br _br = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _span = null;
	private org.safris.web.depiction.schema.xhtml.strict.IInline _bdo = null;
	private org.safris.web.depiction.schema.xhtml.strict.Map _map = null;
	private org.safris.web.depiction.schema.xhtml.strict.Object _object = null;
	private org.safris.web.depiction.schema.xhtml.strict.Img _img = null;

	public IFlow(org.safris.web.depiction.schema.xhtml.strict.IFlow binding)
	{
		super(binding);
		this.text = binding.text;
		this._noscript = binding._noscript != null ? binding._noscript.clone() : null;
		this._ins = binding._ins != null ? binding._ins.clone() : null;
		this._del = binding._del != null ? binding._del.clone() : null;
		this._script = binding._script != null ? binding._script.clone() : null;
		this._pre = binding._pre != null ? binding._pre.clone() : null;
		this._hr = binding._hr != null ? binding._hr.clone() : null;
		this._blockquote = binding._blockquote != null ? binding._blockquote.clone() : null;
		this._address = binding._address != null ? binding._address.clone() : null;
		this.p = binding.p != null ? binding.p.clone() : null;
		this._h1 = binding._h1 != null ? binding._h1.clone() : null;
		this._h2 = binding._h2 != null ? binding._h2.clone() : null;
		this._h3 = binding._h3 != null ? binding._h3.clone() : null;
		this._h4 = binding._h4 != null ? binding._h4.clone() : null;
		this._h5 = binding._h5 != null ? binding._h5.clone() : null;
		this._h6 = binding._h6 != null ? binding._h6.clone() : null;
		this._div = binding._div != null ? binding._div.clone() : null;
		this._ul = binding._ul != null ? binding._ul.clone() : null;
		this._ol = binding._ol != null ? binding._ol.clone() : null;
		this._dl = binding._dl != null ? binding._dl.clone() : null;
		this._fieldset = binding._fieldset != null ? binding._fieldset.clone() : null;
		this._table = binding._table != null ? binding._table.clone() : null;
		this._form = binding._form != null ? binding._form.clone() : null;
		this.any = binding.any != null ? binding.any.clone() : null;
		this._input = binding._input != null ? binding._input.clone() : null;
		this._select = binding._select != null ? binding._select.clone() : null;
		this._textarea = binding._textarea != null ? binding._textarea.clone() : null;
		this._label = binding._label != null ? binding._label.clone() : null;
		this._button = binding._button != null ? binding._button.clone() : null;
		this._em = binding._em != null ? binding._em.clone() : null;
		this._strong = binding._strong != null ? binding._strong.clone() : null;
		this._dfn = binding._dfn != null ? binding._dfn.clone() : null;
		this._code = binding._code != null ? binding._code.clone() : null;
		this.q = binding.q != null ? binding.q.clone() : null;
		this._samp = binding._samp != null ? binding._samp.clone() : null;
		this._kbd = binding._kbd != null ? binding._kbd.clone() : null;
		this._var = binding._var != null ? binding._var.clone() : null;
		this._cite = binding._cite != null ? binding._cite.clone() : null;
		this._abbr = binding._abbr != null ? binding._abbr.clone() : null;
		this._acronym = binding._acronym != null ? binding._acronym.clone() : null;
		this._sub = binding._sub != null ? binding._sub.clone() : null;
		this._sup = binding._sup != null ? binding._sup.clone() : null;
		this._tt = binding._tt != null ? binding._tt.clone() : null;
		this.i = binding.i != null ? binding.i.clone() : null;
		this.b = binding.b != null ? binding.b.clone() : null;
		this._big = binding._big != null ? binding._big.clone() : null;
		this._small = binding._small != null ? binding._small.clone() : null;
		this.a = binding.a != null ? binding.a.clone() : null;
		this._br = binding._br != null ? binding._br.clone() : null;
		this._span = binding._span != null ? binding._span.clone() : null;
		this._bdo = binding._bdo != null ? binding._bdo.clone() : null;
		this._map = binding._map != null ? binding._map.clone() : null;
		this._object = binding._object != null ? binding._object.clone() : null;
		this._img = binding._img != null ? binding._img.clone() : null;
	}

	public IFlow(java.lang.String text)
	{
		this.text = text;
	}

	public IFlow()
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

	public void setANY(org.safris.xml.bind.runtime.lang.Binding any)
	{
		this.any = any;
	}

	public org.safris.xml.bind.runtime.lang.Binding getANY()
	{
		return any;
	}

	public void setInput(org.safris.web.depiction.schema.xhtml.strict.Input _input)
	{
		this._input = _input;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Input getInput()
	{
		return _input;
	}

	public void setSelect(org.safris.web.depiction.schema.xhtml.strict.Select _select)
	{
		this._select = _select;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Select getSelect()
	{
		return _select;
	}

	public void setTextarea(org.safris.web.depiction.schema.xhtml.strict.Textarea _textarea)
	{
		this._textarea = _textarea;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Textarea getTextarea()
	{
		return _textarea;
	}

	public void setLabel(org.safris.web.depiction.schema.xhtml.strict.IInline _label)
	{
		this._label = _label;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getLabel()
	{
		return _label;
	}

	public void setButton(org.safris.web.depiction.schema.xhtml.strict.IButtonContent _button)
	{
		this._button = _button;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IButtonContent getButton()
	{
		return _button;
	}

	public void setEm(org.safris.web.depiction.schema.xhtml.strict.IInline _em)
	{
		this._em = _em;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getEm()
	{
		return _em;
	}

	public void setStrong(org.safris.web.depiction.schema.xhtml.strict.IInline _strong)
	{
		this._strong = _strong;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getStrong()
	{
		return _strong;
	}

	public void setDfn(org.safris.web.depiction.schema.xhtml.strict.IInline _dfn)
	{
		this._dfn = _dfn;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getDfn()
	{
		return _dfn;
	}

	public void setCode(org.safris.web.depiction.schema.xhtml.strict.IInline _code)
	{
		this._code = _code;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getCode()
	{
		return _code;
	}

	public void setQ(org.safris.web.depiction.schema.xhtml.strict.IInline q)
	{
		this.q = q;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getQ()
	{
		return q;
	}

	public void setSamp(org.safris.web.depiction.schema.xhtml.strict.IInline _samp)
	{
		this._samp = _samp;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getSamp()
	{
		return _samp;
	}

	public void setKbd(org.safris.web.depiction.schema.xhtml.strict.IInline _kbd)
	{
		this._kbd = _kbd;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getKbd()
	{
		return _kbd;
	}

	public void setVar(org.safris.web.depiction.schema.xhtml.strict.IInline _var)
	{
		this._var = _var;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getVar()
	{
		return _var;
	}

	public void setCite(org.safris.web.depiction.schema.xhtml.strict.IInline _cite)
	{
		this._cite = _cite;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getCite()
	{
		return _cite;
	}

	public void setAbbr(org.safris.web.depiction.schema.xhtml.strict.IInline _abbr)
	{
		this._abbr = _abbr;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getAbbr()
	{
		return _abbr;
	}

	public void setAcronym(org.safris.web.depiction.schema.xhtml.strict.IInline _acronym)
	{
		this._acronym = _acronym;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getAcronym()
	{
		return _acronym;
	}

	public void setSub(org.safris.web.depiction.schema.xhtml.strict.IInline _sub)
	{
		this._sub = _sub;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getSub()
	{
		return _sub;
	}

	public void setSup(org.safris.web.depiction.schema.xhtml.strict.IInline _sup)
	{
		this._sup = _sup;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getSup()
	{
		return _sup;
	}

	public void setTt(org.safris.web.depiction.schema.xhtml.strict.IInline _tt)
	{
		this._tt = _tt;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getTt()
	{
		return _tt;
	}

	public void setI(org.safris.web.depiction.schema.xhtml.strict.IInline i)
	{
		this.i = i;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getI()
	{
		return i;
	}

	public void setB(org.safris.web.depiction.schema.xhtml.strict.IInline b)
	{
		this.b = b;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getB()
	{
		return b;
	}

	public void setBig(org.safris.web.depiction.schema.xhtml.strict.IInline _big)
	{
		this._big = _big;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getBig()
	{
		return _big;
	}

	public void setSmall(org.safris.web.depiction.schema.xhtml.strict.IInline _small)
	{
		this._small = _small;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getSmall()
	{
		return _small;
	}

	public void setA(org.safris.web.depiction.schema.xhtml.strict.IAContent a)
	{
		this.a = a;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IAContent getA()
	{
		return a;
	}

	public void setBr(org.safris.web.depiction.schema.xhtml.strict.Br _br)
	{
		this._br = _br;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Br getBr()
	{
		return _br;
	}

	public void setSpan(org.safris.web.depiction.schema.xhtml.strict.IInline _span)
	{
		this._span = _span;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getSpan()
	{
		return _span;
	}

	public void setBdo(org.safris.web.depiction.schema.xhtml.strict.IInline _bdo)
	{
		this._bdo = _bdo;
	}

	public org.safris.web.depiction.schema.xhtml.strict.IInline getBdo()
	{
		return _bdo;
	}

	public void setMap(org.safris.web.depiction.schema.xhtml.strict.Map _map)
	{
		this._map = _map;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Map getMap()
	{
		return _map;
	}

	public void setObject(org.safris.web.depiction.schema.xhtml.strict.Object _object)
	{
		this._object = _object;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Object getObject()
	{
		return _object;
	}

	public void setImg(org.safris.web.depiction.schema.xhtml.strict.Img _img)
	{
		this._img = _img;
	}

	public org.safris.web.depiction.schema.xhtml.strict.Img getImg()
	{
		return _img;
	}

	protected javax.xml.namespace.QName _getTypeName()
	{
		return NAME;
	}

	protected org.w3c.dom.Element marshal() throws org.safris.xml.bind.runtime.lang.MarshalException, org.safris.xml.bind.runtime.lang.ValidationException
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
			element.appendChild(element.getOwnerDocument().createTextNode(text.toString()));

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

		if(any != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, any, org.safris.xml.bind.runtime.lang.Binding._getName(any), org.safris.xml.bind.runtime.lang.Binding._getTypeName(any)));
		}

		if(_input != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _input, _getName(_input), _getTypeName(_input)));
		}

		if(_select != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _select, _getName(_select), _getTypeName(_select)));
		}

		if(_textarea != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _textarea, _getName(_textarea), _getTypeName(_textarea)));
		}

		if(_label != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _label, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "label"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_button != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _button, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "button"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "button.content")));
		}

		if(_em != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _em, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "em"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_strong != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _strong, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "strong"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_dfn != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _dfn, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "dfn"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_code != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _code, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "code"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(q != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, q, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "q"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_samp != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _samp, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "samp"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_kbd != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _kbd, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "kbd"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_var != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _var, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "var"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_cite != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _cite, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "cite"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_abbr != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _abbr, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "abbr"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_acronym != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _acronym, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "acronym"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_sub != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _sub, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "sub"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_sup != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _sup, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "sup"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_tt != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _tt, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "tt"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(i != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, i, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "i"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(b != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, b, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "b"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_big != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _big, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "big"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_small != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _small, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "small"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(a != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, a, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "a"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "a.content")));
		}

		if(_br != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _br, _getName(_br), _getTypeName(_br)));
		}

		if(_span != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _span, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "span"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_bdo != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _bdo, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "bdo"), new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Inline")));
		}

		if(_map != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _map, _getName(_map), _getTypeName(_map)));
		}

		if(_object != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _object, _getName(_object), _getTypeName(_object)));
		}

		if(_img != null)
		{
			element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _img, _getName(_img), _getTypeName(_img)));
		}

		return element;
	}

	protected void parse(org.w3c.dom.Element element) throws org.safris.xml.bind.runtime.lang.UnmarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.NodeList nodeList = element.getChildNodes();
		org.w3c.dom.Node childNode = null;
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			childNode = nodeList.item(i);
			if(childNode.getNodeType() == org.w3c.dom.Node.TEXT_NODE)
			{
				if(childNode.getNodeValue().length() != 0)
				{
					java.lang.String value = childNode.getNodeValue().trim();
					if(text == null && value.length() != 0)
						text = new java.lang.String(value);
				}
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
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "input".equals(childNode.getLocalName()))
			{
				this._input = (org.safris.web.depiction.schema.xhtml.strict.Input)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Input.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "input"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "select".equals(childNode.getLocalName()))
			{
				this._select = (org.safris.web.depiction.schema.xhtml.strict.Select)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Select.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "select"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "textarea".equals(childNode.getLocalName()))
			{
				this._textarea = (org.safris.web.depiction.schema.xhtml.strict.Textarea)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Textarea.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "textarea"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "label".equals(childNode.getLocalName()))
			{
				this._label = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Label.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "label"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "button".equals(childNode.getLocalName()))
			{
				this._button = (org.safris.web.depiction.schema.xhtml.strict.IButtonContent)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Button.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "button"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "em".equals(childNode.getLocalName()))
			{
				this._em = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Em.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "em"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "strong".equals(childNode.getLocalName()))
			{
				this._strong = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Strong.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "strong"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "dfn".equals(childNode.getLocalName()))
			{
				this._dfn = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Dfn.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "dfn"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "code".equals(childNode.getLocalName()))
			{
				this._code = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Code.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "code"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "q".equals(childNode.getLocalName()))
			{
				this.q = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Q.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "q"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "samp".equals(childNode.getLocalName()))
			{
				this._samp = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Samp.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "samp"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "kbd".equals(childNode.getLocalName()))
			{
				this._kbd = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Kbd.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "kbd"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "var".equals(childNode.getLocalName()))
			{
				this._var = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Var.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "var"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "cite".equals(childNode.getLocalName()))
			{
				this._cite = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Cite.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "cite"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "abbr".equals(childNode.getLocalName()))
			{
				this._abbr = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Abbr.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "abbr"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "acronym".equals(childNode.getLocalName()))
			{
				this._acronym = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Acronym.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "acronym"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "sub".equals(childNode.getLocalName()))
			{
				this._sub = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Sub.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "sub"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "sup".equals(childNode.getLocalName()))
			{
				this._sup = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Sup.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "sup"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "tt".equals(childNode.getLocalName()))
			{
				this._tt = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Tt.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "tt"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "i".equals(childNode.getLocalName()))
			{
				this.i = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.I.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "i"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "b".equals(childNode.getLocalName()))
			{
				this.b = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.B.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "b"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "big".equals(childNode.getLocalName()))
			{
				this._big = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Big.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "big"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "small".equals(childNode.getLocalName()))
			{
				this._small = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Small.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "small"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "a".equals(childNode.getLocalName()))
			{
				this.a = (org.safris.web.depiction.schema.xhtml.strict.IAContent)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.A.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "a"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "br".equals(childNode.getLocalName()))
			{
				this._br = (org.safris.web.depiction.schema.xhtml.strict.Br)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Br.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "br"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "span".equals(childNode.getLocalName()))
			{
				this._span = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Span.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "span"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "bdo".equals(childNode.getLocalName()))
			{
				this._bdo = (org.safris.web.depiction.schema.xhtml.strict.IInline)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Bdo.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "bdo"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "map".equals(childNode.getLocalName()))
			{
				this._map = (org.safris.web.depiction.schema.xhtml.strict.Map)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Map.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "map"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "object".equals(childNode.getLocalName()))
			{
				this._object = (org.safris.web.depiction.schema.xhtml.strict.Object)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Object.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "object"));
				element.removeChild(childNode);
				i--;
			}
			else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "img".equals(childNode.getLocalName()))
			{
				this._img = (org.safris.web.depiction.schema.xhtml.strict.Img)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Img.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "img"));
				element.removeChild(childNode);
				i--;
			}
			else
			{
				this.any = org.safris.xml.bind.runtime.lang.Bindings.unmarshal((org.w3c.dom.Element)childNode);
				element.removeChild(childNode);
				i--;
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.xhtml.strict.IFlow clone()
	{
		return org.safris.web.depiction.schema.xhtml.strict.IFlow.newInstance(this._getName());
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.IFlow))
			return false;

		org.safris.web.depiction.schema.xhtml.strict.IFlow binding = (org.safris.web.depiction.schema.xhtml.strict.IFlow)obj;
		if((this._noscript == null && binding._noscript != null) || (this._noscript != null && !this._noscript.equals(binding._noscript)))
			return false;

		if((this._ins == null && binding._ins != null) || (this._ins != null && !this._ins.equals(binding._ins)))
			return false;

		if((this._del == null && binding._del != null) || (this._del != null && !this._del.equals(binding._del)))
			return false;

		if((this._script == null && binding._script != null) || (this._script != null && !this._script.equals(binding._script)))
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

		if((this.any == null && binding.any != null) || (this.any != null && !this.any.equals(binding.any)))
			return false;

		if((this._input == null && binding._input != null) || (this._input != null && !this._input.equals(binding._input)))
			return false;

		if((this._select == null && binding._select != null) || (this._select != null && !this._select.equals(binding._select)))
			return false;

		if((this._textarea == null && binding._textarea != null) || (this._textarea != null && !this._textarea.equals(binding._textarea)))
			return false;

		if((this._label == null && binding._label != null) || (this._label != null && !this._label.equals(binding._label)))
			return false;

		if((this._button == null && binding._button != null) || (this._button != null && !this._button.equals(binding._button)))
			return false;

		if((this._em == null && binding._em != null) || (this._em != null && !this._em.equals(binding._em)))
			return false;

		if((this._strong == null && binding._strong != null) || (this._strong != null && !this._strong.equals(binding._strong)))
			return false;

		if((this._dfn == null && binding._dfn != null) || (this._dfn != null && !this._dfn.equals(binding._dfn)))
			return false;

		if((this._code == null && binding._code != null) || (this._code != null && !this._code.equals(binding._code)))
			return false;

		if((this.q == null && binding.q != null) || (this.q != null && !this.q.equals(binding.q)))
			return false;

		if((this._samp == null && binding._samp != null) || (this._samp != null && !this._samp.equals(binding._samp)))
			return false;

		if((this._kbd == null && binding._kbd != null) || (this._kbd != null && !this._kbd.equals(binding._kbd)))
			return false;

		if((this._var == null && binding._var != null) || (this._var != null && !this._var.equals(binding._var)))
			return false;

		if((this._cite == null && binding._cite != null) || (this._cite != null && !this._cite.equals(binding._cite)))
			return false;

		if((this._abbr == null && binding._abbr != null) || (this._abbr != null && !this._abbr.equals(binding._abbr)))
			return false;

		if((this._acronym == null && binding._acronym != null) || (this._acronym != null && !this._acronym.equals(binding._acronym)))
			return false;

		if((this._sub == null && binding._sub != null) || (this._sub != null && !this._sub.equals(binding._sub)))
			return false;

		if((this._sup == null && binding._sup != null) || (this._sup != null && !this._sup.equals(binding._sup)))
			return false;

		if((this._tt == null && binding._tt != null) || (this._tt != null && !this._tt.equals(binding._tt)))
			return false;

		if((this.i == null && binding.i != null) || (this.i != null && !this.i.equals(binding.i)))
			return false;

		if((this.b == null && binding.b != null) || (this.b != null && !this.b.equals(binding.b)))
			return false;

		if((this._big == null && binding._big != null) || (this._big != null && !this._big.equals(binding._big)))
			return false;

		if((this._small == null && binding._small != null) || (this._small != null && !this._small.equals(binding._small)))
			return false;

		if((this.a == null && binding.a != null) || (this.a != null && !this.a.equals(binding.a)))
			return false;

		if((this._br == null && binding._br != null) || (this._br != null && !this._br.equals(binding._br)))
			return false;

		if((this._span == null && binding._span != null) || (this._span != null && !this._span.equals(binding._span)))
			return false;

		if((this._bdo == null && binding._bdo != null) || (this._bdo != null && !this._bdo.equals(binding._bdo)))
			return false;

		if((this._map == null && binding._map != null) || (this._map != null && !this._map.equals(binding._map)))
			return false;

		if((this._object == null && binding._object != null) || (this._object != null && !this._object.equals(binding._object)))
			return false;

		if((this._img == null && binding._img != null) || (this._img != null && !this._img.equals(binding._img)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_noscript != null ? _noscript.hashCode() : 0).append("-");
		stringBuffer.append(_ins != null ? _ins.hashCode() : 0).append("-");
		stringBuffer.append(_del != null ? _del.hashCode() : 0).append("-");
		stringBuffer.append(_script != null ? _script.hashCode() : 0).append("-");
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
		stringBuffer.append(any != null ? any.hashCode() : 0).append("-");
		stringBuffer.append(_input != null ? _input.hashCode() : 0).append("-");
		stringBuffer.append(_select != null ? _select.hashCode() : 0).append("-");
		stringBuffer.append(_textarea != null ? _textarea.hashCode() : 0).append("-");
		stringBuffer.append(_label != null ? _label.hashCode() : 0).append("-");
		stringBuffer.append(_button != null ? _button.hashCode() : 0).append("-");
		stringBuffer.append(_em != null ? _em.hashCode() : 0).append("-");
		stringBuffer.append(_strong != null ? _strong.hashCode() : 0).append("-");
		stringBuffer.append(_dfn != null ? _dfn.hashCode() : 0).append("-");
		stringBuffer.append(_code != null ? _code.hashCode() : 0).append("-");
		stringBuffer.append(q != null ? q.hashCode() : 0).append("-");
		stringBuffer.append(_samp != null ? _samp.hashCode() : 0).append("-");
		stringBuffer.append(_kbd != null ? _kbd.hashCode() : 0).append("-");
		stringBuffer.append(_var != null ? _var.hashCode() : 0).append("-");
		stringBuffer.append(_cite != null ? _cite.hashCode() : 0).append("-");
		stringBuffer.append(_abbr != null ? _abbr.hashCode() : 0).append("-");
		stringBuffer.append(_acronym != null ? _acronym.hashCode() : 0).append("-");
		stringBuffer.append(_sub != null ? _sub.hashCode() : 0).append("-");
		stringBuffer.append(_sup != null ? _sup.hashCode() : 0).append("-");
		stringBuffer.append(_tt != null ? _tt.hashCode() : 0).append("-");
		stringBuffer.append(i != null ? i.hashCode() : 0).append("-");
		stringBuffer.append(b != null ? b.hashCode() : 0).append("-");
		stringBuffer.append(_big != null ? _big.hashCode() : 0).append("-");
		stringBuffer.append(_small != null ? _small.hashCode() : 0).append("-");
		stringBuffer.append(a != null ? a.hashCode() : 0).append("-");
		stringBuffer.append(_br != null ? _br.hashCode() : 0).append("-");
		stringBuffer.append(_span != null ? _span.hashCode() : 0).append("-");
		stringBuffer.append(_bdo != null ? _bdo.hashCode() : 0).append("-");
		stringBuffer.append(_map != null ? _map.hashCode() : 0).append("-");
		stringBuffer.append(_object != null ? _object.hashCode() : 0).append("-");
		stringBuffer.append(_img != null ? _img.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}
}