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

package org.safris.xml.generator.lexer.processor.model.element;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RedefineModel extends Model {
    private final LinkedHashSet<SimpleTypeModel> simpleTypeModels = new LinkedHashSet<SimpleTypeModel>();
    private final LinkedHashSet<ComplexTypeModel> complexTypeModels = new LinkedHashSet<ComplexTypeModel>();
    private final LinkedHashSet<GroupModel> groupModels = new LinkedHashSet<GroupModel>();
    private final LinkedHashSet<AttributeGroupModel> attributeGroupModels = new LinkedHashSet<AttributeGroupModel>();
    private String schemaLocation = null;

    protected RedefineModel(Node node, Model parent) {
        super(node, parent);
        final NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            final Node attribute = attributes.item(i);
            if ("schemaLocation".equals(attribute.getLocalName()))
                schemaLocation = attribute.getNodeValue();
        }
    }

    public final String getSchemaLocation() {
        return schemaLocation;
    }

    public final LinkedHashSet<SimpleTypeModel> getSimpleTypeModels() {
        return simpleTypeModels;
    }

    public final LinkedHashSet<ComplexTypeModel> getComplexTypeModels() {
        return complexTypeModels;
    }

    public final LinkedHashSet<GroupModel> getGroupModels() {
        return groupModels;
    }

    public final LinkedHashSet<AttributeGroupModel> getAttributeGroupModels() {
        return attributeGroupModels;
    }
}
