package org.safris.xml.generator.compiler.runtime.lang;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.safris.xml.generator.compiler.runtime.lang.DateTime;
import org.safris.xml.generator.compiler.runtime.lang.DateTimeTest;

public class DateTimeTest extends TestCase
	{
	private static final Map<String,String> map = new HashMap<String,String>();
	
	static
		{
		map.put("2002-10-10T12:00:00+05:00", "2002-10-10T07:00:00Z");
		map.put("2002-10-10T00:00:00+05:00", "2002-10-09T19:00:00Z");
		map.put("1999-10-10T00:00:00-00:00", "1999-10-10T00:00:00Z");
		map.put("2000-03-04T23:00:00-03:00", "2000-03-04T26:00:00Z");
		map.put("2000-03-04T23:00:00-03:45", "2000-03-04T26:45:00Z");
		map.put("2007-06-20T20:43:23.887Z", "2007-06-20T25:43:23.887+05:00");
	map.put("2001-10-26T21:32:52.12679", "2001-10-26T21:32:52.12679");
	}
	
	public static void main(String[] args)
		{
	new DateTimeTest().testCase();
	}
	
	public void testCase()
		{
			for(Map.Entry<String,String> entry : map.entrySet())
	regressionTest(entry.getKey(), entry.getValue());
	}
	
	private void regressionTest(String s1, String s2)
		{
		DateTime dateTime1 = DateTime.parseDateTime(s1);
		DateTime dateTime2 = DateTime.parseDateTime(s2);
		
		regressionTest(dateTime1, dateTime1);
		regressionTest(dateTime2, dateTime2);
	regressionTest(dateTime1, dateTime2);
	}
	
	private void regressionTest(DateTime dateTime1, DateTime dateTime2)
		{
			if(dateTime1.equals(dateTime2))
		return;
		
	fail("for: [ " + dateTime1 + " ], [ " + dateTime2 + " ]\ngot: ![ " + dateTime1 + " ].equals([ " + dateTime2 + " ])\n\t" + dateTime1.getTime() + "\n\t" + dateTime2.getTime());
}
}
