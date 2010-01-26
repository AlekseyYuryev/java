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
import org.safris.xml.generator.lexer.processor.model.MultiplicableModel;
import org.safris.xml.generator.lexer.schema.attribute.Occurs;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AllModel extends Model implements MultiplicableModel {
    private final LinkedHashSet<MultiplicableModel> multiplicableModels = new LinkedHashSet<MultiplicableModel>();

    private Occurs maxOccurs = Occurs.parseOccurs("1");
    private Occurs minOccurs = Occurs.parseOccurs("1");

    protected AllModel(Node node, Model parent) {
        super(node, parent);
        final NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            final Node attribute = attributes.item(i);
            if ("maxOccurs".equals(attribute.getLocalName()))
                maxOccurs = Occurs.parseOccurs(attribute.getNodeValue());
            else if ("minOccurs".equals(attribute.getLocalName()))
                minOccurs = Occurs.parseOccurs(attribute.getNodeValue());
        }
    }

    public void addMultiplicableModel(MultiplicableModel multiplicableModel) {
        if (!this.equals(multiplicableModel))
            this.multiplicableModels.add(multiplicableModel);
    }

    public LinkedHashSet<MultiplicableModel> getMultiplicableModels() {
        return multiplicableModels;
    }

    public Occurs getMaxOccurs() {
        return maxOccurs;
    }

    public Occurs getMinOccurs() {
        return minOccurs;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return super.toString().replace(TO_STRING_DELIMITER, "maxOccurs=\"" + maxOccurs + "\" minOccurs=\"" + minOccurs + "\"");
    }
}
