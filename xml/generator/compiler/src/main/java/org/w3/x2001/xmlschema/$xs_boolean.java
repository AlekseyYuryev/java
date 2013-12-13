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
import org.w3c.dom.Element;

public abstract class $xs_boolean<T extends BindingType> extends $xs_anySimpleType<T> {
  private static final Map<Boolean,String[]> valueMap = new HashMap<Boolean,String[]>();

  public static final Boolean parseBoolean(String s) {
    if (s == null)
      return false;

    if (s.length() == 1)
      return "1".equals(s);

    return Boolean.parseBoolean(s);
  }

  static
  {
    valueMap.put(true, new String[]{"true", "1"});
    valueMap.put(false, new String[]{"false", "0"});
  }

  public $xs_boolean(final $xs_boolean<T> binding) {
    super(binding);
  }

  public $xs_boolean(final Boolean value) {
    super(value);
  }

  protected $xs_boolean() {
    super();
  }

  public Boolean getText() {
    return (Boolean)super.getText();
  }

  public void setText(final Boolean text) {
    super.setText(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.setText(Boolean.valueOf("true".equals(value) || "1".equals(value)));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    if (super.getText() == null)
      return "";

    if (_$$getPattern() == null)
      return String.valueOf(super.getText());

    for (final String pattern : _$$getPattern()) {
      String[] ret = valueMap.get(super.getText());
      for (int i = 0; i < ret.length; i++) {
        if (ret[i].matches(pattern))
          return ret[i];
      }
    }

    throw new MarshalException("No valid return type. Schema error!!!");
  }

  public $xs_boolean<T> clone() {
    return new $xs_boolean<T>(this) {
      protected $xs_boolean<T> inherits() {
        return this;
      }
    };
  }
}