/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.math;

public final class Functions {
  /**
   * Find the index of the sorted array, whose value most closely matches
   * the value passed in.
   *
   * @param sorted The sorted array.
   * @param value The value to match.
   * @param start The starting index of the sorted array to search from.
   * @param end The ending index of the sorted array to search to.
   *
   * @return The closest index of the sorted array matching the desired value.
   */
  public static int binaryClosestSearch(final short[] sorted, final short value, final int start, final int end) {
    if (sorted == null)
      throw new NullPointerException("sorted == null");

    if (end < 0)
      throw new IllegalArgumentException("end < 0: " + end);

    if (end == 0)
      return 0;

    int first = 0;
    int upto = end;
    int mid = -1;
    while (first < upto) {
      mid = (first + upto) / 2;    // Compute mid point.
      if (value < sorted[mid])
        upto = mid;        // repeat search in bottom half.
      else if (value > sorted[mid])
        first = mid + 1;      // Repeat search in top half.
      else
        return mid;
    }

    return first == end - 1 && value > sorted[first] ? first + 1 : (first + upto) / 2;
  }

  /**
   * Find the index of the sorted array, whose value most closely matches
   * the value passed in.
   *
   * @param sorted The sorted array.
   * @param value The value to match.
   * @param start The starting index of the sorted array to search from.
   * @param end The ending index of the sorted array to search to.
   *
   * @return The closest index of the sorted array matching the desired value.
   */
  public static int binaryClosestSearch(final int[] sorted, final int value, final int start, final int end) {
    if (sorted == null)
      throw new NullPointerException("sorted == null");

    if (end < 0)
      throw new IllegalArgumentException("end < 0: " + end);

    if (end == 0)
      return 0;

    int first = 0;
    int upto = end;
    int mid = -1;
    while (first < upto) {
      mid = (first + upto) / 2;    // Compute mid point.
      if (value < sorted[mid])
        upto = mid;        // repeat search in bottom half.
      else if (value > sorted[mid])
        first = mid + 1;      // Repeat search in top half.
      else
        return mid;
    }

    return first == end - 1 && value > sorted[first] ? first + 1 : (first + upto) / 2;
  }

  /**
   * Find the index of the sorted array, whose value most closely matches
   * the value passed in.
   *
   * @param sorted The sorted array.
   * @param value The value to match.
   * @param start The starting index of the sorted array to search from.
   * @param end The ending index of the sorted array to search to.
   *
   * @return The closest index of the sorted array matching the desired value.
   */
  public static int binaryClosestSearch(final long[] sorted, final long value, final int start, final int end) {
    if (sorted == null)
      throw new NullPointerException("sorted == null");

    if (end < 0)
      throw new IllegalArgumentException("end < 0: " + end);

    if (end == 0)
      return 0;

    int first = 0;
    int upto = end;
    int mid = -1;
    while (first < upto) {
      mid = (first + upto) / 2;    // Compute mid point.
      if (value < sorted[mid])
        upto = mid;        // repeat search in bottom half.
      else if (value > sorted[mid])
        first = mid + 1;      // Repeat search in top half.
      else
        return mid;
    }

    return first == end - 1 && value > sorted[first] ? first + 1 : (first + upto) / 2;
  }

  /**
   * Find the index of the sorted array, whose value most closely matches
   * the value passed in.
   *
   * @param sorted The sorted array.
   * @param value The value to match.
   * @param start The starting index of the sorted array to search from.
   * @param end The ending index of the sorted array to search to.
   *
   * @return The closest index of the sorted array matching the desired value.
   */
  public static int binaryClosestSearch(final float[] sorted, final float value, final int start, final int end) {
    if (sorted == null)
      throw new NullPointerException("sorted == null");

    if (end < 0)
      throw new IllegalArgumentException("end < 0: " + end);

    if (end == 0)
      return 0;

    int first = 0;
    int upto = end;
    int mid = -1;
    while (first < upto) {
      mid = (first + upto) / 2;    // Compute mid point.
      if (value < sorted[mid])
        upto = mid;        // repeat search in bottom half.
      else if (value > sorted[mid])
        first = mid + 1;      // Repeat search in top half.
      else
        return mid;
    }

    return first == end - 1 && value > sorted[first] ? first + 1 : (first + upto) / 2;
  }

