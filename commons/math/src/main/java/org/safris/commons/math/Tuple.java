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

package org.safris.commons.math;

import java.util.Comparator;

public class Tuple<X extends Number,Y extends Number> {
  public static final Comparator<Tuple> comparatorX = new Comparator<Tuple>() {
    public int compare(Tuple o1, Tuple o2) {
      return Double.compare(o1.x.doubleValue(), o2.x.doubleValue());
    }
  };

  public static final Comparator<Tuple> comparatorY = new Comparator<Tuple>() {
    public int compare(Tuple o1, Tuple o2) {
      return Double.compare(o1.y.doubleValue(), o2.y.doubleValue());
    }
  };

  public final X x;
  public final Y y;

  public Tuple(X x, Y y) {
    this.x = x;
    this.y = y;
  }
}
