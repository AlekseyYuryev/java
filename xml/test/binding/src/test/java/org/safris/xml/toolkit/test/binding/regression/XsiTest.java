package org.safris.xml.toolkit.test.binding.regression;

import com.safris.schema.test.ITeComplexA;
import com.safris.schema.test.ITeComplexD;
import com.safris.schema.test.TeElemD;
import java.io.StringReader;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.toolkit.test.binding.regression.Metadata;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.xml.sax.InputSource;

public class XsiTest extends Metadata
{
	private static final String DEFAULT_HOST = "aol-3";
	private static final String DEFAULT_DOMAIN = "liberty-iop.biz";
	private static String host = DEFAULT_HOST;
	private static String domain = DEFAULT_DOMAIN;

	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			host = args[0];
			domain = args[1];
		}

		ITeComplexD xsiType = new ITeComplexD()
		{
			protected ITeComplexA inherits()
			{
				return null;
			}
		};
		xsiType.setTeA_attr1Attr(new ITeComplexD.TeA_attr1Attr(RegressionTest.getRandomString()));
		xsiType.setTeA_attr2Attr(new ITeComplexD.TeA_attr2Attr(RegressionTest.getRandomString()));
		xsiType.setTeC_attr1Attr(new ITeComplexD.TeC_attr1Attr(RegressionTest.getRandomString()));
		xsiType.setTeC_attr2Attr(new ITeComplexD.TeC_attr2Attr(RegressionTest.getRandomString()));
		xsiType.setTeD_attr1Attr(new ITeComplexD.TeD_attr1Attr(RegressionTest.getRandomString()));
		xsiType.setTeD_attr2Attr(new ITeComplexD.TeD_attr2Attr(RegressionTest.getRandomString()));

		TeElemD elemD = new TeElemD();
		elemD.setTeElemC(xsiType);

		try
		{
			String marshalled = elemD.toString();
			Binding binding = Bindings.parse(new InputSource(new StringReader(marshalled)));
			String remarshalled = binding.toString();
			System.out.println(marshalled);
			System.out.println("-----------------------------------------");
			System.out.println(remarshalled);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
