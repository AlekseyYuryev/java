package org.safris.commons.pipeline;

public interface PipelineDirectory<C extends PipelineContext,K extends PipelineEntity,V extends PipelineEntity>
{
	public PipelineEntity<V> getEntity(K entity, V parent);
	public PipelineProcessor<C,K,V> getProcessor();
	public void clear();
}
