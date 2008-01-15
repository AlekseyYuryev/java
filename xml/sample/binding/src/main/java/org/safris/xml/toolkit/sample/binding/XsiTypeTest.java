package org.safris.xml.toolkit.sample.binding;

import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.toolkit.sample.binding.enums.EnumsColorAttr;
import org.safris.xml.toolkit.sample.binding.enums.EnumsColoredFruitBasket;
import org.safris.xml.toolkit.sample.binding.simple.ISimpleFruitType;
import org.safris.xml.toolkit.sample.binding.simple.SimpleFruitBasket;
import org.safris.xml.toolkit.sample.binding.xsitype.ITypeDehiscentDryFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.ITypeFleshyFruitType;
import org.safris.xml.toolkit.sample.binding.xsitype.ITypeIndehiscentDryFruitType;

public class XsiTypeTest
{
	public static void main(String[] args)
	{
		new XsiTypeTest().testExample();
	}

	public Binding testExample()
	{
		// Since there is no element declaration for the fleshyFruitType,
		// we need to instantiate a nameless element. Once this element is
		// put into the basket, the element will obtain the needed name.
		ITypeFleshyFruitType berry = new ITypeFleshyFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return null;
			}
		};
		berry.setTypeNameAttr(new ITypeFleshyFruitType.TypeNameAttr(ITypeFleshyFruitType.TypeNameAttr.BERRY));
		berry.setTypePericarpAttr(new ITypeFleshyFruitType.TypePericarpAttr(ITypeFleshyFruitType.TypePericarpAttr.SOFT));

		// Again, instantiate a nameless element.
		ITypeFleshyFruitType drupe = new ITypeFleshyFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return null;
			}
		};
		drupe.setTypeNameAttr(new ITypeFleshyFruitType.TypeNameAttr(ITypeFleshyFruitType.TypeNameAttr.DRUPE));
		drupe.setTypePericarpAttr(new ITypeFleshyFruitType.TypePericarpAttr(ITypeFleshyFruitType.TypePericarpAttr.FLESHY));

		// Again, instantiate a nameless element.
		ITypeDehiscentDryFruitType legume = new ITypeDehiscentDryFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return null;
			}
		};
		legume.setTypeNameAttr(new ITypeDehiscentDryFruitType.TypeNameAttr(ITypeDehiscentDryFruitType.TypeNameAttr.LEGUME));

		// Again, instantiate a nameless element.
		ITypeDehiscentDryFruitType follicle = new ITypeDehiscentDryFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return null;
			}
		};
		follicle.setTypeNameAttr(new ITypeDehiscentDryFruitType.TypeNameAttr(ITypeDehiscentDryFruitType.TypeNameAttr.FOLLICLE));

		// Again, instantiate a nameless element.GenericBasket
		ITypeIndehiscentDryFruitType grain = new ITypeIndehiscentDryFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return null;
			}
		};
		grain.setTypeNameAttr(new ITypeIndehiscentDryFruitType.TypeNameAttr(ITypeIndehiscentDryFruitType.TypeNameAttr.GRAIN));

		// Again, instantiate a nameless element.
		ITypeIndehiscentDryFruitType nut = new ITypeIndehiscentDryFruitType()
		{
			protected ISimpleFruitType inherits()
			{
				return null;
			}
		};
		nut.setTypeNameAttr(new ITypeIndehiscentDryFruitType.TypeNameAttr(ITypeIndehiscentDryFruitType.TypeNameAttr.NUT));
		nut.setTypeDryAttr(new ITypeIndehiscentDryFruitType.TypeDryAttr(ITypeIndehiscentDryFruitType.TypeDryAttr.TRUE));
//		nut.setDryAttr(new ITypeSimpleFruitType.DryAttr(false));

		SimpleFruitBasket.SimpleFruits fruits = new SimpleFruitBasket.SimpleFruits();
		fruits.addSimpleFruit(berry);
		fruits.addSimpleFruit(drupe);
		fruits.addSimpleFruit(legume);
		fruits.addSimpleFruit(follicle);
		fruits.addSimpleFruit(grain);
		fruits.addSimpleFruit(nut);

		EnumsColoredFruitBasket coloredBasket = new EnumsColoredFruitBasket();
		coloredBasket.setEnumsColorAttr(new EnumsColorAttr(EnumsColorAttr.RED));
		coloredBasket.setSimpleFruits(fruits);

		// Now verify the integrity of the code representing this XML structure.
		return coloredBasket;
	}
}
