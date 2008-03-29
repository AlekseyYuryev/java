package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.schema.attribute.Occurs;

public interface MultiplicableModel extends ElementableModel
{
	public Occurs getMaxOccurs();
	public Occurs getMinOccurs();
}
