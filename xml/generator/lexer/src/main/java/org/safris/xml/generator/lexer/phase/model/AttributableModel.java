package org.safris.xml.generator.lexer.phase.model;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.model.element.AttributeModel;

public interface AttributableModel
{
	public void addAttribute(AttributeModel model);
	public LinkedHashSet<AttributeModel> getAttributes();
}
