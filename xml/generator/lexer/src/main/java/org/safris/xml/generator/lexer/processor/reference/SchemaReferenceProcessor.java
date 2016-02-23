/* Copyright (c) 2008 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.xml.generator.lexer.processor.reference;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

import org.safris.commons.net.URLs;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.GeneratorContext;

public final class SchemaReferenceProcessor implements PipelineEntity, PipelineProcessor<GeneratorContext,SchemaReference,SchemaReference> {
  private static final Logger logger = Logger.getLogger(SchemaReferenceProcessor.class.getName());

  // FIXME: There still exists a deadlock condition!!
  private static final class Counter {
    protected volatile int count = 0;
  }

  @Override
  public Collection<SchemaReference> process(final GeneratorContext pipelineContext, final Collection<SchemaReference> schemas, final PipelineDirectory<GeneratorContext,SchemaReference,SchemaReference> directory) {
    final File destDir = pipelineContext.getDestdir();
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
          new Thread(threadGroup, schemaReference.getURL().toString()) {
            @Override
            public void run() {
              try {
                final File directory = new File(destDir, schemaReference.getNamespaceURI().getPackageName().toString().replace('.', File.separatorChar));
                logger.fine("checking whether directory is up-to-date: " + directory.getAbsolutePath());
                if (pipelineContext.getOverwrite() || !directory.exists() || directory.lastModified() < pipelineContext.getManifestLastModified()) {
                  logger.fine("adding: " + directory.getAbsolutePath());
                  selectedSchemas.add(schemaReference);
                }
                else {
                  for (final File file : directory.listFiles()) {
                    logger.fine("checking whether file in directory is up-to-date: " + file.getAbsolutePath());
                    if (schemaReference.getLastModified() < file.lastModified())
                      continue;

                    logger.fine("adding: " + directory.getAbsolutePath());
                    selectedSchemas.add(schemaReference);
                    logger.fine("deleting files in: " + directory.getAbsolutePath());
                    for (final File deleteMe : directory.listFiles())
                      deleteMe.delete();

                    return;
                  }

                  logger.info("Bindings for " + URLs.getName(schemaReference.getURL()) + " are up-to-date.");
                }
              }
              catch (final Exception e) {
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
    catch (final Exception e) {
      throw new LexerError(e);
    }

    return selectedSchemas;
  }
}