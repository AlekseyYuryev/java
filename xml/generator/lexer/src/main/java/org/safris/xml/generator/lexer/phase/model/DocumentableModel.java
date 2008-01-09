package org.safris.xml.generator.lexer.phase.model;

import org.safris.xml.generator.lexer.phase.model.element.DocumentationModel;

public interface DocumentableModel
{
	public void setDocumentation(DocumentationModel documentation);
	public DocumentationModel getDocumentation();
}
