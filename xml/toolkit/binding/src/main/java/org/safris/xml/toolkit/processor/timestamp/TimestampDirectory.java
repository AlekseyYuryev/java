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

package org.safris.xml.toolkit.processor.timestamp;

import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class TimestampDirectory implements PipelineDirectory<GeneratorContext,Bundle,Bundle> {
    private TimestampProcessor processor = new TimestampProcessor();

    public PipelineEntity<Bundle> getEntity(Bundle entity, Bundle parent) {
        return processor;
    }

    public PipelineProcessor<GeneratorContext,Bundle,Bundle> getProcessor() {
        return processor;
    }

    public void clear() {
    }
}
