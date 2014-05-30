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

import org.safris.commons.logging.Logger;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.processor.model.Model;

public abstract class Normalizer<T extends Model> implements PipelineEntity {
  protected static final Logger logger = Logger.getLogger(LexerLoggerName.NORMALIZE);
  private final NormalizerDirectory directory;

  public Normalizer(final NormalizerDirectory directory) {
    this.directory = directory;
  }

  public NormalizerDirectory getDirectory() {
    return directory;
  }

  // NOTE: This stage used for fixing globally accessible types
  protected abstract void stage1(final T handler);

  // NOTE: This stage used for de-referencing qName references to correct types
  protected abstract void stage2(final T handler);

  // NOTE: This stage used for de-referencing <redefine/> rules
  protected abstract void stage3(final T handler);

  // NOTE: This stage used for injection of information into parent elements as per the physical schema structure
  protected abstract void stage4(final T handler);

  // NOTE: This stage used for injection of information into parent elements as per the logical schema structure
  protected abstract void stage5(final T handler);

  // NOTE: This stage used to amend information in certain edge-case situations.
  protected abstract void stage6(final T handler);
}