package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.net.URL;
import org.safris.commons.xml.validation.ValidationException;
import org.xml.sax.InputSource;

public class BindingDocument
{
	private final URL url;
	private final Binding document;

	public BindingDocument(URL url) throws IOException, ParseException, ValidationException
	{
		this.url = url;
		url.openConnection();
		document = Bindings.parse(new InputSource(url.openStream()));
	}

	public Binding getDocument()
	{
		return document;
	}

	public URL getURL()
	{
		return url;
	}
}
