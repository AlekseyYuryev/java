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

package org.safris.xml.toolkit.test.binding.regression;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import org.junit.Ignore;
import org.safris.commons.io.Files;

@Ignore("Make this a real test!")
public class TestMain
{
	private static final FileFilter xsdFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(".xsd");
		}
	};

	public static void main(String[] args)
	{
		while(true)
		{
			try
			{
				final File debugDir = new File("../../bin/Debug");
				final File targetDir = new File("target/generated-sources/xmlbinding/");
				if(debugDir.exists() && targetDir.exists())
				{
					final List<File> files = Files.listAll(targetDir, xsdFileFilter);
					for(File file : files)
						Files.copy(file, new File(debugDir, Files.relativePath(targetDir, file)));
				}

				XencRegressionTest.main(null);
				DsRegressionTest.main(null);
				AcRegressionTest.main(null);
				SamlRegressionTest.main(null);
				SamlpRegressionTest.main(null);
				LibRegressionTest.main(null);
//				IdppRegressionTest.main(null);
				DscRegressionTest.main(null);
				TnsRegressionTest.main(null);
				MdRegressionTest.main(null);
			}
			catch(Throwable t)
			{
				t.printStackTrace();
			}

			System.out.println("Smallest Tested XML Document:\n" + RegressionTestMetrics.getSmallestXMLDocument());
			System.out.println("Largest Tested XML Document:\n" + RegressionTestMetrics.getLargestXMLDocument());
			System.out.println("Test Count: " + RegressionTestMetrics.getTestCount());
		}
	}
}
