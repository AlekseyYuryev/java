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
	public static int binaryClosestSearch(short[] sorted, short value, int start, int end) {
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
			mid = (first + upto) / 2;	   // Compute mid point.
			if (value < sorted[mid])
				upto = mid;				 // repeat search in bottom half.
			else if (value > sorted[mid])
				first = mid + 1;			// Repeat search in top half.
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
	public static int binaryClosestSearch(int[] sorted, int value, int start, int end) {
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
			mid = (first + upto) / 2;	   // Compute mid point.
			if (value < sorted[mid])
				upto = mid;				 // repeat search in bottom half.
			else if (value > sorted[mid])
				first = mid + 1;			// Repeat search in top half.
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
	public static int binaryClosestSearch(long[] sorted, long value, int start, int end) {
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
			mid = (first + upto) / 2;	   // Compute mid point.
			if (value < sorted[mid])
				upto = mid;				 // repeat search in bottom half.
			else if (value > sorted[mid])
				first = mid + 1;			// Repeat search in top half.
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
	public static int binaryClosestSearch(float[] sorted, float value, int start, int end) {
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
			mid = (first + upto) / 2;	   // Compute mid point.
			if (value < sorted[mid])
				upto = mid;				 // repeat search in bottom half.
			else if (value > sorted[mid])
				first = mid + 1;			// Repeat search in top half.
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
	public static int binaryClosestSearch(double[] sorted, double value, int start, int end) {
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
			mid = (first + upto) / 2;	   // Compute mid point.
			if (value < sorted[mid])
				upto = mid;				 // repeat search in bottom half.
			else if (value > sorted[mid])
				first = mid + 1;			// Repeat search in top half.
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
	public static double log(int base, int value) {
		return Math.log(value) / Math.log(base);
	}

	/**
	 * Calculate the root mean suqre of an array of values.
	 *
	 * @param values The values.
	 *
	 * @return The rms.
	 */
	public static double rms(short ... values) {
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
	public static double rms(int ... values) {
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
	public static double rms(long ... values) {
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
	public static double rms(float ... values) {
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
	public static double rms(double ... values) {
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
	public static short min(short ... values) {
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
	public static int min(int ... values) {
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
	public static long min(long ... values) {
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
	public static float min(float ... values) {
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
	public static double min(double ... values) {
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
	public static short max(short ... values) {
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
	public static int max(int ... values) {
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
	public static long max(long ... values) {
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
	public static float max(float ... values) {
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
	public static double max(double ... values) {
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
	public static short threshold(short value, short min, short max) {
		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
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
	public static int threshold(int value, int min, int max) {
		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
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
	public static long threshold(long value, long min, long max) {
		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
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
	public static float threshold(float value, float min, float max) {
		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
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
	public static double threshold(double value, double min, double max) {
		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
	}

	private Functions() {
	}
}
