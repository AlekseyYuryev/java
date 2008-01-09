package org.safris.xml.generator.lexer.phase.model;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;

public interface EnumerableModel
{
	public void addEnumeration(EnumerationModel model);
	public LinkedHashSet<EnumerationModel> getEnumerations();
}
