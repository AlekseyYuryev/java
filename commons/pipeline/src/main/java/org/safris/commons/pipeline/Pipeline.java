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

package org.safris.commons.pipeline;

import java.util.ArrayList;
import java.util.Collection;

public class Pipeline<C extends PipelineContext> {
  private final class Entry<I extends PipelineEntity,O extends PipelineEntity> {
    private final Collection<I> input;
    private final Collection<O> output;
    private final PipelineDirectory<C,I,O> directory;

    public Entry(Collection<I> input, Collection<O> output, PipelineDirectory<C,I,O> directory) {
      this.input = input;
      this.output = output;
      this.directory = directory;
    }

    public PipelineProcessor<C,I,O> getProcessor() {
      return directory.getProcessor();
    }

    public Collection<I> getInput() {
      return input;
    }

    public Collection<O> getOutput() {
      return output;
    }

    public PipelineDirectory<C,I,O> getDirectory() {
      return directory;
    }
  }

  private final Collection<Entry> entries = new ArrayList<Entry>();
  private final C pipelineContext;

  public Pipeline(C pipelineContext) {
    this.pipelineContext = pipelineContext;
  }

  public <I extends PipelineEntity,O extends PipelineEntity>void addProcessor(Collection<I> input, Collection<O> output, PipelineDirectory<C,I,O> handlerDirectory) {
    synchronized (entries) {
      final Entry<I,O> modulePair = new Entry<I,O>(input, output, handlerDirectory);
      entries.add(modulePair);
    }
  }

  public void begin() {
    final Collection<PipelineDirectory> directories = new ArrayList<PipelineDirectory>();
    synchronized (entries) {
      for (Entry modulePair : entries) {
        directories.add(modulePair.getDirectory());
        final Collection output = modulePair.getProcessor().process(pipelineContext, modulePair.getInput(), modulePair.getDirectory());
        if (output != null && modulePair.getOutput() != null)
          modulePair.getOutput().addAll(output);
      }
    }

    for (PipelineDirectory directory : directories)
      directory.clear();
  }
}
