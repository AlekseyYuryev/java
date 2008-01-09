package org.safris.commons.format;

import org.safris.commons.format.FormatModule;

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
