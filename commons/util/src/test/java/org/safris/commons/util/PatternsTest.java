package org.safris.commons.util;

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