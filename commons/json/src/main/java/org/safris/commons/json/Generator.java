/* Copyright (c) 2015 Seva Safris
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

package org.safris.commons.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.Resources;
import org.safris.commons.lang.Strings;
import org.safris.commons.util.Collections;
import org.safris.commons.xml.XMLException;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class Generator {
  public static void main(final String[] args) throws Exception {
    Generator.generate(Resources.getResource(args[0]).getURL(), new File(args[1]));
  }

  public static void generate(final URL url, final File destDir) throws IOException, XMLException {
    final json_json json = (json_json)Bindings.parse(new InputSource(url.openStream()));
    if (json._object() == null) {
      System.err.println("[ERROR] Missing <object> elements: " + url.toExternalForm());
      return;
    }

    final File outDir = new File(destDir, "json");
    if (!outDir.exists() && !outDir.mkdirs())
      throw new IOException("Unable to mkdirs: " + outDir.getAbsolutePath());

    for (final json_json._object object : json._object()) {
      writeJavaClass(object, outDir);
    }
  }

  private static String getType(final $json_value value) {
    if (value instanceof $json_string)
      return String.class.getName();

    if (value instanceof $json_number)
      return Number.class.getName();

    if (value instanceof $json_boolean)
      return Boolean.class.getName();

    if (value instanceof $json_object)
      return Strings.toClassCase((($json_object)value)._type$().text());

    throw new UnsupportedOperationException("Unknown type: " + value.typeName());
  }

  private static String getValueName(final $json_value value) {
    if (value instanceof $json_name)
      return (($json_name)value)._name$().text();

    if (value instanceof $json_object)
      return (($json_object)value)._type$().text();

    throw new UnsupportedOperationException("Unexpected type: " + value.typeName());
  }

  private static String writeField(final $json_value value) {
    final String valueName = getValueName(value);
    final boolean isArray = value._array$().text() != null && value._array$().text();
    final String rawType = getType(value);
    final String type = isArray ? Collection.class.getName() + "<" + rawType + ">" : rawType;

    final StringBuilder out = new StringBuilder();
    out.append("\n  private ").append(type).append(" _").append(Strings.toInstanceCase(valueName)).append(" = null;\n");
    out.append("\n  public void set").append(Strings.toClassCase(valueName)).append("(final ").append(type).append(" value) {");
    out.append("\n    this._").append(Strings.toInstanceCase(valueName)).append(" = value;\n  }\n");
    out.append("\n  public ").append(type).append(" get").append(Strings.toClassCase(valueName)).append("() {");
    out.append("\n    return this._").append(Strings.toInstanceCase(valueName)).append(";\n  }\n");
    return out.toString();
  }

  private static String writeToString(final $json_value value) {
    final String valueName = getValueName(value);
    final String out = "out.append(pad(depth)).append(\"\\\"" + valueName + "\\\": \").append(";
    if (!value._array$().isNull() && value._array$().text())
      return out + "tokenize(_" + Strings.toInstanceCase(valueName) + ", depth + 1)).append(\"";

    if (value instanceof $json_object)
      return out + "_" + Strings.toInstanceCase(valueName) + " != null ? _" + Strings.toInstanceCase(valueName) + ".encode(depth + 1) : \"null\").append(\"";

    if (value instanceof $json_string)
      return out + "_" + Strings.toInstanceCase(valueName) + " != null ? \"\\\"\" + _" + Strings.toInstanceCase(valueName) + " + \"\\\"\" : \"null\").append(\"";

    if (value instanceof $json_number)
      return out + "format(_" + Strings.toInstanceCase(valueName) + ")).append(\"";

    return out + "_" + Strings.toInstanceCase(valueName) + ").append(\"";
  }

  private static void writeJavaClass(final json_json._object object, final File outDir) throws IOException {
    final String objectName = object._name$().text();
    if (object._value() == null) {
      System.err.println("[ERROR] <object name='" + objectName + "'> is missing values.");
      return;
    }

    final StringBuilder out = new StringBuilder();

    final String className = Strings.toClassCase(objectName);
    out.append("package json;\n\n");
    out.append("public class ").append(className).append(" extends " + JSObject.class.getName() + " {");
    out.append("\n  private static final ").append(String.class.getName()).append(" name = \"").append(objectName).append("\";\n");
    out.append("\n  private static final ").append(Map.class.getName()).append("<").append(String.class.getName()).append(",").append(Binding.class.getName()).append("> bindings = new ").append(HashMap.class.getName()).append("<").append(String.class.getName()).append(",").append(Binding.class.getName()).append(">(").append(object._value().size()).append(");");
    out.append("\n  static {");
    out.append("\n    registerBinding(name, ").append(className).append(".class);");
    out.append("\n    try {");
    for (final $json_value value : object._value()) {
      final String valueName = getValueName(value);
      out.append("\n      bindings.put(\"").append(valueName).append("\", new ").append(Binding.class.getName()).append("(").append(className).append(".class.getDeclaredField(\"_").append(Strings.toInstanceCase(valueName)).append("\"), ").append(getType(value)).append(".class, ").append(!value._array$().isNull() && value._array$().text()).append("));");
    }
    out.append("\n    }");
    out.append("\n    catch (final ").append(NoSuchFieldException.class.getName()).append(" e) {");
    out.append("\n      throw new ").append(ExceptionInInitializerError.class.getName()).append("(e);");
    out.append("\n    }");
    out.append("\n  }\n");
    out.append("\n  protected ").append(Binding.class.getName()).append(" lookupBinding(final ").append(String.class.getName()).append(" name) {");
    out.append("\n    return bindings.get(name);");
    out.append("\n  }\n");
    out.append("\n  protected " + String.class.getName() + " name() {");
    out.append("\n    return name;");
    out.append("\n  }\n");
    for (final $json_value value : object._value())
      out.append(writeField(value));
    out.append("\n  protected " + String.class.getName() + " encode(final int depth) {");
    out.append("\n    final ").append(StringBuilder.class.getName()).append(" out = new ").append(StringBuilder.class.getName()).append("();");
    out.append("\n    out.append(\"{\\n\");");
    if (object._value().size() > 0) {
      for (int i = 0; i < object._value().size() - 1; i++)
        out.append("\n    ").append(writeToString(object._value(i))).append(", \\n\");");

      out.append("\n    ").append(writeToString(object._value(object._value().size() - 1))).append("\\n\");");
    }

    out.append("\n    out.append(pad(depth - 1)).append(\"}\");");
    out.append("\n    return out.toString();\n  }\n");

    out.append("\n  public " + String.class.getName() + " toString() {");
    out.append("\n    final ").append(StringBuilder.class.getName()).append(" out = new ").append(StringBuilder.class.getName()).append("();");
    out.append("\n    out.append(\"{\\n  \\\"").append(object._name$().text()).append("\\\": \").append(encode(2)).append(\"\\n}\");");
    out.append("\n    return out.toString();\n  }\n");
    out.append("}");

    final FileOutputStream fos = new FileOutputStream(new File(outDir, className + ".java"));
    fos.write(out.toString().getBytes());
    fos.close();
  }
}
