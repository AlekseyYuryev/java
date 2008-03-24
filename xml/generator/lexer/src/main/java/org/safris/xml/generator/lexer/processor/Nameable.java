package org.safris.xml.generator.lexer.processor;

import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.commons.pipeline.PipelineEntity;

public interface Nameable<T extends PipelineEntity>
{
	public UniqueQName getName();
}
