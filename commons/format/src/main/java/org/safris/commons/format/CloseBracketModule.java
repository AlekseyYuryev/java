package org.safris.commons.format;

import org.safris.commons.format.FormatModule;

public class CloseBracketModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().lastIndexOf(";") != token.trim().length() - 1 || "};".equals(token.trim()))
		{
			if(token.trim().indexOf("}") == 0)
			{
				decreaseDepth();
				for(int i = 0; i < getDepth(); i++)
					token = "\t" + token;
				
				if(!formated.endsWith("\n"))
					token = "\n" + token;
			}
		}
		return token;
	}
}
