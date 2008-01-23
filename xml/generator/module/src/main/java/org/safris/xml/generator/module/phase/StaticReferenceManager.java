package org.safris.xml.generator.module.phase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class StaticReferenceManager
{
	private static final ThreadLocal<Collection<Map>> maps = new ThreadLocal<Collection<Map>>();
	private static final ThreadLocal<Collection<Collection>> collections = new ThreadLocal<Collection<Collection>>();

	static
	{
		maps.set(new ArrayList<Map>());
		collections.set(new ArrayList<Collection>());
	}

	public static <T extends Map>T manageMap(T value)
	{
		maps.get().add(value);
		return value;
	}

	public static <T extends Collection>T manageCollection(T value)
	{
		collections.get().add(value);
		return value;
	}

	public static void clearAll()
	{
		if(maps.get().size() > 0)
		{
			synchronized(maps)
			{
				if(maps.get().size() > 0)
					for(Map map : maps.get())
						map.clear();
			}
		}

		if(collections.get().size() > 0)
		{
			synchronized(collections)
			{
				if(collections.get().size() > 0)
					for(Collection collection : collections.get())
						collection.clear();
			}
		}
	}

	private StaticReferenceManager()
	{
	}
}
