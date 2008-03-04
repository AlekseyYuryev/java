package org.safris.commons.xml.binding;

public class Year
{
	public static Year parseYear(String string)
	{
		if(string == null || string.length() == 0)
			return null;

		return new Year(Integer.parseInt(string));
	}

	private final int year;

	public Year(int Year)
	{
		this.year = Year;
	}
}
