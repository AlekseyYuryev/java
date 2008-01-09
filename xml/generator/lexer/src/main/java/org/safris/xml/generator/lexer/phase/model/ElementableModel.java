package org.safris.xml.generator.lexer.phase.model;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;

public interface ElementableModel
{
	public void addMultiplicableModel(MultiplicableModel multiplicable);
	public LinkedHashSet<MultiplicableModel> getMultiplicableModels();
}
