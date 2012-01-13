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

package org.safris.xml.toolkit.processor.timestamp;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.List;
import org.safris.commons.io.Files;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class TimestampProcessor implements PipelineEntity<Bundle>, PipelineProcessor<GeneratorContext,Bundle,Bundle> {
  private static final FileFilter fileFilter = new FileFilter() {
    public boolean accept(File pathname) {
      return pathname != null && pathname.isFile();
    }
  };

  private static final FileFilter dirFileFilter = new FileFilter() {
    public boolean accept(File pathname) {
      return pathname != null && pathname.isDirectory();
    }
  };

  protected TimestampProcessor() {
  }

  public Collection<Bundle> process(GeneratorContext pipelineContext, Collection<Bundle> documents, PipelineDirectory<GeneratorContext,Bundle,Bundle> directory) {
    // Get the earliest lastModified time of all the files
    long lastModified = Long.MAX_VALUE;
    final List<File> files = Files.listAll(pipelineContext.getDestDir(), fileFilter);
    if (files != null)
      for (File file : files)
        if (file.lastModified() < lastModified)
          lastModified = file.lastModified();

    // Set the lastModified time of all directories to just before the value from above
    final List<File> dirs = Files.listAll(pipelineContext.getDestDir(), dirFileFilter);
    if (dirs != null)
      for (File dir : dirs)
        dir.setLastModified(lastModified - 100);

    return null;
  }
}
