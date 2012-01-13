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

package org.safris.xml.generator.compiler.processor.write.element;

import java.io.StringWriter;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.compiler.processor.plan.element.AttributeGroupPlan;
import org.safris.xml.generator.compiler.processor.write.Writer;

public class AttributeGroupWriter extends Writer<AttributeGroupPlan> {
  protected void appendDeclaration(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  protected void appendGetMethod(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  protected void appendSetMethod(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  protected void appendMarshal(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  protected void appendParse(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  public void appendCopy(StringWriter writer, AttributeGroupPlan plan, Plan parent, String variable) {
  }

  protected void appendEquals(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  protected void appendHashCode(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }

  protected void appendClass(StringWriter writer, AttributeGroupPlan plan, Plan parent) {
  }
}
