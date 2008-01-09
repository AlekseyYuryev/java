package org.safris.web.depiction;

public abstract class HTMLEncode
{
	public static String encode(String string)
	{
		string = string.replace("\"", "&quot;");
		string = string.replace("'", "&39;");
		string = string.replace("'", "&39;");
		string = string.replace("<", "&lt;");
		string = string.replace(">", "&gt;");
		return string;
	}
}
