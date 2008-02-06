package org.safris.commons.formatter;

public class FieldModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().lastIndexOf(";") == token.trim().length() - 1 || token.trim().indexOf("=") != -1)
		{
			if(token.trim().indexOf("public") == 0 || token.trim().indexOf("protected") == 0 || token.trim().indexOf("private") == 0)
			{
				for(int i = 0; i < getDepth(); i++)
				{
					token = "\t" + token;
				}

				if(getLastModule() instanceof CloseBracketModule)
				{
					token = "\n\n" + token;
				}
				else
				{
					token = "\n" + token;
				}
			}
		}
		return token;
	}
}
