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
 * document base URI
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public class Base extends org.safris.xml.bind.runtime.lang.Binding<org.safris.xml.bind.runtime.lang.ComplexType>
{
  private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "base");

  static
  {
    getElementBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.Base.class.getName());
  }

  private org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr _hrefAttr = null;
  private org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr _idAttr = null;

  public Base(org.safris.web.depiction.schema.xhtml.strict.Base binding)
  {
    super(binding);
    if(binding instanceof org.safris.web.depiction.schema.xhtml.strict.Base)
    {
      org.safris.web.depiction.schema.xhtml.strict.Base element = (org.safris.web.depiction.schema.xhtml.strict.Base)binding;
      this._hrefAttr = element._hrefAttr != null ? element._hrefAttr.clone() : null;
      this._idAttr = element._idAttr != null ? element._idAttr.clone() : null;
    }
  }

  public Base()
  {
    super();
  }

  public void setHrefAttr(org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr _hrefAttr)
  {
    this._hrefAttr = _hrefAttr;
  }

  public org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr getHrefAttr()
  {
    return _hrefAttr;
  }

  public void setIdAttr(org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr _idAttr)
  {
    this._idAttr = _idAttr;
  }

  public org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr getIdAttr()
  {
    return _idAttr;
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
    if(_hrefAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_hrefAttr, "href", element));
    }

    if(_idAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_idAttr, "id", element));
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
      else if("href".equals(attribute.getLocalName()))
      {
        this._hrefAttr = (org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr.class, element, attribute);
      }
      else if("id".equals(attribute.getLocalName()))
      {
        this._idAttr = (org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr.class, element, attribute);
      }
    }

    super.parse(element);
  }

  public org.safris.web.depiction.schema.xhtml.strict.Base clone()
  {
    return new org.safris.web.depiction.schema.xhtml.strict.Base(this);
  }

  public boolean equals(java.lang.Object obj)
  {
    if(this == obj)
      return true;

    if(!(obj instanceof org.safris.web.depiction.schema.xhtml.strict.Base))
      return false;

    org.safris.web.depiction.schema.xhtml.strict.Base binding = (org.safris.web.depiction.schema.xhtml.strict.Base)obj;
    if((this._hrefAttr == null && binding._hrefAttr != null) || (this._hrefAttr != null && !this._hrefAttr.equals(binding._hrefAttr)))
      return false;

    if((this._idAttr == null && binding._idAttr != null) || (this._idAttr != null && !this._idAttr.equals(binding._idAttr)))
      return false;

    return super.equals(obj);
  }

  public int hashCode()
  {
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
    stringBuffer.append(_hrefAttr != null ? _hrefAttr.hashCode() : 0).append("-");
    stringBuffer.append(_idAttr != null ? _idAttr.hashCode() : 0).append("-");
    return stringBuffer.toString().hashCode();
  }

  /**
   * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
  */

  public static class HrefAttr extends org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "href");

    public HrefAttr(org.safris.web.depiction.schema.xhtml.strict.IURI<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public HrefAttr(java.lang.String value)
    {
      super(value);
    }

    protected HrefAttr()
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

    public org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr clone()
    {
      return new org.safris.web.depiction.schema.xhtml.strict.Base.HrefAttr(this);
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

    public org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr clone()
    {
      return new org.safris.web.depiction.schema.xhtml.strict.Base.IdAttr(this);
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
