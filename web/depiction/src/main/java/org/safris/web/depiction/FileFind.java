/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.web.depiction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.safris.web.depiction.tag.bean.FileBean;

public abstract class FileFind
{
	private static Comparator<File> name = new Comparator<File>()
	{
		public int compare(File o1, File o2)
		{
			char[] file1 = o1.getPath().toCharArray();
			char[] file2 = o2.getPath().toCharArray();
			int length1 = file1.length;
			int length2 = file2.length;
			for(int i = 0; i < (length1 < length2 ? length1 : length2); i++)
			{
				if(file1[i] < file2[i])
					return -1;

				if(file1[i] > file2[i])
					return 1;
			}

			return 0;
		}
	};

	private static Comparator lastModified = new Comparator()
	{
		public int compare(Object o1, Object o2)
		{
			return ((File)o1).lastModified() <= ((File)o2).lastModified() ? -1 : 1;
		}
	};

	public static List<File> filter(List<File> files, String regex)
	{
		if(files == null || regex == null)
			return null;

		List<File> list = new LinkedList<File>();
		for(File file : files)
			if(file.getPath().matches(regex))
				list.add(file);

		return list;
	}

	public static List<File> ls(File path)
	{
		if(path.exists())
		{
			if(path.isDirectory())
			{
				return Arrays.asList(path.listFiles());
			}
			else
			{
				List<File> list = new ArrayList<File>(1);
				list.add(path);
				return list;
			}
		}

		return null;
	}

	public static List<FileBean> ls(File dir, String regex)
	{
		if(!dir.exists())
			return null;

		if(dir.isDirectory())
		{
			File[] files = dir.listFiles();
			List<FileBean> list = new ArrayList<FileBean>(files.length);
			for(File file : files)
				if(file.getName().matches(regex))
					list.add(new FileBean(file));

			return list;
		}
		else if(dir.getName().matches(regex))
		{
			List<FileBean> list = new ArrayList<FileBean>(1);
			list.add(new FileBean(dir));
			return list;
		}

		return null;
	}

	public static File[] sort(File[] files)
	{
//		Arrays.sort(files, lastModified);
		Arrays.sort(files, name);
		return files;
	}
}
