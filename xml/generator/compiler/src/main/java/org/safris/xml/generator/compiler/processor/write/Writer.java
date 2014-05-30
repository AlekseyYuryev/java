/*  Copyright Safris Software 2008
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.Nameable;

public abstract class Writer<T extends Plan<?>> implements PipelineEntity {
  private static final StringBuffer license = new StringBuffer();
  protected static final Logger logger = Logger.getLogger(CompilerLoggerName.WRITE);
  private final Collection<String> messages = new HashSet<String>();

  static {
    final int year = (int)((double)System.currentTimeMillis() / 1000d / 3600d / 24d / 365.25d + 1970d);
    license.append("/*  Copyright Safris Software ").append(year).append(" \n");
    license.append(" *\n");
    license.append(" *  This code is free software: you can redistribute it and/or modify\n");
    license.append(" *  it under the terms of the GNU General Public License as published by\n");
    license.append(" *  the Free Software Foundation, either version 3 of the License, or\n");
    license.append(" *  (at your option) any later version.\n");
    license.append(" *\n");
    license.append(" *  This program is distributed in the hope that it will be useful,\n");
    license.append(" *  but WITHOUT ANY WARRANTY; without even the implied warranty of\n");
    license.append(" *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n");
    license.append(" *  GNU General Public License for more details.\n");
    license.append(" *\n");
    license.append(" *  You should have received a copy of the GNU General Public License\n");
    license.append(" *  along with this program.  If not, see <http://www.gnu.org/licenses/>.\n");
    license.append(" */\n\n");
  }

  protected void writeFile(final Writer<T> writer, final T plan, final File destDir) {
    if (!(plan instanceof AliasPlan) || (plan instanceof NestablePlan && ((NestablePlan)plan).isNested()))
      return;

    final String display = Files.relativePath(Files.getCwd(), new File(plan.getModel().getSchema().getURL().getFile()).getAbsoluteFile());
    final String message = "Compiling {" + plan.getModel().getTargetNamespace() + "} from " + display;
    if (!messages.contains(message)) {
      logger.info(message);
      messages.add(message);
    }

    final Nameable<?> nameable = (Nameable<?>)plan;
    try {
      final String pkg = nameable.getName().getNamespaceURI().getPackageName().toString();
      if (pkg == null) {
        System.err.println("The binding configuration does not specify a package for " + ((Nameable<?>)plan).getName().getNamespaceURI());
        System.exit(1);
      }

      final File directory = new File(destDir, pkg.replace('.', '/'));
      directory.mkdirs();

      final String fileName = JavaBinding.getClassSimpleName(plan.getModel()) + ".java";
      final String absoluteFilePath = directory.getAbsolutePath() + File.separator + fileName;
      final StringWriter stringWriter = new StringWriter();
      writer.appendClass(stringWriter, plan, null);
      final String text = SourceFormat.getDefaultFormat().format(stringWriter.toString());
      final FileOutputStream out = new FileOutputStream(absoluteFilePath);
      out.write(license.toString().getBytes());
      out.write(text.getBytes());
      out.flush();
      out.close();
    }
    catch (final Exception e) {
      throw new CompilerError(e);
    }
  }

  public static void writeDeclaration(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendDeclaration(writer, plan, parent);
  }

  public static void writeGetMethod(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendGetMethod(writer, plan, parent);
  }

  public static void writeSetMethod(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendSetMethod(writer, plan, parent);
  }

  public static void writeMarshal(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendMarshal(writer, plan, parent);
  }

  public static void writeParse(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendParse(writer, plan, parent);
  }

  public static void writeCopy(final StringWriter writer, final Plan<?> plan, Plan<?> parent, final String variable) {
    ((Writer)directory.getEntity(plan, null)).appendCopy(writer, plan, parent, variable);
  }

  public static void writeEquals(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendEquals(writer, plan, parent);
  }

  public static void writeHashCode(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendHashCode(writer, plan, parent);
  }

  public static void writeClass(final StringWriter writer, final Plan<?> plan, final Plan<?> parent) {
    ((Writer)directory.getEntity(plan, null)).appendClass(writer, plan, parent);
  }

  protected static PipelineDirectory<GeneratorContext,Plan<?>,Writer<?>> directory = null;

  protected abstract void appendDeclaration(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendGetMethod(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendSetMethod(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendMarshal(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendParse(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendCopy(final StringWriter writer, final T plan, final Plan<?> parent, final String variable);
  protected abstract void appendEquals(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendHashCode(final StringWriter writer, final T plan, final Plan<?> parent);
  protected abstract void appendClass(final StringWriter writer, final T plan, final Plan<?> parent);
}