/* Copyright (c) 2015 lib4j
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

package org.libx4j.jjb.generator;

public class Generator {
//  private static final Logger logger = LoggerFactory.getLogger(Generator.class);
//
//  public static void main(final String[] args) throws Exception {
//    Generator.generate(Resources.getResource(args[0]).getURL(), new File(args[1]), false);
//  }
//
//  public static void generate(final URL url, final File destDir, final boolean compile) throws GeneratorExecutionException, IOException, XMLException {
//    final jsonx_jsonx json = (jsonx_jsonx)Bindings.parse(url);
//    if (json._object() == null) {
//      logger.error("Missing <object> elements: " + url.toExternalForm());
//      return;
//    }
//
//    final String packageName = "jjb";
//    final File outDir = new File(destDir, packageName.replace('.', '/'));
//    if (!outDir.exists() && !outDir.mkdirs())
//      throw new IOException("Unable to mkdirs: " + outDir.getAbsolutePath());
//
//    for (final jsonx_jsonx._object object : json._object())
//      objectNameToObject.put(object._name$().text(), object);
//
//    final String name = json._name$().text();
//
//    String out = "";
//
//    out += "package " + packageName + ";\n";
//    if (!json._description(0).isNull())
//      out += "\n/**\n * " + json._description(0).text() + "\n */";
//
//    out += "\n@" + SuppressWarnings.class.getName() + "(\"all\")";
//    out += "\npublic class " + name + " extends " + JSBundle.class.getName() + " {";
//    out += "\n  public static final " + String.class.getName() + " mimeType = \"" + json._mimeType$().text() + "\";";
//    out += "\n  private static " + name + " instance = null;";
//    out += "\n\n  protected static " + name + " instance() {";
//    out += "\n    return instance == null ? instance = new " + name + "() : instance;";
//    out += "\n  }";
//
//    out += "\n\n  @" + Override.class.getName();
//    out += "\n  protected " + String.class.getName() + " getSpec() {";
//    out += "\n    return \"" + DOMs.domToString(json.marshal(), DOMStyle.INDENT).replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\";";
//    out += "\n  }";
//
//    final Stack<String> parents = new Stack<String>();
//    parents.push(name);
//    for (final jsonx_jsonx._object object : json._object())
//      out += writeJavaClass(parents, object, 0);
//
//    out += "\n\n  private " + name + "() {";
//    out += "\n  }";
//    out += "\n}";
//    try (final FileOutputStream fos = new FileOutputStream(new File(outDir, name + ".java"))) {
//      fos.write(out.toString().getBytes());
//    }
//
//    if (compile) {
//      try {
//        new JavaCompiler(destDir).compile(destDir);
//      }
//      catch (final CompilationException e) {
//        throw new UnsupportedOperationException(e);
//      }
//
//      ClassLoaders.addURL((URLClassLoader)ClassLoader.getSystemClassLoader(), destDir.toURI().toURL());
//    }
//  }
//
//  private static final Map<String,jsonx_jsonx._object> objectNameToObject = new HashMap<String,jsonx_jsonx._object>();
//
//  private static String getType(final Stack<String> parent, final $jsonx_property property) {
//    if (property instanceof $jsonx_string)
//      return String.class.getName();
//
//    if (property instanceof $jsonx_number) {
//      final $jsonx_number numberProperty = ($jsonx_number)property;
//      if ($jsonx_number._form$.integer.text().equals(numberProperty._form$().text()))
//        return BigInteger.class.getName();
//
//      if ($jsonx_number._form$.real.text().equals(numberProperty._form$().text()))
//        return BigDecimal.class.getName();
//
//      throw new UnsupportedOperationException("Unknown number form: " + numberProperty._form$().text());
//    }
//
//    if (property instanceof $jsonx_boolean)
//      return Boolean.class.getName();
//
//    if (property instanceof $jsonx_object) {
//      final $jsonx_object object = ($jsonx_object)property;
//      if (!object._extends$().isNull() && !property.elementIterator().hasNext())
//        return Strings.toClassCase(object._extends$().text());
//
//      return Collections.toString(parent, ".") + "." + Strings.toClassCase((($jsonx_object)property)._name$().text());
//    }
//
//    throw new UnsupportedOperationException("Unknown type: " + property.getClass().getName());
//  }
//
//  private static String getPropertyName(final $jsonx_property property) {
//    if (property instanceof $jsonx_named)
//      return (($jsonx_named)property)._name$().text();
//
//    if (property instanceof $jsonx_object)
//      return (($jsonx_object)property)._name$().text();
//
//    throw new UnsupportedOperationException("Unsupported type: " + property);
//  }
//
//  private static String getInstanceName(final $jsonx_element element) {
//    $jsonx_property property;
//    if (element instanceof $jsonx_array) {
//      $jsonx_array array = ($jsonx_array)element;
//      while ((property = array._property(0)).isNull())
//        array = array._array(0);
//    }
//    else if (element instanceof $jsonx_property) {
//      property = ($jsonx_property)element;
//    }
//    else {
//      throw new UnsupportedOperationException("Unsupported type: " + element.getClass().getName());
//    }
//
//    return Strings.toInstanceCase(getPropertyName(property));
//  }
//
//  private static String declareField(final String type, final int arrayDepth) {
//    final StringBuilder builder = new StringBuilder(type);
//    for (int i = 0; i < arrayDepth; i++) {
//      builder.insert(0, List.class.getName() + "<");
//      builder.append(">");
//    }
//
//    return builder.toString();
//  }
//
//  private static String writeProperty(final Stack<String> parent, final $jsonx_element element, final int depth) {
//    if (element instanceof $jsonx_property)
//      return writeProperty(parent, ($jsonx_property)element, 0, depth);
//
//    if (element instanceof $jsonx_array) {
//      $jsonx_array array = ($jsonx_array)element;
//      int arrayDepth = 1;
//      while (!array._array(0).isNull()) {
//        array = array._array(0);
//        ++arrayDepth;
//      }
//
//      return writeProperty(parent, array._property(0), arrayDepth, depth);
//    }
//
//    throw new UnsupportedOperationException("Unsupported type: " + element.getClass().getName());
//  }
//
//  private static String writeProperty(final Stack<String> parent, final $jsonx_property property, final int arrayDepth, final int depth) {
//    final String valueName = getPropertyName(property);
//    final String type = declareField(getType(parent, property), arrayDepth);
//
//    final String instanceName = getInstanceName(property);
//
//    final String pad = Strings.padFixed("", depth * 2, false);
//    String out = "\n";
//    if (!property._description(0).isNull())
//      out += "\n" + pad + "   /**\n" + pad + "    * " + property._description(0).text() + "\n" + pad + "    */";
//
//    out += "\n" + pad + "   public final " + Property.class.getName() + "<" + type + "> " + instanceName + " = new " + Property.class.getName() + "<" + type + ">(this, (" + Binding.class.getName() + "<" + type + ">)bindings.get(\"" + valueName + "\"));";
//    return out;
//  }
//
//  private static $jsonx_property getProperty($jsonx_array array) {
//    $jsonx_property property;
//    while ((property = array._property(0)).isNull())
//      array = array._array(0);
//
//    return property;
//  }
//
//  private static String writeEncode(final $jsonx_element element, final int depth) {
//    $jsonx_property property;
//    if (element instanceof $jsonx_array)
//      property = getProperty(($jsonx_array)element);
//    else if (element instanceof $jsonx_property)
//      property = ($jsonx_property)element;
//    else
//      throw new UnsupportedOperationException("Unsupported type: " + element.getClass().getName());
//
//    final String valueName = getPropertyName(property);
//    final String instanceName = getInstanceName(property);
//    final String pad = Strings.padFixed("", depth * 2, false);
//
//    String out = "";
//    if (property._required$().text()) {
//      out += "\n" + pad + "     if (!" + instanceName + ".present())";
//      out += "\n" + pad + "       throw new " + EncodeException.class.getName() + "(\"\\\"" + valueName + "\\\" is required\", this);\n";
//    }
//
//    if (!property._null$().text()) {
//      out += "\n" + pad + "     if (" + instanceName + ".present() && " + instanceName + ".get() == null)";
//      out += "\n" + pad + "       throw new " + EncodeException.class.getName() + "(\"\\\"" + valueName + "\\\" cannot be null\", this);\n";
//    }
//
//    out += "\n" + pad + "     if (" + instanceName + ".present() || required(" + instanceName + "))";
//    out += "\n" + pad + "       out.append(\",\\n\").append(pad(depth)).append(\"\\\"" + valueName + "\\\": \").append(";
//    if (element instanceof $jsonx_array)
//      return out + "toString(encode(" + instanceName + "), _getBinding(\"" + instanceName + "\").cardinality, depth + 1));\n";
//
//    if (property instanceof $jsonx_object)
//      return out + "" + instanceName + ".get() != null ? encode(encode(" + instanceName + "), depth + 1) : \"null\");\n";
//
//    if (property instanceof $jsonx_string)
//      return out + "" + instanceName + ".get() != null ? \"\\\"\" + encode(" + instanceName + ") + \"\\\"\" : \"null\");\n";
//
//    return out + "encode(" + instanceName + "));\n";
//  }
//
//  private static String writeBinding(final String pad, final String className, final ObjectType objectType, final Stack<String> parent, final $jsonx_element element) throws GeneratorExecutionException {
//    $jsonx_property property;
//    Integer[] cardinality = null;
//    if (element instanceof $jsonx_array) {
//      final List<Integer> spec = new ArrayList<Integer>();
//      $jsonx_array array = ($jsonx_array)element;
//      do {
//        spec.add(array._null$().text() ? 1 : 0);
//        spec.add(array._minOccurs$().text());
//        spec.add("unbounded".equals(array._maxOccurs$().text()) ? null : Integer.parseInt(array._maxOccurs$().text()));
//      }
//      while ((property = array._property(0)).isNull() && !(array = array._array(0)).isNull());
//
//      cardinality = spec.toArray(new Integer[spec.size()]);
//    }
//    else if (element instanceof $jsonx_property) {
//      property = ($jsonx_property)element;
//    }
//    else {
//      throw new UnsupportedOperationException("Unknown type: " + element.getClass().getName());
//    }
//
//    final String propertyName = getPropertyName(property);
//    final String rawType = getType(parent, property);
//    final String type = declareField(rawType, cardinality == null ? 0 : cardinality.length / 3);
//
//    String out = "\n" + pad + "       bindings.put(\"" + propertyName + "\", new " + Binding.class.getName() + "<" + type + ">(\"" + propertyName + "\", " + className + ".class.getDeclaredField(\"" + getInstanceName(property) + "\"), " + rawType + ".class, " + objectType.isAbstract + ", " + property._required$().text() + ", " + !property._null$().text() + ", " + (cardinality == null ? "null" : "new " + Cardinality.class.getName() + "(" + Arrays.toString(cardinality, ", ") + ")") + (property instanceof $jsonx_string ? ", " + (($jsonx_string)property)._urlDecode$().text() + ", " + (($jsonx_string)property)._urlEncode$().text() : "");
//    if (property instanceof $jsonx_string) {
//      final $jsonx_string string = ($jsonx_string)property;
//      if (string._pattern$().text() != null || string._length$().text() != null)
//        out += ", new " + StringValidator.class.getName() + "(" + (string._pattern$().isNull() ? "null" : "\"" + XMLText.unescapeXMLText(string._pattern$().text()).replace("\\", "\\\\").replace("\"", "\\\"") + "\"") + ", " + (string._length$().isNull() ? "null" : string._length$().text()) + ")";
//    }
//    else if (property instanceof $jsonx_number) {
//      final $jsonx_number string = ($jsonx_number)property;
//      if (_form$.integer.text().equals(string._form$().text()) || string._min$().text() != null || string._max$().text() != null) {
//        if (string._min$().text() != null && string._max$().text() != null && string._min$().text() > string._max$().text())
//          throw new GeneratorExecutionException("min (" + string._min$().text() + ") > max (" + string._max$().text() + ") on property: " + objectType.objectName + "." + propertyName);
//
//        out += ", new " + NumberValidator.class.getName() + "(" + _form$.integer.text().equals(string._form$().text()) + ", " + (string._min$().isNull() ? "null" : string._min$().text().intValue()) + ", " + (string._max$().isNull() ? "null" : string._max$().text().intValue()) + ")";
//      }
//    }
//
//    out += "));";
//
//    return out;
//  }
//
//  private static String writeJavaClass(final Stack<String> parent, final $jsonx_element object, final int depth) throws GeneratorExecutionException {
//    final ObjectType objectType;
//    if (object instanceof $jsonx_object) {
//      final $jsonx_object object1 = ($jsonx_object)object;
//      if (!object1._extends$().isNull() && !object.elementIterator().hasNext() && !objectNameToObject.get(object1._extends$().text())._abstract$().text())
//        return "";
//
//      objectType = new ObjectType(object1);
//    }
//    else if (object instanceof jsonx_jsonx._object) {
//      objectType = new ObjectType((jsonx_jsonx._object)object);
//    }
//    else {
//      throw new UnsupportedOperationException("Unsupported object type: " + object.getClass().getName());
//    }
//
//    final String className = Strings.toClassCase(objectType.objectName);
//    parent.add(className);
//
//    final String pad = Strings.padFixed("", depth * 2, false);
//    String out = "\n";
//    if (!object._description(0).isNull())
//      out += "\n" + pad + " /**\n" + pad + "  * " + object._description(0).text() + "\n" + pad + "  */";
//
//    out += "\n" + pad + " public static" + (objectType.isAbstract ? " abstract" : "") + " class " + className + " extends " + (objectType.extendsPropertyName != null ? parent.get(0) + "." + Strings.toClassCase(objectType.extendsPropertyName) : JSObject.class.getName()) + " {";
//    out += "\n" + pad + "   private static final " + String.class.getName() + " _name = \"" + objectType.objectName + "\";\n";
//    out += "\n" + pad + "   private static final " + Map.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>> bindings = new " + HashMap.class.getName() + "<" + String.class.getName() + "," + Binding.class.getName() + "<?>>(" + objectType.getNumProperties() + ");";
//
//    out += "\n" + pad + "   static {";
//    out += "\n" + pad + "     registerBinding(_name, " + className + ".class);";
//    Iterator<? extends $jsonx_element> propertyIterator = objectType.propertyIterator();
//    if (propertyIterator.hasNext()) {
//      out += "\n" + pad + "     try {";
//      do {
//        final $jsonx_element element = propertyIterator.next();
//        out += writeBinding(pad, className, objectType, parent, element);
//      }
//      while (propertyIterator.hasNext());
//
//      out += "\n" + pad + "     }";
//      out += "\n" + pad + "     catch (final " + ReflectiveOperationException.class.getName() + " e) {";
//      out += "\n" + pad + "       throw new " + ExceptionInInitializerError.class.getName() + "(e);";
//      out += "\n" + pad + "     }";
//    }
//    out += "\n" + pad + "   }";
//
//    propertyIterator = objectType.propertyIterator();
//    if (propertyIterator.hasNext()) {
//      do {
//        final $jsonx_element element = propertyIterator.next();
//        if (element instanceof $jsonx_object)
//          out += writeJavaClass(parent, element, depth + 1);
//        else if (element instanceof $jsonx_array) {
//          final $jsonx_property property = getProperty(($jsonx_array)element);
//          if (property instanceof $jsonx_object)
//            out += writeJavaClass(parent, property, depth + 1);
//        }
//      }
//      while (propertyIterator.hasNext());
//    }
//
//    out += "\n\n" + pad + "   public " + className + "(final " + JSObject.class.getName() + " object) {";
//    out += "\n" + pad + "     super(object);";
//    propertyIterator = objectType.propertyIterator();
//    if (propertyIterator.hasNext()) {
//      out += "\n" + pad + "     if (!(object instanceof " + className + "))";
//      out += "\n" + pad + "       return;";
//      out += "\n\n" + pad + "     final " + className + " that = (" + className + ")object;";
//      do {
//        final $jsonx_element element = propertyIterator.next();
//        final String instanceName = getInstanceName(element);
//        out += "\n" + pad + "     clone(this." + instanceName + ", that." + instanceName + ");";
//      }
//      while (propertyIterator.hasNext());
//    }
//
//    out += "\n" + pad + "   }";
//
//    out += "\n\n" + pad + "   public " + className + "() {";
//    out += "\n" + pad + "     super();";
//    out += "\n" + pad + "   }";
//
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   protected boolean _skipUnknown() {";
//      out += "\n" + pad + "     return " + objectType.skipUnknown + ";";
//    out += "\n" + pad + "   }\n";
//
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   protected " + Binding.class.getName() + "<?> _getBinding(final " + String.class.getName() + " name) {";
//    if (objectType.extendsPropertyName != null) {
//      out += "\n" + pad + "     final " + Binding.class.getName() + " binding = super._getBinding(name);";
//      out += "\n" + pad + "     return binding != null ? binding : bindings.get(name);";
//    }
//    else {
//      out += "\n" + pad + "     return bindings.get(name);";
//    }
//    out += "\n" + pad + "   }\n";
//    out += "\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   protected " + Collection.class.getName() + "<" + Binding.class.getName() + "<?>> _bindings() {";
//    if (objectType.extendsPropertyName != null) {
//      out += "\n" + pad + "     final " + List.class.getName() + " bindings = new " + ArrayList.class.getName() + "<" + Binding.class.getName() + "<?>>();";
//      out += "\n" + pad + "     bindings.addAll(super._bindings());";
//      out += "\n" + pad + "     bindings.addAll(this.bindings.values());";
//      out += "\n" + pad + "     return bindings;";
//    }
//    else {
//      out += "\n" + pad + "     return bindings.values();";
//    }
//    out += "\n" + pad + "   }";
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   protected " + JSBundle.class.getName() + " _bundle() {";
//    out += "\n" + pad + "     return " + parent.get(0) + ".instance();";
//    out += "\n" + pad + "   }";
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   protected " + String.class.getName() + " _name() {";
//    out += "\n" + pad + "     return _name;";
//    out += "\n" + pad + "   }";
//    propertyIterator = objectType.propertyIterator();
//    if (propertyIterator.hasNext()) {
//      do
//        out += writeProperty(parent, propertyIterator.next(), depth);
//      while (propertyIterator.hasNext());
//
//      out += "\n\n" + pad + "   @" + Override.class.getName();
//      out += "\n" + pad + "   protected " + String.class.getName() + " _encode(final int depth) {";
//      out += "\n" + pad + "     final " + StringBuilder.class.getName() + " out = new " + StringBuilder.class.getName() + "(super._encode(depth));";
//      out += "\n" + pad + "     final int startLength = out.length();";
//      propertyIterator = objectType.propertyIterator();
//      while (propertyIterator.hasNext())
//        out += writeEncode(propertyIterator.next(), depth);
//
//      out += "\n" + pad + "     return startLength == out.length() || startLength != 0 ? out.toString() : out.substring(2);\n" + pad + "   }";
//    }
//
//    if (!objectType.isAbstract) {
//      out += "\n\n" + pad + "   @" + Override.class.getName();
//      out += "\n" + pad + "   public " + className + " clone() {";
//      out += "\n" + pad + "     return new " + className + "(this);";
//      out += "\n" + pad + "   }";
//    }
//
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   public boolean equals(final " + Object.class.getName() + " obj) {";
//    out += "\n" + pad + "     if (obj == this)";
//    out += "\n" + pad + "       return true;";
//    out += "\n\n" + pad + "     if (!(obj instanceof " + className + ")" + (objectType.extendsPropertyName != null ? " || !super.equals(obj)" : "") + ")";
//    out += "\n" + pad + "       return false;\n";
//    propertyIterator = objectType.propertyIterator();
//    if (propertyIterator.hasNext()) {
//      out += "\n" + pad + "     final " + className + " that = (" + className + ")obj;";
//      do {
//        final $jsonx_element element = propertyIterator.next();
//        final String instanceName = getInstanceName(element);
//        out += "\n" + pad + "     if (that." + instanceName + " != null ? !that." + instanceName + ".equals(" + instanceName + ") : " + instanceName + " != null)";
//        out += "\n" + pad + "       return false;\n";
//      }
//      while (propertyIterator.hasNext());
//    }
//    out += "\n" + pad + "     return true;";
//    out += "\n" + pad + "   }";
//
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   public int hashCode() {";
//    propertyIterator = objectType.propertyIterator();
//    if (propertyIterator.hasNext()) {
//      out += "\n" + pad + "     int hashCode = " + className.hashCode() + (objectType.extendsPropertyName != null ? " ^ 31 * super.hashCode()" : "") + ";";
//      do {
//        final $jsonx_element element = propertyIterator.next();
//        final String instanceName = getInstanceName(element);
//        out += "\n" + pad + "     if (" + instanceName + " != null)";
//        out += "\n" + pad + "       hashCode ^= 31 * " + instanceName + ".hashCode();\n";
//      }
//      while (propertyIterator.hasNext());
//      out += "\n" + pad + "     return hashCode;";
//    }
//    else {
//      out += "\n" + pad + "     return " + className.hashCode() + (objectType.extendsPropertyName != null ? " ^ 31 * super.hashCode()" : "") + ";";
//    }
//    out += "\n" + pad + "   }";
//
//    out += "\n\n" + pad + "   @" + Override.class.getName();
//    out += "\n" + pad + "   public " + String.class.getName() + " toString() {";
//    out += "\n" + pad + "     return encode(this, 1);";
//    out += "\n" + pad + "   }";
//
//    out += "\n" + pad + " }";
//
//    parent.pop();
//    return out.toString();
//  }
}