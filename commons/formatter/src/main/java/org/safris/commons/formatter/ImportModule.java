package org.safris.commons.formatter;

public class ImportModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().indexOf("import") == 0)
		{
			token = "\n" + token;
		}

		return token;
	}
}
