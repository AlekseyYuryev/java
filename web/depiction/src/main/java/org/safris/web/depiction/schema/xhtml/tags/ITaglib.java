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

package org.safris.web.depiction.schema.xhtml.tags;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public abstract class ITaglib<T extends org.safris.xml.bind.runtime.lang.ComplexType> extends org.safris.xml.bind.runtime.lang.Binding<T>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/xhtml1-tags.xsd", "taglib");

	protected static ITaglib newInstance(final javax.xml.namespace.QName name)
	{
		return new ITaglib()
		{
			protected javax.xml.namespace.QName _getName()
			{
				return name;
			}
		};
	}

	static
	{
		getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.tags.ITaglib.class.getName());
	}

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

	public ITaglib(org.safris.web.depiction.schema.xhtml.tags.ITaglib binding)
	{
		super(binding);
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

	public ITaglib()
	{
		super();
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
			if(childNode == null || childNode.getNodeType() == org.w3c.dom.Node.TEXT_NODE)
			{
				continue;
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

	public org.safris.web.depiction.schema.xhtml.tags.ITaglib clone()
	{
		return org.safris.web.depiction.schema.xhtml.tags.ITaglib.newInstance(this._getName());
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.xhtml.tags.ITaglib))
			return false;

		org.safris.web.depiction.schema.xhtml.tags.ITaglib binding = (org.safris.web.depiction.schema.xhtml.tags.ITaglib)obj;
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