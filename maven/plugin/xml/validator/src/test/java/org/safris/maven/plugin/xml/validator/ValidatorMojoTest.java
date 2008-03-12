package org.safris.maven.plugin.xml.validator;

import java.io.File;
import org.junit.Test;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;

public class ValidatorMojoTest
{
	public static void main(String[] args) throws Exception
	{
		new ValidatorMojoTest().testValidate();
	}

	@Test
	public void testValidate() throws Exception
	{
		ValidatorMojo.validate(new File("src/test/resources/xml/valid.xml"), null);
		try
		{
			ValidatorMojo.validate(new File("src/test/resources/xml/invalid.xml"), null);
		}
		catch(SAXException e)
		{
			if(!"cvc-datatype-valid.1.2.1: 'a' is not a valid value for 'integer'.".equals(e.getMessage()))
				fail(e.getMessage());
		}

		try
		{
			ValidatorMojo.validate(new File("src/test/resources/xsd/test.xsd"), null);
		}
		catch(SAXException e)
		{
			if(e.getMessage() != null && e.getMessage().startsWith("schema_reference.4: Failed to read schema document 'http://www.w3.org/2001/XMLSchema.xsd'"))
				System.err.println(e.getMessage());
			else
				fail(e.getMessage());
		}
	}
}
