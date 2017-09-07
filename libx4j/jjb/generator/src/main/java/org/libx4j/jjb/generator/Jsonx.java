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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.lib4j.lang.Strings;
import org.lib4j.util.Collections;
import org.lib4j.util.Iterators;
import org.libx4j.jjb.jsonx.xe.$jsonx_element;
import org.libx4j.jjb.jsonx.xe.jsonx_jsonx;

class Jsonx extends Factor {
  private final Registry registry;
  private final jsonx_jsonx jsonx;
  private final String packageName;

  public Jsonx(final jsonx_jsonx jsonx) {
    this.jsonx = jsonx;
    this.packageName = (String)jsonx._package$().text();
    final Iterator<? extends $jsonx_element> elements = Iterators.filter(jsonx.elementIterator(), $jsonx_element.class);
    final StrictDigraph<jsonx_jsonx._object,String> digraph = new StrictDigraph<jsonx_jsonx._object,String>("Object cannot inherit from itself", obj -> obj._class$().text());
    final Registry registry = new Registry();
    while (elements.hasNext()) {
      final $jsonx_element element = elements.next();
      if (element instanceof jsonx_jsonx._boolean)
        BooleanModel.declare(registry, (jsonx_jsonx._boolean)element);
      else if (element instanceof jsonx_jsonx._number)
        NumberModel.declare(registry, (jsonx_jsonx._number)element);
      else if (element instanceof jsonx_jsonx._string)
        StringModel.declare(registry, (jsonx_jsonx._string)element);
      else if (element instanceof jsonx_jsonx._array)
        ArrayModel.declare(registry, (jsonx_jsonx._array)element);
      else if (element instanceof jsonx_jsonx._object) {
        final jsonx_jsonx._object object = (jsonx_jsonx._object)element;
        digraph.addEdgeRef(object, object._extends$().text());
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + jsonx.getClass().getSimpleName() + " member type: " + element.getClass().getName());
      }
    }

    final List<jsonx_jsonx._object> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Inheritance cycle detected in object hierarchy: " + Collections.toString(digraph.getCycle(), " -> "));

    final ListIterator<jsonx_jsonx._object> topologicalOrder = digraph.getTopologicalOrder().listIterator(digraph.getSize());
    while (topologicalOrder.hasPrevious())
      ObjectModel.declare(registry, topologicalOrder.previous());

    this.registry = registry;
  }

  public Jsonx(final Class<?> ... classes) {
    final Registry registry = new Registry();
    this.jsonx = null;
    for (final Class<?> clazz : classes)
      ObjectModel.referenceOrDeclare(registry, null, clazz);

    this.registry = registry.normalize();

    final String classPrefix = getClassPrefix();
    this.packageName = classPrefix == null ? null : classPrefix.substring(0, classPrefix.length() - 1);
  }

  public final jsonx_jsonx jsonx() {
    return this.jsonx;
  }

  public final String packageName() {
    return this.packageName;
  }

  public final Collection<Model> members() {
    return this.registry.elements();
  }

  @Override
  protected final void collectClassNames(final List<String> classNames) {
    if (members() != null)
      for (final Model member : members())
        member.collectClassNames(classNames);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder string = new StringBuilder();
    string.append("{\n").append("  package: \"").append(packageName() == null ? "" : packageName()).append('"');
    for (final Model member : members())
      if (!(member instanceof ObjectModel) || registry.getNumReferrers(member) != 1 || ((ObjectModel)member).isAbstract())
        string.append(",\n  \"").append(member.ref()).append("\": ").append(member.toJSON(pacakgeName).replace("\n", "\n  "));

    string.append("\n}");
    return string.toString();
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder string = new StringBuilder("<jsonx\n  package=\"" + (pacakgeName == null ? "" : pacakgeName) + "\"\n  xmlns=\"http://jjb.libx4j.org/jsonx.xsd\"\n  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n  xsi:schemaLocation=\"http://jjb.libx4j.org/jsonx.xsd /Users/seva/Work/SevaSafris/java/libx4j/jjb/generator/src/main/resources/jsonx.xsd\"");
    if (this.registry.size() > 0) {
      string.append(">");
      for (final Model member : members())
        if (!(member instanceof ObjectModel) || registry.getNumReferrers(member) != 1 || ((ObjectModel)member).isAbstract())
          string.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      string.append("\n</jsonx>");
    }
    else {
      string.append("/>");
    }

    return string.toString();
  }

  private final String getClassPrefix() {
    final List<String> classNames = new LinkedList<String>();
    collectClassNames(classNames);
    final String classPrefix = Strings.getCommonPrefix(classNames);
    if (classPrefix == null)
      return null;

    final int index = classPrefix.lastIndexOf('.');
    if (index < 0)
      return null;

    return classPrefix.substring(0, index + 1);
  }

  @Override
  public final String toJSON() {
    return toJSON(packageName);
  }

  @Override
  public final String toJSONX() {
    return toJSONX(packageName);
  }

  public void toJava(final File dir) throws IOException {
    final Map<String,ClassHolder> map = new HashMap<String,ClassHolder>();
    for (final Model member : members()) {
      if (member instanceof ObjectModel && (registry.getNumReferrers(member) <= 1 || ((ObjectModel)member).isAbstract())) {
        final ObjectModel objectModel = (ObjectModel)member;
        final String code = objectModel.toJava();
        final ClassHolder classHolder = new ClassHolder(objectModel.classSimpleName(), objectModel.superObject() == null ? null : objectModel.superObject().className(), objectModel.toObjectAnnotation(), code);
        if (objectModel.className().contains("$"))
          map.get(objectModel.className().substring(0, objectModel.className().lastIndexOf('$'))).memberClasses.add(classHolder);
        else
          map.put(objectModel.className(), classHolder);
      }
    }

    for (final Map.Entry<String,ClassHolder> entry : map.entrySet()) {
      final String className = entry.getKey();
      final ClassHolder holder = entry.getValue();
      final File file = new File(dir, className.replace('.', '/') + ".java");
      file.getParentFile().mkdirs();
      try (final FileOutputStream out = new FileOutputStream(file)) {
        final String string = holder.getAnnotation() + "\npublic " + holder.toString();
        out.write((packageName != null && packageName.length() > 0 ? "package " + packageName + ";\n\n" + string : string).getBytes());
      }
    }
  }
}