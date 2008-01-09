package org.safris.xml.toolkit.test.binding.regression;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import org.safris.commons.util.Files;
import org.safris.xml.toolkit.test.binding.regression.AcRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.DscRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.LibRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.MdRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTestMetrics;
import org.safris.xml.toolkit.test.binding.regression.SamlRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.SamlpRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.TnsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.XencRegressionTest;

public class Test
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
