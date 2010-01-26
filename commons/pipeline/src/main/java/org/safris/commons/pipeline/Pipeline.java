/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
