package org.safris.xml.toolkit.test.binding.regression;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.lang.Base64Binary;
import org.safris.xml.generator.compiler.runtime.lang.DateTime;
import org.safris.xml.generator.compiler.runtime.lang.Duration;
import org.safris.xml.generator.compiler.runtime.lang.HexBinary;
import org.safris.xml.generator.compiler.util.DefaultValidator;
import org.safris.xml.generator.compiler.util.Validator;
import org.safris.xml.generator.lexer.module.model.element.AnyAttributeModel;
import org.w3.x2001.xmlschema.IXSIDREF;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import sun.misc.BASE64Encoder;

public abstract class RegressionTest
{
	protected static final float ADD_SEED = 0.001f;
	protected static final float CHOICE_SEED = .5f;
	protected static final float RECURSION_SEED = 0.001f;
	private static boolean verifiable = true;
	private static final List<Class<? extends RegressionTest>> anyList = new LinkedList<Class<? extends RegressionTest>>();

	static
	{
		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);
		Bindings.bootstrapConfig(bindingConfig);

		anyList.add(AcRegressionTest.class);
		anyList.add(DscRegressionTest.class);
		anyList.add(DsRegressionTest.class);
//		anyList.add(IdppRegressionTest.class);
		anyList.add(LibRegressionTest.class);
		anyList.add(MdRegressionTest.class);
		anyList.add(SamlpRegressionTest.class);
		anyList.add(SamlRegressionTest.class);
		anyList.add(SbRegressionTest.class);
		anyList.add(TnsRegressionTest.class);

