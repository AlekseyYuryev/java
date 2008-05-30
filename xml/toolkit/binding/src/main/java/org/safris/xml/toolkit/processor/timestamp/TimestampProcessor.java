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

package org.safris.xml.toolkit.processor.timestamp;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import org.safris.commons.io.Files;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class TimestampProcessor implements PipelineEntity<Bundle>, PipelineProcessor<GeneratorContext,Bundle,Bundle>
{
	private static final FileFilter fileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname != null && pathname.isFile();
		}
	};

	private static final FileFilter dirFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname != null && pathname.isDirectory();
		}
	};

	protected TimestampProcessor()
	{
	}

	public Collection<Bundle> process(GeneratorContext pipelineContext, Collection<Bundle> documents, PipelineDirectory<GeneratorContext,Bundle,Bundle> directory)
	{
		// Get the earliest lastModified time of all the files
		long lastModified = Long.MAX_VALUE;
		for(File file : Files.listAll(pipelineContext.getDestDir(), fileFilter))
			if(file.lastModified() < lastModified)
				lastModified = file.lastModified();

		// Set the lastModified time of all directories to just before the value from above
		for(File dir : Files.listAll(pipelineContext.getDestDir(), dirFileFilter))
			dir.setLastModified(lastModified - 100);

		return null;
	}
}
