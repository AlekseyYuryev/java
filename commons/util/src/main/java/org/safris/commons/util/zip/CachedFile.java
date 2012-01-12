/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.util.zip;

import java.io.File;
import java.net.URI;

// FIXME: This is not the right way to be doing this!
// FIXME: It creates unnecessary dependencies and relations.
public class CachedFile extends File {
	private final byte[] bytes;

	public CachedFile(String pathname, byte[] bytes) {
		super(pathname);
		this.bytes = bytes;
	}

	public CachedFile(String parent, String child, byte[] bytes) {
		super(parent, child);
		this.bytes = bytes;
	}

	public CachedFile(File parent, String child, byte[] bytes) {
		super(parent, child);
		this.bytes = bytes;
	}

	public CachedFile(URI uri, byte[] bytes) {
		super(uri);
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}
}
