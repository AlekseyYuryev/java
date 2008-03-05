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
		ValidatorMojo.validate(new File("src/test/resources/xml/valid.xml"));
		try
		{
			ValidatorMojo.validate(new File("src/test/resources/xml/invalid.xml"));
		}
		catch(SAXException e)
		{
			if(!"cvc-datatype-valid.1.2.1: 'a' is not a valid value for 'integer'.".equals(e.getMessage()))
				fail(e.getMessage());
		}

		ValidatorMojo.validate(new File("src/test/resources/xsd/test.xsd"));
	}
}
