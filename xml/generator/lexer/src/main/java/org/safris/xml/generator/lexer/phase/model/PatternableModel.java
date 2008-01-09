package org.safris.xml.generator.lexer.phase.model;

import java.util.LinkedHashSet;
import org.safris.xml.generator.lexer.phase.model.element.PatternModel;

public interface PatternableModel
{
	public void addPattern(PatternModel model);
	public LinkedHashSet<PatternModel> getPatterns();
}
