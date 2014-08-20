package org.safris.commons.measure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AngleTest {
  @Test
  public void testDMS() {
    final Angle latitude = new Angle(3.58324, Angle.Unit.DEG);
    final Angle longitude = new Angle(4.59202, Angle.Unit.DEG);
    assertEquals("3˚34'59.664\"", latitude.toDMS());
    assertEquals("4˚35'31.272\"", longitude.toDMS());
    assertEquals(latitude, new Angle(latitude.toDMS()));
    assertEquals(longitude, new Angle(longitude.toDMS()));
  }
}