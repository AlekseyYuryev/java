package org.safris.xml.generator.lexer.processor.normalize.element;

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.ChoiceModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class ChoiceNormalizer extends Normalizer<ChoiceModel>
{
	public ChoiceNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

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