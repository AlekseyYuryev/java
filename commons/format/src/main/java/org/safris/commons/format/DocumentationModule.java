package org.safris.commons.format;

import org.safris.commons.format.DocumentationModule;

public class DocumentationModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().indexOf("//") == 0 || token.trim().indexOf("/*") == 0 || (token.trim().indexOf("*") == 0 && getLastModule() instanceof DocumentationModule))
		{
			for(int i = 0; i < getDepth(); i++)
			{
				token = "\t" + token;
			}
			
			if(getLastModule() instanceof CloseBracketModule)
				token = "\n" + token;
			
			if(token.trim().indexOf("/") == 0)
				token = "\n" + token;
			
			return token + "\n";
		}
		
		return token;
	}
}
