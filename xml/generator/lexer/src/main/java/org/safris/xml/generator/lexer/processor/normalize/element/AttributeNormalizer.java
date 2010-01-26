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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;
import org.safris.xml.generator.lexer.processor.normalize.element.SimpleTypeNormalizer;

public class AttributeNormalizer extends Normalizer<AttributeModel> {
    private final Map<UniqueQName,AttributeModel> all = new HashMap<UniqueQName,AttributeModel>();
    private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

    public AttributeNormalizer(NormalizerDirectory directory) {
        super(directory);
    }

    public final AttributeModel parseAttribute(UniqueQName name) {
        return all.get(name);
    }

    protected void stage1(AttributeModel model) {
        if (model.getName() == null || !(model.getParent() instanceof SchemaModel))
            return;

        if (parseAttribute(model.getName()) == null)
            all.put(model.getName(), model);
    }

    protected void stage2(AttributeModel model) {
        // First set the attributeFormDefault value
        Model schema = model.getParent();
        if (schema != null)
            while (!((schema = schema.getParent()) instanceof SchemaModel) && schema != null);

        if (schema != null)
            model.setFormDefault(((SchemaModel)schema).getAttributeFormDefault());

        if (model.getRef() instanceof AttributeModel.Reference) {
            final AttributeModel ref = parseAttribute(model.getRef().getName());
            model.setRef(ref);
        }

        if (model.getSuperType() instanceof SimpleTypeModel.Reference) {
            SimpleTypeModel type = simpleTypeNormalizer.parseSimpleType(model.getSuperType().getName());
            if (type == null)
                type = SimpleTypeModel.Undefined.parseSimpleType(model.getSuperType().getName());

            model.setSuperType(type);
        }

        // Add the attribute to the attributeGroup
        if (model.getParent() instanceof AttributeGroupModel)
            ((AttributeGroupModel)model.getParent()).addAttribute(model);
    }

    protected void stage3(AttributeModel model) {
    }

    protected void stage4(AttributeModel model) {
        Model parent = model;
        while ((parent = parent.getParent()) != null) {
            if (parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null) {
                ((AttributableModel)parent).addAttribute(model);
                break;
            }
        }

        if (model.getFixed() != null)
            model.addEnumeration(new EnumerationModel(model.getFixed()));
    }

    protected void stage5(AttributeModel model) {
    }

    protected void stage6(AttributeModel model) {
        if (model.getName() == null)
            return;

        if (model.getSuperType() == null) {
            final SimpleTypeModel type = ComplexTypeModel.Undefined.parseComplexType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType"));
            model.setSuperType(type);
            model.setItemTypes(Arrays.<SimpleTypeModel>asList(type));
        }
    }
}
