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
 * A decoder for the Nikon 5700 MakerNote tag.
 *
 * <p>Credit to TsuruZoh Tachibanaya[1] and Martin Krzywinski[2] for working out
 * the tricky bits.</p>
 *
 * <p>[1] http://www.ba.wakwak.com/~tsuruzoh/Computer/Digicams/exif-e.html#APP2<br>
 * [2] http://www.worldatwar.org/photos/exif/
 * </p>
 *
 * @author Norman Walsh
 * @version $Revision: 1.2 $
 */
public class Nikon5700Decoder implements TagDecoder
{
	private static final String namespace = "http://nwalsh.com/rdf/exif-nikon5700#";
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

		//dump(raw);

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
		else
		{
			// silently do nothing
		}
	}

	protected void decodeNikon(int offsetBase, int length)
	{
		//System.err.println("Decode Nikon MakerNote: " + offsetBase + ", " + length);

		int dirStart = offsetBase + 18;
		int numEntries = data.get16u(dirStart);

		//System.err.println("\tnumEntries: " + numEntries);
		//System.err.println("\tOffset: " + offsetBase + " " + dirStart);

		for(int de = 0; de < numEntries; de++)
		{
			int dirOffset = dirStart + 2 + (12 * de);

			//System.err.println("\tdir Offset: " + dirOffset);

			int tag = data.get16u(dirOffset);
			int format = data.get16u(dirOffset + 2);
			int components = data.get32u(dirOffset + 4);
			int byteCount = components * ExifData.bytesPerFormat[format];

			/*
			 System.err.println("EXIF: entry: 0x" + Integer.toHexString(tag)
			 + " " + format
			 + " " + components
			 + " (" + byteCount + ")");
			 */

			if(format < 0 || format > data.NUM_FORMATS)
			{
				System.err.println("Bad number of formats in EXIF dir: " + format);
				return;
			}

			int valueOffset = dirOffset + 8;

			if(byteCount > 4)
			{
				int offsetVal = data.get32u(dirOffset + 8);
				//System.err.println("\t\toffsetVal=" + offsetVal);
				valueOffset = offsetBase + offsetVal + 10;
			}

			//System.err.println("\tvalue Offset: " + valueOffset);

			//if (format == ExifData.FMT_STRING || format == ExifData.FMT_UNDEFINED) {
			//String value = "";
			//if (format == ExifData.FMT_STRING) {
			//value = data.getString(valueOffset, byteCount);
			//} else {
			//value = data.getUndefined(valueOffset, byteCount);
			//}
			//System.err.println("\t" + value);
			//}

			switch(tag)
			{
				case 0x0001:
					// Unknown
					break;
				case 0x0002:
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						exif.put(prefix + "isoSetting", String.valueOf(value));
					}
				case 0x0003:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "colorMode", value.trim());
					}
					break;
				case 0x0004:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "qualityValue", value.trim());
					}
					break;
				case 0x0005:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "whiteBalance", value.trim());
					}
					break;
				case 0x0006:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "sharpening", value.trim());
					}
					break;
				case 0x0007:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "focus", value.trim());
					}
					break;
				case 0x0008:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "flash", value.trim());
					}
					break;
				case 0x0009:
				case 0x000A:
				case 0x000B:
					// Unknown
					break;
				case 0x000F:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "isoSelection", value.trim());
					}
					break;
				case 0x0011:
					// Thumbnail image IFD
					break;
				case 0x0080:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "imageAdjustment", value.trim());
					}
					break;
				case 0x0082:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "lensAdapter", value.trim());
					}
					break;
				case 0x0085:
					if(format == ExifData.FMT_URATIONAL)
					{
						int num = data.get32u(valueOffset);
						int den = data.get32u(valueOffset + 4);
						exif.put(prefix + "manualFocus", "" + num + "/" + den);
					}
					break;
				case 0x0086:
					if(format == ExifData.FMT_URATIONAL)
					{
						int num = data.get32u(valueOffset);
						int den = data.get32u(valueOffset + 4);
						exif.put(prefix + "digitalZoom", "" + num + "/" + den);
					}
					break;
				case 0x0088:
					if(format == ExifData.FMT_UNDEFINED)
					{
						String value = data.getUndefined(valueOffset, byteCount);
						// This is a bit of a hack...
						if("%00%00%00%00".equals(value))
						{
							value = "CENTER";
						}
						else if("%00%01%00%00".equals(value))
						{
							value = "TOP";
						}
						else if("%00%02%00%00".equals(value))
						{
							value = "BOTTOM";
						}
						else if("%00%03%00%00".equals(value))
						{
							value = "LEFT";
						}
						else if("%00%04%00%00".equals(value))
						{
							value = "RIGHT";
						}
						exif.put(prefix + "afFocusPosition", value);
					}
					break;
				case 0x008F:
				case 0x0094:
				case 0x009B:
					// Unknown
					break;
				case 0x0095:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "noiseReduction", value.trim());
					}
					break;
				default:
					break;
			}
		}
	}

	protected void dump(byte[] raw)
	{
		int count = 0;
		while(count < raw.length)
		{
			int pos = 0;
			while(count + pos < raw.length && pos < 12)
			{
				int chx = raw[count + pos];
				if(chx < 0)
				{ chx = 127 - chx; }
				String hex = Integer.toHexString(chx);
				if(hex.length() < 2)
				{
					hex = "0" + hex;
				}
				System.err.print(hex + " ");
				pos++;
			}
			pos = 0;
			while(count + pos < raw.length && pos < 12)
			{
				int chx = raw[count + pos];
				if(chx < 0)
				{ chx = 127 - chx; }
				if(chx < 32 || chx > 126)
				{
					System.err.print(".");
				}
				else
				{
					char ch = (char) chx;
					System.err.print(ch);
				}
				pos++;
			}
			System.err.println();
			count += 12;
		}
	}
}