  /**
   * Find the index of the sorted array, whose value most closely matches
   * the value passed in.
   *
   * @param sorted The sorted array.
   * @param value The value to match.
   * @param start The starting index of the sorted array to search from.
   * @param end The ending index of the sorted array to search to.
   *
   * @return The closest index of the sorted array matching the desired value.
   */
  public static int binaryClosestSearch(final double[] sorted, final double value, final int start, final int end) {
    if (sorted == null)
      throw new NullPointerException("sorted == null");

    if (end < 0)
      throw new IllegalArgumentException("end < 0: " + end);

    if (end == 0)
      return 0;

    int first = 0;
    int upto = end;
    int mid = -1;
    while (first < upto) {
      mid = (first + upto) / 2;    // Compute mid point.
      if (value < sorted[mid])
        upto = mid;                // repeat search in bottom half.
      else if (value > sorted[mid])
        first = mid + 1;           // Repeat search in top half.
      else
        return mid;
    }

    return first == end - 1 && value > sorted[first] ? first + 1 : (first + upto) / 2;
  }

  /**
   * Calculate the log given both the base and value.
   *
   * @param base The base.
   * @param value The value.
   *
   * @return The log.
   */
  public static double log(final int base, final int value) {
    return Math.log(value) / Math.log(base);
  }

  /**
   * Calculate the root mean suqre of an array of values.
   *
   * @param values The values.
   *
   * @return The rms.
   */
  public static double rms(final short ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double rms = 0.0D;
    for (int i = 0; i < values.length; i++)
      rms += values[i] * values[i];

    return Math.sqrt(rms / (double)values.length);
  }

  /**
   * Calculate the root mean suqre of an array of values.
   *
   * @param values The values.
   *
   * @return The rms.
   */
  public static double rms(final int ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double rms = 0.0D;
    for (int i = 0; i < values.length; i++)
      rms += values[i] * values[i];

    return Math.sqrt(rms / (double)values.length);
  }

  /**
   * Calculate the root mean suqre of an array of values.
   *
   * @param values The values.
   *
   * @return The rms.
   */
  public static double rms(final long ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double rms = 0.0D;
    for (int i = 0; i < values.length; i++)
      rms += values[i] * values[i];

    return Math.sqrt(rms / (double)values.length);
  }

  /**
   * Calculate the root mean suqre of an array of values.
   *
   * @param values The values.
   *
   * @return The rms.
   */
  public static double rms(final float ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double rms = 0.0D;
    for (int i = 0; i < values.length; i++)
      rms += values[i] * values[i];

    return Math.sqrt(rms / (double)values.length);
  }

  /**
   * Calculate the root mean suqre of an array of values.
   *
   * @param values The values.
   *
   * @return The rms.
   */
  public static double rms(final double ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double rms = 0.0D;
    for (int i = 0; i < values.length; i++)
      rms += values[i] * values[i];

    return Math.sqrt(rms / (double)values.length);
  }

