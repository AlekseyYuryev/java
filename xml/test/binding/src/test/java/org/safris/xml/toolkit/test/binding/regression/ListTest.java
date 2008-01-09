package org.safris.xml.toolkit.test.binding.regression;

import java.util.ArrayList;
import java.util.List;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.schema.binding.test.unit.list.ITestListSimpleTypeFive;
import org.safris.xml.schema.binding.test.unit.list.ITestListSimpleTypeFour;
import org.safris.xml.schema.binding.test.unit.list.ITestListSimpleTypeOne;
import org.safris.xml.schema.binding.test.unit.list.ITestListSimpleTypeThree;
import org.safris.xml.schema.binding.test.unit.list.ITestListSimpleTypeTwo;
import org.w3.x2001.xmlschema.IXSByte;
import org.w3.x2001.xmlschema.IXSNCName;
import org.w3.x2001.xmlschema.IXSQName;

public class ListTest
{
	public static void main(String[] args)
	{
		ITestListSimpleTypeOne one = null;
		List<ITestListSimpleTypeOne.RESTRICTION> restrictions1 = new ArrayList<ITestListSimpleTypeOne.RESTRICTION>();
		restrictions1.add(ITestListSimpleTypeOne.TEST1);
		one.setTEXT(restrictions1);

		ITestListSimpleTypeTwo two = null;
		List<ITestListSimpleTypeTwo.RESTRICTION> restrictions2 = new ArrayList<ITestListSimpleTypeTwo.RESTRICTION>();
		restrictions2.add(ITestListSimpleTypeTwo.TEST2);
		two.setTEXT(restrictions2);

		ITestListSimpleTypeThree three = null;
		List<IXSNCName> threes = new ArrayList<IXSNCName>();
		threes.add(new IXSNCName()
		{
			protected Binding inherits()
			{
				// TODO: Implement this method
				return null;
			}
		});
		three.setTEXT(threes);

		ITestListSimpleTypeFour four = null;
		List<IXSQName> fours = new ArrayList<IXSQName>();
		fours.add(new IXSQName()
		{
			protected Binding inherits()
			{
				// TODO: Implement this method
				return null;
			}
		});
		four.setTEXT(fours);

		ITestListSimpleTypeFive five = null;
		List<IXSByte> fives = new ArrayList<IXSByte>();
		fives.add(new IXSByte()
		{
			protected Binding inherits()
			{
				// TODO: Implement this method
				return null;
			}
		});
		five.setTEXT(fives);
	}
}
