package org.safris.commons.xml;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.Test;
import org.safris.commons.xml.NamespaceBinding;

public class NamespaceBindingTest extends TestCase
{
	public static void main(String[] args)
	{
		testNamespaceToPackage();
	}

	@Test
	public static void testNamespaceToPackage()
	{
		final Map<String,String> map = new HashMap<String,String>();
		map.put("http://www.w3.org/2001/XMLSchema", "org.w3.x2001.xmlschema");
		map.put("http://java.sun.com/xml/ns/j2ee", "com.sun.java.xml.ns.j2ee");
		map.put("http://openuri.org/nameworld", "org.openuri.nameworld");
		map.put("http://schemas.xmlsoap.org/soap/envelope/", "org.xmlsoap.schemas.soap.envelope");
		map.put("http://www.safris.com/schema/test", "com.safris.schema.test");
		map.put("http://www.w3.org/1999/xhtml", "org.w3.x1999.xhtml");
		map.put("http://www.w3.org/1999/xlink", "org.w3.x1999.xlink");
		map.put("http://www.w3.org/2000/09/xmldsig#", "org.w3.x2000.x09.xmldsig");
		map.put("http://www.w3.org/2001/04/xmlenc#", "org.w3.x2001.x04.xmlenc");
		map.put("http://www.w3.org/2001/10/xml-exc-c14n#", "org.w3.x2001.x10.xmlexcc14n");
		map.put("http://www.w3.org/2001/XMLSchema", "org.w3.x2001.xmlschema");
		map.put("http://www.w3.org/2001/XMLSchema", "org.w3.x2001.xmlschema");
		map.put("http://www.w3.org/XML/1998/namespace", "org.w3.xml.x1998.namespace");
		map.put("http://xml.safris.org/schema/binding/test/unit/attributes.xsd", "org.safris.xml.schema.binding.test.unit.attributes");
		map.put("http://xml.safris.org/schema/binding/test/unit/complexTypes.xsd", "org.safris.xml.schema.binding.test.unit.complextypes");
		map.put("http://xml.safris.org/schema/binding/test/unit/everything.xsd", "org.safris.xml.schema.binding.test.unit.everything");
		map.put("http://xml.safris.org/schema/binding/test/unit/lists.xsd", "org.safris.xml.schema.binding.test.unit.lists");
		map.put("http://xml.safris.org/schema/binding/test/unit/mixed.xsd", "org.safris.xml.schema.binding.test.unit.mixed");
		map.put("http://xml.safris.org/schema/binding/test/unit/namespace.xsd", "org.safris.xml.schema.binding.test.unit.namespace");
		map.put("http://xml.safris.org/schema/binding/test/unit/simpleTypes.xsd", "org.safris.xml.schema.binding.test.unit.simpletypes");
		map.put("http://xml.safris.org/schema/binding/test/unit/types.xsd", "org.safris.xml.schema.binding.test.unit.types");
		map.put("http://xml.safris.org/schema/binding/xdb.xsd", "org.safris.xml.schema.binding.xdb");
		map.put("http://xml.safris.org/toolkit/binding/manifest.xsd", "org.safris.xml.toolkit.binding.manifest");
		map.put("http://xml.safris.org/toolkit/binding/test/maven.xsd", "org.safris.xml.toolkit.binding.test.maven");
		map.put("http://xml.safris.org/toolkit/binding/tutorial/invoice.xsd", "org.safris.xml.toolkit.binding.tutorial.invoice");
		map.put("http://xml.safris.org/toolkit/sample/binding/any.xsd", "org.safris.xml.toolkit.sample.binding.any");
		map.put("http://xml.safris.org/toolkit/sample/binding/any.xsd", "org.safris.xml.toolkit.sample.binding.any");
		map.put("http://xml.safris.org/toolkit/sample/binding/enums.xsd", "org.safris.xml.toolkit.sample.binding.enums");
		map.put("http://xml.safris.org/toolkit/sample/binding/enums.xsd", "org.safris.xml.toolkit.sample.binding.enums");
		map.put("http://xml.safris.org/toolkit/sample/binding/simple.xsd", "org.safris.xml.toolkit.sample.binding.simple");
		map.put("http://xml.safris.org/toolkit/sample/binding/simple.xsd", "org.safris.xml.toolkit.sample.binding.simple");
		map.put("http://xml.safris.org/toolkit/sample/binding/xsitype.xsd", "org.safris.xml.toolkit.sample.binding.xsitype");
		map.put("http://xml.safris.org/toolkit/sample/binding/xsitype.xsd", "org.safris.xml.toolkit.sample.binding.xsitype");
		map.put("http://xml.safris.org/toolkit/tutorial/binding/beginner/invoice.xsd", "org.safris.xml.toolkit.tutorial.binding.beginner.invoice");
		map.put("test-namespace-targetNamespace", "test_namespace_targetnamespace");
		map.put("urn:aol:liberty:config", "aol_liberty_config");
		map.put("urn:berkeley:safris:game:chess", "berkeley_safris_game_chess");
		map.put("urn:liberty:ac:2003-08", "liberty_ac_2003_08");
		map.put("urn:liberty:ac:2004-12", "liberty_ac_2004_12");
		map.put("urn:liberty:disco:2003-08", "liberty_disco_2003_08");
		map.put("urn:liberty:id-sis-pp:2003-08", "liberty_id_sis_pp_2003_08");
		map.put("urn:liberty:iff:2003-08", "liberty_iff_2003_08");
		map.put("urn:liberty:metadata:2003-08", "liberty_metadata_2003_08");
		map.put("urn:liberty:sb:2003-08", "liberty_sb_2003_08");
		map.put("urn:oasis:names:tc:SAML:1.0:assertion", "_0_assertion.oasis_names_tc_saml_1");
		map.put("urn:oasis:names:tc:SAML:1.0:protocol", "_0_protocol.oasis_names_tc_saml_1");
		map.put("xhttp://www.safris.com/schema/testtwo", "com.safris.schema.testtwo");

		for(Map.Entry<String,String> entry : map.entrySet())
		{
			final String pkg = NamespaceBinding.getPackageFromNamespace(entry.getKey());
			assertEquals(pkg, entry.getValue());
		}
	}
}