  /**
   * Calculate the minimum value of an array of values.
   *
   * @param values The values.
   *
   * @return The minimum value.
   */
  public static short min(final short ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    short min = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] < min)
        min = values[i];

    return min;
  }

  /**
   * Calculate the minimum value of an array of values.
   *
   * @param values The values.
   *
   * @return The minimum value.
   */
  public static int min(final int ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    int min = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] < min)
        min = values[i];

    return min;
  }

  /**
   * Calculate the minimum value of an array of values.
   *
   * @param values The values.
   *
   * @return The minimum value.
   */
  public static long min(final long ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    long min = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] < min)
        min = values[i];

    return min;
  }

  /**
   * Calculate the minimum value of an array of values.
   *
   * @param values The values.
   *
   * @return The minimum value.
   */
  public static float min(final float ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    float min = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] < min)
        min = values[i];

    return min;
  }

  /**
   * Calculate the minimum value of an array of values.
   *
   * @param values The values.
   *
   * @return The minimum value.
   */
  public static double min(final double ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double min = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] < min)
        min = values[i];

    return min;
  }

  /**
   * Calculate the maximum value of an array of values.
   *
   * @param values The values.
   *
   * @return The maximum value.
   */
  public static short max(final short ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    short max = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] > max)
        max = values[i];

    return max;
  }

  /**
   * Calculate the maximum value of an array of values.
   *
   * @param values The values.
   *
   * @return The maximum value.
   */
  public static int max(final int ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    int max = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] > max)
        max = values[i];

    return max;
  }

  /**
   * Calculate the maximum value of an array of values.
   *
   * @param values The values.
   *
   * @return The maximum value.
   */
  public static long max(final long ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    long max = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] > max)
        max = values[i];

    return max;
  }

  /**
   * Calculate the maximum value of an array of values.
   *
   * @param values The values.
   *
   * @return The maximum value.
   */
  public static float max(final float ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    float max = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] > max)
        max = values[i];

    return max;
  }

  /**
   * Calculate the maximum value of an array of values.
   *
   * @param values The values.
   *
   * @return The maximum value.
   */
  public static double max(final double ... values) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (values.length == 0)
      throw new IllegalArgumentException("values.length == 0");

    double max = values[0];
    for (int i = 1; i < values.length; i++)
      if (values[i] > max)
        max = values[i];

    return max;
  }

  /**
   * Test if a value is within the threshold of a min and a max. If the value
   * is less than min, this method will return min. If the value is greater
   * than max, this method will return max.
   *
   * @param value The value to test.
   * @param min The minimum accepted value.
   * @param max The maximum accepted value.
   *
   * @return The value complying to the threshold.
   */
  public static short threshold(final short value, final short min, final short max) {
    return value < min ? min : (max < value ? max : value);
  }

  /**
   * Test if a value is within the threshold of a min and a max. If the value
   * is less than min, this method will return min. If the value is greater
   * than max, this method will return max.
   *
   * @param value The value to test.
   * @param min The minimum accepted value.
   * @param max The maximum accepted value.
   *
   * @return The value complying to the threshold.
   */
  public static int threshold(final int value, final int min, final int max) {
    return value < min ? min : (max < value ? max : value);
  }

  /**
   * Test if a value is within the threshold of a min and a max. If the value
   * is less than min, this method will return min. If the value is greater
   * than max, this method will return max.
   *
   * @param value The value to test.
   * @param min The minimum accepted value.
   * @param max The maximum accepted value.
   *
   * @return The value complying to the threshold.
   */
  public static long threshold(final long value, final long min, final long max) {
    return value < min ? min : (max < value ? max : value);
  }

  /**
   * Test if a value is within the threshold of a min and a max. If the value
   * is less than min, this method will return min. If the value is greater
   * than max, this method will return max.
   *
   * @param value The value to test.
   * @param min The minimum accepted value.
   * @param max The maximum accepted value.
   *
   * @return The value complying to the threshold.
   */
  public static float threshold(final float value, final float min, final float max) {
    return value < min ? min : (max < value ? max : value);
  }

  /**
   * Test if a value is within the threshold of a min and a max. If the value
   * is less than min, this method will return min. If the value is greater
   * than max, this method will return max.
   *
   * @param value The value to test.
   * @param min The minimum accepted value.
   * @param max The maximum accepted value.
   *
   * @return The value complying to the threshold.
   */
  public static double threshold(final double value, final double min, final double max) {
    return value < min ? min : (max < value ? max : value);
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param value The vararg array of values.
   *
   * @return The average value.
   */
  public static double avg(final short ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    return Functions.sum(value) / (double)value.length;
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param value The vararg array of values.
   *
   * @return The average value.
   */
  public static double avg(final int ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    return Functions.sum(value) / (double)value.length;
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param value The vararg array of values.
   *
   * @return The average value.
   */
  public static double avg(final long ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    return Functions.sum(value) / (double)value.length;
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param value The vararg array of values.
   *
   * @return The average value.
   */
  public static double avg(final float ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      return Double.NaN;

    return Functions.sum(value) / (double)value.length;
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param value The vararg array of values.
   *
   * @return The average value.
   */
  public static double avg(final double ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new NullPointerException("value == null");

    return Functions.sum(value) / (double)value.length;
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static double avg(final short[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0 || start == end)
      return Double.NaN;

    return Functions.sum(values, start, end) / (double)(end - start);
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static double avg(final int[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0 || start == end)
      return Double.NaN;

    return Functions.sum(values, start, end) / (double)(end - start);
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static double avg(final long[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0 || start == end)
      return Double.NaN;

    return Functions.sum(values, start, end) / (double)(end - start);
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static double avg(final float[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0 || start == end)
      return Double.NaN;

    return Functions.sum(values, start, end) / (double)(end - start);
  }

  /**
   * Compute the average of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static double avg(final double[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0 || start == end)
      return Double.NaN;

    return Functions.sum(values, start, end) / (double)(end - start);
  }

  /**
   * Compute the sum of values in the argument array.
   *
   * @param  value The vararg array of values.
   *
   * @return The sum of values in the argument array.
   */
  public static int sum(final short ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    int sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array.
   *
   * @param  value The vararg array of values.
   *
   * @return The sum of values in the argument array.
   */
  public static long sum(final int ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    long sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array.
   *
   * @param  value The vararg array of values.
   *
   * @return The sum of values in the argument array.
   */
  public static long sum(final long ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    long sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array.
   *
   * @param  value The vararg array of values.
   *
   * @return The sum of values in the argument array.
   */
  public static float sum(final float ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    float sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array.
   *
   * @param  value The vararg array of values.
   *
   * @return The sum of values in the argument array.
   */
  public static double sum(final double ... value) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    double sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i];

    return sum;
  }

  /**
   * Compute the sum of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static int sum(final short[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    if (start == end)
      throw new IllegalArgumentException("start == end");

    int sum = 0;
    for (int i = start; i < end; i++)
      sum += values[i];

    return sum;
  }

  /**
   * Compute the sum of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static int sum(final int[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    if (start == end)
      throw new IllegalArgumentException("start == end");

    int sum = 0;
    for (int i = start; i < end; i++)
      sum += values[i];

    return sum;
  }

  /**
   * Compute the sum of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static long sum(final long[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    if (start == end)
      throw new IllegalArgumentException("start == end");

    long sum = 0;
    for (int i = start; i < end; i++)
      sum += values[i];

    return sum;
  }

  /**
   * Compute the sum of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static float sum(final float[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    if (start == end)
      throw new IllegalArgumentException("start == end");

    float sum = 0;
    for (int i = start; i < end; i++)
      sum += values[i];

    return sum;
  }

  /**
   * Compute the sum of the members of the argument array.
   *
   * @param values The array of values.
   * @param start Start index from which to aggregate.
   * @param end End index to which to aggregate.
   *
   * @return The average value.
   */
  public static double sum(final double[] values, final int start, final int end) {
    if (values == null)
      throw new NullPointerException("values == null");

    if (start < 0)
      throw new IllegalArgumentException("start < 0");

    if (values.length <= end)
      throw new IllegalArgumentException("values.length <= end");

    if (end < start)
      throw new IllegalArgumentException("end < start");

    if (values.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    if (start == end)
      throw new IllegalArgumentException("start == end");

    int sum = 0;
    for (int i = start; i < end; i++)
      sum += values[i];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array. This method
   * accepts a 2 dimensional array, allowing one to iterate through
   * a specific dimension, 0 or 1.
   *
   * @param value The 2 dimensional array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The sum of values in the argument array.
   */
  public static int sum(final short[][] value, final int dimension) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    int sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i][dimension];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array. This method
   * accepts a 2 dimensional array, allowing one to iterate through
   * a specific dimension, 0 or 1.
   *
   * @param value The 2 dimensional array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The sum of values in the argument array.
   */
  public static int sum(final int[][] value, final int dimension) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    int sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i][dimension];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array. This method
   * accepts a 2 dimensional array, allowing one to iterate through
   * a specific dimension, 0 or 1.
   *
   * @param value The 2 dimensional array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The sum of values in the argument array.
   */
  public static long sum(final long[][] value, final int dimension) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    long sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i][dimension];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array. This method
   * accepts a 2 dimensional array, allowing one to iterate through
   * a specific dimension, 0 or 1.
   *
   * @param value The 2 dimensional array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The sum of values in the argument array.
   */
  public static float sum(final float[][] value, final int dimension) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    float sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i][dimension];

    return sum;
  }

  /**
   * Compute the sum of values in the argument array. This method
   * accepts a 2 dimensional array, allowing one to iterate through
   * a specific dimension, 0 or 1.
   *
   * @param value The 2 dimensional array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The sum of values in the argument array.
   */
  public static double sum(final double[][] value, final int dimension) {
    if (value == null)
      throw new NullPointerException("value == null");

    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    double sum = 0;
    for (int i = 0; i < value.length; i++)
      sum += value[i][dimension];

    return sum;
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   *
   * @param values The array of values.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final short[] values) {
    final double u = sum(values) / values.length;
    double sum = 0;
    for (int i = 0; i < values.length; i++)
      sum += (values[i] - u) * (values[i] - u);

    return Math.sqrt(sum / values.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   *
   * @param values The array of values.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final int[] values) {
    final double u = sum(values) / values.length;
    double sum = 0;
    for (int i = 0; i < values.length; i++)
      sum += (values[i] - u) * (values[i] - u);

    return Math.sqrt(sum / values.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   *
   * @param values The array of values.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final long[] values) {
    final double u = sum(values) / values.length;
    double sum = 0;
    for (int i = 0; i < values.length; i++)
      sum += (values[i] - u) * (values[i] - u);

    return Math.sqrt(sum / values.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   *
   * @param values The array of values.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final float[] values) {
    final double u = sum(values) / values.length;
    double sum = 0;
    for (int i = 0; i < values.length; i++)
      sum += (values[i] - u) * (values[i] - u);

    return Math.sqrt(sum / values.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   *
   * @param values The array of values.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final double[] values) {
    final double u = sum(values) / values.length;
    double sum = 0;
    for (int i = 0; i < values.length; i++)
      sum += (values[i] - u) * (values[i] - u);

    return Math.sqrt(sum / values.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   * This method accepts a 2 dimensional array, allowing one to iterate
   * through a specific dimension, 0 or 1.
   *
   * @param values The array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final short[][] data, final int dimension) {
    final double u = sum(data, dimension) / data.length;
    double sum = 0;
    for (int i = 0; i < data.length; i++)
      sum += (data[i][dimension] - u) * (data[i][dimension] - u);

    return Math.sqrt(sum / data.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   * This method accepts a 2 dimensional array, allowing one to iterate
   * through a specific dimension, 0 or 1.
   *
   * @param values The array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final int[][] data, final int dimension) {
    final double u = sum(data, dimension) / data.length;
    double sum = 0;
    for (int i = 0; i < data.length; i++)
      sum += (data[i][dimension] - u) * (data[i][dimension] - u);

    return Math.sqrt(sum / data.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   * This method accepts a 2 dimensional array, allowing one to iterate
   * through a specific dimension, 0 or 1.
   *
   * @param values The array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final long[][] data, final int dimension) {
    final double u = sum(data, dimension) / data.length;
    double sum = 0;
    for (int i = 0; i < data.length; i++)
      sum += (data[i][dimension] - u) * (data[i][dimension] - u);

    return Math.sqrt(sum / data.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   * This method accepts a 2 dimensional array, allowing one to iterate
   * through a specific dimension, 0 or 1.
   *
   * @param values The array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final float[][] data, final int dimension) {
    final double u = sum(data, dimension) / data.length;
    double sum = 0;
    for (int i = 0; i < data.length; i++)
      sum += (data[i][dimension] - u) * (data[i][dimension] - u);

    return Math.sqrt(sum / data.length);
  }

  /**
   * Calculate the standard deviation of the values in the argument array.
   * This method accepts a 2 dimensional array, allowing one to iterate
   * through a specific dimension, 0 or 1.
   *
   * @param values The array of values.
   * @param dimension The index of dimension that should be aggregated.
   *
   * @return The standard deviation of the values.
   */
  public static double stdDev(final double[][] data, final int dimension) {
    final double u = sum(data, dimension) / data.length;
    double sum = 0;
    for (int i = 0; i < data.length; i++)
      sum += (data[i][dimension] - u) * (data[i][dimension] - u);

    return Math.sqrt(sum / data.length);
  }

  /**
   * In-place normalization of argument array.
   *
   * @param data The array to normalize.
   */
  public static void normalize(final short[] data) {
    double sumLinear = 0d;
    double sumSquares = 0d;
    for (int i = 0; i < data.length; i++) {
      sumLinear += data[i];
      sumSquares += data[i] * data[i];
    }

    final double length = (double)data.length;
    final double mean = sumLinear / length;
    double scale = Math.sqrt((sumSquares - sumLinear * mean) / length);
    if (scale == 0d)
      scale = 1d;

    for (int i = 0; i < data.length; i++)
      data[i] = (short)((data[i] - mean) / scale);
  }

  /**
   * In-place normalization of argument array.
   *
   * @param data The array to normalize.
   */
  public static void normalize(final int[] data) {
    double sumLinear = 0d;
    double sumSquares = 0d;
    for (int i = 0; i < data.length; i++) {
      sumLinear += data[i];
      sumSquares += data[i] * data[i];
    }

    final double length = (double)data.length;
    final double mean = sumLinear / length;
    double scale = Math.sqrt((sumSquares - sumLinear * mean) / length);
    if (scale == 0d)
      scale = 1d;

    for (int i = 0; i < data.length; i++)
      data[i] = (int)((data[i] - mean) / scale);
  }

  /**
   * In-place normalization of argument array.
   *
   * @param data The array to normalize.
   */
  public static void normalize(final long[] data) {
    double sumLinear = 0d;
    double sumSquares = 0d;
    for (int i = 0; i < data.length; i++) {
      sumLinear += data[i];
      sumSquares += data[i] * data[i];
    }

    final double length = (double)data.length;
    final double mean = sumLinear / length;
    double scale = Math.sqrt((sumSquares - sumLinear * mean) / length);
    if (scale == 0d)
      scale = 1d;

    for (int i = 0; i < data.length; i++)
      data[i] = (long)((data[i] - mean) / scale);
  }

  /**
   * In-place normalization of argument array.
   *
   * @param data The array to normalize.
   */
  public static void normalize(final float[] data) {
    double sumLinear = 0d;
    double sumSquares = 0d;
    for (int i = 0; i < data.length; i++) {
      sumLinear += data[i];
      sumSquares += data[i] * data[i];
    }

    final double length = (double)data.length;
    final double mean = sumLinear / length;
    double scale = Math.sqrt((sumSquares - sumLinear * mean) / length);
    if (scale == 0d)
      scale = 1d;

    for (int i = 0; i < data.length; i++)
      data[i] = (float)((data[i] - mean) / scale);
  }

  /**
   * In-place normalization of argument array.
   *
   * @param data The array to normalize.
   */
  public static void normalize(final double[] data) {
    double sumLinear = 0d;
    double sumSquares = 0d;
    for (int i = 0; i < data.length; i++) {
      sumLinear += data[i];
      sumSquares += data[i] * data[i];
    }

    final double length = (double)data.length;
    final double mean = sumLinear / length;
    double scale = Math.sqrt((sumSquares - sumLinear * mean) / length);
    if (scale == 0d)
      scale = 1d;

    for (int i = 0; i < data.length; i++)
      data[i] = (data[i] - mean) / scale;
  }

  private Functions() {
  }
}
