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

package org.safris.xml.generator.compiler.processor.write;

import java.io.StringWriter;
import java.util.Collection;

import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.Nameable;

public final class WriterProcessor implements PipelineProcessor<GeneratorContext,Plan<?>,Writer<?>> {
  private final Writer<?> root = new Writer<Plan<?>>() {
    protected void appendDeclaration(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendGetMethod(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendSetMethod(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendMarshal(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendParse(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendCopy(final StringWriter writer, final Plan<?> plan, Plan<?> parent, final String variable) {
    }

    protected void appendEquals(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendHashCode(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }

    protected void appendClass(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    }
  };

  public Collection<Writer<?>> process(final GeneratorContext pipelineContext, final Collection<Plan<?>> documents, final PipelineDirectory<GeneratorContext,Plan<?>,Writer<?>> directory) {
    Writer.directory = directory;
    tailRecurse(pipelineContext, documents, directory);
    return null;
  }

  protected final void tailRecurse(final GeneratorContext pipelineContext, final Collection<Plan<?>> models, final PipelineDirectory<GeneratorContext,Plan<?>,Writer<?>> directory) {
    if (models == null || models.size() == 0)
      return;

    for (final Plan<?> model : models)
      if (model != null)
        tailRecurse(pipelineContext, disclose(pipelineContext, model, directory), directory);
  }

  protected Collection<Plan<?>> disclose(final GeneratorContext pipelineContext, final Plan<?> plan, final PipelineDirectory<GeneratorContext,Plan<?>,Writer<?>> directory) {
    if (!(plan instanceof AliasPlan) || (plan instanceof NestablePlan && ((NestablePlan)plan).isNested()))
      return null;

    if (((Nameable<?>)plan).getName().getNamespaceURI() == null)
      throw new CompilerError("Cannot generate classes for schema with no targetNamespace.");

    ((Writer)root).writeFile(((Writer<?>)directory.getEntity(plan, null)), plan, pipelineContext.getDestDir());
    return null;
  }
}