package org.safris.commons.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public final class Systems
{
	public static void setenv(String name, String value)
	{
		Map map;
		try
		{
			final Field env = Class.forName("java.lang.ProcessEnvironment").getDeclaredField("theEnvironment");
			env.setAccessible(true);
			map = (Map)env.get(null);
		}
		catch(IllegalAccessException e)
		{
			throw new SecurityException(e);
		}
		catch(IllegalArgumentException e)
		{
			return;
		}
		catch(ClassNotFoundException e)
		{
			return;
		}
		catch(NoSuchFieldException e)
		{
			return;
		}

		final Object variableDate = createVariable(name);
		if(variableDate == null)
			return;

		final Object valueDate = createValue(value);
		if(valueDate == null)
			return;

		map.put(variableDate, valueDate);
	}

	private static Object createVariable(String name)
	{
		try
		{
			final Class clazz = Class.forName("java.lang.ProcessEnvironment$Variable");
			final Constructor constructor = clazz.getDeclaredConstructor(String.class, byte[].class);
			constructor.setAccessible(true);
			return constructor.newInstance(name, name.getBytes());
		}
		catch(Exception e)
		{
			return null;
		}
	}

	private static Object createValue(String value)
	{
		try
		{
			final Class clazz = Class.forName("java.lang.ProcessEnvironment$Value");
			final Constructor constructor = clazz.getDeclaredConstructor(String.class, byte[].class);
			constructor.setAccessible(true);
			return constructor.newInstance(value, value.getBytes());
		}
		catch(Exception e)
		{
			return null;
		}
	}

	private Systems()
	{
	}
}
