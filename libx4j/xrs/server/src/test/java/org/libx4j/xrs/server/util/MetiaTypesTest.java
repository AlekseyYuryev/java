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

package org.libx4j.xrs.server.util;

import java.util.Collections;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;
import org.libx4j.xrs.server.util.MediaTypes;

public class MetiaTypesTest {
  @Test
  public void testParse() {
    Assert.assertEquals(new MediaType("application", "json"), MediaTypes.parse("application/json"));
    Assert.assertEquals(new MediaType("application", "json", Collections.singletonMap("charset", "utf8")), MediaTypes.parse("application/json; charset=utf8"));
  }
}