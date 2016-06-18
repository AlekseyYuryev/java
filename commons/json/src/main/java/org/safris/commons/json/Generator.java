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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.json.xe.$json_boolean;
import org.safris.commons.json.xe.$json_name;
import org.safris.commons.json.xe.$json_number;
import org.safris.commons.json.xe.$json_property;
import org.safris.commons.json.xe.$json_ref;
import org.safris.commons.json.xe.$json_string;
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

    for (final json_json._object object : json._object())
      objectNameToObject.put(object._name$().text(), object);

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

    for (final json_json._object object : json._object())
      out += writeJavaClass(bundleName, object, outDir);

    out += "\n\n  private " + bundleName + "() {";
    out += "\n  }";
    out += "\n}";
    try (final FileOutputStream fos = new FileOutputStream(new File(outDir, bundleName + ".java"))) {
      fos.write(out.toString().getBytes());
    }
  }

  private static final Map<String,json_json._object> objectNameToObject = new HashMap<String,json_json._object>();

  private static String getType(final $json_property property) {
    if (property instanceof $json_string)
      return String.class.getName();

    if (property instanceof $json_number)
      return Number.class.getName();

    if (property instanceof $json_boolean)
      return Boolean.class.getName();

    if (property instanceof $json_ref)
      return Strings.toClassCase((($json_ref)property)._type$().text());

    throw new UnsupportedOperationException("Unknown type: " + property.typeName());
  }

  private static String getPropertyName(final $json_property property) {
    if (property instanceof $json_name)
      return (($json_name)property)._name$().text();

    if (property instanceof $json_ref)
      return (($json_ref)property)._type$().text();

    throw new UnsupportedOperationException("Unexpected type: " + property.typeName());
  }

  private static String getInstanceName(final $json_property property) {
    return Strings.toInstanceCase(getPropertyName(property));
  }

  private static String writeField(final $json_property property) {
    final String valueName = getPropertyName(property);
    final boolean isArray = property._array$().text() != null && property._array$().text();
    final String rawType = getType(property);
    final String type = isArray ? Collection.class.getName() + "<" + rawType + ">" : rawType;

    final String instanceName = getInstanceName(property);

    String out = "";
    out += "\n\n    public final " + Property.class.getName() + "<" + type + "> " + instanceName + " = new " + Property.class.getName() + "<" + type + ">(this, (" + Binding.class.getName() + "<" + type + ">)bindings.get(\"" + valueName + "\"));";
    out += "\n\n    public " + type + " " + instanceName + "() {";
    out += "\n      return get(" + instanceName + ");";
    out += "\n    }";
    out += "\n\n    public void " + instanceName + "(final " + type + " value) {";
    out += "\n      set(" + instanceName + ", value);";
    out += "\n    }";
    if (isArray) {
      out += "\n\n    public void " + instanceName + "(final " + rawType + " ... value) {";
      out += "\n      set(" + instanceName + ", " + Arrays.class.getName() + ".asList(value));";
      out += "\n    }";
    }
    return out;
  }

  private static String writeEncode(final $json_property property) {
    final String valueName = getPropertyName(property);
    final String instanceName = getInstanceName(property);
    String out = "";
    if (property._required$().text()) {
      out += "\n      if (!wasSet(" + instanceName + "))";
      out += "\n        throw new " + EncodeException.class.getName() + "(\"\\\"" + valueName + "\\\" is required\", this);\n";
    }

    if (!property._null$().text()) {
      out += "\n      if (wasSet(" + instanceName + ") && get(" + instanceName + ") == null)";
      out += "\n        throw new " + EncodeException.class.getName() + "(\"\\\"" + valueName + "\\\" cannot be null\", this);\n";
    }

    out += "\n      if (wasSet(" + instanceName + "))";
    out += "\n        out.append(\",\\n\").append(pad(depth)).append(\"\\\"" + valueName + "\\\": \").append(";
    if (!property._array$().isNull() && property._array$().text())
      return out + "tokenize(encode(" + instanceName + "), depth + 1));\n";

    if (property instanceof $json_ref)
      return out + instanceName + " != null && get(" + instanceName + ") != null ? encode(encode(" + instanceName + "), depth + 1) : \"null\");\n";

    if (property instanceof $json_string)
      return out + instanceName + " != null && get(" + instanceName + ") != null ? \"\\\"\" + encode(" + instanceName + ") + \"\\\"\" : \"null\");\n";

    return out + "encode(" + instanceName + "));\n";
  }

  private static String writeJavaClass(final String bundleName, final json_json._object object, final File outDir) {
    final String objectName = object._name$().text();
    String out = "";

    final String className = Strings.toClassCase(objectName);
    out += "\n\n  public static" + (object._abstract$().text() ? " abstract" : "") + " class " + className + " extends " + (!object._extends$().isNull() ? Strings.toClassCase(object._extends$().text()) : JSObject.class.getName()) + " {";
    out += "\n    private static final " + String.class.getName() + " _name = \"" + objectName + "\";\n";
    out += "\n    private static final " + Map.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>> bindings = new " + HashMap.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>>(" + (object._property() != null ? object._property().size() : 0) + ");";

    out += "\n    static {";
    out += "\n      registerBinding(_name, " + className + ".class);";
    if (object._property() != null) {
      out += "\n      try {";
      for (final $json_property property : object._property()) {
        final String valueName = getPropertyName(property);
        final String rawType = getType(property);
        final boolean isArray = property._array$().text() != null && property._array$().text();
        final String type = isArray ? Collection.class.getName() + "<" + rawType + ">" : rawType;

        out += "\n        bindings.put(\"" + valueName + "\", new " + Binding.class.getName() + "<" + type + ">(\"" + valueName + "\", " + className + ".class.getDeclaredField(\"" + getInstanceName(property) + "\"), " + rawType + ".class, " + object._abstract$().text() + ", " + isArray + ", " + property._required$().text() + ", " + !property._null$().text();
        if (property instanceof $json_string) {
          final $json_string string = ($json_string)property;
          if (string._pattern$().text() != null)
            out += ", new " + PatternValidator.class.getName() + "(\"" + XMLText.unescapeXMLText(string._pattern$().text()).replace("\\", "\\\\") + "\")";
        }

        out += "));";
      }

      out += "\n      }";
      out += "\n      catch (final " + ReflectiveOperationException.class.getName() + " e) {";
      out += "\n        throw new " + ExceptionInInitializerError.class.getName() + "(e);";
      out += "\n      }";
    }
    out += "\n    }\n";
    out += "\n    @" + Override.class.getName();
    out += "\n    protected " + Binding.class.getName() + "<?> _getBinding(final " + String.class.getName() + " name) {";
    if (!object._extends$().isNull()) {
      out += "\n      final " + Binding.class.getName() + " binding = super._getBinding(name);";
      out += "\n      if (binding != null)";
      out += "\n        return binding;\n";
    }
    out += "\n      return bindings.get(name);";
    out += "\n    }\n";
    out += "\n    @" + Override.class.getName();
    out += "\n    protected " + Collection.class.getName() + "<" + Binding.class.getName() + "<?>> _bindings() {";
    if (!object._extends$().isNull()) {
      out += "\n      final " + Collection.class.getName() + " bindings = new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<?>>();";
      out += "\n      bindings.addAll(super._bindings());";
      out += "\n      bindings.addAll(bindings);";
      out += "\n      return bindings;";
    }
    else {
      out += "\n      return bindings.values();";
    }
    out += "\n    }";
    out += "\n\n    @" + Override.class.getName();
    out += "\n    protected " + JSBundle.class.getName() + " _bundle() {";
    out += "\n      return " + bundleName + ".instance();";
    out += "\n    }";
    out += "\n\n    @" + Override.class.getName();
    out += "\n    protected " + String.class.getName() + " _name() {";
    out += "\n      return _name;";
    out += "\n    }";
    if (object._property() != null) {
      for (final $json_property property : object._property())
        out += writeField(property);

      out += "\n\n    @" + Override.class.getName();
      out += "\n    protected " + String.class.getName() + " _encode(final int depth) {";
      out += "\n      final " + StringBuilder.class.getName() + " out = new " + StringBuilder.class.getName() + "(super._encode(depth));";
      for (int i = 0; i < object._property().size(); i++)
        out += writeEncode(object._property(i));

      out += "\n      return out." + (!object._extends$().isNull() ? "toString()" : "substring(2)") + ";\n    }";
    }

    out += "\n\n    @" + Override.class.getName();
    out += "\n    public " + String.class.getName() + " toString() {";
    out += "\n      return encode(this, 1);";
    out += "\n    }";

    out += "\n\n    @" + Override.class.getName();
    out += "\n    public boolean equals(final " + Object.class.getName() + " obj) {";
    out += "\n      if (obj == this)";
    out += "\n        return true;";
    out += "\n\n      if (!(obj instanceof " + className + ")" + (!object._extends$().isNull() ? " || !super.equals(obj)" : "") + ")";
    out += "\n        return false;\n";
    if (object._property() != null) {
      out += "\n      final " + className + " that = (" + className + ")obj;";
      for (final $json_property property : object._property()) {
        final String instanceName = getInstanceName(property);
        out += "\n      if (that." + instanceName + " != null ? that." + instanceName + ".equals(" + instanceName + ") : " + instanceName + " == null)";
        out += "\n        return false;\n";
      }
    }
    out += "\n      return true;";
    out += "\n    }";

    out += "\n\n    @" + Override.class.getName();
    out += "\n    public int hashCode() {";
    if (object._property() != null) {
      out += "\n      int hashCode = " + className.hashCode() + (!object._extends$().isNull() ? " ^ 31 * super.hashCode()" : "") + ";";
      for (final $json_property property : object._property()) {
        final String instanceName = getInstanceName(property);
        out += "\n      if (" + instanceName + " != null)";
        out += "\n        hashCode ^= 31 * " + instanceName + ".hashCode();\n";
      }
      out += "\n      return hashCode;";
    }
    else {
      out += "\n      return " + className.hashCode() + (!object._extends$().isNull() ? " ^ 31 * super.hashCode()" : "") + ";";
    }
    out += "\n    }";

    out += "\n  }";

    return out.toString();
  }
}