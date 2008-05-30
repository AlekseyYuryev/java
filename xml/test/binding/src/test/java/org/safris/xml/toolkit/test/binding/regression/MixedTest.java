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

import org.junit.Test;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.mixed._root;

public class MixedTest
{
	private static String createString()
	{
		return "";
	}

	public static void main(String[] args)
	{
		new MixedTest().testMixed();
	}

	@Test
	public void testMixed()
	{
		_root._zero zero = new _root._zero();

		_root._oneEx oneEx = new _root._oneEx(createString());
		oneEx.setText(createString());

		_root._oneRe oneRe = new _root._oneRe();			// FIXME: This should NOT be mixed

		_root._two two = new _root._two("mixed");
		two.setText("mixed");

		_root._three three = new _root._three(createString());
		three.setText(createString());

		_root._four four = new _root._four(createString());
		four.setText(createString());

		_root._five five = new _root._five();

		_root._six six = new _root._six(createString());
		six.setText(createString());

		_root._seven seven = new _root._seven();

		_root._eight eight = new _root._eight("mixed");
		eight.setText("mixed");

		_root._ten ten = new _root._ten();

		_root._elevenEx elevenEx = new _root._elevenEx(createString());
		elevenEx.setText(createString());

		_root._elevenRe elevenRe = new _root._elevenRe();	// FIXME: This should NOT be mixed

		_root._twelve twelve = new _root._twelve("mixed");
		twelve.setText("mixed");

		_root._thirteen thirteen = new _root._thirteen(createString());
		thirteen.setText(createString());

		_root._fourteen fourteen = new _root._fourteen(createString());
		fourteen.setText(createString());

		_root._fifteen fifteen = new _root._fifteen();

		_root._sixteen sixteen = new _root._sixteen(createString());
		sixteen.setText(createString());

		_root._seventeen seventeen = new _root._seventeen();

		_root._eighteen eighteen = new _root._eighteen("mixed");
		eighteen.setText("mixed");

		_root._twenty twenty = new _root._twenty();

		_root._twentyOne twentyOne = new _root._twentyOne();

		_root._twentyTwo twentyTwo = new _root._twentyTwo("mixed");
		twentyTwo.setText("mixed");

		_root._twentyThree twentyThree = new _root._twentyThree(createString());
		twentyThree.setText(createString());

		_root._twentyFour twentyFour = new _root._twentyFour(createString());
		twentyFour.setText(createString());

		_root._twentyFive twentyFive = new _root._twentyFive();

		_root._twentySix twentySix = new _root._twentySix(createString());
		twentySix.setText(createString());

		_root._twentySeven twentySeven = new _root._twentySeven();

		_root._twentyEight twentyEight = new _root._twentyEight("mixed");
		twentyEight.setText("mixed");

		_root._thirty thirty = new _root._thirty();

		_root._thirtyOne thirtyOne = new _root._thirtyOne();

		_root._thirtyTwo thirtyTwo = new _root._thirtyTwo("mixed");
		thirtyTwo.setText("mixed");

		_root._thirtyThree thirtyThree = new _root._thirtyThree(createString());
		thirtyThree.setText(createString());

		_root._thirtyFour thirtyFour = new _root._thirtyFour(createString());
		thirtyFour.setText(createString());

		_root._thirtyFive thirtyFive = new _root._thirtyFive();

		_root._thirtySix thirtySix = new _root._thirtySix(createString());
		thirtySix.setText(createString());

		_root._thirtySeven thirtySeven = new _root._thirtySeven();

		_root._thirtyEight thirtyEight = new _root._thirtyEight("mixed");
		thirtyEight.setText("mixed");

		_root root = new _root();
		root.add_zero(zero);
		root.add_oneEx(oneEx);
		root.add_oneRe(oneRe);
		root.add_two(two);
		root.add_three(three);
		root.add_four(four);
		root.add_five(five);
		root.add_six(six);
		root.add_seven(seven);
		root.add_eight(eight);
		root.add_ten(ten);
		root.add_elevenEx(elevenEx);
		root.add_elevenRe(elevenRe);
		root.add_twelve(twelve);
		root.add_thirteen(thirteen);
		root.add_fourteen(fourteen);
		root.add_fifteen(fifteen);
		root.add_sixteen(sixteen);
		root.add_seventeen(seventeen);
		root.add_eighteen(eighteen);
		root.add_twenty(twenty);
		root.add_twentyOne(twentyOne);
		root.add_twentyTwo(twentyTwo);
		root.add_twentyThree(twentyThree);
		root.add_twentyFour(twentyFour);
		root.add_twentyFive(twentyFive);
		root.add_twentySix(twentySix);
		root.add_twentySeven(twentySeven);
		root.add_twentyEight(twentyEight);
		root.add_thirty(thirty);
		root.add_thirtyOne(thirtyOne);
		root.add_thirtyTwo(thirtyTwo);
		root.add_thirtyThree(thirtyThree);
		root.add_thirtyFour(thirtyFour);
		root.add_thirtyFive(thirtyFive);
		root.add_thirtySix(thirtySix);
		root.add_thirtySeven(thirtySeven);
		root.add_thirtyEight(thirtyEight);

		try
		{
			System.out.println(DOMs.domToString(Bindings.marshal(root), DOMStyle.INDENT));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
