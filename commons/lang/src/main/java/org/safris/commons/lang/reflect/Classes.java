/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.lang.reflect;

public final class Classes
{
	public static Class<?> getGreatestCommonSuperclass(Class<?> ... classes)
	{
		if(classes == null || classes.length == 0)
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
