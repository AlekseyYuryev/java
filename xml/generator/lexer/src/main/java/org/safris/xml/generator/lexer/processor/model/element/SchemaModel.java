/*  Copyright Safris Software 2008
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

package org.safris.xml.generator.lexer.processor.model.element;

import java.io.File;
import java.net.URL;

import org.safris.commons.io.Files;
import org.safris.commons.xml.NamespaceURI;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.schema.attribute.BlockDefault;
import org.safris.xml.generator.lexer.schema.attribute.FinalDefault;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class SchemaModel extends Model {
  private Form attributeFormDefault = Form.UNQUALIFIED;
  private BlockDefault blockDefault = null;
  private Form elementFormDefault = Form.UNQUALIFIED;
  private FinalDefault finalDefault = null;
  private String lang = null;
  private NamespaceURI targetNamespace = null;
  private String version = null;
  private URL url = null;

  protected SchemaModel(final Node node, final Model parent) {
    // NOTE: A SchemaModel does not have a parent.
    super(node, null);
    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("attributeFormDefault".equals(attribute.getLocalName()))
        attributeFormDefault = Form.parseForm(attribute.getNodeValue());
      else if ("blockDefault".equals(attribute.getLocalName()))
        blockDefault = BlockDefault.parseBlockDefault(attribute.getNodeValue());
      else if ("elementFormDefault".equals(attribute.getLocalName()))
        elementFormDefault = Form.parseForm(attribute.getNodeValue());
      else if ("finalDefault".equals(attribute.getLocalName()))
        finalDefault = FinalDefault.parseFinalDefault(attribute.getNodeValue());
      else if ("lang".equals(attribute.getLocalName()))
        lang = attribute.getNodeValue();
      else if ("targetNamespace".equals(attribute.getLocalName()))
        setTargetNamespace(NamespaceURI.getInstance(attribute.getNodeValue()));
      else if ("version".equals(attribute.getLocalName()))
        version = attribute.getNodeValue();
    }
  }

  public final void setURL(final URL url) {
    this.url = url;
    final String display = Files.relativePath(Files.getCwd().getAbsoluteFile(), new File(url.getFile()).getAbsoluteFile());
    logger.info("Scanning {" + getTargetNamespace() + "} from " + display);
  }

  public final URL getURL() {
    return url;
  }

  public final Form getAttributeFormDefault() {
    return attributeFormDefault;
  }

  public final BlockDefault getBlockDefault() {
    return blockDefault;
  }

  public final Form getElementFormDefault() {
    return elementFormDefault;
  }

  public final FinalDefault getFinalDefault() {
    return finalDefault;
  }

  public final String getLang() {
    return lang;
  }

  public final void setTargetNamespace(final NamespaceURI targetNamespace) {
    if (targetNamespace == null)
      throw new IllegalArgumentException("targetNamespace == null");

    this.targetNamespace = targetNamespace;
  }

  public final NamespaceURI getTargetNamespace() {
    return targetNamespace;
  }

  public final String getVersion() {
    return version;
  }
  
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;
    
    if (!(obj instanceof SchemaModel))
      return false;
    
    final SchemaModel that = (SchemaModel)obj;
    return (targetNamespace != null ? targetNamespace.equals(that.targetNamespace) : that.targetNamespace == null) && (url != null ? url.equals(that.url) : that.url == null); 
  }
  
  public int hashCode() {
    return (targetNamespace != null ? targetNamespace.hashCode() : -7) * (url != null ? url.hashCode() : -9);
  }

  public String toString() {
    return UniqueQName.XS.getNamespaceURI() + " " + targetNamespace;
  }
}