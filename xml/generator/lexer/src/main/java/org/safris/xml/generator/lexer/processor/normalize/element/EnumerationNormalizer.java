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
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class EnumerationNormalizer extends Normalizer<EnumerationModel> {
    public EnumerationNormalizer(NormalizerDirectory directory) {
        super(directory);
    }

    protected void stage1(EnumerationModel model) {
    }

    protected void stage2(EnumerationModel model) {
    }

    protected void stage3(EnumerationModel model) {
    }

    protected void stage4(EnumerationModel model) {
        if (model.getValue() == null || model.getValue().getLocalPart().length() == 0)
            return;

        Model parent = model;
        while ((parent = parent.getParent()) != null) {
            if (parent instanceof EnumerableModel && parent instanceof Nameable) {
                ((EnumerableModel)parent).addEnumeration(model);
                if (((Nameable)parent).getName() != null)
                    break;
            }
        }
    }

    protected void stage5(EnumerationModel model) {
    }

    protected void stage6(EnumerationModel model) {
    }
}
