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

import org.lib4j.lang.IllegalAnnotationException;
import org.libx4j.jjb.jsonx.xe.$jsonx_array;
import org.libx4j.jjb.jsonx.xe.$jsonx_object;
import org.libx4j.jjb.jsonx.xe.jsonx_jsonx;
import org.libx4j.jjb.runtime.BooleanElement;
import org.libx4j.jjb.runtime.BooleanProperty;

class BooleanModel extends SimpleModel {
  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $jsonx_array._boolean binding) {
    return new BooleanModel(binding);
  }

  // Annullable, Recurrable
  private BooleanModel(final $jsonx_array._boolean binding) {
    super(binding, binding._nullable$().text(), binding._minOccurs$(), binding._maxOccurs$());
  }

  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $jsonx_object._boolean binding) {
    return new BooleanModel(binding);
  }

  // Nameable, Annullable
  private BooleanModel(final $jsonx_object._boolean binding) {
    super(binding, binding._name$().text(), binding._required$().text(), binding._nullable$().text());
  }

  public static BooleanModel declare(final Registry registry, final jsonx_jsonx._boolean binding) {
    return registry.declare(binding).value(new BooleanModel(binding), null);
  }

  // Nameable
  private BooleanModel(final jsonx_jsonx._boolean binding) {
    super(binding._name$().text(), null, null, null, null);
  }

  public static BooleanModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanProperty booleanProperty, final Field field) {
    return new BooleanModel(booleanProperty, field);
  }

  private BooleanModel(final BooleanProperty booleanProperty, final Field field) {
    super(getName(booleanProperty.name(), field), booleanProperty.required(), booleanProperty.nullable(), null, null);
    if (field.getType() != Boolean.class && (field.getType() != boolean.class || booleanProperty.nullable()))
      throw new IllegalAnnotationException(booleanProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Boolean type or non-nullable boolean type.");
  }

  public static BooleanModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanElement booleanElement) {
    return new BooleanModel(booleanElement);
  }

  private BooleanModel(final BooleanElement booleanElement) {
    super(null, null, booleanElement.nullable(), booleanElement.minOccurs(), booleanElement.maxOccurs());
  }

  private BooleanModel(final Element element) {
    super(element);
  }

  @Override
  protected String className() {
    return Boolean.class.getName();
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return BooleanProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return BooleanElement.class;
  }

  @Override
  protected BooleanModel merge(final PropertyElement propertyElement) {
    return new BooleanModel(propertyElement);
  }

  @Override
  protected BooleanModel normalize(final Registry registry) {
    return this;
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    return "{\n" + super.toJSON(pacakgeName) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    return new StringBuilder("<boolean").append(super.toJSONX(pacakgeName)).append("/>").toString();
  }
}