package org.safris.xml.generator.lexer.processor.reference;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.commons.logging.Logger;
import org.safris.commons.net.URLs;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public final class SchemaReferenceProcessor implements ElementModule<SchemaReference>, ModuleProcessor<SchemaReference,SchemaReference>
{
	private static final Logger logger = Logger.getLogger(LexerLoggerName.REFERENCE);

	// FIXME: There still exists a deadlock condition!!
	private static final class Counter
	{
		protected volatile int count = 0;
	}

	public Collection<SchemaReference> process(final Collection<SchemaReference> schemas, final GeneratorContext generatorContext, ProcessorDirectory<SchemaReference, SchemaReference> directory)
	{
		final File destDir = generatorContext.getDestDir();

		final Collection<SchemaReference> selectedSchemas = new LinkedHashSet<SchemaReference>(3);
		try
		{
			// select schemas that should be generated based on timestamps
			synchronized(schemas)
			{
				final Counter counter = new Counter();
				counter.count = 0;

				final ThreadGroup threadGroup = new ThreadGroup("SchemaReferenceProcess");
				// download and cache the schemas into a temporary directory
				for(final SchemaReference schemaReference : schemas)
				{
					new Thread(threadGroup, schemaReference.getURL().toString())
					{
						public void run()
						{
							try
							{
								final File directory = new File(destDir, schemaReference.getNamespaceURI().getPackageName().toString().replace('.', File.separatorChar));
								if(generatorContext.getOverwrite() || !directory.exists() || directory.lastModified() < generatorContext.getManifestLastModified())
								{
									selectedSchemas.add(schemaReference);
								}
								else
								{
									for(File file : directory.listFiles())
									{
										if(directory.lastModified() < file.lastModified() && schemaReference.getLastModified() < file.lastModified())
											continue;

										System.out.println("Added: [dir < file: " + (directory.lastModified() < file.lastModified()) + "] [schema < file: " + (schemaReference.getLastModified() < file.lastModified()) + "]");
										selectedSchemas.add(schemaReference);
										for(File deleteMe : directory.listFiles())
											deleteMe.delete();

										return;
									}

									System.err.println("Bindings for " + URLs.getName(schemaReference.getURL()) +  " are up-to-date.");
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
		}
		catch(Exception e)
		{
			throw new LexerError(e);
		}

		return selectedSchemas;
	}
}
