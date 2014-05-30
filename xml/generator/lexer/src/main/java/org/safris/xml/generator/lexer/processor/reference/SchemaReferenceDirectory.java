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

import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.processor.GeneratorContext;

public final class SchemaReferenceDirectory implements PipelineDirectory<GeneratorContext,SchemaReference,SchemaReference> {
  private final SchemaReferenceProcessor schemaReferenceProcessor = new SchemaReferenceProcessor();

  public PipelineEntity getEntity(final SchemaReference entity, final SchemaReference parent) {
    return schemaReferenceProcessor;
  }

  public PipelineProcessor<GeneratorContext,SchemaReference,SchemaReference> getProcessor() {
    return schemaReferenceProcessor;
  }

  public void clear() {
  }
}