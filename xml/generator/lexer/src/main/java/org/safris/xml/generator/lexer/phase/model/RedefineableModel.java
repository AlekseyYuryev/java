package org.safris.xml.generator.lexer.phase.model;

import org.safris.xml.generator.lexer.phase.model.NamedModel;

public interface RedefineableModel<T extends NamedModel>
{
	public void setRedefine(T model);
	public T getRedefine();
}
