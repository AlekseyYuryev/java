/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.toolkit.test.binding.regression;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import org.junit.Ignore;
import org.safris.commons.io.Files;

@Ignore("Make this a real test!")
public class TestMain {
  private static final FileFilter xsdFileFilter = new FileFilter()
  {
    public boolean accept(File pathname) {
      return pathname.getName().endsWith(".xsd");
    }
  };

  public static void main(String[] args) {
    while (true) {
      try {
        final File debugDir = new File("../../bin/Debug");
        final File targetDir = new File("target/generated-sources/xmlbinding/");
        if (debugDir.exists() && targetDir.exists()) {
          final List<File> files = Files.listAll(targetDir, xsdFileFilter);
          for (File file : files)
            Files.copy(file, new File(debugDir, Files.relativePath(targetDir, file)));
        }

        XencRegressionTest.main(null);
        DsRegressionTest.main(null);
        AcRegressionTest.main(null);
        SamlRegressionTest.main(null);
        SamlpRegressionTest.main(null);
        LibRegressionTest.main(null);
//              IdppRegressionTest.main(null);
        DscRegressionTest.main(null);
        TnsRegressionTest.main(null);
        MdRegressionTest.main(null);
      }
      catch (Throwable t) {
        t.printStackTrace();
      }

      System.out.println("Smallest Tested XML Document:\n" + RegressionTestMetrics.getSmallestXMLDocument());
      System.out.println("Largest Tested XML Document:\n" + RegressionTestMetrics.getLargestXMLDocument());
      System.out.println("Test Count: " + RegressionTestMetrics.getTestCount());
    }
  }
}
