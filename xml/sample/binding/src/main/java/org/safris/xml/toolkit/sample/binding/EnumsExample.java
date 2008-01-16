package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.enums.EnumsColorAttr;
import org.safris.xml.toolkit.sample.binding.enums.EnumsColoredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.SimpleFruit;

public class EnumsExample
{
	public static void main(String[] args)
	{
		new EnumsExample().runExample();
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

		EnumsColoredFruitBasket.SimpleFruits simpleFruits = new EnumsColoredFruitBasket.SimpleFruits();
		simpleFruits.addSimpleFruit(strawberry);
		simpleFruits.addSimpleFruit(jackfruit);

		EnumsColoredFruitBasket coloredBasket = new EnumsColoredFruitBasket();
		coloredBasket.setEnumsColorAttr(new EnumsColorAttr(EnumsColorAttr.BLUE));
		coloredBasket.setSimpleFruits(simpleFruits);

		// Now verify the integrity of the code representing this XML structure.
		return coloredBasket;
	}
}
