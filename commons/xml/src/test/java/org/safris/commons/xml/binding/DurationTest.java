/* Copyright (c) 2008 Seva Safris
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

package org.safris.commons.xml.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public final class DurationTest {
  public static void main(final String[] args) {
    new DurationTest().testDuration();
  }

  @Test
  public void testDuration() {
    try {
      Duration.parseDuration(null);
      fail("Expected a NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      Duration.parseDuration("");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("X1347Y");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("PTT347Y");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P16349286492843693864932864932864293864Y3M5DT7H10M3.3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1.Y3M5DT7H10M3.3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5D3.3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT7H10M3.3S3.3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT7H10M3S3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT7H10M10M3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT7H3S10M");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT7H7H10M3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT10M7H3S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT3S7H10M");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M5DT7H10M3Y");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3Y3M5D");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P3M1Y5D");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P3D1Y");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M3M5D");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y5D3M");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M3D3D");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M3D7H");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      Duration.parseDuration("P1Y3M3D7S");
      fail("Expected a IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    final String[] durations = new String[] {
    "P3Y", "-P1Y", "P10M", "-P8M", "P7D", "-P2D", "PT7H", "-PT9H", "PT8M", "-PT1M", "PT5S", "-PT4S", "PT5.555S", "-PT4.332S", "P3Y4M", "-P13Y34M",

    "P1Y3M5DT7H10M3.3S", "P1M", "P1D"
    };

    for (final String duration : durations)
      assertEquals(duration, Duration.parseDuration(duration).toString());
  }
}