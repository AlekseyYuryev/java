package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.enums.enums_color$;
import org.safris.xml.toolkit.sample.binding.enums.enums_coloredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;

public class EnumsExample
{
	public static void main(String[] args)
	{
		new EnumsExample().runExample();
	}

	public Binding runExample()
	{
		simple_fruit strawberry = new simple_fruit();
		strawberry.add_name$(new simple_fruit._name$("strawberry"));
		strawberry.add_sweet$(new simple_fruit._sweet$(true));

		simple_fruit jackfruit = new simple_fruit();
		jackfruit.add_name$(new simple_fruit._name$("jackfruit"));
		jackfruit.add_sweet$(new simple_fruit._sweet$(false));
		jackfruit.add_dry$(new simple_fruit._dry$(false));

		enums_coloredFruitBasket._fruits simple_fruits = new enums_coloredFruitBasket._fruits();
		simple_fruits.addsimple_fruit(strawberry);
		simple_fruits.addsimple_fruit(jackfruit);

		enums_coloredFruitBasket coloredBasket = new enums_coloredFruitBasket();
		coloredBasket.addenums_color$(new enums_color$(enums_color$.BLUE));
		coloredBasket.add_fruits(simple_fruits);

		// Now verify the integrity of the code representing this XML structure.
		return coloredBasket;
	}
}
