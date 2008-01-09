package org.safris.commons.format;

import org.safris.commons.format.FormatModule;

public class OpenBracketModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().lastIndexOf(";") != token.trim().length() - 1)
		{
			if(token.trim().indexOf("{") == 0)
			{
				for(int i = 0; i < getDepth(); i++)
				{
					token = "\t" + token;
				}
				increaseDepth();
				token = "\n" + token;
			}
		}
		
		return token;
	}
}
