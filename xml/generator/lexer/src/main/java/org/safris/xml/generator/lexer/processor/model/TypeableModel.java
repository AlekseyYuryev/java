package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.processor.model.TypeableModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public interface TypeableModel<T extends TypeableModel>
{
	public SimpleTypeModel getSuperType();
}
