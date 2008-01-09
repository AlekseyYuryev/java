package org.safris.xml.generator.compiler.runtime;

import com.sun.org.apache.xerces.internal.impl.xs.XSDDescription;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.NamespaceBinding;
import org.safris.commons.util.URLs;
import org.safris.xml.generator.compiler.runtime.BindingError;
import org.safris.xml.generator.compiler.runtime.Bindings;

public class XMLSchemaResolver implements XMLEntityResolver
{
	public static void registerSchemaLocation(String namespaceURI, URL schemaReference)
	{
		final URL present = schemaReferences.get(namespaceURI);
		if(present != null && !present.equals(schemaReference))
			throw new BindingError("We should not be resetting {" + namespaceURI + "} from " + present + " to " + schemaReference);

		schemaReferences.put(namespaceURI, schemaReference);
	}

	public static URL lookupSchemaLocation(String namespaceURI)
	{
		if(namespaceURI == null)
			return null;

		URL schemaReference = schemaReferences.get(namespaceURI);
		if(schemaReference == null)
		{
			// The schemaReference may not have been registered yet
			synchronized(namespaceURI)
			{
				if(!schemaReferences.containsKey(namespaceURI))
				{
					// When loading the classes, the static block of each binding will call the
					// registerSchemaLocation() function.
					String pkg = NamespaceBinding.getPackageFromNamespace(namespaceURI);
					Bindings.bootstrapSchemaPackage(pkg, ClassLoader.getSystemClassLoader());
				}
			}

			schemaReference = schemaReferences.get(namespaceURI);
		}

		return schemaReference;
	}

	private static final Map<String,URL> schemaReferences = new HashMap<String,URL>();

	public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException
	{
		if(resourceIdentifier == null)
			return null;

		final String namespaceURI = resourceIdentifier.getNamespace();
		String baseId = resourceIdentifier.getBaseSystemId();
		if(baseId == null)
			baseId = resourceIdentifier.getExpandedSystemId();

		// for some reason, this happens every once in a while
		if(namespaceURI == null && baseId == null)
			return null;

		try
		{
			final URL schemaReference;
			if(((XSDDescription)resourceIdentifier).getContextType() == XSDDescription.CONTEXT_INCLUDE)
			{
				final String localName = URLs.getName(resourceIdentifier.getExpandedSystemId());
				schemaReference = new URL(URLs.getParent(baseId) + "/" + localName);
			}
			else
				schemaReference = lookupSchemaLocation(namespaceURI);

			if(schemaReference == null)
				throw new BindingError("The schemaReference for " + resourceIdentifier + " is null!");

			final String expandedSystemId;
			try
			{
				expandedSystemId = URLs.toExternalForm(schemaReference);
			}
			catch(MalformedURLException e)
			{
				final IOException ioException = new IOException("Cannot obtain externalForm of " + schemaReference);
				ioException.initCause(e);
				throw ioException;
			}

			resourceIdentifier.setExpandedSystemId(expandedSystemId);
			resourceIdentifier.setLiteralSystemId(expandedSystemId);

			final XMLInputSource inputSource = new XMLInputSource(resourceIdentifier);
			inputSource.setByteStream(schemaReference.openStream());
			return inputSource;
		}
		catch(IOException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new BindingError(resourceIdentifier.toString(), e);
		}
	}
}
