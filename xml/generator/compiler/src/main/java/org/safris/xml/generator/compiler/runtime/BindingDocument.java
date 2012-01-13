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

package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.net.URL;
import org.safris.commons.xml.validator.ValidationException;
import org.xml.sax.InputSource;

public class BindingDocument {
  private final URL url;
  private final Binding document;

  public BindingDocument(URL url) throws IOException, ParseException, ValidationException {
    this.url = url;
    url.openConnection();
    document = Bindings.parse(new InputSource(url.openStream()));
  }

  public Binding getDocument() {
    return document;
  }

  public URL getURL() {
    return url;
  }
}
