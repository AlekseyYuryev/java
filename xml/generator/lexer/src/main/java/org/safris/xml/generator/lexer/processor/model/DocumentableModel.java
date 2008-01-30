package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.processor.model.element.DocumentationModel;

public interface DocumentableModel
{
	public void setDocumentation(DocumentationModel documentation);
	public DocumentationModel getDocumentation();
}
