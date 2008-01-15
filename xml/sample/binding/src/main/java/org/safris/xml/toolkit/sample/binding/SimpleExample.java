package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.simple.SimpleFruit;
import org.safris.xml.toolkit.sample.binding.simple.SimpleFruitBasket;

public class SimpleExample
{
	public static void main(String[] args)
	{
		new SimpleTest().testExample();
	}

	public Binding runExample()
	{
		SimpleFruit strawberry = new SimpleFruit();
		strawberry.setSimpleNameAttr(new SimpleFruit.SimpleNameAttr("strawberry"));
		strawberry.setSimpleSweetAttr(new SimpleFruit.SimpleSweetAttr(true));

		SimpleFruit jackfruit = new SimpleFruit();
		jackfruit.setSimpleNameAttr(new SimpleFruit.SimpleNameAttr("jackfruit"));
		jackfruit.setSimpleSweetAttr(new SimpleFruit.SimpleSweetAttr(false));
		jackfruit.setSimpleDryAttr(new SimpleFruit.SimpleDryAttr(false));

		SimpleFruitBasket.SimpleFruits fruits = new SimpleFruitBasket.SimpleFruits();
		fruits.addSimpleFruit(strawberry);
		fruits.addSimpleFruit(jackfruit);

		SimpleFruitBasket genericBasket = new SimpleFruitBasket();
		genericBasket.setSimpleFruits(fruits);

		// Now verify the integrity of the code representing this XML structure.
		return genericBasket;
	}
}
