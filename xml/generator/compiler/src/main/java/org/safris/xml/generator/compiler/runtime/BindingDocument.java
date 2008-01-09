package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.net.URL;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.ValidationException;
import org.xml.sax.InputSource;

public class BindingDocument<T extends Binding>
{
	private final URL url;
	private final T document;
	
	public BindingDocument(URL url) throws IOException, ParseException, ValidationException
	{
		this.url = url;
		url.openConnection();
		document = (T)Bindings.parse(new InputSource(url.openStream()));
	}
	
	public T getDocument()
	{
		return document;
	}
	
	public URL getURL()
	{
		return url;
	}
}
