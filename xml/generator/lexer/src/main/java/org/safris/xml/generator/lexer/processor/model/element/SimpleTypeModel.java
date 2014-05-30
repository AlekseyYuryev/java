/*  Copyright Safris Software 2008
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.generator.lexer.processor.model.element;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.Undefineable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.DocumentableModel;
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.PatternableModel;
import org.safris.xml.generator.lexer.processor.model.RedefineableModel;
import org.safris.xml.generator.lexer.processor.model.TypeableModel;
import org.safris.xml.generator.lexer.schema.attribute.Final;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SimpleTypeModel<T extends SimpleTypeModel<?>> extends AliasModel implements DocumentableModel, EnumerableModel, PatternableModel, RedefineableModel<T>, TypeableModel<T> {
  private final LinkedHashSet<EnumerationModel> enumerations = new LinkedHashSet<EnumerationModel>();
  private final LinkedHashSet<PatternModel> patterns = new LinkedHashSet<PatternModel>();

  private T redefine = null;
  private SimpleTypeModel<?> superType = null;
  private Collection<SimpleTypeModel<?>> itemType = null;

  private boolean restriction = false;
  private boolean list = false;

  private Final _final = null;

  protected SimpleTypeModel(final Node node, final Model parent) {
    super(node, parent);
    if (node == null)
      return;

    final NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      final Node attribute = attributes.item(i);
      if ("final".equals(attribute.getLocalName()))
        _final = Final.parseFinal(attribute.getNodeValue());
    }
  }

  public final void setRedefine(final T redefine) {
    this.redefine = redefine;
  }

  public final T getRedefine() {
    return redefine;
  }

  public final void setSuperType(final SimpleTypeModel<?> superType) {
    if (!this.equals(superType))
      this.superType = superType;
  }

  public SimpleTypeModel<?> getSuperType() {
    return superType;
  }

  public final void setItemTypes(final Collection<SimpleTypeModel<?>> itemType) {
    this.itemType = itemType;
  }

  public final Collection<SimpleTypeModel<?>> getItemTypes() {
    return itemType;
  }

  public final void setRestriction(final boolean isRestriction) {
    this.restriction = isRestriction;
  }

  public final boolean isRestriction() {
    return restriction;
  }

  public final void setList(final boolean list) {
    this.list = list;
  }

  public final boolean isList() {
    return list;
  }

  public final Final getFinal() {
    return _final;
  }

  public final void addEnumeration(final EnumerationModel enumeration) {
    if (!enumerations.contains(enumeration))
      enumerations.add(enumeration);
  }

  public final LinkedHashSet<EnumerationModel> getEnumerations() {
    return enumerations;
  }

  public final void addPattern(final PatternModel pattern) {
    this.patterns.add(pattern);
  }

  public final LinkedHashSet<PatternModel> getPatterns() {
    return patterns;
  }

  public static final class Reference extends SimpleTypeModel<SimpleTypeModel<?>> implements Referenceable {
    private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

    public static Reference parseSimpleType(final UniqueQName name) {
      Reference type = all.get(name);
      if (type != null)
        return type;

      type = new Reference(null);
      type.setName(name);
      Reference.all.put(name, type);
      return type;
    }

    protected Reference(final Model parent) {
      super(null, parent);
    }
  }

  public static final class Undefined extends SimpleTypeModel<SimpleTypeModel<?>> implements Undefineable {
    private static final Map<UniqueQName,Undefined> all = new HashMap<UniqueQName,Undefined>();

    public static Undefined parseSimpleType(final UniqueQName name) {
      Undefined type = all.get(name);
      if (type != null)
        return type;

      type = new Undefined(null);
      type.setName(name);
      Undefined.all.put(name, type);
      return type;
    }

    protected Undefined(final Model parent) {
      super(null, parent);
    }
  }
}