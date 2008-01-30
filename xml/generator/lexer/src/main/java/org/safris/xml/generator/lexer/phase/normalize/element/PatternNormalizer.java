package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.PatternableModel;
import org.safris.xml.generator.lexer.phase.model.element.PatternModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.module.phase.Nameable;

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
