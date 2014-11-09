package org.safris.commons.measure;

import org.junit.Assert;
import org.junit.Test;

public class AngleTest {
  @Test
  public void testDMS() {
    final Angle latitude = new Angle(3.58324, Angle.Unit.DEG);
    final Angle longitude = new Angle(4.59202, Angle.Unit.DEG);
    Assert.assertEquals("3˚34'59.664\"", latitude.toDMS());
    Assert.assertEquals("4˚35'31.272\"", longitude.toDMS());
    Assert.assertEquals(latitude, new Angle(latitude.toDMS()));
    Assert.assertEquals(longitude, new Angle(longitude.toDMS()));
  }
}