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
import org.safris.xml.toolkit.sample.binding.simple.$simple_fruitType;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_dehiscentDryFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_fleshyFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_indehiscentDryFruitType;

public class XsiTypeExample {
  public static void main(final String[] args) {
    new XsiTypeExample().runExample();
  }

  public Binding runExample() {
    // Since there is no element declaration for the fleshyFruitType,
    // we need to instantiate a nameless element. Once this element is
    // put into the basket, the element will obtain the needed name.
    $type_fleshyFruitType berry = new $type_fleshyFruitType() {
      protected $simple_fruitType inherits() {
        return null;
      }
    };
    berry._name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.BERRY));
    berry._pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.SOFT));

    // Again, instantiate a nameless element.
    $type_fleshyFruitType drupe = new $type_fleshyFruitType() {
      protected $simple_fruitType inherits() {
        return null;
      }
    };
    drupe._name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.DRUPE));
    drupe._pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.FLESHY));

    // Again, instantiate a nameless element.
    $type_dehiscentDryFruitType legume = new $type_dehiscentDryFruitType() {
      protected $simple_fruitType inherits() {
        return null;
      }
    };
    legume._name$(new $type_dehiscentDryFruitType._name$($type_dehiscentDryFruitType._name$.LEGUME));

    // Again, instantiate a nameless element.
    $type_dehiscentDryFruitType follicle = new $type_dehiscentDryFruitType() {
      protected $simple_fruitType inherits() {
        return null;
      }
    };
    follicle._name$(new $type_dehiscentDryFruitType._name$($type_dehiscentDryFruitType._name$.FOLLICLE));

    // Again, instantiate a nameless element.GenericBasket
    $type_indehiscentDryFruitType grain = new $type_indehiscentDryFruitType() {
      protected $simple_fruitType inherits() {
        return null;
      }
    };
    grain._name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.GRAIN));

    // Again, instantiate a nameless element.
    $type_indehiscentDryFruitType nut = new $type_indehiscentDryFruitType() {
      protected $simple_fruitType inherits() {
        return null;
      }
    };
    nut._name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.NUT));
    nut._dry$(new $type_indehiscentDryFruitType._dry$($type_indehiscentDryFruitType._dry$.TRUE));
//      nut.addDry$(new ITypesimple_fruitType.Dry$(false));

    simple_fruitBasket._fruits fruits = new simple_fruitBasket._fruits();
    fruits.simple_fruit(berry);
    fruits.simple_fruit(drupe);
    fruits.simple_fruit(legume);
    fruits.simple_fruit(follicle);
    fruits.simple_fruit(grain);
    fruits.simple_fruit(nut);

    enums_coloredFruitBasket coloredBasket = new enums_coloredFruitBasket();
    coloredBasket.enums_color$(new enums_color$(enums_color$.RED));
    coloredBasket._fruits(fruits);

    // Now verify the integrity of the code representing this XML structure.
    return coloredBasket;
  }
}