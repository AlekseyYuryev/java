package org.safris.xml.generator.lexer.processor;

import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.lexer.schema.attribute.Form;

public interface Formable<T extends PipelineEntity>
{
	public Form getFormDefault();
}
