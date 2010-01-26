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

package org.safris.xml.generator.compiler.lang;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.MultiplicableModel;
import org.safris.xml.generator.lexer.processor.model.RedefineableModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;

public class ElementWrapper extends Model implements Nameable {
    public static LinkedHashSet<ElementWrapper> asSet(LinkedHashSet<MultiplicableModel> multiplicableModels) {
        final LinkedHashSet<ElementWrapper> elementWrappers = new LinkedHashSet<ElementWrapper>();
        asSet(multiplicableModels, elementWrappers, 1, 1, new HashSet<UniqueQName>());
        return elementWrappers;
    }

    private static void asSet(LinkedHashSet<MultiplicableModel> multiplicableModels, LinkedHashSet<ElementWrapper> elementWrappers, int min, int max, Collection<UniqueQName> redefines) {
        for (MultiplicableModel multiplicableModel : multiplicableModels) {
            // FIXME: the list used to track redefines seems BAD!!!
            if (multiplicableModel instanceof RedefineableModel && ((RedefineableModel)multiplicableModel).getRedefine() != null && !redefines.contains(((Nameable)multiplicableModel).getName())) {
                multiplicableModel = (MultiplicableModel)((RedefineableModel)multiplicableModel).getRedefine();
                redefines.add(((Nameable)multiplicableModel).getName());
            }

            int maxOccurs = multiplicableModel.getMaxOccurs().getValue();
            if (maxOccurs != Integer.MAX_VALUE)
                maxOccurs *= max;

            int minOccurs = multiplicableModel.getMinOccurs().getValue();
            minOccurs *= min;

            if (multiplicableModel instanceof ElementModel)
                elementWrappers.add(new ElementWrapper((ElementModel)multiplicableModel, minOccurs, maxOccurs));
            else
                asSet(multiplicableModel.getMultiplicableModels(), elementWrappers, minOccurs, maxOccurs, redefines);
        }
    }

    private final ElementModel elementModel;
    private final int minOccurs;
    private final int maxOccurs;

    public ElementWrapper(ElementModel elementModel, int minOccurs, int maxOccurs) {
        super(null, elementModel.getParent());
        this.elementModel = elementModel;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    public final ElementModel getElementModel() {
        return elementModel;
    }

    public final int getMinOccurs() {
        return minOccurs;
    }

    public final int getMaxOccurs() {
        return maxOccurs;
    }

    public UniqueQName getName() {
        return elementModel.getName();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ElementWrapper))
            return false;

        return elementModel.equals(((ElementWrapper)obj).elementModel);
    }

    public int hashCode() {
        return elementModel.hashCode();
    }
}
