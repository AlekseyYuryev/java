/* Nikon885Decoder.java */

package com.nwalsh.jpegrdf;

import java.util.Map;
import org.w3c.tools.jpeg.ExifData;
import org.w3c.tools.jpeg.TagDecoder;

/**
 * A decoder for the Nikon CoolPix 885 MakerNote tag.
 *
 * <p>Based on <code>Nikon5700Decoder</code>, with minor adjustments
 * in offset values.  It seems there are at least <em>three</em>
 * MakerNote formats in the Nikon range alone.  This class decodes
 * basically the format described by TsuruZoh Tachibanaya[1] for the
 * E990.  The E5700 evidently has a hybrid format somewhere between
 * the two&emdash;it uses the same tag numbers as the E885/E990, but
 * starts with the <code>Nikon</code> string as do the
 * E700/E800/E900/E900S/E910/E950 cameras.  Extra tag numbers and
 * their names were obtained from Martin Krzywinski's[2]
 * <code>exif</code> Perl script.</p>
 *
 * <p>[1]
 * http://www.ba.wakwak.com/~tsuruzoh/Computer/Digicams/exif-e.html#APP2<br>
 * [2] http://www.worldatwar.org/photos/exif/ </p>
 *
 * @author Norman Walsh
 * @author Paul Hoadley
 * @version $Revision: 1.1 $
 */
public class Nikon885Decoder implements TagDecoder
{
    private static final String namespace = "http://nwalsh.com/rdf/exif-nikon885#";
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
	    && format == ExifData.FMT_UNDEFINED)
		{
			decodeNikon(offset, length);
		}

		// else silently do nothing
    }

    protected void decodeNikon(int offsetBase, int length)
	{
		//System.err.println("Decode Nikon MakerNote: " + offsetBase + ", " + length);

		// Data starts at the first byte with the 885
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

			//System.err.println("EXIF: entry: 0x" + Integer.toHexString(tag)
			//	       + " " + format
			//	       + " " + components
			//	       + " (" + byteCount + ")");

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
				valueOffset = offsetVal + 6;
			}

			//System.err.println("\tvalue Offset: " + valueOffset);

			//if (format == ExifData.FMT_STRING || format == ExifData.FMT_UNDEFINED) {
			//String value = "";
			//if (format == ExifData.FMT_STRING) {
			//    value = data.getString(valueOffset, byteCount);
			//} else {
			//    value = data.getUndefined(valueOffset, byteCount);
			//}
			//System.err.println("\t" + value);
			//}

			switch(tag)
			{
				case 0x0001:
					// Unknown
					break;
				case 0x0002:
					//FIXME: is ushort correct here?
					if(format == ExifData.FMT_USHORT)
					{
						int value = data.get16u(valueOffset);
						int value2 = data.get16u(valueOffset + 2);
						exif.put(prefix + "isoSetting", "" + value2);
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
