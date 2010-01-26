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

package org.safris.xml.generator.lexer.processor.normalize;

import java.lang.reflect.Method;
import java.util.Collection;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;

public class NormalizerProcessor implements PipelineProcessor<GeneratorContext,Model,Normalizer> {
    private int stage = 0;

    protected final void tailRecurse(GeneratorContext pipelineContext, Collection<Model> models, PipelineDirectory<GeneratorContext,Model,Normalizer> directory) {
        if (models == null || models.size() == 0)
            return;

        for (Model model : models) {
            if (model == null)
                continue;

            tailRecurse(pipelineContext, disclose(pipelineContext, model, directory), directory);
        }
    }

    private Collection<Model> disclose(GeneratorContext pipelineContext, Model model, PipelineDirectory<GeneratorContext,Model,Normalizer> directory) {
        final Normalizer normalizer = (Normalizer)directory.getEntity(model, null);
        try {
            final Method method = normalizer.getClass().getDeclaredMethod("stage" + (stage + 1), Model.class);
            method.setAccessible(true);
            method.invoke(normalizer, model);
        }
        catch (Exception e) {
            throw new LexerError(e);
        }

        return model.getChildren();
    }

    public Collection<Normalizer> process(GeneratorContext pipelineContext, Collection<Model> documents, PipelineDirectory<GeneratorContext,Model,Normalizer> directory) {
        int stages = 0;
        Method[] methods = Normalizer.class.getDeclaredMethods();
        for (Method method : methods)
            if (method.getName().startsWith("stage"))
                stages++;

        for (int stage = 0; stage < stages; stage++) {
            this.stage = stage;
            tailRecurse(pipelineContext, documents, directory);
        }

        return null;
    }
}
