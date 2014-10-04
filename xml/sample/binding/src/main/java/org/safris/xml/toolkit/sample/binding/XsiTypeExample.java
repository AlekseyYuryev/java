/* Copyright (c) 2006 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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