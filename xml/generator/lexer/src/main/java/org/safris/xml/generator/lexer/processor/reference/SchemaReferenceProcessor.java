/*  Copyright Safris Software 2008
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.generator.lexer.processor.reference;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.commons.logging.Logger;
import org.safris.commons.net.URLs;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;

public final class SchemaReferenceProcessor implements PipelineEntity<SchemaReference>, PipelineProcessor<GeneratorContext,SchemaReference,SchemaReference> {
  private static final Logger logger = Logger.getLogger(LexerLoggerName.REFERENCE);

  // FIXME: There still exists a deadlock condition!!
  private static final class Counter {
    protected volatile int count = 0;
  }

  public Collection<SchemaReference> process(final GeneratorContext pipelineContext, final Collection<SchemaReference> schemas, PipelineDirectory<GeneratorContext,SchemaReference,SchemaReference> directory) {
    final File destDir = pipelineContext.getDestDir();
    logger.fine("destDir = " + destDir != null ? destDir.getAbsolutePath() : null);

    final Collection<SchemaReference> selectedSchemas = new LinkedHashSet<SchemaReference>(3);
    try {
      // select schemas that should be generated based on timestamps
      synchronized (schemas) {
        final Counter counter = new Counter();
        counter.count = 0;

        final ThreadGroup threadGroup = new ThreadGroup("SchemaReferenceProcess");
        logger.fine("created SchemaReferenceProcess ThreadGroup");
        // download and cache the schemas into a temporary directory
        for (final SchemaReference schemaReference : schemas) {
          new Thread(threadGroup, schemaReference.getURL().toString())
          {
            public void run() {
              try {
                final File directory = new File(destDir, schemaReference.getNamespaceURI().getPackageName().toString().replace('.', File.separatorChar));
                logger.fine("checking whether directory is up-to-date: " + directory.getAbsolutePath());
                if (pipelineContext.getOverwrite() || !directory.exists() || directory.lastModified() < pipelineContext.getManifestLastModified()) {
                    logger.fine("adding: " + directory.getAbsolutePath());
                    selectedSchemas.add(schemaReference);
                }
                else {
                    for (File file : directory.listFiles()) {
                        logger.fine("checking whether file in directory is up-to-date: " + file.getAbsolutePath());
                        if (directory.lastModified() < file.lastModified() && schemaReference.getLastModified() < file.lastModified())
                            continue;

                        logger.fine("adding: " + directory.getAbsolutePath());
                        selectedSchemas.add(schemaReference);
                        logger.fine("deleting files in: " + directory.getAbsolutePath());
                        for (File deleteMe : directory.listFiles())
                            deleteMe.delete();

                        return;
                    }

                    logger.info("Bindings for " + URLs.getName(schemaReference.getURL()) +  " are up-to-date.");
                }
              }
              catch (Exception e) {
                throw new LexerError(e);
              }
              finally {
                synchronized (schemas) {
                    ++counter.count;
                    schemas.notify();
                }
              }
            }
          }.start();
        }

        while (counter.count != schemas.size())
          schemas.wait();
      }
    }
    catch (Exception e) {
      throw new LexerError(e);
    }

    return selectedSchemas;
  }
}
