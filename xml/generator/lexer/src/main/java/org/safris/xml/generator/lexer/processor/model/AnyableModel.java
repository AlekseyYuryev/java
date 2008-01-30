package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.schema.attribute.Namespace;
import org.safris.xml.generator.lexer.schema.attribute.ProcessContents;

public interface AnyableModel
{
	public ProcessContents getProcessContents();
	public Namespace getNamespace();
}
