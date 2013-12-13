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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_IDREFS<T extends BindingType> extends $xs_anySimpleType<T> {
  public $xs_IDREFS(final $xs_IDREFS<T> binding) {
    super(binding);
  }

  public $xs_IDREFS(final List<String> value) {
    super(value);
  }

  protected $xs_IDREFS() {
    super();
  }

  public List<String> getText() {
    return (List<String>)super.getText();
  }

  public void setText(final List<String> text) {
    super.setText(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    if (value == null || value.length() == 0)
      return;

    super.setText(new ArrayList<String>());
    final StringTokenizer tokenizer = new StringTokenizer(String.valueOf(value));
    while (tokenizer.hasMoreTokens())
      ((List<String>)super.getText()).add(tokenizer.nextToken());
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    if (super.getText() == null || ((List<String>)super.getText()).size() == 0)
      return null;

    String value = "";
    for (final String temp : (List<String>)super.getText())
      value += " " + temp;

    return value.substring(1);
  }

  public $xs_IDREFS<T> clone() {
    return new $xs_IDREFS<T>(this) {
      protected $xs_IDREFS<T> inherits() {
        return this;
      }
    };
  }
}