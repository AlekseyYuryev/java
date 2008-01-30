package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.processor.model.NamedModel;

public interface RedefineableModel<T extends NamedModel>
{
	public void setRedefine(T model);
	public T getRedefine();
}
