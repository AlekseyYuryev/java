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

package org.safris.xml.toolkit.test.binding;

import org.junit.Test;
import org.safris.commons.xml.XMLException;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.$sg_twentyType;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.sg_evenTen;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.sg_evenTwenty;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.sg_oddTen;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.sg_oddTwenty;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.sg_root;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.sg_twentyImpl;

public class SubstitutionGroupTest
{
	public static void main(String[] args) throws XMLException
	{
		new SubstitutionGroupTest().testSubstitutionGroup();
	}

	@Test
	public void testSubstitutionGroup() throws XMLException
	{
		sg_root root = new sg_root();

		$sg_twentyType twentyImpl = new sg_twentyImpl(13);
		root.addsg_twenty(twentyImpl);

		$sg_twentyType oddTwenty = new sg_oddTwenty(sg_oddTwenty._19);
		root.addsg_twenty(oddTwenty);

		$sg_twentyType evenTwenty = new sg_evenTwenty(sg_evenTwenty._18);
		root.addsg_twenty(evenTwenty);

		$sg_twentyType oddTen = new sg_oddTen(sg_oddTen._9);
		root.addsg_twenty(oddTen);

		$sg_twentyType evenTen = new sg_evenTen(sg_evenTen._8);
		root.addsg_twenty(evenTen);

		System.out.println(DOMs.domToString(Bindings.marshal(root), DOMStyle.INDENT));
	}
}
