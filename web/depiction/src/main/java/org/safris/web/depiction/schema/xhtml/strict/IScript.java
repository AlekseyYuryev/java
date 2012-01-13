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
 * script expression
 *
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
 */

public abstract class IScript<T extends org.safris.xml.bind.runtime.lang.BindingType> extends org.safris.xml.bind.runtime.types.StringType<T>
{
  private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://www.w3.org/1999/xhtml", "Script");

  protected static IScript newInstance(final javax.xml.namespace.QName name)
  {
    return new IScript()
    {
      protected javax.xml.namespace.QName _getName()
      {
        return name;
      }
    };
  }

  static
  {
    getTypeBindings().put(NAME, org.safris.web.depiction.schema.xhtml.strict.IScript.class.getName());
  }

  protected IScript(org.safris.xml.bind.runtime.types.StringType binding)
  {
    super(binding);
  }

  protected IScript(java.lang.String value)
  {
    super(value);
  }

  protected IScript()
  {
    super();
  }

  public void setTEXT(java.lang.String text)
  {
    super.setTEXT(text);
  }

  public java.lang.String getTEXT()
  {
    return super.getTEXT();
  }

  protected javax.xml.namespace.QName _getTypeName()
  {
    return NAME;
  }

  protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
  {
    return super.marshalAttr(name, parent);
  }

  protected org.w3c.dom.Element marshal() throws org.safris.xml.bind.runtime.lang.MarshalException, org.safris.xml.bind.runtime.lang.ValidationException
  {
    org.w3c.dom.Element root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());
    return marshal(root, _getName(), _getTypeName());
  }

  protected org.w3c.dom.Element marshal(org.w3c.dom.Element parent, javax.xml.namespace.QName name, javax.xml.namespace.QName typeName) throws org.safris.xml.bind.runtime.lang.MarshalException
  {
    return super.marshal(parent, name, typeName);
  }

  public org.safris.web.depiction.schema.xhtml.strict.IScript clone()
  {
    return new org.safris.web.depiction.schema.xhtml.strict.IScript(this)
    {
      protected javax.xml.namespace.QName _getName()
      {
        return _getName();
      }
    };
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
