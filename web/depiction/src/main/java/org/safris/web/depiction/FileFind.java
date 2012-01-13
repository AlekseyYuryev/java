/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
