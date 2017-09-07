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
import java.math.BigDecimal;
import java.math.BigInteger;

import org.lib4j.lang.IllegalAnnotationException;
import org.libx4j.jjb.jsonx.xe.$jsonx_array;
import org.libx4j.jjb.jsonx.xe.$jsonx_object;
import org.libx4j.jjb.jsonx.xe.jsonx_jsonx;
import org.libx4j.jjb.runtime.Form;
import org.libx4j.jjb.runtime.NumberElement;
import org.libx4j.jjb.runtime.NumberProperty;

class NumberModel extends SimpleModel {
  private final Form form;
  private final BigDecimal min;
  private final BigDecimal max;

  public static NumberModel reference(final Registry registry, final ComplexModel referrer, final $jsonx_array._number binding) {
    return new NumberModel(binding);
  }

  // Annullable, Recurrable
  public NumberModel(final $jsonx_array._number binding) {
    super(binding, binding._nullable$().text(), binding._minOccurs$(), binding._maxOccurs$());
    this.form = Form.valueOf(binding._form$().text().toUpperCase());
    this.min = binding._min$().text();
    this.max = binding._max$().text();
  }

  public static NumberModel reference(final Registry registry, final ComplexModel referrer, final $jsonx_object._number binding) {
    return new NumberModel(binding);
  }

  // Nameable, Annullable
  public NumberModel(final $jsonx_object._number binding) {
    super(binding, binding._name$().text(), binding._required$().text(), binding._nullable$().text());
    this.form = Form.valueOf(binding._form$().text().toUpperCase());
    this.min = binding._min$().text();
    this.max = binding._max$().text();
  }

  public static NumberModel declare(final Registry registry, final jsonx_jsonx._number binding) {
    return registry.declare(binding).value(new NumberModel(binding), null);
  }

  // Nameable
  public NumberModel(final jsonx_jsonx._number binding) {
    super(binding._name$().text(), null, null, null, null);
    this.form = Form.valueOf(binding._form$().text().toUpperCase());
    this.min = binding._min$().text();
    this.max = binding._max$().text();
  }

  public static NumberModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final NumberProperty numberProperty, final Field field) {
    return new NumberModel(numberProperty, field);
  }

  public NumberModel(final NumberProperty numberProperty, final Field field) {
    super(getName(numberProperty.name(), field), numberProperty.required(), numberProperty.nullable(), null, null);
    if (!Number.class.isAssignableFrom(field.getType()) && (!field.getType().isPrimitive() || field.getType() == char.class || numberProperty.nullable()))
      throw new IllegalAnnotationException(numberProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Number subtypes or primitive numeric non-nullable types.");

    this.form = numberProperty.form();
    this.min = numberProperty.min().length() == 0 ? null : new BigDecimal(numberProperty.min());
    this.max = numberProperty.max().length() == 0 ? null : new BigDecimal(numberProperty.max());
  }

  public static NumberModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final NumberElement numberElement) {
    return new NumberModel(numberElement);
  }

  public NumberModel(final NumberElement numberElement) {
    super(null, null, numberElement.nullable(), numberElement.minOccurs(), numberElement.maxOccurs());
    this.form = numberElement.form();
    this.min = numberElement.min().length() == 0 ? null : new BigDecimal(numberElement.min());
    this.max = numberElement.max().length() == 0 ? null : new BigDecimal(numberElement.max());
  }

  public NumberModel(final Element element, final NumberModel copy) {
    super(element);
    this.form = copy.form;
    this.min = copy.min;
    this.max = copy.max;
  }

  public final Form form() {
    return this.form;
  }

  public final BigDecimal min() {
    return this.min;
  }

  public final BigDecimal max() {
    return this.max;
  }

  @Override
  protected String className() {
    return form == Form.INTEGER ? BigInteger.class.getName() : BigDecimal.class.getName();
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return NumberProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return NumberElement.class;
  }

  @Override
  protected NumberModel merge(final PropertyElement propertyElement) {
    return new NumberModel(propertyElement, this);
  }

  @Override
  protected NumberModel normalize(final Registry registry) {
    return this;
  }

  @Override
  protected String toJSON(final String pacakgeName) {
    final StringBuilder string = new StringBuilder(super.toJSON(pacakgeName));
    if (string.length() > 0)
      string.insert(0, ",\n");

    if (form != null)
      string.append(",\n  form: \"").append(form.toString().toLowerCase()).append('"');

    if (min != null)
      string.append(",\n  min: ").append(min);

    if (max != null)
      string.append(",\n  max: ").append(max);

    return "{\n" + (string.length() > 0 ? string.substring(2) : string.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder string = new StringBuilder("<number");
    if (form != Form.REAL)
      string.append(" form=\"").append(form.toString().toLowerCase()).append('"');

    if (min != null)
      string.append(" min=\"").append(min).append('"');

    if (max != null)
      string.append(" max=\"").append(max).append('"');

    return string.append(super.toJSONX(pacakgeName)).append("/>").toString();
  }

  @Override
  protected String toAnnotation(final boolean full) {
    final StringBuilder string = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (form != Form.REAL)
      string.append(", form=").append(Form.class.getName()).append('.').append(form);

    if (min != null)
      string.append(", min=\"").append(min).append('"');

    if (max != null)
      string.append(", max=\"").append(max).append('"');

    return string.toString();
  }
}