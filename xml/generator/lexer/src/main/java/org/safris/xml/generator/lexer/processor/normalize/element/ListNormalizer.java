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

import java.util.Collection;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.ListModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.UnionModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class ListNormalizer extends Normalizer<ListModel> {
    private final SimpleTypeNormalizer simpleTypeNormalizer = (SimpleTypeNormalizer)getDirectory().lookup(SimpleTypeModel.class);

    public ListNormalizer(NormalizerDirectory directory) {
        super(directory);
    }

    protected void stage1(ListModel model) {
    }

    protected void stage2(ListModel model) {
        final Collection<SimpleTypeModel> itemTypes = model.getItemType();
        if (itemTypes == null || itemTypes.size() != 1)
            throw new LexerError("This should not happen, right?!");

        final SimpleTypeModel itemType = itemTypes.iterator().next();
        SimpleTypeModel type = itemType;
        if (type instanceof SimpleTypeModel.Reference) {
            type = simpleTypeNormalizer.parseSimpleType(type.getName());
            if (type == null) {
                if (!UniqueQName.XS.getNamespaceURI().equals(itemType.getName().getNamespaceURI()))
                    throw new LexerError("type == null for " + type.getName());

                type = SimpleTypeModel.Undefined.parseSimpleType(itemType.getName());
            }

            model.setItemType(type);
        }
    }

    protected void stage3(ListModel model) {
    }

    protected void stage4(ListModel model) {
        if (model.getItemType() == null)
            throw new LexerError("This cant happen.");

        Model parent = model;
        while ((parent = parent.getParent()) != null) {
            // If there is a higher level union, then this list is one of the memberTypes
            if (parent instanceof UnionModel) {
                ((UnionModel)parent).getMemberTypes().addAll(model.getItemType());
                break;
            }
        }
    }

    protected void stage5(ListModel model) {
    }

    protected void stage6(ListModel model) {
        if (model.getItemType() == null)
            throw new LexerError("This can't happen.");

        Model parent = model;
        while ((parent = parent.getParent()) != null) {
            // If this list defines a named simpleType
            if (parent instanceof SimpleTypeModel && ((SimpleTypeModel)parent).getName() != null) {
                final SimpleTypeModel simpleTypeModel = (SimpleTypeModel)parent;
                simpleTypeModel.setSuperType(SimpleTypeModel.Undefined.parseSimpleType(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType")));
                simpleTypeModel.setItemTypes(model.getItemType());
                simpleTypeModel.setList(true);
                break;
            }
        }
    }
}
