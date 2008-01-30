package org.safris.xml.generator.compiler.lang;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;
import org.safris.xml.generator.lexer.phase.model.RedefineableModel;
import org.safris.xml.generator.lexer.phase.model.element.ElementModel;
import org.safris.xml.generator.module.phase.BindingQName;
import org.safris.xml.generator.module.phase.Nameable;

public class ElementWrapper extends Model
{
	public static LinkedHashSet<ElementWrapper> asSet(LinkedHashSet<MultiplicableModel> multiplicableModels)
	{
		final LinkedHashSet<ElementWrapper> elementWrappers = new LinkedHashSet<ElementWrapper>();
		asSet(multiplicableModels, elementWrappers, 1, 1, new HashSet<BindingQName>());
		return elementWrappers;
	}

	private static void asSet(LinkedHashSet<MultiplicableModel> multiplicableModels, LinkedHashSet<ElementWrapper> elementWrappers, int min, int max, Collection<BindingQName> redefines)
	{
		for(MultiplicableModel multiplicableModel : multiplicableModels)
		{
			// FIXME: the list used to track redefines seems BAD!!!
			if(multiplicableModel instanceof RedefineableModel && ((RedefineableModel)multiplicableModel).getRedefine() != null && !redefines.contains(((Nameable)multiplicableModel).getName()))
			{
				multiplicableModel = (MultiplicableModel)((RedefineableModel)multiplicableModel).getRedefine();
				redefines.add(((Nameable)multiplicableModel).getName());
			}

			int maxOccurs = multiplicableModel.getMaxOccurs().getValue();
			if(maxOccurs != Integer.MAX_VALUE)
				maxOccurs *= max;

			int minOccurs = multiplicableModel.getMinOccurs().getValue();
			minOccurs *= min;

			if(multiplicableModel instanceof ElementModel)
				elementWrappers.add(new ElementWrapper((ElementModel)multiplicableModel, minOccurs, maxOccurs));
			else
				asSet(multiplicableModel.getMultiplicableModels(), elementWrappers, minOccurs, maxOccurs, redefines);
		}
	}

	private final ElementModel elementModel;
	private final int minOccurs;
	private final int maxOccurs;

	public ElementWrapper(ElementModel elementModel, int minOccurs, int maxOccurs)
	{
		super(null, elementModel.getParent());
		this.elementModel = elementModel;
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
	}

	public final ElementModel getElementModel()
	{
		return elementModel;
	}

	public final int getMinOccurs()
	{
		return minOccurs;
	}

	public final int getMaxOccurs()
	{
		return maxOccurs;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof ElementWrapper))
			return false;

		return elementModel.equals(((ElementWrapper)obj).elementModel);
	}

	public int hashCode()
	{
		return elementModel.hashCode();
	}
}
