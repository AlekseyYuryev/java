/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.web.depiction.schema.xhtml.strict;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public abstract class IBlock<T extends org.safris.xml.bind.runtime.lang.ComplexType> extends org.safris.xml.bind.runtime.lang.Binding<T>
{
  private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Block");

  protected static IBlock newInstance(final javax.xml.namespace.QName name)
  {
    return new IBlock()
    {
      protected javax.xml.namespace.QName _getName()
      {
        return name;
      }
    };
  }

  static
  {
    getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.IBlock.class.getName());
  }

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

  public IBlock(org.safris.web.depiction.schema.xhtml.strict.IBlock binding)
  {
    super(binding);
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
    this._noscript = binding._noscript != null ? binding._noscript.clone() : null;
    this._ins = binding._ins != null ? binding._ins.clone() : null;
    this._del = binding._del != null ? binding._del.clone() : null;
    this._script = binding._script != null ? binding._script.clone() : null;
  }

  public IBlock()
  {
    super();
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
    }

    super.parse(element);
  }

  public org.safris.web.depiction.schema.xhtml.strict.IBlock clone()
  {
    return org.safris.web.depiction.schema.xhtml.strict.IBlock.newInstance(this._getName());
  }

  public boolean equals(java.lang.Object obj)
  {
    if(this == obj)
      return true;

    if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.IBlock))
      return false;

    org.safris.web.depiction.schema.xhtml.strict.IBlock binding = (org.safris.web.depiction.schema.xhtml.strict.IBlock)obj;
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

    return super.equals(obj);
  }

  public int hashCode()
  {
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
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
    return stringBuffer.toString().hashCode();
  }
}
