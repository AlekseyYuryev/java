package org.safris.xml.toolkit.binding;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.commons.util.xml.SchemaReference;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.Phase;

public class SchemaLocator extends Phase
{
	private static final SchemaLocator instance = new SchemaLocator();

	public static SchemaLocator instance()
	{
		return instance;
	}

	// FIXME: There still exists a deadlock condition!!
	private static final class Counter
	{
		protected volatile int count = 0;
	}

	public Collection manipulate(final Collection schemas, final BindingContext bindingContext)
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

									AbstractGenerator.warning("No modification detected for " + schemaReference.getURL().toString() + " and its destination directory " + directory.getPath() + " has not been modified either. Skipping compilation!");
								}
							}
							catch(Exception e)
							{
								throw new CompilerError(e);
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
			throw new CompilerError(e);
		}

		return selectedSchemas;
	}
}
