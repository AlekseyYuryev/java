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

package org.safris.xml.generator.compiler.processor.plan.element;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.lang.ElementWrapper;
import org.safris.xml.generator.compiler.lang.XSTypeDirectory;
import org.safris.xml.generator.compiler.processor.plan.AttributablePlan;
import org.safris.xml.generator.compiler.processor.plan.ElementablePlan;
import org.safris.xml.generator.compiler.processor.plan.EnumerablePlan;
import org.safris.xml.generator.compiler.processor.plan.ExtensiblePlan;
import org.safris.xml.generator.compiler.processor.plan.MixablePlan;
import org.safris.xml.generator.compiler.processor.plan.NativeablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.compiler.processor.plan.element.SimpleTypePlan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.MixableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.TypeableModel;
import org.safris.xml.generator.lexer.processor.model.element.ComplexTypeModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleContentModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public class ComplexTypePlan<T extends ComplexTypeModel> extends SimpleTypePlan<T> implements AttributablePlan, ElementablePlan, EnumerablePlan, ExtensiblePlan, MixablePlan, NativeablePlan {
    private final Boolean mixed;

    private Boolean mixedType = null;
    private boolean simpleContent = false;

    private LinkedHashSet<AttributePlan> attributes;
    private LinkedHashSet<ElementPlan> elements;

    public ElementPlan elementRefExistsInParent(UniqueQName name) {
        // FIXME: This is slow!
        if (getElements() != null)
            for (ElementPlan element : getElements())
                if (element.getName() != null && element.getName().equals(name))
                    return element;

        if (getSuperType() == null)
            return null;

        return getSuperType().elementRefExistsInParent(name);
    }

    public ComplexTypePlan(T model, Plan parent) {
        super(model.getRedefine() != null ? (T)model.getRedefine() : model, parent);
        mixed = getModel().getMixed();
        for (Model child : model.getChildren()) {
            if (child instanceof SimpleContentModel) {
                simpleContent = true;
                break;
            }
        }
    }

    public final boolean hasSimpleContent() {
        return simpleContent;
    }

    public final LinkedHashSet<AttributePlan> getAttributes() {
        if (attributes != null)
            return attributes;

        return attributes = Plan.<AttributePlan>analyze(getModel().getAttributes(), this);
    }

    public final LinkedHashSet<ElementPlan> getElements() {
        if (elements != null)
            return elements;

        return elements = Plan.<ElementPlan>analyze(ElementWrapper.asSet(getModel().getMultiplicableModels()), this);
    }

    public final Boolean getMixed() {
        return mixed;
    }

    public final Boolean getMixedType() {
        if (mixedType != null)
            return mixedType;

        // this flag may be a HACK. I am using it to see if I have a restriction in the chain of
        // dependencies. If I do, then mixed="true" has to be stated explicitly.
        boolean isEverRestriction = false;
        TypeableModel parent = getModel();
        boolean restriction = getModel().isRestriction();
        while ((parent = parent.getSuperType()) != null) {
            if (parent instanceof MixableModel && !restriction && ((MixableModel)parent).getMixed() != null && ((MixableModel)parent).getMixed())
                return mixedType = true;

            restriction = ((SimpleTypeModel)parent).isRestriction();
            isEverRestriction = isEverRestriction || restriction;
        }

        parent = getModel();
        while ((parent = parent.getSuperType()) != null)
            if (parent instanceof MixableModel && ((MixableModel)parent).getMixed() != null)
                return mixedType = ((MixableModel)parent).getMixed();

        if (!isEverRestriction && XSTypeDirectory.ANYTYPE.getNativeBinding().getName().equals(getBaseXSItemTypeName()))
            return mixedType = true;

        return mixedType = false;
    }
}
