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

package org.safris.cf.xsb.compiler.processor.reference;

import org.safris.cf.xsb.compiler.processor.GeneratorContext;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;

public final class SchemaReferenceDirectory implements PipelineDirectory<GeneratorContext,SchemaReference,SchemaReference> {
  private final SchemaReferenceProcessor schemaReferenceProcessor = new SchemaReferenceProcessor();

  @Override
  public PipelineEntity getEntity(final SchemaReference entity, final SchemaReference parent) {
    return schemaReferenceProcessor;
  }

  @Override
  public PipelineProcessor<GeneratorContext,SchemaReference,SchemaReference> getProcessor() {
    return schemaReferenceProcessor;
  }

  @Override
  public void clear() {
  }
}