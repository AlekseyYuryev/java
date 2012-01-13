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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.safris.web.depiction.Comment;

public class Comment
{
  private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
  private static final Comparator dateComparator = new DateComparator();
  private final Date date;
  private final String submitter;
  private final String text;
  
  public static String encode(Comment comment)
  {
    return comment.getDate().getTime() + " " + comment.getSubmitter() + ": " + comment.getText();
  }
  
  public static Comment decode(String string)
  {
    int index3 = string.indexOf(' ');
    Date date = new Date(new Long(string.substring(0, index3)));
    int index4 = string.indexOf(": ");
    String submitter = string.substring(index3 + 1, index4);
    String text = string.substring(index4 + 2, string.length());
    return new Comment(date, submitter, text);
  }
  
  public Comment(Date date, String submitter, String text)
  {
    this.date = date;
    this.submitter = submitter;
    this.text = text;
  }
  
  public Date getDate()
  {
    return date;
  }
  
  public String getSubmitter()
  {
    return submitter;
  }
  
  public String getText()
  {
    return text;
  }
  
  public static List<Comment> sortByDate(List<Comment> list)
  {
    Comment[] comments = list.toArray(new Comment[list.size()]);
    Arrays.sort(comments, dateComparator);
    return Arrays.asList(comments);
  }
  
  public String toString()
  {
    return dateFormat.format(date) + " " + submitter + ": " + text;
  }
  
  static class DateComparator implements Comparator<Comment>
  {
    public int compare(Comment o1, Comment o2)
    {
      if(o1.getDate().before(o2.getDate()))
        return -1;
      
      if(o1.getDate().after(o2.getDate()))
        return 1;
      
      return 0;
    }
  }
}
