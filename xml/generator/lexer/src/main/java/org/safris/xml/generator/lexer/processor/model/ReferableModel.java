package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.ReferableModel;

	public interface ReferableModel<T extends ReferableModel> extends Nameable<Model>
	{
public T getRef();
	public void setRef(T ref);
}
