/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.pipeline;

public interface PipelineDirectory<C extends PipelineContext,K extends PipelineEntity,V extends PipelineEntity>
{
	public PipelineEntity<V> getEntity(K entity, V parent);
	public PipelineProcessor<C,K,V> getProcessor();
	public void clear();
}
