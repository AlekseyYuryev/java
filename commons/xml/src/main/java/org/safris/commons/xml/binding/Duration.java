package org.safris.commons.xml.binding;

public class Duration
{
	public static Duration parseDuration(String string)
	{
		if(string == null)
			throw new NullPointerException("The duration value must not be null.");

		int len = string.length();
		int offset = 0;
		boolean isNegative;
		if(len > 0)
		{
			char c = string.charAt(0);
			if(c == '-')
			{
				isNegative = true;
				++offset;
			}
			else if(c == '+')
			{
				isNegative = false;
				++offset;
			}
			else
			{
				isNegative = false;
			}
		}
		else
		{
			throw new IllegalArgumentException("Invalid duration: Empty string");
		}

		if(len == 0  ||  string.charAt(offset) != 'P')
		{
			throw new IllegalArgumentException("Invalid duration: " + string + " (must start with P, +P, or -P)");
		}
		else
		{
			++offset;
		}

		int years = -1, months = -1, daysOfMonth = -1, hours = -1, minutes = -1, seconds = -1;
		int preDurationPoint = -1;
		boolean separatorSeen = false;
		StringBuffer digits = new StringBuffer();
		while(offset < len)
		{
			char c = string.charAt(offset);
			if(Character.isDigit(c))
			{
				digits.append(c);
			}
			else if(c == 'T')
			{
				if(separatorSeen)
				{
					throw new IllegalArgumentException("Invalid duration: " + string
					+ " (date/time separator 'T' used twice)");
				}
				else
				{
					separatorSeen = true;
				}
			}
			else
			{
				int l;
				if(digits.length() == 0)
				{
					l = 0;
				}
				else
				{
					try
					{
						l = java.lang.Integer.parseInt(digits.toString());
					}
					catch(NumberFormatException e)
					{
						throw new IllegalArgumentException("Invalid duration: "  + string
						+ " (max long value exceeded by " + digits + ")");
					}
					digits.setLength(0);
				}
				if(preDurationPoint >= 0)
				{
					if(c == 'S')
					{
						if(!separatorSeen)
						{
							throw new IllegalArgumentException("Invalid duration: " + string
							+ "(seconds specified before date/time separator 'T' seen)");
						}
						if(seconds != -1)
						{
							throw new IllegalArgumentException("Invalid duration: " + string
							+ " (seconds specified twice)");
						}
						seconds = preDurationPoint;
						preDurationPoint = -1;
					}
					else
					{
						throw new IllegalArgumentException("Invalid duration: " + string
						+ " (Duration point not allowed here: "
						+ preDurationPoint + "." + digits + c + ")");
					}
				}
				else if(l > java.lang.Integer.MAX_VALUE)
				{
					throw new IllegalArgumentException("Invalid duration: " + string
					+ " (max integer value exceeded by " + digits + ")");
				}
				else
				{
					int i = l;
					if(c == '.')
					{
						preDurationPoint = i;
					}
					else if(separatorSeen)
					{
						if(c == 'Y'  ||  c == 'D')
						{
							throw new IllegalArgumentException("Invalid duration: " + string
							+ " (years or days of month specified after date/time separator 'T' seen)");
						}
						else if(c == 'S')
						{
							if(seconds != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (seconds specified twice)");
							}
							seconds = i;
						}
						else if(c == 'M')
						{
							if(minutes != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (minutes specified twice)");
							}
							else if(seconds != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (minutes specified after seconds)");
							}
							minutes = i;
						}
						else if(c == 'H')
						{
							if(hours != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (hours specified twice)");
							}
							else if(minutes != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (hours specified after minutes)");
							}
							else if(seconds != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (seconds specified after minutes)");
							}
							hours = i;
						}
					}
					else
					{
						if(c == 'H'  ||  c == 'S')
						{
							throw new IllegalArgumentException("Invalid duration: " + string
							+ " (hours or seconds specified before date/time separator 'T' seen)");
						}
						else if(c == 'Y')
						{
							if(years != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (years specified twice)");
							}
							else if(months != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (years specified after months)");
							}
							else if(daysOfMonth != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (years specified after days of month)");
							}
							years = i;
						}
						else if(c == 'M')
						{
							if(months != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (months specified twice)");
							}
							else if(daysOfMonth != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (days of month specified after months)");
							}
							months = i;
						}
						else if(c == 'D')
						{
							if(daysOfMonth != -1)
							{
								throw new IllegalArgumentException("Invalid duration: " + string
								+ " (days of month specified twice)");
							}
							daysOfMonth = i;
						}
					}
				}
			}
			++offset;
		}

		return new Duration(isNegative, years == -1 ? 0 : years, months == -1 ? 0 : months, daysOfMonth == -1 ? 0 : daysOfMonth, hours == -1 ? 0 : hours, minutes == -1 ? 0 : minutes, seconds == -1 ? 0 : seconds);
	}

