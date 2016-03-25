/* Copyright (c) 2008 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.xml.generator.compiler.processor.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

import org.safris.commons.formatter.SourceFormat;
import org.safris.commons.io.Files;
import org.safris.commons.maven.Log;
import org.safris.commons.net.URLs;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.lang.JavaBinding;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.NestablePlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.Nameable;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Writer<T extends Plan<?>> implements PipelineEntity {
  private static final StringBuffer license = new StringBuffer();
  private final Collection<String> messages = new HashSet<String>();

  static {
    license.append("/* .-------------------------------------------------------------. */\n");
    license.append("/* | GENERATED CODE - XML Binding [xml.safris.org] - DO NOT EDIT | */\n");
    license.append("/* '-------------------------------------------------------------' */\n\n");
  }

  protected void writeFile(final Writer<T> writer, final T plan, final File destDir) {
    if (!(plan instanceof AliasPlan) || (plan instanceof NestablePlan && ((NestablePlan)plan).isNested()))
      return;

    final URL url = plan.getModel().getSchema().getURL();
    final String display = URLs.isLocal(url) ? Files.relativePath(Files.getCwd().getAbsoluteFile(), new File(url.getFile()).getAbsoluteFile()) : url.toExternalForm();
    final String message = "Compiling {" + plan.getModel().getTargetNamespace() + "} from " + display;

    if (!messages.contains(message)) {
      messages.add(message);
      Log.info(message);
    }

    final Nameable<?> nameable = (Nameable<?>)plan;
    try {
      final String pkg = nameable.getName().getNamespaceURI().getPackageName().toString();
      if (pkg == null) {
        Log.error("The binding configuration does not specify a package for " + ((Nameable<?>)plan).getName().getNamespaceURI());
        System.exit(1);
      }

      final File directory = new File(destDir, pkg.replace('.', '/'));
      directory.mkdirs();

      final String fileName = JavaBinding.getClassSimpleName(plan.getModel()) + ".java";
      final String absoluteFilePath = directory.getAbsolutePath() + File.separator + fileName;
      final StringWriter stringWriter = new StringWriter();
      writer.appendClass(stringWriter, plan, null);
      final String text = SourceFormat.getDefaultFormat().format(stringWriter.toString());
      try (final FileOutputStream out = new FileOutputStream(absoluteFilePath)) {
        out.write(license.toString().getBytes());
        out.write(text.getBytes());
        out.flush();
      }
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