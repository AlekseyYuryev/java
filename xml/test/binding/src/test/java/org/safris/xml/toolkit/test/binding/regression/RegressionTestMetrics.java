package org.safris.xml.toolkit.test.binding.regression;

public class RegressionTestMetrics
{
	private static int testCount = 0;
	private static String smallestXMLDocument = null;
	private static String largestXMLDocument = "";
	
	public static void process(String xml)
	{
		testCount++;
		if(smallestXMLDocument == null || smallestXMLDocument.length() > xml.length())
		{
			smallestXMLDocument = xml;
		}
		if(largestXMLDocument.length() < xml.length())
		{
			largestXMLDocument = xml;
		}
	}
	
	public static int getTestCount()
	{
		return testCount;
	}
	
	public static String getSmallestXMLDocument()
	{
		return smallestXMLDocument;
	}
	
	public static String getLargestXMLDocument()
	{
		return largestXMLDocument;
	}
}
