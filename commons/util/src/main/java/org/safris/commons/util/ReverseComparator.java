/*  Copyright Safris Software 2012
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

package org.safris.commons.util;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReverseComparator<T extends Comparable> implements Comparator<T> {
  private static Map<Class<? extends Comparator<?>>,ReverseComparator<? extends Comparator>> instances = new ConcurrentHashMap<Class<? extends Comparator<?>>,ReverseComparator<? extends Comparator>>();
  private static final Object instancesMutex = new Object();

  private static ReverseComparator instance = null;
  private static volatile boolean instanceInited = false;
  private static final Object instanceMutex = new Object();

  public static <T extends Comparable<T>> ReverseComparator<T> reverse(Class<? extends Comparator<? super T>> comparatorClass) {
    ReverseComparator instance = instances.get(comparatorClass);
    if (instance != null)
      return instance;

    synchronized (instancesMutex) {
      if (instance != null)
        return instance;

      instances.put(comparatorClass, instance = new ReverseComparator(comparatorClass));
      return instance;
    }
  }

  public static <T extends Comparable<T>> ReverseComparator<T> reverse() {
    if (instanceInited)
      return instance;

    synchronized (instanceMutex) {
      if (instanceInited)
        return instance;

      instance = new ReverseComparator();
      instanceInited = true;
      return instance;
    }
  }

  private final Comparator<? super T> comparatorInstance;

  private ReverseComparator(Class<? extends Comparator<? super T>> comparatorClass) {
    try {
      this.comparatorInstance = comparatorClass.newInstance();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private ReverseComparator() {
    this.comparatorInstance = null;
  }

  /**
   * Compares its two arguments for order.  Returns a negative integer,
   * zero, or a positive integer as the first argument is less than, equal
   * to, or greater than the second.<p>
   * In the foregoing description, the notation
   * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
   * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
   * <tt>0</tt>, or <tt>1</tt> according to whether the value of
   * <i>expression</i> is negative, zero or positive.<p>
   * The implementor must ensure that <tt>sgn(compare(x, y)) ==
   * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
   * implies that <tt>compare(x, y)</tt> must throw an exception if and only
   * if <tt>compare(y, x)</tt> throws an exception.)<p>
   * The implementor must also ensure that the relation is transitive:
   * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
   * <tt>compare(x, z)&gt;0</tt>.<p>
   * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
   * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
   * <tt>z</tt>.<p>
   * It is generally the case, but <i>not</i> strictly required that
   * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
   * any comparator that violates this condition should clearly indicate
   * this fact.  The recommended language is "Note: this comparator
   * imposes orderings that are inconsistent with equals."
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return a negative integer, zero, or a positive integer as the
   * 	       first argument is less than, equal to, or greater than the
   *	       second.
   * @throws ClassCastException if the arguments' types prevent them from
   * 	       being compared by this comparator.
   */
  public int compare(T o1, T o2) {
    if (comparatorInstance != null)
      return comparatorInstance.compare(o2, o1);

    return o2.compareTo(o1);
  }
}
