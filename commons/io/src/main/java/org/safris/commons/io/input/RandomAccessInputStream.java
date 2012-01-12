/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.commons.io.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class RandomAccessInputStream extends InputStream {
	private final RandomAccessFile file;
	private long mark = 0;

	public RandomAccessInputStream(File file) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "r");
	}

	public int available() throws IOException {
		final long availableBytes = file.length() - file.getFilePointer();
		if (availableBytes > 0x7fffffffl)
			return 0x7fffffff;

		return (int)availableBytes;
	}

	public void close() throws IOException {
		file.close();
	}

	public void mark(int readlimit) {
		try {
			this.mark = file.getFilePointer();
		}
		catch (IOException e) {
			this.mark = -1;
		}
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		return file.read();
	}

	public int read(byte[] b) throws IOException {
		return file.read(b);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		return file.read(b, off, len);
	}

	public void reset() throws IOException {
		if (mark < 0)
			throw new IOException("Invalid mark position");

		file.seek(mark);
	}

	public long skip(long n) throws IOException {
		final long position = file.getFilePointer();
		try {
			file.seek(n + position);
		}
		catch (IOException e) {
			if (!"Negative seek offset".equals(e.getMessage()))
				throw e;
		}

		return file.getFilePointer() - position;
	}
}
