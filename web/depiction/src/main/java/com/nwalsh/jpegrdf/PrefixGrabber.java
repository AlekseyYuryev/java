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
