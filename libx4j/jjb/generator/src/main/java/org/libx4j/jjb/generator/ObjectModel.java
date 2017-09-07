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
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lib4j.util.Iterators;
import org.libx4j.jjb.jsonx.xe.$jsonx_array;
import org.libx4j.jjb.jsonx.xe.$jsonx_element;
import org.libx4j.jjb.jsonx.xe.$jsonx_object;
import org.libx4j.jjb.jsonx.xe.jsonx_jsonx;
import org.libx4j.jjb.runtime.JsonxObject;
import org.libx4j.jjb.runtime.ObjectElement;
import org.libx4j.jjb.runtime.ObjectProperty;
import org.libx4j.jjb.runtime.Unknown;

class ObjectModel extends ComplexModel {
  private static ObjectModel getParent(final String superClassName, final Registry registry) {
    if (superClassName == null)
      return null;

    final ObjectModel parent;
    try {
      parent = (ObjectModel)registry.getElement(superClassName);
    }
    catch (final ClassCastException e) {
      throw new IllegalStateException("Top-level object extends=\"" + superClassName + "\" in array points to a non-object");
    }

    if (parent == null)
      throw new IllegalStateException("Top-level object extends=\"" + superClassName + "\" in array not found");

    return parent;
  }

  protected static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ObjectProperty objectProperty, final Field field) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(objectProperty.type().getName());
    return new PropertyElement(getName(objectProperty.name(), field), objectProperty.required(), objectProperty.nullable(), objectModel == null ? registry.declare(objectProperty.type()).value(new ObjectModel(registry, objectProperty, field), referrer) : registry.reference(objectModel, referrer));
  }

  protected static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ObjectElement objectElement) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(objectElement.type().getName());
    return new PropertyElement(objectElement.nullable(), objectElement.minOccurs(), objectElement.maxOccurs(), objectModel == null ? registry.declare(objectElement.type()).value(new ObjectModel(registry, objectElement), referrer) : registry.reference(objectModel, referrer));
  }

  public static ObjectModel reference(final Registry registry, final ComplexModel referrer, final $jsonx_array._object binding, final String superClassName) {
    return registry.reference(new ObjectModel(registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static ObjectModel reference(final Registry registry, final ComplexModel referrer, final $jsonx_object._object binding, final String superClassName) {
    return registry.reference(new ObjectModel(registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static ObjectModel declare(final Registry registry, final jsonx_jsonx._object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), null);
  }

  public static ObjectModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final Class<?> clazz) {
    return referenceOrDeclare(registry, referrer, clazz, checkJSObject(clazz));
  }

  private static ObjectModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final Class<?> clazz, final JsonxObject jsObject) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(clazz.getName());
    return objectModel == null ? registry.declare(clazz).value(new ObjectModel(registry, null, clazz, jsObject), referrer) : registry.reference(objectModel, referrer);
  }

  private static JsonxObject checkJSObject(final Class<?> clazz) {
    final JsonxObject jsObject = clazz.getDeclaredAnnotation(JsonxObject.class);
    if (jsObject == null)
      throw new IllegalArgumentException("Class " + clazz.getName() + " goes not specify the @" + JsonxObject.class.getSimpleName() + " annotation.");

    return jsObject;
  }

  private static void recurseInnerClasses(final Registry registry, final Class<?> clazz) {
    for (final Class<?> innerClass : clazz.getClasses()) {
      final JsonxObject innerJSObject = innerClass.getDeclaredAnnotation(JsonxObject.class);
      if (innerJSObject == null)
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, null, innerClass, innerJSObject);
    }
  }

  private final Map<String,Element> members;
  private final String className;
  private final ObjectModel superObject;
  private final Boolean isAbstract;
  private final Unknown unknown;

  private ObjectModel(final Registry registry, final ObjectProperty objectProperty, final Field field) {
    this(registry, getName(objectProperty.name(), field), objectProperty.type(), checkJSObject(objectProperty.type()));
  }

  private ObjectModel(final Registry registry, final ObjectElement objectElement) {
    this(registry, null, objectElement.type(), checkJSObject(objectElement.type()));
  }

  private static void x() {
    int i = 0;
  }

  // Annullable, Recurrable
  private ObjectModel(final Registry registry, final $jsonx_array._object binding, final ObjectModel superObject) {
    super(binding, binding._nullable$().text(), binding._minOccurs$(), binding._maxOccurs$());
    this.className = binding._class$().text();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding._unknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  // Nameable, Annullable, Requirable
  private ObjectModel(final Registry registry, final $jsonx_object._object binding, final ObjectModel superObject) {
    super(binding, binding._name$().text(), binding._required$().text(), binding._nullable$().text());
    this.className = binding._class$().text();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding._unknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  // Nameable
  private ObjectModel(final Registry registry, final jsonx_jsonx._object binding) {
    super(null, null, null, null, null);
    this.className = binding._class$().text();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.isAbstract = binding._abstract$().text();
    this.unknown = Unknown.valueOf(binding._unknown$().text().toUpperCase());
    this.superObject = getParent(binding._extends$().text(), registry);
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private static Map<String,Element> parseMembers(final Registry registry, final $jsonx_object binding, final ObjectModel model) {
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    final Iterator<? extends $jsonx_element> elements = Iterators.filter(binding.elementIterator(), $jsonx_element.class);
    while (elements.hasNext()) {
      final $jsonx_element element = elements.next();
      if (element instanceof $jsonx_object._boolean) {
        final $jsonx_object._boolean member = ($jsonx_object._boolean)element;
        members.put(member._name$().text(), BooleanModel.reference(registry, model, member));
      }
      else if (element instanceof $jsonx_object._number) {
        final $jsonx_object._number member = ($jsonx_object._number)element;
        members.put(member._name$().text(), NumberModel.reference(registry, model, member));
      }
      else if (element instanceof $jsonx_object._string) {
        final $jsonx_object._string member = ($jsonx_object._string)element;
        members.put(member._name$().text(), StringModel.reference(registry, model, member));
      }
      else if (element instanceof $jsonx_object._array) {
        final $jsonx_object._array member = ($jsonx_object._array)element;
        final ArrayModel child = ArrayModel.reference(registry, model, member);
        members.put(member._name$().text(), child);
      }
      else if (element instanceof $jsonx_object._object) {
        final $jsonx_object._object member = ($jsonx_object._object)element;
        final ObjectModel child = ObjectModel.reference(registry, model, member, member._extends$().text());
        members.put(member._name$().text(), child);
      }
      else if (element instanceof $jsonx_object._property) {
        final $jsonx_object._property member = ($jsonx_object._property)element;
        final Element ref = registry.getElement(member._ref$().text());
        if (ref == null)
          throw new IllegalStateException("Top-level element ref=\"" + member._ref$().text() + "\" on " + member._name$().text() + " not found");

        members.put(member._name$().text(), ref instanceof Model ? new PropertyElement(member, (Model)ref) : ref);
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + element.getClass().getSimpleName() + " member type: " + element.getClass().getName());
      }
    }

    return Collections.unmodifiableMap(members);
  }

  private ObjectModel(final Registry registry, final String name, final Class<?> clazz, final JsonxObject jsObject) {
    super(name, null, null, null, null);
    this.className = clazz.getName();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.isAbstract = Modifier.isAbstract(clazz.getModifiers());
    this.unknown = jsObject.unknown();
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    final Class<?> superClass = clazz.getSuperclass();
    if (superClass != null) {
      final JsonxObject superObject = superClass.getDeclaredAnnotation(JsonxObject.class);
      this.superObject = superObject == null ? null : referenceOrDeclare(registry, this, superClass);
    }
    else {
      this.superObject = null;
    }

    Object object;
    try {
      object = clazz.newInstance();
    }
    catch (final IllegalAccessException | InstantiationException e) {
      object = null;
    }

    for (final Field field : clazz.getDeclaredFields()) {
      final Element member = Element.toElement(registry, this, field, object);
      if (member != null)
        members.put(member.name(), member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(registry, clazz);
  }

  private ObjectModel(final Element element, final ObjectModel model) {
    super(element);
    this.className = model.className;
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = model.superObject;
    this.isAbstract = model.isAbstract;
    this.unknown = model.unknown;
    this.members = Collections.unmodifiableMap(model.members);
  }

  private ObjectModel(final ObjectModel copy, final ObjectModel superObject, final Map<String,Element> members) {
    super(copy);
    this.className = copy.className;
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = superObject;
    this.isAbstract = copy.isAbstract;
    this.unknown = copy.unknown;
    this.members = Collections.unmodifiableMap(members);
  }

  public final Map<String,Element> members() {
    return this.members;
  }

  @Override
  public final String className() {
    return this.className;
  }

  public final String classSimpleName() {
    final int from = className.lastIndexOf('.');
    final int to = className.lastIndexOf('$');
    return to > 0 ? className.substring(to + 1) : from > 0 ? className.substring(from + 1) : className;
  }

  public final ObjectModel superObject() {
    return this.superObject;
  }

  public final boolean isAbstract() {
    return this.isAbstract != null && this.isAbstract;
  }

  public final Unknown unknown() {
    return this.unknown;
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return ObjectProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return ObjectElement.class;
  }

  @Override
  protected ObjectModel merge(final PropertyElement propertyElement) {
    return new ObjectModel(propertyElement, this);
  }

  @Override
  protected final Element normalize(final Registry registry) {
    final Element element = registry.getElement(ref());
    if (element instanceof PropertyElement)
      return element.normalize(registry);

    final Map<String,Element> members = normalize(registry, this.members);
    final ObjectModel superObject;
    if (superObject() == null) {
      superObject = null;
    }
    else {
      final Element superElement = superObject().normalize(registry);
      superObject = (ObjectModel)(superElement instanceof PropertyElement ? ((PropertyElement)superElement).ref() : superElement);
    }

    final ObjectModel clone = new ObjectModel(this, superObject, members);
    final ObjectModel objectModel = (ObjectModel)element;
    if (registry.getNumReferrers(this) != 1 || objectModel.isAbstract())
      return clone;

    return objectModel == this ? clone : new ObjectModel(objectModel, clone);
  }

  @Override
  protected final void collectClassNames(final List<String> classNames) {
    classNames.add(className());
    if (superObject != null)
      superObject.collectClassNames(classNames);

    if (members != null)
      for (final Element member : members.values())
        member.collectClassNames(classNames);
  }

  @Override
  protected String ref() {
    return className();
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder string = new StringBuilder(super.toJSON(pacakgeName));
    if (string.length() > 0)
      string.insert(0, ",\n");

    string.append(",\n  class: \"").append(getShortName(className, pacakgeName)).append('"');

    if (superObject != null)
      string.append(",\n  extends: \"").append(getShortName(superObject.className(), pacakgeName)).append('"');

    if (isAbstract != null)
      string.append(",\n  abstract: ").append(isAbstract);

    if (unknown != null)
      string.append(",\n  unknown: \"").append(unknown.toString().toLowerCase()).append('"');

    if (members != null && members.size() > 0) {
      string.append(",\n  members: ");
      final StringBuilder members = new StringBuilder();
      for (final Map.Entry<String,Element> entry : this.members.entrySet())
        members.append(",\n    \"").append(entry.getKey()).append("\": ").append(entry.getValue().toJSON(pacakgeName).replace("\n", "\n    "));

      string.append("{");
      if (members.length() > 0)
        string.append("\n").append(members.substring(2)).append("\n  ");

      string.append("}");
    }

    return "{\n" + (string.length() > 0 ? string.substring(2) : string.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder string = new StringBuilder("<object");
    string.append(" class=\"").append(getShortName(className, pacakgeName)).append('"');

    if (superObject != null)
      string.append(" extends=\"").append(getShortName(superObject.className(), pacakgeName)).append('"');

    if (isAbstract != null && isAbstract)
      string.append(" abstract=\"").append(isAbstract).append('"');

    if (unknown != Unknown.ERROR)
      string.append(" unknown=\"").append(unknown.toString().toLowerCase()).append('"');

    if (members != null && members.size() > 0) {
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members.values())
        members.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      string.append(super.toJSONX(pacakgeName)).append(">").append(members).append("\n</object>");
    }
    else {
      string.append(super.toJSONX(pacakgeName)).append("/>");
    }

    return string.toString();
  }

  protected final String toObjectAnnotation() {
    return unknown() == Unknown.ERROR ? "" : "unknown=" + Unknown.class.getName() + "." + unknown();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder string = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (string.length() > 0)
      string.append(", ");

    string.append("type=").append(className.replace('$', '.')).append(".class");
    return string.toString();
  }

  protected final String toJava() {
    final StringBuilder string = new StringBuilder();
    if (members != null && members.size() > 0)
      for (final Element member : this.members.values())
        string.append("\n\n").append(member.toField());

    return string.length() > 1 ? string.substring(2).toString() : string.toString();
  }
}