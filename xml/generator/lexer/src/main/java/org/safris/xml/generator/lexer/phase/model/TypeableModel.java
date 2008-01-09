package org.safris.xml.generator.lexer.phase.model;

import org.safris.xml.generator.lexer.phase.model.TypeableModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;

public interface TypeableModel<T extends TypeableModel>
{
	public SimpleTypeModel getSuperType();
}
