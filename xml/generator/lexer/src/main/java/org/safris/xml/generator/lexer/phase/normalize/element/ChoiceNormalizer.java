package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.model.ElementableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.ChoiceModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.module.phase.Nameable;

public class ChoiceNormalizer extends Normalizer<ChoiceModel>
{
	protected void stage1(ChoiceModel model)
	{
	}

	protected void stage2(ChoiceModel model)
	{
	}

	protected void stage3(ChoiceModel model)
	{
	}

	protected void stage4(ChoiceModel model)
	{
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof ElementableModel && (!(parent instanceof Nameable) || ((Nameable)parent).getName() != null))
			{
				((ElementableModel)parent).addMultiplicableModel(model);
				break;
			}
		}
	}

	protected void stage5(ChoiceModel model)
	{
	}

	protected void stage6(ChoiceModel model)
	{
	}
}
