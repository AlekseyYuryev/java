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

import org.safris.commons.json.xe.$json_boolean;
import org.safris.commons.json.xe.$json_name;
import org.safris.commons.json.xe.$json_number;
import org.safris.commons.json.xe.$json_object;
import org.safris.commons.json.xe.$json_string;
import org.safris.commons.json.xe.$json_value;
import org.safris.commons.json.xe.json_json;
import org.safris.commons.json.validator.PatternValidator;
import org.safris.commons.lang.Resources;
import org.safris.commons.lang.Strings;
import org.safris.commons.maven.Log;
import org.safris.commons.xml.XMLException;
import org.safris.commons.xml.XMLText;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class Generator {
  public static void main(final String[] args) throws Exception {
    Generator.generate(Resources.getResource(args[0]).getURL(), new File(args[1]));
  }

  public static void generate(final URL url, final File destDir) throws IOException, XMLException {
    final json_json json = (json_json)Bindings.parse(new InputSource(url.openStream()));
    if (json._object() == null) {
      Log.error("Missing <object> elements: " + url.toExternalForm());
      return;
    }

    final File outDir = new File(destDir, "json");
    if (!outDir.exists() && !outDir.mkdirs())
      throw new IOException("Unable to mkdirs: " + outDir.getAbsolutePath());

    final String bundleName = json._bundleName$().text();

    String out = "";

    out += "package json;";
    out += "\n\n@" + SuppressWarnings.class.getName() + "(\"all\")";
    out += "\npublic class " + bundleName + " extends " + JSBundle.class.getName() + " {";
    out += "\n  private static " + bundleName + " instance = null;";
    out += "\n\n  protected static " + bundleName + " instance() {";
    out += "\n    return instance == null ? instance = new " + bundleName + "() : instance;";
    out += "\n  }";

    out += "\n\n  @" + Override.class.getName();
    out += "\n  protected " + String.class.getName() + " getSpec() {";
    out += "\n    return \"" + DOMs.domToString(json.marshal(), DOMStyle.INDENT).replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\";";
    out += "\n  }";

    for (final json_json._object object : json._object()) {
      out += writeJavaClass(bundleName, object, outDir);
    }

    out += "\n\n  private " + bundleName + "() {";
    out += "\n  }";
    out += "\n}";
    try (final FileOutputStream fos = new FileOutputStream(new File(outDir, bundleName + ".java"))) {
      fos.write(out.toString().getBytes());
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

    final String instanceName = Strings.toInstanceCase(valueName);

    String out = "";
    out += "\n\n    public final " + Property.class.getName() + "<" + type + "> " + instanceName + " = new " + Property.class.getName() + "<" + type + ">(this, (" + Binding.class.getName() + "<" + type + ">)bindings.get(\"" + valueName + "\"));";
    out += "\n\n    public " + type + " " + instanceName + "() {";
    out += "\n      return get(" + instanceName + ");";
    out += "\n    }";
    return out;
  }

  private static String writeEncode(final $json_value value) {
    final String valueName = getValueName(value);
    final String instanceName = Strings.toInstanceCase(valueName);
    String out = "";
    if (value._required$().text()) {
      out += "\n\n      if (!wasSet(" + instanceName + "))";
      out += "\n        throw new " + EncodeException.class.getName() + "(\"\\\"" + valueName + "\\\" is required\", this);";
    }

    if (!value._null$().text()) {
      out += "\n\n      if (wasSet(" + instanceName + ") && get(" + instanceName + ") == null)";
      out += "\n        throw new " + EncodeException.class.getName() + "(\"\\\"" + valueName + "\\\" cannot be null\", this);";
    }

    out += "\n\n      if (wasSet(" + instanceName + "))";
    out += "\n        out.append(\",\\n\").append(pad(depth)).append(\"\\\"" + valueName + "\\\": \").append(";
    if (!value._array$().isNull() && value._array$().text())
      return out + "tokenize(encode(" + instanceName + "), depth + 1));";

    if (value instanceof $json_object)
      return out + instanceName + " != null && get(" + instanceName + ") != null ? encode(" + instanceName + ")._encode(depth + 1) : \"null\");";

    if (value instanceof $json_string)
      return out + instanceName + " != null && get(" + instanceName + ") != null ? \"\\\"\" + encode(" + instanceName + ") + \"\\\"\" : \"null\");";

    return out + "encode(" + instanceName + "));";
  }

  private static String writeJavaClass(final String bundleName, final json_json._object object, final File outDir) {
    final String objectName = object._name$().text();
    if (object._value() == null) {
      Log.error("<object name='" + objectName + "'> is missing values.");
      return "";
    }

    String out = "";

    final String className = Strings.toClassCase(objectName);
    out += "\n\n  public static class " + className + " extends " + JSObject.class.getName() + " {";
    out += "\n    private static final " + String.class.getName() + " _name = \"" + objectName + "\";\n";
    out += "\n    private static final " + Map.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>> bindings = new " + HashMap.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>>(" + object._value().size() + ");";
    out += "\n    static {";
    out += "\n      registerBinding(_name, " + className + ".class);";
    out += "\n      try {";
    for (final $json_value value : object._value()) {
      final String valueName = getValueName(value);
      final String rawType = getType(value);
      final boolean isArray = value._array$().text() != null && value._array$().text();
      final String type = isArray ? Collection.class.getName() + "<" + rawType + ">" : rawType;

      out += "\n        bindings.put(\"" + valueName + "\", new " + Binding.class.getName() + "<" + type + ">(\"" + valueName + "\", " + className + ".class.getDeclaredField(\"" + Strings.toInstanceCase(valueName) + "\"), " + rawType + ".class, " + isArray + ", " + value._required$().text() + ", " + !value._null$().text();
      if (value instanceof $json_string) {
        final $json_string string = ($json_string)value;
        if (string._pattern$().text() != null)
          out += ", new " + PatternValidator.class.getName() + "(\"" + XMLText.unescapeXMLText(string._pattern$().text()).replace("\\", "\\\\") + "\")";
      }

      out += "));";
    }
    out += "\n      }";
    out += "\n      catch (final " + ReflectiveOperationException.class.getName() + " e) {";
    out += "\n        throw new " + ExceptionInInitializerError.class.getName() + "(e);";
    out += "\n      }";
    out += "\n    }\n";
    out += "\n    @" + Override.class.getName();
    out += "\n    protected " + Map.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>> _bindings() {";
    out += "\n      return bindings;";
    out += "\n    }\n";
    out += "\n    @" + Override.class.getName();
    out += "\n    protected " + JSBundle.class.getName() + " _bundle() {";
    out += "\n      return " + bundleName + ".instance();";
    out += "\n    }\n";
    out += "\n    @" + Override.class.getName();
    out += "\n    protected " + String.class.getName() + " _name() {";
    out += "\n      return _name;";
    out += "\n    }";
    for (final $json_value value : object._value())
      out += writeField(value);
    out += "\n\n    @" + Override.class.getName();
    out += "\n    protected " + String.class.getName() + " _encode(final int depth) {";
    out += "\n      final " + StringBuilder.class.getName() + " out = new " + StringBuilder.class.getName() + "();";
    if (object._value().size() > 0)
      for (int i = 0; i < object._value().size(); i++)
        out += writeEncode(object._value(i));

    out += "\n\n      out.append(\"\\n\").append(pad(depth - 1)).append(\"}\");";
    out += "\n      out.setCharAt(0, '{');";
    out += "\n      return out.toString();\n    }\n";

    out += "\n    @" + Override.class.getName();
    out += "\n    public " + String.class.getName() + " toString() {";
    out += "\n      return _encode(1);";
    out += "\n    }\n";
    out += "  }";

    return out.toString();
  }
}