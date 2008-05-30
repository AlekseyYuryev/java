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
 * A decoder for the Canon MakerNote tag.
 *
 * <p>Info from
 * http://www.ozhiker.com/electronics/pjmt/jpeg_info/canon_mn.html</p>
 *
 * @author Norman Walsh
 * @version $Revision: 1.1 $
 */
public class CanonDecoder implements TagDecoder
{
	private static final String namespace = "http://nwalsh.com/rdf/exif-canon#";
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

		decodeCanon(offset, length);
	}

	protected void decodeCanon(int offsetBase, int length)
	{
		/*
		 System.err.println("Decode Canon MakerNote: "
		 + offsetBase + ", " + length);
		 */

		int dirStart = offsetBase;
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

			int valueOffset = dirOffset + 8;

			if(byteCount > 4)
			{
				int offsetVal = data.get32u(dirOffset + 8);
				//System.err.println("\t\toffsetVal=" + offsetVal);
				valueOffset = offsetVal + 6;
			}

			/*
			 System.err.println("EXIF: entry: 0x" + Integer.toHexString(tag)
			 + " " + format
			 + " " + components
			 + " (" + byteCount
			 + ", " + valueOffset + ")");
			 */

			if(format < 0 || format > data.NUM_FORMATS)
			{
				System.err.println("Bad number of formats in EXIF dir: " + format);
				return;
			}

			if(format == ExifData.FMT_STRING || format == ExifData.FMT_UNDEFINED)
			{
				String value = "";
				if(format == ExifData.FMT_STRING)
				{
					value = data.getString(valueOffset, byteCount);
				}
				else
				{
					value = data.getUndefined(valueOffset, byteCount);
				}
			}

			switch(tag)
			{
				case 0x0001:
					if(format == ExifData.FMT_USHORT)
					{
						decodeSettings1(valueOffset);
					}
					break;
				case 0x0004:
					if(format == ExifData.FMT_USHORT)
					{
						decodeSettings2(valueOffset);
					}
					break;
				case 0x0006:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "imageType", "" + value);
					}
					break;
				case 0x0007:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						exif.put(prefix + "firmwareVersion", "" + value);
					}
					break;
				case 0x0008:
					if(format == ExifData.FMT_ULONG)
					{
						int value = data.get32u(valueOffset);
						exif.put(prefix + "imageNumber", "" + value);
					}
					break;
				case 0x0009:
					if(format == ExifData.FMT_STRING)
					{
						String value = data.getString(valueOffset, byteCount);
						if(!"".equals(value))
						{
							exif.put(prefix + "ownerName", "" + value);
						}
					}
					break;
				case 0x000C:
					// Unknown
					break;
				case 0x000F:
					// Unknown;
					break;
			}
		}
	}

	protected void decodeSettings1(int baseOffset)
	{
		int byteLength = data.get16u(baseOffset);

		String names[] = {
		//|--------+----------------------------------|
		//| 1      | Macro mode | 1: macro            |
		//|        |            | 2: normal           |
			"macroMode",
		//|--------+----------------------------------|
		//| 2      | If non-zero, length of           |
		//|        | self-timer in 10ths of a second  |
			"timer",
		//|--------+----------------------------------|
		//|        |            | 2: normal           |
		//| 3      | Quality    | 3: fine             |
		//|        |            | 5: superfine        |
			"quality",
		//|--------+------------+---------------------|
		//|        |            | 0: flash not fired  |
		//|        |            | 1: auto             |
		//|        |            | 2: on               |
		//|        |            | 3: red-eye          |
		//|        |            | reduction           |
		//| 4      | Flash mode | 4: slow synchro     |
		//|        |            | 5: auto + red-eye   |
		//|        |            | reduction           |
		//|        |            | 6: on + red-eye     |
		//|        |            | reduction           |
		//|        |            | 16: external flash  |
		//|        |            | (not set on D30)    |
			"flashMode",
		//|--------+------------+---------------------|
		//|        | Continuous | 0: single or timer  |
		//| 5      | drive mode | (see field 2)       |
		//|        |            | 1: continuous       |
			"driveMode",
		//|--------+----------------------------------|
		//| 6      | unknown                          |
			null,
		//|--------+----------------------------------|
		//|        |            | 0: One-Shot         |
		//|        |            | 1: AI Servo         |
		//|        |            | 2: AI Focus         |
		//| 7      | Focus Mode | 3: MF               |
		//|        |            | 4: Single (but      |
		//|        |            | check field 32)     |
		//|        |            | 5: Continuous       |
		//|        |            | 6: MF               |
			"focusMode",
		//|--------+----------------------------------|
		//| 8, 9   | unknown                          |
		//|--------+----------------------------------|
			null,
			null,
		//|--------+----------------------------------|
		//|        |            | 0: large            |
		//| 10     | Image size | 1: medium           |
		//|        |            | 2: small            |
			"imageSize",
		//|--------+------------+---------------------|
		//|        |            | 0: Full Auto        |
		//|        |            | 1: Manual           |
		//|        |            | 2: Landscape        |
		//|        |            | 3: Fast Shutter     |
		//|        |            | 4: Slow Shutter     |
		//|        | "Easy      | 5: Night            |
		//| 11     | shooting"  | 6: B&W              |
		//|        | mode       | 7: Sepia            |
		//|        |            | 8: Portrait         |
		//|        |            | 9: Sports           |
		//|        |            | 10: Macro /         |
		//|        |            | Close-Up            |
		//|        |            | 11: Pan Focus       |
			"easyMode",
		//|--------+------------+---------------------|
		//|        | Digital    | 0: none             |
		//| 12     | Zoom       | 1: 2x               |
		//|        |            | 2: 4x               |
			"digitalZoom",
		//|--------+------------+---------------------|
		//|        |            | 0xffff: low         |
		//| 13     | Contrast   | 0x0000: normal      |
		//|        |            | 0x0001: high        |
			"contrast",
		//|--------+------------+---------------------|
		//|        |            | 0xffff: low         |
		//| 14     | Saturation | 0x0000: normal      |
		//|        |            | 0x0001: high        |
			"saturation",
		//|--------+------------+---------------------|
		//|        |            | 0xffff: low         |
		//| 15     | Sharpness  | 0x0000: normal      |
		//|        |            | 0x0001: high        |
			"sharpness",
		//|--------+------------+---------------------|
		//|        |            | If zero, use        |
		//|        |            | ISOSpeedRatings     |
		//|        |            | EXIF tag instead    |
		//| 16     | ISO        | 15: auto            |
		//|        |            | 16: 50              |
		//|        |            | 17: 100             |
		//|        |            | 18: 200             |
		//|        |            | 19: 400             |
			"iso",
		//|--------+------------+---------------------|
		//|        | Metering   | 3: Evaluative       |
		//| 17     | mode       | 4: Partial          |
		//|        |            | 5: Center-weighted  |
			"meteringMode",
		//|--------+------------+---------------------|
		//|        |            | 0: manual           |
		//|        |            | 1: auto             |
		//| 18     | Focus type | 3: close-up (macro) |
		//|        |            | 8: locked (pan      |
		//|        |            | mode)               |
			"focusType",
		//|--------+------------+---------------------|
		//|        |            | 0x3000: none (MF)   |
		//|        |            | 0x3001:             |
		//| 19     | AF point   | auto-selected       |
		//|        | selected   | 0x3002: right       |
		//|        |            | 0x3003: center      |
		//|        |            | 0x3004: left        |
			"afPoint",
		//|--------+------------+---------------------|
		//|        |            | 0: "Easy shooting"  |
		//|        |            | (use field 11)      |
		//|        | Exposure   | 1: Program          |
		//| 20     | mode       | 2: Tv-priority      |
		//|        |            | 3: Av-priority      |
		//|        |            | 4: Manual           |
		//|        |            | 5: A-DEP            |
			"exposureMode",
		//|--------+----------------------------------|
		//| 21, 22 | unknown                          |
			null,
			null,
		//|--------+----------------------------------|
		//| 23     | "long" focal length of lens (in  |
		//|        | "focal units")                   |
			"longFocalLength",
		//|--------+----------------------------------|
		//| 24     | "short" focal length of lens (in |
		//|        | "focal units")                   |
			"shortFocalLength",
		//|--------+----------------------------------|
		//| 25     | "focal units" per mm             |
			"focalUnitsPerMM",
		//|--------+----------------------------------|
		//| 26 -   | unknown                          |
		//| 27     |                                  |
			null,
			null,
		//|--------+----------------------------------|
		//| 28     | Flash      | 0: did not fire     |
		//|        | Activity   | 1: fired            |
			"flash",
		//|--------+------------+---------------------|
		//|        |            | Bits 15..0:         |
		//|        |            | 14: external E-TTL  |
		//|        |            | 13: internal flash  |
		//|        | Flash      | 11: FP sync used    |
		//| 29     | details    | 7:                  |
		//|        |            | 2nd("rear")-curtain |
		//|        |            | sync used           |
		//|        |            | 4: FP sync enabled  |
		//|        |            | other bits unknown  |
			"flashDetails",
		//|--------+----------------------------------|
		//| 30 -   | unknown                          |
		//| 31     |                                  |
			null,
			null,
		//|--------+----------------------------------|
		//|        |            | G1 seems to use     |
		//|        |            | this in preference  |
		//| 32     | Focus mode | to field 7          |
		//|        |            | 0: Single           |
		//|        |            | 1: Continuous       |
			"altFocusMode"
		};

		int count = 0;
		for(int offset = 0; offset < byteLength; offset += 2)
		{
			count++;
			int value = data.get16u(baseOffset + 2 + offset);

			if(count < names.length)
			{
				String name = names[count];

				if(name != null)
				{
					exif.put(prefix + name, "" + value);
				}
			}
		}
	}

	protected void decodeSettings2(int baseOffset)
	{
		int byteLength = data.get16u(baseOffset);

		String names[] = {
		//|----------+--------------------------------|
		//| 1 - 6    | unknown                        |
			null,
			null,
			null,
			null,
			null,
			null,
		//|----------+--------------------------------|
		//|          |           | 0: auto            |
		//|          |           | 1: Sunny           |
		//|          | White     | 2: Cloudy          |
		//| 7        | balance   | 3: Tungsten        |
		//|          |           | 4: Flourescent     |
		//|          |           | 5: Flash           |
		//|          |           | 6: Custom          |
			"whiteBalance",
		//|----------+--------------------------------|
		//| 8        | unknown                        |
			null,
		//|----------+--------------------------------|
		//| 9        | Sequence number (if in a       |
		//|          | continuous burst)              |
			"seqNumber",
		//|----------+--------------------------------|
		//| 10 - 13  | unknown                        |
			null,
			null,
			null,
			null,
		//|----------+--------------------------------|
		//|          |           | Only set in        |
		//|          |           | One-Shot mode?     |
		//|          |           | If none used, AF   |
		//|          |           | failed or manual   |
		//|          |           | focus was used     |
		//|          |           | (e.g. on a lens    |
		//|          | AF point  | with full-time     |
		//| 14       | used      | manual focus)      |
		//|          |           | Bits 15..0:        |
		//|          |           | 15-12: number of   |
		//|          |           | available focus    |
		//|          |           | points             |
		//|          |           | 2: left            |
		//|          |           | 1: center          |
		//|          |           | 0: right           |
			"afPointUsed",
		//|----------+-----------+--------------------|
		//|          |           | 0xffc0: -2 EV      |
		//|          |           | 0xffcc: -1.67 EV   |
		//|          |           | 0xffd0: -1.50 EV   |
		//|          |           | 0xffd4: -1.33 EV   |
		//|          |           | 0xffe0: -1 EV      |
		//|          |           | 0xffec: -0.67 EV   |
		//|          |           | 0xfff0: -0.50 EV   |
		//|          | Flash     | 0xfff4: -0.33 EV   |
		//| 15       | bias      | 0x0000: 0 EV       |
		//|          |           | 0x000c: 0.33 EV    |
		//|          |           | 0x0010: 0.50 EV    |
		//|          |           | 0x0014: 0.67 EV    |
		//|          |           | 0x0020: 1 EV       |
		//|          |           | 0x002c: 1.33 EV    |
		//|          |           | 0x0030: 1.50 EV    |
		//|          |           | 0x0034: 1.67 EV    |
		//|          |           | 0x0040: 2 EV       |
			"flashBias",
		//|----------+--------------------------------|
		//| 16 - 18  | unknown                        |
			null,
			null,
			null,
		//|----------+--------------------------------|
		//|          |           | Units are either   |
		//|          |           | 0.01m or 0.001m    |
		//|          |           | (both have been    |
		//|          | Subject   | observed). Still   |
		//| 19       | Distance  | investigating.     |
		//|          |           | In any case, the   |
		//|          |           | SubjectDistance    |
		//|          |           | EXIF tag is set by |
		//|          |           | Canon cameras.     |
		//+-------------------------------------------+
			"subjectDistance"
		};

		int count = 0;
		for(int offset = 0; offset < byteLength; offset += 2)
		{
			count++;
			int value = data.get16u(baseOffset + 2 + offset);

			if(count < names.length)
			{
				String name = names[count];

				if(name != null)
				{
					//System.err.println("2: " + name + "=" + value);
					exif.put(prefix + name, "" + value);
				}
			}
		}
	}

	protected void dump(byte[] raw)
	{
		int count = 0;
		while(count < raw.length)
		{
			System.err.print(count + " ");

			int pos = 0;
			while(count + pos < raw.length && pos < 16)
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
			while(count + pos < raw.length && pos < 16)
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
			count += 16;
		}
	}
}
