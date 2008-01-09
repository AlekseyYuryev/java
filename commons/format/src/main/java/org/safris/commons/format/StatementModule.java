package org.safris.commons.format;

import org.safris.commons.format.CloseBracketModule;
import org.safris.commons.format.FormatModule;

public class StatementModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().lastIndexOf(";") == token.trim().length() - 1 || token.trim().lastIndexOf(")") == token.trim().length() - 1 || (token.trim().indexOf("else") == 0 && token.trim().indexOf("(") == -1))
		{
			if(token.trim().indexOf("else") == 0)
			{
				for(int i = 0; i < getDepth(); i++)
				{
					token = "\t" + token;
				}
				
				token = "\n" + token;
			}
			else if(token.trim().lastIndexOf("try") == token.trim().length() - 1 || token.trim().indexOf("catch") == 0 || token.trim().lastIndexOf("finally") == token.trim().length() - 1 || token.trim().lastIndexOf("do") == token.trim().length() - 1 || token.trim().indexOf("if") == 0 || token.trim().indexOf("for") == 0 || token.trim().indexOf("while") == 0 || token.trim().lastIndexOf(";") == token.trim().length() - 1)
			{
				for(int i = 0; i < getDepth(); i++)
				{
					token = "\t" + token;
				}
				
				if(getLastModule() instanceof CloseBracketModule || getLastModule() instanceof FieldModule)
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
