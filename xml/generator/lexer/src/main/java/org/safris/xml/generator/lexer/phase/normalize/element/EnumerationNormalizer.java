package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.Nameable;
import org.safris.xml.generator.lexer.phase.model.EnumerableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;

public class EnumerationNormalizer extends Normalizer<EnumerationModel>
{
	public EnumerationNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(EnumerationModel model)
	{
	}

	protected void stage2(EnumerationModel model)
	{
	}

	protected void stage3(EnumerationModel model)
	{
	}

	protected void stage4(EnumerationModel model)
	{
		if(model.getValue() == null || model.getValue().getLocalPart().length() == 0)
			return;

		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof EnumerableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				((EnumerableModel)parent).addEnumeration(model);
				break;
			}
		}
	}

	protected void stage5(EnumerationModel model)
	{
	}

	protected void stage6(EnumerationModel model)
	{
	}
}
