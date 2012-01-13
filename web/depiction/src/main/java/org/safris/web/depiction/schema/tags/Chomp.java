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

package org.safris.web.depiction.schema.tags;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class Chomp extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
  private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "chomp");

  static
  {
    getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.Chomp.class.getName());
  }

  private org.safris.web.depiction.schema.tags.Chomp.PathAttr _pathAttr = null;
  private org.safris.web.depiction.schema.tags.Chomp.NameAttr _nameAttr = null;
  private org.safris.web.depiction.schema.tags.Chomp.VarAttr _varAttr = null;

  public Chomp(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
  {
    super(binding);
    if(binding instanceof org.safris.web.depiction.schema.tags.Chomp)
    {
      org.safris.web.depiction.schema.tags.Chomp element = (org.safris.web.depiction.schema.tags.Chomp)binding;
      this._pathAttr = element._pathAttr != null ? element._pathAttr.clone() : null;
      this._nameAttr = element._nameAttr != null ? element._nameAttr.clone() : null;
      this._varAttr = element._varAttr != null ? element._varAttr.clone() : null;
    }
  }

  public Chomp()
  {
    super();
  }

  public void setPathAttr(org.safris.web.depiction.schema.tags.Chomp.PathAttr _pathAttr)
  {
    this._pathAttr = _pathAttr;
  }

  public org.safris.web.depiction.schema.tags.Chomp.PathAttr getPathAttr()
  {
    return _pathAttr;
  }

  public void setNameAttr(org.safris.web.depiction.schema.tags.Chomp.NameAttr _nameAttr)
  {
    this._nameAttr = _nameAttr;
  }

  public org.safris.web.depiction.schema.tags.Chomp.NameAttr getNameAttr()
  {
    return _nameAttr;
  }

  public void setVarAttr(org.safris.web.depiction.schema.tags.Chomp.VarAttr _varAttr)
  {
    this._varAttr = _varAttr;
  }

  public org.safris.web.depiction.schema.tags.Chomp.VarAttr getVarAttr()
  {
    return _varAttr;
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
    if(_pathAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_pathAttr, "path", element));
    }

    if(_nameAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_nameAttr, "name", element));
    }

    if(_varAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_varAttr, "var", element));
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
      else if("path".equals(attribute.getLocalName()))
      {
        this._pathAttr = (org.safris.web.depiction.schema.tags.Chomp.PathAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Chomp.PathAttr.class, element, attribute);
      }
      else if("name".equals(attribute.getLocalName()))
      {
        this._nameAttr = (org.safris.web.depiction.schema.tags.Chomp.NameAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Chomp.NameAttr.class, element, attribute);
      }
      else if("var".equals(attribute.getLocalName()))
      {
        this._varAttr = (org.safris.web.depiction.schema.tags.Chomp.VarAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Chomp.VarAttr.class, element, attribute);
      }
    }

    super.parse(element);
  }

  public org.safris.web.depiction.schema.tags.Chomp clone()
  {
    return new org.safris.web.depiction.schema.tags.Chomp(this);
  }

  public boolean equals(java.lang.Object obj)
  {
    if(this == obj)
      return true;

    if(!(obj instanceof org.safris.web.depiction.schema.tags.Chomp))
      return false;

    org.safris.web.depiction.schema.tags.Chomp binding = (org.safris.web.depiction.schema.tags.Chomp)obj;
    if((this._pathAttr == null && binding._pathAttr != null) || (this._pathAttr != null && !this._pathAttr.equals(binding._pathAttr)))
      return false;

    if((this._nameAttr == null && binding._nameAttr != null) || (this._nameAttr != null && !this._nameAttr.equals(binding._nameAttr)))
      return false;

    if((this._varAttr == null && binding._varAttr != null) || (this._varAttr != null && !this._varAttr.equals(binding._varAttr)))
      return false;

    return super.equals(obj);
  }

  public int hashCode()
  {
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
    stringBuffer.append(_pathAttr != null ? _pathAttr.hashCode() : 0).append("-");
    stringBuffer.append(_nameAttr != null ? _nameAttr.hashCode() : 0).append("-");
    stringBuffer.append(_varAttr != null ? _varAttr.hashCode() : 0).append("-");
    return stringBuffer.toString().hashCode();
  }

  /**
   * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
  */

  public static class PathAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "path");

    public PathAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public PathAttr(java.lang.String value)
    {
      super(value);
    }

    protected PathAttr()
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

    public org.safris.web.depiction.schema.tags.Chomp.PathAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Chomp.PathAttr(this);
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

  public static class NameAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "name");

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

    public org.safris.web.depiction.schema.tags.Chomp.NameAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Chomp.NameAttr(this);
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

  public static class VarAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "var");

    public VarAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public VarAttr(java.lang.String value)
    {
      super(value);
    }

    protected VarAttr()
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

    public org.safris.web.depiction.schema.tags.Chomp.VarAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Chomp.VarAttr(this);
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
