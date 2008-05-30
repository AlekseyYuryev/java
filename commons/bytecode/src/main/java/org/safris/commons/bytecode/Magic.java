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

package org.safris.commons.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Magic
{
	private static final int[] validMagic = new int[]{0xca, 0xfe, 0xba, 0xbe};

	public static void main(String[] args) throws IOException
	{
		for(int i = 0; i < args.length; i++)
			changeClassVersion(new File(args[i]), 47);
	}

	private static void changeClassVersion(File file, int version) throws IOException
	{
		final File inFile = file;
		final File tempFile = new File(file + ".tmp");
		final DataInputStream in = new DataInputStream(new FileInputStream(inFile));
		final DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));

		final int[] magic = new int[4];
		for(int i = 0; i < magic.length; i++)
		{
			magic[i] = in.read();
			if(validMagic[i] != magic[i])
			{
				in.close();
				out.close();
				tempFile.deleteOnExit();
				throw new Error(file.getName() + " is not a valid class!");
			}

			out.write(magic[i]);
		}

		final int[] minor = new int[2];
		for(int i = 0; i < minor.length; i++)
			minor[i] = in.read();

		out.write(0);
		out.write(0);

		final int[] major = new int[2];
		for(int i = 0; i < major.length; i++)
			major[i] = in.read();

		if((major[0] | major[1]) == version)
		{
			in.close();
			out.close();
			tempFile.deleteOnExit();
			System.out.println(file.getName() + " is already version " + version);
			System.exit(1);
		}

		out.write(0);
		out.write(version);

		int ch = -1;
		while((ch = in.read()) != -1)
			out.write(ch);

		in.close();
		out.close();

		tempFile.renameTo(inFile);
	}
}
