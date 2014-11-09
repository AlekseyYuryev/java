package org.safris.commons.measure;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {
  @Test
  public void testLocation() throws Exception {
    final Angle lat = new Angle(38.898556, Angle.Unit.DEG);
    final Location l1 = new Location(lat, new Angle(-77.037852, Angle.Unit.DEG));
    final Location l2 = new Location(lat, new Angle(-77.043934, Angle.Unit.DEG));
    final Distance expected = new Distance(0.5269164586229639, Distance.Unit.KM);
    Assert.assertEquals(expected, l1.distance(l2));

    final Location location = expected.locate(new Location(lat, new Angle(0, Angle.Unit.DEG)), new Angle(90, Angle.Unit.DEG));
    final double dlng = 77.037852 - 77.043934;
    Assert.assertEquals(dlng, location.longitude.value(Angle.Unit.DEG), 0.0000000001);
  }
}