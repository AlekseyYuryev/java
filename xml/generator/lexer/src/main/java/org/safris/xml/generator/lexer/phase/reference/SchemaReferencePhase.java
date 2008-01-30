package org.safris.xml.generator.lexer.phase.reference;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;

public final class SchemaReferencePhase extends Phase<SchemaReference,SchemaReference> implements ElementModule<SchemaReference>
{
	// FIXME: There still exists a deadlock condition!!
	private static final class Counter
	{
		protected volatile int count = 0;
	}

	protected SchemaReferencePhase()
	{
	}

	public Collection<SchemaReference> manipulate(final Collection<SchemaReference> schemas, final BindingContext bindingContext, HandlerDirectory<SchemaReference, SchemaReference> directory)
	{
		final File destDir = bindingContext.getDestDir();

		final Collection<SchemaReference> selectedSchemas = new LinkedHashSet<SchemaReference>(3);
		try
		{
			// select schemas that should be generated based on timestamps
			synchronized(schemas)
			{
				final Counter counter = new Counter();
				counter.count = 0;

				final ThreadGroup threadGroup = new ThreadGroup("SchemaLocation");
				// download and cache the schemas into a temporary directory
				for(Object object : schemas)
				{
					final SchemaReference schemaReference = (SchemaReference)object;
//					if(schemaReference.getURL() == null
					new Thread(threadGroup, schemaReference.getURL().toString())
					{
						public void run()
						{
							try
							{
								final File directory = new File(destDir, schemaReference.getNamespaceURI().getPackageName().toString().replace('.', File.separatorChar));
								if(bindingContext.getOverwrite() || !directory.exists())
								{
									selectedSchemas.add(schemaReference);
								}
								else
								{
									for(File file : directory.listFiles())
									{
										if(directory.lastModified() < file.lastModified() && schemaReference.getLastModified() < file.lastModified())
											continue;

										selectedSchemas.add(schemaReference);
										for(File deleteMe : directory.listFiles())
											deleteMe.delete();

										return;
									}

									System.err.println("No modification detected for " + schemaReference.getURL().toString() + " and its destination directory " + directory.getPath() + " has not been modified either. Skipping compilation!");
								}
							}
							catch(Exception e)
							{
								throw new LexerError(e);
							}
							finally
							{
								synchronized(schemas)
								{
									++counter.count;
									schemas.notify();
								}
							}
						}
					}.start();
				}

				while(counter.count != schemas.size())
					schemas.wait();
			}

			if(selectedSchemas.size() == 0)
			{
				logger().info("Determined that all bindings are up-to-date!");
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			throw new LexerError(e);
		}

		return selectedSchemas;
	}
}
