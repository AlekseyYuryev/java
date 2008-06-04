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

import java.io.File;
import java.io.FileInputStream;
import org.junit.Test;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.id.$id_complexType;
import org.safris.xml.schema.binding.test.unit.id.$id_simpleType;
import org.safris.xml.schema.binding.test.unit.id.id_root;
import org.xml.sax.InputSource;

public class IdTest
{
	public static void main(String[] args) throws Exception
	{
		new IdTest().testId();
	}

	@Test
	public void testId() throws Exception
	{
		File file = new File("src/test/resources/xml/unit/id.xml");
		if(!file.exists())
			throw new Error("File " + file.getAbsolutePath() + " does not exist.");

		if(!file.canRead())
			throw new Error("File " + file.getAbsolutePath() + " is not readable.");

		id_root parsed = (id_root)Bindings.parse(new InputSource(new FileInputStream(file)));

		id_root._random random = new id_root._random("foo");
		random.setText("bar");
		id_root marshalled = new id_root();
		marshalled.add_random(random);

		id_root._random bar = id_root._random.lookupId("bar");
		id_root._random foo = id_root._random.lookupId("foo");
		$id_simpleType two = $id_complexType._attribute$.lookupId("two");
	}
}
