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

package org.safris.xml.generator.lexer.processor.composite;

import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;

public final class SchemaCompositeDirectory implements PipelineDirectory<GeneratorContext,SchemaDocument,SchemaComposite> {
  private SchemaCompositeProcessor processor = new SchemaCompositeProcessor();

  public PipelineEntity getEntity(final SchemaDocument entity, final SchemaComposite parent) {
    return processor;
  }

  public PipelineProcessor<GeneratorContext,SchemaDocument,SchemaComposite> getProcessor() {
    return processor;
  }

  public void clear() {
  }
}