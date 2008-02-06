package org.safris.commons.lang.reflect;

public final class Classes
{
	public static Class<?> getGreatestCommonSuperclass(Class<?> ... classes)
	{
		if(classes.length == 0)
			return null;

		if(classes.length == 1)
			return classes[0];

		Class<?> gcc = getGreatestCommonSuperclass(classes[0], classes[1]);
		for(int i = 2; i < classes.length && gcc != null; i++)
			gcc = getGreatestCommonSuperclass(gcc, classes[i]);

		return gcc;
	}

	private static Class<?> getGreatestCommonSuperclass(Class<?> class1, Class<?> class2)
	{
		Class<?> super1 = class1;
		do
		{
			Class<?> super2 = class2;
			do
			{
				if(super1.isAssignableFrom(super2))
					return super1;
			}
			while((super2 = super2.getSuperclass()) != null);
		}
		while((super1 = super1.getSuperclass()) != null);

		return null;
	}

	private Classes()
	{
	}
}
