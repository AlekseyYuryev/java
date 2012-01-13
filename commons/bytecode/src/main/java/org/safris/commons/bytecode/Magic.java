/*  Copyright Safris Software 2008
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

package org.safris.commons.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Magic {
  private static final int[] validMagic = new int[]{0xca, 0xfe, 0xba, 0xbe};

  public static void main(String[] args) throws IOException {
    for (int i = 0; i < args.length; i++)
      changeClassVersion(new File(args[i]), 47);
  }

  private static void changeClassVersion(File file, int version) throws IOException {
    final File inFile = file;
    final File tempFile = new File(file + ".tmp");
    final DataInputStream in = new DataInputStream(new FileInputStream(inFile));
    final DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));

    final int[] magic = new int[4];
    for (int i = 0; i < magic.length; i++) {
      magic[i] = in.read();
      if (validMagic[i] != magic[i]) {
        in.close();
        out.close();
        tempFile.deleteOnExit();
        throw new Error(file.getName() + " is not a valid class!");
      }

      out.write(magic[i]);
    }

    final int[] minor = new int[2];
    for (int i = 0; i < minor.length; i++)
      minor[i] = in.read();

    out.write(0);
    out.write(0);

    final int[] major = new int[2];
    for (int i = 0; i < major.length; i++)
      major[i] = in.read();

    if ((major[0] | major[1]) == version) {
      in.close();
      out.close();
      tempFile.deleteOnExit();
      System.out.println(file.getName() + " is already version " + version);
      System.exit(1);
    }

    out.write(0);
    out.write(version);

    int ch = -1;
    while ((ch = in.read()) != -1)
      out.write(ch);

    in.close();
    out.close();

    tempFile.renameTo(inFile);
  }
}
