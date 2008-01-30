package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.Nameable;
import org.safris.xml.generator.lexer.phase.model.AttributableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.AnyAttributeModel;
import org.safris.xml.generator.lexer.phase.model.element.AttributeGroupModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;

public class AnyAttributeNormalizer extends Normalizer<AnyAttributeModel>
{
	public AnyAttributeNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(AnyAttributeModel model)
	{
	}

	protected void stage2(AnyAttributeModel model)
	{
		// add the handler attribute to the attributeGroup
		if(model.getParent() instanceof AttributeGroupModel)
			((AttributeGroupModel)model.getParent()).addAttribute(model);
	}

	protected void stage3(AnyAttributeModel model)
	{
	}

	protected void stage4(AnyAttributeModel model)
	{
		// Add the handler to the Attributable class with a name
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof AttributableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				((AttributableModel)parent).addAttribute(model);
				break;
			}
		}
	}

	protected void stage5(AnyAttributeModel model)
	{
	}

	protected void stage6(AnyAttributeModel model)
	{
	}
}