		Validator validator = new DefaultValidator();
		validator.setValidateOnMarshal(true);
		Validator.setSystemValidator(validator);
	}

	public static void setVerifiable(boolean verifiable)
	{
		RegressionTest.verifiable = verifiable;
	}

	public static boolean isVerifiable()
	{
		return verifiable;
	}

	protected static final Base64Binary getBase64Binary()
	{
		return new Base64Binary(getRandomString().getBytes());
	}

	protected static final HexBinary getHexBinary()
	{
		return new HexBinary(getRandomString().getBytes());
	}

	protected static final String getRandomString()
	{
		String random = new BASE64Encoder().encode(Integer.toString((int)(Math.random() * 1000000000)).getBytes());
		return random.substring(0, random.length() - 3).toLowerCase();
	}

	protected static final List<String> getRandomStrings()
	{
		String random = new BASE64Encoder().encode(Integer.toString((int)(Math.random() * 1000000000)).getBytes());
		int length = (int)(Math.random() * 10);
		List<String> list = new ArrayList<String>(length);
		for(int i = 0; i < length; i++)
			list.add(random.substring(0, random.length() - 3).toLowerCase());

		return list;
	}

	protected static final List<IXSIDREF> getRandomIDRefTypes()
	{
		String random = new BASE64Encoder().encode(Integer.toString((int)(Math.random() * 1000000000)).getBytes());
		int length = (int)(Math.random() * 10);
		List<IXSIDREF> list = new ArrayList<IXSIDREF>(length);
		for(int i = 0; i < length; i++)
			list.add(new IXSIDREF(random.substring(0, random.length() - 3).toLowerCase())
			{
				protected Binding inherits()
				{
					return null;
				}
			});

		return list;
	}

	protected static final QName getRandomQName()
	{
		Class cls = anyList.get((int)(Math.random() % anyList.size()));
		String namespaceURI = null;
		try
		{
			namespaceURI = (String)cls.getDeclaredMethod("getNamespaceURI").invoke(null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return new QName(namespaceURI, getRandomString());
	}

	protected static final Integer getRandomInteger()
	{
		return new Integer((int)(Math.random() * 1000000000));
	}

	protected static final Integer getRandomNonNegativeInteger()
	{
		return new Integer(Math.abs((int)(Math.random() * 1000000000)));
	}

	protected static final Boolean getRandomBoolean()
	{
		return new Boolean(Math.random() < 0.5);
	}

	protected static final DateTime getRandomDateTime()
	{
		return new DateTime();
	}

	protected static final Duration getRandomDuration()
	{
		double random = Math.random();
		if(random < 1 / 6)
			return new Duration(false, getRandomInteger() % 10);
		else if(random < 2 / 6)
			return new Duration(false, getRandomInteger() % 10, getRandomInteger() % 12);
		else if(random < 3 / 6)
			return new Duration(false, getRandomInteger() % 10, getRandomInteger() % 12, getRandomInteger() % 28);
		else if(random < 4 / 6)
			return new Duration(false, getRandomInteger() % 10, getRandomInteger() % 12, getRandomInteger() % 28, getRandomInteger() % 24);
		else if(random < 5 / 6)
			return new Duration(false, getRandomInteger() % 10, getRandomInteger() % 12, getRandomInteger() % 28, getRandomInteger() % 24, getRandomInteger() % 60);
		else
			return new Duration(false, getRandomInteger() % 10, getRandomInteger() % 12, getRandomInteger() % 28, getRandomInteger() % 24, getRandomInteger() % 60, getRandomInteger() % 60);
	}

	public final Binding getAny()
	{
		if(anyList == null || anyList.size() == 0)
			throw new IllegalArgumentException("getAnyList() == null || getAnyList().size() == 0");

		int classMod = (int)(Math.random() * (float)anyList.size());
		Class<? extends RegressionTest> bindingClass = anyList.get(classMod);
		if(getClass().isAssignableFrom(bindingClass))
			return getAny();

		Method[] methods = bindingClass.getDeclaredMethods();
		Method method = null;
		do
		{
			int methodMod = (int)(Math.random() * (float)methods.length);
			method = methods[methodMod];
		}
		while(method != null && !method.getName().startsWith("get"));
		// NOTE: "final" distinguishes the getAny method.
		if(Binding.class.isAssignableFrom(method.getReturnType()) && method.getParameterTypes().length == 0 && !Modifier.isFinal(method.getModifiers()) && Modifier.isStatic(method.getModifiers()))
		{
			try
			{
				return (Binding)method.invoke(null);
			}
			catch(Exception e)
			{
				// Caused by OutOfMemoryError or StackOverflowError
				e.getCause().printStackTrace();
				System.exit(1);
			}
		}
		// NOTE: This makes sure that an Element is returned.
		return getAny();
	}

	public final AnyAttributeModel getAnyAttribute(String namespaceURI)
	{
		// FIXME: Implement this!!!
		return null;
//		return new AnyAttributeParser(getRandomQName(), getRandomQName());
	}

	protected static final void verify(Binding binding)
	{
		Throwable throwable = null;

		String marshalledString = null;
		String remarshalledString = null;
		Binding remarshalledBinding = null;
		try
		{
			Method marshalMethod = binding.getClass().getMethod("marshal");
			Element marshalledElement = (Element)marshalMethod.invoke(binding);
			marshalledString = Bindings.domToString(marshalledElement);

			remarshalledBinding = Bindings.parse(new InputSource(new StringReader(marshalledString)));
			Element remarshalledElement = (Element)binding.getClass().getMethod("marshal").invoke(remarshalledBinding);
			remarshalledString = Bindings.domToString(remarshalledElement);
		}
		catch(Exception e)
		{
			if(e.getCause() != null && e.getCause().getStackTrace() != null && e.getCause().getStackTrace().length != 0)
				throwable = e.getCause();
			else
				throwable = e;
		}

		//System.out.println("------------------------------------------------");
		boolean stringNotEqual = true;
		boolean objectNotEqual = true;
		if(throwable != null || (stringNotEqual = !marshalledString.equals(remarshalledString)) || (objectNotEqual = !binding.equals(remarshalledBinding)))
		{
			try
			{
				if(marshalledString != null)
					System.out.println(marshalledString);

				if(marshalledString != null)
				{
					FileOutputStream marshalledOut = new FileOutputStream("marshalledOut.txt");
					marshalledOut.write(marshalledString.getBytes());
					marshalledOut.close();
				}

				if(throwable != null)
				{
					throwable.printStackTrace();
				}

				System.out.println("================================================");
				System.out.println(remarshalledString);
				if(remarshalledString != null)
				{
					FileOutputStream remarshalledOut = new FileOutputStream("unmarshalledOut.txt");
					remarshalledOut.write(remarshalledString.getBytes());
					remarshalledOut.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

			if(stringNotEqual)
				System.err.println("[INFO] stringNotEqual = true");

			if(objectNotEqual)
			{
				binding.equals(remarshalledBinding);
				System.err.println("[INFO] objectNotEqual = true");
			}

			System.exit(1);
		}
		else
		{
//			System.out.println(marshalledString);
//			System.out.println("------------------------------------------------");
		}
		RegressionTestMetrics.process(remarshalledString);
	}
}
