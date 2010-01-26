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

package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.ImportPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class ImportWriter extends Writer<ImportPlan> {
    protected void appendDeclaration(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    protected void appendGetMethod(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    protected void appendSetMethod(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    protected void appendMarshal(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    protected void appendParse(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    public void appendCopy(StringWriter writer, ImportPlan plan, Plan parent, String variable) {
    }

    protected void appendEquals(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    protected void appendHashCode(StringWriter writer, ImportPlan plan, Plan parent) {
    }

    protected void appendClass(StringWriter writer, ImportPlan plan, Plan parent) {
    }
}
