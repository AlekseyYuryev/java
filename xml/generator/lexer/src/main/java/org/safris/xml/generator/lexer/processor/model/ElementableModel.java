package org.safris.xml.generator.lexer.processor.model;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.processor.model.MultiplicableModel;

public interface ElementableModel
{
	public void addMultiplicableModel(MultiplicableModel multiplicable);
	public LinkedHashSet<MultiplicableModel> getMultiplicableModels();
}
