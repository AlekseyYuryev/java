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

package org.safris.xml.generator.compiler.processor.write;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;

public class BindingFileFilter implements FileFilter
{
	private final boolean acceptHidden;

	public BindingFileFilter(boolean acceptHidden)
	{
		this.acceptHidden = acceptHidden;
	}

	public boolean accept(File pathname)
	{
		if(!acceptHidden && pathname.isHidden())
			return false;

		if(pathname.isDirectory())
			return true;

		try
		{
			final InputStream in = pathname.toURL().openStream();
			final byte[] bytes = new byte[15];
			in.read(bytes);
			in.close();
			return new String(bytes).contains("Safris Technologies Inc.");
		}
		catch(IOException e)
		{
			return false;
		}
	}
}
