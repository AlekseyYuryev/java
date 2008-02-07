package org.safris.commons.el;

import java.util.Map;

public final class ELs
{
	public static String dereference(String string, Map<String,String> variables) throws ExpressionFormatException
	{
		final int i = string.indexOf("${");
		if(i == -1)
			return string;

		final int j = string.indexOf("}", i + 2);
		if(j == -1)
			throw new ExpressionFormatException("There is an error in your expression: " + string);

		final String token = string.substring(i + 2, j);
		final String variable = variables.get(token);
		return dereference(string.substring(0, i) + variable + string.substring(j + 1, string.length()), variables);
	}

	private ELs()
	{
	}
}
