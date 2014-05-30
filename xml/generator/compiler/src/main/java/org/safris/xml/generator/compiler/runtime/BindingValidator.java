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

package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.xml.dom.DOMs;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.sax.SAXProperty;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public final class BindingValidator extends Validator {
  private final Map<String,URL> schemaReferences = new HashMap<String,URL>();

  protected URL lookupSchemaLocation(final String namespaceURI) {
    return schemaReferences.get(namespaceURI);
  }

  protected URL getSchemaLocation(final String namespaceURI) {
    return BindingEntityResolver.lookupSchemaLocation(namespaceURI);
  }

  protected void parse(final Element element) throws IOException, ValidationException {
    final SAXParser saxParser;
    try {
      saxParser = SAXParsers.createParser();

      saxParser.setFeature(SAXFeature.CONTINUE_AFTER_FATAL_ERROR, true);
      saxParser.setFeature(SAXFeature.DYNAMIC_VALIDATION, true);
      saxParser.setFeature(SAXFeature.NAMESPACE_PREFIXES, true);
      saxParser.setFeature(SAXFeature.NAMESPACES, true);
      saxParser.setFeature(SAXFeature.SCHEMA_VALIDATION, true);
      saxParser.setFeature(SAXFeature.VALIDATION, true);

      saxParser.setProptery(SAXProperty.SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema http://www.w3.org/2001/XMLSchema.xsd");
      saxParser.setProptery(SAXProperty.ENTITY_RESOLVER, new BindingEntityResolver());

      saxParser.setErrorHandler(BindingErrorHandler.getInstance());
    }
    catch (final Exception e) {
      throw new ValidationException(e);
    }

    final String output = DOMs.domToString(element);
    try {
      saxParser.parse(new InputSource(new StringReader(output)));
    }
    catch (final IOException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidationException("\n" + e.getMessage() + "\n" + output, e);
    }
  }
}