package org.safris.xws.xrs;

import java.util.Collections;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

public class MetiaTypeUtilTest {
  @Test
  public void testParse() {
    Assert.assertEquals(new MediaType("application", "json"), MediaTypes.parse("application/json"));
    Assert.assertEquals(new MediaType("application", "json", Collections.singletonMap("charset", "utf8")), MediaTypes.parse("application/json; charset=utf8"));
  }
}