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

package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.enums.enums_color$;
import org.safris.xml.toolkit.sample.binding.enums.enums_coloredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;

public class EnumsExample {
  public static void main(String[] args) {
    new EnumsExample().runExample();
  }

  public Binding runExample() {
    simple_fruit strawberry = new simple_fruit();
    strawberry.set_name$(new simple_fruit._name$("strawberry"));
    strawberry.set_sweet$(new simple_fruit._sweet$(true));

    simple_fruit jackfruit = new simple_fruit();
    jackfruit.set_name$(new simple_fruit._name$("jackfruit"));
    jackfruit.set_sweet$(new simple_fruit._sweet$(false));
    jackfruit.set_dry$(new simple_fruit._dry$(false));

    enums_coloredFruitBasket._fruits simple_fruits = new enums_coloredFruitBasket._fruits();
    simple_fruits.addsimple_fruit(strawberry);
    simple_fruits.addsimple_fruit(jackfruit);

    enums_coloredFruitBasket coloredBasket = new enums_coloredFruitBasket();
    coloredBasket.setenums_color$(new enums_color$(enums_color$.BLUE));
    coloredBasket.add_fruits(simple_fruits);

    // Now verify the integrity of the code representing this XML structure.
    return coloredBasket;
  }
}
