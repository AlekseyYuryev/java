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
import org.safris.xml.toolkit.sample.binding.any.any_trash;
import org.safris.xml.toolkit.sample.binding.enums.enums_color$;
import org.safris.xml.toolkit.sample.binding.enums.enums_coloredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.$simple_fruitType;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_fleshyFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_indehiscentDryFruitType;

public class AnySample {
  public static void main(final String[] args) {
    new AnySample().runSample();
  }

  public Binding runSample() {
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
    coloredBasket.enums_color$(new enums_color$(enums_color$.blue));
    coloredBasket._fruits(simple_fruits);

    $type_fleshyFruitType berry = new $type_fleshyFruitType() {
      @Override
      protected $simple_fruitType inherits() {
        return new simple_fruit();
      }
    };
    berry._name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.Berry));
    berry._pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.soft));

    // Again, instantiate a nameless element.GenericBasket
    $type_indehiscentDryFruitType grain = new $type_indehiscentDryFruitType()
    {
      @Override
      protected $simple_fruitType inherits() {
        return new simple_fruit();
//              return new SimpleSpecialFruit();
      }
    };
    grain._name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.Grain));

    // Again, instantiate a nameless element.
    $type_indehiscentDryFruitType nut = new $type_indehiscentDryFruitType() {
      @Override
      protected $simple_fruitType inherits() {
        return new simple_fruit();
      }
    };
    nut._name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.Nut));
    nut._dry$(new $type_indehiscentDryFruitType._dry$($type_indehiscentDryFruitType._dry$._5Ftrue));
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