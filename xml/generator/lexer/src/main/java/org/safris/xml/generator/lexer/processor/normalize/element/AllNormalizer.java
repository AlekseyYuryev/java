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

package org.safris.xml.generator.lexer.processor.normalize.element;

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AllModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AllNormalizer extends Normalizer<AllModel> {
    public AllNormalizer(NormalizerDirectory directory) {
        super(directory);
    }

    protected void stage1(AllModel model) {
    }

    protected void stage2(AllModel model) {
    }

    protected void stage3(AllModel model) {
    }

    protected void stage4(AllModel model) {
        Model parent = model;
        while ((parent = parent.getParent()) != null) {
            if (parent instanceof ElementableModel && (!(parent instanceof Nameable) || ((Nameable)parent).getName() != null)) {
                ((ElementableModel)parent).addMultiplicableModel(model);
                break;
            }
        }
    }

    protected void stage5(AllModel model) {
    }

    protected void stage6(AllModel model) {
    }
}
