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

package org.safris.xml.generator.compiler.processor.plan;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.ElementWrapper;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public abstract class Plan<T extends Model> implements PipelineEntity<Plan> {
    public ElementPlan elementRefExistsInParent(UniqueQName name) {
        return null;
    }

    public static <A extends Plan>LinkedHashSet analyze(Collection<? extends Model> models, Plan owner) {
        final LinkedHashSet<A> plans;
        if (models != null && models.size() != 0) {
            plans = new LinkedHashSet<A>(models.size());
            for (Object model : models) {
                // If there is a name conflict with a parent type, then skip
                // adding this in duplicate. Otherwise there will be conflicts
                // with method names.
//              if(owner.getSuperType() != null && model instanceof Nameable && owner.getSuperType().elementRefExistsInParent(((Nameable)model).getName()))
//                  continue;

                if (model instanceof ElementWrapper) {
                    final ElementWrapper element = (ElementWrapper)model;
                    final A plan = Plan.<A>analyze(element.getElementModel(), owner);
                    ((ElementPlan)plan).setMinOccurs(element.getMinOccurs());
                    ((ElementPlan)plan).setMaxOccurs(element.getMaxOccurs());
                    plans.add(plan);
                }
                else if (model instanceof Model) {
                    plans.add(Plan.<A>analyze((Model)model, owner));
                }
            }
        }
        else {
            plans = new LinkedHashSet<A>(0);
        }

        return plans;
    }

    // FIXME: Forgot this section here!!!
    public static <A extends Plan>A analyze(Model model, Plan owner) {
        if (model == null)
            return null;

        final String planName = Plan.class.getPackage().getName() + ".element." + model.getClass().getSimpleName().substring(0, model.getClass().getSimpleName().indexOf("Model")) + "Plan";
        A plan = null;
        try {
            final Constructor constructor = Class.forName(planName).getConstructor(model.getClass(), Plan.class);
            plan = (A)constructor.newInstance(model, owner);
        }
        catch (ClassNotFoundException e) {
            throw new CompilerError("Class not found for element [" + model.getClass().getSimpleName() + "]");
        }
        catch (Exception e) {
            throw new CompilerError(e);
        }

        return plan;
    }

    protected static boolean hasEnumerations(EnumerableModel model) {
        final Collection<EnumerationModel> enumerations = model.getEnumerations();
        final boolean hasEnumerations = enumerations != null && enumerations.size() != 0;
        if (hasEnumerations)
            return hasEnumerations;

        if (!(model instanceof SimpleTypeModel))
            return false;

        SimpleTypeModel restrictionType = (SimpleTypeModel)model;
        if (restrictionType == null)
            return hasEnumerations;

        while ((restrictionType = restrictionType.getSuperType()) != null)
            if (restrictionType instanceof EnumerableModel)
                return hasEnumerations((EnumerableModel)restrictionType);

        return hasEnumerations;
    }

    private final T model;
    private final Plan parent;

    public Plan(T model, Plan parent) {
        this.model = model;
        this.parent = parent;
    }

    public T getModel() {
        return model;
    }

    public final Plan getParent() {
        return parent;
    }

    public Plan getSuperType() {
        return null;
    }
}
