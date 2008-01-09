package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.NamedModel;
import org.w3c.dom.Node;

public class UniqueModel extends NamedModel
{
	protected UniqueModel(Node node, Model parent)
	{
		super(node, parent);
	}
}