	private static final char P = 'P';
	private static final char Y = 'Y';
	private static final char M = 'M';
	private static final char D = 'D';
	private static final char T = 'T';
	private static final char H = 'H';
	private static final char S = 'S';

	private java.util.Date value = null;
	private boolean isNegative = false;

	public Duration(Duration binding)
	{
		if(binding == null)
			throw new IllegalArgumentException("value cannot be null");

		this.value = binding.value;
	}

	protected Duration()
	{
		this(false, 0, 0, 0, 0, 0, 0);
	}

	public Duration(boolean isNegative, int years)
	{
		this.isNegative = isNegative;
		this.value = new java.util.Date(years, 0, 0, 0, 0, 0);
	}

	public Duration(boolean isNegative, int years, int months)
	{
		this.isNegative = isNegative;
		this.value = new java.util.Date(years, months, 0, 0, 0, 0);
	}

	public Duration(boolean isNegative, int years, int months, int days)
	{
		this.isNegative = isNegative;
		this.value = new java.util.Date(years, months, days, 0, 0, 0);
	}

	public Duration(boolean isNegative, int years, int months, int days, int hours)
	{
		this.isNegative = isNegative;
		this.value = new java.util.Date(years, months, days, hours, 0, 0);
	}

	public Duration(boolean isNegative, int years, int months, int days, int hours, int minutes)
	{
		this.isNegative = isNegative;
		this.value = new java.util.Date(years, months, days, hours, minutes, 0);
	}

	public Duration(boolean isNegative, int years, int months, int days, int hours, int minutes, int seconds)
	{
		this.isNegative = isNegative;
		this.value = new java.util.Date(years, months, days, hours, minutes, seconds);
	}

	public String toString()
	{
		StringBuffer stringBuffer = null;
		if(isNegative)
			stringBuffer = new StringBuffer("-");
		else
			stringBuffer = new StringBuffer();

		stringBuffer.append(java.lang.String.valueOf(P));
		if(this.value.getYear() != -1)
		{
			if(this.value.getYear() != 0)
			{
				stringBuffer.append(this.value.getYear());
				stringBuffer.append(Y);
			}

			if(this.value.getMonth() != 0)
			{
				stringBuffer.append(this.value.getMonth());
				stringBuffer.append(M);
			}

			if(this.value.getDate() != 0)
			{
				stringBuffer.append(this.value.getDate());
				stringBuffer.append(D);
			}
		}

		if(this.value.getHours() != 0 || this.value.getMinutes() != 0 || this.value.getSeconds() != 0)
		{
			stringBuffer.append(T);
			if(this.value.getHours() != 0)
			{
				stringBuffer.append(this.value.getHours());
				stringBuffer.append(H);
			}

			if(this.value.getMinutes() != 0)
			{
				stringBuffer.append(this.value.getMinutes());
				stringBuffer.append(M);
			}

			if(this.value.getSeconds() != 0)
			{
				stringBuffer.append(this.value.getSeconds());
				stringBuffer.append(S);
			}
		}

		if(stringBuffer.length() == 1)
		{
			stringBuffer.append(0).append(D);
		}

		return stringBuffer.toString();
	}
}
