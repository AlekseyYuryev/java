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
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;

public class SimpleExample {
  public static void main(String[] args) {
    new SimpleExample().runExample();
  }

  public Binding runExample() {
    simple_fruit strawberry = new simple_fruit();
    strawberry.set_name$(new simple_fruit._name$("strawberry"));
    strawberry.set_sweet$(new simple_fruit._sweet$(true));

    simple_fruit jackfruit = new simple_fruit();
    jackfruit.set_name$(new simple_fruit._name$("jackfruit"));
    jackfruit.set_sweet$(new simple_fruit._sweet$(false));
    jackfruit.set_dry$(new simple_fruit._dry$(false));

    simple_fruitBasket._fruits fruits = new simple_fruitBasket._fruits();
    fruits.addsimple_fruit(strawberry);
    fruits.addsimple_fruit(jackfruit);

    simple_fruitBasket genericBasket = new simple_fruitBasket();
    genericBasket.add_fruits(fruits);

    // Now verify the integrity of the code representing this XML structure.
    return genericBasket;
  }
}
