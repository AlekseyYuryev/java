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

public final class PlanProcessor implements PipelineProcessor<GeneratorContext,Model,Plan> {
  private static final Logger logger = Logger.getLogger(CompilerLoggerName.PLAN);
  private Plan root;

  public final Collection<Plan> process(GeneratorContext pipelineContext, Collection<Model> documents, PipelineDirectory<GeneratorContext,Model,Plan> directory) {
    root = new Plan(null, null){};
    final Collection<Plan> plans = new ArrayList<Plan>();
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

  protected final void disclose(Model model, Plan parent, Collection<Plan> plans, GeneratorContext pipelineContext, PipelineDirectory<GeneratorContext,Model,Plan> directory) {
    final Plan plan = (Plan)directory.getEntity(model, parent);
    plans.add(plan);
  }
}
