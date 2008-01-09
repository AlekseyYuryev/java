package org.safris.xml.toolkit.test.binding.regression;

import java.util.ArrayList;
import java.util.List;
import org.safris.xml.schema.binding.test.unit.union.UnFour;
import org.safris.xml.schema.binding.test.unit.union.UnRoot;
import org.safris.xml.schema.binding.test.unit.union.UnSix;
import org.safris.xml.schema.binding.test.unit.union.UnTwo;

public class UnionTest
{
	public static void main(String[] args)
	{
		UnRoot root = new UnRoot();

		UnRoot.UnOne one = new UnRoot.UnOne();
		one.setTEXT(new Integer(9));
		root.addUnOne(one);

		UnTwo two = new UnTwo();
		List twos = new ArrayList();
		twos.add(new Integer(1));
		twos.add("hi");
		two.setTEXT(twos);
		root.addUnTwo(two);

		UnRoot.UnThree three = new UnRoot.UnThree(5);
		root.addUnThree(three);

		UnFour four = new UnFour("text");
		root.addUnFour(four);

		UnRoot.UnFive five = new UnRoot.UnFive(314);
		root.addUnFive(five);

		UnSix six = new UnSix("yeah");
		root.addUnSix(six);

		UnRoot.UnSeven seven = new UnRoot.UnSeven(777);
		root.addUnSeven(seven);
	}
}
