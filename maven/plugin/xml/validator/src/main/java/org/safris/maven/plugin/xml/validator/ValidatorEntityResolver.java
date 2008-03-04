package org.safris.maven.plugin.xml.validator;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.safris.commons.net.URLs;

public class ValidatorEntityResolver implements XMLEntityResolver
{
	private final File basedir;

	public ValidatorEntityResolver(File basedir)
	{
		this.basedir = basedir;
	}

	public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException
	{
		final String systemId = resourceIdentifier.getLiteralSystemId();
		final URL url;
		if(!URLs.isAbsolute(systemId))
			url = URLs.makeUrlFromPath(basedir.getAbsolutePath(), systemId);
		else
			url = URLs.makeUrlFromPath(systemId);

		final XMLInputSource inputSource = new XMLInputSource(resourceIdentifier);
		inputSource.setByteStream(url.openStream());
		return inputSource;
	}
}
