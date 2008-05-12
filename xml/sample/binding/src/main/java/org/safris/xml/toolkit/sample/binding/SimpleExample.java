package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruit;
import org.safris.xml.toolkit.sample.binding.simple.simple_fruitBasket;

public class SimpleExample
{
	public static void main(String[] args)
	{
		new SimpleExample().runExample();
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

		simple_fruitBasket._fruits fruits = new simple_fruitBasket._fruits();
		fruits.addsimple_fruit(strawberry);
		fruits.addsimple_fruit(jackfruit);

		simple_fruitBasket genericBasket = new simple_fruitBasket();
		genericBasket.add_fruits(fruits);

		// Now verify the integrity of the code representing this XML structure.
		return genericBasket;
	}
}
