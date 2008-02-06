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
	private static final int targetVersion = 47;

	public static void main(String[] args) throws IOException
	{
		for(int i = 0; i < args.length; i++)
			checkClassVersion(args[i]);
	}

	private static void checkClassVersion(String filename) throws IOException
	{
		final File inFile = new File(filename);
		final File outFile = new File(filename + ".tmp");
		final DataInputStream in = new DataInputStream(new FileInputStream(inFile));
		final DataOutputStream out = new DataOutputStream(new FileOutputStream(outFile));

		final int[] magic = new int[4];
		for(int i = 0; i < magic.length; i++)
		{
			magic[i] = in.read();
			if(validMagic[i] != magic[i])
			{
				in.close();
				out.close();
				outFile.deleteOnExit();
				System.out.println(filename + " is not a valid class!");
				System.exit(1);
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

		if((major[0] | major[1]) == targetVersion)
		{
			in.close();
			out.close();
			outFile.deleteOnExit();
			System.out.println(filename + " is already version " + targetVersion);
			System.exit(1);
		}

		out.write(0);
		out.write(targetVersion);

		int ch = -1;
		while((ch = in.read()) != -1)
			out.write(ch);

		in.close();
		out.close();

		outFile.renameTo(inFile);
//		System.out.println(filename + ": " + (major[0] | major[1]) + " . " + (minor[0] | minor[1]));
	}
}
