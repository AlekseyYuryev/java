package org.safris.xml.toolkit.test.binding.regression;

import java.io.FileInputStream;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.toolkit.test.binding.regression.Metadata;
import org.xml.sax.InputSource;

public class UnitTest extends Metadata
{
	public static void main(String[] args)
	{
		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);

		Bindings.bootstrapConfig(bindingConfig);
		try
		{
			Binding marchalledBinding = Bindings.parse(new InputSource(new FileInputStream("marshalledOut.txt")));
			Binding unmarchalledBinding = Bindings.parse(new InputSource(new FileInputStream("unmarshalledOut.txt")));
			if(!marchalledBinding.equals(unmarchalledBinding))
			{
				System.err.println("!equals()");
				System.exit(1);
			}

			String marshalledString = marchalledBinding.toString();
			String unmarshalledString = marchalledBinding.toString();
			System.out.println(marshalledString);
			System.out.println("-----------------------------------------");
			System.out.println(unmarshalledString);
			if(marshalledString != null && marshalledString.equals(unmarshalledString))
				System.out.println("PASS");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
