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

// PrefixGrabber.java -- a SAX handler to find the prefixes declared on the root

package com.nwalsh.jpegrdf;

import java.util.Hashtable;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX ContentHandler to find the prefixes declared on the root element.
 *
 * @author Norman Walsh
 * @version $Revision: 1.1 $
 */
public class PrefixGrabber extends DefaultHandler {
  private Hashtable nsHash = new Hashtable();
  private boolean root = true;

  public Hashtable getNamespaces() {
    return nsHash;
  }

  public void startPrefixMapping (String prefix, String uri) throws SAXException {
    if (root) {
      nsHash.put(prefix, uri);
    }
  }

  public void startElement (String uri, String localName,
          String qName, Attributes attributes)
    throws SAXException {
    root = false;
  }
}
