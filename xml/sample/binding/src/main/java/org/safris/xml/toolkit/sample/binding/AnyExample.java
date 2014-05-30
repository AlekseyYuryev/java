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
import org.safris.xml.toolkit.sample.binding.any.any_trash;
import org.safris.xml.toolkit.sample.binding.enums.enums_color$;
import org.safris.xml.toolkit.sample.binding.enums.enums_coloredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.$simple_fruitType;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_fleshyFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_indehiscentDryFruitType;

public class AnyExample {
  public static void main(final String[] args) {
    new AnyExample().runExample();
  }

  public Binding runExample() {
    simple_fruit strawberry = new simple_fruit();
    strawberry._name$(new simple_fruit._name$("strawberry"));
    strawberry._sweet$(new simple_fruit._sweet$(true));

    simple_fruit jackfruit = new simple_fruit();
    jackfruit._name$(new simple_fruit._name$("jackfruit"));
    jackfruit._sweet$(new simple_fruit._sweet$(false));
    jackfruit._dry$(new simple_fruit._dry$(false));

    simple_fruitBasket._fruits simple_fruits = new simple_fruitBasket._fruits();
    simple_fruits.simple_fruit(strawberry);
    simple_fruits.simple_fruit(jackfruit);

    enums_coloredFruitBasket coloredBasket = new enums_coloredFruitBasket();
    coloredBasket.enums_color$(new enums_color$(enums_color$.BLUE));
    coloredBasket._fruits(simple_fruits);

    $type_fleshyFruitType berry = new $type_fleshyFruitType() {
      protected $simple_fruitType inherits() {
        return new simple_fruit();
      }
    };
    berry._name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.BERRY));
    berry._pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.SOFT));

    // Again, instantiate a nameless element.GenericBasket
    $type_indehiscentDryFruitType grain = new $type_indehiscentDryFruitType()
    {
      protected $simple_fruitType inherits() {
        return new simple_fruit();
//              return new SimpleSpecialFruit();
      }
    };
    grain._name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.GRAIN));

    // Again, instantiate a nameless element.
    $type_indehiscentDryFruitType nut = new $type_indehiscentDryFruitType() {
      protected $simple_fruitType inherits() {
        return new simple_fruit();
      }
    };
    nut._name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.NUT));
    nut._dry$(new $type_indehiscentDryFruitType._dry$($type_indehiscentDryFruitType._dry$.TRUE));
//      nut.setDryAttr(new ITypesimple_fruitType.DryAttr(false));

    any_trash trash = new any_trash();
    trash.addAny(coloredBasket);
    trash.addAny(berry);
    trash.addAny(grain);
    trash.addAny(nut);

    // Now verify the integrity of the code representing this XML structure.
    return trash;
  }
}