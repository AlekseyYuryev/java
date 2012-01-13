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

package org.safris.xml.toolkit.test.binding;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.union.un_four;
import org.safris.xml.schema.binding.test.unit.union.un_root;
import org.safris.xml.schema.binding.test.unit.union.un_six;
import org.safris.xml.schema.binding.test.unit.union.un_two;

public class UnionTest {
  public static void main(String[] args) throws Exception {
    new UnionTest().testUnion();
  }

  @Test
  public void testUnion() throws Exception {
    un_root root = new un_root();

    un_root._one one = new un_root._one();
    one.setText(new Integer(9));
    root.add_one(one);

    un_two two = new un_two();
    List twos = new ArrayList();
    twos.add(new Integer(1));
    twos.add("hi");
    two.setText(twos);
    root.addun_two(two);

    un_root._three three = new un_root._three(5);
    root.add_three(three);

    un_four four = new un_four("text");
    root.addun_four(four);

    un_root._five five = new un_root._five(314);
    root.add_five(five);

    un_six six = new un_six("yeah");
    root.addun_six(six);

    un_root._seven seven = new un_root._seven(777);
    root.add_seven(seven);

    System.out.println(DOMs.domToString(Bindings.marshal(root), DOMStyle.INDENT));
  }
}
