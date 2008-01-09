package org.safris.commons.format;

import org.safris.commons.format.CloseBracketModule;
import org.safris.commons.format.FormatModule;
import org.safris.commons.format.ImportModule;

public class ClassModule extends FormatModule
{
	String format(String formated, String token)
	{
		if(token.trim().indexOf("class ") != -1 || token.trim().indexOf("interface ") != -1)
		{
			for(int i = 0; i < getDepth(); i++)
			{
				token = "\t" + token;
			}
			
			if(getLastModule() instanceof ImportModule || getLastModule() instanceof CloseBracketModule || getLastModule() instanceof FieldModule)
			{
				token = "\n\n" + token;
			}
			else
			{
				token = "\n" + token;
			}
		}
		
		return token;
	}
}
