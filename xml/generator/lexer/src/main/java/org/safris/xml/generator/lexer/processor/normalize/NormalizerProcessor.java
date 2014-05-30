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

package org.safris.xml.generator.lexer.processor.normalize;

import java.lang.reflect.Method;
import java.util.Collection;

import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.model.Model;

public final class NormalizerProcessor implements PipelineProcessor<GeneratorContext,Model,Normalizer<?>> {
  private int stage = 0;

  protected final void tailRecurse(final GeneratorContext pipelineContext, final Collection<Model> models, final PipelineDirectory<GeneratorContext,Model,Normalizer<?>> directory) {
    if (models == null || models.size() == 0)
      return;

    for (final Model model : models)
      if (model != null)
        tailRecurse(pipelineContext, disclose(pipelineContext, model, directory), directory);
  }

  private Collection<Model> disclose(final GeneratorContext pipelineContext, final Model model, final PipelineDirectory<GeneratorContext,Model,Normalizer<?>> directory) {
    final Normalizer<?> normalizer = (Normalizer<?>)directory.getEntity(model, null);
    try {
      final Method method = normalizer.getClass().getDeclaredMethod("stage" + (stage + 1), Model.class);
      method.setAccessible(true);
      method.invoke(normalizer, model);
    }
    catch (final Exception e) {
      throw new LexerError(e);
    }

    return model.getChildren();
  }

  public Collection<Normalizer<?>> process(final GeneratorContext pipelineContext, final Collection<Model> documents, final PipelineDirectory<GeneratorContext,Model,Normalizer<?>> directory) {
    int stages = 0;
    final Method[] methods = Normalizer.class.getDeclaredMethods();
    for (final Method method : methods)
      if (method.getName().startsWith("stage"))
        stages++;

    for (int stage = 0; stage < stages; stage++) {
      this.stage = stage;
      tailRecurse(pipelineContext, documents, directory);
    }

    return null;
  }
}