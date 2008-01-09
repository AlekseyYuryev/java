package org.safris.xml.toolkit.test.binding.regression;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import liberty_id_sis_pp_2003_08.PpQuery;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XPathTest
{
	public static void main(String[] args)
	{
		String xpath = args[0];
		try
		{
			PpQuery.PpQueryItem queryItem = new PpQuery.PpQueryItem();
//			queryItem.setSelect(new QueryType.QueryItem.Select(xpath));

			PpQuery query = new PpQuery();
			query.addPpQueryItem(queryItem);

			Element element = query.marshal();
			System.out.println(Bindings.domToString(element));

			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			/*			NodeIterator nodeIterator = XPathAPI.selectNodeIterator(element, xpath);
			 Node node = null;
			 while((node = nodeIterator.nextNode())!= null)
			 {
			 if(isTextNode(node))
			 {
			 // DOM may have more than one node corresponding to a
			 // single XPath text node.  Coalesce all contiguous text nodes
			 // at this level
			 StringBuffer stringBuffer = new StringBuffer(node.getNodeValue());
			 for(Node nextNode = node.getNextSibling(); isTextNode(nextNode); nextNode = nextNode.getNextSibling())
			 {
			 stringBuffer.append(nextNode.getNodeValue());
			 }
			 System.out.print(stringBuffer);
			 }
			 else
			 {
			 serializer.transform(new DOMSource(node), new StreamResult(new OutputStreamWriter(System.out)));
			 }
			 }*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	static boolean isTextNode(Node n)
	{
		if(n == null)
			return false;
		short nodeType = n.getNodeType();
		return nodeType == Node.CDATA_SECTION_NODE || nodeType == Node.TEXT_NODE;
	}
}
