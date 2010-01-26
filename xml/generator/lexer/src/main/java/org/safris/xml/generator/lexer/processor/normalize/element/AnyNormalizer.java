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

import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AnyModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AnyNormalizer extends Normalizer<AnyModel> {
    public AnyNormalizer(NormalizerDirectory directory) {
        super(directory);
    }

    protected void stage1(AnyModel model) {
    }

    protected void stage2(AnyModel model) {
    }

    protected void stage3(AnyModel model) {
    }

    protected void stage4(AnyModel model) {
        Model parent = model;
        while ((parent = parent.getParent()) != null) {
            if (parent instanceof ElementableModel) {
                ((ElementableModel)parent).addMultiplicableModel(model);
                break;
            }
        }
    }

    protected void stage5(AnyModel model) {
    }

    protected void stage6(AnyModel model) {
    }
}
