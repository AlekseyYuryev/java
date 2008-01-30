package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.Nameable;
import org.safris.xml.generator.lexer.phase.model.ElementableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AllModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;

public class AllNormalizer extends Normalizer<AllModel>
{
	public AllNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(AllModel model)
	{
	}

	protected void stage2(AllModel model)
	{
	}

	protected void stage3(AllModel model)
	{
	}

	protected void stage4(AllModel model)
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

	protected void stage5(AllModel model)
	{
	}

	protected void stage6(AllModel model)
	{
	}
}
