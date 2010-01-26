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

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;

public class EnumerationPlan extends Plan<EnumerationModel> {
    private String declarationName = null;
    private final QName value;

    public static String getDeclarationName(QName value) {
        String string = null;
        if (47 < value.getLocalPart().charAt(0) && value.getLocalPart().charAt(0) < 58)
            string = "_" + value.getLocalPart();
        else
            string = value.getLocalPart();

        if (value.getPrefix() != null && value.getPrefix().toString().length() != 0)
            string = value.getPrefix() + "_" + string;

        string = string.replace('.', '_');
        string = string.replace('-', '_');
        return string.toUpperCase();
    }

    public EnumerationPlan(EnumerationModel model, Plan parent) {
        super(model, parent);
        this.value = model.getValue();
    }

    public QName getValue() {
        return value;
    }

    public String getDeclarationName() {
        if (declarationName != null)
            return declarationName;

        if (getModel().getValue().getLocalPart().length() == 0)
            throw new CompilerError("The localPart of this enumeration cannot be length == 0");

        return declarationName = getDeclarationName(getModel().getValue());
    }
}
