/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.toolkit.test.binding.regression;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.schema.binding.test.unit.list.$_test_list_simpleType_five;
import org.safris.xml.schema.binding.test.unit.list.$_test_list_simpleType_four;
import org.safris.xml.schema.binding.test.unit.list.$_test_list_simpleType_one;
import org.safris.xml.schema.binding.test.unit.list.$_test_list_simpleType_three;
import org.safris.xml.schema.binding.test.unit.list.$_test_list_simpleType_two;
import org.w3.x2001.xmlschema.$xs_NCName;
import org.w3.x2001.xmlschema.$xs_byte;

public class ListTest
{
	public static void main(String[] args)
	{
		new ListTest().testList();
	}

	@Test
	public void testList()
	{
		$_test_list_simpleType_one one = new $_test_list_simpleType_one()
		{
			protected $_test_list_simpleType_one inherits()
			{
				// TODO: Implement this method
				return null;
			}
		};
		List<$_test_list_simpleType_one.RESTRICTION> restrictions1 = new ArrayList<$_test_list_simpleType_one.RESTRICTION>();
		restrictions1.add($_test_list_simpleType_one.TEST1);
		one.setText(restrictions1);

		$_test_list_simpleType_two two = new $_test_list_simpleType_two()
		{
			protected $_test_list_simpleType_two inherits()
			{
				// TODO: Implement this method
				return null;
			}
		};
		List<$_test_list_simpleType_two.RESTRICTION> restrictions2 = new ArrayList<$_test_list_simpleType_two.RESTRICTION>();
		restrictions2.add($_test_list_simpleType_two.TEST2);
		two.setText(restrictions2);

		$_test_list_simpleType_three three = new $_test_list_simpleType_three()
		{
			protected $_test_list_simpleType_three inherits()
			{
				// TODO: Implement this method
				return null;
			}
		};
		List<$xs_NCName> threes = new ArrayList<$xs_NCName>();
		threes.add(new $xs_NCName()
		{
			protected Binding inherits()
			{
				// TODO: Implement this method
				return null;
			}
		});
		three.setText(threes);

		$_test_list_simpleType_four four = new $_test_list_simpleType_four()
		{
			protected $_test_list_simpleType_four inherits()
			{
				// TODO: Implement this method
				return null;
			}
		};
		List<$xs_NCName> fours = new ArrayList<$xs_NCName>();
		fours.add(new $xs_NCName()
		{
			protected Binding inherits()
			{
				// TODO: Implement this method
				return null;
			}
		});
		four.setText(fours);

		$_test_list_simpleType_five five = new $_test_list_simpleType_five()
		{
			protected $_test_list_simpleType_five inherits()
			{
				// TODO: Implement this method
				return null;
			}
		};
		List<$xs_byte> fives = new ArrayList<$xs_byte>();
		fives.add(new $xs_byte()
		{
			protected Binding inherits()
			{
				// TODO: $mplement this method
				return null;
			}
		});
		five.setText(fives);
	}
}
