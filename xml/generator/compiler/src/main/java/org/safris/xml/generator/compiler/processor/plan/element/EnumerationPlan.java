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

package org.safris.xml.generator.compiler.processor.plan.element;

import javax.xml.namespace.QName;

import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.processor.model.element.EnumerationModel;

public final class EnumerationPlan extends Plan<EnumerationModel> {
  private String declarationName = null;
  private final QName value;

  public static String getDeclarationName(final QName value) {
    String string = null;
    if (47 < value.getLocalPart().charAt(0) && value.getLocalPart().charAt(0) < 58)
      string = "_" + value.getLocalPart();
    else
      string = value.getLocalPart();

    if (value.getPrefix() != null && value.getPrefix().toString().length() != 0)
      string = value.getPrefix() + "_" + string;

    string = string.replace("_", "__");
    string = string.replace('~', '_');
    string = string.replace('.', '_');
    string = string.replace(' ', '_');
    string = string.replace('-', '_');
    string = string.replace('=', '_');
    string = string.replace('+', '_');
    string = string.replace('|', '_');
    string = string.replace('@', '_');
    string = string.replace("'", "_");
    string = string.replace("\"", "_");
    string = string.replace("*", "_");
    string = string.replace("\\", "_");
    string = string.replace(",", "_");
    string = string.replace("!", "_");
    string = string.replace("@", "_");
    string = string.replace("#", "_");
    string = string.replace("%", "_");
    string = string.replace("^", "_");
    string = string.replace("&", "_");
    string = string.replace("(", "_");
    string = string.replace(")", "_");
    string = string.replace("{", "_");
    string = string.replace("}", "_");
    string = string.replace("[", "_");
    string = string.replace("]", "_");
    string = string.replace("|", "_");
    string = string.replace(";", "_");
    string = string.replace(":", "_");
    string = string.replace("<", "_");
    string = string.replace(">", "_");
    string = string.replace("?", "_");
    string = string.replace("/", "");
    return string.toUpperCase();
  }

  public EnumerationPlan(final EnumerationModel model, final Plan<?> parent) {
    super(model, parent);
    this.value = model.getValue();
  }

  public QName getValue() {
    return value;
  }

  public String getDeclarationName() {
    if (declarationName != null)
      return declarationName;

    if (getModel().getValue().getLocalPart().length() == 0)
      throw new CompilerError("The localPart of this enumeration cannot be length == 0");

    return declarationName = getDeclarationName(getModel().getValue());
  }
}