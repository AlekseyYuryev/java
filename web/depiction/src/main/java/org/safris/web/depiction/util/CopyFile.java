package org.safris.web.depiction.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public abstract class CopyFile
{
	public static synchronized void copyFile(File in, File out) throws IOException
	{
		FileChannel sourceChannel = new FileInputStream(in).getChannel();
		FileChannel destinationChannel = new FileOutputStream(out).getChannel();
		sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
		// or
		//  destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		sourceChannel.close();
		destinationChannel.close();
	}
}
