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
 * content model is "head.misc" combined with a single title and an optional base
 * element in any order
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public class Head extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
  private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "head");

  static
  {
    getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Head.class.getName());
  }

  private org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr _idAttr = null;
  private org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr _profileAttr = null;
  private LangAttr _langAttr = null;
  private org.safris.web.depiction.schema.xml.namespace.XmlLangAttr _xmlLangAttr = null;
  private DirAttr _dirAttr = null;
  private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Script,org.safris.xml.bind.runtime.lang.ComplexType> _script = null;
  private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Style,org.safris.xml.bind.runtime.lang.ComplexType> _style = null;
  private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Meta,org.safris.xml.bind.runtime.lang.ComplexType> _meta = null;
  private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Link,org.safris.xml.bind.runtime.lang.ComplexType> _link = null;
  private org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Object,org.safris.xml.bind.runtime.lang.ComplexType> _object = null;
  private org.safris.web.depiction.schema.xhtml.strict.Title _title = null;
  private org.safris.web.depiction.schema.xhtml.strict.Base _base = null;

  public Head(org.safris.web.depiction.schema.xhtml.strict.Head binding)
  {
    super(binding);
    if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Head)
    {
      org.safris.web.depiction.schema.xhtml.strict.Head element = (org.safris.web.depiction.schema.xhtml.strict.Head)binding;
      this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
      this._profileAttr = element._profileAttr != null ? element._profileAttr.clone() : null;
      this._langAttr = element._langAttr != null ? element._langAttr.clone() : null;
      this._xmlLangAttr = element._xmlLangAttr != null ? element._xmlLangAttr.clone() : null;
      this._dirAttr = element._dirAttr != null ? element._dirAttr.clone() : null;
      this._script = element._script != null ? element._script.clone() : null;
      this._style = element._style != null ? element._style.clone() : null;
      this._meta = element._meta != null ? element._meta.clone() : null;
      this._link = element._link != null ? element._link.clone() : null;
      this._object = element._object != null ? element._object.clone() : null;
      this._title = element._title != null ? element._title.clone() : null;
      this._base = element._base != null ? element._base.clone() : null;
    }
  }

  public Head()
  {
    super();
  }

  public void setIdAttr(org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr _idAttr)
  {
    this._idAttr = _idAttr;
  }

  public org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr getIdAttr()
  {
    return _idAttr;
  }

  public void setProfileAttr(org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr _profileAttr)
  {
    this._profileAttr = _profileAttr;
  }

  public org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr getProfileAttr()
  {
    return _profileAttr;
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

  public void addScript(org.safris.web.depiction.schema.xhtml.strict.Script _script)
  {
    if(this._script == null)
      this._script = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Script,org.safris.xml.bind.runtime.lang.ComplexType>();

    this._script.add(_script);
  }

  public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Script,org.safris.xml.bind.runtime.lang.ComplexType> getScript()
  {
    return _script;
  }

  public void addStyle(org.safris.web.depiction.schema.xhtml.strict.Style _style)
  {
    if(this._style == null)
      this._style = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Style,org.safris.xml.bind.runtime.lang.ComplexType>();

    this._style.add(_style);
  }

  public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Style,org.safris.xml.bind.runtime.lang.ComplexType> getStyle()
  {
    return _style;
  }

  public void addMeta(org.safris.web.depiction.schema.xhtml.strict.Meta _meta)
  {
    if(this._meta == null)
      this._meta = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Meta,org.safris.xml.bind.runtime.lang.ComplexType>();

    this._meta.add(_meta);
  }

  public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Meta,org.safris.xml.bind.runtime.lang.ComplexType> getMeta()
  {
    return _meta;
  }

  public void addLink(org.safris.web.depiction.schema.xhtml.strict.Link _link)
  {
    if(this._link == null)
      this._link = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Link,org.safris.xml.bind.runtime.lang.ComplexType>();

    this._link.add(_link);
  }

  public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Link,org.safris.xml.bind.runtime.lang.ComplexType> getLink()
  {
    return _link;
  }

  public void addObject(org.safris.web.depiction.schema.xhtml.strict.Object _object)
  {
    if(this._object == null)
      this._object = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Object,org.safris.xml.bind.runtime.lang.ComplexType>();

    this._object.add(_object);
  }

  public org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Object,org.safris.xml.bind.runtime.lang.ComplexType> getObject()
  {
    return _object;
  }

  public void setTitle(org.safris.web.depiction.schema.xhtml.strict.Title _title)
  {
    this._title = _title;
  }

  public org.safris.web.depiction.schema.xhtml.strict.Title getTitle()
  {
    return _title;
  }

  public void setBase(org.safris.web.depiction.schema.xhtml.strict.Base _base)
  {
    this._base = _base;
  }

  public org.safris.web.depiction.schema.xhtml.strict.Base getBase()
  {
    return _base;
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

    if(_profileAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_profileAttr, "profile", element));
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

    if(_script != null)
    {
      for(org.safris.web.depiction.schema.xhtml.strict.Script _script : this._script)
        element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _script, _getName(_script), _getTypeName(_script)));
    }

    if(_style != null)
    {
      for(org.safris.web.depiction.schema.xhtml.strict.Style _style : this._style)
        element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _style, _getName(_style), _getTypeName(_style)));
    }

    if(_meta != null)
    {
      for(org.safris.web.depiction.schema.xhtml.strict.Meta _meta : this._meta)
        element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _meta, _getName(_meta), _getTypeName(_meta)));
    }

    if(_link != null)
    {
      for(org.safris.web.depiction.schema.xhtml.strict.Link _link : this._link)
        element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _link, _getName(_link), _getTypeName(_link)));
    }

    if(_object != null)
    {
      for(org.safris.web.depiction.schema.xhtml.strict.Object _object : this._object)
        element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _object, _getName(_object), _getTypeName(_object)));
    }

    if(_title != null)
    {
      element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _title, _getName(_title), _getTypeName(_title)));
    }

    if(_base != null)
    {
      element.appendChild(org.safris.xml.bind.runtime.lang.Binding.marshal(element, _base, _getName(_base), _getTypeName(_base)));
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
        this._idAttr = (org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr.class, element, attribute);
      }
      else if("profile".equals(attribute.getLocalName()))
      {
        this._profileAttr = (org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr.class, element, attribute);
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
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "script".equals(childNode.getLocalName()))
      {
        if(this._script == null)
          this._script = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Script,org.safris.xml.bind.runtime.lang.ComplexType>();

        this._script.add((org.safris.web.depiction.schema.xhtml.strict.Script)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Script.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "script")));
        element.removeChild(childNode);
        i--;
      }
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "style".equals(childNode.getLocalName()))
      {
        if(this._style == null)
          this._style = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Style,org.safris.xml.bind.runtime.lang.ComplexType>();

        this._style.add((org.safris.web.depiction.schema.xhtml.strict.Style)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Style.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "style")));
        element.removeChild(childNode);
        i--;
      }
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "meta".equals(childNode.getLocalName()))
      {
        if(this._meta == null)
          this._meta = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Meta,org.safris.xml.bind.runtime.lang.ComplexType>();

        this._meta.add((org.safris.web.depiction.schema.xhtml.strict.Meta)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Meta.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "meta")));
        element.removeChild(childNode);
        i--;
      }
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "link".equals(childNode.getLocalName()))
      {
        if(this._link == null)
          this._link = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Link,org.safris.xml.bind.runtime.lang.ComplexType>();

        this._link.add((org.safris.web.depiction.schema.xhtml.strict.Link)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Link.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "link")));
        element.removeChild(childNode);
        i--;
      }
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "object".equals(childNode.getLocalName()))
      {
        if(this._object == null)
          this._object = new org.safris.xml.bind.runtime.lang.CloneableList<org.safris.web.depiction.schema.xhtml.strict.Object,org.safris.xml.bind.runtime.lang.ComplexType>();

        this._object.add((org.safris.web.depiction.schema.xhtml.strict.Object)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Object.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "object")));
        element.removeChild(childNode);
        i--;
      }
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "title".equals(childNode.getLocalName()))
      {
        this._title = (org.safris.web.depiction.schema.xhtml.strict.Title)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Title.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "title"));
        element.removeChild(childNode);
        i--;
      }
      else if("http://www.w3.org/1999/xhtml".equals(childNode.getNamespaceURI()) && "base".equals(childNode.getLocalName()))
      {
        this._base = (org.safris.web.depiction.schema.xhtml.strict.Base)org.safris.xml.bind.runtime.lang.Binding.unmarshal((org.w3c.dom.Element)childNode, org.safris.web.depiction.schema.xhtml.strict.Base.class, new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "base"));
        element.removeChild(childNode);
        i--;
      }
    }

    super.parse(element);
  }

  public org.safris.web.depiction.schema.xhtml.strict.Head clone()
  {
    return new org.safris.web.depiction.schema.xhtml.strict.Head(this);
  }

  public boolean equals(java.lang.Object obj)
  {
    if(this == obj)
      return true;

    if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Head))
      return false;

    org.safris.web.depiction.schema.xhtml.strict.Head binding = (org.safris.web.depiction.schema.xhtml.strict.Head)obj;
    if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
      return false;

    if((this._profileAttr == null && binding._profileAttr != null) || (this._profileAttr != null && !this._profileAttr.equals(binding._profileAttr)))
      return false;

    if((this._langAttr == null && binding._langAttr != null) || (this._langAttr != null && !this._langAttr.equals(binding._langAttr)))
      return false;

    if((this._xmlLangAttr == null && binding._xmlLangAttr != null) || (this._xmlLangAttr != null && !this._xmlLangAttr.equals(binding._xmlLangAttr)))
      return false;

    if((this._dirAttr == null && binding._dirAttr != null) || (this._dirAttr != null && !this._dirAttr.equals(binding._dirAttr)))
      return false;

    if((this._script == null && binding._script != null) || (this._script != null && !this._script.equals(binding._script)))
      return false;

    if((this._style == null && binding._style != null) || (this._style != null && !this._style.equals(binding._style)))
      return false;

    if((this._meta == null && binding._meta != null) || (this._meta != null && !this._meta.equals(binding._meta)))
      return false;

    if((this._link == null && binding._link != null) || (this._link != null && !this._link.equals(binding._link)))
      return false;

    if((this._object == null && binding._object != null) || (this._object != null && !this._object.equals(binding._object)))
      return false;

    if((this._title == null && binding._title != null) || (this._title != null && !this._title.equals(binding._title)))
      return false;

    if((this._base == null && binding._base != null) || (this._base != null && !this._base.equals(binding._base)))
      return false;

    return super.equals(obj);
  }

  public int hashCode()
  {
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
    stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
    stringBuffer.append(_profileAttr != null ? _profileAttr.hashCode() : 0).append("-");
    stringBuffer.append(_langAttr != null ? _langAttr.hashCode() : 0).append("-");
    stringBuffer.append(_xmlLangAttr != null ? _xmlLangAttr.hashCode() : 0).append("-");
    stringBuffer.append(_dirAttr != null ? _dirAttr.hashCode() : 0).append("-");
    stringBuffer.append(_script != null ? _script.hashCode() : 0).append("-");
    stringBuffer.append(_style != null ? _style.hashCode() : 0).append("-");
    stringBuffer.append(_meta != null ? _meta.hashCode() : 0).append("-");
    stringBuffer.append(_link != null ? _link.hashCode() : 0).append("-");
    stringBuffer.append(_object != null ? _object.hashCode() : 0).append("-");
    stringBuffer.append(_title != null ? _title.hashCode() : 0).append("-");
    stringBuffer.append(_base != null ? _base.hashCode() : 0).append("-");
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

    public org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr clone()
    {
      return new org.safris.web.depiction.schema.xhtml.strict.Head.IdAttr(this);
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

  public static class ProfileAttr extends org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "profile");

    public ProfileAttr(org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public ProfileAttr(java.lang.String value)
    {
      super(value);
    }

    protected ProfileAttr()
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

    public org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr clone()
    {
      return new org.safris.web.depiction.schema.xhtml.strict.Head.ProfileAttr(this);
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
}
