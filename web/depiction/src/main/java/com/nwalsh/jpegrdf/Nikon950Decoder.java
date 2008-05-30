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

package com.nwalsh.jpegrdf;

import java.util.Map;
import org.w3c.tools.jpeg.ExifData;
import org.w3c.tools.jpeg.TagDecoder;

/**
 * A decoder for the Nikon 950 MakerNote tag.
 *
 * @author Norman Walsh
 * @version $Revision: 1.1 $
 */
public class Nikon950Decoder implements TagDecoder
{
	private static final String namespace = "http://nwalsh.com/rdf/exif-nikon950#";
	private String prefix = "";

	Map<String,String> exif = null;
	ExifData data = null;

	public String getNamespace()
	{
		return namespace;
	}

	public void setPrefix(String prefix)
	{
		if(prefix != null && !"".equals(prefix))
		{
			this.prefix = prefix + ":";
		}
	}

	public void decode(Map<String,String> exif, ExifData data, int format, int offset, int length)
	{
		this.exif = exif;
		this.data = data;

		byte[] raw = data.getBytes(offset, length);

		if(raw == null)
		{
			// abandon ship...
			return;
		}

		String noteStr = new String(raw, 0, length);
		if(noteStr.length() > 6
		&& noteStr.substring(0, 5).equals("Nikon")
		&& format == ExifData.FMT_UNDEFINED)
		{
			decodeNikon(offset, length);
		}

		// else silently do nothing
	}

	protected void decodeNikon(int offsetBase, int length)
	{
		//System.err.println("Decode Nikon MakerNote: " + offsetBase + ", " + length);

		int dirStart = offsetBase + 8;
		int numEntries = data.get16u(dirStart);

		for(int de = 0; de < numEntries; de++)
		{
			int dirOffset = dirStart + 2 + (12 * de);

			int tag = data.get16u(dirOffset);
			int format = data.get16u(dirOffset + 2);
			int components = data.get32u(dirOffset + 4);

			//System.err.println("EXIF: entry: 0x" + Integer.toHexString(tag)
			//+ " " + format
			//+ " " + components);

			if(format < 0 || format > data.NUM_FORMATS)
			{
				System.err.println("Bad number of formats in EXIF dir: " + format);
				return;
			}

			int byteCount = components * ExifData.bytesPerFormat[format];
			int valueOffset = dirOffset + 8;

			if(byteCount > 4)
			{
				int offsetVal = data.get32u(dirOffset + 8);
				valueOffset = offsetVal + 6;
			}

			switch(tag)
			{
				case 0x0002:
					// Unknown: "09.41" or "08.00"
					break;
				case 0x0003:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "quality", "" + value);
					}
					break;
				case 0x0004:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "colorMode", "" + value);
					}
					break;
				case 0x0005:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "imageAdjustment", "" + value);
					}
					break;
				case 0x0006:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "ccdSensitivity", "" + value);
					}
					break;
				case 0x0007:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "whiteBalance", "" + value);
					}
					break;
				case 0x0008:
					if(format == ExifData.FMT_URATIONAL)
					{
						int num = data.get32u(valueOffset);
						int den = data.get32u(valueOffset + 4);
						exif.put(prefix + "focus", "" + num + "/" + den);
					}
					break;
				case 0x0009:
					// Unknown
					break;
				case 0x000A:
					if(format == ExifData.FMT_URATIONAL)
					{
						int num = data.get32u(valueOffset);
						int den = data.get32u(valueOffset + 4);
						exif.put(prefix + "digitalZoom", "" + num + "/" + den);
					}
					break;
				case 0x000B:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "converter", "" + value);
					}
					break;
				case 0x0F00:
					// Unknown
					break;
				default:
					break;
			}
		}
	}
}
