package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.model.ElementableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AnyModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.module.phase.Nameable;

public class AnyNormalizer extends Normalizer<AnyModel>
{
	protected void stage1(AnyModel model)
	{
	}

	protected void stage2(AnyModel model)
	{
	}

	protected void stage3(AnyModel model)
	{
	}

	protected void stage4(AnyModel model)
	{
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof ElementableModel)
			{
				((ElementableModel)parent).addMultiplicableModel(model);
				break;
			}
		}
	}

	protected void stage5(AnyModel model)
	{
	}

	protected void stage6(AnyModel model)
	{
	}
}
