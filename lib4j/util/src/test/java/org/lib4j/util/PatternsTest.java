/* Copyright (c) 2016 lib4j
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.lib4j.util;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class PatternsTest {
  @Test
  public void testGetGroupNames() {
    Assert.assertArrayEquals(null, Patterns.getGroupNames(Pattern.compile("test")));
    Assert.assertArrayEquals(new String[] {"one"}, Patterns.getGroupNames(Pattern.compile("(?<one>[a-z])")));
    Assert.assertArrayEquals(new String[] {"one", "two"}, Patterns.getGroupNames(Pattern.compile("(?<one>[a-z])(?<two>[a-z])")));
    Assert.assertArrayEquals(new String[] {"one", "two", "three"}, Patterns.getGroupNames(Pattern.compile("(?<one>[a-z])(?<two>[a-z](?<three>[a-z]))")));
  }
}