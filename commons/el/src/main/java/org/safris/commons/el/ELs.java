package org.safris.commons.el;

import java.util.Map;

public final class ELs
{
	public static String dereference(String string, Map<String,String> variables) throws ExpressionFormatException
	{
		if(string == null || string.length() == 0 || variables == null)
			return string;

		final StringBuffer buffer = new StringBuffer();
		int i = -1;
		int j = -1;
		while(true)
		{
			i = string.indexOf("${", i);
			if(i == -1)
				break;

			j = string.indexOf("}", i + 2);
			if(j == -1)
				throw new ExpressionFormatException("There is an error in your expression: " + string);

			final String token = string.substring(i + 2, j);
			final String variable = variables.get(token);
			buffer.append(string.substring(0, i)).append(variable);
			string = string.substring(j + 1);
		}

		buffer.append(string);

		return buffer.toString();
	}

	private ELs()
	{
	}
}
