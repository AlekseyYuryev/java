package org.safris.xml.generator.lexer.phase.model;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.ReferableModel;
import org.safris.xml.generator.module.phase.Nameable;

	public interface ReferableModel<T extends ReferableModel> extends Nameable<Model>
	{
public T getRef();
	public void setRef(T ref);
}
