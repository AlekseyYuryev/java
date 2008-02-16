package org.safris.commons.xml;

public class PackageName
{
	private final String packageName;

	public PackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof PackageName))
			return false;

		return packageName.equals(((PackageName)obj).packageName);
	}

	public int hashCode()
	{
		return packageName.hashCode();
	}

	public String toString()
	{
		return packageName;
	}
}
