/*  Copyright Safris Software 2006
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

package org.w3.x2001.xmlschema;

import java.util.HashMap;
import java.util.Map;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.Element;

public abstract class $xs_ID<T extends BindingType> extends $xs_NCName<T> {
  protected static final Map<String,Map<Object,$xs_ID<? extends BindingType>>> namespaceIds = new HashMap<String,Map<Object,$xs_ID<? extends BindingType>>>();

  private static void persist(final String namespace, final Object value, final $xs_ID<? extends BindingType> id) {
    Map<Object,$xs_ID<? extends BindingType>> idMap = namespaceIds.get(namespace);
    if (idMap == null)
      namespaceIds.put(namespace, idMap = new HashMap<Object,$xs_ID<? extends BindingType>>());

    idMap.put(value, id);
  }

  private static void remove(final String namespace, final Object value) {
    final Map<Object,$xs_ID<? extends BindingType>> ids = namespaceIds.get(namespace);
    if (ids == null)
      return;

    ids.remove(value);
  }

  public static $xs_ID<? extends BindingType> lookupId(final Object id) {
    final Map<Object,$xs_ID<? extends BindingType>> ids = namespaceIds.get(UniqueQName.XS.getNamespaceURI().toString());
    if (ids == null)
      return null;

    return ids.get(id);
  }

  public $xs_ID(final $xs_ID<T> binding) {
    super(binding);
  }

  public $xs_ID(final String value) {
    super(value);
    persist(_$$getName().getNamespaceURI(), value, this);
  }

  protected $xs_ID() {
    super();
  }

  public String getText() {
    return super.getText();
  }

  public void setText(final String text) {
    final Object old = getText();
    super.setText(text);
    if (old != null)
      remove(_$$getName().getNamespaceURI(), old);

    persist(_$$getName().getNamespaceURI(), text, this);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    persist(parent.getNamespaceURI(), value, this);
    super.setText(value);
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    if (super.getText() == null)
      return "";

    return super.getText().toString();
  }

  public $xs_ID<T> clone() {
    return new $xs_ID<T>(this) {
      protected $xs_ID<T> inherits() {
        return this;
      }
    };
  }
}