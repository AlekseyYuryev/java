/* Copyright (c) 2008 lib4j
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

package org.libx4j.xsb.compiler.processor.normalize;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.lib4j.pipeline.PipelineDirectory;
import org.lib4j.pipeline.PipelineProcessor;
import org.libx4j.xsb.compiler.lang.LexerFailureException;
import org.libx4j.xsb.compiler.processor.GeneratorContext;
import org.libx4j.xsb.compiler.processor.model.Model;

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
    catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new LexerFailureException(e);
    }

    return model.getChildren();
  }

  @Override
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