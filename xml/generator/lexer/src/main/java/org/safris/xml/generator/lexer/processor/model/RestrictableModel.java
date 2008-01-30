package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;

public interface RestrictableModel<T extends RestrictableModel>
{
	public AliasModel getRestrictionOwner();
	public void setRestrictionOwner(AliasModel restrictionOwner);
	
	public T getRestriction();
	public void setRestriction(T restriction);
}
