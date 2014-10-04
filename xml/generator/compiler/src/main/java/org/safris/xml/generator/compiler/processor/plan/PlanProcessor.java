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

package org.safris.xml.generator.compiler.processor.plan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.safris.commons.io.Files;
import org.safris.commons.logging.Logger;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.compiler.lang.CompilerLoggerName;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.model.Model;

public final class PlanProcessor implements PipelineProcessor<GeneratorContext,Model,Plan<?>> {
  private static final Logger logger = Logger.getLogger(CompilerLoggerName.PLAN);
  private Plan<?> root;

  public final Collection<Plan<?>> process(final GeneratorContext pipelineContext, final Collection<Model> documents, final PipelineDirectory<GeneratorContext,Model,Plan<?>> directory) {
    root = new Plan<Model>(null, null) {};
    final Collection<Plan<?>> plans = new ArrayList<Plan<?>>();
    for (final Model model : documents) {
      if (model.getChildren() == null || model.getChildren().size() == 0)
        continue;

      final String display = Files.relativePath(Files.getCwd(), new File(model.getSchema().getURL().getFile()).getAbsoluteFile());
      logger.info("Parsing {" + model.getTargetNamespace() + "} from " + display);

      for (final Model child : model.getChildren())
        disclose(child, root, plans, pipelineContext, directory);
    }

    return plans;
  }

  protected final void disclose(final Model model, final Plan<?> parent, Collection<Plan<?>> plans, final GeneratorContext pipelineContext, final PipelineDirectory<GeneratorContext,Model,Plan<?>> directory) {
    final Plan<?> plan = (Plan<?>)directory.getEntity(model, parent);
    plans.add(plan);
  }
}