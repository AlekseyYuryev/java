/* Copyright (c) 2017 lib4j
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

import org.lib4j.lang.Strings;
import org.libx4j.jjb.jsonx.xe.$jsonx_element;
import org.libx4j.jjb.jsonx.xe.$jsonx_maxCardinality;
import org.libx4j.jjb.jsonx.xe.$jsonx_object;
import org.libx4j.jjb.jsonx.xe.jsonx_jsonx;
import org.libx4j.jjb.runtime.ArrayProperty;
import org.libx4j.jjb.runtime.BooleanProperty;
import org.libx4j.jjb.runtime.NumberProperty;
import org.libx4j.jjb.runtime.ObjectProperty;
import org.libx4j.jjb.runtime.StringProperty;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.Bindings;
import org.w3.x2001.xmlschema.xe.$xs_nonNegativeInteger;

abstract class Element extends Factor implements Annullable, Nameable, Recurrable, Requirable {
  protected static Element toElement(final Registry registry, final ComplexModel declarer, final Field field, final Object object) {
    final BooleanProperty booleanProperty = field.getDeclaredAnnotation(BooleanProperty.class);
    if (booleanProperty != null)
      return BooleanModel.referenceOrDeclare(registry, declarer, booleanProperty, field);

    final NumberProperty numberProperty = field.getDeclaredAnnotation(NumberProperty.class);
    if (numberProperty != null)
      return NumberModel.referenceOrDeclare(registry, declarer, numberProperty, field);

    final StringProperty stringProperty = field.getDeclaredAnnotation(StringProperty.class);
    if (stringProperty != null)
      return StringModel.referenceOrDeclare(registry, declarer, stringProperty, field);

    final ObjectProperty objectProperty = field.getDeclaredAnnotation(ObjectProperty.class);
    if (objectProperty != null)
      return ObjectModel.referenceOrDeclare(registry, declarer, objectProperty, field);

    final ArrayProperty arrayProperty = field.getDeclaredAnnotation(ArrayProperty.class);
    if (arrayProperty != null)
      return ArrayModel.referenceOrDeclare(registry, declarer, arrayProperty, field);

    return null;
  }

  protected static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }

  protected static String getShortName(final String longName, final String pacakgeName) {
    return pacakgeName != null && longName.startsWith(pacakgeName) ? longName.substring(pacakgeName.length() + 1) : longName;
  }

  protected static final Function<Binding,String> elementXPath = new Function<Binding,String>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof jsonx_jsonx._boolean)
        name = ((jsonx_jsonx._boolean)t)._name$().text();
      else if (t instanceof jsonx_jsonx._number)
        name = ((jsonx_jsonx._number)t)._name$().text();
      else if (t instanceof jsonx_jsonx._string)
        name = ((jsonx_jsonx._string)t)._name$().text();
      else if (t instanceof jsonx_jsonx._array)
        name = ((jsonx_jsonx._array)t)._name$().text();
      else if (t instanceof jsonx_jsonx._object)
        name = ((jsonx_jsonx._object)t)._class$().text();
      else if (t instanceof $jsonx_object._boolean)
        name = (($jsonx_object._boolean)t)._name$().text();
      else if (t instanceof $jsonx_object._number)
        name = (($jsonx_object._number)t)._name$().text();
      else if (t instanceof $jsonx_object._string)
        name = (($jsonx_object._string)t)._name$().text();
      else if (t instanceof $jsonx_object._array)
        name = (($jsonx_object._array)t)._name$().text();
      else if (t instanceof $jsonx_object._object)
        name = (($jsonx_object._object)t)._name$().text();
      else if (t instanceof $jsonx_object._property)
        name = (($jsonx_object._property)t)._name$().text();
      else
        name = null;

      return name != null ? t.name().getLocalPart() + "[@" + (t instanceof $jsonx_object ? "class" : "name=") + name + "]" : t.name().getLocalPart();
    }
  };

  private static Integer parseMaxCardinality(final int minCardinality, final $jsonx_maxCardinality maxCardinality) {
    final Integer max = "unbounded".equals(maxCardinality.text()) ? null : Integer.parseInt(maxCardinality.text());
    if (max != null && minCardinality > max)
      throw new ValidationException(Bindings.getXPath(((org.libx4j.xsb.runtime.Attribute)maxCardinality).owner(), elementXPath) + ": minOccurs=\"" + minCardinality + "\" > maxOccurs=\"" + max + "\"");

    return max;
  }

  private final String name;
  private final Boolean required;
  private final Boolean nullable;
  private final Integer minOccurs;
  private final Integer maxOccurs;

  public Element(final String name, final Boolean required, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs) {
    this.name = name;
    this.required = required;
    this.nullable = nullable;
    this.minOccurs = minOccurs;
    this.maxOccurs = maxOccurs != null && maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
  }

  // Annullable, Recurrable
  public Element(final String name, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs) {
    this(name, null, nullable, minOccurs, maxOccurs);
  }

  public Element(final $jsonx_element binding, final String name, final boolean nullable, final $xs_nonNegativeInteger minOccurs, final $jsonx_maxCardinality maxOccurs) {
    this(name, nullable, minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs));
  }

  // Annullable, Nameable, Requirable
  public Element(final String name, final boolean required, final boolean nullable) {
    this(name, required, nullable, null, null);
  }

  // Nameable
  public Element(final String name) {
    this(name, null, null, null, null);
  }

  public Element(final $jsonx_element binding, final String name) {
    this(name);
  }

  public Element(final Element copy) {
    this.name = copy.name;
    this.required = copy.required;
    this.nullable = copy.nullable;
    this.minOccurs = copy.minOccurs;
    this.maxOccurs = copy.maxOccurs;
  }

  @Override
  public final String name() {
    return this.name;
  }

  public final String instanceName() {
    return Strings.toInstanceCase(name());
  }

  @Override
  public final Boolean required() {
    return required;
  }

  @Override
  public final Boolean nullable() {
    return nullable;
  }

  @Override
  public final Integer minOccurs() {
    return minOccurs;
  }

  @Override
  public final Integer maxOccurs() {
    return maxOccurs;
  }

  @Override
  protected String toJSON(final String pacakgeName) {
    final StringBuilder string = new StringBuilder("  type: \"").append(getClass().getSimpleName().toLowerCase()).append('"');
    if (name != null)
      string.append(",\n  name: \"").append(name).append('"');

    if (required != null)
      string.append(",\n  required: ").append(required);

    if (nullable != null)
      string.append(",\n  nullable: ").append(nullable);

    if (minOccurs != null)
      string.append(",\n  minOccurs: ").append(minOccurs);

    if (maxOccurs != null)
      string.append(",\n  maxOccurs: ").append(maxOccurs);

    return string.toString();
  }

  @Override
  protected String toJSONX(final String pacakgeName) {
    final StringBuilder string = new StringBuilder();
    if (name != null)
      string.append(" name=\"").append(name).append('"');

    if (required != null && !required)
      string.append(" required=\"").append(required).append('"');

    if (nullable != null && !nullable)
      string.append(" nullable=\"").append(nullable).append('"');

    if (minOccurs != null && minOccurs != 0)
      string.append(" minOccurs=\"").append(minOccurs).append('"');

    if (maxOccurs != null)
      string.append(" maxOccurs=\"").append(maxOccurs).append('"');

    return string.toString();
  }

  @Override
  public final String toJSON() {
    return toJSON(null);
  }

  @Override
  public final String toJSONX() {
    return toJSONX(null);
  }

  protected String toAnnotation(final boolean full) {
    final StringBuilder string = new StringBuilder();
//    if (name != null)
//      string.append(", name=\"").append(name).append('"');

    if (required != null && !required)
      string.append(", required=").append(required);

    if (nullable != null && !nullable)
      string.append(", nullable=").append(nullable);

    if (minOccurs != null && minOccurs != 0)
      string.append(", minOccurs=").append(minOccurs);

    if (maxOccurs != null)
      string.append(", maxOccurs=").append(maxOccurs);

    return string.length() == 0 ? "" : string.substring(2);
  }

  protected String toField() {
    final StringBuilder string = new StringBuilder();
    string.append("@").append(propertyAnnotation().getName());
    final String annotation = toAnnotation(true);
    if (annotation.length() > 0)
      string.append("(").append(annotation).append(")");

    string.append('\n');
    string.append("public ");
    string.append(className());
    string.append(" ").append(name());
    string.append(";");
    return string.toString();
  }

  protected abstract String className();
  protected abstract Class<? extends Annotation> propertyAnnotation();
  protected abstract Class<? extends Annotation> elementAnnotation();
  protected abstract Element normalize(final Registry registry);
}