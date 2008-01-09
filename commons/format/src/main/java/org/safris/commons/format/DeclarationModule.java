package org.safris.commons.format;

import org.safris.commons.format.CloseBracketModule;
import org.safris.commons.format.FormatModule;
import org.safris.commons.format.StatementModule;

public class DeclarationModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().lastIndexOf(";") == token.trim().length() - 1)
		{
			if(token.indexOf("package") == -1 && token.indexOf("import") == -1 && token.indexOf("public") == -1 && token.indexOf("protected") == -1 && token.indexOf("private") == -1)
			{
				for(int i = 0; i < getDepth(); i++)
				{
					token = "\t" + token;
				}
				
				if(getLastModule() instanceof CloseBracketModule)
				{
					token = "\n\n" + token;
				}
				else if(getLastModule() instanceof StatementModule)
				{
					token = "\n\t" + token + "\n";
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
