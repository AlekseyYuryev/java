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

public class Thumbnail extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
  private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "thumbnail");

  static
  {
    getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.Thumbnail.class.getName());
  }

  private org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr _imgClassAttr = null;
  private org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr _sizeAttr = null;
  private org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr _metadataAttr = null;
  private org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr _baseDirAttr = null;
  private org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr _baseUrlAttr = null;

  public Thumbnail(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
  {
    super(binding);
    if(binding instanceof org.safris.web.depiction.schema.tags.Thumbnail)
    {
      org.safris.web.depiction.schema.tags.Thumbnail element = (org.safris.web.depiction.schema.tags.Thumbnail)binding;
      this._imgClassAttr = element._imgClassAttr != null ? element._imgClassAttr.clone() : null;
      this._sizeAttr = element._sizeAttr != null ? element._sizeAttr.clone() : null;
      this._metadataAttr = element._metadataAttr != null ? element._metadataAttr.clone() : null;
      this._baseDirAttr = element._baseDirAttr != null ? element._baseDirAttr.clone() : null;
      this._baseUrlAttr = element._baseUrlAttr != null ? element._baseUrlAttr.clone() : null;
    }
  }

  public Thumbnail()
  {
    super();
  }

  public void setImgClassAttr(org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr _imgClassAttr)
  {
    this._imgClassAttr = _imgClassAttr;
  }

  public org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr getImgClassAttr()
  {
    return _imgClassAttr;
  }

  public void setSizeAttr(org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr _sizeAttr)
  {
    this._sizeAttr = _sizeAttr;
  }

  public org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr getSizeAttr()
  {
    return _sizeAttr;
  }

  public void setMetadataAttr(org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr _metadataAttr)
  {
    this._metadataAttr = _metadataAttr;
  }

  public org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr getMetadataAttr()
  {
    return _metadataAttr;
  }

  public void setBaseDirAttr(org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr _baseDirAttr)
  {
    this._baseDirAttr = _baseDirAttr;
  }

  public org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr getBaseDirAttr()
  {
    return _baseDirAttr;
  }

  public void setBaseUrlAttr(org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr _baseUrlAttr)
  {
    this._baseUrlAttr = _baseUrlAttr;
  }

  public org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr getBaseUrlAttr()
  {
    return _baseUrlAttr;
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
    if(_imgClassAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_imgClassAttr, "imgClass", element));
    }

    if(_sizeAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_sizeAttr, "size", element));
    }

    if(_metadataAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_metadataAttr, "metadata", element));
    }

    if(_baseDirAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_baseDirAttr, "baseDir", element));
    }

    if(_baseUrlAttr != null)
    {
      element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_baseUrlAttr, "baseUrl", element));
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
      else if("imgClass".equals(attribute.getLocalName()))
      {
        this._imgClassAttr = (org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr.class, element, attribute);
      }
      else if("size".equals(attribute.getLocalName()))
      {
        this._sizeAttr = (org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr.class, element, attribute);
      }
      else if("metadata".equals(attribute.getLocalName()))
      {
        this._metadataAttr = (org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr.class, element, attribute);
      }
      else if("baseDir".equals(attribute.getLocalName()))
      {
        this._baseDirAttr = (org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr.class, element, attribute);
      }
      else if("baseUrl".equals(attribute.getLocalName()))
      {
        this._baseUrlAttr = (org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr.class, element, attribute);
      }
    }

    super.parse(element);
  }

  public org.safris.web.depiction.schema.tags.Thumbnail clone()
  {
    return new org.safris.web.depiction.schema.tags.Thumbnail(this);
  }

  public boolean equals(java.lang.Object obj)
  {
    if(this == obj)
      return true;

    if(!(obj instanceof org.safris.web.depiction.schema.tags.Thumbnail))
      return false;

    org.safris.web.depiction.schema.tags.Thumbnail binding = (org.safris.web.depiction.schema.tags.Thumbnail)obj;
    if((this._imgClassAttr == null && binding._imgClassAttr != null) || (this._imgClassAttr != null && !this._imgClassAttr.equals(binding._imgClassAttr)))
      return false;

    if((this._sizeAttr == null && binding._sizeAttr != null) || (this._sizeAttr != null && !this._sizeAttr.equals(binding._sizeAttr)))
      return false;

    if((this._metadataAttr == null && binding._metadataAttr != null) || (this._metadataAttr != null && !this._metadataAttr.equals(binding._metadataAttr)))
      return false;

    if((this._baseDirAttr == null && binding._baseDirAttr != null) || (this._baseDirAttr != null && !this._baseDirAttr.equals(binding._baseDirAttr)))
      return false;

    if((this._baseUrlAttr == null && binding._baseUrlAttr != null) || (this._baseUrlAttr != null && !this._baseUrlAttr.equals(binding._baseUrlAttr)))
      return false;

    return super.equals(obj);
  }

  public int hashCode()
  {
    java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
    stringBuffer.append(_imgClassAttr != null ? _imgClassAttr.hashCode() : 0).append("-");
    stringBuffer.append(_sizeAttr != null ? _sizeAttr.hashCode() : 0).append("-");
    stringBuffer.append(_metadataAttr != null ? _metadataAttr.hashCode() : 0).append("-");
    stringBuffer.append(_baseDirAttr != null ? _baseDirAttr.hashCode() : 0).append("-");
    stringBuffer.append(_baseUrlAttr != null ? _baseUrlAttr.hashCode() : 0).append("-");
    return stringBuffer.toString().hashCode();
  }

  /**
   * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
  */

  public static class ImgClassAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "imgClass");

    public ImgClassAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public ImgClassAttr(java.lang.String value)
    {
      super(value);
    }

    protected ImgClassAttr()
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

    public org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Thumbnail.ImgClassAttr(this);
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
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "size");

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

    public org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Thumbnail.SizeAttr(this);
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

  public static class MetadataAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "metadata");

    public MetadataAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public MetadataAttr(java.lang.String value)
    {
      super(value);
    }

    protected MetadataAttr()
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

    public org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Thumbnail.MetadataAttr(this);
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

  public static class BaseDirAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "baseDir");

    public BaseDirAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public BaseDirAttr(java.lang.String value)
    {
      super(value);
    }

    protected BaseDirAttr()
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

    public org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Thumbnail.BaseDirAttr(this);
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

  public static class BaseUrlAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
  {
    private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "baseUrl");

    public BaseUrlAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
    {
      super(binding);
    }

    public BaseUrlAttr(java.lang.String value)
    {
      super(value);
    }

    protected BaseUrlAttr()
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

    public org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr clone()
    {
      return new org.safris.web.depiction.schema.tags.Thumbnail.BaseUrlAttr(this);
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
