/*  Copyright Safris Software 2014
 *
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * An efficient stream searching class based on the Knuth-Morris-Pratt algorithm. For more on the algorithm works see:
 * http://www.inf.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm.
 */
public class StreamSearcher {
  public static final byte ANY = '\0';
  protected final byte[][] pattern;
  protected final int[][] borders;

  public StreamSearcher(final byte[] ... pattern) {
    this.pattern = pattern;
    this.borders = new int[pattern.length][pattern[0].length + 1];
    for (int p = 0; p < pattern.length; p++) {
      int i = 0;
      int j = -1;
      borders[p][i] = j;
      while (i < pattern[0].length) {
        while (j >= 0 && pattern[p][i] != pattern[p][j])
          j = borders[p][j];

        borders[p][++i] = ++j;
      }
    }
  }

  /**
   * Searches for the next occurrence of the pattern in the stream, starting from the current stream position. Note that the position of the stream is changed.
   * If a match is found, the stream points to the end of the match -- i.e. the byte AFTER the pattern. Else, the stream is entirely consumed. The latter is
   * because InputStream semantics make it difficult to have another reasonable default, i.e. leave the stream unchanged.
   * 
   * @return number of bytes the stream is advanced
   * @throws IOException
   */
  public int search(final InputStream stream, final byte[] buffer, final int offset) throws IOException {
    int i = 0;
    int b = 0;
    final int[] j = new int[pattern.length];

    while ((b = stream.read()) != -1) {
      buffer[offset + i++] = (byte)b;
      for (int p = 0; p < pattern.length; p++) {
        while (j[p] >= 0 && (byte)b != pattern[p][j[p]])
          j[p] = borders[p][j[p]];

        // Move to the next character in the pattern.
        ++j[p];

        // If we've matched up to the full pattern length, we found it. Return,
        // which will automatically save our position in the InputStream at the point immediately
        // following the pattern match.
        if (j[p] == pattern[p].length)
          return i;
      }
    }

    // No dice, return false. Note that the stream is now completely consumed.
    return i;
  }
}