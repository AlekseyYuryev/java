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

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class SimpleTypeNormalizer extends Normalizer<SimpleTypeModel> {
    private final Map<UniqueQName,SimpleTypeModel> all = new HashMap<UniqueQName,SimpleTypeModel>();

    public SimpleTypeNormalizer(NormalizerDirectory directory) {
        super(directory);
    }

    public SimpleTypeModel parseSimpleType(UniqueQName name) {
        return all.get(name);
    }

    protected void stage1(SimpleTypeModel model) {
        if (model.getName() == null || !(model.getParent() instanceof SchemaModel))
            return;

        if (parseSimpleType(model.getName()) == null)
            all.put(model.getName(), model);
    }

    protected void stage2(SimpleTypeModel model) {
    }

    protected void stage3(SimpleTypeModel model) {
    }

    protected void stage4(SimpleTypeModel model) {
    }

    protected void stage5(SimpleTypeModel model) {
    }

    protected void stage6(SimpleTypeModel model) {
    }
}
