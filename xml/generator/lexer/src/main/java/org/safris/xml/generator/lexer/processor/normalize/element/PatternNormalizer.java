package org.safris.xml.generator.lexer.processor.normalize.element;

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.PatternableModel;
import org.safris.xml.generator.lexer.processor.model.element.PatternModel;
import org.safris.xml.generator.lexer.processor.normalize.Normalizer;
import org.safris.xml.generator.lexer.processor.normalize.NormalizerDirectory;

public class PatternNormalizer extends Normalizer<PatternModel>
{
	public PatternNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(PatternModel model)
	{
	}

	protected void stage2(PatternModel model)
	{
	}

	protected void stage3(PatternModel model)
	{
	}

	protected void stage4(PatternModel model)
	{
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof PatternableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				((PatternableModel)parent).addPattern(model);
				break;
			}
		}
	}

	protected void stage5(PatternModel model)
	{
	}

	protected void stage6(PatternModel model)
	{
	}
}
