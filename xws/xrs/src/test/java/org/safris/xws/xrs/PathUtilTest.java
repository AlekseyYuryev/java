package org.safris.xws.xrs;

import java.lang.annotation.Annotation;

import javax.ws.rs.Path;

import org.junit.Assert;
import org.junit.Test;
import org.safris.xws.xrs.PathPattern;

public class PathUtilTest {
  protected static class TestPath implements Path {
    private final String value;

    public TestPath(final String value) {
      this.value = value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return Path.class;
    }

    @Override
    public String value() {
      return value;
    }
  }

  private static String pathToPattern(final String classPath, final String methodPath) {
    return new PathPattern(classPath == null ? null : new TestPath(classPath), methodPath == null ? null : new TestPath(methodPath)).getPattern().toString();
  }

  @Test
  public void test() {
    try {
      pathToPattern(null, "bar");
      Assert.fail("Expected IllegalArgumentException(\"classPath == null\")");
    }
    catch (final IllegalArgumentException e) {
      if (!"classPath == null".equals(e.getMessage()))
        throw e;
    }

    Assert.assertEquals("/foo/bar", pathToPattern("/foo", "/bar"));
    Assert.assertEquals("/foo/bar", pathToPattern("foo", "bar"));
    Assert.assertEquals("/foo/bar", pathToPattern("/foo", "bar"));
    Assert.assertEquals("/foo/bar", pathToPattern("foo", "/bar"));

    Assert.assertEquals("/foo", pathToPattern("/foo", null));
    Assert.assertEquals("/foo", pathToPattern("/foo", null));
    Assert.assertEquals("/foo/(?<id>[^\\/]+)", pathToPattern("/foo", "{id}"));
    Assert.assertEquals("/foo/bar/(?<id>[^\\/]+)", pathToPattern("/foo", "bar/{id}"));

    Assert.assertEquals("/foo/bar/(?<id>[^\\/]+)", pathToPattern("/foo", "bar/{id:([^\\/]+)}"));
    Assert.assertEquals("/foo/bar/(?<id>[^\\/]+)/(?<name>[^\\/]+)", pathToPattern("/foo", "bar/{id:([^\\/]+)}/{name}"));
    Assert.assertEquals("/foo/bar/(?<id>[^\\/]+)/blank/(?<name>[^\\/]+)", pathToPattern("/foo", "bar/{id:([^\\/]+)}/blank/{name}"));
  }
}