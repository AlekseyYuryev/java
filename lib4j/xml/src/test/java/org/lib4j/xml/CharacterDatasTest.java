/* Copyright (c) 2017 lib4j
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

package org.lib4j.xml;

import org.junit.Assert;
import org.junit.Test;

public class CharacterDatasTest {
  private static final String[] unescaped = {"foo & bar", "< foo bar", "foo bar >", "'foo bar'", "\"foo bar\""};
  private static final String[] escaped = {"foo &amp; bar", "&lt; foo bar", "foo bar &gt;", "&apos;foo bar&apos;", "&quot;foo bar&quot;"};

  @Test
  public void testEscape() {
    for (int i = 0; i < escaped.length; i++)
      Assert.assertEquals(escaped[i], CharacterDatas.escape(unescaped[i]));
  }

  @Test
  public void testUnescape() {
    for (int i = 0; i < unescaped.length; i++)
      Assert.assertEquals(unescaped[i], CharacterDatas.unescape(escaped[i]));
  }
}