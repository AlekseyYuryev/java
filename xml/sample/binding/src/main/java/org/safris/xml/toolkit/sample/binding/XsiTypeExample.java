package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.enums.enums_color$;
import org.safris.xml.toolkit.sample.binding.enums.enums_coloredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.$simple_fruitType;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_dehiscentDryFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_fleshyFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.$type_indehiscentDryFruitType;

public class XsiTypeExample
{
	public static void main(String[] args)
	{
		new XsiTypeExample().runExample();
	}

	public Binding runExample()
	{
		// Since there is no element declaration for the fleshyFruitType,
		// we need to instantiate a nameless element. Once this element is
		// put into the basket, the element will obtain the needed name.
		$type_fleshyFruitType berry = new $type_fleshyFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return null;
			}
		};
		berry.set_name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.BERRY));
		berry.set_pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.SOFT));

		// Again, instantiate a nameless element.
		$type_fleshyFruitType drupe = new $type_fleshyFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return null;
			}
		};
		drupe.set_name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.DRUPE));
		drupe.set_pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.FLESHY));

		// Again, instantiate a nameless element.
		$type_dehiscentDryFruitType legume = new $type_dehiscentDryFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return null;
			}
		};
		legume.set_name$(new $type_dehiscentDryFruitType._name$($type_dehiscentDryFruitType._name$.LEGUME));

		// Again, instantiate a nameless element.
		$type_dehiscentDryFruitType follicle = new $type_dehiscentDryFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return null;
			}
		};
		follicle.set_name$(new $type_dehiscentDryFruitType._name$($type_dehiscentDryFruitType._name$.FOLLICLE));

		// Again, instantiate a nameless element.GenericBasket
		$type_indehiscentDryFruitType grain = new $type_indehiscentDryFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return null;
			}
		};
		grain.set_name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.GRAIN));

		// Again, instantiate a nameless element.
		$type_indehiscentDryFruitType nut = new $type_indehiscentDryFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return null;
			}
		};
		nut.set_name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.NUT));
		nut.set_dry$(new $type_indehiscentDryFruitType._dry$($type_indehiscentDryFruitType._dry$.TRUE));
//		nut.addDry$(new ITypesimple_fruitType.Dry$(false));

		simple_fruitBasket._fruits fruits = new simple_fruitBasket._fruits();
		fruits.addsimple_fruit(berry);
		fruits.addsimple_fruit(drupe);
		fruits.addsimple_fruit(legume);
		fruits.addsimple_fruit(follicle);
		fruits.addsimple_fruit(grain);
		fruits.addsimple_fruit(nut);

		enums_coloredFruitBasket coloredBasket = new enums_coloredFruitBasket();
		coloredBasket.setenums_color$(new enums_color$(enums_color$.RED));
		coloredBasket.add_fruits(fruits);

		// Now verify the integrity of the code representing this XML structure.
		return coloredBasket;
	}
}
