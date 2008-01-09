package org.safris.xml.toolkit.test.binding.regression;

import java.io.File;
import java.io.FileInputStream;
import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.id.IIdComplexType;
import org.safris.xml.schema.binding.test.unit.id.IIdSimpleType;
import org.safris.xml.schema.binding.test.unit.id.IdRoot;
import org.xml.sax.InputSource;

public class IdTest
{
	public static void main(String[] args) throws Exception
	{
		File file = new File("src/test/resources/xml/id.xml");
		if(!file.exists())
			throw new Error("File " + file.getAbsolutePath() + " does not exist.");

		if(!file.canRead())
			throw new Error("File " + file.getAbsolutePath() + " is not readable.");

		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);
		Bindings.bootstrapConfig(bindingConfig);

		IdRoot parsed = Bindings.<IdRoot>parse(new InputSource(new FileInputStream(file)));

		IdRoot.IdRandom random = new IdRoot.IdRandom("foo");
		random.setTEXT("bar");
		IdRoot marshalled = new IdRoot();
		marshalled.addIdRandom(random);

		IdRoot.IdRandom bar = IdRoot.IdRandom.lookupId("bar");
		IdRoot.IdRandom foo = IdRoot.IdRandom.lookupId("foo");
		IIdSimpleType two = IIdComplexType.IdAttributeAttr.lookupId("two");
		int i = 0;
	}
}
