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

package org.safris.commons.xml.sax;

import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class SAXParser {
  private final XMLReader xmlReader;

  protected SAXParser(XMLReader xmlReader) {
    this.xmlReader = xmlReader;
  }

  public void setFeature(SAXFeature feature, boolean value) {
    try {
      xmlReader.setFeature(feature.getFeature(), value);
    }
    catch (SAXNotRecognizedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
    catch (SAXNotSupportedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
  }

  public boolean getFeature(SAXFeature feature) {
    try {
      return xmlReader.getFeature(feature.getFeature());
    }
    catch (SAXNotRecognizedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
    catch (SAXNotSupportedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
  }

  public void setProptery(SAXProperty property, Object value) {
    try {
      xmlReader.setProperty(property.getProperty(), value);
    }
    catch (SAXNotRecognizedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
    catch (SAXNotSupportedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
  }

  public Object getProperty(SAXProperty property) {
    try {
      return xmlReader.getProperty(property.getProperty());
    }
    catch (SAXNotRecognizedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
    catch (SAXNotSupportedException e) {
      // FIXME: Remove this!
      throw new Error(e);
    }
  }

  public void setEntityResolver(EntityResolver resolver) {
    xmlReader.setEntityResolver(resolver);
  }

  public EntityResolver getEntityResolver() {
    return xmlReader.getEntityResolver();
  }

  public void setDTDHandler(DTDHandler handler) {
    xmlReader.setDTDHandler(handler);
  }

  public DTDHandler getDTDHandler() {
    return xmlReader.getDTDHandler();
  }

  public void setContentHandler(ContentHandler handler) {
    xmlReader.setContentHandler(handler);
  }

  public ContentHandler getContentHandler() {
    return xmlReader.getContentHandler();
  }

  public void setErrorHandler(ErrorHandler handler) {
    xmlReader.setErrorHandler(handler);
  }

  public ErrorHandler getErrorHandler() {
    return xmlReader.getErrorHandler();
  }

  public void parse(InputSource input) throws IOException, SAXException {
    xmlReader.parse(input);
  }

  public void parse(String systemId) throws IOException, SAXException {
    xmlReader.parse(systemId);
  }
}
