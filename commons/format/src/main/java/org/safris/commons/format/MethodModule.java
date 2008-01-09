package org.safris.commons.format;

import org.safris.commons.format.CloseBracketModule;
import org.safris.commons.format.FormatModule;

public class MethodModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().lastIndexOf(";") != token.trim().length() - 1)
		{
			if(token.trim().indexOf("else") == -1 && token.trim().indexOf("else ") == -1 && ((token.trim().indexOf("static") == 0 && token.trim().length() == 6) || (token.trim().indexOf(" ") != -1 && token.trim().indexOf(" ") < token.trim().indexOf("(") - 1 && (token.trim().lastIndexOf(")") == token.length() - 1 || token.trim().lastIndexOf(")") < token.trim().lastIndexOf("throws")))))
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
