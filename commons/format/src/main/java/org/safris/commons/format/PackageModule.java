package org.safris.commons.format;

import org.safris.commons.format.FormatModule;

public class PackageModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().indexOf("package") == 0)
		{
			token = token + "\n";
		}
		return token;
	}
}
