/* Copyright (c) 2016 Seva Safris
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

package org.safris.maven.plugin.version;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class VersionTest extends LoggableTest {
  @Test
  public void testVersion() throws MojoExecutionException {
    Assert.assertEquals(new Version("0.1.1-SNAPSHOT"), new Version("0.1.0-SNAPSHOT").increment(Version.Part.PATCH));
    Assert.assertEquals(new Version("0.2.0-SNAPSHOT"), new Version("0.1.0-SNAPSHOT").increment(Version.Part.MINOR));
    Assert.assertEquals(new Version("1.1.0-SNAPSHOT"), new Version("0.1.0-SNAPSHOT").increment(Version.Part.MAJOR));

    Assert.assertEquals(0, new Version("1.1.0-SNAPSHOT").compareTo(new Version("1.1.0-SNAPSHOT")));
    Assert.assertEquals(-1, new Version("1.1.0-SNAPSHOT").compareTo(new Version("1.1.1-SNAPSHOT")));
    Assert.assertEquals(-1, new Version("1.1.0-SNAPSHOT").compareTo(new Version("1.2-SNAPSHOT")));
    Assert.assertEquals(0, new Version("1.1.1-SNAPSHOT").compareTo(new Version("1.1.1-SNAPSHOT")));

    Assert.assertEquals(1, new Version("RELEASE").compareTo(new Version("1.1.0-SNAPSHOT")));
  }
}