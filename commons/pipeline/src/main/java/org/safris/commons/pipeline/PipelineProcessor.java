package org.safris.commons.pipeline;

import java.util.Collection;

public interface PipelineProcessor<C extends PipelineContext,I extends PipelineEntity,O extends PipelineEntity>
{
	public Collection<O> process(C pipelineContext, Collection<I> documents, PipelineDirectory<C,I,O> directory);
}
