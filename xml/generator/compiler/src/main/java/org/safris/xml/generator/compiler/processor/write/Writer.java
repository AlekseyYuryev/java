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

package org.safris.xml.generator.compiler.processor.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;
import org.safris.commons.formatter.SourceFormat;
import org.safris.commons.io.Files;
import org.safris.commons.logging.Logger;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.CompilerLoggerName;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.write.Writer;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.Nameable;

public abstract class Writer<T extends Plan> implements PipelineEntity<Writer> {
    private static final StringBuffer license = new StringBuffer();
    protected static final Logger logger = Logger.getLogger(CompilerLoggerName.WRITE);
    private final Collection<String> messages = new HashSet<String>();

    static
    {
        license.append("/*  Copyright 2010 Safris Technologies Inc.\n");
        license.append(" *\n");
        license.append(" *  Licensed under the Apache License, Version 2.0 (the \"License\");\n");
        license.append(" *  you may not use this file except in compliance with the License.\n");
        license.append(" *  You may obtain a copy of the License at\n");
        license.append(" *\n");
        license.append(" *      http://www.apache.org/licenses/LICENSE-2.0\n");
        license.append(" *\n");
        license.append(" *  Unless required by applicable law or agreed to in writing, software\n");
        license.append(" *  distributed under the License is distributed on an \"AS IS\" BASIS,\n");
        license.append(" *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");
        license.append(" *  See the License for the specific language governing permissions and\n");
        license.append(" *  limitations under the License.\n");
        license.append(" */\n\n");
    }

    protected void writeFile(Writer writer, Plan plan, File destDir) {
        if (!(plan instanceof AliasPlan) || (plan instanceof NestablePlan && ((NestablePlan)plan).isNested()))
            return;

        final String display = Files.relativePath(Files.getCwd(), new File(plan.getModel().getSchema().getURL().getFile()).getAbsoluteFile());
        final String message = "Compiling {" + plan.getModel().getTargetNamespace() + "} from " + display;
        if (!messages.contains(message)) {
            logger.info(message);
            messages.add(message);
        }

        final Nameable nameable = (Nameable)plan;
        try {
            final String pkg = nameable.getName().getNamespaceURI().getPackageName().toString();
            if (pkg == null) {
                System.err.println("The binding configuration does not specify a package for " + ((Nameable)plan).getName().getNamespaceURI());
                System.exit(1);
            }

            final File directory = new File(destDir, pkg.replace('.', '/'));
            directory.mkdirs();

            final String absoluteFilePath = directory.getAbsolutePath() + File.separator + JavaBinding.getClassSimpleName(plan.getModel()) + ".java";
            final StringWriter stringWriter = new StringWriter();
            writer.appendClass(stringWriter, plan, null);
            final String text = SourceFormat.getDefaultFormat().format(stringWriter.toString());
            final FileOutputStream out = new FileOutputStream(absoluteFilePath);
            out.write(license.toString().getBytes());
            out.write(text.getBytes());
            out.flush();
            out.close();
        }
        catch (Exception e) {
            throw new CompilerError(e);
        }
    }

    public static void writeDeclaration(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendDeclaration(writer, plan, parent);
    }

    public static void writeGetMethod(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendGetMethod(writer, plan, parent);
    }

    public static void writeSetMethod(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendSetMethod(writer, plan, parent);
    }

    public static void writeMarshal(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendMarshal(writer, plan, parent);
    }

    public static void writeParse(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendParse(writer, plan, parent);
    }

    public static void writeCopy(StringWriter writer, Plan plan, Plan parent, String variable) {
        ((Writer)directory.getEntity(plan, null)).appendCopy(writer, plan, parent, variable);
    }

    public static void writeEquals(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendEquals(writer, plan, parent);
    }

    public static void writeHashCode(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendHashCode(writer, plan, parent);
    }

    public static void writeClass(StringWriter writer, Plan plan, Plan parent) {
        ((Writer)directory.getEntity(plan, null)).appendClass(writer, plan, parent);
    }

    protected static PipelineDirectory<GeneratorContext,Plan,Writer> directory = null;

    protected abstract void appendDeclaration(StringWriter writer, T plan, Plan parent);
    protected abstract void appendGetMethod(StringWriter writer, T plan, Plan parent);
    protected abstract void appendSetMethod(StringWriter writer, T plan, Plan parent);
    protected abstract void appendMarshal(StringWriter writer, T plan, Plan parent);
    protected abstract void appendParse(StringWriter writer, T plan, Plan parent);
    protected abstract void appendCopy(StringWriter writer, T plan, Plan parent, String variable);
    protected abstract void appendEquals(StringWriter writer, T plan, Plan parent);
    protected abstract void appendHashCode(StringWriter writer, T plan, Plan parent);
    protected abstract void appendClass(StringWriter writer, T plan, Plan parent);
}
