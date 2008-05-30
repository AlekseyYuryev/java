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

package org.safris.xml.generator.lexer.processor.normalize;

import org.safris.commons.logging.Logger;
import org.safris.xml.generator.lexer.lang.LexerLoggerName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.commons.pipeline.PipelineEntity;

public abstract class Normalizer<T extends Model> implements PipelineEntity<Normalizer>
{
	protected static final Logger logger = Logger.getLogger(LexerLoggerName.NORMALIZE);
	private final NormalizerDirectory directory;

	public Normalizer(NormalizerDirectory directory)
	{
		this.directory = directory;
	}

	public NormalizerDirectory getDirectory()
	{
		return directory;
	}

	// NOTE: This stage used for fixing globally accessible types
	protected abstract void stage1(T handler);

	// NOTE: This stage used for de-referencing qName references to correct types
	protected abstract void stage2(T handler);

	// NOTE: This stage used for de-referencing <redefine/> rules
	protected abstract void stage3(T handler);

	// NOTE: This stage used for injection of information into parent elements as per the physical schema structure
	protected abstract void stage4(T handler);

	// NOTE: This stage used for injection of information into parent elements as per the logical schema structure
	protected abstract void stage5(T handler);

	// NOTE: This stage used to ammend information in certain edge-case situations.
	protected abstract void stage6(T handler);
}
