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

package org.safris.xml.toolkit.test.binding.regression;

import com.sun.org.apache.xpath.internal.XPathAPI;
import java.io.OutputStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import liberty_id_sis_pp_2003_08.pp_Query;
import org.junit.Ignore;
import org.junit.Test;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class XPathTest {
  private static String xpath = null;

  protected static boolean isTextNode(Node node) {
    if (node == null)
      return false;

    final short nodeType = node.getNodeType();
    return nodeType == Node.CDATA_SECTION_NODE || nodeType == Node.TEXT_NODE;
  }

  public static void main(String[] args) throws Exception {
    xpath = args[0];
    new XPathTest().testXPath();
  }

  @Ignore("Finish implementing this test!")
  @Test
  public void testXPath() throws Exception {
    pp_Query._QueryItem queryItem = new pp_Query._QueryItem();
//      queryItem.setSelect(new QueryType.QueryItem.Select(xpath));

    pp_Query query = new pp_Query();
    query.add_QueryItem(queryItem);

    Element element = query.marshal();
    System.out.println(DOMs.domToString(element, DOMStyle.INDENT));

    Transformer serializer = TransformerFactory.newInstance().newTransformer();
    serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

    NodeIterator nodeIterator = XPathAPI.selectNodeIterator(element, xpath);
    Node node = null;
    while ((node = nodeIterator.nextNode()) != null) {
      if (isTextNode(node)) {
        // DOM may have more than one node corresponding to a
        // single XPath text node.  Coalesce all contiguous text nodes
        // at this level
        final StringBuffer stringBuffer = new StringBuffer(node.getNodeValue());
        for (Node nextNode = node.getNextSibling(); isTextNode(nextNode); nextNode = nextNode.getNextSibling())
          stringBuffer.append(nextNode.getNodeValue());

        System.out.print(stringBuffer);
      }
      else {
        serializer.transform(new DOMSource(node), new StreamResult(new OutputStreamWriter(System.out)));
      }
    }
  }
}
