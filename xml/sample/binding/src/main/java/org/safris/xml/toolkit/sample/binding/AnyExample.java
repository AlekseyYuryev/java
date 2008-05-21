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

public class AnyExample
{
	public static void main(String[] args)
	{
		new AnyExample().runExample();
	}

	public Binding runExample()
	{
		simple_fruit strawberry = new simple_fruit();
		strawberry.set_name$(new simple_fruit._name$("strawberry"));
		strawberry.set_sweet$(new simple_fruit._sweet$(true));

		simple_fruit jackfruit = new simple_fruit();
		jackfruit.set_name$(new simple_fruit._name$("jackfruit"));
		jackfruit.set_sweet$(new simple_fruit._sweet$(false));
		jackfruit.set_dry$(new simple_fruit._dry$(false));

		simple_fruitBasket._fruits simple_fruits = new simple_fruitBasket._fruits();
		simple_fruits.addsimple_fruit(strawberry);
		simple_fruits.addsimple_fruit(jackfruit);

		enums_coloredFruitBasket coloredBasket = new enums_coloredFruitBasket();
		coloredBasket.setenums_color$(new enums_color$(enums_color$.BLUE));
		coloredBasket.add_fruits(simple_fruits);

		$type_fleshyFruitType berry = new $type_fleshyFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return new simple_fruit();
			}
		};
		berry.set_name$(new $type_fleshyFruitType._name$($type_fleshyFruitType._name$.BERRY));
		berry.set_pericarp$(new $type_fleshyFruitType._pericarp$($type_fleshyFruitType._pericarp$.SOFT));

		// Again, instantiate a nameless element.GenericBasket
		$type_indehiscentDryFruitType grain = new $type_indehiscentDryFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return new simple_fruit();
//				return new SimpleSpecialFruit();
			}
		};
		grain.set_name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.GRAIN));

		// Again, instantiate a nameless element.
		$type_indehiscentDryFruitType nut = new $type_indehiscentDryFruitType()
		{
			protected $simple_fruitType inherits()
			{
				return new simple_fruit();
			}
		};
		nut.set_name$(new $type_indehiscentDryFruitType._name$($type_indehiscentDryFruitType._name$.NUT));
		nut.set_dry$(new $type_indehiscentDryFruitType._dry$($type_indehiscentDryFruitType._dry$.TRUE));
//		nut.setDryAttr(new ITypesimple_fruitType.DryAttr(false));

		any_trash trash = new any_trash();
		trash.addAny(coloredBasket);
		trash.addAny(berry);
		trash.addAny(grain);
		trash.addAny(nut);

		// Now verify the integrity of the code representing this XML structure.
		return trash;
	}
}
