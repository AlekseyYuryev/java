package org.safris.commons.lang;

public final class Numbers
{
	public static boolean isNumber(String s)
	{
		if(s == null || (s = s.trim()).length() == 0)
			return false;

		int exponent = Integer.MIN_VALUE;
		int dot = Integer.MIN_VALUE;
		boolean negative = false;
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c < '0')
			{
				if(c == '.')
				{
					if(dot != Integer.MIN_VALUE)
						return false;

					dot = i;
				}
				else if(c == '-')
				{
					if(i != exponent + 1 && i != 0)
						return false;
					else
						negative = true;
				}
				else
					return false;
			}
			else if('9' < c)
			{
				if(c == 'E')
				{
					if(1 < exponent || (negative && i == 1) || i - 1 == dot)
						return false;

					exponent = i;
				}
				else
					return false;
			}
		}

		return true;
	}

	private Numbers()
	{
	}
}
