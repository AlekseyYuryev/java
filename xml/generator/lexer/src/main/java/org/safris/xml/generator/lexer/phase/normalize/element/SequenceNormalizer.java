package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.model.ElementableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.SequenceModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.module.phase.Nameable;

public class SequenceNormalizer extends Normalizer<SequenceModel>
{
	public SequenceNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(SequenceModel model)
	{
	}

	protected void stage2(SequenceModel model)
	{
	}

	protected void stage3(SequenceModel model)
	{
	}

	protected void stage4(SequenceModel model)
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

	protected void stage5(SequenceModel model)
	{
	}

	protected void stage6(SequenceModel model)
	{
	}
}
