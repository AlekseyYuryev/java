package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.any.AnyTrash;
import org.safris.xml.toolkit.sample.binding.enums.EnumsColorAttr;
import org.safris.xml.toolkit.sample.binding.enums.EnumsColoredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.ISimpleFruitType;
import org.safris.xml.toolkit.sample.binding.simple.SimpleFruit;
import org.safris.xml.toolkit.sample.binding.simple.SimpleFruitBasket;
import org.safris.xml.toolkit.sample.binding.xsitype.ITypeFleshyFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.ITypeIndehiscentDryFruitType;

public class AnyExample
{
	public static void main(String[] args)
	{
		new AnyExample().runExample();
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

		SimpleFruitBasket.SimpleFruits SimpleFruits = new SimpleFruitBasket.SimpleFruits();
		SimpleFruits.addSimpleFruit(strawberry);
		SimpleFruits.addSimpleFruit(jackfruit);

		EnumsColoredFruitBasket coloredBasket = new EnumsColoredFruitBasket();
		coloredBasket.setEnumsColorAttr(new EnumsColorAttr(EnumsColorAttr.BLUE));
		coloredBasket.setSimpleFruits(SimpleFruits);

		ITypeFleshyFruitType berry = new ITypeFleshyFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return new SimpleFruit();
			}
		};
		berry.setTypeNameAttr(new ITypeFleshyFruitType.TypeNameAttr(ITypeFleshyFruitType.TypeNameAttr.BERRY));
		berry.setTypePericarpAttr(new ITypeFleshyFruitType.TypePericarpAttr(ITypeFleshyFruitType.TypePericarpAttr.SOFT));

		// Again, instantiate a nameless element.GenericBasket
		ITypeIndehiscentDryFruitType grain = new ITypeIndehiscentDryFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return new SimpleFruit();
//				return new SimpleSpecialFruit();
			}
		};
		grain.setTypeNameAttr(new ITypeIndehiscentDryFruitType.TypeNameAttr(ITypeIndehiscentDryFruitType.TypeNameAttr.GRAIN));

		// Again, instantiate a nameless element.
		ITypeIndehiscentDryFruitType nut = new ITypeIndehiscentDryFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return new SimpleFruit();
			}
		};
		nut.setTypeNameAttr(new ITypeIndehiscentDryFruitType.TypeNameAttr(ITypeIndehiscentDryFruitType.TypeNameAttr.NUT));
		nut.setTypeDryAttr(new ITypeIndehiscentDryFruitType.TypeDryAttr(ITypeIndehiscentDryFruitType.TypeDryAttr.TRUE));
//		nut.setDryAttr(new ITypeSimpleFruitType.DryAttr(false));

		AnyTrash trash = new AnyTrash();
		trash.addANY(coloredBasket);
		trash.addANY(berry);
		trash.addANY(grain);
		trash.addANY(nut);

		// Now verify the integrity of the code representing this XML structure.
		return trash;
	}
}
