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