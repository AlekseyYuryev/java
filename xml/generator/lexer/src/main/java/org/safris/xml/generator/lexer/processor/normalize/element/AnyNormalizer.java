package org.safris.xml.generator.lexer.processor.normalize.element;

import org.safris.xml.generator.lexer.processor.model.ElementableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AnyModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class AnyNormalizer extends Normalizer<AnyModel>
{
	public AnyNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

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
