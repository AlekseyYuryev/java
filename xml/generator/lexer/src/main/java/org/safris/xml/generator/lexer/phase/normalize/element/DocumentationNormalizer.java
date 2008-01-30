package org.safris.xml.generator.lexer.phase.normalize.element;

import org.safris.xml.generator.lexer.phase.model.DocumentableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.element.DocumentationModel;
import org.safris.xml.generator.lexer.phase.normalize.Normalizer;
import org.safris.xml.generator.lexer.phase.normalize.NormalizerDirectory;
import org.safris.xml.generator.processor.phase.Nameable;

public class DocumentationNormalizer extends Normalizer<DocumentationModel>
{
	public DocumentationNormalizer(NormalizerDirectory directory)
	{
		super(directory);
	}

	protected void stage1(DocumentationModel model)
	{
	}

	protected void stage2(DocumentationModel model)
	{
	}

	protected void stage3(DocumentationModel model)
	{
	}

	protected void stage4(DocumentationModel model)
	{
		Model parent = model;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof DocumentableModel && parent instanceof Nameable && ((Nameable)parent).getName() != null)
			{
				final DocumentationModel documentationModel = ((DocumentableModel)parent).getDocumentation();
				if(documentationModel != null)
					documentationModel.merge(model);
				else
					((DocumentableModel)parent).setDocumentation(model);

				break;
			}
		}
	}

	protected void stage5(DocumentationModel model)
	{
	}

	protected void stage6(DocumentationModel model)
	{
	}
}
