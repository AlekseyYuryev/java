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

package org.safris.xml.generator.lexer.processor;

import java.io.File;
import java.io.IOException;
import org.safris.commons.pipeline.PipelineContext;

public final class GeneratorContext implements PipelineContext
{
	private final long manifestLastModified;
	private final File destDir;
	private final boolean explodeJars;
	private final boolean overwrite;

	public GeneratorContext(long manifestLastModified, File destDir, boolean explodeJars, boolean overwrite)
	{
		this.manifestLastModified = manifestLastModified;
		File tempDestDir;
		try
		{
			tempDestDir = destDir.getCanonicalFile();
		}
		catch(IOException e)
		{
			tempDestDir = destDir;
		}
		this.destDir = tempDestDir;
		this.explodeJars = explodeJars;
		this.overwrite = overwrite;
	}

	public long getManifestLastModified()
	{
		return manifestLastModified;
	}

	public File getDestDir()
	{
		return destDir;
	}

	public boolean getExplodeJars()
	{
		return explodeJars;
	}

	public boolean getOverwrite()
	{
		return overwrite;
	}
}
