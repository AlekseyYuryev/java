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