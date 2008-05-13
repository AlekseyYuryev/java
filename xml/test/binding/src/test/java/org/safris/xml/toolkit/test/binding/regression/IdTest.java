package org.safris.xml.toolkit.test.binding.regression;

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
